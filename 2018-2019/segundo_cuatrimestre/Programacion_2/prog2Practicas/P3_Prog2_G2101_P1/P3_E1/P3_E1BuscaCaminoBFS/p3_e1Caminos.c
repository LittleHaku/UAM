/****************************************************
Nombre del módulo: p3_e1Caminos.c
Descripción: main a ejecutar en el ejercicio 1
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: graph.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "graph.h"

extern int errno;

int main_free(int flag, FILE *pf, Graph *g, Node *n);

int main(int argc, char** argv) {
	FILE *pf = NULL;
	Graph *g = NULL;
	Node *n = NULL;
        
	if(argc < 4)return main_free(EXIT_FAILURE, pf, g, n);

	pf = fopen(argv[1], "r");
	if(!pf) {
		fprintf (stderr, "%s\n", strerror(errno));
        return main_free(EXIT_FAILURE, pf, g, n);
	}

	g = graph_ini();
	if(!g) {
		fprintf (stderr, "%s\n", strerror(errno));
        return main_free(EXIT_FAILURE, pf, g, n);
	}

	if(graph_readFromFile (pf, g) == ERROR) return main_free(EXIT_FAILURE, pf, g, n);

	n = graph_findBreathSearch(g, atoi(argv[2]), atoi(argv[3]));

	if(!n) {
		fprintf(stdout, "No es posible encontrar un camino\n");
		return main_free(EXIT_SUCCESS, pf, g, n);
	}

	fprintf(stdout, "Es posible encontrar un camino\n");

	graph_printCaminoBFS(stdout, g, atoi(argv[2]), atoi(argv[3]));

    return main_free(EXIT_SUCCESS, pf, g, n);
}

int main_free(int flag, FILE *pf, Graph *g, Node *n) {
	fclose(pf);
	node_destroy(n);
	graph_destroy(g);

	return flag;
}
