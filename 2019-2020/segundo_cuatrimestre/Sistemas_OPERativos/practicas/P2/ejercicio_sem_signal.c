/**
 * @file ejercicio_sem_signal.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 11-03-2020
 * Descripcion: Test para conocer como funcionan los semaforos con señales.
 * */

#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <signal.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

#define SEM_NAME "/example_sem"

void manejador(int sig) {
    return;
}

int main(void) {
	sem_t *sem = NULL;
    struct sigaction act;

	if ((sem = sem_open(SEM_NAME, O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 0)) == SEM_FAILED) {
		perror("sem_open");
		exit(EXIT_FAILURE);
	}

    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;

    /* Se arma la señal SIGINT. */
    act.sa_handler = manejador;
    if (sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    //signal(SIGINT, SIG_IGN);

	printf("Entrando en espera (PID=%d)\n", getpid());
    
	while(sem_wait(sem) == -1);

    printf("Fin de la espera\n");
    sem_close(sem);
	sem_unlink(SEM_NAME);
}
