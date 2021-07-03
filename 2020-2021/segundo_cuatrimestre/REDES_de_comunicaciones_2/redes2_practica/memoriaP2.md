# MEMORIA

### 1. INTRODUCCIÓN

En esta segunda práctica se ha implementado el cliente del servicio SecureBox en python. Mediante este cliente se puede: crear una identidad, borrar la identidad, subir ficheros, descargar ficheros, así como cifrar y firmar archivos, todo ello a través de un token asociado a cada usuario. La gracia de esta práctica es que el contenido de los ficheros no pueda ser leído (y comprendido) en caso de ser interceptado, y para ello, se cifran y firman los ficheros antes de subirlos. Por esta regla, al descargarlos, como están cifrados, habrá que descifrarlos para obtener el contenido en claro, pero además verificar si es de la persona que dice que lo ha mandado usando una firma digital.

### 2. PAQUETES NECESARIOS
En la práctica, se han usado los siguientes paquetes:

- **requests**, para realizar las peticiones a la API.
- **pycriptodome**, para cifrar y descifrar.
- **argparser**, para el manejo de los argumentos de entrada.

Es importante que si no se tienen descargados, se deben instalar con pip3.

### 3. IMPLEMENTACIÓN

La práctica se puede dividir en dos partes fundamentales: la parte de cifrado y descifrado, y los comandos. Por un lado, las funciones de cifrar, firmar y descifrar se encuentran en el fichero *libreria_secure_box.py*, mientras que la administración de los comandos está implementada en el *cliente.py*. 

La parte de cifrado y descifrado fue la primera que se implementó. Para su implementación se han usado los paquetes de python: **Crypto.Signature**, **Crypto.Cipher**, **Crypto.Random**, **Crypto.PublicKey**, **Crypto.Hash** y **Crypto.Util.Padding**, que son necesarios para poder ejecutar el programa. Se han implementado tres funciones: *cifrar_fichero*, *firmar_fichero* y *descifrar_mensaje*, que cumplen los requisitos de modo de cifrado, así como los tamaños de clave y vector de inicialización.

Es importante destacar que como para firmar es imprescindible la clave privada del emisor, cuando se generan las claves RSA en la creación de la identidad del usuario, la clave privada se guarda en el fichero *private.pem*, y de esta manera, cuando hay que firmar algún fichero, la clave se recupera de ese fichero. La clave pública no hace falta guardarla ya que se puede conseguir tanto haciendo una petición a la API como desde la clave privada.

En *libreria_secure_box.py* también están las funciones *generar_fichero* que crea un fichero con el contenido dado, y *get_public_key*, que dado un ID haciendo una request a la API te devuelve su clave pública.

Una vez que nos aseguramos de que funcionaba correctamente esta primera parte, se empezaron a desarrollar los distintos comandos. Para esta parte se ha usado, por un lado, el paquete **argparse** para parsear los argumentos, y por otro lado, para enviar las peticiones al servidor se ha usado el paquete **requests**. En general, las peticiones están formadas por: una ruta, que se forma concatenando 'https://vega.ii.uam.es:8080/api' con la dirección adecuada en función de la petición; una cabecera, en la que se incluye el *Content-Type*, el *Content-Length*, y la *Authorization*, en la que se indica el token del usuario que realiza la petición; y el body en caso de que sea necesario. 

Las respuestas obtenidas de las peticiones, se convierten a **json**, de manera que se pueda acceder a los campos de la respuesta fácilmente. Esto no ocurre en el caso de la descarga de un fichero, ya que la respuesta en este caso es el binario del fichero directamente. 

### 4. ESTRUCTURA

La práctica se ha dividido en tres carpetas: 

- **files**: en esta carpeta se encuentran los archivos del usuario propios, los que se ha descargado, los que ha cifrado y los que ha firmado.
- **keys**: contiene:
	- *private.pem* contiene la clave privada del usuario generada,
	- *token.txt* contiene el token del usuario. De esta manera si el usuario 	
			cambia de token, solo ha de cambiarlo en ese fichero.
- **src**: contiene el código de la práctica, que como se ha mencionado está repartido en dos .py.

### 5. COMANDOS Y EJECUCIÓN

En esta práctica se han implementado un total de 10 comandos, cuya implementación y ejecución se comentarán en esta sección de la práctica. Es importante mencionar que todas las peticiones realizadas son de tipo **POST**, excepto la que se envía en el comando **lista_files** (que es de tipo GET, ya que al no pasar argumentos es más eficiente). Para ejecutar los comandos se indica: **python3 cliente.py argumentos**, donde **argumentos** se sustituye por alguno de los puntos que se van a explicar.

Por un lado, se tienen los comandos asociados a la gestión de usuarios e identidades: 

- **--create_id nombre email (alias)**: este comando sirve para crear una nueva identidad asociada a un token. En este caso, primero se generan unas claves pública y privada RSA, cuya privada se guarda en un *.pem*, para poder firmar ficheros. Tras la creación de las claves, se distinguen dos casos, uno en el que el usuario no ha insertado un alias, y otro, en el que sí (Se añade como un argumento más en el body). Si la respuesta es válida, es decir, el código es 200, se imprime por pantalla que se ha creado la identidad correctamente. En este caso, a la ruta raíz mencionada en el apartado 2, se le concatena '/users/register'.

- **--search_id cadena**: este comando sirve para buscar usuarios a partir de una cadena de caracteres que puede estar incluida tanto en el nombre como en el email como en el alias de un usuario. En este caso, en el body se incluye *cadena* como **data_search**. De la respuesta se obtienen los usuarios encontrados, y es lo que se imprime por pantalla para informar al usuario que ejecuta el comando. En este caso, a la ruta raíz mencionada en el apartado 2, se le concatena '/users/search'.

