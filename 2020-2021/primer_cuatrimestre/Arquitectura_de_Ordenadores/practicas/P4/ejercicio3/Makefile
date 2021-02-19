# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

LIBS = -lm
LIB_DIR =
FLAGS = -lgomp -Wall -D_GNU_SOURCE -fopenmp

.PHONY: clean all

all: multiplication_slow multiplication_slow_par1 multiplication_slow_par2 multiplication_slow_par3 clear

multiplication_slow: multiplication_slow.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

multiplication_slow_par1: multiplication_slow_par1.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

multiplication_slow_par2: multiplication_slow_par2.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

multiplication_slow_par3: multiplication_slow_par3.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

clean:
	rm -f *.o *~ multiplication_slow multiplication_slow_par1 multiplication_slow_par2 multiplication_slow_par3

clear:
	rm -f *.o *~
