########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p2_e3_int
########################################################
OBJECTSP2E3INT = p2_e3_int.o stack_fp.o
########################################################
all: $(EJS) clear

p2_e3_int: $(OBJECTSP2E3INT)
	$(CC) $(CFLAGS) -o p2_e3_int $(OBJECTSP2E3INT)

p2_e3_int.o: p2_e3_int.c stack_fp.h
	$(CC) $(CFLAGS) -c p2_e3_int.c

stack_fp.o: stack_fp.c 
	$(CC) $(CFLAGS) -c stack_fp.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p2_e2"
	./p2_e3_int 7

runv:
	@echo ">>>>>>Running p2_e1 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p2_e3_int 7
