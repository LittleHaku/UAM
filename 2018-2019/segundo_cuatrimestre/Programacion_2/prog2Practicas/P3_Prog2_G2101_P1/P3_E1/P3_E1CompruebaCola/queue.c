/****************************************************
Nombre del módulo: queue.c
Descripción: queue implementada con un array
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 06/04/19
Módulos propios que necesita: queue.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>

#include "queue.h"

struct _Queue {
    void* items [MAXQUEUE];
    int front;
    int rear;
    destroy_element_function_type destroy_element_function;
    copy_element_function_type copy_element_function;
    print_element_function_type print_element_function;
};

Queue* queue_ini(destroy_element_function_type f1, copy_element_function_type f2, print_element_function_type f3) {
    Queue *pq = NULL;
    int i = 0;

    if (!f1 || !f2 || !f3) return NULL;

    pq = (Queue *) malloc(sizeof (Queue));
    if (!pq) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    for (i = 0; i < MAXQUEUE; i++) {
        pq->items[i] = NULL;
    }

    pq->destroy_element_function = f1;
    pq->copy_element_function = f2;
    pq->print_element_function = f3;

    pq->front = 0;
    pq->rear = 0;

    return pq;
}

void queue_destroy(Queue *q) {
    int i;

    if (!q) return;
    
    for (i = q->front; i != q->rear; i=(i+1) % MAXQUEUE) {
        q->destroy_element_function(q->items[i]);
    }

    free(q);

    return;
}

Bool queue_isEmpty(const Queue *q) {
    if (!q)return TRUE;

    if (q->front == q->rear)return TRUE;

    return FALSE;
}

Bool queue_isFull(const Queue* queue) {
    if (!queue)return TRUE;

    if (queue->rear == (queue->front + MAXQUEUE - 1) % MAXQUEUE) return TRUE;

    return FALSE;
}

Status queue_insert(Queue *q, const void* pElem) {
    void *aux = NULL;
    if (!q || !pElem || queue_isFull(q) == TRUE)return ERROR;

    aux = q->copy_element_function(pElem);
    if (!aux)return ERROR;


    q->items[q->rear] = aux;

    q->rear = (q->rear + 1) % MAXQUEUE;

    return OK;

}

void * queue_extract(Queue *q) {
    void * pi = NULL;

    if (!q || queue_isEmpty(q) == TRUE)return NULL;

    pi = q->items[q->front];

    q->front = (q->front + 1) % MAXQUEUE;

    return pi;

}

int queue_size(const Queue *q) {
    if (!q)return ERROR;

    return (q->rear - q->front + MAXQUEUE) % MAXQUEUE;

}

int queue_print(FILE *pf, const Queue *q) {
    int cont = 0, i;
    if (!pf || !q)return ERROR;

    if (queue_isEmpty(q)) {
        return fprintf(pf, "Queue vacia.\n");
    }

    cont += fprintf(pf, "Cola con %d elementos:\n", queue_size(q));

    for (i = q->front; i != q->rear; i = (i + 1) % MAXQUEUE) {
        cont += q->print_element_function(pf, q->items[i]);
    }

    cont += fprintf(pf, "\n");

    return cont;
}
