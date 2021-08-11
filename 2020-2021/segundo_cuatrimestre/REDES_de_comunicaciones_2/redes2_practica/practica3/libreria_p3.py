"""
 Fichero que contiene la implementacion de funciones auxiliares para hacer queries al servidor de descubrimiento.
 Autores:
    Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
    Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
 Grupo 2301, Doble Grado.
"""

import socket

# Maximo buffer para el recv, tiene que ser muy grande para que la imagen quepa completa en una sola lectura.
MAX_BUFF_LEN = 60000

# Segundos del timeout para la lectura del socket.
TIME_OUT = 1

# Decodificacion de bytes <-> string.
UTF = 'utf-8'

# Datos del server.
serverName = 'vega.ii.uam.es'
serverIP = socket.gethostbyname(serverName)
serverPort = 8000

# Se guarda el servidorDescubrimientoSocket para no cerrarlo y abrirlo multiples veces.
servidorDescubrimientoSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    servidorDescubrimientoSocket.connect((serverIP, serverPort))
except (ConnectionRefusedError, socket.timeout):
    print("Error, no se ha podido establecer conexion con el servidor de descubrimiento.")
    exit(0)
print("Conectado con el servidor de descubrimiento.")
# Ponemos un timeout socket.
servidorDescubrimientoSocket.settimeout(TIME_OUT)


'''
********
* FUNCIÓN: get_response(query)
* ARGS_IN: query  - mensaje que se quiere enviar al servidor de descubrimiento.
* DESCRIPCIÓN: envia el mensaje pasado por argumento al servidor, y obtiene la respuesta
*              del servidor. Todas las queries son enviadas y recibidas por esta funcion.
* ARGS_OUT: Devuelve la respuesta dada por el servidor o None si ha habido algun error.
********'''
def get_response(query):
    # print("Query al servidor de descubrimiento:", query)
    query = bytes(query, UTF)
    servidorDescubrimientoSocket.send(query)

    # Siempre se tiene que hacer al menos una lectura, si da timeout es un error.
    try:
        ans = servidorDescubrimientoSocket.recv(MAX_BUFF_LEN)
    except socket.timeout:
        print("Error en get_response, timeout.")
        return None

    try:
        # Mientras el socket no de timeout se siguen haciendo lecturas.
        while True:
            ret = servidorDescubrimientoSocket.recv(MAX_BUFF_LEN)
            # Si el server cierra la conexion, recv devuelve b''.
            if ret == b'':
                break
            ans += ret
    except socket.timeout:
        # Como siempre se va a generar un timeout aqui, no hay que mostrar un error.
        pass

    ans = ans.decode(UTF)
    # print("Respuesta del servidor de descubrimiento:", ans)
    if ans == "NOK SYNTAX_ERROR":
        return None
    return ans


'''
********
* FUNCIÓN: call_users_list()
* ARGS_IN: None
* DESCRIPCIÓN: genera un mensaje con el cual puede obtener la lista de usuarios registrados ('LIST_USERS')
*              en el sistema, y obtiene la respuesta llamando a la función get_response(query).
*              Comprueba que no hay errores, y si no los hay, obtiene la lista de usuarios
*              parseando la respuesta del servidor.
* ARGS_OUT: Devuelve cuatro listas, una con todos los nicks de usuario, otra con todas las ips,
*           otra con todos los puertos de control, y otra con los protocolos soportados de cada usuario.
*           Si surge algún error, se devuelven estas listas vacías.
********'''
def call_users_list():
    query = 'LIST_USERS'
    # Se leera mientras no de timeout, no se van a contar usuarios reales ya que
    # es posible que un usuario introduzca el caracter '#', que es el separador de referencia.
    ans = get_response(query)

    if ans is None or 'OK USERS_LIST' != ans[:13]:
        print('Error en call_users_list.')
        # Return empty results.
        return [[], [], [], []]

    ans = ans[14:]
    ptr = ans.index(' ')
    # El numero de usuarios que hay registrados.
    # expected_users = int(ans[:ptr])
    # Se guarda el resto de la respuesta en ans.
    ans = ans[ptr+1:]
    # Los usuarios se separan mediante el caracter #.
    ans = ans.split('#')

    nicks = []
    ips = []
    puertos_control = []
    protocolos = []
    # Se van guardando los datos de los usuarios validos (Aquellos que tienen 4 campos).
    for user in ans:
        # Si un usuario no tiene 4 campos no se guarda.
        user = user.split(' ')
        if len(user) != 4:
            continue
        a, b, c, d = user

        # Si el nick esta vacio, no se guarda.
        if a == '':
            continue

        nicks += [a]
        ips += [b]
        puertos_control += [c]
        protocolos += [d]

    return [nicks, ips, puertos_control, protocolos]


