#include <stdio.h>
#include <stdlib.h>
#include "tablero.h"
#include "jugador.h"
#include "juego.h"

int main(void) {
    Jugador *jug1=NULL, *jug2=NULL;
    int dimensionx, dimensiony, disparocolumna, temp;
    char disparofila, j1[MAX_STR], j2[MAX_STR];
    
    printf("NOMBRE DEL JUGADOR 1: ");
    scanf("%s", j1);
    printf("NOMBRE DEL JUGADOR 2: ");
    scanf("%s", j2);
    
    printf("NUMERO DE FILAS: ");
    scanf("%d", &dimensionx);
    printf("NUMERO DE COLUMNAS: ");
    scanf("%d", &dimensiony);
    
    jug1=crear_jugador(j1, dimensionx, dimensiony);
    if(!jug1){
        printf("FALLO AL CREAR JUGADOR");
        return 1;
    }

    jug2=crear_jugador(j2, dimensionx, dimensiony);
    if(!jug2){
        printf("FALLO AL CREAR JUGADOR");
        liberar_jugador(jug1);
        return 1;
    }
    
    while(1){
        printf("Turno: %s\n", jug1->nombre);
        visualizar_tablero(jug2->tablero, 1);
        printf("Disparo fila: ");
        scanf("\n%c", &disparofila);
        temp=disparofila-'A'+1;
        printf("Disparo columna: ");
        scanf("%d", &disparocolumna);
        realizar_disparo(jug2, temp, disparocolumna);
        visualizar_tablero(jug2->tablero, 1);
        if(comprobar_derrota(jug2)){
            printf("GANA %s", jug1->nombre);
            break;
        }
        
        printf("Turno: %s\n", jug2->nombre);
        visualizar_tablero(jug1->tablero, 1);
        printf("Disparo fila: ");
        scanf("\n%c", &disparofila);
        temp=disparofila-'A'+1;
        printf("Disparo columna: ");
        scanf("%d", &disparocolumna);
        realizar_disparo(jug1, temp, disparocolumna);
        visualizar_tablero(jug1->tablero, 1);
        if(comprobar_derrota(jug1)){
            printf("GANA %s", jug2->nombre);
            break;
        }
    }


    liberar_jugador(jug1);
    liberar_jugador(jug2);
    
    return 0;
}
/*
 
 EN JUEGO.c tengo un return para poner solo 1 barco
 */
