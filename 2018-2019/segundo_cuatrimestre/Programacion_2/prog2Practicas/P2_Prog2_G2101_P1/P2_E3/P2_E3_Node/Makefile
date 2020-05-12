########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p2_e3_node
########################################################
OBJECTSP2E3NODE = p2_e3_node.o stack_fp.o node.o
########################################################
all: $(EJS) clear

p2_e3_node: $(OBJECTSP2E3NODE)
	$(CC) $(CFLAGS) -o p2_e3_node $(OBJECTSP2E3NODE)

p2_e3_node.o: p2_e3_node.c stack_fp.h
	$(CC) $(CFLAGS) -c p2_e3_node.c

stack_fp.o: stack_fp.c 
	$(CC) $(CFLAGS) -c stack_fp.c

node.o: node.c 
	$(CC) $(CFLAGS) -c node.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p2_e3_node"
	./p2_e3_node

runv:
	@echo ">>>>>>Running p2_e3_node with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p2_e3_node
