/****************************************************
Nombre del módulo: string.c
Descripción: TAD string
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 14/04/19
Módulos propios que necesita: tree.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
 ****************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "types.h"
#include "string.h"

/*Funciones auxiliares para crear el arbol*/
/*Destruye una cadena*/
void cadDestroy(void *cad) {
    if (!cad) return;
    free((char*) cad);

}

/*Devuelve una copia de la cadena cad*/
void * cadCopy(const void *cad) {
    char *copia = NULL;
    if (!cad)return NULL;

    /*+1 para incluir el fin de cadena \0*/
    copia = (char*) malloc((strlen((char*) cad) + 1) * sizeof (char));
    if (!copia) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    strcpy(copia, cad);

    return (void*) copia;
}

/*Muestra en el archivo pf la cadena cad*/
int cadPrint(FILE *pf, const void *cad) {
    int cont;

    if (!pf || !cad) return ERROR;

    cont = fprintf(pf, "%s ", (char*) cad);
    if (ferror(pf)) {
        fprintf(pf, "Error escribiendo el archivo\n");
        return ERROR;
    }

    return cont;
}

/*Compara dos cadenas. LLama a strcmp*/
int cadCmp(const void* a, const void* b) {
    return strcmp(a, b);
}
