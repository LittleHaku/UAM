########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p3_testqueuel
########################################################
OBJECTSP3E1 = p3_testqueuel.o node.o queuel.o list.o
########################################################
all: $(EJS) clear

p3_testqueuel: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p3_testqueuel $(OBJECTSP3E1)

p3_testqueuel.o: p3_testqueuel.c node.h queuel.h
	$(CC) $(CFLAGS) -c p3_testqueuel.c

node.o: node.c  node.h
	$(CC) $(CFLAGS) -c node.c

list.o: list.c list.h
	$(CC) $(CFLAGS) -c list.c

queuel.o: queuel.c queuel.h
	$(CC) $(CFLAGS) -c queuel.c 

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p3_e4"
	./p3_testqueuel nodos.txt

runv:
	@echo ">>>>>>Running p3_e4 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p3_testqueuel nodos.txt


