/**
 * @file ejercicio3.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Es el .c del ejercicio 3 para comprobar el errno
 * */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>

extern int errno;

int main(int argc, char **argv)
{
    int prev_errno = 0;
    FILE *f = NULL;

    if (argc <= 1)
    {
        return 0;
    }

    f = fopen(argv[1], "r");
    if (f != NULL)
    {
        if (fclose(f) == EOF)
        {
            exit(EXIT_FAILURE);
        }
        return 0;
    }
    //En caso de error te muestra el valor de errno y su mensaje de error
    prev_errno = errno;
    if (printf("errno: %d\n", prev_errno) <= 0)
    {
        exit(EXIT_FAILURE);
    }
    errno = prev_errno;
    perror("Error");

    return 0;
}
