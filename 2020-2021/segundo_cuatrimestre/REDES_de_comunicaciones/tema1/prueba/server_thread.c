#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <resolv.h>
#include <arpa/inet.h>
#include <errno.h>
#include <unistd.h>
#include <pthread.h>

#include "conexion.h"

#define SERVER_PORT  1111

int main(void)
{   int sockfd;

	sockfd = tcp_listen(SERVER_PORT, 1);

	while (1)
	{	int clientfd;
		struct sockaddr_in client_addr;
		int addrlen=sizeof(client_addr);
		pthread_t pid;

		// Aceptamos conexiones
		clientfd = accept(sockfd, (struct sockaddr*)&client_addr, &addrlen);
		printf("Conexión desde [%s:%d]\n", inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));

		if ( (pthread_create (&pid, NULL, (void*)&process_request, &clientfd)) != 0) {				
			perror("Error creando hilo");
			exit(1);
		}

		pthread_exit(0);

		// Cerramos la conexión
		close(clientfd);
	}

	close(sockfd);
	return 0;
}