/****************************************************
Nombre del módulo: node_p4.c
Descripción: estructura del nodo y sus funciones respectivas, modificado para la nueva estructura
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 14/04/19
Módulos propios que necesita: node_p4.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
 ****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "types.h"
#include "node.h"

#define MAX_NAME 128

extern int errno;

struct _Node {
    char* name;
    int id;
};

/*Inicializa el nodo*/
Node * node_ini() {
    Node *n;
    n = (Node *) malloc(sizeof (Node));
    if (!n) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    node_setName(n, "");
    node_setId(n, -1);

    return n;
}

/*Destruye el nodo*/
void node_destroy(void * n) {
    if (!n)return;

    free(((Node *) n)->name);
    free((Node *) n);

    return;
}

/*Devuelve el id del nodo*/
int node_getId(const Node * n) {
    if (!n)return ERROR;

    return (n->id);
}

/*Devuelve el nombre del nodo*/
char* node_getName(const Node * n) {
    if (!n)return NULL;

    return (char*) n->name;
}

/*Setter del id*/
Node * node_setId(Node * n, const int id) {
    if (!n)return NULL;

    n->id = id;

    return n;
}

/*Setter del name*/
Node * node_setName(Node * n, const char* name) {
    if (!n)return NULL;

    /*si la cadena esta vacia no se reserva memoria, se da el caso cuando se llama a esta funcion desde node_ini()*/
    if (!name || strcmp(name, "") == 0) {
        n->name = NULL;
        return n;
    }

    free(n->name);
    n->name = NULL;

    /* +1 para poder insertar el fin de cadena*/
    n->name = malloc((strlen(name) + 1) * sizeof (char));
    if (!n->name) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    strcpy(n->name, name);

    return n;
}

/*Compara nodos por el id, node_cmp(a, b) < 0 sii a < b*/
int node_cmp(const void * n1, const void * n2) {
    if (!n1 || !n2)return -2;

    if (node_getId((Node*)n1) == node_getId((Node*)n2))return 0;

    if (node_getId((Node*)n1) < node_getId((Node*)n2))return -1;

    return 1;
}

/*Devuelve una copia del nodo src*/
void * node_copy(const void * src) {
    Node *n = NULL;

    if (!src) return NULL;

    n = node_ini();
    if (!n) {
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
    }

    node_setId(n, node_getId((Node*) src));
    node_setName(n, node_getName((Node*) src));

    return n;
}

/*Muestra por pantalla la informacion del nodo n por el FILE pf*/
int node_print(FILE *pf, const void * n) {
    int cont = 0;

    if (!pf || !n)return ERROR;

    cont = fprintf(pf, "[%d, %s]", node_getId((Node *) n), node_getName((Node *) n));
    if (ferror(pf)) {
        fprintf(stderr, "Error escribiendo el archivo\n");
        return ERROR;
    }

    return cont;
}