'''
********
* FUNCIÓN: call_register(nick, password, IP, puerto_control, protocolo)
* ARGS_IN: nick             - nombre del usuario a registrar
*          password         - contraseña del usuario a registrar
*          IP               - IP del usuario a registrar
*          puerto_control   - puerto de control del usuario a registrar
*          protocolo        - protocolos soportados por el usuario a registrar.
* DESCRIPCIÓN: genera un mensaje con el cual puede registrarse 
*              ("REGISTER " + nick + " " + IP + " " + puerto_control + " " + password + " " + protocolo)
*              y obtiene la respuesta llamando a la función get_response(query).
*              Comprueba que no hay errores, y si no los hay, obtiene el nick.
* ARGS_OUT: devuelve el nick del usuario registrado, si no hay errores. Si los hay, se devuelve None.
********'''
def call_register(nick, password, IP, puerto_control, protocolo):
    query = "REGISTER " + nick + " " + IP + " " + puerto_control + " " + password + " " + protocolo
    ans = get_response(query)
    if ans is None or ans == "NOK WRONG_PASS":
        print("Error en call_register.")
        return None

    # 2 es la posicion del nick que se acaba de registrar.
    return ans.split(' ')[2]


'''
********
* FUNCIÓN: call_query(nick)
* ARGS_IN: nick  - nick del usuario del cual se quiere obtener la IP y el puerto de control.
* DESCRIPCIÓN: genera un mensaje con el cual puede obtener la IP, el puerto de control y el protocolo del usuario
*              ("QUERY " + nick) y obtiene la respuesta llamando a la función get_response(query).
*              Comprueba que no hay errores, y si no los hay, obtiene los datos del usuario de la
*              respuesta del servidor.
* ARGS_OUT: Devuelve estos datos si no ha habido errores, en otro caso, None.
********'''
def call_query(nick):
    query = "QUERY " + nick
    ans = get_response(query)
    if ans is None or ans == "NOK USER_UNKNOWN":
        print("Error en call_query.")
        return None

    ans = ans.split(' ')
    return ans[2], ans[3], ans[4], ans[5]


'''
********
* FUNCIÓN: call_quit()
* ARGS_IN: None
* DESCRIPCIÓN: genera un mensaje con el cual puede cerrar la sesión ('QUIT') 
*              y obtiene la respuesta llamando a la función get_response(query).
*              Comprueba que el servidor responde con 'BYE', y si es así, se cierra
*              la conexión con el servidor.
* ARGS_OUT: Devuelve True para que la GUI acabe.
********'''
def call_quit():
    query = "QUIT"
    ans = get_response(query)
    if ans is None or ans != "BYE":
        print("Error en call_quit")
        return True

    # Cierra la conexion TCP con el servidor de descubrimiento.
    servidorDescubrimientoSocket.close()
    print("Desconectado del servidor de descubrimiento.")
    # Devuelve True para que la GUI acabe.
    return True


'''
********
* FUNCIÓN: valid_IP(IP)
* ARGS_IN: IP - IP que se quiere comprobar que es válida
* DESCRIPCIÓN:  comprueba que la IP pasada por argumento es válida.
* ARGS_OUT: Devuelve True en caso de que la IP sea válida, y False, en otro caso.
********'''
def valid_IP(IP):
    # La IP debe de tener 3 puntos '.'.
    if IP.count('.') != 3:
        return False

    # Entre cada punto debe de haber un numero.
    a, b, c, d = IP.split('.')
    try:
        a = int(a)
        b = int(b)
        c = int(c)
        d = int(d)
    except ValueError:
        return False

    # Ese numero debe de estar entre 0 y 255.
    if not (0 <= a <= 255 and 0 <= b <= 255 and 0 <= c <= 255 and 0 <= d <= 255):
        return False
    return True


'''
********
* FUNCIÓN: valid_puerto(puerto)
* ARGS_IN: puerto - puerto que se quiere comprobar que es válido
* DESCRIPCIÓN:  comprueba que el puerto pasado por argumento es válido.
* ARGS_OUT: Devuelve True en caso de que el puerto sea válido, y False, en otro caso.
********'''
def valid_puerto(puerto):
    # El puerto debe de ser un numero.
    try:
        puerto = int(puerto)
    except ValueError:
        return False

    # El puerto debe de estar entre 1 y 65536 (65536 = 2^16).
    if not (1 <= puerto <= 65536):
        return False
    return True
