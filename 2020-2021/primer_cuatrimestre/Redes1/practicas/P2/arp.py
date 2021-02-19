'''
    arp.py
    Implementación del protocolo ARP y funciones auxiliares que permiten realizar resoluciones de direcciones IP.
    Autor profesor: Javier Ramos <javier.ramos@uam.es>
    Autor: Junco de las Heras <junco.heras@estudiante.uam.es> y Marta Vaquerizo <marta.vaquerizo@estudiante.uam.es>
    2019 EPS-UAM
'''
from ethernet import *
import logging
import socket
import struct
import fcntl
import time
from threading import Lock
from expiringdict import ExpiringDict

#Semáforo global 
globalLock = Lock()
#Dirección de difusión (Broadcast)
broadcastAddr = bytes([0xFF]*6)
#Cabecera ARP común a peticiones y respuestas. Específica para la combinación Ethernet/IP
ARPHeader = bytes([0x00,0x01,0x08,0x00,0x06,0x04])
#longitud (en bytes) de la cabecera común ARP
ARP_HLEN = 6

#Variable que alamacenará que dirección IP se está intentando resolver
requestedIP = None
#Variable que alamacenará que dirección MAC resuelta o None si no se ha podido obtener
resolvedMAC = None
#Variable que alamacenará True mientras estemos esperando una respuesta ARP
awaitingResponse = False

#Variable para proteger la caché
cacheLock = Lock()
#Caché de ARP. Es un diccionario similar al estándar de Python solo que eliminará las entradas a los 10 segundos
cache = ExpiringDict(max_len=100, max_age_seconds=10)

#Tamaño de la cabecera de ARP
TAM_CABECERA_ARP = 28

# Variables globales para initARP
arpInitialized = False
myIP = None
myMAC = None

def getIP(interface:str) -> int:
    '''
        Nombre: getIP
        Descripción: Esta función obtiene la dirección IP asociada a una interfaz. Esta funció NO debe ser modificada
        Argumentos:
            -interface: nombre de la interfaz
        Retorno: Entero de 32 bits con la dirección IP de la interfaz
    '''
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    ip = fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', (interface[:15].encode('utf-8')))
    )[20:24]
    s.close()
    return struct.unpack('!I',ip)[0]

def printCache()->None:
    '''
        Nombre: printCache
        Descripción: Esta función imprime la caché ARP
        Argumentos: Ninguno
        Retorno: Ninguno
    '''
    print('{:>12}\t\t{:>12}'.format('IP','MAC'))
    with cacheLock:
        print("Tamanio cache: " + str(len(cache)))
        for k in cache:
            if k in cache:
                print ('{:>12}\t\t{:>12}'.format(socket.inet_ntoa(struct.pack('!I',k)),':'.join(['{:02X}'.format(b) for b in cache[k]])))



def processARPRequest(data:bytes,MAC:bytes)->None:
    '''
        Nombre: processARPRequest
        Decripción: Esta función procesa una petición ARP. Esta función debe realizar, al menos, las siguientes tareas:
            -Extraer la MAC origen contenida en la petición ARP
            -Si la MAC origen de la trama ARP no es la misma que la recibida del nivel Ethernet retornar
            -Extraer la IP origen contenida en la petición ARP
            -Extraer la IP destino contenida en la petición ARP
            -Comprobar si la IP destino de la petición ARP es la propia IP:
                -Si no es la propia IP retornar
                -Si es la propia IP:
                    -Construir una respuesta ARP llamando a createARPReply (descripción más adelante)
                    -Enviar la respuesta ARP usando el nivel Ethernet (sendEthernetFrame)
        Argumentos:
            -data: bytearray con el contenido de la trama ARP (después de la cabecera común)
            -MAC: dirección MAC origen extraída por el nivel Ethernet
        Retorno: Ninguno
    '''

    origenMAC   = data[0:6]
    origenIP    = data[6:10]
    destIP      = data[16:20]
    
    # Se comprueba que la MAC origen de ARP es la misma que la recivida de Ethernet.
    if origenMAC != MAC:
        return
  
    # Se comprueba que se tenga la IP del destinatario.
    if int(destIP.hex(), 16) != myIP:
        return

    # Se crea un ARP reply y se envia.
    respuesta = createARPReply(int(origenIP.hex(), 16), MAC)

    sendEthernetFrame(respuesta,len(respuesta), 0x0806, MAC)
    

