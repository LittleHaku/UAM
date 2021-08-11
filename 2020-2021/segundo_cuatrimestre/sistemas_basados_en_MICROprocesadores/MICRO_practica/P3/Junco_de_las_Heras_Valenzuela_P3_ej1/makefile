# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract2d.exe

pract2d.exe: pract2d.obj
    tlink /v pract2d.obj

pract2d.obj: pract2d.asm
    tasm /zi pract2d.asm,,pract2d.lst

clean:
    del pract2d.obj
    del pract2d.exe
    del pract2d.map
