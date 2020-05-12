/* 
 * File:   p1_e2.c
 * Autores: Junco de las Heras & Joaquín Murcia
 *
 * Created on 5 de febrero de 2019, 14:43
 */

#include <stdio.h>
#include <stdlib.h>
#include "types.h"
#include "graph.h"
#include "node.h"


int main(int argc, char** argv) {
    
    Node *n1 = NULL, *n2 = NULL;
    
    Graph *g = NULL;

    n1 = node_ini();
    if (!n1) {
        fprintf(stdout, "ERROR");
        return 1;
    }

    n2 = node_ini();
    if (!n2) {
        fprintf(stdout, "ERROR");
        node_destroy(n1);
        return 1;
    }

    node_setId(n1, 111);
    node_setName(n1, "first");

    node_setId(n2, 222);
    node_setName(n2, "second");

    g = graph_ini();
    if (!g) {
        fprintf(stdout, "ERROR");
        node_destroy(n1);
        node_destroy(n2);
        return 1;
    }

    fprintf(stdout, "Insertando nodo 1...resultado...:%d\n", graph_insertNode(g, n1));
    fprintf(stdout, "Insertando nodo 2...resultado...:%d\n", graph_insertNode(g, n2));

    fprintf(stdout, "Insertando edge: nodo 2 ---> nodo 1\n");
    graph_insertEdge(g, node_getId(n2), node_getId(n1));
    
    fprintf(stdout, "Conectados nodo 1 y nodo 2? %s\n", graph_areConnected(g, node_getId(n1), node_getId(n2)) ? "SI" : "NO");
    fprintf(stdout, "Conectados nodo 2 y nodo 1? %s\n", graph_areConnected(g, node_getId(n2), node_getId(n1)) ? "SI" : "NO");

    fprintf(stdout, "Insertando nodo 2...resultado...:%d\n", graph_insertNode(g, n2));

    graph_print(stdout, g);

    
    
    
    graph_destroy(g);
    node_destroy(n1);
    node_destroy(n2);

    
    return (EXIT_SUCCESS);
}