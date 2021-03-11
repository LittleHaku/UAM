/**
 * @file ejemplo_buffer.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Test para conocer como funciona el buffer de un FILE
 * */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(void)
{
    pid_t pid;

    if (printf("Yo soy tu padre\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (fflush(stdout) == EOF)
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
        if (printf("Noooooo\n") <= 0)
        {
            exit(EXIT_FAILURE);
        }
        if (fflush(stdout) == EOF)
        {
            exit(EXIT_FAILURE);
        }
        exit(EXIT_SUCCESS);
    }

    wait(NULL);
    exit(EXIT_SUCCESS);
}
