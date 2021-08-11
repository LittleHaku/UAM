/*****************************************************************
 Fichero que contiene la funcion procesa_una_conexion.
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
#include "picohttpparser.h"
#include "procesa_conexion.h"

#define HEADERS_SIZE 1024
#define BUFFER_SIZE 100000
#define DATA_SIZE 128


/* Parametros fichero configuracion. */
static char *server_root = NULL;
static char *server_signature = NULL;

/*FUNCIONES PRIVADAS*/

/********
* FUNCIÓN: void add_version(int version, char *buf_response, int *buf_response_len)

* ARGS_IN: int version                 - 0 ó 1 dependiendo de la versión HTTP.
           char *buf_response         - buffer en el que se escribirá la respuesta.
           int *buf_response_len     - entero donde se guardará el tamaño del buffer anterior.

* DESCRIPCIÓN: Escribe en el buffer de respuesta la versión HTTP, y guarda 
               la cantidad de bytes guardados en el buffer.

* ARGS_OUT: No devuelve nada.
********/
void add_version(int version, char *buf_response, int *buf_response_len)
{
    strcpy(buf_response, "HTTP/1.");
    buf_response[7] = version + '0';
    buf_response[8] = ' ';
    (*buf_response_len) = 9;
}


/********
* FUNCIÓN: void add_str(char *str, char *buf_response, int *buf_response_len)

* ARGS_IN: char *str                 - cadena que se quiere guardar en el buffer de respuesta.
           char *buf_response         - buffer en el que se escribirá la respuesta.
           int *buf_response_len     - entero donde se guardará el tamaño del buffer anterior.

* DESCRIPCIÓN: Escribe en el buffer de respuesta lo que se le pasa por el primer argumento de entrada, más el fin de línea.

* ARGS_OUT: No devuelve nada.
********/
void add_str(char *str, char *buf_response, int *buf_response_len)
{
    //Copia byte a byte lo que se le pasa por argumento al buffer de respuesta.
    for (int i = 0; i < (int)strlen(str); i++)
    {
        buf_response[*buf_response_len] = str[i];
        (*buf_response_len)++;
    }

    //Se añade el fin de linea.
    buf_response[*buf_response_len] = '\r';
    (*buf_response_len)++;
    buf_response[*buf_response_len] = '\n';
    (*buf_response_len)++;
}

/********
* FUNCIÓN: void add_str2(char *str1, char *str2, char *buf_response, int *buf_response_len)

* ARGS_IN: char *str1                - primera cadena que se quiere guardar en el buffer de respuesta.
           char *str2                - segunda cadena que se quiere guardar en el buffer de respuesta.
           char *buf_response        - buffer en el que se escribirá la respuesta.
           int *buf_response_len     - entero donde se guardará el tamaño del buffer anterior.

* DESCRIPCIÓN: Escribe en el buffer de respuesta lo que se le pasa por los dos primeros argumentos de entrada.

* ARGS_OUT: No devuelve nada.
********/
void add_str2(char *str1, char *str2, char *buf_response, int *buf_response_len)
{
    //Copia byte a byte la primera cadena que se pasa por argumento al buffer respuesta.
    for (int i = 0; i < (int)strlen(str1); i++)
    {
        buf_response[*buf_response_len] = str1[i];
        (*buf_response_len)++;
    }

    //Se copia la segunda llamando a la funcion add_str.
    add_str(str2, buf_response, buf_response_len);
}

/********
* FUNCIÓN: void add_content_type(char *type_data, char *buf_response, int *buf_response_len, int strlen(type_data))

* ARGS_IN: char *type_data             - extensión del fichero solicitado en la petición.
           char *buf_response         - buffer en el que se escribirá la respuesta
           int *buf_response_len     - entero donde se guardará el tamaño del buffer anterior.

* DESCRIPCIÓN: Escribe en el buffer de respuesta el content-type.

* ARGS_OUT: No devuelve nada.
********/
void add_content_type(char *type_data, char *buf_response, int *buf_response_len)
{
    //Los scripts muestran la ejecucion como un texto plano, por eso el content-type es text/plain
    if (strncmp(type_data, "txt", strlen(type_data)) == 0 || strncmp(type_data, "py", strlen(type_data)) == 0 || strncmp(type_data, "php", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "text/plain", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "html", strlen(type_data)) == 0 || strncmp(type_data, "htm", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "text/html", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "gif", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "image/gif", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "jpeg", strlen(type_data)) == 0 || strncmp(type_data, "jpg", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "image/jpeg", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "mpeg", strlen(type_data)) == 0 || strncmp(type_data, "mpg", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "video/mpeg", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "doc", strlen(type_data)) == 0 || strncmp(type_data, "docx", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "application/msword", buf_response, buf_response_len);
    }
    else if (strncmp(type_data, "pdf", strlen(type_data)) == 0)
    {
        add_str2("Content-Type: ", "application/pdf", buf_response, buf_response_len);
    }
}

