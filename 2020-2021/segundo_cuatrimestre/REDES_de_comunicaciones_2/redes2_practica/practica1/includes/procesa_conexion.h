/*****************************************************************
 Fichero que contiene las funciones para establecer una conexion http.
 Autores: 
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
******************************************************************/

#ifndef PROCESA_CONEXION_H
#define PROCESA_CONEXION_H

/********
* FUNCIÓN: void procesa_una_conexion(int clientfd)

* ARGS_IN: int clientfd - descriptor de una conexión por parte de un cliente.

* DESCRIPCIÓN: Procesa todas las peticiones que el cliente solicita por esa conexión, y la cierra si el cliente se lo
               indica.

* ARGS_OUT: No devuelve nada.
********/
void procesa_una_conexion(int clientfd, char * _server_root, char * _server_signature);

#endif /* PROCESA_CONEXION_H */
