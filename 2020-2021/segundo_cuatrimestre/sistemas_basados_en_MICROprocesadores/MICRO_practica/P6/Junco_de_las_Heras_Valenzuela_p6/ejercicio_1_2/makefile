# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Grupo: Doble Grado 2301

all: p4a.com p4b.exe

p4a.com: p4a.obj
    tlink /v /t p4a

p4a.obj: p4a.asm
    tasm /zi p4a.asm,,p4a.lst
	
p4b.exe: p4b.obj
    tlink /v p4b.obj

p4b.obj: p4b.asm
    tasm /zi p4b.asm,,p4b.lst

clean:
    del p4a.obj
    del p4a.map
	del p4a.com
	
	del p4b.exe
	del p4b.obj
    del p4b.map
