/**
 * @file mq_injector.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 04-04-2020
 * Descripcion: Código que mete mensajes en la cola de mensajes
 * */

#include <fcntl.h>
#include <mqueue.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <signal.h>
#include <string.h>
#include <errno.h>
#include <sys/mman.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

#define MAX 2000


int main(int argc, char* argv[]) {
    FILE *f = NULL;

    /*Comprobamos que los argumentos son los que queremos, y no menos*/
    if (argc < 3) {
        printf("Argumentos insuficientes");
        exit(EXIT_FAILURE);
    }

    /*Declaramos la estructura de la cola de mensajes*/
    struct mq_attr attributes = {
        .mq_flags = 0,
        .mq_maxmsg = 10,
        .mq_curmsgs = 0,
        .mq_msgsize = MAX    //los mensajes como máximo de 2kB
    };

    /*abrimos el fichero a leer*/
    f = fopen(argv[1], "r");
    if (f == NULL) {
        perror("Error abriendo fichero");
        exit(EXIT_FAILURE);
    }

    /* Este proceso solo va a escribir los mensajes en la cola*/
    mqd_t queue = mq_open(argv[2],
        O_CREAT | O_WRONLY, 
        S_IRUSR | S_IWUSR, 
        &attributes);

    /*comprobamos que no ha fallado la apertura de la cola de mensajes*/
    if (queue == (mqd_t)-1) {
        fprintf(stderr, "Error opening the queue\n");
        fclose(f);
        return EXIT_FAILURE;
    }

    char linea[MAX] = " ";
    int num_mgs = 0;

    /*Vamos leyendo de 2k en 2k*/
    while (fgets(linea, MAX, f) != NULL && num_mgs < 9) {
        /*guardamos el mensaje en la cola*/
        if (mq_send(queue, (char *)&linea, sizeof(linea), 1) == -1) {
            fprintf(stderr, "Error sending message\n");
            fclose(f);
            return EXIT_FAILURE;
        }
        num_mgs++;
    }

    if (num_mgs == 9) {
        printf("No se va a leer mas de fichero, se va a meter el mensaje de finalizacion\n");
    }

    /*Y por último metemos el mensaje de finalizacion*/
    strcpy(linea, "Finalizacion");
    if (mq_send(queue, (char *)&linea, sizeof(linea), 1) == -1) {
        fprintf(stderr, "Error sending message\n");
        fclose(f);
        return EXIT_FAILURE;
    }

    /*No incluimos el de finalizacion en este dato, meramente informativo*/
    printf("%d mensajes escritos\n", num_mgs);

    /*cerramos el fichero*/
    fclose(f);
    fprintf(stdout, "Press any key to finish\n");
    getchar();

    /*liberamos recursos*/
    mq_close(queue);
    mq_unlink(argv[2]);

    exit(EXIT_SUCCESS);
}