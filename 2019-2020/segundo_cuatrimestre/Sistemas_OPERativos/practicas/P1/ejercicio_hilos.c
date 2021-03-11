/**
 * @file ejercicio_hilos.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: ejercicio en el que hay que calcular con hilos x^3 para distintos valores de x
 * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

//Esta es la estructura en la que se pasa como argumento al crear los hilos
//Tambien sirve para recuperar el valor deseado, en nuestro caso x^3
struct dato
{
    //x es el numero de thread que es y tambien donde va a estar el retorno
    int x;
    //t_espera es el numero aleatorio de segundos que debe de esperar antes de ejecutar
    int t_espera;
};

/**
 * void *f_hijo(void *v)
 * es la funcion que ejecuta un hilo creado para calcular x^3 sabiendo x
 * parametros entrada: void *v variable de una struct dato 
 * parametros salida: no hay, pero se guardara x^3 en *(struct dato *)v.x
 * */
void *f_hijo(void *v)
{
    if(sleep((*(struct dato *)v).t_espera)) {
        exit(EXIT_FAILURE);
    }
    //x = x^3
    (*(struct dato *)v).x = (*(struct dato *)v).x * (*(struct dato *)v).x * (*(struct dato *)v).x;

    pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
    if (argc < 2)
    {
        printf("Insuficientes argumentos de entrada\n");
        exit(EXIT_FAILURE);
    }
    //Creas la semilla para los numeros aleatorios en rand()
    srand(time(NULL));

    int i, n = atoi(argv[1]), error;
    pthread_t *h = malloc(n * sizeof(pthread_t));
    if (!h)
    {
        printf("Reserva incorrecta de memoria\n");
        exit(EXIT_FAILURE);
    }
    struct dato *d = malloc(n * sizeof(struct dato));
    if (!d)
    {
        free(h);
        printf("Reserva incorrecta de memoria\n");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < n; i++)
    {
        d[i].x = i;
        //numero entre 0 y 10
        d[i].t_espera = rand() % 11;
    }
    for (i = 0; i < n; i++)
    {
        error = pthread_create(&h[i], NULL, f_hijo, &d[i]);
        if (error != 0)
        {
            free(h);
            free(d);
            exit(EXIT_FAILURE);
        }
    }

    for (i = 0; i < n; i++)
    {
        error = pthread_join(h[i], NULL);
        if (error != 0)
        {
            free(h);
            free(d);
            exit(EXIT_FAILURE);
        }
        if(printf("resultado: %d\n", d[i].x) <= 0) {
            exit(EXIT_FAILURE);
        }
    }

    free(h);
    free(d);
    pthread_exit(NULL);
}