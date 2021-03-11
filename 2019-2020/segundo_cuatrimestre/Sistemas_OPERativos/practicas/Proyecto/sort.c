/**
 * @file sort.c
 * @author Junco de las Heras junco.heras@estudiante.uam.es y Marta Vaquerizo marta.vaquerizo@estudiante.uam.es
 * Grupo: 2201
 * Fecha: 23-04-2020
 * Descripcion: implementacion de las funciones de sort.h, algoritmo de ordenación de 1 proceso y multiproceso.
 * */
#define _POSIX_C_SOURCE 200112L

#include <errno.h>
#include <fcntl.h>
#include <math.h>
#include <mqueue.h>
#include <semaphore.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <mqueue.h>
#include <time.h>
#include <unistd.h>
#include "sort.h"
#include "utils.h"

#define SHM_NAME "/shm_sort"
#define MQ_NAME "/mq_tareas"
#define MUTEX_SORT "/mutex_sort"
#define MUTEX_USR1 "/mutex_usr1"

/**
 * struct to insert in the mqueue
 * */
typedef struct {
    int i; /**The i-level */
    int j; /**The j-part*/
} Mensaje;

/**
 * Estado en el que esta cada trabajador. El ilustrador se encarga de mostrar las estadisticas.
 * */
typedef struct {
    pid_t pid;
    int level;
    int part;
    int ini;
    int end;
} WorkerStatus;

/**
 * Variables globales para manejar las señales recibidas.
 * */
static volatile sig_atomic_t got_usr1 = 0;
static volatile sig_atomic_t got_term = 0;
static volatile sig_atomic_t got_int = 0;
static volatile sig_atomic_t got_alrm = 0;

sem_t *mutex_usr1 = NULL;
int index_hijo, *fd_work2ilu = NULL, *fd_ilu2work = NULL;
WorkerStatus estadoHijo;

void sort_manejador(int sig) {
    if (sig == SIGUSR1) {
        got_usr1++;
        /**
         * hace un up(mutex_usr1) para que otros procesos 
         * puedan mandar una señal SIGUSR1 sin machacar la variable
         * */
        sem_post(mutex_usr1);
    }
    else if (sig == SIGTERM) {
        got_term++;
    }
    else if(sig == SIGINT) {
        got_int++;
    }
    else if(sig == SIGALRM) {
        Bool leido;
        got_alrm++;

        if(write(fd_work2ilu[2*index_hijo+1], &estadoHijo, sizeof(WorkerStatus)) == -1) {
            perror("error write tuberia");
            return;
        }

        if(read(fd_ilu2work[2*index_hijo], &leido, sizeof(Bool)) == -1) {
            perror("error read tuberia");
            return;
        }

        /*Programa la siguiente alarma*/
        alarm(1);
    }
}

Status bubble_sort(int *vector, int n_elements, int delay) {
    int i, j;
    int temp;

    if ((!(vector)) || (n_elements <= 0)) {
        return ERROR;
    }

    for (i = 0; i < n_elements - 1; i++) {
        for (j = 0; j < n_elements - i - 1; j++) {
            /* Delay. */
            fast_sleep(delay);
            if (vector[j] > vector[j+1]) {
                temp = vector[j];
                vector[j] = vector[j + 1];
                vector[j + 1] = temp;
            }
        }
    }

    return OK;
}

Status merge(int *vector, int middle, int n_elements, int delay) {
    int *aux = NULL;
    int i, j, k, l, m;

    /*El malloc para el array auxiliar es de ambito local.*/
    if (!(aux = (int *)malloc(n_elements * sizeof(int)))) {
        return ERROR;
    }

    for (i = 0; i < n_elements; i++) {
        aux[i] = vector[i];
    }

    i = 0; j = middle;
    for (k = 0; k < n_elements; k++) {
        /* Delay. */
        fast_sleep(delay);
        if ((i < middle) && ((j >= n_elements) || (aux[i] < aux[j]))){
            vector[k] = aux[i];
            i++;
        }
        else {
            vector[k] = aux[j];
            j++;
        }

        /* This part is not needed, and it is computationally expensive, but
        it allows to visualize a partial mixture. */
        /*Rellena los huecos libres del array vector para que se vea bien el modo grafico,
         convierte el coste en O(n^2)*/
        m = k + 1;
        for (l = i; l < middle; l++) {
            vector[m] = aux[l];
            m++;
        }
        for (l = j; l < n_elements; l++) {
            vector[m] = aux[l];
            m++;
        }
    }

    free((void *)aux);
    return OK;
}

