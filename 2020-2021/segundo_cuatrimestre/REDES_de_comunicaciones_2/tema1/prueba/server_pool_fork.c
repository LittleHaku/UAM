#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <resolv.h>
#include <arpa/inet.h>
#include <errno.h>
#include <unistd.h>
#include <semaphore.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <signal.h> 

#include "conexion.h"

#define SERVER_PORT  1111
#define NUM_POOL     4
#define SEM_NAME     "/semMutex"

void child_main(int i, int listenfd, int addrlen);

sem_t *mutex;
int *clientfd;
int *pids;
int sockfd;

void handler(int signal) {

    int i;
    free(clientfd);
    for (i = 0; i < NUM_POOL; i++) {
        kill(pids[i], SIGTERM);
    }
    wait(0);
    close(sockfd);
    exit(0);
}

void handler_hijo(int signal) {
    free(clientfd);
    exit(0);
}

int main(void)
{  
	int childpid, i;
	int addrlen=sizeof(struct sockaddr_in);
    struct sigaction act;

	sockfd = tcp_listen(SERVER_PORT, 1);

    if (sockfd < 0) {
        perror("Error abriendo socket");
        exit(1);
    }

    //establecemos el manejador para SIGINT
    act.sa_handler= handler;
    sigemptyset(&act.sa_mask);
    act.sa_flags= 0;

    sigaction(SIGINT, &act, NULL);

    /*mutex = sem_open(SEM_NAME, O_CREAT|O_EXCL, O_RDWR, 1);
    if (mutex == NULL) {
        perror( "Error semaforo");
        exit(1);
    }*/

    clientfd = (int*)malloc(NUM_POOL);
    if (clientfd == NULL) {
        perror("Error reservando memoria para pool");
        exit(1);
    }

    pids = (int*)malloc(NUM_POOL);
    if (pids == NULL) {
        perror("Error reservando memoria para pool");
        exit(1);
    }

    //sem_unlink(SEM_NAME);

    for (i = 0; i < NUM_POOL; i++) {
    	if ((childpid = fork()) == 0) {     
            pids[i] = getpid();
            child_main(i, sockfd, addrlen);
        }
    }

    wait(0);

    //sem_close(mutex);
    free(clientfd);
	close(sockfd);
	exit(0);
}

void child_main(int i, int listenfd, int addrlen)
{
    sigset_t intmask;
    struct sigaction act;

    sigfillset(&intmask);
    sigaddset(&intmask, SIGTERM);

    if (sigprocmask(SIG_SETMASK, &intmask, NULL) < 0) {
        perror("error en la mascara");
        free(clientfd);
        exit(0);
    }

    //establecemos el manejador para SIGTERM
    act.sa_handler= handler_hijo;
    sigemptyset(&act.sa_mask);
    act.sa_flags= 0;

    sigaction(SIGTERM, &act, NULL);

    while(1) {
		struct sockaddr_in cliaddr;
        int clilen = addrlen;

        //sem_wait(mutex);
        clientfd[i] = accept(listenfd, (struct sockaddr*)&cliaddr, &clilen);
        printf("Conexión desde [%s:%d]\n", inet_ntoa(cliaddr.sin_addr), ntohs(cliaddr.sin_port));
        //sem_post(mutex);

        process_request(clientfd[i]);              
        close(clientfd[i]);
    }

    free(clientfd);
    exit(0);
}