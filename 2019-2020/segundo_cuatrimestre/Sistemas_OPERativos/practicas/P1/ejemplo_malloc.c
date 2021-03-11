/**
 * @file ejemplo_malloc.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Test para saber el comportamiento de memoria dinamica en los procesos
 * */
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define MESSAGE "Hello"

int main(void)
{
    pid_t pid;
    char *sentence = calloc(sizeof(MESSAGE), 1);
    if (!sentence)
    {
        exit(EXIT_FAILURE);
    }

    pid = fork();
    if (pid < 0)
    {
        perror("fork");
        free(sentence);
        exit(EXIT_FAILURE);
    }
    else if (pid == 0)
    {
        strcpy(sentence, MESSAGE);
        free(sentence); //se libera la memoria reservada en el proceso hijo
        exit(EXIT_SUCCESS);
    }
    else
    {
        wait(NULL);
        printf("Padre: %s\n", sentence);
        free(sentence); //se libera la memoria reservada en el proceso padre3
        exit(EXIT_SUCCESS);
    }
}
