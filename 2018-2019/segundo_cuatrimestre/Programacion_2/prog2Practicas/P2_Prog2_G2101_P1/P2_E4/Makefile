########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p2_e4
########################################################
OBJECTSP2E4 = p2_e4.o node.o stack_fp.o graph.o
########################################################
all: $(EJS) clear

p2_e4: $(OBJECTSP2E4)
	$(CC) $(CFLAGS) -o p2_e4 $(OBJECTSP2E4)

p2_e4.o: p2_e4.c stack_fp.h graph.h
	$(CC) $(CFLAGS) -c p2_e4.c

node.o: node.c 
	$(CC) $(CFLAGS) -c node.c

stack_fp.o: stack_fp.c
	$(CC) $(CFLAGS) -c stack_fp.c

graph.o: graph.c node.h
	$(CC) $(CFLAGS) -c graph.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p2_e4"
	./p2_e4 g2_s.txt 1 45

runv:
	@echo ">>>>>>Running p2_e4 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p2_e4 g2_s.txt 1 45
