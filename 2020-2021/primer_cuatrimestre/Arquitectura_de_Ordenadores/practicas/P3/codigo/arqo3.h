//P3 arq 2019-2020
// Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
// Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#ifndef _ARQO_P3_H_
#define _ARQO_P3_H_

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#if __x86_64__
    typedef double tipo;
#else
    typedef float tipo;
#endif

tipo ** generateMatrix(int);
tipo ** generateEmptyMatrix(int);
void freeMatrix(tipo ** );
// Imprime por stdout la matriz, util para debugear..
void printMatrix(tipo ** , int);

#endif /* _ARQO_P3_H_ */
