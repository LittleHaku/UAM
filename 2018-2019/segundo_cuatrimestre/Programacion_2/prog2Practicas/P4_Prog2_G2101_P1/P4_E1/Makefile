########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p4_e1
########################################################
OBJECTSP3E1 = p4_e1.o tree.o
########################################################
all: $(EJS) clear

p4_e1: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p4_e1 $(OBJECTSP3E1)

p4_e1.o: p4_e1.c tree.h
	$(CC) $(CFLAGS) -c p4_e1.c

tree.o: tree.c  tree.h
	$(CC) $(CFLAGS) -c tree.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p4_e1"
	./p4_e1 numeros.txt

runv:
	@echo ">>>>>>Running p4_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p4_e1 numeros.txt