int get_number_parts(int level, int n_levels) {
    /* The number of parts is 2^(n_levels - 1 - level). */
    return 1 << (n_levels - 1 - level);
}

Status init_sort(char *file_name, Sort *sort, int n_levels, int n_processes, int delay) {
    char string[MAX_STRING];
    FILE *file = NULL;
    int i, j, log_data;
    int block_size, modulus;

    if ((!(file_name)) || (!(sort))) {
        fprintf(stderr, "init_sort - Incorrect arguments\n");
        return ERROR;
    }

    /* At most MAX_LEVELS levels. */
    sort->n_levels = MAX(1, MIN(n_levels, MAX_LEVELS));
    /* At most MAX_PARTS processes can work together. */
    sort->n_processes = MAX(1, MIN(n_processes, MAX_PARTS));
    /* The main process PID is stored. */
    sort->ppid = getpid();
    /* Delay for the algorithm in ns (less than 1s). */
    sort->delay = MAX(1, MIN(999999999, delay));

    if (!(file = fopen(file_name, "r"))) {
        perror("init_sort - fopen");
        return ERROR;
    }

    /* The first line contains the size of the data, truncated to MAX_DATA. */
    if (!(fgets(string, MAX_STRING, file))) {
        fprintf(stderr, "init_sort - Error reading file\n");
        fclose(file);
        return ERROR;
    }

    sort->n_elements = atoi(string);
    if (sort->n_elements > MAX_DATA) {
        sort->n_elements = MAX_DATA;
    }

    /* The remaining lines contains one integer number each. */
    for (i = 0; i < sort->n_elements; i++) {
        if (!(fgets(string, MAX_STRING, file))) {
            fprintf(stderr, "init_sort - Error reading file\n");
            fclose(file);
            return ERROR;
        }
        sort->data[i] = atoi(string);
    }
    fclose(file);

    /* Each task should have at least one element. */
    log_data = compute_log(sort->n_elements);
    if (n_levels > log_data) {
        n_levels = log_data;
    }
    sort->n_levels = n_levels;

    /* The data is divided between the tasks, which are also initialized. */
    block_size = sort->n_elements / get_number_parts(0, sort->n_levels);
    modulus = sort->n_elements % get_number_parts(0, sort->n_levels);
    sort->tasks[0][0].completed = INCOMPLETE;
    sort->tasks[0][0].ini = 0;
    sort->tasks[0][0].end = block_size + (modulus > 0);
    sort->tasks[0][0].mid = NO_MID;
    for (j = 1; j < get_number_parts(0, sort->n_levels); j++) {
        sort->tasks[0][j].completed = INCOMPLETE;
        sort->tasks[0][j].ini = sort->tasks[0][j - 1].end;
        sort->tasks[0][j].end = sort->tasks[0][j].ini \
            + block_size + (modulus > j);
        sort->tasks[0][j].mid = NO_MID;
    }
    for (i = 1; i < n_levels; i++) {
        for (j = 0; j < get_number_parts(i, sort->n_levels); j++) {
            sort->tasks[i][j].completed = INCOMPLETE;
            sort->tasks[i][j].ini = sort->tasks[i - 1][2 * j].ini;
            sort->tasks[i][j].mid = sort->tasks[i - 1][2 * j].end;
            sort->tasks[i][j].end = sort->tasks[i - 1][2 * j + 1].end;
        }
    }

    return OK;
}

Bool check_task_ready(Sort *sort, int level, int part) {
    if (!(sort)) {
        return FALSE;
    }

    if ((level < 0) || (level >= sort->n_levels) \
        || (part < 0) || (part >= get_number_parts(level, sort->n_levels))) {
        return FALSE;
    }

    if (sort->tasks[level][part].completed != INCOMPLETE) {
        return FALSE;
    }

    /* The tasks of the first level are always ready. */
    if (level == 0) {
        return TRUE;
    }

    /* Other tasks depend on the hierarchy. */
    if ((sort->tasks[level - 1][2 * part].completed == COMPLETED) && \
        (sort->tasks[level - 1][2 * part + 1].completed == COMPLETED)) {
        return TRUE;
    }

    return FALSE;
}

