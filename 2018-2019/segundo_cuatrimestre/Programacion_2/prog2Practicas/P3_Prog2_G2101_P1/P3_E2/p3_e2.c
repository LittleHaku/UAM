/****************************************************
Nombre del módulo: p3_e2.c
Descripción: test de list.c
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 06/04/19
Módulos propios que necesita: list.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "list.h"

int extern errno;

int mainDestroy (int flag, List *A, List *B);

/*Funciones auxiliares para crear la pila*/
void intDestroy (void *num) {
    if(!num) return;

    free(num);

    return;
}

void * intCopy(const void *num){
    int *aux = NULL;

    if(num == NULL)return NULL;

    aux = (int *)malloc(sizeof(int));
    if(!aux){
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    *aux = *(int*)num;

    return aux;
}

int intPrint(FILE *pf, const void *num) {
    int cont;

    if(!num) return ERROR;

    cont = fprintf(pf, "[%d]\n", *(int*)num);
    if(ferror(pf)){
        fprintf(pf, "Error escribiendo el archivo\n");
        return ERROR;
    }

    return cont;
}

int intCmp(const void *a, const void *b){
    if(!a || !b)return 0;
    return *(int*)a-*(int*)b;
}

int main(int argc, char** argv) {
    List *pla = NULL, *plb = NULL;
    int i = 0;
    
    if(argc < 2) {
        fprintf(stderr, "Introduce un entero como argumento.");
        return mainDestroy(EXIT_FAILURE, pla, plb);
    }
    
    pla = list_ini(intDestroy, intCopy, intPrint, intCmp);
    if(!pla) return mainDestroy(EXIT_FAILURE, pla, plb);
    
    plb = list_ini(intDestroy, intCopy, intPrint, intCmp);
    if(!pla) return mainDestroy(EXIT_FAILURE, pla, plb);
    
    
    /* Insertarmos en la lista A y B con el orden indicado */
    
    for(i = atoi(argv[1]); i > 0; i--) {
        /* Insertamos en A, primera lista */
        if(i%2) { /*Impar */
            if(list_insertLast(pla, (void *) &i) == ERROR){
                return mainDestroy(EXIT_FAILURE, pla, plb);
            }
        }
        else{ /*Par */
            if(list_insertFirst(pla, (void *) &i) == ERROR){
                return mainDestroy(EXIT_FAILURE, pla, plb);
            }
        }
        
        /* Insertamos en B, segunda lista */
        if(list_insertFirst(plb, (void *) &i) == ERROR){
            return mainDestroy(EXIT_FAILURE, pla, plb);
        }
    }
    
    /* Imprimimos ambas listas, A y B, respectivamente */    
    fprintf(stdout, "1º LISTA\n");
    list_print (stdout, pla);
    fprintf(stdout, "2º LISTA\n");
    list_print (stdout, plb);    
    
    return mainDestroy(EXIT_SUCCESS, pla, plb);
}


int mainDestroy (int flag, List *A, List *B) {
    list_destroy(A);
    list_destroy(B);
    
    return flag;
}
