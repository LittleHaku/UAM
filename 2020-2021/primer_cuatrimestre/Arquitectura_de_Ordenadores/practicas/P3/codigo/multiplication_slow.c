// P3 arq 2019-2020
// Codigo que realiza la multiplicacion de forma que da bastantes fallos en el cache.
// Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
// Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#include "arqo3.h"

void compute(tipo ** a, tipo ** b, tipo ** resultado, int n);

int main(int argc, char * argv[]) {
    int n;
    tipo ** a = NULL, ** b = NULL, ** resultado = NULL;
    struct timeval fin, ini;

    printf("Word size: %ld bits\n", 8 * sizeof(tipo));

    if (argc != 2) {
        printf("Error: ./%s <matrix size>\n", argv[0]);
        return -1;
    }

    n = atoi(argv[1]);
    if (n <= 0) {
        printf("Error, el tamanio de la matriz debe de ser > 1.\n");
        return -1;
    }

    a = generateMatrix(n);
    b = generateMatrix(n);
    resultado = generateEmptyMatrix(n);
    if (!a || !b || !resultado) {
        printf("Error reservando memoria.\n");
        if (a)
            freeMatrix(a);
        if (b)
            freeMatrix(b);
        if (resultado)
            freeMatrix(resultado);
        return -1;
    }

    gettimeofday( & ini, NULL);

    /* Main computation */
    compute(a, b, resultado, n);
    /* End of computation */

    gettimeofday( & fin, NULL);
    printf("Execution time: %f\n", ((fin.tv_sec * 1000000 + fin.tv_usec) - (ini.tv_sec * 1000000 + ini.tv_usec)) * 1.0 / 1000000.0);

    freeMatrix(a);
    freeMatrix(b);
    freeMatrix(resultado);
    return 0;
}

// resultado = a*b.
void compute(tipo ** a, tipo ** b, tipo ** resultado, int n) {
    int i, j, k;

    if(n <= 0 || !a || !a[0] || !b || !b[0] || !resultado || !resultado[0])
        return;

    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            // Inicializas a 0 por si nos dan una matriz resultado no vacia.
            resultado[i][j] = 0;
            for (k = 0; k < n; k++) {
                resultado[i][j] += a[i][k] * b[k][j];
            }
        }
    }
}
