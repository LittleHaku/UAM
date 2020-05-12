########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic 
EJS = p2_e1
########################################################
OBJECTSP2E1 = p2_e1.o node.o elestack-node.o stack_elestack.o
########################################################
all: $(EJS) clear

p2_e1: $(OBJECTSP2E1)
	$(CC) $(CFLAGS) -o p2_e1 $(OBJECTSP2E1)

p2_e1.o: p2_e1.c stack_elestack.h
	$(CC) $(CFLAGS) -c p2_e1.c

node.o: node.c node.h
	$(CC) $(CFLAGS) -c node.c

elestack-node.o: elestack-node.c
	$(CC) $(CFLAGS) -c elestack-node.c

stack-elestack.o: stack_elestack.c
	$(CC) $(CFLAGS) -c stack_elestack.c

clear: 
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p2_e1"
	./p2_e1

runv:
	@echo ">>>>>>Running p2_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p2_e1
