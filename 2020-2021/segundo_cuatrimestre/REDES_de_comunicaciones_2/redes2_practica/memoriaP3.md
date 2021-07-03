# MEMORIA

### 1. INTRODUCCIÓN

En esta práctica se ha realizado una aplicación multimedia que trasmite solo vídeo entre dos clientes en una misma red local, usando un servidor de descubrimiento para poder establecer la conexión entre ambos clientes, algo parecido a *Skype*. A través de la aplicación el cliente puede registrarse indicando una serie de campos; puede mirar toda la lista de usuarios registrados, y llamarlos; y puede buscar un usuario por su nombre y llamarlo. Estas acciones se explicarán más extensamente en los siguientes apartados. Es importante que para esta práctica estén instalados los siguientes paquetes: *python3-pil.imagetk*, *python3-tk*, *python3-numpy*, y *python-imaging-tk*.

### 2. ESTRUCTURA

Antes de pasar al apartado que explica el diseño y la implementación de las acciones ya comentadas, se va a comentar como está estructurada la práctica. En la carpeta principal (**practica3**), tenemos:

- **imgs**: carpeta donde se guardan los vídeos que se pueden enviar a la persona con la que ha establecido conexión el cliente.

- **practica3_client.py**: programa principal donde se encuentra toda la interfaz gráfica, así como la interacción con ella.

- **libreria_p3.py**: en este fichero se han implementado las funciones que realizan *queries* al servidor de descubrimiento mediante una conexión TCP.

- **manage_call.py**: en este fichero se han implementado las funciones necesarias para establecer una llamada con otro cliente, incluyendo la conexión de control (TCP), y la de vídeo (UDP).

- **simulate_internet.sh**: este es el fichero de prueba con el que se simula flujo internet, para probar el control del flujo de nuestro programa.


### 3. DISEÑO

Esta práctica se divide en tres partes principalmente: una parte es el diseño de la interfaz gráfica, otra es la implementación de la conexión con el servidor de descubrimiento, y la última, la de la implementación de la conexión de control, para establecer la llamada con el otro cliente, y de la conexión UDP para el envío y recepción de frames. 

### 4. IMPLEMENTACIÓN, PARTE 1

En cuanto al diseño de la interfaz gráfica, nada más abrir la aplicación nos encontramos una ventana que, por un lado, en la parte superior, un gracioso GIF para dar la bienvenida al cliente, y por otro lado, en la parte inferior, aparecen varias pestañas: **Registrarse**, **Lista de usuarios**, **Buscar usuario**, **Configuración** y **Salir**. Además, en el medio de las dos partes, se marca la hora. Cuando uno no se ha registrado, solo puede acceder a la de **Registrarse** y a la de **Salir**, pero una vez el cliente se registra, tiene acceso a todas las demás.

- En la pestaña **Registrarse**, por defecto hay unos valores predeterminados en las casillas a rellenar, de manera que el usuario tiene una idea de cómo hay que rellenarlas, y si el usuario no conoce estos términos (es ajeno a la asignatura de redes), todavía puede usar la aplicación. Estas casillas son: *Nombre del usuario*, *Contraseña*, *IP*, *Puerto de control*, *Puerto de datos*, y *Protocolo*, donde *Protocolo* indica que protocolos soporta la aplicación, desde el básico V0 a superiores. Una vez que el usuario ha rellenado las casillas es capaz de registrarse pulsando el botón **Registrarse**, tras lo cual, parte de las casillas se vaciarán, cosa que el usuario puede hacer el mismo pulsando el botón **Limpiar**.

- En la pestaña **Lista de usuarios**, nos encontramos con una lista de todos los usuarios registrados, y el usuario puede seleccionar uno de estos y llamarlo pulsando el botón **Llamar al seleccionado**. Asimismo, el usuario puede refrescar la lista, por si algún cliente se ha registrado recientemente, pulsando el botón **Refrescar**.

- En la pestaña **Buscar usuario**, el cliente puede buscar un usuario por su nombre, y al pulsar el botón **Buscar**, le aparecerá la información del usuario buscado (si existe), con la opción de llamarlo pulsando el botón **Llamar**. No se podrá llamar hasta que el usuario que se haya buscado exista y tenga los datos IP y puertos válidos. 

- Luego tenemos la pestaña **Configuración**, en la que el usuario puede establecer el vídeo por defecto que se enviará en sus llamadas. 

- Por último, la pestaña **Salir** consta de un único botón **Salir**, que permite abandonar la aplicación.

