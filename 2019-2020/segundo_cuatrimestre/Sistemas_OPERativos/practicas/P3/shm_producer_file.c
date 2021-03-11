/**
 * @file shm_producer_file.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 01-04-2020
 * Descripcion: Código de Productor
 * */

/* PRODUCER */
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

#define MAX_QUEUE 100
#define MAX_PRODUCERS 15
#define FILE_NAME_OUT "items.txt"

typedef struct {
    int queue[MAX_QUEUE];
    sem_t empty;
    sem_t fill;
    sem_t mutex;
} File;

int main(int argc, char* argv[]) {
    int N, aleat, i;
    int fd_file;

    srand (time(NULL));

    if (argc <3) {
        perror("Argumentos insuficientes");
        exit(EXIT_FAILURE);
    }

    N = atoi(argv[1]);
    aleat = atoi(argv[2]);

    fd_file = open(FILE_NAME_OUT, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd_file == -1) {
        perror("Error abriendo fichero");
        exit(EXIT_FAILURE);
    }

    if (ftruncate(fd_file, sizeof(File)) == -1) {
        fprintf(stderr, "Error resizing the shared file\n");
        return EXIT_FAILURE;
    }

    File *file_struct = mmap(NULL, 
        sizeof(*file_struct),
        PROT_WRITE,
        MAP_SHARED,
        fd_file,
        0);
    close(fd_file);

    sem_init(&file_struct->mutex, 1, 1);
    sem_init(&file_struct->empty, 1, MAX_QUEUE);
    sem_init(&file_struct->fill, 1, 0);

    //Algoritmo de productor-consumidor, insertando N+1 items (N items + el finalizado "-1")
    for(i = 0; i <= N; i++) {
        sem_wait(&file_struct->empty);
        sem_wait(&file_struct->mutex);

        if (aleat == 1) {
            file_struct->queue[i%MAX_QUEUE] = i%10;
        }
        else {
            file_struct->queue[i%MAX_QUEUE] = rand()%10;
        }
        if(i == N) {
            file_struct->queue[i%MAX_QUEUE] = -1;
        }
        
        printf("Elemento numero %d: %d\n", i, file_struct->queue[i%MAX_QUEUE]);


        sem_post(&file_struct->mutex);
        sem_post(&file_struct->fill);
    }

    fprintf(stdout, "Press any key to finish\n");
    getchar();

    sem_destroy(&file_struct->fill);
    sem_destroy(&file_struct->empty);
    sem_destroy(&file_struct->mutex);
    
    munmap(NULL, sizeof(file_struct));
    unlink(FILE_NAME_OUT);
    exit(EXIT_SUCCESS);
}