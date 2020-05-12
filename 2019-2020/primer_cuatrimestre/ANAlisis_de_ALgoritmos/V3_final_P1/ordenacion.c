/**
 *
 * Descripcion: Implementacion de funciones de ordenacion
 *
 * Fichero: ordenacion.c
 * Autores: Junco de las Heras Valenzuela && Jia Xue Jin
 * Version: 1.0
 * Fecha: 20-09-2019
 *
 */


#include "ordenacion.h"


/********************************************************/
/* Funcion: InsertSort Fecha: 20-09-2019                */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin*/
/*                                                      */
/* Rutina que ordena con InsertSort, de menor a mayor   */
/*                                                      */
/*                                                      */
/* Entrada:                                             */
/* int* tabla: tabla a ordenar                          */
/* int ip: limite inferior                              */
/* int iu: limite superior                              */
/* Salida:                                              */
/* int: numero de veces que se ha hecho la CDC          */
/********************************************************/
int InsertSort(int* tabla, int ip, int iu) {
    /* vuestro codigo */
    /*var_swap is the temporaly variable to swap elements*/
    int i, j, var_swap, cont = 0;

    if (!tabla || ip > iu || ip < 0)
        return ERR;

    for (i = ip + 1; i <= iu; i++) {
        var_swap = tabla[i];
        j = i - 1;
        while (j >= ip && ++cont && tabla[j] > var_swap) {
            tabla[j + 1] = tabla[j];
            j--;
        }
        tabla[j + 1] = var_swap;
    }

    return cont;
}

/********************************************************/
/* Funcion: InsertSortInv Fecha: 20-09-2019             */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin*/
/*                                                      */
/* Rutina que ordena con InsertSort, de mayor a menor   */
/*                                                      */
/*                                                      */
/* Entrada:                                             */
/* int* tabla: tabla a ordenar                          */
/* int ip: limite inferior                              */
/* int iu: limite superior                              */
/* Salida:                                              */
/* int: numero de veces que se ha hecho la CDC          */
/********************************************************/
int InsertSortInv(int* tabla, int ip, int iu) {
    /* vuestro codigo */
    /*var_swap is the temporaly variable to swap elements*/
    int i, j, var_swap, cont = 0;

    if (!tabla || ip > iu || ip < 0)
        return ERR;

    for (i = ip + 1; i <= iu; i++) {
        var_swap = tabla[i];
        j = i - 1;
        while (j >= ip && ++cont && tabla[j] < var_swap) {
            tabla[j + 1] = tabla[j];
            j--;
        }
        tabla[j + 1] = var_swap;
    }

    return cont;
}
