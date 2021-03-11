/**
 * @file ejercicio_pipes.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Ejercicio en el que hay que crear dos hilos A y B y que A cree un
 * numero aleatorio x para pasarselo al padre, y este se lo pase a B y B lo imprima en un fichero FILE1
 * */

#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <time.h>

#define FILE1 "numero_leido.txt"
#define BUFF 64

int main(void)
{
    int fd1[2];
    int fd2[2];

    //Se crea la semilla para los numeros aleatorios en rand()
    srand(time(NULL));

    char aleat[BUFF];
    int num = 0;
    char readbuffer[BUFF];

    //Se crea la primera tuberia
    int pipe1 = pipe(fd1);

    if (pipe1 < 0)
    {
        perror("Primera tuberia");
        exit(EXIT_FAILURE);
    }

    pid_t child1 = fork();

    if (child1 < 0)
    {
        perror("Primer hijo");
        exit(EXIT_FAILURE);
    }

    if (child1 == 0)
    {
        //Se cierra el extremo de lectura
        if(close(fd1[0]) == -1) {
            exit(EXIT_FAILURE);
        }

        //Se genera el numero aleatorio
        num = rand();

        if (sprintf(aleat, "%d", num) <= 0)
        {
            exit(EXIT_FAILURE);
        }

        ssize_t nbytes = write(fd1[1], aleat, strlen((char *)aleat) + 1);
        if (nbytes < 0)
        {
            perror("Escribiendo en primera tuberia");
            exit(EXIT_FAILURE);
        }

        printf("Hijo 1 ha escrito en primera tuberia %s\n", aleat);

        if(close(fd1[1]) == -1) {
            exit(EXIT_FAILURE);
        }
        exit(EXIT_SUCCESS);
    }
    //es el padre y recive x, luego manda la x al hijo 2
    if (close(fd1[1]) == -1)
    {
        exit(EXIT_FAILURE);
    }

    ssize_t nbytes = 0;

    do
    {
        nbytes = read(fd1[0], readbuffer, sizeof(readbuffer));
        if (nbytes < 0)
        {
            perror("lectura por parte del padre");
            exit(EXIT_FAILURE);
        }
        if (nbytes > 0)
        {
            printf("Padre ha leido primera tuberia: %.*s\n", (int)nbytes, readbuffer);
        }
    } while (nbytes != 0);

    wait(NULL); //espera a que muera el 1º hijo
    if (close(fd1[0]) == -1)
    {
        exit(EXIT_FAILURE);
    }

    //Se crea la segunda tuberia
    int pipe2 = pipe(fd2);
    if (pipe2 < 0)
    {
        perror("Segunda tuberia");
        exit(EXIT_FAILURE);
    }

    pid_t child2 = fork();

    if (child2 < 0)
    {
        perror("segundo hijo");
        exit(EXIT_FAILURE);
    }

    if (child2 != 0)
    { //el padre manda el numero generado por su otro hijo
        if (close(fd2[0]) == -1)
        {
            exit(EXIT_FAILURE);
        }

        ssize_t nbytes = write(fd2[1], readbuffer, strlen(readbuffer) + 1);
        if (nbytes < 0)
        {
            perror("Escritura por parte de padre");
            exit(EXIT_FAILURE);
        }

        close(fd2[1]);
        if (printf("Padre ha escrito en segunda tuberia %s \n", readbuffer) <= 0)
        {
            exit(EXIT_FAILURE);
        }

        wait(NULL); //espera al segundo hijo
        exit(EXIT_SUCCESS);
    }
    else
    { //el 2º hijo recibe x
        if (close(fd2[1]) == -1)
        {
            exit(EXIT_FAILURE);
        }
        ssize_t nbytes = 0;

        do
        {
            nbytes = read(fd2[0], readbuffer, sizeof(readbuffer));
            if (nbytes < 0)
            {
                perror("Lectura de hijo2 en segunda tuberia");
                exit(EXIT_FAILURE);
            }

            if (nbytes > 0)
            {
                if (printf("El hijo 2 ha leido %.*s\n", (int)nbytes, readbuffer) <= 0)
                {
                    exit(EXIT_FAILURE);
                }
            }
        } while (nbytes != 0);
        if (close(fd2[0]) == -1)
        {
            exit(EXIT_FAILURE);
        }

        int file1 = open(FILE1, O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
        if (file1 < 0)
        {
            perror("escritura en fichero");
            exit(EXIT_FAILURE);
        }

        ssize_t size_total = 0;
        ssize_t tam = strlen(readbuffer) + 1;

        do
        {
            ssize_t size_written = write(file1, readbuffer, tam - size_total);
            if (size_written == -1)
            {
                perror("write en fichero");
                exit(EXIT_FAILURE);
            }
            size_total += size_written;
        } while (tam != size_total);

        if (close(file1) == -1)
        {
            exit(EXIT_FAILURE);
        }

        exit(EXIT_SUCCESS);
    }
}