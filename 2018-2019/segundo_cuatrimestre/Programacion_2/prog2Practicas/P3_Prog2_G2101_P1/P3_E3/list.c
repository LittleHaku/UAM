/****************************************************
Nombre del módulo: list.c
Descripción: lista circular
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

typedef struct _NodeList {
    void* info;
    struct _NodeList *next;/*next nunca va a ser NULL pues es una lista circular*/
} NodeList;

struct _List {
    NodeList *last; /*La LEC apunta al último nodo y el último al primero*/
    destroy_element_function_type destroy_element_function;
    copy_element_function_type copy_element_function;
    print_element_function_type print_element_function;
    cmp_element_function_type cmp_element_function;
};

NodeList * NodeList_ini() {
    NodeList * pnl = NULL;

    pnl = (NodeList *) malloc(sizeof (NodeList));
    if (!pnl) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    pnl->info = NULL;
    pnl->next = NULL;

    return pnl;

}

void NodeList_destroy(List *list, NodeList *pnl) {

    if (!list || !pnl)return;

    list->destroy_element_function(pnl->info);

    free(pnl);

}

List* list_ini(destroy_element_function_type f1, copy_element_function_type f2, print_element_function_type f3,
        cmp_element_function_type f4) {
    List * pl = NULL;

    if (!f1 || !f2 || !f3 || !f4)return NULL;

    pl = (List *) malloc(sizeof (List));
    if (!pl) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    pl->destroy_element_function = f1;
    pl->copy_element_function = f2;
    pl->print_element_function = f3;
    pl->cmp_element_function = f4;


    pl->last = NULL;

    return pl;

}

void list_destroy(List* list) {
    NodeList *pn = NULL, *paux = NULL;

    if (!list)return;

    if (!list->last) {
        free(list);
        return;
    }

    pn = list->last->next;
    while (pn != list->last) {
        paux = pn->next;
        NodeList_destroy(list, pn);
        pn = paux;
    }
    
    NodeList_destroy(list, list->last);

    free(list);

}

Status list_insertFirst(List* list, const void *pelem) {
    void * pele = NULL;
    NodeList *pnl = NULL;


    
    if (!list || !pelem)return ERROR;

    pele = list->copy_element_function(pelem);
    if (!pele)return ERROR;

    pnl = NodeList_ini();
    if (!pnl) {
        list->destroy_element_function(pele);
        return ERROR;
    }

    pnl->info = pele;

    
    /*Caso la lista este vacia*/
    if(list_isEmpty(list) == TRUE){
        list->last = pnl;
        pnl->next = pnl;
        
        return OK;
    }
    
    pnl->next = list->last->next;
    
    list->last->next = pnl;

    return OK;

}

Status list_insertLast(List* list, const void *pelem) {
    NodeList *pnl = NULL;
    void *pele = NULL;
    
    if (!list || !pelem)return ERROR;

    pele = list->copy_element_function(pelem);
    if (!pele)return ERROR;

    pnl = NodeList_ini();
    if (!pnl) {
        list->destroy_element_function(pele);
        return ERROR;
    }

    pnl->info = pele;

    /*Es el caso de que la lista este vacia*/
    if (list_isEmpty(list) == TRUE) {
        list->last = pnl;
        pnl->next = pnl;
        return OK;
    }

    pnl->next = list->last->next;

    list->last->next = pnl;
    list->last = pnl;

    return OK;
}

Status list_insertInOrder(List *list, const void *pelem) {
    NodeList *pnl = NULL, *paux = NULL;
    void *pele = NULL;

    if (!list || !pelem)return ERROR;
    
    /*si pelem>list->last->info*/
    if (list->cmp_element_function(pelem, list->last->info) > 0) {
        return list_insertLast(list, pelem);
    }

    if (list->cmp_element_function(pelem, list->last->next->info) < 0) {
        return list_insertFirst(list, pelem);
    }


    pele = list->copy_element_function(pelem);
    if (!pele)return ERROR;

    pnl = NodeList_ini();
    if (!pnl) {
        list->destroy_element_function(pele);
        return ERROR;
    }

    pnl->info = pele;

    paux = list->last->next;

    while (list->cmp_element_function(pele, paux->next->info) > 0) {
        paux = paux->next;
    }

    pnl->next = paux->next;

    paux->next = pnl;

    return OK;

}

void * list_extractFirst(List* list) {
    void *pele = NULL;
    NodeList *pnlAux = NULL;

    if (!list || list_isEmpty(list) == TRUE)return NULL;

    /*Si solo hay un elemento*/
    if (list->last == list->last->next) {
        pele = list->last->info;
        list->last->info = NULL;

        NodeList_destroy(list, list->last);

        list->last = NULL;

        return pele;
    }

    /*Guarda el elemento en pele*/
    pele = list->last->next->info;
    list->last->next->info = NULL;

    pnlAux = list->last->next;

    list->last->next = pnlAux->next;

    NodeList_destroy(list, pnlAux);


    return pele;
}

void * list_extractLast(List* list) {
    void *pele = NULL;
    NodeList *pnlAux = NULL, *pnlPenultimo = NULL;

    if (!list || list_isEmpty(list) == TRUE)return NULL;

    /*Si solo hay un elemento*/
    if (list->last == list->last->next) {
        pele = list->last->info;
        list->last->info = NULL;

        NodeList_destroy(list, list->last);

        list->last = NULL;

        return pele;
    }

    /*Guarda el elemento en pele*/
    pele = list->last->info;
    list->last->info = NULL;

    pnlAux = list->last;

    pnlPenultimo = list->last->next;
    while (pnlPenultimo->next != list->last)pnlPenultimo = pnlPenultimo->next;

    pnlPenultimo->next = pnlPenultimo->next->next;

    list->last = pnlPenultimo;
    
    NodeList_destroy(list, pnlAux);

    return pele;
}

Bool list_isEmpty (const List* list){
    if(!list || list->last == NULL)return TRUE;
    return FALSE;
}

const void* list_get(const List* list, int index) {
    int i = 0;
    NodeList *pnl = NULL;
    
    if (!list || index < 0 || index >= list_size(list) || list_isEmpty(list) == TRUE) return NULL;

    pnl = list->last->next;

    while (i != index) {
        pnl = pnl->next;
        i++;
    }
    
    /*Devuelve una copia de la info*/
    return (const void*) list->copy_element_function(pnl->info);

}

int list_size(const List* list) {
    int cont = 1;
    NodeList *pnl = NULL;

    if (!list) return -1;

    if (list_isEmpty(list) == TRUE) return 0;

    pnl = list->last->next;

    for (; pnl != list->last; pnl = pnl->next, cont++);

    return cont;
}

int list_print(FILE *fd, const List* list) {
    int cont = 0;
    NodeList *pnl = NULL;

    if (!fd || !list) return -1;

    if (list_isEmpty(list) == TRUE) return 0;

    pnl = list->last->next;
    cont += list->print_element_function(fd, pnl->info);
    pnl = pnl->next;

    for (; pnl != list->last->next; pnl = pnl->next)
        cont += list->print_element_function(fd, pnl->info);
  
    cont += fprintf(fd, "\n");
    
    return cont;
}