def processARPReply(data:bytes,MAC:bytes)->None:
    '''
        Nombre: processARPReply
        Decripción: Esta función procesa una respuesta ARP. Esta función debe realizar, al menos, las siguientes tareas:
            -Extraer la MAC origen contenida en la petición ARP
            -Si la MAC origen de la trama ARP no es la misma que la recibida del nivel Ethernet retornar
            -Extraer la IP origen contenida en la petición ARP
            -Extraer la MAC destino contenida en la petición ARP
            -Extraer la IP destino contenida en la petición ARP
            -Comprobar si la IP destino de la petición ARP es la propia IP:
                -Si no es la propia IP retornar
                -Si es la propia IP:
                    -Comprobar si la IP origen se corresponde con la solicitada (requestedIP). Si no se corresponde retornar
                    -Copiar la MAC origen a la variable global resolvedMAC
                    -Añadir a la caché ARP la asociación MAC/IP.
                    -Cambiar el valor de la variable awaitingResponse a False
                    -Cambiar el valor de la variable requestedIP a None
        Las variables globales (requestedIP, awaitingResponse y resolvedMAC) son accedidas concurrentemente por la función ARPResolution y deben ser protegidas mediante un Lock.
        Argumentos:
            -data: bytearray con el contenido de la trama ARP (después de la cabecera común)
            -MAC: dirección MAC origen extraída por el nivel Ethernet
        Retorno: Ninguno
    '''
    global requestedIP,resolvedMAC,awaitingResponse,cache
    
    #se obtienen las direcciones IP y MAC de la cabereca de ARP    
    origenMAC   = data[0:6]
    origenIP    = data[6:10]
    destMAC     = data[10:16]
    destIP      = data[16:20]

    #se comprueba que la MAC origen de ARP es la misma que la recivida de Ethernet
    if origenMAC != MAC:
        return

    #se comprueba que la direccion IP destino es la nuestra, y que la origen es la solicitada
    if int(destIP.hex(), 16) != myIP:
        return

    # Todo este bloque necesita el globalLock.
    with globalLock:
        if int(origenIP.hex(), 16) != requestedIP:
            return

        #guardamos la relacion en la chaché entre la IP y la MAC
        with cacheLock:
            cache[requestedIP] = origenMAC

        resolvedMAC = origenMAC
        awaitingResponse = False
        requestedIP = None
       

def createARPRequest(ip:int) -> bytes:
    '''
        Nombre: createARPRequest
        Descripción: Esta función construye una petición ARP y devuelve la trama con el contenido.
        Argumentos: 
            -ip: dirección a resolver 
        Retorno: Bytes con el contenido de la trama de petición ARP
    '''
    global myMAC,myIP
    frame = ARPHeader + bytes([0x00, 0x01]) + myMAC + myIP.to_bytes(4, 'big') + broadcastAddr + ip.to_bytes(4, 'big')

    return frame

    
def createARPReply(IP:int, MAC:bytes) -> bytes:
    '''
        Nombre: createARPReply
        Descripción: Esta función construye una respuesta ARP y devuelve la trama con el contenido.
        Argumentos: 
            -IP: dirección IP a la que contestar
            -MAC: dirección MAC a la que contestar
        Retorno: Bytes con el contenido de la trama de petición ARP
    '''
    global myMAC,myIP
    frame = ARPHeader + bytes([0x00, 0x02]) + myMAC + myIP.to_bytes(4, 'big') + MAC + IP.to_bytes(4, 'big')

    return frame


