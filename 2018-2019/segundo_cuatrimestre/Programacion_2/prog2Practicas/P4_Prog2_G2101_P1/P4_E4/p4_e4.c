/****************************************************
Nombre del módulo: p4_e4.c
Descripción: main que testea el tree.c insertando cadenas
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

#include "tree.h"
#include "string.h"

extern int errno;

int main_destroy(int, Tree *, FILE *);

int main(int argc, char** argv) {
    Tree *t = NULL;
    FILE *f = NULL;
    char cad[MAX_STR];

    /*Necesita como argumento el nombre del fichero*/
    if (argc < 2){
      fprintf(stderr, "ERROR EN LOS ARGUMENTOS DE ENTRADA\n");
      return main_destroy(EXIT_FAILURE, t, f);
    }

    f = fopen(argv[1], "r");
    if (!f)return main_destroy(EXIT_FAILURE, t, f);

    t = tree_ini(cadDestroy, cadCopy, cadPrint, cadCmp);
    if (!t)return main_destroy(EXIT_FAILURE, t, f);

    /*Lee los datos del fichero*/
    while (fscanf(f, "%s", cad) == 1) {
        tree_insert(t, (void*) cad);
    }

    /*Muestra el numero de nodos del arbol*/
    fprintf(stdout, "Numero de nodos: %d\n", tree_numNodes(t));

    /*Muestra la profundidad del arbol*/
    fprintf(stdout, "Profundidad: %d\n", tree_depth(t));

    /*Muestra el recorrido in0rder*/
    tree_inOrder(stdout, t);
    fprintf(stdout, "\n");

    /*Busca una cadena en el arbol*/
    fprintf(stdout, "Introduce una cadena para buscar en el árbol (siguiendo el mismo formato que en el fichero de entrada): ");
    scanf("%s", cad);

    fprintf(stdout, "Elemento %sencontrado!\n", tree_find(t, (void*) cad) ? "" : "no ");

    return main_destroy(EXIT_SUCCESS, t, f);
}

/*Destructor del main*/
int main_destroy(int flag, Tree * t, FILE *f) {

    tree_free(t);
    if (f != NULL)fclose(f);

    return flag;
}
