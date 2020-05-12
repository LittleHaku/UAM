/****************************************************
Nombre del módulo: p2_e2.c
Descripción: main a ejecutar en el ejercicio 2
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: stack_elestack.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "stack_elestack.h"

extern int errno;

double averageStack (Stack *s);
int main_free(int flag, Stack *s,  EleStack *e);

int main(int argc, char *argv[]) {
    Stack* s = NULL;
    EleStack* e = NULL;
    int i = 0, ne = 0;


    //Inicializamos cada elemento
    s = stack_ini();
    if(!s) {
        fprintf(stderr, "%s\n", strerror(errno));
        return main_free(EXIT_FAILURE, s, e);
    }

    e = EleStack_ini(e);
    if(!e) {
        fprintf(stderr, "%s\n", strerror(errno));
        return main_free(EXIT_FAILURE, s, e);
    }

    if(argc<1)main_free(EXIT_FAILURE, s, e);
    ne = atoi(argv[1]);

    //Introducimos en la pila los enteros de 0 al valor de argv[1]
    for(i = 0; i <= ne; i++) {
        if(EleStack_setInfo(e, &i) == ERROR){
            fprintf(stderr, "%s\n", strerror(errno));
    		return main_free(EXIT_FAILURE, s, e);
        }

        if (stack_push(s, e) == ERROR) {
            fprintf(stderr, "%s\n", strerror(errno));
            return main_free(EXIT_FAILURE, s, e);
        }
    }

    //Imprimimos la pila
    fprintf(stdout, "Pila antes de la llamada a la funcion:\n");

    stack_print(stdout, s);
    if(ferror(stdout)){
        fprintf(stdout, "Error escribiendo el archivo\n");
        return main_free(EXIT_FAILURE, s, e);
    }

    //Calculamos la media
    fprintf(stdout, "\nLa media es: %lf", averageStack(s));

    //Imprimimos la pila despues de la llamada a averageStack
    fprintf(stdout, "\nPila despues de la llamada a la funcion:\n");

    stack_print(stdout, s);
    if(ferror(stdout)){
        fprintf(stdout, "Error escribiendo el archivo\n");
        return main_free(EXIT_FAILURE, s, e);
    }


    return main_free(EXIT_FAILURE, s, e);
}

double averageStack (Stack *s) {
    Stack *aux = NULL;
    EleStack *e = NULL;
    double r, i = 0.0;
    int c = 0;

    aux = stack_ini();
    if(!aux) {
        fprintf(stderr, "%s\n", strerror(errno));
        return -1;
    }

    //Sacamos los elementos de la pila original y los introducimos en la auxiliar
    while (stack_isEmpty(s) != TRUE) {
        e = stack_pop(s);
        stack_push(aux, e);

        EleStack_destroy(e);
    }

    //Reintroducimos los elementos de vuelta en la pila original
    while (stack_isEmpty(aux) != TRUE) {
        e = stack_pop(aux);
        stack_push(s, e);

        c += *((int*) EleStack_getInfo(e));
        i++;

        EleStack_destroy(e);
    }

    stack_destroy(aux);

    if(i==0)return 0;
    r = c/i;

    return r;
}

int main_free(int flag, Stack *s,  EleStack *e) {
	EleStack_destroy(e);
	stack_destroy(s);

	return flag;
}
