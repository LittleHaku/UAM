/**
 * @file shm_consumer.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 31-03-2020
 * Descripcion: Código de Consumidor
 * */

/* CONSUMER */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <pthread.h>
#include <semaphore.h>

#include "shm_histograma.h"

#define SHM_NAME "/shm_production"
#define MAX_QUEUE 100
#define NAME_FICHERO "data.txt"

typedef struct {
    int Queue[MAX_QUEUE];
    sem_t mutex;
    sem_t empty;
    sem_t fill;
} ShmProduction;


int main(int argc, char* argv[]) {
    int i;
    int shm_fd;
    int *frecuencia = NULL;

    frecuencia = (int*)malloc(10*sizeof(int));
    if (frecuencia == NULL) {
        perror("error reservando memoria");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < 10; i++) {
        frecuencia[i] = 0;
    }

    /*Ponemos RDWR ya que los semaforos se modifican*/
    shm_fd = shm_open(SHM_NAME, O_RDWR, 0);
    if (shm_fd == -1) {
        perror("Segmento existente o error al crearlo");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }
    
    ShmProduction *producer_struct = mmap(NULL, 
        sizeof(*producer_struct),
        PROT_READ | PROT_WRITE,
        MAP_SHARED,
        shm_fd,
        0);
    close(shm_fd);

    if (producer_struct == MAP_FAILED) {
        perror("Error mapeando");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }

    /*leyendo datos*/
    i = 0;
    int c = 0;

    //Hace el bucle hasta que lee una c == -1
    while(1) {    
        sem_wait(&producer_struct->fill);
        sem_wait(&producer_struct->mutex);
        c = producer_struct->Queue[i%MAX_QUEUE];
        if(c == -1) break;

        frecuencia[c]++;
        i = (i+1) % MAX_QUEUE;
        c = producer_struct->Queue[i%MAX_QUEUE];

        sem_post(&producer_struct->mutex);
        sem_post(&producer_struct->empty);
    }

    for (i = 0; i < 10; i++) {
        printf("El numero %d sale %d veces\n", i, frecuencia[i]);
    }

    int flag = histograma_fichero(NAME_FICHERO, frecuencia);
    if(flag == HISTOGRAMA_FAIL) {
        printf("error al pasar a fichero");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }

    munmap(NULL, sizeof(producer_struct));

    free(frecuencia);
    exit(EXIT_SUCCESS);
}