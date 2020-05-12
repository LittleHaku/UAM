########################################################
CC=gcc
CFLAGS=-g -Wall -pedantic -ansi
AFLAGS=-g -Wall -pedantic
###-ansi no se recomienda usar al enlazar archivos (aparecen warnings)
EJS=p1_e2 p1_e2files
###DEPS=graph.h node.h

########################################################
OBJ1=p1_e2.o graph.o node.o
OBJ2=p1_e2files.o graph.o node.o
########################################################
##%.o: %.c $(DEPS)
##	$(CC) -c -o $@ $< $(CFLAGS)
all: $(EJS)
p1_e2: $(OBJ1)
	$(CC) $(CFLAGS) -o p1_e2 $(OBJ1)
p1_e2files: $(OBJ2)
	$(CC) $(CFLAGS) -o p1_e2files $(OBJ2)
##p1_e2.o: graph.o node.o
##	$(CC) $(CFLAGS) -c p1_e2.c -o p1_e2.o
##node.o: node.c node.h
##	$(CC) $(CFLAGS) -c node.c -o node.o
##graph.o: graph.c node.c
##	$(CC) $(CFLAGS) -c graph.c -o graph.o

### ... otras tareas necesarias para compilar/linkar el resto de ficheros
clear:
	rm -rf *.o 
