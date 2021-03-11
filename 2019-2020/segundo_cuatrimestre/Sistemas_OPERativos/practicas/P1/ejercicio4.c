/**
 * @file ejercicio4.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Es el .c del ejercicio4 para ver que con sleep el proceso esta suspendido
 * pero que si usas un bucle while no, sino que se esta ejecutando
 * */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>

int main(int argc, char **argv)
{

    if (printf("empiezo con pid: %d\n", getpid()) <= 0)
    {
        exit(EXIT_FAILURE);
    }
    //sleep(10);
    clock_t t1 = clock();
    if (t1 < 0)
        return 0;

    while (clock() - t1 <= 10 * CLOCKS_PER_SEC)
    {
    }

    return 0;
}
