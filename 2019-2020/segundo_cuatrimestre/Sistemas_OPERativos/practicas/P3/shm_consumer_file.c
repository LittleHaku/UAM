/**
 * @file shm_consumer_file.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 01-04-2020
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
#include <string.h>
#include <pthread.h>
#include <semaphore.h>

#include "shm_histograma.h"

#define MAX_QUEUE 100
#define FILE_NAME_IN "items.txt"
#define FILE_NAME_OUT "data2.txt"


typedef struct {
    int queue[MAX_QUEUE];
    sem_t empty;
    sem_t fill;
    sem_t mutex;
} File;


int main(int argc, char* argv[]) {
    int i;
    int fd_file;
    int *frecuencia = NULL;

    frecuencia = (int*)malloc(10*sizeof(int));
    if (frecuencia == NULL) {
        perror("error reservando memoria");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < 10; i++) {
        frecuencia[i] = 0;
    }

    /*ponemos RDWR porque los semaforos se modifican*/
    fd_file = open(FILE_NAME_IN, O_RDWR, 0);
    if (fd_file == -1) {
        perror("Error abriendo fichero");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }

    File *file_struct = mmap(NULL, 
        sizeof(*file_struct),
        PROT_READ | PROT_WRITE,
        MAP_SHARED,
        fd_file,
        0);
    close(fd_file);

    if (file_struct == MAP_FAILED) {
        perror("Error mapeando");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }

    i = 0;
    int c = 0;

    //Hace el bucle hasta que lee una c == -1
    while(1) {    
        sem_wait(&file_struct->fill);
        sem_wait(&file_struct->mutex);
        c = file_struct->queue[i%MAX_QUEUE];

        if(c == -1) break;

        frecuencia[c]++;
        i = (i+1) % MAX_QUEUE;
        c = file_struct->queue[i%MAX_QUEUE];

        sem_post(&file_struct->mutex);
        sem_post(&file_struct->empty);
    }
    
    for (i = 0; i < 10; i++) {
        printf("El numero %d sale %d veces\n", i, frecuencia[i]);
    }

    int flag = histograma_fichero(FILE_NAME_OUT, frecuencia);
    if(flag == HISTOGRAMA_FAIL) {
        perror("error al pasar a fichero");
        free(frecuencia);
        exit(EXIT_FAILURE);
    }

    munmap(NULL, sizeof(file_struct));

    free(frecuencia);
    exit(EXIT_SUCCESS);
}