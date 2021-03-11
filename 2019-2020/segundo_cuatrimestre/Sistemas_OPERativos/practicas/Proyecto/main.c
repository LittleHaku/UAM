/**
 * @file main.c
 * @author Teaching team of SOPER y Junco de las Heras junco.heras@estudiante.uam.es y Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 25-04-2020
 * Descripcion: Main para comprobar nuestro codigo.
 * */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "sort.h"

int main(int argc, char *argv[]) {
    int n_levels, n_processes, delay;
    clock_t inicio, fin;
    Status ret;

    if (argc < 4) {
        fprintf(stderr, "Usage: %s <FILE> <N_LEVELS> <N_PROCESSES> [<DELAY>]\n", argv[0]);
        fprintf(stderr, "    <FILE> :        Data file\n");
        fprintf(stderr, "    <N_LEVELS> :    Number of levels (1 - %d)\n", MAX_LEVELS);
        fprintf(stderr, "    <N_PROCESSES> : Number of processes (1 - %d)\n", MAX_PARTS);
        fprintf(stderr, "    [<DELAY>] :     Delay (ms)\n");
        exit(EXIT_FAILURE);
    }

    n_levels = atoi(argv[2]);
    n_processes = atoi(argv[3]);
    if (argc > 4) {
        delay = 1e6 * atoi(argv[4]);
    }
    else {
        delay = 1e8;
    }

    inicio = clock();
    if (inicio < 0) {
        return EXIT_FAILURE;
    }

    /*Descomentar la funcion a utilizar, single_process o multy_process*/
    /*return sort_single_process(argv[1], n_levels, n_processes, delay);*/
    
    ret = sort_multy_process(argv[1], n_levels, n_processes, delay);
    fin = clock()-inicio;
    if (fin < 0) {
        return EXIT_FAILURE;
    }

    printf("Ha durado %f\n", ((double)fin)/(CLOCKS_PER_SEC));

   return ret;
}
