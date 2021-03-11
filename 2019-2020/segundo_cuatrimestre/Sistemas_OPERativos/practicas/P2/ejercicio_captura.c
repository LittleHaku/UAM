/**
 * @file ejercicio_captura.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 23-02-2020
 * Descripcion: Test para conocer como funciona la captura de señales.
 * */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>

/* manejador: rutina de tratamiento de la señal SIGINT. */
void manejador(int sig) {
    printf("Señal número %d recibida \n", sig);
    fflush(stdout);
}

int main(void) {
    struct sigaction act;

    act.sa_handler = manejador;
    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;

    if (sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    //Para el ejercicio4
    int i;
    for(i = 1; i <= 31; i++) {
        if (sigaction(i, &act, NULL) < 0) {
            printf("Failed in %d\n", i); //9 y 19 no se pueden ejecutar
            perror("sigaction");
            //exit(EXIT_FAILURE);
        }
    }

    while(1) {
        printf("En espera de Ctrl+C (PID = %d)\n", getpid());
        sleep(9999);
    }
}
