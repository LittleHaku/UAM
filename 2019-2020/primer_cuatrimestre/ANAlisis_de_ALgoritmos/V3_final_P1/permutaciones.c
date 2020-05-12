/**
 *
 * Descripcion: Implementacion de funciones de generacion de permutaciones
 *
 * Fichero: permutaciones.c
 * Autor: Junco de las Heras Valenzuela && Jia Xue Jin
 * Version: 1.0
 * Fecha: 13-09-2019
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include "permutaciones.h"

/********************************************************/
/* Funcion: aleat_num Fecha: 13-09-2019                 */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin*/
/*                                                      */
/* Rutina que genera un numero aleatorio                */
/* entre dos numeros dados                              */
/*                                                      */
/* Entrada:                                             */
/* int inf: limite inferior                             */
/* int sup: limite superior                             */
/* Salida:                                              */
/* int: numero aleatorio                                */
/********************************************************/
int aleat_num(int inf, int sup) {
    if (inf > sup)
        return ERR;
    return (rand() / (RAND_MAX + 1.0))*(sup - inf + 1) + inf;
}

/********************************************************/
/* Funcion: genera_perm Fecha: 13-09-2019               */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin*/
/*                                                      */
/* Rutina que genera una permutacion                    */
/* aleatoria                                            */
/*                                                      */
/* Entrada:                                             */
/* int n: Numero de elementos de la                     */
/* permutacion                                          */
/* Salida:                                              */
/* int *: puntero a un array de enteros                 */
/* que contiene a la permutacion                        */
/* o NULL en caso de error                              */
/********************************************************/
int* genera_perm(int N) {
    int *perm = NULL, i, ale, swap_var;
    if (N <= 0)
        return NULL;

    perm = (int*) malloc(N * sizeof (int));
    if (!perm)
        return NULL;
    for (i = 0; i < N; i++)
        perm[i] = i + 1;
    for (i = 0; i < N; i++) {
        ale = aleat_num(i, N - 1);
        swap_var = perm[ale];
        perm[ale] = perm[i];
        perm[i] = swap_var;
    }

    return perm;
}

/********************************************************/
/* Funcion: genera_permutaciones Fecha: 13-09-2019      */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin*/
/*                                                      */
/* Funcion que genera n_perms permutaciones             */
/* aleatorias de tamanio elementos                      */
/*                                                      */
/* Entrada:                                             */
/* int n_perms: Numero de permutaciones                 */
/* int N: Numero de elementos de cada                   */
/* permutacion                                          */
/* Salida:                                              */
/* int**: Array de punteros a enteros                   */
/* que apuntan a cada una de las                        */
/* permutaciones                                        */
/* NULL en caso de error                                */
/********************************************************/
int** genera_permutaciones(int n_perms, int N) {
    int **perms = NULL, i;
    if (n_perms < 0 || N < 0)
        return NULL;

    perms = (int**) malloc(n_perms * sizeof (int*));
    if (!perms)
        return NULL;

    for (i = 0; i < n_perms; i++) {
        perms[i] = genera_perm(N);
        if (!perms[i]) {
            for (i--; i >= 0; i--) {
                free(perms[i]);
            }
            free(perms);
            return NULL;
        }
    }

    return perms;
}
