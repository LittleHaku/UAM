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

int main(void) {
    int fd[2];
    int fd1[2];

    //Se crea la semilla para los numeros aleatorios en rand()
    srand(time(NULL));

	char aleat[20];
    int num = 0;
	char readbuffer[30];
    
    int pipe1 = pipe(fd);

    if (pipe1 < 0) {
        perror("Primera tuberia");
        exit(EXIT_FAILURE);
    }

    int pipe2 = pipe(fd1);
    if (pipe2 < 0) {
        perror("Segunda tuberia");
        exit(EXIT_FAILURE);
    }

    pid_t child1 = fork();

    if (child1 < 0) {
        perror("Primer hijo");
        exit(EXIT_FAILURE);
    }
    else if (child1 == 0) {
        close(fd[0]);

        num = rand();

       sprintf(aleat,"%d",num);

        ssize_t nbytes = write(fd[1], aleat, strlen((char*)aleat)+1);
        if (nbytes < 0) {
            perror("Escribiendo en primera tuberia");
            exit(EXIT_FAILURE);
        }

        printf("Hijo 1 ha escrito en primera tuberia %s\n", aleat);
        exit(EXIT_SUCCESS);
    }
    else {
        close(fd[1]);

        ssize_t nbytes = 0;

        do {
            nbytes = read(fd[0], readbuffer, sizeof(readbuffer));
            if (nbytes < 0) {
                perror("lectura por parte del padre");
                exit(EXIT_FAILURE);
            }
            if (nbytes > 0) {
                printf("Padre ha leido primera tuberia: %.*s\n", (int)nbytes, readbuffer);
            }
        } while(nbytes != 0);
        wait(NULL);

    }

    pid_t child2 = fork();

    if (child2 < 0) {
        perror("segundo hijo");
        exit(EXIT_FAILURE);
    }
    else if (child2 != 0) {
        close(fd1[0]);

        ssize_t nbytes = write(fd1[1], readbuffer, strlen(readbuffer)+1);
        if (nbytes < 0) {
            perror("Escritura por parte de padre");
            exit (EXIT_FAILURE);
        }

        printf("Padre ha escrito en segunda tuberia %s \n", readbuffer);
        exit(EXIT_SUCCESS);
    }
    else {
        close(fd1[1]);
        ssize_t nbytes = 0;

        do {
            nbytes = read(fd1[0], readbuffer, sizeof(readbuffer));
            if (nbytes < 0) {
                perror("Lectura de hijo2 en segunda tuberia");
                exit(EXIT_FAILURE);
            }

            if (nbytes > 0) {
                printf("El hijo 2 ha leido %.*s\n", (int) nbytes, readbuffer);
            }
        } while(nbytes!= 0);


        int file1 = open(FILE1, O_CREAT | O_TRUNC | O_RDWR,  S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
        if (file1 < 0) {
            perror("escritura en fichero");
            exit(EXIT_FAILURE);
        }

        ssize_t size_total = 0;
        ssize_t tam = strlen(readbuffer) +1;

        do {
            ssize_t size_written = write(file1, readbuffer, tam-size_total);
            if(size_written == -1)
            {
                perror("write en fichero");
                exit(EXIT_FAILURE);
            }
            size_total +=size_written;
	    } while(tam != size_total);

        close(file1);

        exit(EXIT_SUCCESS);
    }
    wait(NULL);
}