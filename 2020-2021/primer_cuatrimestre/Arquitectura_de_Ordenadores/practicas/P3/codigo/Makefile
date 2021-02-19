# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

LIBS =
LIB_DIR =
FLAGS = -g -Wall -D_GNU_SOURCE

.PHONY: clean all

all: fast slow multiplication_slow multiplication_fast

fast: fast.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

slow: slow.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

multiplication_slow: multiplication_slow.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

multiplication_fast: multiplication_fast.c arqo3.c arqo3.h
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

clean:
	rm -f *.o *~ fast slow multiplication_slow multiplication_fast
