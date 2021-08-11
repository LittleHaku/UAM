# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract3.exe

pract3.exe: pract3ac.obj pract3a.obj pract3b.obj
	tcc -v -ml -Lc:\compila\tc\lib pract3ac.obj pract3a.obj pract3b.obj

pract3ac.obj: pract3ac.c
	tcc -c -v -ml -Ic:\compila\tc\include pract3ac.c

pract3a.obj: pract3a.asm
	tasm /zi /ml pract3a,,pract3a

pract3b.obj: pract3b.asm
	tasm /zi /ml pract3b,,pract3b
	
#vgaddga.exe: vgaddga.obj
#    tlink /v vgaddga.obj

#vgaddga.obj: vgaddga.asm
#    tasm /zi vgaddga.asm,,vgaddga.lst
	
clean:
	del pract3.exe
	del pract3ac.obj
	del pract3a.obj
	del pract3b.obj
#	del vgaddga.obj



