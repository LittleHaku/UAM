/**
 * @file shm_histograma.h
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 01-04-2020
 * Descripcion: TAD para copiar los datos en un fichero
 * */
#ifndef SHM_HISTOGRAMA_H
#define SHM_HISTOGRAMA_H

#define HISTOGRAMA_FAIL 1
#define HISTOGRAMA_SUCCESS 0
int histograma_fichero(char* filename, int *listado);

#endif