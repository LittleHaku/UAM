/****************************************************
Nombre del módulo: node.c
Descripción: estructura del nodo y sus funciones respectivas
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: node.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "types.h"
#include "node.h"

#define MAX_NAME 128

extern int errno;

struct _Node {
    char name[MAX_NAME];
    int id;
    int nConnect;
    
    int antecesor;
};

 Node * node_ini(){
     Node *n;
     n=(Node *)malloc(sizeof(Node));
     if(!n){
         fprintf(stderr, "%s", strerror(errno));
         return NULL;
     }

     node_setName(n, "");
     node_setId(n, -1);
     node_setConnect(n, 0);
     node_setAntecesor(n, -1);


     return n;
 }

 void node_destroy(void * n){
     if(!n)return;

     free((Node *)n);

     return;
 }

 int node_getId(const Node * n){
     if(!n)return ERROR;

     return (n->id);
 }


 char* node_getName(const Node * n){
     if(!n)return NULL;

     return (char*)n->name;
 }

 int node_getConnect(const Node * n){
     if(!n)return ERROR;

     return n->nConnect;
 }
 
int node_getAntecesor(const Node * n){
    if(!n)return -1;
    
    return n->antecesor;
 }

 Node * node_setId(Node * n, const int id){
     if(!n)return NULL;

     n->id=id;

     return n;
 }


 Node * node_setName(Node * n, const char* name){
     if(!n)return NULL;

     strncpy(n->name,name,MAX_NAME-1);

     return n;
 }
 
 Node * node_setAntecesor(Node * n, int antecesor){
     if(!n)return NULL;
     
     n->antecesor = antecesor;
     
     return n;
 }
 
 Node * node_setConnect(Node * n, const int cn){
     if(!n)return NULL;

     n->nConnect=cn;

     return n;
 }

 int node_cmp (const Node * n1, const Node * n2){
     if(!n1||!n2)return -2;

     if(node_getId(n1)==node_getId(n2))return 0;

     if(node_getId(n1)<node_getId(n2))return -1;

     return 1;
 }

 void * node_copy(const void * src){
     Node *n = NULL;

     if(!src) return NULL;

     n = node_ini();
     if(!n) {
         fprintf(stderr, "%s\n", strerror(errno));
         return NULL;
     }

     node_setId(n,node_getId((Node*)src));
     node_setName(n,node_getName((Node*)src));
     node_setConnect(n,node_getConnect((Node*)src));

     return n;
 }

 int node_print(FILE *pf, const void * n){
     int cont = 0;

     if(!pf||!n)return ERROR;

     cont = fprintf(pf,"[%d, %s, %d]",node_getId((Node *)n),node_getName((Node *)n),node_getConnect((Node *)n));
     if (ferror(pf)) {
 		fprintf(stderr, "Error escribiendo el archivo\n");
 		return ERROR;
 	 }

     return cont;
 }