Status solve_task(Sort *sort, int level, int part) {
    /* In the first level, bubble-sort. */
    if (sort->tasks[level][part].mid == NO_MID) {
        return bubble_sort(\
            sort->data + sort->tasks[level][part].ini, \
            sort->tasks[level][part].end - sort->tasks[level][part].ini, \
            sort->delay);
    }
    /* In other levels, merge. */
    else {
        return merge(\
            sort->data + sort->tasks[level][part].ini, \
            sort->tasks[level][part].mid - sort->tasks[level][part].ini, \
            sort->tasks[level][part].end - sort->tasks[level][part].ini, \
            sort->delay);
    }
}

Status sort_single_process(char *file_name, int n_levels, int n_processes, int delay) {
    int i, j;
    Sort sort;

    /* The data is loaded and the structure initialized. */
    if (init_sort(file_name, &sort, n_levels, n_processes, delay) == ERROR) {
        fprintf(stderr, "sort_single_process - init_sort\n");
        return ERROR;
    }

    plot_vector(sort.data, sort.n_elements);
    printf("\nStarting algorithm with %d levels and %d processes...\n", sort.n_levels, sort.n_processes);
    /* For each level, and each part, the corresponding task is solved. */
    for (i = 0; i < sort.n_levels; i++) {
        for (j = 0; j < get_number_parts(i, sort.n_levels); j++) {
            solve_task(&sort, i, j);
            plot_vector(sort.data, sort.n_elements);
            printf("\n%10s%10s%10s%10s%10s\n", "PID", "LEVEL", "PART", "INI", \
                "END");
            printf("%10d%10d%10d%10d%10d\n", getpid(), i, j, \
                sort.tasks[i][j].ini, sort.tasks[i][j].end);
        }
    }

    plot_vector(sort.data, sort.n_elements);
    printf("\nAlgorithm completed\n");

    return OK;
}
/**
 * Es la funcion que ejecuta el proceso trabajador.
 * @method funcion_worker
 * @date   2020-04-25
 * @author Junco de las Heras y Marta Vaquerizo
 * @param  sort        Pointer to the sort structure.
 * @param  ppiid       Pid del proceso padre.
 * @return             ERROR in case of error, OK otherwise.
 */
