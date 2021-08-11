"""
 Fichero que contiene la implementacion de funciones auxiliares para manejar las conexiones que hay en una llamada.
 Autores:
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
"""

from libreria_p3 import MAX_BUFF_LEN
from libreria_p3 import UTF
from libreria_p3 import TIME_OUT
from libreria_p3 import valid_puerto
from libreria_p3 import valid_IP
from libreria_p3 import call_query
from PIL import Image, ImageTk

import threading
import time
import numpy as np
import cv2
import socket
import heapq


# Socket, global para ser accedidos por los threads en el callback.
sendControlSocket = None

# Constantes, distintos valores de resolucion.
RESOLUCION_LOW = "160x120"
RESOLUCION_MEDIUM = "320x240"
RESOLUCION_HIGH = "640x480"
RESOLUCION_LOW_VALUE = (160, 120)
RESOLUCION_MEDIUM_VALUE = (320, 240)
RESOLUCION_HIGH_VALUE = (640, 480)

# Constante, minimo numero de FPS que se van a mostrar por pantalla.
MIN_FPS = 16

# Maximo tamaño de la cola de recepcion (Para no desbordarla).
MAX_QUEUE_LEN = 10000

'''
********
* FUNCIÓN: call_calling(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  abre el socket de control de conexión que envia los mensajes de control con el usuario
*				al que se quiere llamar (TCP), y envía el mensaje de que le están intentando llamar 
*				('CALLING ' + videoClient.local_nick + ' ' + str(videoClient.local_puerto_datos)).
*				Se usa la GUI para acceder a sus atributos.
* ARGS_OUT: None
********'''
def call_calling(videoClient):
    global sendControlSocket
    if sendControlSocket is not None:
        sendControlSocket.close()
    sendControlSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sendControlSocket.settimeout(TIME_OUT)
    try:
        sendControlSocket.connect((videoClient.call_IP, videoClient.call_puerto_control))
    except (ConnectionRefusedError, socket.timeout):
        videoClient.app.infoBox("Error", "El usuario no esta online, no se le puede llamar.")
        return

    message = 'CALLING ' + videoClient.local_nick + ' ' + str(videoClient.local_puerto_datos)
    message = bytes(message, UTF)
    sendControlSocket.send(message)
    return


'''
********
* FUNCIÓN: start_call(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  Primero, inicializa las variables de la GUI que servirán para controlar
* 				partes de la llamada, después abre los sockets de envío (sendDataSocket) 
*   			y recepción de datos (receiveDataSocket), ambos UDP,
*				y lanza dos hilos, cada uno correspondiente al envío y recepción de frames.
*				También muestra una ventana secuendaria que es para la llamada en sí.
* ARGS_OUT: None
********'''
def start_call(videoClient):
    videoClient.thread_send_frames_has_to_end = False
    videoClient.thread_receive_frames_has_to_end = False
    videoClient.video_is_paused = False

    # Muestra que puertos e IP se ha hecho la conexion.
    print("Llamada conectada")
    print("Usuario local con IP: " + videoClient.local_IP + ", puerto de datos: " + str(videoClient.local_puerto_datos)
          + ", puerto de control:" + str(videoClient.local_puerto_control))
    print("Usuario externo con IP: " + videoClient.call_IP + ", puerto de datos: " + str(videoClient.call_puerto_datos)
          + ", puerto de control:" + str(videoClient.call_puerto_control))

    # Thread que maneja el enviar frames.
    thread = threading.Thread(target=send_frames, args=(videoClient,))
    thread.start()

    # Thread que maneja el recibir frames.
    thread = threading.Thread(target=receive_frames, args=(videoClient,))
    thread.start()

    videoClient.app.showSubWindow("Ventana de llamada")
    return


