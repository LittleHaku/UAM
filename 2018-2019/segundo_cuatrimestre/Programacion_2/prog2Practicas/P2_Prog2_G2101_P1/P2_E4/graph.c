/****************************************************
Nombre del módulo: graph.c
Descripción: estructura del grafo de nodos y sus funciones respectivas
Autor: Junco de las Heras, Joaquin Murcia & Borja Velasco
Fecha: 25/03/19
Módulos propios que necesita: graph.h
Notas: version final a entregar
Modificaciones: -
Mejoras pendientes: -
****************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#include "graph.h"
#include "stack_fp.h"


#define MAX_NODES 4096
#define MAX_LINE 2000
#define NO_FILE_POS_VALUES 2 /*Num de argumentos de sscanf*/
#define BLANCO 0
#define NEGRO 1

extern int erno;

struct _Graph {
    int num_nodes;
    int num_edges;
    Node *nodes[MAX_NODES];
    /*un nodo i esta conectado con el nodo j si y solo si connections[i][j]=TRUE*/
    Bool connections[MAX_NODES][MAX_NODES];

};

/* --- Funciones privadas, facilitadas por el profesor */
int find_node_index(const Graph * g, int nId1) {
    int i;

    if (!g){
        return -1;
    }

    for (i = 0; i < g->num_nodes; i++) {
        if (node_getId(g->nodes[i]) == nId1) return i;
    }

    /* ID not find*/
    return -1;
}

int* graph_getConectionsIndex(const Graph * g, int index) {
    int *array = NULL, i, j = 0, size;

    if (!g){
        return NULL;
    }

    if (index < 0 || index > g->num_nodes) return NULL;
    /* get memory for the array with the connected nodes index*/
    size = node_getConnect(g->nodes[index]);
    array = (int *) malloc(sizeof (int) * size);
    if (!array) {
        /* print errorr message*/
        fprintf(stderr, "%s\n", strerror(errno));
        return NULL;
    }

    /* asigno valores al array con los indices de los nodos conectados*/
    for (i = 0; i < g->num_nodes; i++) {
        if (g->connections[index][i] == TRUE) {
            array[j] = i;
            j++;
        }
    }

    return array;
}


/* --- Funciones publicas */
/*Inicializa todo a 0, NULL O FALSE*/
Graph * graph_ini() {
    Graph *g = NULL;
    int i;

    g = (Graph*) malloc(sizeof (Graph));
    if (!g){
        fprintf(stderr,"%s\n",strerror(errno));
        return NULL;
    }

    g->num_nodes = 0;

    for(i=0;i<MAX_NODES;++i){
        g->nodes[i]=NULL;
    }

    /*Cada vez que se inserta un nuevo nodo se reseteara todo a false, optimizacion de tiempo*/
    for (i = 0; i < MAX_NODES; ++i) {
        g->connections[i][0] = FALSE;
        g->connections[0][i] = FALSE;
    }

    return g;
}

void graph_destroy(Graph * g) {
    int i;
    if (!g){
        return;
    }

    for(i=0;i<g->num_nodes;++i){
        if(g->nodes[i]!=NULL)free(g->nodes[i]);
    }

    free(g);
}

Status graph_insertNode(Graph * g, const Node* n) {
    int i, numNodos;
    /*copia el nodo porque n es const Node*/
    Node *copia = node_copy(n);
    if (!g || !n){
        return ERROR;
    }

    if (g->num_nodes == MAX_NODES){
        return ERROR;
    }

    if (!copia){
        return ERROR;
    }

    /*la funcion devuelve -1 si el nodo no esta ya en el grafo*/
    if (find_node_index(g, node_getId(n)) != -1){
        node_destroy(copia);
        return ERROR;
    }


    numNodos=graph_getNumberOfNodes(g);

    g->nodes[numNodos] = copia;
    g->num_nodes += 1;

    /*Inicializa una nueva fila y columna cada vez que se añade un nuevo nodo*/
    for (i = 0; i < MAX_NODES; ++i) {
        g->connections[i][numNodos] = FALSE;
        g->connections[numNodos][i] = FALSE;
    }

    return OK;
}

Status graph_insertEdge(Graph * g, const int nId1, const int nId2) {
    int Id1=find_node_index(g, nId1), Id2=find_node_index(g, nId2);
    if(!g){
        return ERROR;
    }

    if (Id1 < 0 || Id1 >= g->num_nodes || Id2 < 0 || Id2 >= g->num_nodes){
        return ERROR;
    }

    g->connections[Id1][Id2] = TRUE;
    /*Incrementa el numero de conexiones del nodo*/
    node_setConnect(g->nodes[Id1], node_getConnect(g->nodes[Id1]) + 1);

    g->num_edges++;

    return OK;
}

Node *graph_getNode(const Graph *g, int nId) {
    int Id=find_node_index(g, nId);
    if(!g){
        return ERROR;
    }


    if (Id < 0 || Id >= g->num_nodes){
        return ERROR;
    }

    return node_copy(g->nodes[Id]);
}

