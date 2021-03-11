/**
 * @file ejercicio_prottemp_mejorado_op.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 23-02-2020
 * Descripcion: ejercicio 13, optativo
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

#define FILENAME "data.txt"
#define SEM_NAME "/ejercicio13_1"

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
 * es una funcion que lee 2 numeros de data.txt y los devuelve en n1 y n2
 * @param n1 el primer numero a devolver
 * @param n2 el segundo numero a devolve
 * @return -1 si hay error, 0 en caso contrario
*/
int lee_numeros(int *n1, int *n2){
    FILE *f = NULL;
    char *cad = "\n";

    if(!n1 || !n2) {
        return -1;
    }

    f = fopen(FILENAME, "r");
    if(!f) {
        perror("fopen");
        return -1;
    }

    if(fread((void*)n1, sizeof(int), 1, f) <= 0) {
        perror("fread");
        fclose(f);
        return -1;
    }

    if(fread((void*)(&cad), sizeof(char), 1, f) <= 0) {
        perror("fread");
        fclose(f);
        return -1;
    }

    if(fread((void*)(n2), sizeof(int), 1, f) <= 0) {
        perror("fread");
        fclose(f);
        return -1;
    }

    fclose(f);

    return 0;
}

/**
 * Es la funcion que escribe n1, \n, n2 en data.txt
 * @param n1 el primer numero a escribir
 * @param n2 el segundo numero a escribir
 * @return -1 si ha habido algun error, 0 en caso contrario
 * */
int escribe_numeros(int n1, int n2) {
    FILE *f = NULL;
    char *cad = "\n";

    f = fopen(FILENAME, "w");
    if(!f) {
        perror("fopen");
        return -1;
    }

    if(fwrite((void*)(&n1), sizeof(int), 1, f) <= 0) {
        perror("fwrite");
        fclose(f);
        return -1;
    }

    if(fwrite((void*)(cad), sizeof(char), 1, f) <= 0) {
        perror("fwrite");
        fclose(f);
        return -1;
    }

    if(fwrite((void*)(&n2), sizeof(int), 1, f) <= 0) {
        perror("fwrite");
        fclose(f);
        return -1;
    }

    fclose(f);

    return 0;
}


/**
 * es la  funcion que ejecutara el hijo. 
 * @param ppid es el el pid_t del padre
 * */
void f_hijo(pid_t ppid) {
    sigset_t set;
    pid_t pid = getpid();
    int i, n1, n2;
    long int sum = 0, fin = pid/10;
    sem_t *sem = NULL;

    sigemptyset(&set);

    //Abre el semaforo SEM_NAME
    if ((sem = sem_open(SEM_NAME, O_CREAT, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }

    /*Calcula la suma que se pide en el ejercicio para cada hijo*/
    for(i = 1; i <= fin; i++) {
        sum += i;
    }


    printf("Soy el hijo %d, mi trabajo es %ld\n", pid, sum);

    sem_wait(sem);
    //seccion critica, lee los numeros, los escribe en data.txt y envia SIGUSR2 al padre
    lee_numeros(&n1, &n2);
    escribe_numeros(n1+1, n2+sum);
    
    //manda la senial SIGUSR2 al padre
    if(kill(ppid, SIGUSR2) < 0) {
        sem_close(sem);
        perror("kill");
        exit(EXIT_FAILURE);
    }

    sem_post(sem);
    sem_close(sem);
    
    /*Se suspenden hasta que reciba la señal del padre SIGTERM de que ya ha terminado*/
    while(got_term == 0) {
        sigsuspend(&set);
    }

    exit(EXIT_SUCCESS);
}



int main(int argc, char* argv[]) {
    int N, T, i, n1, n2, imprimio;
    pid_t ppid, *phijo = NULL;
    struct sigaction act;
    sigset_t set;
    sem_t *sem = NULL;

    /*Se comprueba que tiene todos los argumentos necesarios, argv[1] = N, argv[2] = T*/
    if(argc < 3) {
    	printf("Argumentos insuficientes\n");
        exit(EXIT_FAILURE);
    }

    N = atoi(argv[1]);
    T = atoi(argv[2]);

    //crea el data.txt y escribe 0 0
    escribe_numeros(0, 0);

    if ((sem = sem_open(SEM_NAME, O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("sem_open");
        exit(EXIT_FAILURE);
    }

    /*obtenemos el pid del padre para que los hijos puedan enviar señales al padre*/
    ppid = getpid();

    /*Se crea un array de pid_t's para guardar los pid de cada uno de los hijos, para
    que el padre pueda enviarles la señal*/
    phijo = malloc(N*sizeof(pid_t));
    if(!phijo) {
        perror("malloc");
        sem_close(sem);
        exit(EXIT_FAILURE);
    }

    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;
    act.sa_handler = manejador;

    /*Se rellena la estructura del manejador, con SIGALRM, SIGTERM Y SIGUSR2*/
    if(sigaction(SIGALRM, &act, NULL) < 0) {
        perror("sigaction");
        sem_close(sem);
        exit(EXIT_FAILURE);
    }

    if(sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction");
        sem_close(sem);
        exit(EXIT_FAILURE);
    }

    if(sigaction(SIGUSR2, &act, NULL) < 0) {
        perror("sigaction");
        sem_close(sem);
        exit(EXIT_FAILURE);
    }

    sigemptyset(&set);

    /*Se crean los N hijos y cada hijo ejecuta su codigo en la funcion f_hijo*/
    for(i = 0; i < N; i++) {
        phijo[i] = fork();
        if(phijo[i] < 0) {
            perror("fork");
            sem_close(sem);
            exit(EXIT_FAILURE);
        }
        //el hijo ejecuta f_hijo y acaba con exit
        if(phijo[i] == 0) {
            free(phijo); //el hijo ya no va a necesitar el array
            sem_close(sem); //el hijo hereda sem, pero vamos a abrir otro puntero en la funcion
            f_hijo(ppid);
        }
        //el padre
    }

    alarm(T);

    //Suspenderlo mientras no llegue SIGALRM o el numero de seniales SIGUSR2 recibidas es N
    while(got_alarm == 0) {
        if(num_usr2 == N) {
            lee_numeros(&n1, &n2);
            printf("Han acabado todos, resultado: %d %d\n", n1, n2);
            imprimio = 1;
            break;
        }
        sigsuspend(&set);
    }
    //si ha acabado por got_alarm, evita que imprima el resultado y luego le llege la alarma e imprima falta trabajo
    if(imprimio == 0) { 
        printf("Falta trabajo\n");
    }

    for(i = 0; i < N; i++) {
        if(kill(phijo[i], SIGTERM) < 0) {
            sem_close(sem);
            perror("kill");
            exit(EXIT_FAILURE);
        }
    }

    printf("Padre recibe %d seniales SIGUSR2\n", num_usr2);
    printf("Finalizado Padre\n");

    free(phijo);
    sem_unlink(SEM_NAME);
    sem_close(sem);

    while(wait(NULL) != -1);

    exit(EXIT_SUCCESS);
}