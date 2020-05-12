########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p2_e2
########################################################
OBJECTSP2E2 = p2_e2.o elestack-int.o stack_elestack.o
########################################################
all: $(EJS) clear

p2_e2: $(OBJECTSP2E2)
	$(CC) $(CFLAGS) -o p2_e2 $(OBJECTSP2E2)

p2_e2.o: p2_e2.c stack_elestack.h
	$(CC) $(CFLAGS) -c p2_e2.c

elestack-int.o: elestack-int.c
	$(CC) $(CFLAGS) -c elestack-int.c

stack-elestack.o: stack_elestack.c
	$(CC) $(CFLAGS) -c stack_elestack.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p2_e2"
	./p2_e2 3

runv:
	@echo ">>>>>>Running p2_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p2_e2 3
