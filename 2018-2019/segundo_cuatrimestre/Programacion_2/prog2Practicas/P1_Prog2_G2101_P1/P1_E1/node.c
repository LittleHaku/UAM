/* 
 * File:   node.c
 * Autores: Junco de las Heras & Joaquín Murcia
 *
 * Created on 5 de febrero de 2019, 13:33
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "types.h"
#include "node.h"

struct _Node {
 char name[100];
 int id;
 int nConnect; };
 
 Node * node_ini(){
     Node *n;
     n=(Node *)malloc(sizeof(Node));
     if(n==NULL){
         fprintf(stdout,"FALLO DE RESERVA DE MEMORIA\n");
     }
     node_setName(n, "");
     node_setId(n, -1);
     node_setConnect(n, 0);
     return n;     
 }
 
 void node_destroy(Node * n){
     if(n==NULL)return;
     free(n);
 }
 
 int node_getId(const Node * n){
     if(n==NULL)return -1;
     return (n->id);
 }
 
 
 char* node_getName(const Node * n){
     if(n==NULL)return NULL;
     return (char*)n->name;
 }

 int node_getConnect(const Node * n){
     if(n==NULL)return -1;
     return n->nConnect;
 }
 
 Node * node_setId(Node * n, const int id){
     if(n==NULL)return NULL;
     n->id=id;
     return n;
 }
 
 
 Node * node_setName(Node * n, const char* name){
     if(n==NULL)return NULL;
     strcpy(n->name,name);
     return n;
 }
 
 Node * node_setConnect(Node * n, const int cn){
     if(n==NULL)return NULL;
     n->nConnect=cn;
     return n;
 }
 
 int node_cmp (const Node * n1, const Node * n2){
     if(!n1||!n2)return -2;
     
     if(node_getId(n1)==node_getId(n2))return 0;
     
     if(node_getId(n1)<node_getId(n2))return -1;
     
     return 1;
 }
 
 Node * node_copy(const Node * src){
     Node *n;
     n=node_ini();
     
     node_setId(n,node_getId(src));
     node_setName(n,node_getName(src));
     node_setConnect(n,node_getConnect(src));
     
     return n;
 }
 
 int node_print(FILE *pf, const Node * n){
     if(!pf||!n){
         fprintf(pf,"ERROR EN LOS PARAMETROS DE ENTRADA\n");
         return 0;
     }
     return fprintf(pf,"[%d, %s, %d]",node_getId(n),node_getName(n),node_getConnect(n));
 }