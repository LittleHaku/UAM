#include <stdio.h>
#include <stdlib.h>
#include "tablero.h"
#include "jugador.h"
#include "juego.h"


Tablero *crear_tablero(int numFilas, int numColumnas) {
    Tablero *tablero = NULL;
    int i;
    if (numFilas > FILAS_MAX || numFilas < FILAS_MIN || numColumnas > COLUMNAS_MAX || numColumnas < COLUMNAS_MIN) {
        printf("LAS DIMENSIONES NO SON CORRECTAS, EL TABLERO DEBE DE ESTAR ENTRE %dX%d Y %dX%d\n", FILAS_MIN, COLUMNAS_MIN, FILAS_MAX, COLUMNAS_MAX);
        return NULL;
    }

    tablero = (Tablero*) calloc(1, sizeof (Tablero));
    if (!tablero) {
        return NULL;
    }
    tablero->casillas = (Casilla**) calloc(numFilas, sizeof (Casilla*));
    if (!tablero->casillas) {
        free(tablero);
        return NULL;
    }
    tablero->numeroFilas = 0;
    for (i = 0; i < numFilas; i++) {
        tablero->casillas[i] = (Casilla*) calloc(numColumnas, sizeof (Casilla));
        if (!tablero->casillas[i]) {
            liberar_tablero(tablero);
            return NULL;
        }
        tablero->numeroFilas++;
    }
    tablero->numeroColumnas = numColumnas;
    /*rellenar datos del tablero aqui, ahora es un tablero vacio*/
    return tablero;

}

void liberar_tablero(Tablero *tablero) {
    int i;
    for (i = 0; i < tablero->numeroFilas; i++) {
        free(tablero->casillas[i]);
    }
    free(tablero->casillas);
    free(tablero);

}

void visualizar_tablero(Tablero *tablero, int soloDisparos) {
    int i, j, k;
    printf("  ");
    for (i = 0; i < tablero->numeroColumnas; i++) {
        printf(" %2d ", i + 1);
    }
    printf("\n");
    for (i = 0; i < tablero->numeroFilas; i++) {
        printf("%c |", i + 'A');
        for (j = 0; j < tablero->numeroColumnas; j++) {
            if(tablero->casillas[i][j]==0){
                printf("   |");
            }
            else if(tablero->casillas[i][j]==1 && !soloDisparos){
                printf(" @ |");
            }
            else if(tablero->casillas[i][j]==1){
                printf("  |");
            }
            else if(tablero->casillas[i][j]==2){
                printf(" O |");
            }
            else if(tablero->casillas[i][j]==3){
                printf(" # |");
            }
            else if(tablero->casillas[i][j]==4){
                printf(" X |");
            }

        }
        printf("\n");
        for (k = 0; k < 4 * tablero->numeroColumnas; k++) {
            printf("-");
        }
        printf("---\n");
    }
}