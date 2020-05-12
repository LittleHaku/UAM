########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p3_e2
########################################################
OBJECTSP3E1 = p3_e2.o list.o
########################################################
all: $(EJS) clear

p3_e2: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p3_e2 $(OBJECTSP3E1)

p3_e2.o: p3_e2.c list.h
	$(CC) $(CFLAGS) -c p3_e2.c

list.o: list.c list.h
	$(CC) $(CFLAGS) -c list.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p3_e2"
	./p3_e2 6

runv:
	@echo ">>>>>>Running p3_e2 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p3_e2 6


