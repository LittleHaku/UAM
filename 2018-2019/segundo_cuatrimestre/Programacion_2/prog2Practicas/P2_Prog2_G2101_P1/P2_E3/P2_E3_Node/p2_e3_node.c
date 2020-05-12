/****************************************************
Nombre del módulo: p2_e3_node.c
Descripción: main a ejecutar en el ejercicio 3 node
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: stack_fp.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "node.h"
#include "stack_fp.h"

extern int errno;

int mainFree(int STATUS, Node *n, Stack *s);

int main() {
    Node *n = NULL;
    Stack *s = NULL;
    void *pv = NULL;
    int caracteresImpresos = 0;

    /*Inicializamos cada uno de los elementos*/
    s = stack_ini(node_destroy, node_copy, node_print);
    if (!s) {
        fprintf(stderr, "%s", strerror(errno));
        return mainFree(EXIT_FAILURE, n, s);
    }

    n = node_ini();
    if (!n) {
        fprintf(stderr, "%s", strerror(errno));
        return mainFree(EXIT_FAILURE, n, s);
    }

    /*Definición del nodo. Se guarda este en el elemento, y se inserta el elemento en la pila*/
    node_setName(n, "first");
    node_setId(n, 111);
    node_setConnect(n, 0);

    if (stack_push(s, (void *)n) == ERROR) {
        fprintf(stderr, "ERROR AL INSERTAR EN LA PILA");
        return mainFree(EXIT_FAILURE, n, s);
    }

    /*Se repite con otro nodo*/
    node_setName(n, "second");
    node_setId(n, 222);

    if (stack_push(s, (void *)n) == ERROR) {
        fprintf(stderr, "ERROR AL INSERTAR EN LA PILA");
        return mainFree(EXIT_FAILURE, n, s);
    }


    /*Impresión del contenido de la pila*/
    fprintf(stdout, "Contenido de la pila:\n");
    caracteresImpresos = stack_print(stdout, s);
    if(ferror(stdout)){
            fprintf(stderr,"Error escribiendo el archivo\n");
            return mainFree(EXIT_FAILURE, n, s);
    }
    fprintf(stdout, "Caracteres impresos: %d\n", caracteresImpresos);

    /*Vaciado de la pila*/
    fprintf(stdout, "Vaciando pila. Extracciones:\n");
    while (stack_isEmpty(s) == FALSE) {
        node_print(stdout, pv = stack_pop(s));

        if(ferror(stdout)){
            fprintf(stderr,"Error escribiendo el archivo\n");
            return mainFree(EXIT_FAILURE, n, s);
        }

        node_destroy(pv);
        pv = NULL;

    }
    fprintf(stdout, "\nContenido de la pila después de vaciar:");
    caracteresImpresos = stack_print(stdout, s);
    if(ferror(stdout)){
            fprintf(stderr,"Error escribiendo el archvo\n");
            return mainFree(EXIT_FAILURE, n, s);
    }
    fprintf(stdout, "\nCaracteres imprimidos: %d\n", caracteresImpresos);


    return mainFree(EXIT_SUCCESS, n, s);
}

int mainFree(int STATUS, Node *n, Stack *s){
    node_destroy(n);
    stack_destroy(s);

    return STATUS;
}