'''
********
* FUNCIÓN: send_frames(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  principalmente lo que hace es obtener los frames del stream elegido (vídeo o webcam),
*				los comprime y los envía al otro usuario por el socket de envío de datos(sendDataSocket).
*				Comprueba que la aplicación sigue encendida y que la variable que controla la finalización
* 				del envío de paquetes (videoClient.thread_send_frames_has_to_end) no es True. Si no es así,
*				se acabará el bucle y se cierra la conexión. Además, se establece
*				el número de fps en función de si es vídeo o webcam. A cada paquete UDP se le añade una cabecera
*				para el control de flujo. 
* ARGS_OUT: None
********'''
def send_frames(videoClient):
    sendDataSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sendDataSocket.settimeout(TIME_OUT)

    videoClient.cap_envio = cv2.VideoCapture(videoClient.fichero_video_enviando)
    numSecuencia = 0
    fps = 20

    while not videoClient.thread_send_frames_has_to_end and videoClient.app.alive:
        if videoClient.video_is_paused:
            continue

        ret, frame = videoClient.cap_envio.read()
        if frame is None:
            # Si se ha acabado el video lo vuelve a mostrar.
            videoClient.cap_envio = cv2.VideoCapture(videoClient.fichero_video_enviando)
            continue

        # Emite segun la resolucion que se haya establecido, HIGH por defecto.
        if videoClient.actual_resolution == RESOLUCION_LOW:
            frame = cv2.resize(frame, RESOLUCION_LOW_VALUE)
        if videoClient.actual_resolution == RESOLUCION_MEDIUM:
            frame = cv2.resize(frame, RESOLUCION_MEDIUM_VALUE)
        if videoClient.actual_resolution == RESOLUCION_HIGH:
            frame = cv2.resize(frame, RESOLUCION_HIGH_VALUE)

        # Compresión JPG al 50% de resolución (se puede variar)
        encode_param = [cv2.IMWRITE_JPEG_QUALITY, 50]
        result, encimg = cv2.imencode('.jpg', frame, encode_param)
        if not result:
            print('Error al codificar imagen, en send_frame.')
            continue
        encimg = encimg.tobytes()

        # Se le añade a encimg su cabecera de control.
        cabecera = str(numSecuencia) + '#' + str(time.time()) +\
                   '#' + videoClient.actual_resolution + '#' + str(fps) + '#'
        data = bytes(cabecera, UTF) + encimg

        sendDataSocket.sendto(data, (videoClient.call_IP, videoClient.call_puerto_datos))
        numSecuencia += 1
        # Si esta la camara web poner 33 fps, sino (gif o video) poner 16.
        if videoClient.fichero_video_enviando == 0:
            fps = 33
        else:
            fps = 16
        videoClient.app.setPollTime(fps)

    # Cierra el socket de envio de datos.
    sendDataSocket.close()
    # Restauramos el de por defecto.
    videoClient.app.setPollTime(20)
    return


