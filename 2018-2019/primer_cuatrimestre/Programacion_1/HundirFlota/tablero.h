#ifndef TABLERO_H
#define TABLERO_H

#define FILAS_MIN 10
#define FILAS_MAX 20
#define COLUMNAS_MIN 10
#define COLUMNAS_MAX 20

typedef enum {AGUA, NO_TOCADO, TOCADO, HUNDIDO, FALLO} Casilla;

typedef struct {
	Casilla **casillas;
	int numeroFilas;
	int numeroColumnas;
} Tablero;

Tablero *crear_tablero(int numFilas, int numColumnas);

void liberar_tablero(Tablero *tablero);

void visualizar_tablero(Tablero *tablero, int soloDisparos);



#endif /* TABLERO_H */

