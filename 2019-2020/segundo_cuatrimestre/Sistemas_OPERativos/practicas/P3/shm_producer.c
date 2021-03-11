/**
 * @file shm_producer.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 31-03-2020
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
#include <pthread.h>
#include <semaphore.h>

#define SHM_NAME "/shm_production"
#define MAX_QUEUE 100

typedef struct {
    int Queue[MAX_QUEUE];
    sem_t mutex;
    sem_t empty;
    sem_t fill;
} ShmProduction;


int main(int argc, char* argv[]) {
    int N, aleat, i;
    int shm_fd;

    srand (time(NULL));

    if (argc < 3) {
        printf("Argumentos insuficientes");
        exit(EXIT_FAILURE);
    }

    N = atoi(argv[1]);
    aleat = atoi(argv[2]);

    shm_fd = shm_open(SHM_NAME, O_RDWR | O_CREAT | O_EXCL, S_IRUSR | S_IWUSR);
    if (shm_fd == -1) {
        perror("Segmento existente o error al crearlo");
        exit(EXIT_FAILURE);
    }

    if (ftruncate(shm_fd, sizeof(ShmProduction)) == -1) {
        fprintf(stderr, "Error resizing the shared memory segment\n");
        shm_unlink(SHM_NAME);
        return EXIT_FAILURE;
    }

    ShmProduction *producer_struct = mmap(NULL, 
        sizeof(*producer_struct),
        PROT_WRITE,
        MAP_SHARED,
        shm_fd,
        0);
    close(shm_fd);

    if (producer_struct == MAP_FAILED) {
        perror("Error mapeando");
        shm_unlink(SHM_NAME);
        exit(EXIT_FAILURE);
    }

    sem_init(&producer_struct->mutex, 1, 1);
    sem_init(&producer_struct->empty, 1, MAX_QUEUE);
    sem_init(&producer_struct->fill, 1, 0);
    
    //Algoritmo de productor-consumidor, insertando N+1 items (N items + el finalizado "-1")
    for(i = 0; i <= N; i++) {
        sem_wait(&producer_struct->empty);
        sem_wait(&producer_struct->mutex);

        if (aleat == 1) {
            producer_struct->Queue[i%MAX_QUEUE] = i%10;
        }
        else {
            producer_struct->Queue[i%MAX_QUEUE] = rand()%10;
        }
        if(i == N) {
            producer_struct->Queue[i%MAX_QUEUE] = -1;
        }
        
        printf("Elemento numero %d: %d\n", i, producer_struct->Queue[i%MAX_QUEUE]);


        sem_post(&producer_struct->mutex);
        sem_post(&producer_struct->fill);
    }

    fprintf(stdout, "Press any key to finish\n");
    getchar();

    sem_destroy(&producer_struct->mutex);
    sem_destroy(&producer_struct->fill);
    sem_destroy(&producer_struct->empty);
    
    munmap(NULL, sizeof(producer_struct));
    shm_unlink(SHM_NAME);
    exit(EXIT_SUCCESS);
}