'''
********
* FUNCIÓN: receive_frames(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  principalmente, lo que hace es recibir paquetes, ordenarlos por su orden de emisión 
*				(con el timestamp) y reproducirlos en la ventana de llamada. Cuando llega el primer paquete,
*				directamente se separa la cabecera de los datos, y se mete en el buffer (implementado en forma de 
*               heap. Si el retraso es mas de 1 segundo entonces los paquetes no se meten al buffer ya que se 
*               considera que ha pasado mucho tiempo. Dependiendo del retraso cambian los fps, aumentando o 
*               disminuyendo la velocidad. Se comprueba que metiendo el frame no se desborde el buffer, y si 
*               asi es vacia el buffer y lo reinicia. Al principio hay un retraso fijo de 10 paquetes, esto
*               significa que no se va a comenzar a emitir hasta que se hayan guardado 10 paquetes, y luego
*               empieza a emitir, y ya no esta la restriccion de los 10 paquetes.
* ARGS_OUT: None
********'''
def receive_frames(videoClient):
    receiveDataSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    receiveDataSocket.settimeout(TIME_OUT)
    receiveDataSocket.bind((videoClient.local_IP, videoClient.local_puerto_datos))
    # Poco timeout para que se puedan imprimir muchos frames por segundo.
    receiveDataSocket.settimeout(0.06)

    numSecuencia = 0
    # fps por defecto.
    fps = 20
    # Cola que siempre va a estar heapificada.
    queue = []
    # Indicador del ultimo segundo (redondeado a entero) procesado.
    last_ts = 0
    # Contador de frames que se han mostrado en el ultimo segundo indicado por last_ts.
    counter_fps = 0

    while not videoClient.thread_receive_frames_has_to_end and videoClient.app.alive:
        # Si hay frames en la cola intentar mostrar alguno.
        if len(queue) > 0:
            first_time = round(queue[0][0])
            if last_ts != first_time:
                last_ts = first_time
                counter_fps = 0
                continue
            # Si todavia se pueden mostrar frames en este segundo.
            # numSecuencia > 10 indica que hay un retraso fijo que son 10 frames que
            # se han guardado en el buffer.
            if counter_fps < fps and numSecuencia > 10:
                decimg = queue[0][1]
                dx = time.time() - float(queue[0][0])  # queue[0][0] es el timeStamp.
                heapq.heappop(queue)

                # Los paquetes que tienen retraso > a 1 segundo no se muestran.
                if dx > 1:
                    continue

                # Conversión de formato para su uso en el GUI
                cv2_im = cv2.cvtColor(decimg, cv2.COLOR_BGR2RGB)
                img_tk = ImageTk.PhotoImage(Image.fromarray(cv2_im))

                videoClient.app.setImageData("Pantalla_llamada", img_tk, fmt='PhotoImage')

        try:
            data, serverAddress = receiveDataSocket.recvfrom(MAX_BUFF_LEN)
        except socket.timeout:
            continue

        if videoClient.video_is_paused:
            continue

        numSecuencia, data = get_substring_bytes(data)
        numSecuencia = int(numSecuencia.decode(UTF))
        timeStamp, data = get_substring_bytes(data)
        timeStamp = float(timeStamp.decode(UTF))
        resolucion, data = get_substring_bytes(data)
        # Se emite a resolucion alta, por lo que si el emisor emite a menor se vera mas pixelado (pero al emitir
        # menos pixeles habra menor congestion en la red.
        # resolucion = resolucion.decode(UTF)
        fps_recibidos, data = get_substring_bytes(data)
        fps_recibidos = int(fps_recibidos.decode(UTF))

        dx = time.time() - timeStamp
        # Los paquetes que se reciben tarde no se muestran
        if dx > 1:
            continue

        # Si el retraso es grande, bajar los fps, hasta MIN_FPS.
        if dx > 0.3:
            fps = max(fps - 1, MIN_FPS)

        # Si el retraso es pequeño, subir los fps, hasta fps_recibidos.
        elif dx < 0.2:
            fps = min(fps + 1, fps_recibidos)

        # Imprime a cuantos fps estamos mostrando.
        videoClient.app.setLabel("Fps llamada", "Reproduciendo a: " + str(fps) + " fps")

        encimg = data
        decimg = cv2.imdecode(np.frombuffer(encimg, np.uint8), 1)
        # decimg es el frame, y tenga la resolucion que tenga se hace resize a RESOLUTION_HIGH
        decimg = cv2.resize(decimg, RESOLUCION_HIGH_VALUE)

    # Para no desbordar el buffer de recepcion.
        if len(queue) > MAX_QUEUE_LEN:
            queue = []

        # Inserta el timeStamp y el nuevo encimg en el buffer.
        heapq.heappush(queue, (timeStamp, decimg))

    receiveDataSocket.close()
    return


'''
********
* FUNCIÓN: respuesta_llamada(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  abre una ventana secundaria que es de recepción de la llamada,
*				que le aparecerá al que le están llamando. Se reproducirá
*				un video de llamando, y se le indica quien le está llamando.
*				Cuando acepta o rechaza la llamada (videoClient.respuesta_llamada_is_done==True), 
*				se termina el bucle.
* ARGS_OUT: None
********'''
def respuesta_llamada(videoClient):
    videoClient.app.setLabel("Quien_llama", videoClient.call_nick + " te esta llamando ...")
    videoClient.app.showSubWindow("Ventana de recepcion de llamada")

    capturer = cv2.VideoCapture(videoClient.fichero_video_llamando)

    # Mientras el usuario esta decidiendo, reproducir el video.
    while not videoClient.respuesta_llamada_is_done:
        ret, frame = capturer.read()
        # Si el video se acaba, poner de nuevo.
        if frame is None:
            capturer = cv2.VideoCapture(videoClient.fichero_video_llamando)
            continue

        frame = cv2.resize(frame, (500, 300))
        cv2_im = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        img_tk = ImageTk.PhotoImage(Image.fromarray(cv2_im))

        videoClient.app.setImageData("Pantalla_recepcion", img_tk, fmt='PhotoImage')

    videoClient.respuesta_llamada_is_done = False
    return


