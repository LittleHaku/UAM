#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <resolv.h>
#include <arpa/inet.h>
#include <errno.h>
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>

#include "conexion.h"

#define SERVER_PORT  1111
#define NUM_POOL     4
#define NUM_ARGS     3

void child_main(int* args);

int main(void)
{   int sockfd;
    int i;
    int addrlen=sizeof(struct sockaddr_in);
    pthread_t pid;
    int args[] = {0, addrlen};

    sockfd = tcp_listen(SERVER_PORT, 1);
    args[0] = sockfd;

    for (i = 0; i < NUM_POOL; i++) {
        if ((pthread_create (&pid, NULL, (void*)&child_main, (void*)&args)) != 0) {     
            perror("Error creando hilo");
            exit(1);
        }
    }

    close(sockfd);
    exit(0);
}

void child_main(int* args)
{
    while(1) {
        int clientfd;
        struct sockaddr_in cliaddr;
        int clilen = args[1];

        printf("%d, %d \n", args[0], args[1]);

        clientfd = accept(args[0], (struct sockaddr*)&cliaddr, &clilen);
        printf("Conexión desde [%s:%d]\n", inet_ntoa(cliaddr.sin_addr), ntohs(cliaddr.sin_port));

        process_request(clientfd);              
        close(clientfd);
    }

    pthread_exit(0);
}