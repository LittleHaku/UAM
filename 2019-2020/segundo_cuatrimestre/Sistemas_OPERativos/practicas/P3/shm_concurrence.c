/**
 * @file shm_concurrence.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 28-03-2020
 * Descripcion: Ejercicio de multiples procesos utilizando una estructura compartida, sin semaforo
 * */

#include <errno.h>
#include <fcntl.h>
#include <math.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

#define SHM_NAME "/shm_eje3"
#define MAX_MSG 2000

//Funcion dada que devuelve por argumento una cadena con el tiempo
static void getMilClock(char *buf) {
    int millisec;
    char aux[100];
    struct tm* tm_info;
    struct timeval tv;

    gettimeofday(&tv, NULL);
    millisec = lrint(tv.tv_usec/1000.0); // Round to nearest millisec
    if (millisec>=1000) { // Allow for rounding up to nearest second
        millisec -=1000;
        tv.tv_sec++;
    }
    tm_info = localtime(&tv.tv_sec);
    strftime(aux, 10, "%H:%M:%S", tm_info);
    sprintf(buf, "%s.%03d", aux, millisec);
}

//Estructura ClientLog
typedef struct {
    pid_t processid;       /* Logger process PID */
    long logid;            /* Id of current log line */
    char logtext[MAX_MSG]; /* Log text */
} ClientLog;

ClientLog *shm_struct;

//Es el manejador de la senial SIGUSR1
void manejador (int sig) {
    if (sig == SIGUSR1) {
        printf ("Log %ld: Pid %d: %s\n",shm_struct->logid, shm_struct->processid, shm_struct->logtext);
    }
}

//Es la funcion que ejecutaran los procesos hijos
void f_hijo(pid_t ppid, int m) {
    char buf[MAX_MSG];
    pid_t pid = getpid();
    int i, to_sleep;

    //Repite m veces
    for(i = 0; i < m; i++) {
         //Duerme un tiempo aleatorio, en milisegundos
        to_sleep = (rand() % 701) + 200;
        usleep(to_sleep*1000); //[200..900] miliseconds

        //Carga la informacion, como no esta en la zona critica puede ser machacado
        shm_struct->logid++;
        shm_struct->processid = pid;
        getMilClock(buf);
        sprintf(shm_struct->logtext, "Soy el proceso %d a las %s", pid, buf);
        //Imprime la cadena, como no esta en la zona critica puede ser machacado
        kill(ppid, SIGUSR1);
    }

    exit(EXIT_SUCCESS);
}

int main(int argc, char *argv[]) {
    int n, m, i;
    int ret = EXIT_FAILURE;
    struct sigaction act;

    srand(time(NULL));

    if (argc < 3) {
        fprintf(stderr,"usage: %s <n_process> <n_logs> \n",argv[0]);
        return ret;
    }

    n = atoi(argv[1]);
    m = atoi(argv[2]);

    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;

    /* Se arma la señal SIGUSR1. */
    act.sa_handler = manejador;
    if (sigaction(SIGUSR1, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    /* We create the shared memory */
    int fd_shm = shm_open(SHM_NAME,
        O_RDWR | O_CREAT | O_EXCL, /* Create it and open for reading and writing */
        S_IRUSR | S_IWUSR); /* The current user can read and write */

    if (fd_shm == -1) {
        fprintf(stderr, "Error creating the shared memory segment\n");
        return EXIT_FAILURE;
    }

    /* Resize the memory segment */
    if (ftruncate(fd_shm, sizeof(ClientLog)) == -1) {
        fprintf(stderr, "Error resizing the shared memory segment\n");
        shm_unlink(SHM_NAME);
        return EXIT_FAILURE;
    }

    /* Map the memory segment */
    shm_struct = mmap(NULL,
        sizeof(ClientLog), /* Memory mapping size */
        PROT_READ | PROT_WRITE, /* Read and write */
        MAP_SHARED, /* Share data changes */
        fd_shm, /* File or mem_file descriptor */
        0);
    close(fd_shm);

    if (shm_struct == MAP_FAILED) {
        fprintf(stderr, "Error mapping the shared memory segment\n");
        shm_unlink(SHM_NAME);
        return EXIT_FAILURE;
    }
    //Inicializa los valores
    pid_t ppid = getpid();
    shm_struct->logid = -1;

    //Lanza n hijos
    for(i = 0; i < n; i++) {
        if(!fork()) {
            f_hijo(ppid, m);
        }
    }

    //Espera a recibir todas las seniales
    while(shm_struct->logid != n*m-1) {
        pause();
    }

    /* Free the shared memory */
    munmap(shm_struct, sizeof(ClientLog));
    shm_unlink(SHM_NAME);

    return EXIT_SUCCESS;
}