'''
********
* FUNCIÓN: manage_puerto_control(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  se abre un socket para recibir los paquetes de control (TCP). Se queda escuchando en el 
*               puerto de control del usuario "local", hasta que se cierra la aplicacion, se cierra la 
*               conexión o se termina la llamada. Cuando recibe un paquete se dispone a mirar de que tipo
*               es. Si se trata de un CALL_END, ordena terminar la llamada ; si es CALL_HOLD, ordena pausar la 
*				llamada (cambiando la variable video_is_paused a True); y si es CALL_RESUME, se pone la variable
*               a False. Todas estas opciones cuando hacen lo que tienen que hacer vuelven al principio del bucle 
*               o lo terminan. Si se trata de un CALLING, es decir, el usuario intenta llamar, se obtienen los
*               datos para establecer la llamada, y se espera la respuesta, una vez que se obtiene la respuesta,
*               se pasa a ver si el usuario al que ha intentado llamar está ocupado (CALL_BUSY), o ha rechazado 
*               la llamda (CALL_DENIED), o la ha aceptado (CALL_ACCEPTED), en este caso, se empieza la llamada.
*               Lanza un Thread que se encarga de mientras haya un usuario conectado y llamando, responde CALL_BUSY.
* ARGS_OUT: None
********'''
def manage_puerto_control(videoClient):
    receiveControlSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    receiveControlSocket.settimeout(TIME_OUT)
    serverPort = videoClient.local_puerto_control
    # Bind del serverSocket.
    try:
        receiveControlSocket.bind((videoClient.local_IP, serverPort))
    except OSError:
        videoClient.app.infoBox("Error",
                                "Ya hay un usuario activo usando el puerto de control (" + str(serverPort) +
                                "), elige otro por favor.")

        videoClient.app.setTabbedFrameDisabledTab("Tabs", "Lista de usuarios", True)
        videoClient.app.setTabbedFrameDisabledTab("Tabs", "Buscar usuarios", True)
        videoClient.app.setTabbedFrameDisabledTab("Tabs", "Configuracion", True)
        videoClient.app.setTabbedFrameDisabledTab("Tabs", "Registrarse", False)
        return

    # 2 Listen, 1 para atender al control de la llamada y otro que devuelva CALL_BUSY.
    receiveControlSocket.listen(2)
    while videoClient.app.alive:
        # No hay llamada, thread busy tiene que morir.
        videoClient.thread_busy_has_to_end = True
        try:
            conexionSocket, conexionAddress = receiveControlSocket.accept()
        except socket.timeout:
            continue
        # Se lanza el segundo Thread que devuelve CALL_BUSY a cada conexion.
        videoClient.thread_busy_has_to_end = False
        thread_busy = threading.Thread(target=call_call_busy, args=(videoClient, receiveControlSocket))
        thread_busy.start()
        while videoClient.app.alive:
            try:
                message = conexionSocket.recv(MAX_BUFF_LEN)
            except socket.timeout:
                continue
            # Si ha cerrado la conexion se cierra el bucle.
            if message == b'':
                break

            # print("Hilo de control lee comando:", message)
            message = message.decode(UTF).split(' ')
            # si el otro usuario cuelga, el usuario cuelga también, y cierra conexion.
            if len(message) == 2 and message[0] == 'CALL_END' and message[1] == videoClient.call_nick:
                call_call_end(videoClient)
                break
            # si el otro usuario para el vídeo, el usuario también.
            if len(message) == 2 and message[0] == 'CALL_HOLD' and message[1] == videoClient.call_nick:
                videoClient.video_is_paused = True
                continue
            # si el otro usuario reanuda el vídeo, el usuario también.
            if len(message) == 2 and message[0] == 'CALL_RESUME' and message[1] == videoClient.call_nick:
                # Solo haces RESUME si el otro es el que ha pausado.
                if videoClient.yo_pauso:
                    continue
                videoClient.video_is_paused = False
                continue
            # si el usuario le hacen una llamada muestra la ventana de aceptar o rechazar, y prepara la llamada.
            if len(message) == 3 and message[0] == 'CALLING':
                nick = message[1]
                puerto_datos = message[2]

                if not valid_puerto(puerto_datos):
                    continue
                videoClient.call_nick = nick
                videoClient.call_puerto_datos = int(puerto_datos)

                ans = call_query(nick)
                if ans is None:
                    break
                nick, IP, puerto_control, protocolo = ans
                if not valid_IP(IP):
                    videoClient.app.infoBox("Error", "El usuario no tiene una IP valida")
                    break
                if not valid_puerto(puerto_control):
                    videoClient.app.infoBox("Error",
                                            "El usuario no tiene un puerto de control valido: " + puerto_control)
                    break
                videoClient.call_IP = IP
                videoClient.call_puerto_control = int(puerto_control)

                respuesta_llamada(videoClient)
            #  una vez recibe la respuesta, verá que le han respondido.
            if len(message) == 1 and message[0] == "CALL_BUSY":
                videoClient.app.infoBox("Error",
                                        "Al usuario que intenta llamar esta ocupado, por favor, intentelo más tarde.")
                break

            if len(message) == 2 and message[0] == "CALL_DENIED" and message[1] == videoClient.call_nick:
                videoClient.app.infoBox("Informacion", "El usuario declino la llamada.")
                break

            # si le aceptan la llamada, empieza la llamada.
            if len(message) == 3 and message[0] == "CALL_ACCEPTED" and message[1] == videoClient.call_nick:
                a, nick, puerto_datos = message
                if not valid_puerto(puerto_datos):
                    videoClient.app.infoBox("Error", "El usuario no tiene un puerto de datos valido: " + puerto_datos)
                    break

                videoClient.call_puerto_datos = int(puerto_datos)

                start_call(videoClient)

        # Cierra el socket de la conexion con el usuario.
        conexionSocket.close()

    # Cierra el socket de recepcion de control.
    receiveControlSocket.close()
    return


