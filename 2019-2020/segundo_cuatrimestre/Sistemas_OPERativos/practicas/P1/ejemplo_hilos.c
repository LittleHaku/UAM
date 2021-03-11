/**
 * @file ejemplo_hilos.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Test para conocer como funcionan los pthread
 * ATENCION Valgrind da problemas cuando no llamas a pthread_join una vez llamado a pthread_create
 * */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

/**
 * void *slow_printf(void *arg)
 * es la funcion que ejecuta un hilo creado para imprimir la cadena arg
 * parametros entrada: void*arg es la cadena a imprimir 
 * parametros salida: no hay
 * */
void *slow_printf(void *arg)
{
    //Valgrind da problemas si no haces join a los pthreads
    /*if (pthread_detach(pthread_self()))
    {
        exit(EXIT_FAILURE);
    }*/
    const char *msg = arg;
    for (size_t i = 0; i < strlen(msg); i++)
    {
        printf(" %c ", msg[i]);
        if (fflush(stdout) == EOF)
        {
            exit(EXIT_FAILURE);
        }
    }
    pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
    pthread_t h1;
    pthread_t h2;
    char *hola = "Hola";
    char *mundo = "Mundo";
    int error;

    error = pthread_create(&h1, NULL, slow_printf, hola);
    if (error != 0)
    {
        fprintf(stderr, "pthread_create : %s\n", strerror(error));
        exit(EXIT_FAILURE);
    }

    error = pthread_create(&h2, NULL, slow_printf, mundo);
    if (error != 0)
    {
        fprintf(stderr, "pthread_create : %s\n", strerror(error));
        exit(EXIT_FAILURE);
    }

    error = pthread_join(h1, NULL);
    if (error != 0)
    {
        fprintf(stderr, " pthread_join : %s \n ", strerror(error));
        exit(EXIT_FAILURE);
    }

    error = pthread_join(h2, NULL);
    if (error != 0)
    {
        fprintf(stderr, " pthread_join : %s \n ", strerror(error));
        exit(EXIT_FAILURE);
    }

    if (printf(" El programa %s termino correctamente \n ", argv[0]) <= 0)
    {
        exit(EXIT_FAILURE);
    }
    pthread_exit(NULL);
}