// ----------- Arqo P4-----------------------
// Programa que crea hilos utilizando OpenMP
//
// Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
// Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    int tid, nthr, nproc;
    int arg;
    nproc = omp_get_num_procs();
    printf("Hay %d cores disponibles\n", nproc);

    if (argc == 2)
        arg = atoi(argv[1]);
    else
        arg = nproc;

    // Ejecutar en una terminal bash como
    // export OMP_NUM_THREADS=3 && ./omp1 4
    printf("Usando la variable de entorno intenta crear %d hilos\n", omp_get_max_threads());

    printf("Usando la funcion intenta crear %d hilos\n", arg);
    omp_set_num_threads(arg);
    nthr = omp_get_max_threads();
    printf("Me han pedido que lance %d hilos\n", nthr);

#pragma omp parallel private(tid) num_threads(arg + 1)
    {
        tid = omp_get_thread_num();
        nthr = omp_get_num_threads();
        printf("Hola, soy el hilo %d de %d\n", tid, nthr);
    }

    return 0;
}
