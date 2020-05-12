/****************************************************
Nombre del módulo: elestack-int.c
Descripción: estructura del elemento de la pila y sus funciones respectivas
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: elestack.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "elestack.h"

extern int errno;

struct _EleStack {
	int* info;
};

EleStack * EleStack_ini() {
	EleStack *e = NULL;

	e = (EleStack *) malloc(sizeof(EleStack));
	if(!e) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
	}

	e->info = NULL;

	return e;
}

void EleStack_destroy(EleStack *e) {
	if(!e) return;

	free(e->info);
	free(e);

	return;
}

Status EleStack_setInfo(EleStack *e, void *v) {
	int* vaux = NULL;

	if(!e || !v) return ERROR;

	free(e->info);

	vaux = (int *) malloc(sizeof(int));
	if(!vaux) {
		fprintf(stderr, "%s\n", strerror(errno));
		return ERROR;
	}

	*vaux = *((int *) v);
	e->info = vaux;

	return OK;
}


void * EleStack_getInfo(EleStack *e) {
	if(!e) return NULL;

	return (void *) e->info;
}

EleStack * EleStack_copy(const EleStack *e) {
	EleStack *ecp = NULL;

	if(!e) return NULL;

	ecp = EleStack_ini();
	if(!ecp) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
	}

	if(EleStack_setInfo(ecp, (void *) e->info) == ERROR) return NULL;

	return ecp;
}

Bool EleStack_equals(const EleStack *e1, const EleStack *e2) {
	if(!e1 || !e2) return FALSE;

	if(*(e1->info) - *(e2->info)) return FALSE;

	return TRUE;
}

int EleStack_print(FILE *pf, const EleStack *e) {
	int num;

	num = fprintf(pf, "[%d]", *(e->info));
	if(ferror(pf)){
            fprintf(pf, "Error escribiendo el archivo\n");
            return -1;
	}

	return num;
}
