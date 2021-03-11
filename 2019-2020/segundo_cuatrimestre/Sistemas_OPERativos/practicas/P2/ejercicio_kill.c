/**
 * @file ejercicio_kill.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 21-02-2020
 * Descripcion: Test para ver como funciona el kill
 * */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>


int main(int argc, char* argv[]) {
    int signal = 0;
    pid_t pid;

    //Se compruba que se reciben los argumentos
    if (argc != 3) {
        perror("No hay suficientes argumentos");
        exit(EXIT_FAILURE);
    }

    signal = atoi(argv[1]);
    pid = atoi(argv[2]);

    //Comprobar que señal se envia y a que pid
    if(printf("envio\n%d %d:\n", pid, signal) <= 0) {
        exit(EXIT_FAILURE);
    }

    //envio señal
    if(kill(pid, signal) == -1) {
        exit(EXIT_FAILURE);
    }
    
    exit(EXIT_SUCCESS);
}