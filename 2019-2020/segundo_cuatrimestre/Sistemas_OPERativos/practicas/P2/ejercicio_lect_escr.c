/**
 * @file ejercicio_lect_escr.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 23-02-2020
 * Descripcion: ejercicio 14
 * */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/mman.h>

#define SECS 0
#define N_READ 10

//El tiempo entre _INI y _FIN, = 1 para el ejercicio, 0 para el e, otro para debuggear
#define T_OCUPADO 1
#define SEM_ESCRITURA "/ejercicio14_escritura"
#define SEM_LECTURA "/ejercicio14_lectura"

/*se crean estas variables globales para controlar la  señales que 
aparecen en el ejercicio, y así solo usar una rutina para el manejador*/

static int *lectores = NULL; //nº de lectores que hay actualmente escribiendo

static volatile sig_atomic_t got_int = 0;
static volatile sig_atomic_t got_term = 0;

/**
 * es la funcion que maneja las interrupciones del programa
 * @param sig es la senial que recibe el manejador para distinguirlas
 * */
void manejador(int sig) {
    if(sig == SIGINT) {
        got_int = 1;
    }
    else if(sig == SIGTERM) {
        got_term = 1;
    }
}


/**
 * es la  funcion que ejecutara el hijo. 
 * @param ppid es el el pid_t del padre
 * */
void f_hijo(pid_t ppid) {
    pid_t pid = getpid();
    sem_t *sem_lectura = NULL, *sem_escritura = NULL;

    //Abre el semaforo SEM_LECTURA
    if ((sem_lectura = sem_open(SEM_LECTURA, O_CREAT, S_IRUSR | S_IWUSR, 0)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }

    //Abre el semaforo SEM_LECTURA
    if ((sem_escritura = sem_open(SEM_ESCRITURA, O_CREAT, S_IRUSR | S_IWUSR, 0)) == SEM_FAILED) {
        perror("sem_open");
        sem_close(sem_lectura);
        exit(EXIT_FAILURE);
    }


    while(1) {

        sem_wait(sem_lectura);
        (*lectores)++;

        if(*lectores == 1) {
            sem_wait(sem_escritura);
        }
        sem_post(sem_lectura);

        //Funcion de lectura
        printf("R_INI %d\n", pid);
        sleep(T_OCUPADO);
        printf("R_FIN %d\n", pid);

        sem_wait(sem_lectura);
        (*lectores)--;

        if(*lectores == 0) {
            sem_post(sem_escritura);
        }
        sem_post(sem_lectura);


        sleep(SECS);

        /*Si recibe la senial SIGTERM acaba*/
        if(got_term == 1) {
            sem_close(sem_lectura);
            sem_close(sem_escritura);
            exit(EXIT_SUCCESS);
        }
    }
}



int main(int argc, char* argv[]) {
    int i;
    pid_t ppid, *phijo = NULL;
    struct sigaction act;
    sem_t *sem_lectura = NULL, *sem_escritura = NULL;

    lectores = mmap(NULL, sizeof(int), PROT_READ | PROT_WRITE, 
                    MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    *lectores = 0;

    //Abre el semaforo SEM_LECTURA, inicializado a 1
    if ((sem_lectura = sem_open(SEM_LECTURA, O_CREAT| O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }

    //Abre el semaforo SEM_LECTURA, inicializado a 1
    if ((sem_escritura = sem_open(SEM_ESCRITURA, O_CREAT| O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("sem_open");
        sem_close(sem_lectura);
        exit(EXIT_FAILURE);
    }

    /*obtenemos el pid del padre para que los hijos puedan enviar señales al padre*/
    ppid = getpid();
    printf("Soy el padre, mandarme un SIGINT %d\n", ppid);

    /*Se crea un array de pid_t's para guardar los pid de cada uno de los hijos, para
    que el padre pueda enviarles la señal*/
    phijo = malloc(N_READ*sizeof(pid_t));
    if(!phijo) {
        perror("malloc");
        sem_close(sem_lectura);
        sem_close(sem_escritura);
        exit(EXIT_FAILURE);
    }

    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;
    act.sa_handler = manejador;

    if(sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction");
        sem_close(sem_lectura);
        sem_close(sem_escritura);
        exit(EXIT_FAILURE);
    }

    if(sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction");
        sem_close(sem_lectura);
        sem_close(sem_escritura);
        exit(EXIT_FAILURE);
    }



    /*Se crean los N hijos y cada hijo ejecuta su codigo en la funcion f_hijo*/
    for(i = 0; i < N_READ; i++) {
        phijo[i] = fork();
        if(phijo[i] < 0) {
            perror("fork");
            sem_close(sem_lectura);
            sem_close(sem_escritura);
            exit(EXIT_FAILURE);
        }
        //el hijo ejecuta f_hijo y acaba con exit
        if(phijo[i] == 0) {
            free(phijo); //el hijo ya no va a necesitar el array
            sem_close(sem_lectura); //el hijo hereda sem, pero vamos a abrir otro puntero en la funcion
            sem_close(sem_escritura); 
            f_hijo(ppid);
        }
        //el padre
    }

    while(got_int == 0) {

        sem_wait(sem_escritura);

        //Funcion de escritura
        printf("W_INI %d\n", ppid);
        sleep(T_OCUPADO);
        printf("W_FIN %d\n", ppid);

        sem_post(sem_escritura);

        sleep(SECS);
    }


    for(i = 0; i < N_READ; i++) {
        if(kill(phijo[i], SIGTERM) < 0) {
            sem_close(sem_lectura);
            sem_close(sem_escritura);
            perror("kill");
            exit(EXIT_FAILURE);
        }
    }


    printf("Finalizado Padre\n");

    //se libera los recursos
    free(phijo);
    sem_unlink(SEM_ESCRITURA);
    sem_unlink(SEM_LECTURA);
    sem_close(sem_lectura);
    sem_close(sem_escritura);
    munmap(lectores, sizeof(int));

    while(wait(NULL) != -1);

    exit(EXIT_SUCCESS);
}