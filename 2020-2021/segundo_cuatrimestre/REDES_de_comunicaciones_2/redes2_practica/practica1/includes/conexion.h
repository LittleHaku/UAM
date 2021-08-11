/*****************************************************************
 Fichero que contiene las funciones para establecer una conexion http.
 Autores: 
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
******************************************************************/

#ifndef CONEXION_H
#define CONEXION_H
 
#include <sys/socket.h>

/********
* FUNCIÓN:  tcp_listen(int server_port, int queuelen)

* ARGS_IN: int server_port - puerto donde está escuchando el servidor.
		   int queuelen	   - número de conexiones máximas procesandose a la vez.

* DESCRIPCIÓN: Devuelve el descriptor que devuelve socket().

* ARGS_OUT: int - descriptor para identificar el socket.
********/
int tcp_listen(int server_port, int queuelen);

/********
* FUNCIÓN:  accept_conexion(int sockfd, struct sockaddr_in client_addr, int addrlen)

* ARGS_IN: int sockfd 						- descriptor que identifica al socket abierto.
		   struct sockaddr_in client_addr   - estructura que sirve para guardar los datos del cliente que establece una conexión.
		   socklen_t addrlen				- tamaño de la estructura anterior.

* DESCRIPCIÓN: Espera a que le lleguen conexiones.

* ARGS_OUT: int - descriptor devuelto por accept(), que identifica la conexión con un cliente.
********/
int accept_conexion(int sockfd, struct sockaddr_in client_addr, socklen_t addrlen);

/********
* FUNCIÓN:  close_conexion(int clientfd)

* ARGS_IN: int clientfd - descriptor que identifica la conexión con un cliente.

* DESCRIPCIÓN: Cierra la conexión.

* ARGS_OUT: int - 0 si se ha cerrado correctamente la conexión, -1 en otro caso.
********/
int close_conexion(int clientfd);

/********
* FUNCIÓN:  void do_daemon(void)

* ARGS_IN: No tiene argumentos de entrada.

* DESCRIPCIÓN: Convierte el proceso que llama a esta funcion en un proceso daemon.

* ARGS_OUT: No devuelve nada.
********/
void do_daemon(void);

#endif /* CONEXION_H */
