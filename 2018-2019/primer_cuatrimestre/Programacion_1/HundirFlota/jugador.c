#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "tablero.h"
#include "jugador.h"
#include "juego.h"


Jugador *crear_jugador(const char *nombre, int numFilas, int numColumnas){
    Jugador *jugador=NULL;
    jugador=(Jugador*)calloc(1, sizeof(Jugador));
    if(!jugador){
        return NULL;
    }
    jugador->nombre=(char*)calloc(MAX_STR,sizeof(char));
    if(!jugador->nombre){
        return NULL;
    }
    strcpy(jugador->nombre, nombre);

    jugador->tablero=crear_tablero(numFilas, numColumnas);
    if(!jugador->tablero){
        printf("FALLO AL CREAR TABLERO");
        free(jugador);
        return NULL;
    }
    solicitar_barco(jugador, 0);
    
    return jugador;
}

void liberar_jugador(Jugador *jugador){
    liberar_tablero(jugador->tablero);
    free(jugador);
}