/********
* FUNCIÓN: void add_object(char *str, char *argumentosPost, char *argumentosGet, char *buf_response, int *buf_response_len, char *type_data)

* ARGS_IN: char *str                 - ruta del fichero solicitado en la petición.
           char *argumentosPost     - argumentos del body para ejecutar un script.
           char *argumentosGet         - argumentos de la URL para ejecutar un script.
           char *buf_response         - buffer en el que se escribirá la respuesta
           int *buf_response_len     - entero donde se guardará el tamaño del buffer anterior.
           char *type_data            - extensión del fichero pedido, para identificar si es un script.

* DESCRIPCIÓN: Si el primer argumento de entrada es NULL, se envia una respuesta sin body;
               y sino, se mira si es un script. Si no lo es, se abre el fichero y se lee el contenido del mismo;
               y si lo es, se monta el comando para ejecutar el script, y después leer el contenido de la salida de la ejecución del mismo.

* ARGS_OUT: No devuelve nada.
********/
void add_object(char *str, char *argumentosPost, char *argumentosGet, char *buf_response, int *buf_response_len, char *type_data)
{
    char  comando[DATA_SIZE] = "\0";
    FILE *pf = NULL;
    char buffer[BUFFER_SIZE] = "\0";
    int file_bytes;

    // Crear un body vacio.
    if (str == NULL)
    {
        add_str2("Content-Type: ", "text/plain", buf_response, buf_response_len);
        add_str2("Content-Length: ", "0", buf_response, buf_response_len);
        buf_response[*buf_response_len] = '\r';
        (*buf_response_len)++;
        buf_response[*buf_response_len] = '\n';
        (*buf_response_len)++;
        return;
    }

    //Si es un script, se ejecuta.
    if (strncmp(type_data, "py", strlen(type_data)) == 0 || strncmp(type_data, "php", strlen(type_data)) == 0) {

        // Obtenemos el comando para ejecutar el script.
        if (strncmp(type_data, "py", strlen(type_data)) == 0) {
            if (argumentosGet && argumentosPost) 
            {
                sprintf(comando, "echo | python3 %s%s %s", str, argumentosGet, argumentosPost);
            }
            else if (argumentosGet) 
            {
                sprintf(comando, "echo | python3 %s%s", str, argumentosGet);
            }
            else
            {
                sprintf(comando, "echo | python3 %s %s", str, argumentosPost);
            }
        }
        else if (strncmp(type_data, "php", strlen(type_data)) == 0) {
            if (argumentosGet && argumentosPost) 
            {
                sprintf(comando, "echo | php %s%s %s", str, argumentosGet, argumentosPost);
            }
            else if (argumentosGet) 
            {
                sprintf(comando, "echo | php %s%s", str, argumentosGet);
            }
            else
            {
                sprintf(comando, "echo | php %s %s", str, argumentosPost);
            }
        }

        //Ejecutamos el script
        pf = popen(comando, "r");
        if(!pf){
            syslog(LOG_ERR,"Error creando tuberia\n");
            return ;
        }

        char line[DATA_SIZE] = "\0";

        //Leemos la ejecucion
        while (fgets(line, DATA_SIZE, pf) != NULL)
        {
            strcat(buffer, line);
        }

        //Se añade a la respuesta el Content-Length
        file_bytes =  strlen(buffer);
        sprintf(line, "%d", file_bytes);
        add_str2("Content-Length: ", line, buf_response, buf_response_len);
     
        pclose(pf);

    }
    else {
        //Si no es un script, se abre el fichero directamente, y se lee el contenido.
        pf = fopen(str, "rb");
        if(!pf)
        { 
            syslog(LOG_ERR, "Error abriendo fichero");
            return ;
        }

        fseek(pf, 0, SEEK_END);

        // Longitud del documento pedido, en Bytes.
        file_bytes = ftell(pf);
        fseek(pf, 0, SEEK_SET);

        // Convierte file_bytes a string y luego se usa como buffer.
        sprintf(buffer, "%d", file_bytes);

        //Se añade el Content-Length a la respuesta.
        add_str2("Content-Length: ", buffer, buf_response, buf_response_len);

        fread(buffer, file_bytes, 1, pf);

        fclose(pf);
    }

    //Se añade el Content-Type a la respuesta.
    add_content_type(type_data, buf_response, buf_response_len);
    

    // End of HTTP header.
    buf_response[*buf_response_len] = '\r';
    (*buf_response_len)++;
    buf_response[*buf_response_len] = '\n';
    (*buf_response_len)++;

    for (int i = 0; i < file_bytes; ++i)
    {
        // Guarda cada byte leido en el buffer de salida.
        buf_response[*buf_response_len] = buffer[i];
        (*buf_response_len)++;
    }    
}


