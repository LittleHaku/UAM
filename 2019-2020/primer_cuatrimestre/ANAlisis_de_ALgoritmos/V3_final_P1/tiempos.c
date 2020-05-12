/**
 *
 * Descripcion: Implementacion de funciones de tiempo
 *
 * Fichero: tiempos.c
 * Autor: Junco de las Heras Valenzuela && Jia Xue Jin
 * Version: 1.0
 * Fecha: 27-09-2019
 *
 */

#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "tiempos.h"
#include "ordenacion.h"
#include "permutaciones.h"

/************************************************************/
/* Funcion: tiempo_medio_ordenacion Fecha: 27-09-2019       */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin    */
/*                                                          */
/* Rutina que rellena una instancia de TIEMPO con           */
/* la ejecucion de una llamada a metodo                     */
/*                                                          */
/* Entrada:                                                 */
/* pfunc_ordena metodo: funcion a llamar                    */
/* int n_perms: numero de permutaciones que le hacen el test*/
/* int N: tamanio de cada permutacion                       */
/* PTIEMPO: estructura TIEMPO a rellenar                    */
/* Salida:                                                  */
/* int: OK si no ha habido errores, sino ERR                */
/************************************************************/
short tiempo_medio_ordenacion(pfunc_ordena metodo,
        int n_perms,
        int N,
        PTIEMPO ptiempo) {
    /* vuestro codigo */
    int **tablas = NULL, OBS, max_obs = -1, min_obs = -1, j;
    clock_t t1, t2;

    if (!metodo || n_perms <= 0 || N <= 0 || !ptiempo)
        return ERR;

    tablas = genera_permutaciones(n_perms, N);
    if (!tablas)
        return ERR;

    t1 = clock();

    ptiempo->medio_ob = 0;

    for (j = 0; j < n_perms; j++) {
        OBS = metodo(tablas[j], 0, N - 1);

        if (OBS == ERR) {
            for (j = 0; j < n_perms; j++) {
                free(tablas[j]);
            }
            free(tablas);
            return ERR;
        }

        ptiempo->medio_ob += OBS / (double) n_perms;

        if (j == 0)
            max_obs = min_obs = OBS;
        else {
            if (OBS > max_obs)
                max_obs = OBS;
            if (OBS < min_obs)
                min_obs = OBS;
        }
    }

    t2 = clock();

    ptiempo->N = N;
    ptiempo->n_elems = n_perms;

    ptiempo->tiempo = (t2 - t1) / (double) n_perms;

    ptiempo->min_ob = min_obs;
    ptiempo->max_ob = max_obs;


    for (j = 0; j < n_perms; j++) {
        free(tablas[j]);
    }
    free(tablas);

    return OK;
}

/*****************************************************************************/
/* Funcion: genera_tiempos_ordenacion Fecha: 27-09-2019                      */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin                     */
/*                                                                           */
/* Rutina que genera los tiempos y llama a guarda_tabla_tiempos              */
/*                                                                           */
/*                                                                           */
/* Entrada:                                                                  */
/* pfunc_ordena: funcion a cronometrar                                       */
/* char* fichero: fichero a escribir                                         */
/* int num_min: tamanio inferior                                             */
/* int num_max: tamanio superior                                             */
/* int incr: cada cuantos valores llamar a latiempo_medio_ordenacion         */
/* int n_perms: numero de permutaciones que le hacen el test con cada tamanio*/
/* Salida:                                                                   */
/* int: OK si no ha habido erroes sino ERR                                   */
/*****************************************************************************/
short genera_tiempos_ordenacion(pfunc_ordena metodo, char* fichero,
        int num_min, int num_max,
        int incr, int n_perms) {
    /* vuestro codigo */
    int j, cont = 0, tam_tabla;
    PTIEMPO tiempos;

    if (!metodo || !fichero || num_min > num_max || incr <= 0 || n_perms <= 0)
        return ERR;

    /*tam_tabla = ceil((num_max - num_min) / (double) incr));*/
    tam_tabla = (num_max - num_min + 1 + (incr - 1)) / incr;

    tiempos = (PTIEMPO) malloc(tam_tabla * sizeof (TIEMPO));
    if (!tiempos)
        return ERR;

    for (j = num_min; j <= num_max; j += incr) {
        if (tiempo_medio_ordenacion(metodo, n_perms, j, &tiempos[cont++]) == ERR) {
            free(tiempos);
            return ERR;
        }
    }

    if (guarda_tabla_tiempos(fichero, tiempos, n_perms) == ERR) {
        free(tiempos);
        return ERR;
    }

    free(tiempos);
    return OK;
}

/********************************************************************/
/* Funcion: guarda_tabla_tiempos Fecha 27-09-2019                   */
/* Autores: Junco de las Heras Valenzuela && Jia Xue Jin            */
/*                                                                  */
/* Rutina que guarda el contenido de PTIEMPO en el fichero fichero  */
/*                                                                  */
/*                                                                  */
/* Entrada:                                                         */
/* char* fichero: fichero a escribir                                */
/* PTIEMPO: array de TIEMPO con los datos a guardar en el fichero   */
/* int n_tiempos: tamanio del array                                 */
/* Salida:                                                          */
/* int: OK si no ha habido erroes sino ERR                          */
/********************************************************************/
short guarda_tabla_tiempos(char* fichero, PTIEMPO tiempo, int n_tiempos) {
    /* vuestro codigo */
    int i;
    FILE *f = NULL;
    if (!fichero || !tiempo || n_tiempos < 0)
        return ERR;

    f = fopen(fichero, "w");
    if (!f)
        return ERR;

    for (i = 0; i < n_tiempos; i++) {
        if (fprintf(f, "%d %f %f %d %d\n", tiempo[i].N, tiempo[i].tiempo, tiempo[i].medio_ob, tiempo[i].max_ob, tiempo[i].min_ob) <= 0) {
            fclose(f);
            return ERR;
        }
    }

    if (fclose(f) != 0)
        return ERR;

    return OK;
}