def process_arp_frame(us:ctypes.c_void_p,header:pcap_pkthdr,data:bytes,srcMac:bytes) -> None:
    '''
        Nombre: process_arp_frame
        Descripción: Esta función procesa las tramas ARP. 
            Se ejecutará por cada trama Ethenet que se reciba con Ethertype 0x0806 (si ha sido registrada en initARP). 
            Esta función debe realizar, al menos, las siguientes tareas:
                -Extraer la cabecera común de ARP (6 primeros bytes) y comprobar que es correcta
                -Extraer el campo opcode
                -Si opcode es 0x0001 (Request) llamar a processARPRequest (ver descripción más adelante)
                -Si opcode es 0x0002 (Reply) llamar a processARPReply (ver descripción más adelante)
                -Si es otro opcode retornar de la función
                -En caso de que no exista retornar
        Argumentos:
            -us: Datos de usuario pasados desde la llamada de pcap_loop. En nuestro caso será None
            -header: cabecera pcap_pktheader
            -data: array de bytes con el contenido de la trama ARP
            -srcMac: MAC origen de la trama Ethernet que se ha recibido
        Retorno: Ninguno
    '''

    cabecera = data[0:6]
    if cabecera != ARPHeader:
        return

    opcode = data[6:8]
    # Se envia la data sin la cabecera, pedido por los argumentos de las funciones.
    data_sin_cabecera = data[8:]
    if int(opcode.hex(), 16) == 0x0001:
        processARPRequest(data_sin_cabecera, srcMac)
    elif int(opcode.hex(), 16) == 0x0002:
        processARPReply(data_sin_cabecera, srcMac)


def initARP(interface:str) -> int:
    '''
        Nombre: initARP
        Descripción: Esta función construirá inicializará el nivel ARP. Esta función debe realizar, al menos, las siguientes tareas:
            -Registrar la función del callback process_arp_frame con el Ethertype 0x0806
            -Obtener y almacenar la dirección MAC e IP asociadas a la interfaz especificada
            -Realizar una petición ARP gratuita y comprobar si la IP propia ya está asignada. En caso positivo se debe devolver error.
            -Marcar la variable de nivel ARP inicializado a True
    '''
    global myIP,myMAC,arpInitialized

    if arpInitialized is not False:
        return -1

    registerCallback(process_arp_frame, 0x0806)
    myIP  = getIP(interface)
    myMAC = getHwAddr(interface)

    respuesta = ARPResolution(myIP)
    if respuesta is not None:
        return -1

    arpInitialized = True

    return 0

def ARPResolution(ip:int) -> bytes:
    '''
        Nombre: ARPResolution
        Descripción: Esta función intenta realizar una resolución ARP para una IP dada y devuelve la dirección MAC asociada a dicha IP 
            o None en caso de que no haya recibido respuesta. Esta función debe realizar, al menos, las siguientes tareas:
                -Comprobar si la IP solicitada existe en la caché:
                -Si está en caché devolver la información de la caché
                -Si no está en la caché:
                    -Construir una petición ARP llamando a la función createARPRequest (descripción más adelante)
                    -Enviar dicha petición
                    -Comprobar si se ha recibido respuesta o no:
                        -Si no se ha recibido respuesta reenviar la petición hasta un máximo de 3 veces. Si no se recibe respuesta devolver None
                        -Si se ha recibido respuesta devolver la dirección MAC
            Esta función necesitará comunicarse con el la función de recepción (para comprobar si hay respuesta y la respuesta en sí) mediante 3 variables globales:
                -awaitingResponse: indica si está True que se espera respuesta. Si está a False quiere decir que se ha recibido respuesta
                -requestedIP: contiene la IP por la que se está preguntando
                -resolvedMAC: contiene la dirección MAC resuelta (en caso de que awaitingResponse) sea False.
            Como estas variables globales se leen y escriben concurrentemente deben ser protegidas con un Lock
    '''
    global requestedIP,awaitingResponse,resolvedMAC

    # En caso de que se pregunte por la propia IP y que este inicializado.
    if ip == myIP and arpInitialized == True:
        # No hace falta guardarlo en el cache.
        return myMAC

    with cacheLock:
        if cache.get(ip) is not None:
            return cache.get(ip)    

    peticion = createARPRequest(ip)
    with globalLock:
        awaitingResponse = True
        requestedIP = ip

    n_intentos = 3 # Dado en la descripcion de la funcion
    for i in range(n_intentos):
        sendEthernetFrame(peticion,len(peticion), 0x0806, broadcastAddr)
        # Esperas la respuesta 0.1 segundo.
        time.sleep(0.1)
        with globalLock:
            if awaitingResponse is False:
                return resolvedMAC

    return None