'''
********
* FUNCIÓN: call_call_accepted(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  cuando se acepta una llamada, se abre un socket de control (si está ya abierto, se cierra y 
*               se vuelve a abrir), y manda al usuario que está llamando que se le ha aceptado la llamada,
*               y se lanza un hilo que comienza la llamada (start_call). Se pone la variable que indica si se
*               tiene que cerrar la ventana de recepción de llamada (videoClient.respuesta_llamada_is_done) 
*               a True, para que se cierre y empiece la llamada. Se envia al usuario por el socket de control
*               que la llamada ha sido aceptada, para que él también empiece la llamada.
* ARGS_OUT: None
********'''
def call_call_accepted(videoClient):
    # Esconde la ventana GUI.
    videoClient.app.hideSubWindow("Ventana de recepcion de llamada", useStopFunction=False)
    videoClient.respuesta_llamada_is_done = True

    # Prepara el puerto de envio de control.
    global sendControlSocket
    if sendControlSocket is not None:
        sendControlSocket.close()
    sendControlSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sendControlSocket.settimeout(TIME_OUT)
    try:
        sendControlSocket.connect((videoClient.call_IP, videoClient.call_puerto_control))
    except (ConnectionRefusedError, socket.timeout):
        videoClient.app.infoBox("Error", "El usuario no esta online, no se puede aceptar.")
        return

    # Envia CALL_ACCEPTED.
    message = 'CALL_ACCEPTED ' + videoClient.local_nick + ' ' + str(videoClient.local_puerto_datos)
    message = bytes(message, UTF)
    sendControlSocket.send(message)

    thread = threading.Thread(target=start_call, args=(videoClient,))
    thread.start()
    return


'''
********
* FUNCIÓN: call_call_denied(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  cuando se rechaza una llamada, se abre un socket de control (si está ya abierto, se cierra 
*               y se vuelve a abrir), y se manda al usuario que está llamando que se le ha rechazado la
*               llamada, y cierra la conexión. Se pone la variable que indica si se tiene que cerrar la
*               ventana de recepción de llamada (videoClient.respuesta_llamada_is_done) a True, 
*				para que se cierre y vuelva a la ventana principal.  Se envia al usuario por el socket de control que
*				la llamada ha sido rechazada, para que sepa que ha sido rechazada.
* ARGS_OUT: None
********'''
def call_call_denied(videoClient):
    # Se cierra la ventana GUI.
    videoClient.app.hideSubWindow("Ventana de recepcion de llamada", useStopFunction=False)
    videoClient.respuesta_llamada_is_done = True

    global sendControlSocket
    if sendControlSocket is not None:
        sendControlSocket.close()
    sendControlSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sendControlSocket.settimeout(TIME_OUT)
    try:
        sendControlSocket.connect((videoClient.call_IP, videoClient.call_puerto_control))
    except (ConnectionRefusedError, socket.timeout):
        videoClient.app.infoBox("Error", "El usuario no esta online, no se puede denegar.")
        return

    # Se envia CALL_DENIED.
    message = 'CALL_DENIED ' + videoClient.local_nick
    message = bytes(message, UTF)
    sendControlSocket.send(message)

    sendControlSocket.close()
    sendControlSocket = None
    return


