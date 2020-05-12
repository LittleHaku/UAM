/****************************************************
Nombre del módulo: tree.c
Descripción: implementacion de un arbol binario
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

/*La estructura del arbol*/
typedef struct _NodeBT {
    void* info;
    struct _NodeBT* left;
    struct _NodeBT* right;
} NodeBT;

struct _Tree {
    NodeBT *root;
    destroy_elementtree_function_type destroy_element_function;
    copy_elementtree_function_type copy_element_function;
    print_elementtree_function_type print_element_function;
    cmp_elementtree_function_type cmp_element_function;
};

/*devuelve el maximo entre a y b enteros*/
int int_max(int a, int b) {
    return a >= b ? a : b;
}

/*node_ini, es privada*/
NodeBT * nbt_ini() {
    NodeBT *nbt = NULL;

    nbt = (NodeBT*) malloc(sizeof (NodeBT));
    if (!nbt) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    /*Inicializa todo a NULL*/
    nbt->info = NULL;
    nbt->left = NULL;
    nbt->right = NULL;

    return nbt;
}

/*Destruye un nodo, es privada*/
void nbt_free(Tree *t, NodeBT *nbt) {
    if (!t || !nbt)return;

    t->destroy_element_function(nbt->info);

    free(nbt);

}

/*Crea un arbol con las funciones f_i */
Tree* tree_ini(destroy_elementtree_function_type f1, copy_elementtree_function_type f2, print_elementtree_function_type f3, cmp_elementtree_function_type f4) {
    Tree *t = NULL;

    if (!f1 || !f2 || !f3 || !f4)return NULL;

    t = (Tree *) malloc(sizeof (Tree));
    if (!t) {
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    t->destroy_element_function = f1;
    t->copy_element_function = f2;
    t->print_element_function = f3;
    t->cmp_element_function = f4;
    t->root = NULL;

    return t;

}

/*Devuelve true si el arbol esta vacio*/
Bool tree_isEmpty(const Tree *pa) {
    if (!pa || pa->root == NULL)return TRUE;
    return FALSE;
}

/*Funcion recursiva para el tree_free, privada*/
void tree_free_rec(Tree *pa, NodeBT *nbt) {
    if (!nbt)return;

    tree_free_rec(pa, nbt->left);
    tree_free_rec(pa, nbt->right);

    nbt_free(pa, nbt);
}

/*Destruye el tree*/
void tree_free(Tree *pa) {
    if (!pa)return;

    tree_free_rec(pa, pa->root);

    free(pa);
}

/*Funcion recursiva para el tree_insert, es privada*/
Status tree_insert_rec(Tree *pa, NodeBT **nbt, const void *po) {
    NodeBT *nbtNuevo = NULL;

    if (pa->cmp_element_function(po, (*nbt)->info) < 0) {
        /*Hay que insertar en la izq*/
        if ((*nbt)->left == NULL) {

            nbtNuevo = nbt_ini();
            if (!nbtNuevo)return ERROR;

            nbtNuevo->info = pa->copy_element_function(po);
            if (!nbtNuevo->info) {
                nbt_free(pa, nbtNuevo);
                return ERROR;
            }

            (*nbt)->left = nbtNuevo;

            return OK;

        } else {
            return tree_insert_rec(pa, &(*nbt)->left, po);
        }
    } else if (pa->cmp_element_function(po, (*nbt)->info) > 0) {
        /*Hay que insertar en la derecha*/
        if ((*nbt)->right == NULL) {

            nbtNuevo = nbt_ini();
            if (!nbtNuevo)return ERROR;

            nbtNuevo->info = pa->copy_element_function(po);
            if (!nbtNuevo->info) {
                nbt_free(pa, nbtNuevo);
                return ERROR;
            }

            (*nbt)->right = nbtNuevo;

            return OK;
        } else {
            return tree_insert_rec(pa, &(*nbt)->right, po);
        }
    }

    /*En caso de que quieras insertar un elemento ya insertado*/
    return ERROR;

}

/*Inserta un nodo con la info po en el tree pa*/
Status tree_insert(Tree *pa, const void *po) {
    NodeBT *nuevo = NULL;

    if (!pa || !po)return ERROR;

    /*Si esta el arbol vacio no hace falta recursion*/
    if (tree_isEmpty(pa) == TRUE) {
        nuevo = nbt_ini();
        if (!nuevo)return ERROR;

        nuevo->info = pa->copy_element_function(po);
        if (!nuevo->info) {
            pa->destroy_element_function(nuevo);
            return ERROR;
        }
        pa->root = nuevo;
        return OK;
    }

    return tree_insert_rec(pa, &pa->root, po);

}

/*funcion recursiva de la funcion tree_pre0rder, privada*/
void tree_preOrder_rec(FILE *f, const Tree *pa, NodeBT *nbt) {
    if (!nbt)return;

    pa->print_element_function(f, nbt->info);

    tree_preOrder_rec(f, pa, nbt->left);
    tree_preOrder_rec(f, pa, nbt->right);

}

/*Muestra el recorrido en pre0rder del arbol*/
Status tree_preOrder(FILE *f, const Tree *pa) {
    if (!f || !pa)return ERROR;

    tree_preOrder_rec(f, pa, pa->root);

    return OK;

}

/*funcion recursiva de la funcion tree_in0rder, privada*/
void tree_inOrder_rec(FILE *f, const Tree *pa, NodeBT *nbt) {
    if (!nbt)return;

    tree_inOrder_rec(f, pa, nbt->left);

    pa->print_element_function(f, nbt->info);

    tree_inOrder_rec(f, pa, nbt->right);

}

/*Muestra el recorrido en in0rder del arbol*/
Status tree_inOrder(FILE *f, const Tree *pa) {
    if (!f || !pa)return ERROR;

    tree_inOrder_rec(f, pa, pa->root);

    return OK;

}

/*funcion recursiva de la funcion tree_post0rder, privada*/
void tree_postOrder_rec(FILE *f, const Tree *pa, NodeBT *nbt) {
    if (!nbt)return;

    tree_postOrder_rec(f, pa, nbt->left);
    tree_postOrder_rec(f, pa, nbt->right);

    pa->print_element_function(f, nbt->info);

}

/*Muestra el recorrido en post0rder del arbol*/
Status tree_postOrder(FILE *f, const Tree *pa) {
    if (!f || !pa)return ERROR;

    tree_postOrder_rec(f, pa, pa->root);

    return OK;

}

/*Funcion recursiva de tree_depth, privada*/
int tree_depth_rec(NodeBT *nbt) {
    if (!nbt)return -1;

    return int_max(tree_depth_rec(nbt->left), tree_depth_rec(nbt->right)) + 1;
}

/*Calcula la profundidad del arbol*/
int tree_depth(const Tree *pa) {
    if (!pa)return -1;

    return tree_depth_rec(pa->root);
}

/*Funcion recursiva de tree_numNodes, privada*/
int tree_numNodes_rec(NodeBT *nbt) {
    if (!nbt)return 0;

    return tree_numNodes_rec(nbt->left) + tree_numNodes_rec(nbt->right) + 1;
}

/*Calcula el numero de nodos*/
int tree_numNodes(const Tree *pa) {
    if (!pa)return 0;

    return tree_numNodes_rec(pa->root);
}

/*Funcion recursiva de tree_find, privada*/
Bool tree_find_rec(Tree *pa, NodeBT *nbt, const void *pe) {
    int cmp = 0;
    if (!nbt)return FALSE;

    cmp = pa->cmp_element_function(pe, nbt->info);

    if (cmp == 0)return TRUE;

    if (cmp < 0)return tree_find_rec(pa, nbt->left, pe);

    return tree_find_rec(pa, nbt->right, pe);

}

/*Busca si hay algun nodo con la info pe en el arbol pa*/
Bool tree_find(Tree* pa, const void* pe) {
    if (!pa || !pe)return FALSE;

    return tree_find_rec(pa, pa->root, pe);
}