/*FUNCIÓN PÚBLICA*/
void procesa_una_conexion(int clientfd, char * _server_root, char * _server_signature)
{
    int code_response = 0, j = 0, k = 0;
    const char buf_request[BUFFER_SIZE] = "\0", *method = NULL, *path = NULL;
    char real_path[DATA_SIZE], date[DATA_SIZE] = "\0", buffer_aux[BUFFER_SIZE] = "\0";
    size_t method_len = 0, path_len = 0, num_headers = 0, prev_buffer_len = 0, buffer_len = 0;
    int http_version = 0, request_len = 0, rret = 0, i = 0;
    struct phr_header headers[HEADERS_SIZE]; // De Picohttpparser.
    time_t right_now; // Para el Date:
    struct stat attrib; // Para el Last-Modified:
    char *aux_args = NULL, body_aux[BUFFER_SIZE], path_not_real[DATA_SIZE] = "\0", aux_path[DATA_SIZE] = "\0", *direction = NULL;
    char *argumentosPost = NULL;
    char *argumentosGet = NULL;
    char *body = NULL;

    char buf_response[BUFFER_SIZE] = "\0";
    int buf_response_len = 0;

    char *type_data = NULL, aux[BUFFER_SIZE]= "\0";

    server_root = _server_root;
    server_signature = _server_signature;
    
    // Mientras la conexion este abierta:
    while (1)
    {
        //Reseteo de las variables para la siguiente peticion.
        buf_response[0] = 0;
        buf_response_len = 0;
        argumentosPost = NULL;
        argumentosGet = NULL;
        type_data = NULL;
        aux[0] = 0;
        aux_args = NULL;
        body_aux[0] = 0;
        path_not_real[0] = 0;
        direction = NULL;
        body = NULL;
        buffer_aux[0] = 0;
        aux_path[0] = 0;

        // Documentation in https://github.com/h2o/picohttpparser/blob/master/README.md
        // Se lee del descriptor de fichero.
        buffer_len = 0; // Reset del buffer.
        while ((rret = read(clientfd, (void *)(buf_request + buffer_len), sizeof(buf_request) - buffer_len)) == -1 && errno == EINTR)
            ;

        // Se han leido 0 bytes, el cliente ha cerrado la conexion.
        if(!rret) break;

        close(rret);

        prev_buffer_len = buffer_len;
        buffer_len += rret;

        // Se parsea la request.
        num_headers = sizeof(headers) / sizeof(headers[0]);
        request_len = phr_parse_request((char *)buf_request, buffer_len, (const char **)&method, &method_len, (const char **)&path,
                                        &path_len, &http_version, (struct phr_header *)headers, &num_headers, prev_buffer_len);

        //Se añade la version a la respuesta.
        add_version(http_version, buf_response, &buf_response_len);

        // Get the code of the response.
        code_response = -1;

        // Si la peticion esta mal formada, se añade el codigo 400 a la respuesta.
        if (request_len <= 0)
            add_str("400 Bad Request", buf_response, &buf_response_len), code_response = 400;


        if (path_len == 1)
        {
            // Si la longitud de la ruta es 1, se trata del directorio raiz, y por lo tanto el archivo es el index.html
            sprintf(real_path, "%s/index.html", server_root);
        }
        else
        {    
            //Se obtiene la ruta del archivo solicitado.
            strncpy(aux_path, path, path_len);

            direction = strtok(aux_path, "?");

            sprintf(real_path, "%s%.*s", server_root, (int)path_len, direction);
            direction = NULL;
        }

        //Se abre la ruta donde esta el fichero y se comprueba si existe o no, y en funcion de ello, tendra un codigo u otro
        int test_fd = open(real_path, O_RDONLY);

        // Si no se encuentra el archivo, el codigo es 404, y se añade a la respuesta.
        if (code_response == -1 && test_fd < 0)
        {
            add_str("404 Not Found", buf_response, &buf_response_len);
            code_response = 404;
        }

        //Si no ha habido ningun error, el codigo es 200, y se añade a la respuesta.
        if (code_response == -1)
        {
            add_str("200 OK", buf_response, &buf_response_len);
            code_response = 200;
            close(test_fd); //ya que aquí el descriptor existe
        }

        //Se añade a la respuesta la informacion del servidor que contesta a la peticion.
        add_str2("Server: ", server_signature, buf_response, &buf_response_len);

        //Se obtiene la fecha a la que se crea la respuesta,  y se añade a la respuesta.
        time(&right_now);
        strftime(date, DATA_SIZE, "%a, %d %b %Y %H:%M:%S GMT", localtime(&right_now));
        add_str2("Date: ", date, buf_response, &buf_response_len);

        //Si el archivo existe, se obtiene la ultima modificacion del mismo, y se añade a la respuesta.
        if(code_response != 404) {
            stat(real_path, &attrib);
            date[0] = 0;
            strftime(date, DATA_SIZE, "%a, %d %b %Y %H:%M:%S GMT", localtime(&attrib.st_ctime));
            add_str2("Last-Modified: ", date, buf_response, &buf_response_len);
        }


        // Vemos si la peticion es POST, GET u OPTIONS.
        if (strncmp(method, "OPTIONS", method_len) == 0)
        {
            //Si es OPTIONS se añade a la respuesta las opciones sin mostrar nada mas.
            add_str2("Allow: ", "GET, POST, OPTIONS", buf_response, &buf_response_len);
            add_object(NULL, NULL, NULL, NULL, NULL, NULL);
        }
        else if (strncmp(method, "GET", method_len) == 0)
        {
            if (code_response != 200)
            {
                if (code_response == 404)
                {
                    //Cuando no se ha encontrado el fichero (codigo 404), aparecera una imagen indicando que no se ha encontrado

                    //Para que aparezca la imagen, hay que indicarle la ruta
                    strncpy(path_not_real, server_root, strlen(server_root));
                    strcat(path_not_real, "/404.jpg");
                    strncpy(aux, path_not_real, strlen(path_not_real));

                    //Para llamar a add_object se requiere del tipo de extension
                    type_data = strtok(aux, ".");
                    type_data = strtok(NULL, ".");

                    add_object(path_not_real, NULL, NULL, buf_response, &buf_response_len, type_data);
                }
                else 
                {
                    //Si se trata de otro codigo, el servidor no muestra nada.
                    add_object(NULL, NULL, NULL, NULL, NULL, NULL);
                }
            }
            else
            {
                strncpy(aux, real_path, DATA_SIZE); //strtok se carga la cadena, asi que la copiamos en una cadena auxiliar

                //Obtenemos la extension del archivo
                type_data = strtok(aux, ".");
                type_data = strtok(NULL, "?");

                //Si se trata de un script, obtenemos los argumentos para ejecutarlo.
                if (strncmp(type_data, "py", strlen(type_data)) == 0 || strncmp(type_data, "php", strlen(type_data)) == 0 )
                {
                    //A partir de la URL obtenemos los argumentos para el GET.
                    strncpy(aux_path, path, path_len);

                    argumentosGet = (char*)malloc(DATA_SIZE*sizeof(char));
                    if (!argumentosGet) {
                        return;
                    }

                    argumentosGet[0] = 0;
                    aux_args = NULL;

                    strtok(aux_path, "=");

                    k = 0; // Numero de argumentos leidos.

                    //Leemos los argumentos  y los guardamos.
                    while(aux_args != NULL || k == 0) 
                    {
                        if(aux_args != NULL)
                            strcat(argumentosGet, aux_args);

                        strcat(argumentosGet, " ");

                        aux_args = strtok(NULL, "&");
                        strtok(NULL, "=");
                        k++;
                    }
                }
                
                add_object(real_path, argumentosPost, argumentosGet, buf_response, &buf_response_len, type_data);       
                
                if (argumentosGet)
                    free(argumentosGet);        
            }
        }
        else if (strncmp(method, "POST", method_len) == 0)
        {
            if (code_response != 200)
            {
                if (code_response == 404)
                {
                    //Cuando no se ha encontrado el fichero (codigo 404), aparecera una imagen indicandote que no se ha encontrado

                    //Para que aparezca la imagen, hay que indicarle la ruta
                    strncpy(path_not_real, server_root, strlen(server_root));
                    strcat(path_not_real, "/404.jpg");
                    strncpy(aux, path_not_real, strlen(path_not_real));

                    //Para llamar a add_object se requiere del tipo de extension
                    type_data = strtok(aux, ".");
                    type_data = strtok(NULL, ".");

                    add_object(path_not_real, NULL, NULL, buf_response, &buf_response_len, type_data);
                }
                else 
                {
                    //Si se trata de otro codigo, el servidor no muestra nada.
                    add_object(NULL, NULL, NULL, NULL, NULL, NULL);
                }
                
            }
            else
            {    
                strncpy(aux, real_path, strlen(real_path)); //strtok se carga la cadena, asi que la copiamos en una cadena auxiliar

                //Obtenemos la extension del archivo
                type_data = strtok(aux, ".");
                type_data = strtok(NULL, "?");

                //Si se trata de un script, obtenemos antes los argumentos para ejecutarlo.
                if (strncmp(type_data, "py", strlen(type_data)) == 0 || strncmp(type_data, "php", strlen(type_data)) == 0 )
                {
                    //Guardamos en una variable auxiliar el contenido de la peticion
                    strncpy(buffer_aux, buf_request, buffer_len);

                    //Nos saltamos la linea de comando
                    body = strchr(buffer_aux, '\n');

                    //Pasamos el numero de cabeceras de long a int para iterar sobre ello
                    int num_head = num_headers;

                    //Saltamos la cabera para llegar al body
                    direction = strtok(body, "\n");
                    for (j = 0; j < num_head + 1; j++) {
                        direction = strtok(NULL, "\n");
                    }

                    strncpy(body_aux, direction, BUFFER_SIZE);

                    //Guardamos los argumentos
                    argumentosPost = (char*)malloc(DATA_SIZE*sizeof(char));
                    if (argumentosPost == NULL) {
                        return;
                    }

                    argumentosPost[0] = 0;
                    aux_args = NULL;

                    strtok(body_aux, "=");

                    k = 0; // Numero de argumentos leidos.

                    //Leemos los argumentos  y los guardamos.
                    while(aux_args != NULL || k == 0)
                    {
                        if(aux_args != NULL)
                            strcat(argumentosPost, aux_args);
                        strcat(argumentosPost, " ");

                        aux_args = strtok(NULL, "&");
                        strtok(NULL, "=");
                        k++;
                    }
                    
                    //Como estamos en POST, miramos si tiene argumentos en la URL
                    strncpy(aux_path, path, path_len);

                    argumentosGet = (char*)malloc(BUFFER_SIZE*sizeof(char));
                    if (!argumentosGet) {
                        if (argumentosPost)
                            free(argumentosPost);
                        return;
                    }


                    argumentosGet[0] = 0;
                    aux_args = NULL;

                    strtok(aux_path, "=");

                    k = 0;// Numero de argumentos leidos.

                    //Leemos los argumentos  y los guardamos.
                    while(aux_args != NULL || k == 0) 
                    {
                        if(aux_args != NULL)
                            strcat(argumentosGet, aux_args);
                        strcat(argumentosGet, " ");

                        aux_args = strtok(NULL, "&");
                        strtok(NULL, "=");
                        k++;
                    }
                }

                add_object(real_path, argumentosPost, argumentosGet, buf_response, &buf_response_len, type_data);

                if (argumentosPost)
                    free(argumentosPost);

                if (argumentosGet)
                    free(argumentosGet);
            }
        }

        if (write(clientfd, buf_response, buf_response_len) < 0)
        {
            syslog(LOG_ERR, "Error enviando la respuesta");
            return;
        }

        for (i = 0; i < (int)num_headers; i++)
        {
            if (strncmp(headers[i].name, "close", (int)headers[i].name_len) == 0)
            {
                // Significa que se tiene que cerrar la conexion.
                http_version = 0;
                break;
            }
        }
        // If http_version = 0 or http_version = 1 and Connection: close.
        if (http_version == 0)
            break;
    }

    if (close_conexion(clientfd) < 0)
    {
        syslog(LOG_ERR, "Error al cerrar conexión");
        return;
    }
}
