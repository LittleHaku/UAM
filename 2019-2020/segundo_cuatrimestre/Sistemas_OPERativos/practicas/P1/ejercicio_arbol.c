/**
 * @file ejercicio_arbol.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Ejercicio en el que hay que crear una estructura de que los hijos llamen a otros hijos NUM_PROC veces
 * */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define NUM_PROC 4

/**
 * int funcion_hijo(int cont)
 * es la funcion que ejecuta un hilo creado para hacer una estructura tipo arbol. Se llama recursivamente
 * parametros entrada: int cont es la profundidad alcanzada para que no pase de un umbral 
 * parametros salida: es void, no devuelve nada
 * */
void funcion_hijo(int cont);

int main(void)
{
    pid_t p;
    int cont = 1;

    p = fork();
    if (p < 0)
    {
        perror("fork");
        exit(EXIT_FAILURE);
    }
    else if (p == 0)
    {
        funcion_hijo(cont);
    }
    else
    {
        if (printf("Padre %d\n", cont) <= 0)
        {
            exit(EXIT_FAILURE);
        }
    }

    while (wait(NULL) != -1)
    {
    }

    exit(EXIT_SUCCESS);
}

void funcion_hijo(int cont)
{
    pid_t p;

    if (printf("Hijo %d\n", cont) <= 0)
    {
        exit(EXIT_FAILURE);
    }
    cont++;

    if (cont == NUM_PROC + 1)
    {
        exit(EXIT_SUCCESS);
    }

    p = fork();
    if (p < 0)
    {
        perror("fork");
        exit(EXIT_FAILURE);
    }
    else if (p == 0)
    {
        funcion_hijo(cont);
    }
    else
    {
        if (printf("Padre %d\n", cont) <= 0)
        {
            exit(EXIT_FAILURE);
        }
    }

    while (wait(NULL) != -1)
    {
    }

    exit(EXIT_SUCCESS);
}