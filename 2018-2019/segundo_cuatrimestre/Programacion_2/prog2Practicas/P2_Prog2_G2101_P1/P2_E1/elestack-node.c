/****************************************************
Nombre del módulo: elestack-node.c
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
	Node* info;
};

EleStack * EleStack_ini() {
	EleStack *e = NULL;

	e = (EleStack *) malloc(sizeof(EleStack));
	if(!e) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
	}

	e->info = node_ini();
	if(!(e->info)) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
	}

	return e;
}

void EleStack_destroy(EleStack *e) {
	if(!e) return;

	node_destroy(e->info);
	free(e);

	return;
}

Status EleStack_setInfo(EleStack *e, void *v) {
	if(!e || !v) return ERROR;

	node_destroy(e->info);

	e->info = node_copy((Node *) v);
	if(!(e->info)) {
        fprintf(stderr, "%s\n", strerror(errno));
        return ERROR;
    }

	return OK;
}


void * EleStack_getInfo(EleStack *e) {
	if(!e) return NULL;

	return (void *) e->info;
}

EleStack * EleStack_copy(const EleStack *e) {
	EleStack *ecp;

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

	if(node_cmp(e1->info, e2->info)) return FALSE;

	return TRUE;
}

int EleStack_print(FILE *pf, const EleStack *e) {
	int num;

	num = node_print(pf, e->info);
	if(ferror(pf)){
			fprintf(stderr, "Error escribiendo el archivo\n");
			return -1;
	}

	return num;
}
