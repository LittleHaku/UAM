# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

LIBS = -lm
LIB_DIR =
FLAGS = -O3 -lgomp -Wall -D_GNU_SOURCE -fopenmp

.PHONY: clean all

all: pescalar_serie pescalar_par1 pescalar_par2 pescalar_par3 pescalar_par4 clear

pescalar_serie: pescalar_serie.c arqo4.c
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

pescalar_par1: pescalar_par1.c arqo4.c
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

pescalar_par2: pescalar_par2.c arqo4.c
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

pescalar_par3: pescalar_par3.c arqo4.c
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

pescalar_par4: pescalar_par4.c arqo4.c
	gcc $(FLAGS) $(LIB_DIR) -o $@ $^ $(LIBS)

clean:
	rm -f *.o *~ pescalar_serie pescalar_par1 pescalar_par2 pescalar_par3 pescalar_par4

clear:
	rm -f *.o *~
