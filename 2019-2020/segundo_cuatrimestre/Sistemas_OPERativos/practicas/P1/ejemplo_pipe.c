/**
 * @file ejemplo_pipe.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Test para conocer como funciona las pipe
 * */

#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(void)
{
    int fd[2];

    const char *string = "Hola a todos!\n";
    //comentar esta variable si se va a comentar la lectura del padre
    char readbuffer[80];

    int pipe_status = pipe(fd);
    if (pipe_status == -1)
    {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    pid_t childpid = fork();
    if (childpid == -1)
    {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (childpid == 0)
    {

        /* Cierre del descriptor de entrada en el hijo */
        if (close(fd[0]) == -1)
        {
            exit(EXIT_FAILURE);
        }

        if (sleep(1))
        {
            exit(EXIT_FAILURE);
        }
        /* Enviar el saludo vía descriptor de salida */
        /* strlen(string) + 1 < PIPE_BUF así que no hay escrituras cortas */
        ssize_t nbytes = write(fd[1], string, strlen(string) + 1);
        if (nbytes == -1)
        {
            perror("write");
            exit(EXIT_FAILURE);
        }

        if (printf("He escrito en el pipe\n") <= 0)
        {
            exit(EXIT_FAILURE);
        }
        if (close(fd[1]) == -1)
        {
            exit(EXIT_FAILURE);
        }
    }
    else
    {
        /* Cierre del descriptor de salida en el padre */
        if (close(fd[1]) == -1)
        {
            exit(EXIT_FAILURE);
        }
        /* Leer algo de la tubería... el saludo! */
        ssize_t nbytes = 0;
        do
        {
            nbytes = read(fd[0], readbuffer, sizeof(readbuffer));
            if (nbytes == -1)
            {
                perror("read");
                exit(EXIT_FAILURE);
            }
            if (nbytes > 0)
            {
                if (printf("He recibido el string: %.*s", (int)nbytes, readbuffer) <= 0)
                {
                    exit(EXIT_FAILURE);
                }
            }
        } while (nbytes != 0);

        wait(NULL);
        if (close(fd[0]) == -1)
        {
            exit(EXIT_FAILURE);
        }
    }

    exit(EXIT_SUCCESS);
}
