"""
 Fichero que contiene el programa principal con la interfaz gráfica de usuario.
 Autores:
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
"""

from appJar import gui
from libreria_p3 import *
from manage_call import *


# Clase principal que maneja la interfaz gráfica.
class VideoClient(object):
    # Variables del usuario a llamar.
    call_nick, call_IP, call_puerto_datos, call_puerto_control, call_protocolo = None, None, None, None, None
    # Variable booleana que indica si el usuario a llamar es valido.
    valid_call_user = False

    # Haces que te conectas a google y lees la ip local que tienes.
    # Codigo sacado de: https://stackoverflow.com/questions/166506/finding-local-ip-addresses-using-pythons-stdlib
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(("8.8.8.8", 80))
    local_IP = s.getsockname()[0]
    s.close()

    # Variables del usuario local.
    local_puerto_datos = None
    local_puerto_control = None
    local_nick = None

    # El fichero que se muestra al abrir la GUI, o 0 si es la webcam.
    fichero_video = 'imgs/webcam.gif'
    # El fichero que se muestra al llamar, o 0 si es la webcam.
    fichero_video_llamando = 'imgs/llamando.gif'
    # El fichero que se esta enviando, o 0 si es la webcam.
    fichero_video_enviando = 'imgs/llamando.gif'
    # El fichero cuando no hay recepcion de datos.
    fichero_no_signal = 'imgs/no_signal.gif'

    # Variables globales para manejar los hilos.
    # Son False si tienen que estar vivos, sino True.
    thread_send_frames_has_to_end = True
    thread_receive_frames_has_to_end = True

    # True si el video esta pausado, sino False.
    video_is_paused = False
    # Variable que dice si eres tu el que pausa, para que solo tu puedas renaudarlo.
    yo_pauso = False

    # True si el usuario ha pulsado Accept or Decline.
    respuesta_llamada_is_done = False
    # .cap mientras se esta llamando.
    cap_envio = None

    # Variable que guarda la resolucion actual que muestra el video.
    actual_resolution = RESOLUCION_HIGH

    def __init__(self, window_size):
        # Creamos una variable que contenga el GUI principal
        self.app = gui("Redes2 - P2P", window_size)
        self.app.setGuiPadding(10, 10)

        # Preparación del interfaz
        self.app.addLabel("title", "Cliente Multimedia P2P - Redes2 ")
        # Pone una imagen que luego se va a sobreescribir con el video.
        self.app.addImage("video", self.fichero_video)

        self.app.addLabel("Time", "tiempo")
        self.app.registerEvent(self.print_time)

        # Registramos la función de captura de video
        # Esta misma función también sirve para enviar un vídeo
        # 0 para webcam, "fichero" para archivo de video.
        self.cap = cv2.VideoCapture(self.fichero_video)
        self.mode_webcam = False
        self.app.setPollTime(20)
        self.app.registerEvent(self.capturaVideo)

        # Funcion a llamar cuando la GUI acabe.
        self.app.setStopFunction(call_quit)

        # Creamos una ventana secundaria.
        self.app.startSubWindow("Ventana de llamada", modal=True)
        self.app.addImage("Pantalla_llamada", self.fichero_no_signal)
        self.app.addButtons(["Colgar", "Pausar", "Renaudar", "Usar webcam", "Usar video"], self.buttonsCallback)
        self.app.addLabelEntry("Nombre del video a transmitir")
        self.app.addLabel("Tiempo llamada", "00:00")
        self.app.addLabel("Fps llamada", "0 fps")
        # Se pone un video por defecto.
        self.app.setEntry("Nombre del video a transmitir", self.fichero_video_llamando)
        self.app.addButtons(["Resolucion baja", "Resolucion media", "Resolucion alta"], self.buttonsCallback)
        self.app.stopSubWindow()
        self.setImageResolution(RESOLUCION_HIGH)

        # Creamos una ventana secundaria.
        self.app.startSubWindow("Ventana de recepcion de llamada", modal=True)
        self.app.addImage("Pantalla_recepcion", self.fichero_video_llamando)
        self.app.addLabel("Quien_llama", "Te esta llamando ...")
        self.app.addButtons(["Aceptar", "Rechazar"], self.buttonsCallback)
        self.app.stopSubWindow()
        # Crea el tabbed frame.
        with self.app.tabbedFrame("Tabs"):
            # Tab para registrarse.
            with self.app.tab("Registrarse"):
                self.app.setBg("Goldenrod1")
                self.app.addLabelEntry("Nick\t\t", 0, 0)
                self.app.addLabelSecretEntry("Contraseña\t", 1, 0)
                self.app.addLabelEntry("IP\t\t", 2, 0)
                self.app.addLabelEntry("Puerto de control\t", 3, 0)
                self.app.addLabelEntry("Puerto de datos\t", 4, 0)
                self.app.addLabelEntry("Protocolo\t\t", 5, 0)
                self.app.addButtons(["Registrarse", "Limpiar"], self.buttonsCallback, 6, 0, 2)
                self.app.setEntryFocus("Nick\t\t")
                # Pone default values.
                self.app.setEntry("IP\t\t", self.local_IP)
                self.app.setEntry("Protocolo\t\t", "V0")
                self.app.setEntry("Puerto de control\t", "4444")
                self.app.setEntry("Puerto de datos\t", "4445")

            # Tab para listar usuarios.
            with self.app.tab("Lista de usuarios"):
                self.app.setBg("Goldenrod1")

                # Guarda los nicks registrados actualmente para mostrarlos.
                database = call_users_list()
                nicks = database[0]

                self.app.addListBox("Nicks de usuarios registrados", nicks, 0, 0, 1, 4)
                # Boton que refresca la lista de usuarios.
                self.app.addButton("Refrescar", self.buttonsCallback, 0, 1)
                self.app.addButton("Llamar al seleccionado", self.buttonsCallback, 1, 1)

            # Tab para buscar usuarios.
            with self.app.tab("Buscar usuarios"):
                self.app.setBg("Goldenrod1")
                self.app.addLabel("UserBuscar", "Busca un usuario para llamarlo.", 2, 0)
                self.app.addLabelEntry("Nick a buscar", 0, 0)
                self.app.addButton("Buscar", self.buttonsCallback, 1, 0)
                self.app.addButton("Llamar", self.buttonsCallback, 3, 0)

            with self.app.tab("Configuracion"):
                self.app.setBg("Goldenrod1")
                self.app.addLabel("Select", "Si quiere cambiar a webcam, pulse el botón.", 0, 0)
                self.app.addButton("Webcam", self.buttonsCallback, 0, 1)
                self.app.addLabel("Eleccion", "Si quiere enviar un vídeo, escriba el nombre del vídeo.", 1, 0)
                self.app.addLabelEntry("Nombre del video", 2, 0)
                self.app.addButton("Establecer video", self.buttonsCallback, 3, 0)
                # Se pone un video por defecto.
                self.app.setEntry("Nombre del video", "imgs/llamando_homer.gif")

            # Tab para salir del programa.
            with self.app.tab("Salir"):
                self.app.setBg("Goldenrod1")
                self.app.addButton("Salir", self.buttonsCallback, 0, 0)

        # Las desabilita hasta que el usuario se registra.
        self.app.setTabbedFrameDisabledTab("Tabs", "Lista de usuarios")
        self.app.setTabbedFrameDisabledTab("Tabs", "Buscar usuarios")
        self.app.setTabbedFrameDisabledTab("Tabs", "Configuracion")

    '''
    ********
    * FUNCIÓN: start(self)
    * ARGS_IN: self - la clase
    * DESCRIPCIÓN: Funcion que inicia la GUI. Es llamada en el main.
    * ARGS_OUT: None
    ********'''
    def start(self):
        self.app.go()

    '''
    ********
    * FUNCIÓN: print_time(self)
    * ARGS_IN: self - la clase
    * DESCRIPCIÓN:  añade la etiqueta hora, con la hora actual a cada ventana.
    * ARGS_OUT: None
    ********'''
    def print_time(self):
        self.app.setLabel("Time", "Hora: " + time.strftime("%X"))
        self.app.setLabel("Tiempo llamada", "Hora: " + time.strftime("%X"))

    '''
    ********
    * FUNCIÓN: capturaVideo(self)
    * ARGS_IN: self - la clase
    * DESCRIPCIÓN: Función que captura el frame a mostrar en cada momento.
    * ARGS_OUT: None
    ********'''
    def capturaVideo(self):
        # Capturamos un frame de la cámara o del vídeo
        ret, frame = self.cap.read()
        # Si el video se acaba, poner de nuevo.
        if frame is None:
            self.cap = cv2.VideoCapture(self.fichero_video)
            return

        frame = cv2.resize(frame, (500, 300))
        cv2_im = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        img_tk = ImageTk.PhotoImage(Image.fromarray(cv2_im))

        # Lo mostramos en el GUI
        self.app.setImageData("video", img_tk, fmt='PhotoImage')

    '''
    ********
    * FUNCIÓN: setImageResolution(self, resolution)
    * ARGS_IN: self - la clase.
    *          resolution - cadena de resolucion, puede ser "LOW", "MEDIUM" o "HIGH".
    * DESCRIPCIÓN: Establece la resolución de la imagen capturada.
    * ARGS_OUT: None
    ********'''
    def setImageResolution(self, resolution):
        # Se establece la resolución de captura de la webcam
        # Puede añadirse algún valor superior si la cámara lo permite
        # pero no modificar estos
        if resolution == "LOW":
            self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, 160)
            self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 120)
        elif resolution == "MEDIUM":
            self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, 320)
            self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 240)
        elif resolution == "HIGH":
            self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
            self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

    '''
    ********
    * FUNCIÓN: buttonsCallback(self, button)
    * ARGS_IN: self - la clase
    * DESCRIPCIÓN: Función que gestiona los callbacks de los botones.
    * ARGS_OUT: None
    ********'''
    def buttonsCallback(self, button):
        # si se pulsa el botón Registrarse, se obtienen los datos introducidos
        # por el usuario, y se comprueban que son válidos, si está correcto,
        # se lanza el hilo que maneja la conexión de control.
        if button == "Registrarse":
            nick = self.app.getEntry("Nick\t\t")
            password = self.app.getEntry("Contraseña\t")
            IP = self.app.getEntry("IP\t\t")
            puerto_control = self.app.getEntry("Puerto de control\t")
            puerto_datos = self.app.getEntry("Puerto de datos\t")
            protocolo = self.app.getEntry("Protocolo\t\t")

            if not valid_IP(IP):
                self.app.infoBox("Error", "La IP no es valida.")
                return

            if not valid_puerto(puerto_datos):
                self.app.infoBox("Error", "El puerto de control no es valido.")
                return

            if not valid_puerto(puerto_datos):
                self.app.infoBox("Error", "El puerto de datos no es valido.")
                return

            ans = call_register(nick, password, IP, puerto_control, protocolo)
            if ans is None:
                self.app.infoBox("Error", "La contraseña es erronea.")
                return
            # Una vez registrado limpia los campos.
            self.buttonsCallback("Limpiar")

            self.app.infoBox("Exito", "Te has registrado como: " + str(ans))
            self.local_nick = nick
            self.local_IP = IP
            self.local_puerto_control = int(puerto_control)
            self.local_puerto_datos = int(puerto_datos)

            # Habilita los Tabs una vez registrado.
            self.app.setTabbedFrameDisabledTab("Tabs", "Lista de usuarios", False)
            self.app.setTabbedFrameDisabledTab("Tabs", "Buscar usuarios", False)
            self.app.setTabbedFrameDisabledTab("Tabs", "Configuracion", False)
            # Deshabilita el Tab de registrarse de nuevo.
            self.app.setTabbedFrameDisabledTab("Tabs", "Registrarse", True)

            # Hilo que se encarga de recibir una llamada entrante. Solo disponible
            # una vez registrado.
            thread = threading.Thread(target=manage_puerto_control, args=(self,))
            thread.start()

        # si se pulsa el botón Limpiar, se limpian los todos los campos, excepto aquellos que
        # por comodidad para el usuario, no se borran.
        elif button == "Limpiar":
            self.app.clearEntry("Nick\t\t")
            self.app.clearEntry("Contraseña\t")
            # Pone default values.
            self.app.setEntry("IP\t\t", self.local_IP)
            self.app.setEntry("Protocolo\t\t", "V0")
            self.app.setEntry("Puerto de control\t", "4444")
            self.app.setEntry("Puerto de datos\t", "4445")
            self.app.setEntryFocus("Nick\t\t")

        # si se pulsa el botón de Refrescar, se actualiza la lista de usuarios regsitrados.
        elif button == "Refrescar":
            # Actualiza los nicks registrados actualmente.
            database = call_users_list()
            nicks = database[0]

            self.app.updateListBox("Nicks de usuarios registrados", nicks)

        # si se pulsa el botón Buscar, a partir del nombre, se obtendrán los demás datos 
        # del usuario solicitado, y se mostrará por pantalla.
        elif button == "Buscar":
            self.valid_call_user = False
            nick = self.app.getEntry("Nick a buscar")
            ans = call_query(nick)
            if ans is None:
                self.app.infoBox("Error", "No existe ningun usuario que se llame: " + nick)
                return
            self.call_nick, self.call_IP, self.call_puerto_control, self.call_protocolo = ans
            self.app.setLabel("UserBuscar",
                              "Nick: " + self.call_nick +
                              "\nIP:" + self.call_IP +
                              "\nPuerto de control:" + str(self.call_puerto_control) +
                              "\nProtocolo:" + self.call_protocolo)

            if not valid_IP(self.call_IP):
                return

            if not valid_puerto(self.call_puerto_control):
                return

            self.call_puerto_control = int(self.call_puerto_control)

            # El usuario que se ha buscado se puede intentar llamar.
            self.valid_call_user = True

        # si se pulsa el botón Llamar, se manda llama a call_calling que hace la petición.
        elif button == "Llamar":
            # Si no se ha buscado a un usuario, o este no es valido, avisar.
            if not self.valid_call_user:
                self.app.infoBox("Error", "Primero busca un usuario valido para llamarlo.")
                return

            # Compureba que no te llamas a ti mismo.
            if self.local_IP == self.call_IP and self.local_puerto_control == self.call_puerto_control:
                self.app.infoBox("Error", "No puedes llamarte a ti mismo.")
                return

            call_calling(self)

        # si se pulsa Establece video, lo que se haya escrito en la casilla, se mandará por defecto
        # en su siguientes llamadas.
        elif button == "Establecer video":
            # Comprueba que el video existe.
            file_name = self.app.getEntry("Nombre del video")
            try:
                with open(file_name, 'r'):
                    pass
            except FileNotFoundError:
                self.app.infoBox("Error", "Archivo no encontrado.")
                return

            self.fichero_video = file_name
            self.cap = cv2.VideoCapture(self.fichero_video)

        # si se pulsa Webcam, se establece por defecto que se use la webcam en la siguientes llamadas.
        elif button == "Webcam":
            # Si ya esta la webcam no volver a capturar 0, sino da un warning.
            if self.fichero_video == 0:
                return
            self.fichero_video = 0
            self.cap = cv2.VideoCapture(self.fichero_video)

        # si se pulsa Usar video, se cambiará el video que se esta reproduciendo en la llamada por
        # el indicado en la casilla.
        elif button == "Usar video":
            # Se comprueba que el video existe.
            file_name = self.app.getEntry("Nombre del video a transmitir")
            try:
                with open(file_name, 'r'):
                    pass
            except FileNotFoundError:
                self.app.infoBox("Error", "Archivo no encontrado.")
                return

            self.fichero_video_enviando = file_name
            self.cap_envio = cv2.VideoCapture(self.fichero_video_enviando)

        # si se pulsa Usar webcam, se cambiará el video que se esta reproduciendo en la llamada por la webcam.
        elif button == "Usar webcam":
            # Si ya esta la webcam no volver a capturar 0, sino da un warning.
            if self.fichero_video_enviando == 0:
                return
            self.fichero_video_enviando = 0
            self.cap_envio = cv2.VideoCapture(self.fichero_video_enviando)

        # si se pulsa a colgar, se llama a la función que se encarga de terminarla.
        elif button == "Colgar":
            call_call_end(self)

        # si se pulsa a pausar, se llama a la función que se encarga de pausarla.
        elif button == "Pausar":
            self.video_is_paused = True
            call_call_hold(self)

        # si se pulsa a reanudar, se llama a la función que se encarga de reanudarla.
        elif button == "Renaudar":
            if self.video_is_paused:
                call_call_resume(self)
            self.video_is_paused = False

        # si se pulsa Aceptar, se llama a la función que se encarga de que se establezca la llamada.
        elif button == "Aceptar":
            call_call_accepted(self)

        # si se pulsa Rechazar, se llama a la función que se encarga de que se indique se le ha rechazado la llamada.
        elif button == "Rechazar":
            call_call_denied(self)

        # si se pulsa a Salir, se para la interfaz.
        elif button == "Salir":
            # Se cierra la interfaz grafica.
            # Al cerrar la GUI llama a call_quit().
            self.app.stop()

        # si se pulsa a LLamar al seleccionado, se obtienen los datos relacionados al nick seleccionado,
        # y se intenta llamarlo.
        elif button == "Llamar al seleccionado":
            # Busca al elemento seleccionado, si hay varios llama al primero de ellos.
            element = self.app.getListBox("Nicks de usuarios registrados")
            if element == []:
                self.app.infoBox("Error", "Seleccione un usuario a llamar.")
                return
            nick = element[0]
            ans = call_query(nick)
            if ans is None:
                self.app.infoBox("Error", "No existe ningun usuario que se llame: " + nick)
                return
            self.call_nick, self.call_IP, self.call_puerto_control, self.call_protocolo = ans

            if not valid_IP(self.call_IP):
                self.app.infoBox("Error", "El usuario tiene una IP invalida.")
                return

            if not valid_puerto(self.call_puerto_control):
                self.app.infoBox("Error", "El usuario tiene un puerto invalido.")
                return

            self.call_puerto_control = int(self.call_puerto_control)

            if self.local_IP == self.call_IP and self.local_puerto_control == self.call_puerto_control:
                self.app.infoBox("Error", "No puedes llamarte a ti mismo.")
                return

            call_calling(self)

        elif button == "Resolucion baja":
            self.setImageResolution(RESOLUCION_LOW)
            self.actual_resolution = RESOLUCION_LOW

        elif button == "Resolucion media":
            self.setImageResolution(RESOLUCION_MEDIUM)
            self.actual_resolution = RESOLUCION_MEDIUM

        elif button == "Resolucion alta":
            self.setImageResolution(RESOLUCION_HIGH)
            self.actual_resolution = RESOLUCION_HIGH


# Main, empieza aqui a ejecutarse.
if __name__ == '__main__':
    vc = VideoClient("660x690")

    # Lanza el bucle principal del GUI
    # El control ya NO vuelve de esta función, por lo que todas las
    # acciones deberán ser gestionadas desde callbacks y threads
    vc.start()