Por otro lado tenemos la ventana de la llamada, que solo aparece cuando se ha establecido una llamada entre dos usuarios. Esta ventana tiene un espacio para el vídeo que se está recibiendo, y tiene 8 botones: **Colgar**, que termina la llamada; **Pausar**, que para la llamada; **Reanudar**, que continúa la llamada que fue parada (solo reanuda si has sido tú el que la ha pausado); **Usar webcam**, que permite cambiar de vídeo a webcam; **Usar vídeo**, que permite reproducir un vídeo señalado en la casilla de la misma pantalla. También se han añadido unos botones **Resolucion baja**, **Resolucion media** y **Resolucion alta** para que el usuario elija la resolución del vídeo que está emitiendo. Además, en esta pantalla se han incluido la hora y los fps a los que se está reproduciendo. 

Para que un usuario sepa que le están llamando,  se crea una ventana para la recepción de la llamada, que se muestra al cliente al que le están llamando, en la que hay un vídeo de un teléfono sonando, y dos botones: **Aceptar**, con el que se acepta la llamada; y **Rechazar**, que permite rechazar la llamada. Esta ventana también indica al usuario quién le está llamando. 

Al usuario que llama no se le muestra nada, simplemente llama, y si recibe respuesta se muestra (como que el usuario ha aceptado, ha rechazado, no estaba online...). Esta decisión es para que el usuario al que ha llamado tenga tiempo para decidir si quiere aceptar la llamada o rechazarla. 


### 5. IMPLEMENTACIÓN, PARTE 2

Pasando a la segunda parte, las funciones que realizan las *queries* al servidor de  descubrimiento, como se ha mencionado anteriormente, se encuentran en el archivo **libreria_p3.py**. Para realizar las *queries* es necesario establecer una conexión TCP por la que se enviarán, y por la que se recibirá la respuesta del servidor. Al ejecutar la aplicación, te muestra con un mensaje por terminal si se ha podido conectar al servidor o no (por ejemplo si el servidor está caído). Los distintos mensajes que soporta el servidor, y para los que se ha creado una función son obtenidos con los siguientes comandos:

- **REGISTER** nick ip_address port password protocol: sirve para registrar al cliente con los distintos campos (nick, ip_address, port, password, protocol), y se envía el mensaje con este comando siempre que el cliente pulse el botón de **Registrarse**. Nuestra aplicación hace un parseo de los argumentos, y solo registra cuando el IP y puertos son correctos (Números en un cierto intervalo, el IP separado por puntos etc.).

- **QUERY** name: sirve para obtener la IP y el puerto de control de un usuario a partir de su nick. Este mensaje se usa cuando se busca un usuario, ya que, si existe, se muestra por pantalla los datos del mismo, incluyendo la IP y el puerto. También se usa para cuando se quiere llamar a un usuario desde la lista de usuarios. En general se usa cuando se quiere conectar con el usuario, ya que se recibe la IP y el puerto de control.

- **LIST_USERS**: sirve para obtener la lista de los usuarios registrados en el sistema y poder llamarles. Se usa cuando se abre la pestaña de **Lista de usuario**, para poder mostrarla al usuario. Nuestra aplicación filtra a los usuarios inválidos, que por ejemplo no tienen nick, o no tienen todos los campos necesarios, y esos no los muestra. Sí que muestra los que tienen IP o puertos de control inválidos, pero no se les va a poder llamar.

- **QUIT**: sirve para indicar que se quiere cerrar la conexión con el servidor, de manera que se sale de la aplicación. Este mensaje se usa cuando se pulsa el botón **Salir** o se cierra la GUI, es decir siempre que la aplicación acabe. Se muestra por un mensaje por la terminal que la conexión con el servidor ha terminado.

En este archivo, aparte de las funciones que se encargan de generar estos mensajes, hay una función que se encarga de enviar el mensaje al servidor y obtener la respuesta (Como una especie de API en resumen). Esta respuesta es devuelta a las respectivas funciones para que estas se aseguren que no hay ningún error, y para que devuelvan los datos necesarios.

### 6. IMPLEMENTACIÓN, PARTE 3

Y por último, la implementación de la llamada en sí. En esta parte hay, a su vez, dos partes: la conexión de control (por TCP), y la conexión de vídeo (por UDP). Ambas partes están implementadas en el fichero **manage_call.py**, como se ha comentado antes. 

