#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#include "tree.h"
#include "node.h"
#include "types.h"

#define MAX_STR 1024

int mainDestroy(int, FILE *, Node*, Tree*);

int main(int argc, char const *argv[]){
	FILE *f=NULL;
	Node *n=NULL;
	int i;
	char cad[MAX_STR];
	Tree *t=NULL;

	/*Necesita el nombre del fichero como argumento*/
	if(argc < 2){
		fprintf(stderr, "ARGUMENTOS INCORRECTOS\n");
		return EXIT_FAILURE;
	}

	f=fopen(argv[1], "r");
	if(!f){
		fprintf(stderr, "ERROR AL ABRIR EL ARCHIVO");
		return mainDestroy(EXIT_FAILURE, f, n, t);
	}

	n = node_ini();
	if(!n){
		return mainDestroy(EXIT_FAILURE, f, n, t);
	}

	t = tree_ini(node_destroy, node_copy, node_print, node_cmp);
	if(!t){
		return mainDestroy(EXIT_FAILURE, f, n, t);
	}


/*Lee del fichero, la funcion principal de este ejercicio*/
	while(fscanf(f, "%d %s", &i, cad) == 2){
		node_setName(n, cad);
		node_setId(n, i);
		tree_insert(t, n);

	}

	/*Muestra el recorrido in0rder*/
	fprintf(stdout, "Comprobación de buena lectura, reccorrido in Order\n");
	tree_inOrder(stdout, t);



	return mainDestroy(EXIT_SUCCESS, f, n, t);
}

/*Destructor del main*/
int mainDestroy(int salida, FILE *f, Node*n, Tree*t){

	if(f!=NULL)fclose(f);
	node_destroy(n);
	tree_free(t);

	return salida;
}
