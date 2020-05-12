/****************************************************
Nombre del módulo: stack_elestack.c
Descripción: estructura de la pila y sus funciones respectivas
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

# define MAXSTACK 1024

extern int errno;

struct _Stack {
	int top;
	EleStack * item[MAXSTACK];
};

Stack * stack_ini() {
	Stack *s = NULL;
        int i;

	s = (Stack *) malloc(sizeof(Stack));
	if(!s) {
            fprintf(stderr, "%s\n", strerror(errno));
            return NULL;
	}

	for(i=0; i<MAXSTACK; i++){
		s->item[i] = NULL;
	}

	s->top = -1;

	return s;
}

void stack_destroy(Stack *s) {
	int i;

	if(!s) return;

	for(i=0; i < (s->top + 1); i++){
		EleStack_destroy(s->item[i]);
	}
	free(s);

	return;
}

Status stack_push(Stack *s, const EleStack *e){
	EleStack *ecp = NULL;

	if(!s || !e || stack_isFull(s) == TRUE) return ERROR;

	ecp = EleStack_copy(e);

	if(!ecp){
            fprintf(stderr, "%s\n", strerror(errno));
            return ERROR;
  }

	s->top++;
	s->item[s->top] = ecp;

	return OK;
}

EleStack * stack_pop(Stack *s){
  EleStack *aux = NULL;

  if(!s || stack_isEmpty(s) == TRUE) return NULL;

	aux = s->item[s->top];
	s->item[s->top] = NULL;
	s->top--;

	return aux;
}

Bool stack_isEmpty(const Stack *s){
    if(s == NULL) return TRUE;

    if(s->top == -1) return TRUE;

    return FALSE;
}

Bool stack_isFull(const Stack *s){
    if(s == NULL) return TRUE;

    if(s->top == MAXSTACK-1) return TRUE;

    return FALSE;
}

int stack_print(FILE *pf, const Stack *s){
    int i, num;
    if(!pf || !s) return -1;

    for(num=0, i=s->top; i>=0; i--){
        num += EleStack_print(pf, s->item[i]);
        if(ferror(pf)){
            fprintf(stderr, "Error escribiendo el archivo\n");
            return -1;
        }
				if (i != 0) num += fprintf(pf, "\n");
    }

    return num;
}
