/**
 * @file shm_histogram.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 01-04-2020
 * Descripcion: Implementacion de las funciones de shm_histograma.h
 * */
#include <stdio.h>
#include <stdlib.h>

#include "shm_histograma.h"

#define MAX 10

int histograma_fichero(char* filename, int *listado) {
    FILE *f = NULL;
    int i;

    if (filename == NULL || listado == NULL) {
        printf("algun dato nulo");
        return HISTOGRAMA_FAIL;
    }

    f = fopen(filename, "w");
    if (f == NULL) {
        perror("error abriendo fichero");
        return HISTOGRAMA_FAIL;
    }

    for (i = 0; i < MAX; i++) {
        fprintf(f, "%d\t%d\n", i, listado[i]);
    }

    fclose(f);
    return HISTOGRAMA_SUCCESS;
}