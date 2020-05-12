/****************************************************
Nombre del módulo: stack_fp.c
Descripción: estructura de la pila y sus funciones respectivas
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: stack_fp.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "stack_fp.h"

#define MAXSTACK 1024
#define EMPTY_STACK -1

extern int errno;

struct _Stack {
    int top;
    void * item[MAXSTACK];

    P_stack_ele_destroy pf_destroy;
    P_stack_ele_copy pf_copy;
    P_stack_ele_print pf_print;

};

Stack *stack_ini(P_stack_ele_destroy fd, P_stack_ele_copy fc, P_stack_ele_print fp) {
    Stack *s = NULL;
    int i;

    if(!fd || ! fc || !fp)return NULL;

    s = (Stack*) malloc(sizeof (Stack));
    if (!s) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    /* Inicializo tope y asigno los punteros a función */
    s->top = -1;
    s->pf_destroy = fd;
    s->pf_copy = fc;
    s->pf_print = fp;

    /* Asigno los punteros de los elementos */
    for (i = 0; i < MAXSTACK; i++)
        s->item[i] = NULL;

    return s;
}

void stack_destroy(Stack* s) {
    if (!s) return;

    while (s->top != EMPTY_STACK) {
        /*Llamada a la función cuyo puntero se almacenó como campo
        de la pila, es decir, utilizando el puntero a la función*/
        s->pf_destroy(s->item[s->top]);

        s->top--;
    }

    free(s);

    return;
}

Status stack_push(Stack *s, const void *pv) {
    void *aux = NULL;

    if (!s || !pv)return ERROR;
    if (stack_isFull(s) == TRUE)return ERROR;

    aux = s->pf_copy(pv);
    if (!aux) {
        fprintf(stderr, "%s", strerror(errno));
        return ERROR;
    }

    s->top++;
    s->item[s->top] = aux;

    return OK;
}

void * stack_pop(Stack *s){
    void *aux = NULL;

    if(!s)return NULL;

    if(stack_isEmpty(s) == TRUE) return NULL;

    aux = s->item[s->top];
    s->item[s->top] = NULL;
    s->top--;

    return aux;
}

Bool stack_isEmpty(const Stack *s){
    if(!s)return TRUE;
    if(s->top == EMPTY_STACK) return TRUE;

    return FALSE;
}

Bool stack_isFull(const Stack *s){

    if(!s)return TRUE;
    if(s->top == MAXSTACK-1) return TRUE;

    return FALSE;
}

int stack_print(FILE *pf, const Stack *s){
    int i, cont = 0;
    if(!s || !pf) return ERROR;

    for(i=s->top; i >= 0; i--){
        cont += s->pf_print(pf, s->item[i]);
        if(ferror(pf)){
            fprintf(stderr,"Error escribiendo el archivo\n");
            return ERROR;
        }
    }
    return cont;
}
