/**
 * @file ejercicio_prottemp.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 23-02-2020
 * Descripcion: ejercicio 8, para ver como se comunican padre e hijos con las señales
 * */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>

/*se crean estas variables globales para controlar las tres señales que 
aparecen en el ejercicio, y así solo usar una rutina para el manejador*/

static volatile int num_usr2 = 0;
static volatile sig_atomic_t got_usr2 = 0;
static volatile sig_atomic_t got_alarm = 0;
static volatile sig_atomic_t got_term = 0;

/**
 * es la funcion que maneja las interrupciones del programa
 * @param sig es la senial que recibe el manejador para distinguirlas
 * */
void manejador(int sig) {
    if(sig == SIGALRM) {
        got_alarm = 1;
    }
    else if(sig == SIGTERM) {
        got_term = 1;
    }
    else if(sig == SIGUSR2) {
        got_usr2 = 1;
        num_usr2++;
    }
}

/**
 * es la  funcion que ejecutara el hijo. 
 * @param ppid es el el pid_t del padre
 * */
void f_hijo(pid_t ppid) {
    sigset_t set;
    pid_t pid = getpid();
    int i;
    long int sum = 0, fin = pid/10;

    sigemptyset(&set);

    /*Calcula la suma que se pide en el ejercicio para cada hijo*/
    for(i = 1; i <= fin; i++) {
        sum += i;
    }

    if(kill(ppid, SIGUSR2) < 0) {
        perror("kill");
        exit(EXIT_FAILURE);
    }

    printf("Soy el hijo %d, mi trabajo es %ld\n", pid, sum);

    /*Se suspenden hasta que reciba la señal del padre SIGTERM de que ya ha terminado*/
    while(got_term == 0) {
        sigsuspend(&set);
    }


    exit(EXIT_SUCCESS);
}



int main(int argc, char* argv[]) {
    int N, T, i;
    pid_t ppid, *phijo = NULL;
    struct sigaction act;
    sigset_t set;

    /*Se comprueba que tiene todos los argumentos necesarios, argv[1] = N, argv[2] = T*/
    if(argc < 3) {
	printf("Argumentos insuficientes\n");
        exit(EXIT_FAILURE);
    }

    N = atoi(argv[1]);
    T = atoi(argv[2]);

    /*obtenemos el pid del padre para que los hijos puedan enviar señales al padre*/
    ppid = getpid();

    /*Se crea un array de pid_t's para guardar los pid de cada uno de los hijos, para
    que el padre pueda enviarles la señal*/
    phijo = malloc(N*sizeof(pid_t));
    if(!phijo) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }

    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;
    act.sa_handler = manejador;

    /*Se rellena la estructura del manejador, con SIGALRM, SIGTERM Y SIGUSR2*/
    if(sigaction(SIGALRM, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    if(sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    if(sigaction(SIGUSR2, &act, NULL) < 0) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    sigemptyset(&set);

    /*Se crean los N hijos y cada hijo ejecuta su codigo en la funcion f_hijo*/
    for(i = 0; i < N; i++) {
        phijo[i] = fork();
        if(phijo[i] < 0) {
            perror("fork");
            exit(EXIT_FAILURE);
        }
        //el hijo ejecuta f_hijo y acaba con exit
        if(phijo[i] == 0) {
            //el hijo ya no va a necesitar el array
            free(phijo);
            f_hijo(ppid);
        }
        //el padre
    }


    alarm(T);

    //Suspenderlo mientras no llegue SIGALRM
    while(got_alarm == 0) {
        sigsuspend(&set);
    }

    for(i = 0; i < N; i++) {
        if(kill(phijo[i], SIGTERM) < 0) {
            perror("kill");
            exit(EXIT_FAILURE);
        }
    }
    printf("Padre recibe %d seniales SIGUSR2\n", num_usr2);
    printf("Finalizado Padre\n");

    free(phijo);

    while(wait(NULL) != -1);

    exit(EXIT_SUCCESS);
}
