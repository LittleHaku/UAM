SRC = ./src
OBJ = ./obj
LIB = ./lib
SRCLIB = ./srclib
INCLUD = ./includes

CC = gcc
CFLAGS = -Wextra -Wall -Iincludes -g

OBJETOS = $(OBJ)/server.o $(OBJ)/procesa_conexion.o $(LIB)/conexion.a $(LIB)/picohttpparser.a

all: server

server: $(OBJETOS)
	$(CC) $(CFLAGS) -o server $(OBJETOS) -lconfuse

$(OBJ)/server.o: $(SRC)/server.c 
	$(CC) $(CFLAGS) -o $@ -c $(SRC)/server.c

$(OBJ)/conexion.o: $(SRCLIB)/conexion.c $(INCLUD)/conexion.h
	$(CC) $(CFLAGS) -o $@ -c $(SRCLIB)/conexion.c 

$(LIB)/conexion.a: $(OBJ)/conexion.o 
	ar -rcs $(LIB)/conexion.a $(OBJ)/conexion.o

$(OBJ)/picohttpparser.o: $(SRCLIB)/picohttpparser.c $(INCLUD)/picohttpparser.h
	$(CC) $(CFLAGS) -o $@ -c $(SRCLIB)/picohttpparser.c 

$(LIB)/picohttpparser.a: $(OBJ)/picohttpparser.o
	ar -rcs $(LIB)/picohttpparser.a $(OBJ)/picohttpparser.o

$(OBJ)/procesa_conexion.o: $(SRC)/procesa_conexion.c $(INCLUD)/procesa_conexion.h
	$(CC) $(CFLAGS) -o $@ -c $(SRC)/procesa_conexion.c

run:
	@echo "Ejecutando el server ..."
	@./server

runv: 
	@echo "Ejecutando el server con Valgrind ..."
	@valgrind --tool=memcheck --leak-check=full --show-leak-kinds=all ./server

clear:
	rm -rf *.o *.a obj/*.o lib/*.a

clean:
	rm -f server

