/****************************************************
Nombre del módulo: p4_e1.c
Descripción: main que testea el tree.c
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 13/04/19
Módulos propios que necesita: tree.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
 ****************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "tree.h"

extern int errno;

int main_destroy(int, Tree *, FILE *);

/*Funciones auxiliares para el arbol*/
void intDestroy(void *);
void * intCopy(const void *);
int intPrint(FILE *, const void *);
int intCmp(const void*, const void*);

int main(int argc, char** argv) {
    Tree *t = NULL;
    FILE *f = NULL;
    int num = 0;

    /*Necesita como argumento el nombre del fichero*/
    if (argc < 2)return main_destroy(EXIT_FAILURE, t, f);

    f = fopen(argv[1], "r");
    if (!f)return main_destroy(EXIT_FAILURE, t, f);

    t = tree_ini(intDestroy, intCopy, intPrint, intCmp);
    if (!t)return main_destroy(EXIT_FAILURE, t, f);

    /*Lectura del fichero*/
    while (fscanf(f, "%d", &num) == 1) {
        tree_insert(t, (void*) &num);
    }

    /*Muestra el numero de nodos*/
    fprintf(stdout, "Numero de nodos: %d\n", tree_numNodes(t));

    /*Muestra la profundidad del arbol*/
    fprintf(stdout, "Profundidad: %d\n", tree_depth(t));

    /*Pide un numero y lo busca en el arbol*/
    fprintf(stdout, "Introduzca un numero: ");
    scanf("%d", &num);
    fprintf(stdout, "Numero introducido: %d\n", num);

    fprintf(stdout, "El dato %d %sse encuentra dentro del Arbol\n", num, tree_find(t, (void*) &num) ? "" : "no ");

    return main_destroy(EXIT_SUCCESS, t, f);
}

/*Es el destructor del main*/
int main_destroy(int flag, Tree * t, FILE *f) {

    tree_free(t);
    if (f != NULL)fclose(f);

    return flag;
}

/*Funciones auxiliares para crear el arbol*/
/*Hace free a un entero*/
void intDestroy(void *num) {
    if (!num) return;

    free(num);
}

/*Copia un entero (le hace casting)*/
void * intCopy(const void *num) {
    int *aux = NULL;

    if (num == NULL)return NULL;

    aux = (int *) malloc(sizeof (int));
    if (!aux) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    *aux = *(int*) num;

    return aux;
}

/*Imprime un entero en el FILE pf*/
int intPrint(FILE *pf, const void *num) {
    int cont;

    if (!pf || !num) return ERROR;

    cont = fprintf(pf, "[%d]\n", *(int*) num);
    if (ferror(pf)) {
        fprintf(pf, "Error escribiendo el archivo\n");
        return ERROR;
    }

    return cont;
}

/*Compara dos enteros. intCMP(a, b) < 0 sii a < b*/
int intCmp(const void* a, const void* b) {
    return *(int*) a - *(int*) b;
}
