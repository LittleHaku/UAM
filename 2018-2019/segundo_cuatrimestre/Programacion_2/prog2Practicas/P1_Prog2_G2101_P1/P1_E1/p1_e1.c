/* 
 * File:   main.c
 * Autores: Junco de las Heras & Joaquín Murcia
 *
 * Created on 5 de febrero de 2019, 13:33
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "types.h"
#include "node.h"


int main(int argc, char** argv) {
    Node *n1, *n2;
    n1= node_ini();
    n2= node_ini();
    
    node_setName(n1, "first");
    node_setId(n1, 111);
    
    node_setName(n2, "second");
    node_setId(n2, 222);
    
    node_print(stdout, n1);
    node_print(stdout, n2);
    fprintf(stdout,"\n");
    
    fprintf(stdout,"Son iguales? %s\n", node_cmp(n1,n2)?"NO":"SI");
    
    fprintf(stdout,"Id del primer nodo: %d\n",node_getId(n1));
    fprintf(stdout,"Nombre del segundo nodo: %s\n",node_getName(n2));
    
    n2=node_copy(n1);
    
    node_print(stdout, n1);
    node_print(stdout, n2);
    fprintf(stdout,"\n");
    
    fprintf(stdout,"Son iguales? %s\n", node_cmp(n1,n2)?"NO":"SI");    
    
    
    return (EXIT_SUCCESS);
}

