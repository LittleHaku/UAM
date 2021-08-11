# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract2a.exe

pract2a.exe: pract2a.obj
    tlink /v pract2a.obj

pract2a.obj: pract2a.asm
    tasm /zi pract2a.asm,,pract2a.lst

clean:
    del pract2a.obj
    del pract2a.exe
    del pract2a.map
