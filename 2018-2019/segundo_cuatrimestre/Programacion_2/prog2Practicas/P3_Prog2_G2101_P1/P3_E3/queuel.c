/****************************************************
Nombre del módulo: queuel.c
Descripción: queue implementada con una lista
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 06/04/19
Módulos propios que necesita: queuel.h list.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>

#include "queuel.h"
#include "list.h"

struct _Queue {
    List *l;

    int size;
};

/*Solo se va a usar para crear la lista*/
int DummyFunction(const void* v1, const void* v2){
    return 0;
}

Queue* queue_ini(destroy_element_function_type f1, copy_element_function_type f2, print_element_function_type f3){
    Queue *q = NULL;
    if(!f1 || !f2 || !f3)return NULL;

    q = (Queue *)malloc(sizeof(Queue));
    if(!q){
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    /*EL cuarto argumento es necesario porque list_ini comprueba los parametos de entrada*/
    q->l = list_ini(f1, f2, f3, DummyFunction);
    q->size = 0;

    return q;
}

void queue_destroy(Queue *q){
    if(!q)return;

    list_destroy(q->l);

    free(q);

}

Bool queue_isEmpty(const Queue *q){
    if(!q)return TRUE;
    return list_isEmpty(q->l);
}

Bool queue_isFull(const Queue* queue){
    if(!queue)return TRUE;
    return FALSE;
}

Status queue_insert(Queue *q, const void* pElem){
    if(!q || !pElem)return ERROR;

    if(list_insertLast(q->l, pElem) == ERROR) return ERROR;

    q->size++;

    return OK;
}

void * queue_extract(Queue *q){
    void *v = NULL;
    if(!q)return NULL;

    v= list_extractFirst(q->l);
    
    if(!v) return NULL;

    q->size--;

    return v;
}

int queue_size(const Queue *q){
    if(!q)return -1;
    return q->size;
}

int queue_print(FILE *pf, const Queue *q){
    int cont=0;
    if(!pf || !q)return 0;

    if(queue_isEmpty(q) == TRUE){
        return fprintf(pf, "Queue vacia.\n");
    }
    cont = fprintf(pf, "Cola con %d elementos:\n", queue_size(q));
    return cont + list_print(pf, q->l);
}
