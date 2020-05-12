#include <stdio.h>
#include <stdlib.h>
#include "tablero.h"
#include "jugador.h"
#include "juego.h"

void ecribir_barco(int NumBarco, int LongBarco, char *NombreBarco, Jugador *jugador){
    int i, j, col, correcto;
    char f, dir;
    
    for (i = 0; i < NumBarco; i++) {
        correcto = 1;
        do {
            printf("%s - Colocación de Portaviones %d de %d (longitud = %d)\n", jugador->nombre, i + 1, NumBarco, LongBarco);
            printf("Selecciona una fila: ");
            scanf("\n%c", &f);
            printf("Selecciona una columna: ");
            scanf("%d", &col);
            printf("Introduce dirección vertical/horizontal (V/H): ");
            scanf("\n%c", &dir);
            if (dir == 'H') {
                for (j = col - 1; j < col - 1 + LongBarco; j++) {
                    if (j >= jugador->tablero->numeroColumnas || jugador->tablero->casillas[f - 'A'][j]) {
                        correcto = 0;
                        break;
                    }
                }
            }
            if (dir == 'V') {
                for (j = f - 'A'; j < f - 'A' + LongBarco && j < jugador->tablero->numeroFilas; j++) {
                    if (j >= jugador->tablero->numeroColumnas || jugador->tablero->casillas[j][col - 1]) {
                        correcto = 0;
                        break;
                    }
                }
            }
        } while (!correcto);
        if (dir == 'H') {
            for (j = col - 1; j < col - 1 + LongBarco && j < jugador->tablero->numeroColumnas; j++) {
                jugador->tablero->casillas[f - 'A'][j] = NO_TOCADO;
            }
        }
        if (dir == 'V') {
            for (j = f - 'A'; j < f - 'A' + LongBarco && j < jugador->tablero->numeroColumnas; j++) {
                jugador->tablero->casillas[j][col - 1] = NO_TOCADO;
            }
        }
        printf("%s introducido\n", NombreBarco);
        visualizar_tablero(jugador->tablero, 0);
    }
}

void solicitar_barco(Jugador *jugador, int tipoBarco) {
    
    ecribir_barco(NUMERO_PORTAVIONES, LONGITUD_PORTAVIONES, TIPO_PORTAVIONES, jugador);
    ecribir_barco(NUMERO_ACORAZADO, LONGITUD_ACORAZADO, TIPO_ACORAZADO, jugador);
    ecribir_barco(NUMERO_DESTRUCTOR, LONGITUD_DESTRUCTOR, TIPO_DESTRUCTOR, jugador);
    ecribir_barco(NUMERO_FRAGATA, LONGITUD_FRAGATA, TIPO_FRAGATA, jugador);
    

}

boolean comprobar_derrota(Jugador *jugador) {
    int i, j;
    for (i = 0; i < jugador->tablero->numeroFilas; i++) {
        for (j = 0; j < jugador->tablero->numeroColumnas; j++) {
            if (jugador->tablero->casillas[i][j] == 1) {
                return 0;
            }
        }
    }
    return 1;
}

void realizar_disparo(Jugador *jugador, int fila, int columna) {
    int flag1, flag2, i;
    if (jugador->tablero->casillas[fila - 1][columna - 1] == 0) {
        jugador->tablero->casillas[fila - 1][columna - 1] = 4;
    } else if (jugador->tablero->casillas[fila - 1][columna - 1] == 1) {
        jugador->tablero->casillas[fila - 1][columna - 1] = 2;
        flag1 = 0;
        for (i = fila; i < jugador->tablero->numeroFilas; i++) {
            if (jugador->tablero->casillas[i][columna - 1] == 1) {
                flag1 = 1;
                break;
            }
            if (jugador->tablero->casillas[i][columna - 1] != 2) {
                break;
            }
        }
        for (i = fila - 2; i >= 0; i--) {
            if (jugador->tablero->casillas[i][columna - 1] == 1) {
                flag1 = 1;
                break;
            }
            if (jugador->tablero->casillas[i][columna - 1] != 2) {
                break;
            }
        }
        
        flag2 = 0;
        for (i = columna; i < jugador->tablero->numeroColumnas; i++) {
            if (jugador->tablero->casillas[fila - 1][i] == 1) {
                flag2 = 1;
                break;
            }
            if (jugador->tablero->casillas[fila - 1][i] != 2) {
                break;
            }

        }
        for (i = columna - 2; i >= 0; i--) {
            if (jugador->tablero->casillas[fila - 1][i] == 1) {
                flag2 = 1;
                break;
            }
            if (jugador->tablero->casillas[fila - 1][i] != 2) {
                break;
            }
        }
        if (!flag1&&!flag2) {
             /*ELIMINA EL BARCO VERTICAL*/
            printf("BARCO HUNDIDO\n");
            jugador->tablero->casillas[fila - 1][columna - 1] = 3;
            for (i = fila; i < jugador->tablero->numeroFilas; i++) {
                if (jugador->tablero->casillas[i][columna - 1] != 2) {
                    break;
                }
                jugador->tablero->casillas[i][columna - 1] = 3;
            }
            for (i = fila - 2; i >= 0; i--) {
                if (jugador->tablero->casillas[i][columna - 1] != 2) {
                    break;
                }
                jugador->tablero->casillas[i][columna - 1] = 3;
            }

            /*ELIMINA EL BARCO HORIZONTAL*/
            //jugador->tablero->casillas[fila - 1][columna - 1] = 3;
            for (i = columna; i < jugador->tablero->numeroFilas; i++) {
                if (jugador->tablero->casillas[fila - 1][i] != 2) {
                    break;
                }
                jugador->tablero->casillas[fila - 1][i] = 3;
            }
            for (i = columna - 2; i >= 0; i--) {
                if (jugador->tablero->casillas[fila - 1][i] != 2) {
                    break;
                }
                jugador->tablero->casillas[fila - 1][i] = 3;
            }
        }




    }

}
