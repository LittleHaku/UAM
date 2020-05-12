########################################################
CC=gcc
CFLAGS= -g -Wall -pedantic
EJS = p4_e4
########################################################
OBJECTSP3E1 = p4_e4.o tree.o string.o
########################################################
all: $(EJS) clear

p4_e4: $(OBJECTSP3E1)
	$(CC) $(CFLAGS) -o p4_e4 $(OBJECTSP3E1)

p4_e4.o: p4_e4.c tree.h string.h
	$(CC) $(CFLAGS) -c p4_e4.c

string.o: string.c string.h
	$(CC) $(CFLAGS) -c string.c

tree.o: tree.c  tree.h
	$(CC) $(CFLAGS) -c tree.c

clear:
	rm -rf *.o

clean:
	rm -rf *.o $(EJS)
run:
	@echo ">>>>>>Running p4_e4"
	./p4_e4 cadenas.txt

runv:
	@echo ">>>>>>Running p4_e4 with valgrind"
	valgrind --leak-check=full --show-leak-kinds=all ./p4_e4 cadenas.txt


