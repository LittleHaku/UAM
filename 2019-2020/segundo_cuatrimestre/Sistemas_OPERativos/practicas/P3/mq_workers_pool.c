/**
 * @file mq_workers_pool.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 04-04-2020
 * Descripcion: Código de contar caracteres
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
#include <ctype.h>

#define MAX 2000

static volatile sig_atomic_t got_usr2 = 0;
static volatile sig_atomic_t got_term = 0;

/*manejador de las señales SIGUSR2 y SIGTERM*/
void manejador (int sig) {
    if (sig == SIGUSR2) {
        got_usr2 = 1;
    }
    else if (sig == SIGTERM) {
        got_term = 1;
    }
}

/*Realiza el trabajo de cada hijo, y recibe como argumento:
el pid del padre, para poder enviarle la señal SIGUSR2,
el nombre de la cola de mensajes, y el caracter a buscar, respectivamente*/
int f_hijo(pid_t ppid, char* name, char* c) {
    int cont = 0;
    int cont_mgs = 0;
    int es_finalizado = 0;

    /*se arma la estructura de la cola de mensajes*/
    struct mq_attr attributes = {
        .mq_flags = 0,
        .mq_maxmsg = 10,
        .mq_curmsgs = 0,
        .mq_msgsize = MAX
    };

    /*se abre la cola de mensajes y se comprueba que no ha fallado*/
    mqd_t queue = mq_open(name,
        O_CREAT | O_RDONLY,
        S_IRUSR | S_IWUSR, 
        &attributes);

    if(queue == (mqd_t)-1) {
        fprintf(stderr, "Error opening the queue\n");
        return EXIT_FAILURE;
    }

    char msg[2000] = " ";
    int pos = 0;

    /*los hijos irán leyendo mensajes, y como la variable got_term es
    local del thread, no necesita que sea protegida, hasta que o llegan al mensaje de
    finalizacion u otro hijo ha llegado y el padre le ha mandado la señal 
    SIGTERM*/
    while (got_term == 0) {

        if (mq_receive(queue, (char *)&msg, sizeof(msg), NULL) == -1) {
            fprintf(stderr, "Error receiving message\n");
            mq_close(queue);
            return EXIT_FAILURE;
        }

        if (strcmp(msg, "Finalizacion\0") == 0 ) {
            es_finalizado = 1;
            break;
        }
        /*en cada mensaje se cuenta del numero de veces que sale el 
        caracter pasado por argumento*/
        pos = 0;
        while (msg[pos] != 0) {
            if (msg[pos] == c[0]) {
                cont++;
            }
            pos++;
        }

        cont_mgs++;
    }

    /*si al salir del bucle, es_finalizado == 1,
     entonces es que se ha leido el mensaje de finalizacion*/
    if (es_finalizado == 1){
        if (kill(ppid, SIGUSR2) == -1) {
            perror("error enviando señal");
            mq_close(queue);
            return EXIT_FAILURE;
        }
    }
        
    /*se liberan recursos*/
    mq_close(queue);

    /*Estadisticas*/
    printf("Soy el hijo con pid %d, y he leido %d mensajes y he contado %d veces el caracter %c \n", getpid(), cont_mgs, cont, c[0]);

    return EXIT_SUCCESS;
}

int main(int argc, char* argv[]) {
    pid_t ppid, pid;
    int i, N;
    struct sigaction act;

    /*comprobamos que tenemos los argumentos suficientes*/
    if (argc < 4) {
        printf("Argumentos insuficientes");
        return EXIT_FAILURE;
    }

    /*llenamos la estructura del manejador*/
    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;
    act.sa_handler = manejador;
    
    /* Se arma la señal SIGUSR2. */
    if (sigaction(SIGUSR2, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    /* Se arma la señal SIGTERM. */
    if (sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    /*se recupera el numero de procesos hijos a lanzar 
    del primer argumento*/
    N = atoi(argv[1]);

    /*guardamos el pid del padre para que los hijos
    puedan mandarle la señal después*/
    ppid = getpid();

    /*Se reserva memoria para guardar los pid's de los
    hijos*/
    pid_t *hijos_p = NULL;
    hijos_p = (pid_t*)malloc(N*sizeof(pid_t));
    if(!hijos_p) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }

    /*se lanzan los N procesos hijos*/
    for (i = 0; i < N; i++) {
        pid = fork();
        if (pid < 0) {
            perror("fork");
            free(hijos_p);
            return EXIT_FAILURE;
        }
        if (!pid) {
            /*cada hijo llama a f_hijo*/
            free(hijos_p);
            return  f_hijo(ppid, argv[2], argv[3]);
        }
        hijos_p[i] = pid;
    }

    sigset_t set;
    sigemptyset(&set);

    /*el padre espera la llegada de la señal de 
    SIGUSR2 de alguno de sus hijos*/
    while (got_usr2 == 0) {
        sigsuspend(&set);
    }

    /*Una vez recibida, les manda a todos la señal SIGTERM
    para que terminen*/
    for (i = 0; i < N; i++) {
        if (kill(hijos_p[i], SIGTERM) == -1) {
            perror("error enviando señal");
            free(hijos_p);
            return EXIT_FAILURE;
        }
    }

    /*los espera*/
    while (wait(NULL) != -1);

    /*libera recursos*/
    free(hijos_p);
    mq_unlink(argv[2]);

    return EXIT_SUCCESS;
}