Status graph_setNode(Graph *g, const Node *n) {
    if (!g || !n){
        return ERROR;
    }

    /*Necesitas asignarle una copia al ser const Node*/
    g->nodes[node_getId(n)] = node_copy(n);

    return OK;
}

int * graph_getNodesId(const Graph * g) {
    int *ids = NULL, i;
    if (!g){
        return NULL;
    }


    ids = (int*) malloc((g->num_nodes) * sizeof (int));
    if (!ids){
        fprintf(stderr,"%s\n",strerror(errno));
        return NULL;
    }

    for (i = 0; i < g->num_nodes; ++i) {
        ids[i] = node_getId(g->nodes[i]);
    }

    return ids;
}

int graph_getNumberOfNodes(const Graph * g) {
    if (!g){
        return ERROR;
    }

    return g->num_nodes;
}

int graph_getNumberOfEdges(const Graph * g) {
    if (!g){
        return ERROR;
    }

    return g->num_edges;
}

Bool graph_areConnected(const Graph * g, const int nId1, const int nId2) {
    int Id1 = find_node_index(g, nId1), Id2=find_node_index(g, nId2);
    if(!g){
        return FALSE;
    }

    if (Id1 < 0 || Id1 >= g->num_nodes || Id2 < 0 || Id2 >= g->num_nodes){
        return FALSE;
    }

    return g->connections[Id1][Id2];
}

int graph_getNumberOfConnectionsFrom(const Graph * g, const int fromId) {
    int Id=find_node_index(g, fromId);

    if(!g){
        return 0;
    }

    if (Id < 0 || Id >= g->num_nodes){
        return 0;
    }

    return node_getConnect(g->nodes[Id]);

}

int* graph_getConnectionsFrom(const Graph * g, const int fromId) {
    int Id=find_node_index(g, fromId);
    if(!g){
        return NULL;
    }

    if (Id < 0 || Id >= g->num_nodes){
        return NULL;
    }

    return graph_getConectionsIndex(g, Id);

}

int graph_print(FILE *pf, const Graph * g) {
    int n = g->num_nodes, *array = NULL, contCaracteres = 0, i, j, numberOfEdges=0, *arrayIdDeNodos=NULL;
    Node *nodeImprimir=NULL;
    if (!pf || !g){
        return -1;
    }

    arrayIdDeNodos=graph_getNodesId(g);
    if(!arrayIdDeNodos){
        return -1;
    }

    fprintf(pf, "Grafo\n");

    for (i = 0; i < n; ++i) {
        nodeImprimir=graph_getNode(g, arrayIdDeNodos[i]);
        contCaracteres += node_print(pf, nodeImprimir);
        node_destroy(nodeImprimir);

        numberOfEdges = graph_getNumberOfConnectionsFrom(g, arrayIdDeNodos[i]);
        if (numberOfEdges == 0) {
            fprintf(pf, "\n");
            continue;
        }

        array = graph_getConnectionsFrom(g, arrayIdDeNodos[i]);
        /*Si !array es igual que si no hay conexiones, continua para hacer print al suiguiente nodo*/
        if (!array)continue;

        for (j = 0; j < numberOfEdges; ++j) {

            contCaracteres += fprintf(pf, " %d", array[j]);
        }
        contCaracteres+=fprintf(pf, "\n");
        free(array);
    }

    free(arrayIdDeNodos);

    return contCaracteres;
}

/*Hecho / Facilitado por el profesor*/
Status graph_readFromFile(FILE *fin, Graph *g) {
    Node *n;
    char buff[MAX_LINE], name[MAX_LINE];
    int i, nnodes = 0, id1, id2;
    Status flag = ERROR;

    /* read number of nodes*/
    if (fgets(buff, MAX_LINE, fin) != NULL)
        if (sscanf(buff, "%d", &nnodes) != 1) return ERROR;

    /* init buffer_node*/
    n = node_ini();
    if (!n) return ERROR;

    /* read nodes line by line*/
    for (i = 0; i < nnodes; i++) {
        if (fgets(buff, MAX_LINE, fin) != NULL)
            if (sscanf(buff, "%d %s", &id1, name) != NO_FILE_POS_VALUES) break;

        /* set node name and node id*/
        node_setName(n, name);
        node_setId(n, id1);

        /* insert node in the graph*/
        if (graph_insertNode(g, n) == ERROR) break;
    }

    /* Check if all node have been inserted*/
    if (i < nnodes) {
        node_destroy(n);
        return ERROR;
    }

    /* read connections line by line and insert it*/
    while (fgets(buff, MAX_LINE, fin)) {
        if (sscanf(buff, "%d %d", &id1, &id2) == NO_FILE_POS_VALUES)
            if (graph_insertEdge(g, id1, id2) == ERROR) break;
    }

    /* check end of file*/
    if (feof(fin)) flag = OK;

    /* clean up, free resources*/
    node_destroy(n);
    return flag;
}

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

