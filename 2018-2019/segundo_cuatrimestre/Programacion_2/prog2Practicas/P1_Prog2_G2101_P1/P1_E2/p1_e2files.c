/* 
 * File:   main.c
 * Autores: Junco de las Heras & Joaquín Murcia
 *
 * Created on February 18, 2019, 6:10 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include "types.h"
#include "graph.h"
#include "node.h"
/*
 * 
 */
int main(int argc, char** argv) {
    FILE *f=NULL;
    Graph *g = NULL;

    
    f=fopen(argv[1],"r");
    if(!f){
        fprintf(stdout, "ERROR AL ABRIR EL FICHERO");
        return -1;
    }
    
    
    g=graph_ini();
    if(!g){
        fprintf(stdout, "ERROR");
        fclose(f);
        return -1;
    }
    
    graph_readFromFile(f, g);
    if(!g){
        fprintf(stdout, "ERROR AL CARGAR");
        return -1;
    }
    
    graph_print(stdout, g);
    
    
    
    
    fclose(f);
    graph_destroy(g);
    return EXIT_SUCCESS;
    return (EXIT_SUCCESS);
}

