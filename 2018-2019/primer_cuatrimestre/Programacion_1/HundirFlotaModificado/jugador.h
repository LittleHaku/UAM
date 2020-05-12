#ifndef JUGADOR_H
#define JUGADOR_H
#define MAX_STR 256
typedef struct {
	char *nombre;
	Tablero *tablero;
} Jugador;
st char *nombre, int numFilas, int numColumnas);

void liberar_jugador(Jugador *jugador);

#endif /* JUGADOR_H */


Jugador *crear_jugador(con