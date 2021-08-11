# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract3b.exe

pract3b.exe: pract3b.obj pract3bs.obj
	tcc -v -ml -Lc:\compila\tc\lib pract3b.obj pract3bs.obj

pract3b.obj: pract3b.c
	tcc -c -v -ml -Ic:\compila\tc\include pract3b.c

pract3bs.obj: pract3bs.asm
	tasm /zi /ml pract3bs,,pract3bs
	
clean:
	del pract3b.exe
	del pract3b.obj
	del pract3bs.obj



