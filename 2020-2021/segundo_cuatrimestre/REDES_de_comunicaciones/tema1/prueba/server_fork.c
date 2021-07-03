#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <resolv.h>
#include <arpa/inet.h>
#include <errno.h>
#include <unistd.h>
#include <signal.h> 
#include <sys/wait.h>
#include "conexion.h"

#define SERVER_PORT  1112

int sockfd;

void handler(int signal) {
	wait(0);
	close(sockfd);
	exit(0);
}

int main(void)
{   
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

	while (1)
	{	int clientfd;
		struct sockaddr_in client_addr;
		int addrlen=sizeof(client_addr);
		int childpid;

		// Aceptamos conexiones
		clientfd = accept(sockfd, (struct sockaddr*)&client_addr, &addrlen);

		if ( (childpid = fork()) == 0) {	
			printf("Conexión desde [%s:%d]\n", inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));	
			process_request(clientfd);	/* Procesa la peticion */
			exit(0);
		}

		// Cerramos la conexión, cuando pasemos a HTTP se mirara si es 1.0 o 1.1 y esto cambiará

		close(clientfd);
	}

	close(sockfd);
	exit(0);
}