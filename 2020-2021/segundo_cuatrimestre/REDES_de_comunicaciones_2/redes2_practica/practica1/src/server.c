/*****************************************************************
 Fichero que contiene el main del servidor.
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
#include <errno.h>
#include <time.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/select.h>
#include <syslog.h>
#include <locale.h>
#include <sys/stat.h> // Para stat().
#include <fcntl.h>    // Para open().
#include <wait.h>     // Para el wait().
#include <signal.h>   // Para capturar el Ctrl+C.

#include "conexion.h"
#include "confuse.h"
#include "procesa_conexion.h"


/* Parametros fichero configuracion. */
cfg_t *cfg = NULL;

static char *server_root = NULL;
static long int max_clients = 0;
static long int listen_port = 0;
static char *server_signature = NULL;

// child pid.
int cpid = 0;

// socket file descriptor opened.
int sockfd = 0;

/********
* FUNCIÓN: int clean_main(int ret, int sockfd)

* ARGS_IN: int ret      - retorno, EXIT_SUCCESS o EXIT_FAILURE
           int sockfd   - descriptor del socket abierto por el servidor para recibir conexiones.

* DESCRIPCIÓN: Cierra el socket, y libera la memoria de las variables para la configuración del servidor.

* ARGS_OUT: Devuelve un entero, ret.
********/
int clean_main(int ret, int sockfd)
{
    if (sockfd != 0)
        close(sockfd);
    if (server_root)
        free(server_root);
    if (server_signature)
        free(server_signature);
    return ret;
}

/********
* FUNCIÓN: void sig_handler(int signo)

* ARGS_IN: int signum  - el numero de la senial de interrupcion.

* DESCRIPCIÓN: Es el manejador de seniales. Cuando recive un SIGINT mata al proceso.

* ARGS_OUT: No devuelve nada.
********/
void sig_handler(int signum)
{
    if (signum == SIGINT) {
        // El padre espera a todos sus hijos.
        while(wait(NULL) > 0);

        // Cierra el sockfd solo si es el proceso padre.
        if(cpid) exit(clean_main(EXIT_SUCCESS, sockfd));
        else exit(clean_main(EXIT_SUCCESS, 0));
    }
}


/********
* FUNCIÓN: int main(void)

* DESCRIPCIÓN: Configura el servidor, abre un socket, acepta conexiones, y las procesa.

* ARGS_OUT: EXIT_SUCCESS si no hay ningun error, sino EXIT_FAILURE.
********/
int main(void)
{
    int clientfd = 0;
    struct sockaddr_in client_addr;
    socklen_t addrlen = sizeof(struct sockaddr_in);

    // Manejador de seniales para capturar el SIGINT.
    signal(SIGINT, sig_handler);

    // Configuracion del servidor usando la libreria libconfuse.
    cfg_opt_t opts[] = {
        CFG_SIMPLE_STR("server_root", &server_root),
        CFG_SIMPLE_INT("max_clients", &max_clients),
        CFG_SIMPLE_INT("listen_port", &listen_port),
        CFG_SIMPLE_STR("server_signature", &server_signature),
        CFG_END()};

    cfg = cfg_init(opts, 0);
    if (cfg_parse(cfg, "server.conf") == CFG_PARSE_ERROR)
    {
        syslog(LOG_ERR, "Error al parsear el fichero server.conf");
        cfg_free(cfg);
        return clean_main(EXIT_FAILURE, sockfd);
    }

    // Hemos cargado toda la configuracion del server.conf.
    cfg_free(cfg);

    // Comment - Uncomment for convert the process to daemon.
    do_daemon();

    // Abrimos el socket.
    sockfd = tcp_listen(listen_port, max_clients);
    if (sockfd < 0)
    {
        syslog(LOG_ERR, "Error al inicializar socket");
        return clean_main(EXIT_FAILURE, sockfd);
    }

    // Pool de procesos.
    for(int i = 0; i < max_clients-1; i++) {
        cpid = fork();
        if(!cpid) break; 
    }

    while(1) { // Se repite hasta recibir el SIGINT.
        // Aceptamos conexiones.
        clientfd = accept_conexion(sockfd, client_addr, addrlen);
        if (clientfd < 0)
        {
            syslog(LOG_ERR, "Error al atender conexión");
            return clean_main(EXIT_FAILURE, sockfd);
        }
        procesa_una_conexion(clientfd, server_root, server_signature);
    }
    
    // Un fork para cada conexion.
    /*while(1) { // Se repite hasta recibir el SIGINT.
        // Aceptamos conexiones.
        clientfd = accept_conexion(sockfd, client_addr, addrlen);
        if (clientfd < 0)
        {
            syslog(LOG_ERR, "Error al atender conexión");
            return clean_main(EXIT_FAILURE, sockfd);
        }

        cpid = fork();
        if(!cpid) {
            procesa_una_conexion(clientfd, server_root, server_signature);
            exit(clean_main(EXIT_SUCCESS, 0)); // Exit successfully without closing sockfd.
        }
    }*/

    // El padre espera a todos sus hijos.
    while(wait(NULL) > 0);

    return clean_main(EXIT_SUCCESS, sockfd);
}
