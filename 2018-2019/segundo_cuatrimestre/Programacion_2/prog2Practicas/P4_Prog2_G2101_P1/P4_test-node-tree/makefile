########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic -ansi
EJS = p4_test-node-tree 
########################################################
OBJECTSP4_TNT = p4_test-node-tree.o tree.o node.o
########################################################

all: $(EJS) clear

p4_test-node-tree: $(OBJECTSP4_TNT)
	$(CC) $(CFLAGS) -o p4_test-node-tree $(OBJECTSP4_TNT)

p4_test-node-tree.o: p4_test-node-tree.c tree.h node.h
	$(CC) $(CFLAGS) -c p4_test-node-tree.c

tree.o: tree.c tree.h
	$(CC) $(CFLAGS) -c tree.c

node.o: node.c node.h
	$(CC) $(CFLAGS) -c node.c

clear:
	rm -rf *.o 

clean:
	rm -rf *.o $(EJS)

run:
	@echo ">>>>>>Running p4_test-node-tree"
	./p4_test-node-tree nodos.txt

runv:
	@echo ">>>>>>Running p4_test-node-tree with valgrind"
	valgrind --leak-check=full ./p4_test-node-tree nodos.txt


