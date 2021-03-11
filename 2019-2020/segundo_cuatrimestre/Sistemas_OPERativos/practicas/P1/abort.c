/**
 * @file abort.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Main que imprime por stdout abortando... y luego aborta
 * */

#include <stdio.h>
#include <stdlib.h>

//main que aborta. Guardese en abort.exe por ejemplo
int main(void)
{
    if(fprintf(stdout, "abortando...\n") <= 0) {
        exit(EXIT_FAILURE);
    }
    abort();
}
