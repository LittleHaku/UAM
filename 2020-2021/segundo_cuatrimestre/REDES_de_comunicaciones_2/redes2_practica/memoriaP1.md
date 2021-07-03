## SERVIDOR WEB

1. **Introducción**

En esta primera práctica, se ha implementado un servidor web, que responde a peticiones de tipo: GET, POST y OPTIONS, configurable a través de un fichero de configuración y da soporte para la ejecución de scripts. En los siguientes apartados, se explicarán los detalles técnicos de la implementación, así como las decisiones de diseño y las dificultades a la que nos hemos enfrentado.

2. **Implementación y Diseño**

Primeramente, se creó un servidor iterativo *echo* básico, y se fue modificando para que soportara más características. Para ello, se creó una librería con todo lo relacionado con las conexiones, llamada *conexion.a*. Una vez que todo funcionaba bien, se probaron servidores concurrentes de distintos tipos. Tras ejecutar los distintos servidores y midiendo los tiempos, se llegó a la conclusión de que el servidor pool de procesos, en el que primero se crean los procesos hijos y luego cada hijo se pone a manejar una conexión, era el más eficiente.

A la hora de desarrollar el protocolo, se empezó con el fichero de configuración, para el que se usó la librería *libconfuse*, luego se usó la librería *picohttpparser.h* para parsear las peticiones HTTP. Una vez que se entendió como funcionaba esta librería, se implementaron los métodos OPTIONS, y GET. Para comprobar que funcionaba, probamos primero que devolvía texto plano a un cliente, en nuestro caso fue Firefox. Más adelante, terminamos la funcionalidad implementando el método POST, así como la ejecución de scripts. 

Una vez que la funcionalidad pedida funcionaba como se quería, se nos ocurrió que cada vez que no se encontraba un objeto (404), se mostrase una imagen de error personalizada como ocurre en la vida real.

3. **Ficheros entregados y estructura**

La estructura sigue el estándar de un servidor o de un proyecto. 

- Carpeta htmlfiles, donde se almacenan los ficheros que se van a compartir al cliente. Esto son "index.html", archivos como imágenes o de texto, y una carpeta de scripts para probarlos.

- Carpeta includes, donde se guardan los .h.

- Carpeta lib, donde se han creado dos librerías, una *conexion.a*, donde hay llamadas genéricas sobre las conexiones entre dos terminales, reutilizables para otros proyectos; y *picohttpparser.a*, donde está la librería de picohttpparser.

- Archivo makefile, necesario para la automatización de la compilación y ejecución del programa.

- Carpeta obj, donde se guardan los ficheros objeto (.o) creados por el makefile en la compilación.

- Fichero server, será el ejecutable del servidor, y generado por el makefile.

- Fichero server.conf, donde se configuran parámetros del servidor como puede ser el número máximo de clientes concurrentes.

- Carpeta src, donde está almacenado el código fuente de nuestro proyecto, *procesa_conexion.c* y *server.c*.

- Carpeta srclib, donde está el código fuente necesario para las librerías, en nuestro caso, *conexion.c* y *picohttpparser.c*.

- Archivo memoria.md, que es este fichero, una memoria en Markdown.

4. **Prueba con el index.html y ejemplo de ejecución**

Para poner en marcha el servidor, se puede hacer con el comando *make run*, o si se quiere ejecutar con valgrind, con el comando *make runv*, tras haber ejecutado el comando make. Una vez que se ha puesto en marcha, para comprobar las funcionalidades del servidor, habrá que conectarse desde un cliente navegador. Por ejemplo, accediendo desde Firefox, basta con ir a la URL "0.0.0.0:8080" y el servidor te redirigirá a la página de index.html. 

5. **Características requeridas**

Primeramente, el servidor soporta tanto la versión HTTP 1.0 como la 1.1, y actúa en función de esta. Si la versión es 1.0, el servidor cierra la conexión tras enviar la respuesta, y si es 1.1, se busca si hay un campo en la cabecera de la petición en la que ponga explícitamente que se cierre la conexión (Connection: close), si la hay, se cierra la conexión como si fuese 1.0. 