Por un lado, cuando se registra un nuevo cliente se guardan todos los datos y se crea un hilo que ejecuta la función **manage_puerto_control**, que se encarga recibir los mensajes de control para controlar las llamadas. Esta función, primeramente, abre un socket TCP de hasta 2 conexiones, una para recibir los paquetes de control, y se queda escuchando en el puerto de control, indicado por el usuario en el registro y la otra para mandar *CALL_BUSY* en caso de que se haya establecido una llamada. Después tiene un bucle, que espera a aceptar conexiones de otros usuarios. Una vez conectado tiene otro bucle que espera a recibir señales de control de la llamada. Se pueden dar las siguientes señales:

- *CALL_END*: Se termina la llamada, se cierran los sockets, los hilos y se retorna a la ventana por defecto para poder seguir llamando a otros usuarios.

- *CALL_HOLD*: El otro usuario pausa la llamada. Se para de emitir y recibir frames hasta que no se reanuda.

- *CALL_RESUME*: El otro usuario reanuda la llamada. Se reanuda, pero primero comprueba que ha sido efectivamente el otro usuario el que la ha pausado, si no, podría reanudar la llamada que tú has pausado.

- *CALLING*: El otro usuario te está llamando. Prepara la ventana de decidir si quieres aceptar o rechazar la llamada.

- *CALL_BUSY*: El usuario al que quieres llamar está en otra llamada, no le puedes llamar. Muestra un mensaje de error por pantalla.

- *CALL_DENIED*: El usuario al que quieres llamar te ha rechazado la llamada. Te lo muestra por pantalla.

- *CALL_ACCEPTED*: El usuario al que quieres llamar te ha aceptado la llamada. Comienza la llamada que ya se ha preparado los datos para la conexión.

### 7. CONTROL DE FLUJO

Para el control de flujo, en los paquetes UDP se ha incluido una cabecera con los siguientes campos y de la siguiente manera: **Número de orden#Timestamp#Resolución video#FPS#Datos**. Tras la última '#' después de FPS se incluyen los datos, en nuestro caso, los bytes correspondientes a la imagen comprimida. 

- Se ha usado un buffer de recepción implementado como una lista *heapificada* (con la propiedad de un heap), que usa el *timestamp* de la cabecera para ordenar los paquetes según van llegando en la parte de recepción de paquetes. Así, si un paquete llega antes que otro, estos se ordenan y se muestran en orden.

- En cuanto a los fps a los que se envía la llamada, depende si se trata de un vídeo almacenado, en ese caso, se envía a 16 fps, o si se trata de la webcam, se envía el vídeo a 33 fps.

- Si el retraso entre el tiempo actual, y el tiempo que se envió el paquete, es superior a 1 segundo, ese frame no se muestra (Se considera que es muy viejo y que tiene mucho retraso).

- Si el retraso es mayor que 0.3 segundos entonces los fps a los que se reproducen los frames disminuyen, hasta un mínimo que se establece constante.

- Si el retraso es menor que 0.2 segundos entonces los fps a los que se reproducen los frames aumentan, hasta el máximo de los fps que emite.

- El emisor puede enviar a distintas resoluciones, baja, media y alta, y el receptor, envíe a la resolución que envíe, va a emitir a alta resolución, por lo que depende de a qué resolución envíe el emisor, se reproducirá más pixelado o menos.

- Se comprueba el tamaño del buffer de recepción, así que si supera un umbral este se vacía (Se considera que los frames son muy viejos). Esta decisión es para que no haya un buffer overflow y desborde.

- Dado el número de fps a mostrar, solo se van a mostrar hasta fps frames, no más. 

- Al principio de la emisión, se espera hasta tener 10 frames en el buffer (retraso fijo) y así la emisión ir más fluida, evitando un poco de retraso de reproducción más adelante provocado por el jitter. Nótese que no exigimos que siempre el buffer tenga tamaño 10, sino solo hasta emitir el primer frame.

### 8. PRUEBAS REALIZADAS

A medida que se iban implementando botones, funciones y requisitos, se probaban y se veía si cumplían con la funcionalidad deseada. La que más pruebas ha requerido ha sido la llamada. Se ha testeado:

- Se abren dos terminales, registrando en cada una un usuario distinto (y un puerto distinto, pero misma IP, puesto que es la misma máquina), de manera que se puedan llamar. Una complicación que nos surgió al principio es que no se podía usar dos veces la misma webcam porque solo había una webcam, entonces, decidimos cambiar la webcam por un vídeo cualquiera, accesible por ambas terminales sin conflicto para continuar las pruebas. 

