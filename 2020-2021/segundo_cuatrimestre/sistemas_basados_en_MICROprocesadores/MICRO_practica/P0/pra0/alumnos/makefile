all: alumno.exe

alumno.exe: alumno.obj
    tlink /v alumno.obj

alumno.obj: alumno.asm
    tasm /zi alumno.asm,,alumno.lst

clean:
    del *.obj
    del *.exe
    del *.map
