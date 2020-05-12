/****************************************************
Nombre del módulo: node.c
Descripción: estructura del nodo y sus funciones respectivas
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: node.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "node.h"

#define MAX_L 100

extern int errno;

struct _Node {
    char name[MAX_L];
    int id;
    int nConnect;
};


// ----- Implementacion funciones publicas

Node * node_ini(){
    Node *n = NULL;

    n = (Node *) malloc(sizeof(Node));
	if(!n) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
    }

    n->nConnect = 0;
    return n;
}

void node_destroy(Node *n){
	if(!n) return;

    free(n);

	return;
}

int node_getId(const Node *n){
    if(!n) return -1;

    return n->id;
}

char * node_getName(const Node *n){
    if(!n) return NULL;

    return (char *)n->name;
}

int node_getConnect(const Node *n){
    if(!n) return -1;

    return n->nConnect;
}

Node * node_setId(Node *n, const int id){
    if(!n) return NULL;

    n->id = id;
    return n;
}

Node * node_setName(Node *n, const char *name){
    if(!n) return NULL;

    strncpy(n->name, name, MAX_L-1);

    return n;
}

Node * node_setConnect(Node *n, const int cn){
    if(!n) return NULL;

	n->nConnect = cn;

    return n;
}

int node_cmp(const Node *n1, const Node *n2){
    if(!n1||!n2)return ERROR;

    if(node_getId(n1)==node_getId(n2))return 0;

    if(node_getId(n1)<node_getId(n2))return -1;

    return 1;
}

Node * node_copy(const Node *src){
    Node *n = NULL;

    if(!src) return NULL;

    n = node_ini();
	if(!n) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
    }

    node_setId(n, node_getId(src));
    node_setName(n, node_getName(src));
    node_setConnect(n, node_getConnect(src));

    return n;
}

int node_print(FILE *pf, const Node *n){
    int num;

    if(!pf || !n) return -1;

    num = fprintf(pf, "[%d, %s, %d]", node_getId(n), node_getName(n), node_getConnect(n));
    if (ferror(pf)) {
		fprintf(stderr, "Error escribiendo el archivo\n");
		return -1;
	}

    return num;
}
