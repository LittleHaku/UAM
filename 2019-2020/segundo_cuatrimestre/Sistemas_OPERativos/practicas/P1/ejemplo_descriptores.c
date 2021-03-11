/**
 * @file ejemplo_descriptores.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 16-02-2020
 * Descripcion: Test para conocer como funcionan los descriptores.
 * */
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#define MESSAGE "Hello"

#define FILE1 "file1.txt"
#define FILE2 "file2.txt"
#define FILE3 "file3.txt"

int main(void)
{
    if (fprintf(stderr, "PID = %d\nStop 1\n", getpid()) <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    int file1 = open(FILE1, O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
    if (file1 == -1)
    {
        perror("open");
        exit(EXIT_FAILURE);
    }

    /* Escribir mensaje */
    ssize_t total_size_written = 0;
    size_t target_size = sizeof(MESSAGE);
    do
    {
        ssize_t size_written = write(file1, MESSAGE + total_size_written, target_size - total_size_written);
        if (size_written == -1)
        {
            perror("write");
            exit(EXIT_FAILURE);
        }

        total_size_written += size_written;
    } while (total_size_written != target_size);

    if (fprintf(stderr, "Stop 2\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    int file2 = open(FILE2, O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
    if (file2 == -1)
    {
        perror("open");
        exit(EXIT_FAILURE);
    }

    if (fprintf(stderr, "Stop 3\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    int status = unlink(FILE1);
    if (status != 0)
    {
        perror("unlink");
        exit(EXIT_FAILURE);
    }

    if (fprintf(stderr, "Stop 4\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    if (close(file1) == -1)
    {
        exit(EXIT_FAILURE);
    }

    if (fprintf(stderr, "Stop 5\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    int file3 = open(FILE3, O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
    if (file3 == -1)
    {
        perror("open");
        exit(EXIT_FAILURE);
    }

    if (fprintf(stderr, "Stop 6\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    int file4 = open(FILE3, O_RDONLY, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
    if (file4 == -1)
    {
        perror("open");
        exit(EXIT_FAILURE);
    }

    if (fprintf(stderr, "Stop 7\n") <= 0)
    {
        exit(EXIT_FAILURE);
    }
    if (getchar() == EOF)
    {
        exit(EXIT_FAILURE);
    }

    if (close(file2) == -1)
    {
        exit(EXIT_FAILURE);
    }
    if (close(file3) == -1)
    {
        exit(EXIT_FAILURE);
    }
    if (close(file4) == 1)
    {
        exit(EXIT_FAILURE);
    }

    status = unlink(FILE2);
    if (status != 0)
    {
        perror("unlink");
        exit(EXIT_FAILURE);
    }

    status = unlink(FILE3);
    if (status != 0)
    {
        perror("unlink");
        exit(EXIT_FAILURE);
    }
}
