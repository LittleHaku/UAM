/**
 * @file shm_open.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 28-03-2020
 * Descripcion: Test del shm_open
 * */
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>

#define SHM_NAME "/shared"
extern int errno;

int main(void) {

    while(1) {
        /* This is only a code fragment, not a complete program... */
        int fd_shm = shm_open ("/shared", O_RDWR | O_CREAT | O_EXCL, S_IRUSR | S_IWUSR);
        if (fd_shm == -1) {
            if (errno == EEXIST) { /* Shared memory segment already exists */
                shm_unlink(SHM_NAME);
                continue;
                fd_shm = shm_open(SHM_NAME, O_RDWR, 0);
                if (fd_shm == -1) {
                    fprintf(stderr, "Error opening the shared memory segment\n");
                    return EXIT_FAILURE;
                }
            }
            else {
                fprintf(stderr, "Error creating the shared memory segment\n");
                return EXIT_FAILURE;
            }
        }
        else {
            printf ("Shared memory segment created\n");
        }
        break;
    }

    return EXIT_SUCCESS;
}