Status funcion_worker(Sort *sort, pid_t ppid) {
    struct mq_attr attributes;
    mqd_t queue;
    Mensaje msg;
    sem_t *mutex_sort = NULL;    

    attributes.mq_flags = 0;
    attributes.mq_maxmsg = 10;
    attributes.mq_curmsgs = 0;
    attributes.mq_msgsize = sizeof(Mensaje);

    /*se abre la cola de mensajes y se comprueba que no ha fallado*/
    queue = mq_open(MQ_NAME,
        O_CREAT | O_RDONLY,
        S_IRUSR | S_IWUSR, 
        &attributes);

    if(queue == (mqd_t)-1) {
        fprintf(stderr, "Error opening the queue\n");
        munmap(NULL, sizeof(Sort));
        sem_close(mutex_usr1);
        free(fd_work2ilu);
        free(fd_ilu2work);
        return ERROR;
    }

    if ((mutex_sort = sem_open(MUTEX_SORT, O_RDWR, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("sem_open");
        munmap(NULL, sizeof(Sort));
        mq_close(queue);
        free(fd_work2ilu);
        free(fd_ilu2work);
        sem_close(mutex_usr1);
        return ERROR;
    }

    estadoHijo.pid = getpid();

    /*Programa la primera alarma. Las demas se programan en el manejador.*/
    alarm(1);

    /*Mientras no reciba la señal got_term*/
    while (got_term == 0) {
        /*receive the task*/
        got_alrm = 0;
        estadoHijo.level = -1;
        estadoHijo.part = -1;
        estadoHijo.ini = -1;
        estadoHijo.end = -1;
        if (mq_receive(queue, (char *)&msg, sizeof(msg), NULL) == -1) {
            /*Interrupted by system call*/
            if(errno == EINTR) {
                /*Llega el SIGTERM del padre*/
                if(got_term > 0) {
                    break;
                }
                /*Era otra señal, repetir el bucle*/
                continue;
            }
            perror("error mq_receive");
            munmap(NULL, sizeof(Sort));
            mq_close(queue);
            sem_close(mutex_sort);
            sem_close(mutex_usr1);
            free(fd_work2ilu);
            free(fd_ilu2work);
            return ERROR;
        }

        /*se indica que la tarea va a ser resuelta*/
        do {
            got_alrm = 0;
            sem_wait(mutex_sort);
            /*Con este do{}while() se evita que salga del sem_wait recibiendo la señal de alarma.*/
        } while(got_alrm > 0);
        sort->tasks[msg.i][msg.j].completed = PROCESSING;
        estadoHijo.level = msg.i;
        estadoHijo.part = msg.j;
        estadoHijo.ini = sort->tasks[msg.i][msg.j].ini;
        estadoHijo.end = sort->tasks[msg.i][msg.j].end;
        sem_post(mutex_sort);

        /*Hace su tarea*/
        solve_task(sort, msg.i, msg.j);

        /*Actualiza el estado a COMPLETED*/
        do {
            got_alrm = 0;
            sem_wait(mutex_sort);
        } while(got_alrm > 0);
        /*se indica que se ha completado la tarea*/
        sort->tasks[msg.i][msg.j].completed = COMPLETED;
        sem_post(mutex_sort);

        if(got_term > 0) {
            break;
        }

        /*Le manda una señal SIGUSR1 al padre. El codigo esta dentro de la zona critica*/
        do {
            got_alrm = 0;
            sem_wait(mutex_usr1);
        } while(got_alrm > 0);
        if (kill(ppid, SIGUSR1) == -1) {
            perror("Error sending signal SIGUSR1\n");
            munmap(NULL, sizeof(Sort));
            mq_close(queue);
            sem_close(mutex_sort);
            sem_close(mutex_usr1);
            free(fd_work2ilu);
            return ERROR;
        }
    }
    
    /*cuando ha terminado, libera sus recursos*/
    munmap(NULL, sizeof(Sort));
    mq_close(queue);
    sem_close(mutex_sort);
    sem_close(mutex_usr1);
    free(fd_work2ilu);
    free(fd_ilu2work);
    return OK;
}
/**
 * Es la funcion que ejecuta el proceso ilustrador.
 * @method funcion_ilustrador
 * @date   2020-04-25
 * @author Junco de las Heras y Marta Vaquerizo
 * @param  sort        Pointer to the sort structure.
 * @return             ERROR in case of error, OK otherwise.
 */
Status funcion_ilustrador(Sort *sort) {
    int i;
    Bool ready = TRUE;
    WorkerStatus leido;

    /*El plot_vector inicial*/
    plot_vector(sort->data, sort->n_elements);
    printf("\nStarting algorithm with %d levels and %d processes...\n", sort->n_levels, sort->n_processes);

    /*Mientras no reciba la señal got_term*/
    while(got_term == 0) {
        for(i = 0; i < sort->n_processes; i++) {
            /*Lee el estado de los trabajadores.*/
            if(read(fd_work2ilu[2*i], &leido, sizeof(WorkerStatus)) == -1) {
                /*Interrupted by System Call*/
                if(errno == EINTR) {
                    /*Llegada de SIGTERM*/
                    break;
                }
                perror("error read tuberia");
                free(fd_ilu2work);
                free(fd_work2ilu);
                return ERROR;
            }
            if(i == 0) {
                /*Imprime el estado por pantalla antes del primer hijo.*/
                plot_vector(sort->data, sort->n_elements);
                printf("\tPID\tLEVEL\tPART\tINI\tEND\n");
            }
            if (leido.level < 0) {
                printf("\t%d\t    -\t   -\t  -\t  -\n", leido.pid);
            }
            else {
                printf("\t%d\t %4d\t %3d\t %2d\t %d\n", leido.pid, leido.level, leido.part, leido.ini, leido.end);
            }
            
        }

        if(got_term > 0) {
            break;
        }

        /*Escribe que pueden continuar a todos los trabajadores.*/
        for(i = 0; i < sort->n_processes; i++) {
            if(write(fd_ilu2work[2*i+1], &ready, sizeof(Bool)) == -1) {
                perror("error write tuberia");
                free(fd_ilu2work);
                free(fd_work2ilu);
                return ERROR;
            }
        }
    }
    
    /*El plot_vector final (siempre que no haya recibido la señal SIGINT)*/
    if(got_int == 0) {
        plot_vector(sort->data, sort->n_elements);
        printf("\nAlgorithm completed\n");
    }

    /*cuando termina, libera los recursos*/
    free(fd_ilu2work);
    free(fd_work2ilu);
    return OK;
}
/**
 * Es la funcion que libera los recursos de sort_multy_process.
 * @method free_smp
 * @date   2020-04-25
 * @author Junco de las Heras y Marta Vaquerizo
 * @param  ret         Es el Status que devuelve la funcion.
 * @param  sem1        Es un sem_t* que hay que liberar.
 * @param  sem2        Es un sem_t* que hay que liberar.
 * @param  arr1        Es un int* que hay que liberar.
 * @param  arr2        Es un int* que hay que liberar.
 * @param  hijos       Es un pid_t* que hay que liberar.
 * @return             ret.
 */
Status free_smp(Status ret, sem_t *sem1, sem_t *sem2, int *arr1, int *arr2, pid_t *hijos) {
    munmap(NULL, sizeof(Sort));
    if(sem1) {sem_close(sem1);}
    if(sem2) {sem_close(sem2);}
    if(arr1) {free(arr1);}
    if(arr2) {free(arr2);}
    if(hijos) {free(hijos);}
    sem_unlink(MUTEX_SORT);
    sem_unlink(MUTEX_USR1);
    shm_unlink(SHM_NAME);
    mq_unlink(MQ_NAME);
    return ret;
}

Status sort_multy_process(char *file_name, int n_levels, int n_processes, int delay) {
    int i, j, parts;
    Sort *sort = NULL;
    struct mq_attr attributes;
    mqd_t queue;
    Mensaje msg;
    struct sigaction act;
    pid_t *p_hijos = NULL;
    pid_t ppid;
    pid_t pid_ilustrador;
    int fd_shm;
    sem_t *mutex_sort = NULL;
    sigset_t set;

    /*Se resetea la mascara para el sigsuspend*/
    sigemptyset(&set);

    /*llenamos la estructura del manejador*/
    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;
    act.sa_handler = sort_manejador;
    
    /* Se arma la señal SIGUSR1. */
    if (sigaction(SIGUSR1, &act, NULL) < 0) {
        perror("sigaction, SIGUSR1");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* Se arma la señal SIGTERM. */
    if (sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction, SIGTERM");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* Se arma la señal SIGINT. */
    if (sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction, SIGINT");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* Se arma la señal SIGALRM. */
    if (sigaction(SIGALRM, &act, NULL) < 0) {
        perror("sigaction, SIGINT");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* We create the shared memory */
    fd_shm = shm_open(SHM_NAME,
        O_RDWR | O_CREAT | O_EXCL, /* Create it and open for reading and writing */
        S_IRUSR | S_IWUSR); /* The current user can read and write */

    if (fd_shm == -1) {
        fprintf(stderr, "Error creating the shared memory segment\n");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* Resize the memory segment */
    if (ftruncate(fd_shm, sizeof(Sort)) == -1) {
        fprintf(stderr, "Error resizing the shared memory segment\n");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* Map the memory segment */
    sort = mmap(NULL,
        sizeof(Sort), /* Memory mapping size */
        PROT_READ | PROT_WRITE, /* Read and write */
        MAP_SHARED, /* Share data changes */
        fd_shm, /* File or mem_file descriptor */
        0);
    close(fd_shm);

    if (sort == MAP_FAILED) {
        fprintf(stderr, "Error mapping the shared memory segment\n");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* The data is loaded and the structure initialized. */
    if (init_sort(file_name, sort, n_levels, n_processes, delay) == ERROR) {
        fprintf(stderr, "sort_single_process - init_sort\n");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /*rellenamos la estructura de la cola de mensajes*/
    attributes.mq_flags = 0;
    attributes.mq_maxmsg = 10;
    attributes.mq_curmsgs = 0;
    attributes.mq_msgsize = sizeof(Mensaje);

    /* This process is only going to send messages */
    queue = mq_open(MQ_NAME,
        O_CREAT | O_WRONLY, /* BLOCKING QUEUE*/
        S_IRUSR | S_IWUSR, /* The user can read and write */
        &attributes);

    if (queue == (mqd_t)-1) {
        perror("Error opening the queue\n");
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    if ((mutex_sort = sem_open(MUTEX_SORT, O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("Error opening semaphore\n");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    if ((mutex_usr1 = sem_open(MUTEX_USR1, O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
        perror("Error opening semaphore\n");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /*The pipe and ilustrator process.
    ilustrator read from i-child fd[2*i]. The i-child writes in fd[2*i+1]*/
    fd_work2ilu = malloc(2*n_processes * sizeof(int));
    if(!fd_work2ilu) {
        perror("malloc fd_work2ilu");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    for(i = 0; i < n_processes; i++) {
        if(pipe(fd_work2ilu+2*i) == -1) {
            perror("error en pipe 1");
            mq_close(queue);
            return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
        }
    }

    fd_ilu2work = malloc(2*n_processes * sizeof(int));
    if(!fd_ilu2work) {
        perror("malloc fd_ilu2work");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    for(i = 0; i < n_processes; i++) {
        if(pipe(fd_ilu2work+2*i) == -1) {
            perror("error en pipe 1");
            mq_close(queue);
            return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
        }
    }

    /*se lanza el ilustrador*/
    pid_ilustrador = fork();
    if(pid_ilustrador < 0) {
        perror("error fork pid_ilustrador");
        mq_close(queue);
        exit(free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos));
    }

    if(!pid_ilustrador) {
        for(i = 0; i < n_processes; i++) {
            /*como el ilustrador va a leer, cierra las de 
            escritura*/
            close(fd_work2ilu[2*i+1]);
            close(fd_ilu2work[2*i]);
        }
        /*se liberan los recursos heredados del padre que 
        el ilustrador no necesita/usa*/
        sem_close(mutex_sort);
        sem_close(mutex_usr1);
        munmap(NULL, sizeof(Sort));
        mq_close(queue);
        exit(funcion_ilustrador(sort));
    }

    for(i = 0; i < n_processes; i++) {
        /*como los trabajadores van a escribir, cierran las
        de lectura*/
        close(fd_work2ilu[2*i]);
        close(fd_ilu2work[2*i+1]);
    }

    ppid = getpid();

    /*Reserva memoria para n_processes hijos*/
    p_hijos = (pid_t*)malloc(n_processes*sizeof(pid_t));
    if(p_hijos == NULL) {
        perror("malloc p_hijos");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /*se crean n_processes trabajadores*/
    for (j = 0; j < n_processes; j++) {
        p_hijos[j] = fork();
        
        if (p_hijos[j] < 0) {
            perror("Fork failed");
            exit(free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos));
        }

        if (!p_hijos[j]) {
            index_hijo = j;
            for(i = 0; i < n_processes; i++) {
                if(i != j) {
                    close(fd_work2ilu[2*i+1]);
                    close(fd_ilu2work[2*i]);
                }
            }
            free(p_hijos);
            sem_close(mutex_sort);
            exit(funcion_worker(sort, ppid));
        }
    }


    /* For each level, solve all the tasks. */
    for (i = 0; i < sort->n_levels; i++) {
        parts = get_number_parts(i, sort->n_levels);

        /*Send all the task in the same level to the mqueue*/
        for (j = 0; j < parts; j++) {
            msg.i = i;
            msg.j = j;
            if (mq_send(queue, (char *)&msg, sizeof(msg), 1) == -1) {
                /*Si se estaba esperando en mq_send y se recibe una señal 
                se debe volver a intentar poner la tarea en la cola*/
                if(errno == EINTR) {
                    j--;
                    continue;
                }
                perror("Error sending message\n");
                return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
            }

            /*se indica que la tarea fue enviada*/
            do {
                errno = 0;
                sem_wait(mutex_sort);
                /*Evita salir del sem_wait por la llegada de una señal.*/
            } while(errno == EINTR);
            sort->tasks[msg.i][msg.j].completed = SENT;
            sem_post(mutex_sort);
        }

        while(got_usr1 != parts) {
            /*Hasta que le llegue alguna señal*/
            sigsuspend(&set);
            if(got_int > 0) {
                break;
            }
        }
        if(got_int > 0) {
            break;
        }
        got_usr1 = 0;
    }

    /*Manda SIGTERM a los hijos.*/
    for (j = 0; j < n_processes; j++) {
        if (kill(p_hijos[j], SIGTERM) == -1) {
            perror("error enviando señal SIGTERM");
            mq_close(queue);
            return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
        }
    }

    if (kill(pid_ilustrador, SIGTERM) == -1) {
        perror("error enviando señal SIGTERM");
        mq_close(queue);
        return free_smp(ERROR, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
    }

    /* espera a que terminen todos sus hijos.*/
    while (wait(NULL) !=-1);   

    /*Acaba liberando todos los recursos.*/
    mq_close(queue);
    return free_smp(OK, mutex_sort, mutex_usr1, fd_work2ilu, fd_ilu2work, p_hijos);
}