'''
********
* FUNCIÓN: call_call_end(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  cuando se cuelga una llamada, se cambian las variables que controlan la finalización del envío y
*				recepción de paquetes a True, para indicar que se pare de enviar y recibir paquetes. Se pone
*				un tiempo de espera para que los hilos acaben. Se envia al usuario por el socket de control que
*				la llamada ha finalizado, para que él también la finalice.
* ARGS_OUT: None
********'''
def call_call_end(videoClient):
    # Se cierra la ventana de llamada.
    videoClient.app.hideSubWindow("Ventana de llamada", useStopFunction=False)

    # Indica a los hilos de envio y recepcion que tienen que acabar.
    videoClient.thread_send_frames_has_to_end = True
    videoClient.thread_receive_frames_has_to_end = True
    # Le da tiempo a los otros threads a acabar.
    time.sleep(2*TIME_OUT)

    # Envia CALL_END.
    query = 'CALL_END ' + videoClient.local_nick
    query = bytes(query, UTF)
    sendControlSocket.send(query)
    return


'''
********
* FUNCIÓN: call_call_hold(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  cuando se pausa una llamada, se pone la variable que controla la parada de la llamada a True
*				(videoClient.yo_pauso), y se envia al otro usuario que se ha parado la llamada, para que le también la 
*				pare. Una llamada se renauda cuando la quiere renaudar el que la pause, y no el otro.
* ARGS_OUT: None
********'''
def call_call_hold(videoClient):
    # Te guardas que eres tu el que la pausas.
    videoClient.yo_pauso = True

    query = 'CALL_HOLD ' + videoClient.local_nick
    query = bytes(query, UTF)
    sendControlSocket.send(query)
    return


'''
********
* FUNCIÓN: call_call_resume(videoClient)
* ARGS_IN: videoClient - GUI con la que se está trabajando
* DESCRIPCIÓN:  cuando se reanuda una llamada, se pone la variable que controla la parada de la llamada a False
*				(videoClient.yo_pauso), y se envia al otro usuario que se ha reanudado la llamada, para que le también la 
*				reanude.
* ARGS_OUT: None
********'''
def call_call_resume(videoClient):
    videoClient.yo_pauso = False

    query = 'CALL_RESUME ' + videoClient.local_nick
    query = bytes(query, UTF)
    sendControlSocket.send(query)
    return


'''
********
* FUNCIÓN: call_call_busy(videoClient, receiveControlSocket)
* ARGS_IN: videoClient	- GUI con la que se está trabajando.
*          receiveControlSocket - socket por el cual mandar CALL_BUSY
* DESCRIPCIÓN:  Mientras haya un usuario llamando, hay un hilo ejecutando esta funcion que devolvera
*               CALL_BUSY a todo el que se conecte al socket.
* ARGS_OUT: Devuelve ambas partes.
********'''
def call_call_busy(videoClient, receiveControlSocket):
    while not videoClient.thread_busy_has_to_end and videoClient.app.alive:
        # Establece una conexion con algun usuario.
        try:
            conexionSocket, conexionAddress = receiveControlSocket.accept()
        except socket.timeout:
            continue
        try:
            message = conexionSocket.recv(MAX_BUFF_LEN)
        except socket.timeout:
            continue
        conexionSocket.close()

        message = message.decode(UTF).split(' ')
        if len(message) != 3 or message[0] != 'CALLING':
            return

        a, nick, b = message
        ans = call_query(nick)
        if ans is None:
            return

        a, IP, puerto_control, d = ans
        if not valid_IP(IP) or not valid_puerto(puerto_control):
            return
        puerto_control = int(puerto_control)

        # Crea el socket para enviar el CALL_BUSY.
        sendBusySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sendBusySocket.settimeout(TIME_OUT)
        try:
            sendBusySocket.connect((IP, puerto_control))
        except (ConnectionRefusedError, socket.timeout):
            return

        # Manda CALL_BUSY y cierra la conexion.
        message = bytes('CALL_BUSY', UTF)
        sendBusySocket.send(message)
        sendBusySocket.close()

    return


'''
********
* FUNCIÓN: get_substring_bytes(cadena)
* ARGS_IN: cadena	- cadena a dividir, que contenga el caracter '#'.
* DESCRIPCIÓN:  divide la cadena en dos partes, una la parte antes de '#', y otra
*				la de después de '#'.
* ARGS_OUT: Devuelve ambas partes.
********'''
def get_substring_bytes(cadena):
    # i sera el indice que contendra el caracter '#'.
    i = 0
    while i < len(cadena):
        if cadena[i:i+1] == b'#':
            break
        i += 1
    return cadena[0:i], cadena[i+1:]