Node *graph_findDeepSearch (Graph *g, int from_id, int to_id){
    int origenId=find_node_index(g, from_id), destinoId=find_node_index(g, to_id), i, *u = NULL;
    Stack *s=NULL;

    int numNodes=graph_getNumberOfNodes(g), *color = NULL;

    if(!g||origenId<0||origenId>=numNodes||destinoId<0||destinoId>=numNodes){
        fprintf(stderr, "ERROR EN LOS PARAMETROS DE ENTRADA DFS\n");
        return NULL;
    }

    /*Color es el array donde se guardan las etiquetas de cada nodo*/
    color = (int *)malloc(numNodes * sizeof(int));
    if(!color){
        fprintf(stderr, "%s", strerror(errno));
        return NULL;
    }

    /*Inicializo una pila de ids, que junto con la matriz de adyacencia tengo
    * toda la informacion*/
    s = stack_ini(intDestroy, intCopy, intPrint);
    if(!s){
        free(color);
        return NULL;
    }

    /*Inicializo el array*/
    for(i=0;i<numNodes;i++){
        color[i]=BLANCO;
    }

    /*Inserto el id origen*/
    if(stack_push(s, &origenId) == ERROR){
        stack_destroy(s);
        free(color);
        return NULL;
    }

    while(stack_isEmpty(s)==FALSE){
        u = stack_pop(s);

        if(color[*u] == BLANCO){
            color[*u] = NEGRO;

            for(i=0;i<numNodes;i++){
                /*Si no es un vecino continua el bucle*/
                if(g->connections[*u][i] == FALSE)continue;

		/*Si es vecino y es el id buscado acaba, devolviendo el nodo*/
                if(i==destinoId){
                    free(color);
                    stack_destroy(s);
                    intDestroy(u);

                    return graph_getNode(g, to_id);
                }

		/*No es el nodo buscado, si no se ha visitado (Etiqueta BLANCA)
		* se mete en la pila */
                if(color[i] == BLANCO){
                    if(stack_push(s, &i) == ERROR){
                        stack_destroy(s);
                        free(color);
                        return NULL;
                    }
                }
            }


        }

        intDestroy(u);
        u = NULL;

    }


    stack_destroy(s);
    free(color);

    return NULL;
}

/*Funcion privada recursiva, no comprueba parametros porque son correctos siempre
* Devuelve TRUE si ya se ha encontrado el nodo a buscar, lo ha impreso y se están mostrando
* el camino (Acaba con todas las llamadas)*/
Bool camino_recursivo(FILE *f, Graph *g, int u, int destinoId, int *color){
    int i, numNodos = graph_getNumberOfNodes(g);

    /*Si ya se ha visitado volver*/
    if(color[u] == NEGRO)return FALSE;
    color[u]=NEGRO;

    for(i=0;i<numNodos;i++){
        /*Mira conexiones de i a u porque estamos yendo en sentido contrario, a ver si existen
         conexiones de un i al nodo u en el que estamos*/
        if(g->connections[i][u] == FALSE)continue;

	/*Si es el destino lo imprime y acaba las llamadas*/
        if(i==destinoId){
            node_print(f, g->nodes[i]);
            return TRUE;
        }

	/*Recursion, si encuentra alguna de esas llamadas al id entonces la funcion devuelve TRUE para
        * acabar*/
        if(camino_recursivo(f, g, i, destinoId, color) == TRUE){
            node_print(f, g->nodes[i]);
            return TRUE;
        }
    }

    return FALSE;
}

void graph_printCaminoDFS (FILE *f, Graph *g, int from_id, int to_id){
    int origenId = find_node_index(g, from_id), destinoId = find_node_index(g, to_id);
    int numNodos = graph_getNumberOfNodes(g), *color = NULL, i;
    Node *n = NULL;

    if(!f || !g)return;
    if(origenId<0||origenId>=numNodos||destinoId<0||destinoId>=numNodos)return;

    /*Comprueba si hay conectividad (si no lo hay n sera NULL y la funcion acabara)*/
    n=graph_findDeepSearch(g, from_id, to_id);
    if(!n){
        fprintf(f, "NO HAY CONECTIVIDAD");
        return;
    }

    node_destroy(n);

    color=(int *)malloc(numNodos*sizeof(int));
    if(!color){
        fprintf(stderr, "%s", strerror(errno));
        return;
    }

    /*Inicializa el array de visitados*/
    for(i=0;i<numNodos;i++){
        color[i]=BLANCO;
    }

    /*Para mostrar el camino de izq a derecha correctamente lo que hace es un DFS del destino hasta el origen.
     Como es un grafo dirigido, en vez de mirar la conectividad con connect[i][j] buscara connect[j][i]*/
    camino_recursivo(f, g, destinoId, origenId, color);
    node_print(f, g->nodes[destinoId]);

    free(color);

    return;
}
