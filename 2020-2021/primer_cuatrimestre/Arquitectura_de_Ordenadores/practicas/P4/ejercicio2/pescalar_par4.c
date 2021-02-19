// ----------- Arqo P4-----------------------
// pescalar_par4
// pescalar para lanzar desde la terminal cambiando el tamanio del vector
// y del numero de threads, para luego graficarlo.
//
// Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
// Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

#include "arqo4.h"

#define MIN_TAMANIO 1000

int main(int argc, char * argv[])
{
    int nproc, tamanio;
    float *A = NULL, *B = NULL;
    long long k = 0;
    struct timeval fin, ini;
    double sum = 0;

    if (argc != 3) {
        printf("Error: ./%s <vector size> <nproc>\n", argv[0]);
        return -1;
    }
    tamanio = atoi(argv[1]);
    nproc = atoi(argv[2]);

    if(tamanio <= 0 || nproc <= 0) {
        printf("Error en los argumentos de entrada\n");
        return -1;
    }

    A = generateVectorOne(tamanio);
    B = generateVectorOne(tamanio);
    if (!A || !B) {
        printf("Error when allocationg matrix\n");
        freeVector(A);
        freeVector(B);
        return -1;
    }

    omp_set_num_threads(nproc);

    printf("El vector tiene tamanio %d\n", tamanio);
    printf("Se han lanzado %d hilos.\n", nproc);

    gettimeofday(&ini, NULL);
    /* Bloque de computo */
    sum = 0;

    #pragma omp parallel for if(tamanio > MIN_TAMANIO) reduction(+:sum) shared(tamanio)
    for (k = 0; k < tamanio; k++) {
        sum = sum + A[k] * B[k];
    }
    /* Fin del computo */
    gettimeofday(&fin, NULL);

    printf("Resultado: %f\n", sum);
    printf("Tiempo: %f\n",
           ((fin.tv_sec * 1000000 + fin.tv_usec) -
            (ini.tv_sec * 1000000 + ini.tv_usec)) * 1.0 / 1000000.0);
    freeVector(A);
    freeVector(B);

    return 0;
}