- Se probó que el envío y la recepción de frames eran correctos, es decir, si se pulsaba a llamar a un usuario, se reproducían bien los paquetes. Una vez que esto funcionaba bien, se implementó la ventana de recepción de llamada, en la que el usuario llamado podía aceptar o no la llamada. Cuando esto también funcionaba, se pasó a la funcionalidad de los botones de colgar, pausar, reanudar, y cambiar el vídeo que se mostraba. Por último, cuando todo esto funcionaba, se pasó al control de flujo, y se observaba como la resolución iba cambiando.

- Llamada en red local con el mismo código. Nos conectamos con la VPN de la UAM y ahí probamos a llamarnos con el mismo código, a ver si funcionaba la llamada y todos los requisitos en general.

- Llamada en red local con distinto código. Nos conectamos con la VPN de la UAM y ahí probamos la práctica llamando a otro grupo distinto, y vemos que a pesar de tener distinto código (el cliente de la aplicación), era completamente compatible.

- Tests de pérdida de paquetes, mucho jitter, retraso etc. con el fichero **simulate_internet.sh**. Se observaba que si poníamos un delay de más de 1 segundo, nuestro control de flujo funcionaba y no se emitía nada, pero si poníamos delay de 900 ms se veía como bajaban los fps, había frames que no se reproducían y otros sí (debido al jitter), justo lo esperado.

- Tests de estrés, probando introducir cualquier cosa en los campos input de la aplicación, pulsando muchas veces los botones en orden aleatorios, para ver si producían errores o no (esto está orientado a la seguridad de la aplicación).

### 9. ¿CÓMO LLAMAR? ¿CÓMO SÉ QUE ME ESTÁN LLAMANDO?

Primero, has de registrarte en la aplicación indicando tu nombre, tu IP, dos puertos, uno de control y otro de datos. Una vez registrado, tendrás acceso a la aplicación en sí, es decir, podrás mirar la lista de usuarios que hay registrados, y podrás llamar a uno que selecciones. Asimismo, podrás buscar un usuario, y llamarlo también. Si llamas a alguien, y esta persona coge la llamada, se abrirá una ventana donde te aparecerá lo que esa persona te está enviando, así como unos botones para colgar, pausar la llamada, reanudarla si está parada, y cambiar el vídeo que quieres enviar a la persona. Sabrás que te están llamando porque aparecerá una ventana, donde se mostrará un vídeo de un teléfono sonando y el nombre de la persona que te está llamando, así como dos botones, que te darán la opción de aceptar o rechazar la llamada.

Si desea cerrar la sesión, podrá acceder a la pestaña **Salir** y pulsar el botón en ella. Como se puede observar, la interfaz gráfica no es para nada complicada, y es muy fácil de usar, por lo que cualquier persona, con o sin conocimientos sobre la transmisión de contenido multimedia, puede usarla y disfrutar de una agradable experiencia con la aplicación.

### 10. NOTAS

Algunas notas sobre la implementación de la aplicación:

- A todos los sockets usados se le han establecido un timeout para que no se cuelguen esperando una lectura que nunca van a obtener. Se les ha metido dentro de un try-except y, en caso de que se necesite la lectura, metido dentro de un bucle while.

- A todos los hilos que crea el hilo principal se les ha metido la condición en el bucle que ejecutan que si la GUI ha acabado se cierren, así cuando la aplicación acaba, no importa el estado de cada hilo y se cierran.

- Todos los sockets y todos los hilos se abren, hacen su función programada y luego se cierran, liberando los recursos que han reservado.

- Para ejecutar la aplicación usar el comando:
> python3 practica3_client.py


### 11. OPINIÓN PERSONAL

En nuestra opinión, esta práctica nos ha enseñado a usar mejor los hilos, sockets, así como la GUI de python. Además, nos ha ayudado a comprender mejor el control de flujo en la recepción, en cuanto a que es mejor tener un retraso inicial, ya que se llena el buffer con algunos paquetes, para que luego no se note el jitter en la reproducción. También hemos visto que no siempre es mejor emitir lo más rápido que puedas, ya que al tener que reproducir un determinado número de fps, aunque mandes más no se van a reproducir más (si no el vídeo iría muy rápido y con posibles cortecitos o saltos).

Una cosa a negativa de la práctica es que el servidor de descubrimiento se ha caído varias veces, impidiendo poder probar la funcionalidad de la práctica durante un tiempo. (Dependencia de la práctica con factores externos a nuestro código, como el caso del servidor).