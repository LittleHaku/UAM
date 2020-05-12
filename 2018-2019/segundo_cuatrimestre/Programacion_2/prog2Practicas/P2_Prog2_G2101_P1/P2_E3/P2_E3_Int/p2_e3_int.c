/****************************************************
Nombre del módulo: p2_e3_int.c
Descripción: main a ejecutar en el ejercicio 3 int
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: stack_fp.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/


#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "stack_fp.h"

extern int errno;

void intDestroy (void *);
void * intCopy(const void *);
int intPrint(FILE *, const void *);
double Stack_media(Stack *);
int mainFree(int, Stack *);

int main(int argc, char ** argv) {
    Stack *s = NULL;
    int num = 0, i = 0;


    if (argc < 2) {
        fprintf(stdout, "Error en el numero introducido\n");
        return mainFree(EXIT_FAILURE, s);
    }

    /*El numero que se pasa por parámetro se guarda en el argv[1]*/
    num = atoi(argv[1]);

    /*Inicializamos cada elemento*/
    s = stack_ini(intDestroy, intCopy, intPrint);
    if (!s) {
        fprintf(stdout, "%s", strerror(errno));
        return mainFree(EXIT_FAILURE, s);
    }


    /*Hace push a los elementos del 0 al num incluidos*/
    for (i = 0; i <= num; i++){
        if(stack_push(s, &i) == ERROR){
            fprintf(stderr, "%s", strerror(errno));
            return mainFree(EXIT_FAILURE, s);
        }
    }

    /*Muestra la pila, luego la media y por último otra vez la pila*/

    fprintf(stdout, "Pila antes de la llamada a la función:\n");
    stack_print(stdout, s);
    if(ferror(stdout)){
        fprintf(stderr, "Error escribiendo el archivo\n");
        return mainFree(EXIT_FAILURE, s);
    }

    fprintf(stdout, "La media es %lf\n", Stack_media(s));

    fprintf(stdout, "Pila después de la llamada a la función:\n");
    stack_print(stdout, s);
    if(ferror(stdout)){
        fprintf(stderr, "Error escribiendo el archivo 2\n");
        return mainFree(EXIT_FAILURE, s);
    }

    return mainFree(EXIT_SUCCESS, s);
}

void intDestroy (void *num) {
    if(!num) return;

    free(num);

    return;
}

void * intCopy(const void *num){
    int *aux = NULL;

    if(!num)return NULL;

    aux = (int *)malloc(sizeof(int));
    if(!aux){
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    *aux = *(int*)num;

    return aux;
}

int intPrint(FILE *pf, const void *num) {
    int cont;

    if(!num) return ERROR;

    cont = fprintf(pf, "[%d]\n", *(int*)num);
    if(ferror(pf)){
        fprintf(pf, "Error escribiendo el archivo\n");
        return ERROR;
    }

    return cont;
}

double Stack_media(Stack *s) {
    Stack *aux = NULL;
    double media = 0, cont = 0;
    int *top = NULL;

    if(!s)return ERROR;

    aux = stack_ini(intDestroy, intCopy, intPrint);
    if (!aux) {
        fprintf(stderr, "%s\n", strerror(errno));
        return ERROR;
    }

    /*Mientras pasa los elementos de la pila a la pila auxiliar va calculando la media
     y luego vuelve a traspasar los elementos a la pila original*/

    while (stack_isEmpty(s) != TRUE) {

        top = (int *) stack_pop(s);
        if (!top) return ERROR;

        media += *top;

        ++cont;

        if(stack_push(aux, top) == ERROR) {
            fprintf(stderr, "%s\n", strerror(errno));
            return ERROR;
        }

        intDestroy(top);
        top = NULL;
    }

    /*Reubicación de los elementos de la pila de la auxiliar a la original*/
    while (stack_isEmpty(aux) != TRUE) {

        top = stack_pop(aux);
        if (!top) return ERROR;

        if(stack_push(s, top) == ERROR) {
            fprintf(stderr, "%s\n", strerror(errno));
            return ERROR;
        }

        intDestroy(top);
        top = NULL;
    }

    stack_destroy(aux);

    /*evita la division de 0*/
    if (cont == 0) return 0;

    return media / cont;
}

int mainFree(int FLAG, Stack *s) {
    stack_destroy(s);
    return FLAG;
}
