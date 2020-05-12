/****************************************************
Nombre del módulo: p2_e1.c
Descripción: main a ejecutar en el ejercicio 1
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

int main_free(int flag, Stack *s,  EleStack *e ,Node *n);

int main() {
	int nc;
	Node *n = NULL;
	Stack *s = NULL;
	EleStack *e = NULL;

	n = node_ini();
	if(!n) {
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

	s = stack_ini();
	if(!s) {
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

	e = EleStack_ini();
	if(!e) {
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

  	/*Primer nodo*/
	node_setName(n, "first");
	node_setId(n, 111);

  	if(EleStack_setInfo(e, (void *) n) == ERROR){
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

	if (stack_push(s, e) == ERROR) {
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

	/*Segundo nodo*/
	node_setName(n, "second");
	node_setId(n, 222);

	EleStack_setInfo(e, (void *) n);
  	if(EleStack_setInfo(e, (void *) n) == ERROR){
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

	if (stack_push(s, e) == ERROR) {
		fprintf(stderr, "%s\n", strerror(errno));
		return main_free(EXIT_FAILURE, s,  e , n);
	}

  	/*Imprimimos la pila*/
	fprintf(stdout, "Contenido de la pila:\n");
	nc = stack_print(stdout, s);
	if(ferror(stdout)){
			fprintf(stderr, "Error escribiendo el archivo\n");
			return main_free(EXIT_FAILURE, s,  e , n);
	}
	fprintf(stdout, "\nCaracteres impresos: %d\n", nc);

	EleStack_destroy(e);
	e = NULL;
  	/*Vaciamos la pila y volvemos a imprimir*/
	fprintf(stdout, "Vaciando pila. Extracciones:\n");
	while (stack_isEmpty(s) != TRUE) {
		e = stack_pop(s);
		if(!e) main_free(EXIT_FAILURE, s,  e , n);

		EleStack_print(stdout, e);
		if(ferror(stdout)){
				fprintf(stderr, "Error escribiendo el archivo\n");
				return main_free(EXIT_FAILURE, s,  e , n);
		}
		EleStack_destroy(e);
		e = NULL;
	}

	fprintf(stdout, "\nContenido de la pila despues de vaciar:\n");
	nc = stack_print(stdout, s);
	if(ferror(stdout)){
			fprintf(stderr, "Error escribiendo el archivo\n");
			return main_free(EXIT_FAILURE, s,  e , n);
	}
	fprintf(stdout, "Caracteres impresos: %d\n", nc);

	return main_free(EXIT_SUCCESS, s,  e , n);
}

int main_free(int flag, Stack *s,  EleStack *e , Node *n) {
	node_destroy(n);
	EleStack_destroy(e);
	stack_destroy(s);

	return flag;
}
