# Makefile creado por Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Grupo: Doble Grado 2301

all: p4a.com snake.exe

p4a.com: p4a.obj
    tlink /v /t p4a

p4a.obj: p4a.asm
    tasm /zi p4a.asm,,p4a.lst
	
snake.exe: snake.obj
    tlink /v snake.obj

snake.obj: snake.asm
    tasm /zi snake.asm,,snake.lst

clean:
    del p4a.obj
    del p4a.map
	del p4a.com
	del p4a.lst
	
	del snake.exe
	del snake.obj
    del snake.map
	del snake.lst
