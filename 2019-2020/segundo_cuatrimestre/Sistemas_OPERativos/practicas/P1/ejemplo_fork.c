/**
 * @file ejemplo_fork.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Ejemplo de como un padre lanza a 3 hijos
 * */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define NUM_PROC 3

int main(void)
{
    pid_t pid, ppadre;
    for (int i = 0; i < NUM_PROC; i++)
    {
        ppadre = getpid();
        if (ppadre <= 0)
        {
            exit(EXIT_FAILURE);
        }
        pid = fork();
        if (pid < 0)
        {
            perror("fork");
            exit(EXIT_FAILURE);
        }
        else if (pid == 0)
        {
            printf("Hijo %d %d\n", getpid(), ppadre);
            exit(EXIT_SUCCESS);
        }
        else if (pid > 0)
        {
            printf("Padre %d \n", i);
        }
    }
    //Se espera a que acaben todos los hijos
    while (wait(NULL) != -1)
    {
    }

    exit(EXIT_SUCCESS);
}