- **--delete_id ID**: este comando borra la identidad del usuario que ejecuta el comando. En el body de la petición se incluye el ID pasado por argumento como **userID**, y en este caso, a la ruta raíz mencionada en el apartado 2, se le concatena '/users/delete'.

Por otro lado, tenemos los comandos asociados al cifrado de los ficheros.

- **--encrypt fichero --dest_id ID**: este comando se encarga de cifrar el fichero local pasado por argumento con la clave pública del usuario con ID, también pasado por argumento, y guardarlo en otro fichero localmente. En este caso, se necesita obtener la clave pública del usuario con el ID pasado por argumento, y para ello, se hace una petición, en la que la ruta es concatenar a la ruta raíz 'users/getPublicKey', y donde el body está formado por el ID pasado por argumento como **user_id**. Una vez que se tiene la clave pública, se cifra con ella llamando a la función que se encarga de ello.

- **--sign fichero**: este comando sirve para firmar el fichero pasado por argumento. Para ello, se leerá la clave privada del fichero *private.pem*, y con ella se firma el fichero llamando a la función que se encarga de ello.

- **--enc_sign fichero --dest_id ID**: este comando es una combinación de los dos anteriores. Primero, se firma el fichero como se ha indicado en el comando anterior, y después, se cifra la firma concatenada con el contenido del fichero con una clave de sesión simétrica. Esta última se cifra con la clave pública del usuario, cuyo ID se pasa por argumento, generando el sobre digital. Y se concatenan el cifrado de la firma con el mensaje, y el sobre digital, y todo eso es el fichero firmado y cifrado.

Y por último, los comandos relacionados con la gestión de los ficheros.

- **--upload_file fichero --dest_id ID**: este comando manda al usuario, cuyo ID se pasa por argumento, el fichero, también pasado por argumento, cifrado y firmado por el usuario que ejecuta el comando. Primero, firmamos y ciframos el fichero como se indica en el último comando de cifrado de ficheros, y después, se sube el fichero con la petición **POST**, en la que en el body se incluye el descriptor de fichero como **ufile**. Si el código de respuesta es 200, todo ha ido bien, se ha enviado el fichero, y se muestra por pantalla el ID del fichero enviado. La ruta de la petición en este caso se consigue concatenando la ruta raíz con '/files/upload'.

- **--download_file file_ID --source_id ID**: este comando descarga un fichero firmado y cifrado por el usuario cuyo **ID** se pasa por argumento. Primero se hace una petición en la que el body incluye el **file_ID** pasado por argumento. La respuesta es el contenido en binario del fichero firmado y cifrado, por lo que primero se descifra la clave de sesión, y una vez que se obtiene esta, se descifra el mensaje y la firma. Se comprueba que realmente el que lo envía es el usuario cuyo **ID** se pasa por argumento. En este caso, la ruta para la petición es la concatenación de la ruta raíz con '/files/download'.

- **--delete_file file_ID**: este comando borra un fichero que pertenece al usuario que ejecuta el comando. En el body de la petición se incluye el ID del fichero, pasado por argumento como **file_id**. Además, la ruta se consigue concatenando la ruta raíz con '/files/delete'.

- **--list_files**: este comando saca por pantalla los ficheros subidos por el usuario que lo ejecuta. A diferencia de los demás, este no tiene ningún argumento, por lo que no tiene un body, es decir, se hace una petición **GET** para obtener los ficheros. La respuesta es una lista con los ID de los ficheros pertenecientes al usuario junto con el nombre de los ficheros.

### 6. PRUEBAS REALIZADAS
Se han realizado una serie de pruebas para comprobar toda la funcionalidad pedida. Una de las pruebas consistió en ir llamando y depurando cada comando que se implementaba. Si el comando requería de una petición, se iba llamando a la API con distintos valores como argumentos. Otra prueba realizada para la comprobación de que la subida y bajada de ficheros iba bien fue subir un fichero con *dest_id* igual a nuestro propio NIA, y descargarlo con el *source_id* igual a nuestro NIA. Para comprobar la compatibilidad con otros compañeros, también se ha subido un fichero con *dest_id* igual al NIA de otro miembro de otra pareja, y la otro miembro de esa otra pareja a nosotros, y cada uno se ha descargado el fichero que se le envió correctamente. Primero enviamos un texto normal, y luego una foto. 

Como nota, si el fichero que nos han enviado se trata de una foto, hay que cambiar la extensión del fichero que se genera con el contenido descifrado a la extensión del fichero que nos han mandado (.jpg, .jpeg, etc.), ya que, por defecto, en nuestra práctica, tiene la extensión .txt. Esto sucede porque no se puede leer el nombre del fichero con una petición a la API.

### 7. OPINION PERSONAL DE LA PRÁCTICA
En nuestra opinión, esta práctica ha resultado ser muy instructiva, así como interesante. Por un lado, la estructura de la práctica ha permitido ir implementando poco a poco los distintos comandos, pudiendo comprobar si funcionaban sin tener en cuenta otros comandos. Además, hemos aprendido cómo usar un API REST, y la metodología de trabajo para desarrollar un cliente que le haga peticiones. 

Una de las partes más complicadas de la prática, a nuestro parecer, ha sido cómo subir el fichero, ya que decía que subieras como argumento 'ufile' pero no decía en qué formato, si en bytes o en json o cómo, así que fue a base de prueba y error.
