// ----------- Arqo P4-----------------------
// pescalar_serie
//
// Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
// Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#include <stdio.h>
#include <stdlib.h>

#include "arqo4.h"

int main(int argc, char * argv[])
{
    float *A = NULL, *B = NULL;
    long long k = 0;
    struct timeval fin, ini;
    double sum = 0;
    // Por defecto M es el tamanio del vector.
    int tamanio = M;

    if(argc == 2) {
        // Si se especifica el tamanio por terminal.
        tamanio = atoi(argv[1]);
        if(tamanio <= 0) {
            printf("Error tamanio <= 0\n");
            return -1;
        }
    }

    A = generateVectorOne(tamanio);
    B = generateVectorOne(tamanio);
    if (!A || !B) {
        printf("Error when allocationg matrix\n");
        freeVector(A);
        freeVector(B);
        return -1;
    }

    gettimeofday(&ini, NULL);
    /* Bloque de computo */
    sum = 0;
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