Por otro lado, el servidor puede devolver tres tipos de código: 200 si todo va correcto, 400 si la petición está formulada de forma incorrecta, y 404 si el fichero pedido no existe. 

En cuanto al Content-Type, que sirve para que el cliente sepa como mostrar el archivo, el servidor soporta las siguientes extensiones:

    text/plain: .txt
    text/html: .html, .htm
    image/gif: .gif
    image/jpeg: .jpeg, .jpg
    video/mpeg: .mpeg, .mpg
    application/msword: .doc, .docx
    application/pdf: .pdf

Además, a parte de la versión, el código de respuesta, y el tipo de archivo que devuelve, en la cabecera de respuesta siempre se añaden los siguientes campos:

- Date: con el formato según el estándar.

- Server: nombre y versión de vuestro servidor.

- Last-Modified: fecha de última modificación del recurso solicitado.

- Content-Length: longitud del recurso solicitado, en bytes.

Por último, el servidor es capaz de ejecutar los del tipo .py y .php, tanto si los argumentos solo están en la URL (método GET), como si están en el *body* (método POST) como si están en la URL y en el *body* (método GET-POST). Además, el usuario puede introducir texto y ejecutar el script, cuyo argumento a imprimir es el texto que ha escrito el usuario.

6. **Características adicionales**

Estas son algunas de las características que no se pedían explícitamente pero que hemos pensado que era buena idea añadirlas:

- Creación de una página index.html para comprobar que todas las características funcionasen correctamente.

- Redirección de la URL "0.0.0.0:8080" a la "0.0.0.0:8080/index.html", para no tener que teclear "/index.html".

- Un timestamp en JavaScript al final del index.html para calcular cuánto tiempo tarda la página en cargar (podemos comprobar que en Google Chrome tarda mucho menos que en Firefox, debido a que usan muchos cachés).

- Un input para que el usuario pueda ejecutar un script con el texto que él mismo haya escrito.

- Un manejador de señales, para que capture la señal SIGINT y cierre todos los procesos.

- Un conversor a proceso daemon, el cual convierte el servidor en un proceso daemeon, por lo que se puede cerrar la terminal y el servidor se sigue ejecutando.

- Una redirección a un 404 personalizado, el cual si accedes a una URL del dominio del servidor pero no existe ese fichero, devuelve una imagen de 404.jpg personalizada.

7. **Pruebas realizadas**

- Pruebas de cliente: Desde Firefox, mediante un index.html creado, se iban comprobando todas las características pedidas, que funcionase el GET, POST, el error 400, que se descargasen bien las imágenes, el gif, que se ejecutasen correctamente los scripts...

- Pruebas de memoria: Con Valgrind se iban comprobando todas las partes y funciones del código para que no existieran fugas de memoria o accesos indebidos de memoria.

- Una prueba de estrés: Se creó un cliente (Un script en bash) que pedía 4000 veces el documento "0.0.0.0:8080/hello.jpeg", y se comprobó que el servidor no se caía sino que iba devolviendo la imagen correctamente.

8. **Opinión personal de la práctica**

Personalmente, creemos que la práctica ha sido una buena manera de entender cómo funciona un servidor en detalle, incluyendo, entre muchas cosas, el tipo de peticiones que los clientes hacen, y cómo las tiene que manejar el propio servidor. Además, hemos podido aplicar los conocimientos aprendidos en teoría, así como que se ha comprobado empíricamente que no es difícil hacer un servidor sencillo, y que puedes crearte uno localmente. También se ha profundizado en cómo funciona la creación, el uso y la liberación de los recursos de los sockets.

Antes de llegar a la comprensión y aprendizaje del que hablamos, hemos tenido algunas dificultades en relación a saber qué era lo que teníamos que hacer, ya que el enunciado era bastante general y no teníamos claro qué era lo que se iba a evaluar o lo que se pedía. Pero poco a poco, fuimos construyendo el servidor y completando los requisitos que pedían.
