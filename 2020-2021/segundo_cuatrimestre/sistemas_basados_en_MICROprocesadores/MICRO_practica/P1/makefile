# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.estudiante
# Grupo: Doble Grado 2301

all: pract1a.exe pract1c.exe

pract1a.exe: pract1a.obj
    tlink /v pract1a.obj

pract1c.exe: pract1c.obj
    tlink /v pract1c.obj

pract1a.obj: pract1a.asm
    tasm /zi pract1a.asm,,pract1a.lst

pract1c.obj: pract1c.asm
    tasm /zi pract1c.asm,,pract1c.lst

clean:
    del pract1a.obj
    del pract1a.exe
    del pract1a.map
    del pract1c.obj
    del pract1c.exe
    del pract1c.map
