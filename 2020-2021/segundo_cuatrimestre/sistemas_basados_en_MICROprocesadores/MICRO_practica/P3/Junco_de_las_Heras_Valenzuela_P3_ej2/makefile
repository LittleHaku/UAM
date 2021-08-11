# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract2d.exe pract2e.exe

pract2d.exe: pract2d.obj
    tlink /v pract2d.obj

pract2d.obj: pract2d.asm
    tasm /zi pract2d.asm,,pract2d.lst

pract2e.exe: pract2e.obj
    tlink /v pract2e.obj

pract2e.obj: pract2e.asm
    tasm /zi pract2e.asm,,pract2e.lst


clean:
    del pract2d.obj
    del pract2d.exe
    del pract2d.map

    del pract2e.obj
    del pract2e.exe
    del pract2e.map
