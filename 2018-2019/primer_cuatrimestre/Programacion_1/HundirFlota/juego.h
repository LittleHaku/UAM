#ifndef JUEGO_H
#define JUEGO_H

#define NUMERO_PORTAVIONES 1
#define NUMERO_ACORAZADO 3
#define NUMERO_DESTRUCTOR 3
#define NUMERO_FRAGATA 2

#define LONGITUD_PORTAVIONES 4
#define LONGITUD_ACORAZADO 3
#define LONGITUD_DESTRUCTOR 2
#define LONGITUD_FRAGATA 1

#define TIPO_PORTAVIONES "Portaviones"
#define TIPO_ACORAZADO "Acorazado"
#define TIPO_SUBMARINO "Submarino"
#define TIPO_DESTRUCTOR "Destructor"
#define TIPO_FRAGATA "Fragata"

typedef enum {FALSE, TRUE} boolean;

void solicitar_barco(Jugador *jugador, int tipoBarco);

void realizar_disparo(Jugador *jugador, int fila, int columna);

boolean comprobar_derrota(Jugador *jugador);


#endif /* JUEGO_H */

