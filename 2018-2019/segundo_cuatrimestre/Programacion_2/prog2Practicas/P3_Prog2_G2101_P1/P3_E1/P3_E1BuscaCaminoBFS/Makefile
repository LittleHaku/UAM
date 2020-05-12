########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p3_e1Caminos
########################################################
OBJECTSP3E1 = p3_e1Caminos.o node.o queue.o stack_fp.o graph.o
########################################################
all: $(EJS) clear

p3_e1Caminos: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p3_e1Caminos $(OBJECTSP3E1)

p3_e1Caminos.o: p3_e1Caminos.c graph.h
	$(CC) $(CFLAGS) -c p3_e1Caminos.c

node.o: node.c  node.h
	$(CC) $(CFLAGS) -c node.c

stack_fp.o: stack_fp.c stack_fp.h
	$(CC) $(CFLAGS) -c stack_fp.c

graph.o: graph.c node.h queue.h graph.h
	$(CC) $(CFLAGS) -c graph.c

queue.o: queue.c queue.h
	$(CC) $(CFLAGS) -c queue.c 

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p3_e1"
	./p3_e1Caminos g2_s.txt 1 45

runv:
	@echo ">>>>>>Running p3_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p3_e1Caminos g2_s.txt 1 45
