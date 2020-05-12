########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p3_testqueue
########################################################
OBJECTSP3E1 = p3_testqueue.o node.o queue.o
########################################################
all: $(EJS) clear

p3_testqueue: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p3_testqueue $(OBJECTSP3E1)

p3_testqueue.o: p3_testqueue.c node.h queue.h
	$(CC) $(CFLAGS) -c p3_testqueue.c

node.o: node.c node.h
	$(CC) $(CFLAGS) -c node.c

queue.o: queue.c queue.h
	$(CC) $(CFLAGS) -c queue.c 

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p3_e1"
	./p3_testqueue nodos.txt

runv:
	@echo ">>>>>>Running p3_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p3_testqueue nodos.txt


