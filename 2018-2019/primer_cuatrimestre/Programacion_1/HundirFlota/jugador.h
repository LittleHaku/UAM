#ifndef JUGADOR_H
#define JUGADOR_H
#define MAX_STR 256
typedef struct {
	char *nombre;
	Tablero *tablero;
} Jugador;

Jugador *crear_jugador(const char *nombre, int numFilas, int numColumnas);

void liberar_jugador(Jugador *jugador);

#endif /* JUGADOR_H */

