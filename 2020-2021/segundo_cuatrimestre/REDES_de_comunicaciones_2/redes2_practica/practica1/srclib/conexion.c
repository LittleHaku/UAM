/*****************************************************************
 Fichero que contiene las declaraciones de conexion.h.
 Autores: 
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
******************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <resolv.h>
#include <arpa/inet.h>
#include <syslog.h>
#include <locale.h>
#include <errno.h>
#include <unistd.h>
#include <sys/stat.h> // Para el umask().

#include "conexion.h"


int tcp_listen(int server_port, int queuelen) {
    /* Codigo parcialmente copiado de las diapositivas del tema 1 "Capa de aplicacion", pagina 43.*/
    int sockfd;
    struct sockaddr_in self;

    if ((server_port <= 0) || (queuelen <= 0)) {
        syslog(LOG_ERR, "%d %d", server_port, queuelen);
        syslog(LOG_ERR, "Error en los argumentos de la función tcp_listen");
        return -1;
    }

    // Creamos el socket TCP*/
    if ( (sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0 )
    {
        syslog(LOG_ERR, "Error al crear el socket");
        return -1;
    }

    // To reuse the port and the address. Copied from 
    // https://stackoverflow.com/questions/24194961/how-do-i-use-setsockoptso-reuseaddr
    int reuse = 1;
    if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, (const char*)&reuse, sizeof(reuse)) < 0)
        syslog(LOG_ERR, "setsockopt(SO_REUSEADDR) failed");

#ifdef SO_REUSEPORT
    if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEPORT, (const char*)&reuse, sizeof(reuse)) < 0) 
        syslog(LOG_ERR, "setsockopt(SO_REUSEPORT) failed");
#endif


    // Inicializamos estructura de dirección y puerto
    bzero(&self, sizeof(self));
    self.sin_family = AF_INET;
    self.sin_port = htons(server_port);
    self.sin_addr.s_addr = INADDR_ANY;

    // Ligamos puerto al socket
    if ( bind(sockfd, (struct sockaddr*)&self,  sizeof(self)) != 0 )
    {
        syslog(LOG_ERR, "Error al hacer bind");
        close(sockfd);
        return -1;
    }

    if (listen(sockfd, queuelen) != 0)
    {
        syslog(LOG_ERR, "Error al ponerse a escuchar en un puerto");
        close(sockfd);
        return -1;
    }

    printf("Escuchando en [%s:%d]...\n", inet_ntoa(self.sin_addr), ntohs(self.sin_port));

    return sockfd;
}

int accept_conexion(int sockfd, struct sockaddr_in client_addr, socklen_t addrlen) {
    int clientfd;

    clientfd = accept(sockfd, (struct sockaddr*)&client_addr, &addrlen);

    if (clientfd < 0) {
        syslog(LOG_ERR, "Error accepting connection");
        return -1;
    }

    // Muestra los datos de la conexion que se acaba de establecer.
    printf("Conexión desde [%s:%d]\n", inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));

    return clientfd;
}

int close_conexion(int clientfd) {
    return close(clientfd);
}

void do_daemon(void) {
    /* Codigo parcialmente copiado de las diapositivas del tema 1 "Capa de aplicacion", pagina 42.*/
    pid_t pid;
    pid = fork(); /* Fork off the parent process */
    if (pid < 0) exit(EXIT_FAILURE);
    if (pid > 0) exit(EXIT_SUCCESS); /* Exiting the parent process. */
    
    setlogmask (LOG_UPTO (LOG_INFO)); /* Open logs here */
    openlog ("Server system messages:", LOG_CONS | LOG_PID | LOG_NDELAY, LOG_LOCAL3);
    syslog (LOG_ERR, "Initiating new server.");
    

    if(setsid() < 0) { /* Create a new SID for the child process */
        syslog (LOG_ERR, "Error creating a new SID for the child process.");
        exit(EXIT_FAILURE);
    }

    syslog (LOG_INFO, "Closing standard file descriptors");
    /* Close out thestandard file descriptors */
    close(STDIN_FILENO); close(STDOUT_FILENO); close(STDERR_FILENO);
    
    return ;
}