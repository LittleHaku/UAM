#!/usr/bin/python
'''
    practica3.py
    Programa principal que realiza el análisis de tráfico sobre una traza PCAP.
    Autor: Javier Ramos <javier.ramos@uam.es>
    Autor: Junco de las Heras <junco.heras@estudiante.uam.es> y Marta Vaquerizo <marta.vaquerizo@estudiante.uam.es>
    2020 EPS-UAM
'''


import sys
import argparse
from argparse import RawTextHelpFormatter
import time
import logging
import shlex
import subprocess
import struct
import pandas as pd
from io import StringIO
import os
import warnings
warnings.filterwarnings("ignore")
import matplotlib.pyplot as plt
# ESTE IMPORT DA EL WARNING "Error retrieving accessibility bus address" ...
import matplotlib.ticker as mtick

# El nombre donde guardar todas las imagenes
PATH = "resultados/"

'''
    Función: calcularECDF
    Entrada: 
        -datos: lista con los datos sobre los que calcular la ECDF
    Salida: :
        -datos: lista con los valores x (datos de entrada)
        -y: lista con los valores de probabilidad acumulada para cada dato de entrada
    Descripción:  Esta función recibe una lista de datos y calcula la función empírica de distribución 
    acumulada sobre los mismos. Los datos se devuelven listos para ser pintados.
'''
def calcularECDF(datos):
    datos.sort()
    n = len (datos)
    y = [(i-1)/n for i in range(1,n+1)]

    return datos,y


'''
    Función: ejecutarComandoObtenerSalida
    Entrada: 
        -comando: cadena de caracteres con el comando a ejecutar
    Salida: 
        -codigo_retorno: código numérico que indica el retorno del comando ejecutado.
        Si este valor es 0, entonces el comando ha ejecutado correctamente.
        -salida_retorno: cadena de caracteres con el retorno del comando. Este retorno
        es el mismo que obtendríamos por stdout al ejecutar un comando de terminal.

    Descripción: Esta función recibe una cadena con un comando a ejecutar, lo ejecuta y retorna
    tanto el código de resultado de la ejecución como la salida que el comando produzca por stdout
'''
def ejecutarComandoObtenerSalida(comando):
    proceso = subprocess.Popen(shlex.split(comando), stdout=subprocess.PIPE)
    salida_retorno = ''
    while True:
        
        salida_parcial = proceso.stdout.readline()
        if salida_parcial.decode() == '' and proceso.poll() is not None:
            break
        if salida_parcial:
            salida_retorno += salida_parcial.decode()
    codigo_retorno = proceso.poll()
    return codigo_retorno,salida_retorno


'''
    Función: pintarECDF
    Entrada:
        -datos: lista con los datos que se usarán para calcular y pintar la ECDF
        -nombre_fichero: cadena de caracteres con el nombre del fichero donde se guardará la imagen
        (por ejemplo figura.png)
        -titulo: cadena de caracteres con el título a pintar en la gráfica
        -titulo_x: cadena de caracteres con la etiqueta a usar para el eje X de la gráfica
        -titulo_y: cadena de caracteres con la etiqueta a usar para el eje Y de la gráfica
    Salida: 
        -Nada

    Descripción: Esta función pinta una gráfica ECDF para unos datos de entrada y la guarda en una imagen
'''
def pintarECDF(datos,nombre_fichero,titulo,titulo_x,titulo_y):
    
    x, y = calcularECDF(datos)
    x.append(x[-1])
    y.append(1) 
    fig1, ax1 = plt.subplots()
    plt.step(x, y, '-')
    _ = plt.xticks(rotation=45)
    plt.title(titulo)
    fig1.set_size_inches(12, 10)
    plt.tight_layout()
    plt.locator_params(nbins=20)
    ax1.set_xlabel(titulo_x)
    ax1.set_ylabel(titulo_y)
    plt.savefig(nombre_fichero, bbox_inches='tight')


'''
    Función: pintarSerieTemporal
    Entrada:
        -x: lista de tiempos en formato epoch y granularidad segundos
        -y: lista con los valores a graficar
        -nombre_fichero: cadena de caracteres con el nombre del fichero donde se guardará la imagen
        (por ejemplo figura.png)
        -titulo: cadena de caracteres con el título a pintar en la gráfica
        -titulo_x: cadena de caracteres con la etiqueta a usar para el eje X de la gráfica
        -titulo_y: cadena de caracteres con la etiqueta a usar para el eje Y de la gráfica
    Salida: 
        -Nada

    Descripción: Esta función pinta una serie temporal dados unos datos x e y de entrada y la guarda en una imagen
'''
def pintarSerieTemporal(x,y,nombre_fichero,titulo,titulo_x,titulo_y):
   
    fig1, ax1 = plt.subplots()
    plt.plot(x, y, '-')
    _ = plt.xticks(rotation=45)
    plt.title(titulo)
    fig1.set_size_inches(12, 10)
    plt.gcf().autofmt_xdate()
    plt.gca().xaxis.set_major_locator(mtick.FixedLocator(x))
    plt.gca().xaxis.set_major_formatter(mtick.FuncFormatter(lambda pos,_: time.strftime("%d-%m-%Y %H:%M:%S",time.localtime(pos))))
    plt.tight_layout()
    plt.locator_params(nbins=20)
    ax1.set_xlabel(titulo_x)
    ax1.set_ylabel(titulo_y)
    plt.savefig(nombre_fichero, bbox_inches='tight')


'''
    Función: pintarTarta
    Entrada:
        -etiquetas: lista con cadenas de caracteres que contienen las etiquetas a usar en el gráfico de tarta
        -valores: lista con los valores a graficar
        -nombre_fichero: cadena de caracteres con el nombre del fichero donde se guardará la imagen
        (por ejemplo figura.png)
        -titulo: cadena de caracteres con el título a pintar en la gráfica
        
    Salida: 
        -Nada

    Descripción: Esta función pinta un gráfico de tarta dadas unas etiquetas y valores de entrada y lo guarda en una imagen
'''
def pintarTarta(etiquetas,valores,nombre_fichero,titulo):
   
    explode = tuple([0.05]*(len(etiquetas)))
 
    fig1, ax1 = plt.subplots()
    plt.pie(valores, autopct='%1.1f%%', startangle=90, pctdistance=0.85)
    plt.legend(etiquetas, loc="best")
    plt.title(titulo)
    centre_circle = plt.Circle((0,0),0.70,fc='white')
    fig1 = plt.gcf()
    fig1.gca().add_artist(centre_circle)
    fig1.set_size_inches(12, 10)
    ax1.axis('equal')  
    plt.tight_layout()
    plt.savefig(nombre_fichero, bbox_inches='tight')

# Calcula las 5 parejas (clave, valor) con los valores mas altos.
# Devuelve 2 listas, una con las claves mas grandes y otra con los valores de las claves.
# Argumento m: es un mapa generico.
def top5_value(m):
	# Crea una lista con los elementos ordenados en orden inverso (mayor a menor) por el valor (item[1]) de la clave.
    v = list(sorted(m.items(), key = lambda item : item[1], reverse = True))
    key_max = []
    value_max = []
    for i in range(min(len(m), 5)):
    	key_max.append(v[i][0])
    	value_max.append(v[i][1])
    return key_max, value_max

# Grafica el top5 parejas (clave, bytes) con mas bytes.
# Argumento salida: output de tshark.
# Argumento nombre_fichero: nombre del fichero que va a contener la grafica.
# Argumento titulo: titulo de la grafica.
def top5_bytes(salida, nombre_fichero, titulo):
    datos={}

    # Se meten los datos en un diccionario de la forma clave origen o destino y valor sumatorio en bytes.
    for linea in salida.split('\n'):

        dato=linea.split('\t')
        if len(dato) == 2 and dato[0] != "" and dato[1] != "":
           datos[dato[0]] = datos.get(dato[0], 0) + int(dato[1])
    
    key_max, value_max = top5_value(datos)
    pintarTarta(key_max, value_max, nombre_fichero, titulo)


# Grafica el top5 parejas (clave, numero de paquetes) con mayor numero de paquetes.
# Argumento salida: output de tshark.
# Argumento nombre_fichero: nombre del fichero que va a contener la grafica.
# Argumento titulo: titulo de la grafica.
def top5_paquetes(salida, nombre_fichero, titulo):
    datos={}

    # Se meten los datos en un diccionario de la forma clave origen o destino y valor el numero de paquetes.
    for linea in salida.split('\n'):
        dato = linea
        if dato != "":
            datos[dato] = datos.get(dato, 0) + 1

    key_max, value_max = top5_value(datos)
    pintarTarta(key_max, value_max, nombre_fichero, titulo)

# Calcula las listas para los ejes x e y del ejericio 4 y poderlos graficar con pintarSerieTemporal
# Argumento salida: output de tshark.
def calcularSerieTemporal(salida):
	# Se coge el tiempo epoch y se trunca al segundo (granulado de segundo), y se suman
    # todos los bytes que haya en ese segundo.
    # last_second guarda -1 si es el primer dato que se lee o el tiempo epoch que se esta procesando.
    # last_byte es el numero de bytes que se tienen el segundo last_second.
    x_value = []
    y_value = []
    last_second = -1
    last_byte = 0
    for linea in salida.split('\n'):
        dato = linea.split('\t')
        if len(dato) == 2 and dato[0] != "" and dato[1] != "":
            # si el tiempo actual esta en el mismo segundo que el anterior se suman los bytes.
            if int(float(dato[0])) == last_second:
                last_byte += int(dato[1])
            # sino se guardan los bytes en las listas y se reinician last_second y last_byte
            else:
                if last_second != -1:
                    x_value.append(last_second)
                    y_value.append(last_byte*8) # por 8 para convertir de Bytes a bits.
                    # Se rellena con ceros todos los segundos entre medias de los dos tiempos.
                    for i in range(last_second + 1, int(float(dato[0]))):
                        x_value.append(i)
                        y_value.append(0)
                last_second = int(float(dato[0]))
                last_byte = int(dato[1])
    # se aniade el ulitmo last_second, siempre que sea valido.
    if last_second != -1:
        x_value.append(last_second)
        y_value.append(last_byte*8) # por 8 para convertir de Bytes a bits.

    return x_value, y_value

# Argumento salida: output de tshark (1 columna).
# Devuelve una lista de floats dado el output de tshark.
def parseSalidaFloat(salida):
    datos = []

    # Se convierten los datos a float y se meten en la lista.
    for dato in salida.split('\n'):
        if dato != '':
            datos.append(float(dato))
    
    return datos

# Argumento salida: output de tshark (1 columna).
# Devuelve una lista de floats dado el output de tshark.
def parseSalidaInt(salida):
    datos = []

    # Se convierten los datos a int y se meten en la lista.
    for dato in salida.split('\n'):
        if dato != '':
            datos.append(int(dato))
    
    return datos


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Programa principal que realiza el análisis de tráfico sobre una traza PCAP',
    formatter_class=RawTextHelpFormatter)
    parser.add_argument('--trace', dest='tracefile', default=False,help='Fichero de traza a usar',required=True)
    parser.add_argument('--mac', dest='mac', default=False,help='MAC usada para filtrar',required=True)
    parser.add_argument('--ip_flujo_tcp', dest='ip_flujo_tcp', default=False,help='IP para filtrar por el flujo TCP',required=True)
    parser.add_argument('--port_flujo_udp', dest='port_flujo_udp', default=False,help='Puerto para filtrar por el flujo UDP',required=True)
    parser.add_argument('--debug', dest='debug', default=False, action='store_true',help='Activar Debug messages')
    args = parser.parse_args()

    if args.debug:
        logging.basicConfig(level = logging.DEBUG, format = '[%(asctime)s %(levelname)s]\t%(message)s')
    else:
        logging.basicConfig(level = logging.INFO, format = '[%(asctime)s %(levelname)s]\t%(message)s')

    #Creamos un directorio a donde volcaremos los resultado e imágenes

    if not os.path.isdir('resultados'):
        os.mkdir('resultados')
  
    #Ejemplo de ejecución de comando tshark y parseo de salida. Se parte toda la salida en líneas usando el separador \n
    """
    logging.info('Ejecutando tshark para obtener el número de paquetes')
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.number'.format(args.tracefile))
    nlineas = 0
    for linea in salida.split('\n'):
        if linea != '':
            print(linea)
            nlineas +=1

    print('{} paquetes en la traza {}'.format(nlineas,args.tracefile))
    """
    
    #Analisis de protocolos
    #TODO: Añadir código para obtener el porcentaje de tráfico IPv4 y NO-IPv4
    #Captura de los protocolos
    logging.info('Ejecutando tshark para obtener los protocolos encapsulados en el nivel ethernet')
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e eth.type -e vlan.etype'.format(args.tracefile))

    nlineas = 0
    countIP = 0

    for linea in salida.split('\n'):
        nlineas += 1
        for dato in linea.split('\t'):
            if dato == "0x00000800":
                countIP += 1
                break

    porcentajeIP = round(((countIP)/nlineas)*100, 3)
    # El resto de paquetes no seran IP.
    porcentajeNoIP = round(100 - porcentajeIP, 3)
    print('Porcentaje de paquetes IPv4: {} %, y de paquetes no IPv4: {} % de la traza {}'.format(porcentajeIP, porcentajeNoIP, args.tracefile))
    
    #TODO: Añadir código para obtener el porcentaje de tráfico TPC,UDP y OTROS sobre el tráfico IP
    logging.info('Ejecutando tshark para obtener los protocolos en el tráfico IP, y posteriormente calcular los porcentajes')
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e ip.proto -Y "ip"'.format(args.tracefile))

    nlineas = 0
    countTCP = 0
    countUDP = 0
    countOtros = 0

    for linea in salida.split('\n'):
        nlineas += 1
        if linea == "17":
            countUDP += 1
        elif linea == "6":
            countTCP += 1
        else:
            countOtros += 1
        

    porcentajeTCP = round(((countTCP)/nlineas)*100, 3)
    porcentajeUDP = round(((countUDP)/nlineas)*100, 3)
    porcentajeOtros = round(((countOtros)/nlineas)*100, 3)

    print('Porcentaje de paquetes TCP: {} %,  de paquetes UDP: {} %, y de otro tipo: {} % de la traza {}'.format(porcentajeTCP,porcentajeUDP, porcentajeOtros, args.tracefile))
    
    #Obtención de top 5 direcciones IP
    logging.info('Ejecutando tshark para obtencion top 5 direcciones IP.')

    #TODO: Añadir código para obtener los datos y generar la gráfica de top IP origen por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e ip.src -e frame.len'.format(args.tracefile))
    
    top5_bytes(salida, PATH + "Top5_IPOrigen_Bytes.png", "Top 5 de IP origen por bytes")
    
    #TODO: Añadir código para obtener los datos y generar la gráfica de top IP origen por paquetes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e ip.src'.format(args.tracefile))

    top5_paquetes(salida, PATH + "Top5_IPOrigen_Paquetes.png", "Top 5 de IP origen por paquetes")    
    
    #TODO: Añadir código para obtener los datos y generar la gráfica de top IP destino por paquetes    
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e ip.dst'.format(args.tracefile))

    top5_paquetes(salida, PATH + "Top5_IPDestino_Paquetes.png", "Top 5 de IP destino por paquetes")

    #TODO: Añadir código para obtener los datos y generar la gráfica de top IP destino por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e ip.dst -e frame.len'.format(args.tracefile))

    top5_bytes(salida, PATH + "Top5_IPDestino_Bytes.png", "Top 5 de IP destino por bytes")
       
    #Obtención de top 5 puertos TCP
    logging.info('Ejecutando tshark para obtencion top 5 puertos TCP.')

    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto origen TCP por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e tcp.srcport -e frame.len'.format(args.tracefile))

    top5_bytes(salida, PATH + "Top5_TCPOrigen_Bytes.png", "Top 5 de puertos origen TCP por bytes")
   
    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto destino TCP por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e tcp.dstport -e frame.len'.format(args.tracefile))

    top5_bytes(salida, PATH + "Top5_TCPDestino_Bytes.png", "Top 5 de puertos destino TCP por bytes")

    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto origen TCP por paquetes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e tcp.srcport'.format(args.tracefile))
   
    top5_paquetes(salida, PATH + "Top5_TCPOrigen_Paquetes.png", "Top 5 de puertos origen TCP por paquetes")
 
    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto destino  TCP por paquetes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e tcp.dstport'.format(args.tracefile))

    top5_paquetes(salida, PATH + "Top5_TCPDestino_Paquetes.png", "Top 5 de puertos destino TCP por paquetes")

    #Obtención de top 5 puertos UDP
    logging.info('Ejecutando tshark para obtencion top 5 puertos UDP.')

    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto origen UDP por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e udp.srcport -e frame.len'.format(args.tracefile))

    top5_bytes(salida, PATH + "Top5_UDPOrigen_Bytes.png", "Top 5 de puertos origen UDP por bytes")

    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto destino UDP por bytes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e udp.dstport -e frame.len'.format(args.tracefile))
    
    top5_bytes(salida, PATH + "Top5_UDPDestino_Bytes.png", "Top 5 de puertos destino UDP por bytes")
    
    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto origen UDP por paquetes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e udp.srcport'.format(args.tracefile))

    top5_paquetes(salida, PATH + "Top5_UDPOrigen_Paquetes.png", "Top 5 de puertos origen UDP por paquetes")

    #TODO: Añadir código para obtener los datos y generar la gráfica de top puerto destino UDP por paquetes
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e udp.dstport'.format(args.tracefile))

    top5_paquetes(salida, PATH + "Top5_UDPDestino_Paquetes.png", "Top 5 de puertos destino UDP por paquetes")
    
        
    #Obtención de series temporales de ancho de banda
    logging.info('Ejecutando tshark para obtener la serie temporal de ancho de banda')

    #TODO: Añadir código para obtener los datos y generar la gráfica de la serie temporal de ancho de banda con MAC como origen
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_epoch -e frame.len -Y "eth and eth.src eq {}"'.format(args.tracefile, args.mac))
    
    x_value, y_value = calcularSerieTemporal(salida)

    pintarSerieTemporal(x_value, y_value, PATH + "Serie_temporal_src.png", "Serie Temporal con la MAC como origen", "Time (epoch)", "Caudal (bits)")
    
    #TODO: Añadir código para obtener los datos y generar la gráfica de la serie temporal de ancho de banda con MAC como destino
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_epoch -e frame.len -Y "eth and eth.dst eq {}"'.format(args.tracefile, args.mac))
    
    x_value, y_value = calcularSerieTemporal(salida)

    pintarSerieTemporal(x_value, y_value, PATH + "Serie_temporal_dst.png", "Serie Temporal con la MAC como destino", "Time (epoch)", "Caudal (bits)")
    

    #Obtención de las ECDF de tamaño de los paquetes
    logging.info('Ejecutando tshark para obtener los tamaños a nivel 2')

    #TODO: Añadir código para obtener los datos y generar la gráfica de la ECDF de los tamaños de los paquetes a nivel 2
    # Mac como dirección SOURCE
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.len -Y "eth and eth.src eq {}"'.format(args.tracefile, args.mac))

    #como la salida devuelve los tamaños de paquete en cadenas de caracteres, se convierten a enteros
    datos = parseSalidaInt(salida)

    if len(datos) > 0:
        pintarECDF(datos, PATH + "ECDF_tamPaquetes_n2_src.png", "ECDF de los tamaños de los paquetes a nivel 2 filtrados por MAC source", "Tamaño del paquete (Bytes)","Probabilidad")
    
    # Mac como dirección DESTINATION
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.len -Y "eth and eth.dst eq {}"'.format(args.tracefile, args.mac))

    #como la salida devuelve los tamaños de paquete en cadenas de caracteres, se convierten a enteros
    datos = parseSalidaInt(salida)

    if len(datos) > 0:
       pintarECDF(datos, PATH + "ECDF_tamPaquetes_n2_dst.png", "ECDF de los tamaños de los paquetes a nivel 2 filtrados por MAC destination", "Tamaño del paquete (Bytes)","Probabilidad")
    
    
    #Obtención de las ECDF de tamaño de los tiempos entre llegadas
    logging.info('Ejecutando tshark para obtener los tiempos entre llegadas para el flujo TCP')

    #TODO: Añadir código para obtener los datos y generar la gráfica de la ECDF de los tiempos entre llegadas para el flujo TCP
    # IP del flujo TCP como dirección SOURCE
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_delta -Y "tcp and ip.src eq {}"'.format(args.tracefile, args.ip_flujo_tcp))

    #como la salida devuelve los tiempos en cadenas de caracteres, se convierten a float
    datos = parseSalidaFloat(salida)
    
    if len(datos) > 0:
        pintarECDF(datos, PATH + "ECDF_interarrival_TCP_src.png", "ECDF de los tiempos entre llegadas para el flujo TCP, con la IP como source", "Interarrival (s)", "Probabilidad")
    
    # IP del flujo TCP como DESTINATION
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_delta -Y "tcp and ip.dst eq {}"'.format(args.tracefile, args.ip_flujo_tcp))

    #como la salida devuelve los tiempos en cadenas de caracteres, se convierten a float
    datos = parseSalidaFloat(salida)
 
    if len(datos) > 0:
        pintarECDF(datos, PATH + "ECDF_interarrival_TCP_dst.png", "ECDF de los tiempos entre llegadas para el flujo TCP, con la IP como destination", "Interarrival (s)", "Probabilidad")
        
        
    #TODO: Añadir código para obtener los datos y generar la gráfica de la ECDF de los tiempos entre llegadas para el flujo UDP
    logging.info('Ejecutando tshark para obtener los tiempos entre llegadas para el flujo UDP')
    
    # IP del flujo UDP como SOURCE
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_delta -Y "udp and udp.srcport eq {}"'.format(args.tracefile, args.port_flujo_udp))

    #como la salida devuelve los tiempos en cadenas de caracteres, se convierten a float
    datos = parseSalidaFloat(salida)
 
    if len(datos) > 0:
        pintarECDF(datos, PATH + "ECDF_interarrival_UDP_src.png", "ECDF de los tiempos entre llegadas para el flujo UDP, con el puerto dado como source", "Interarrival (s)","Probabilidad")
    
    # IP del flujo UDP como DESTINATION
    codigo,salida = ejecutarComandoObtenerSalida('tshark -r {} -T fields -e frame.time_delta -Y "udp and udp.dstport eq {}"'.format(args.tracefile, args.port_flujo_udp))
    
    #como la salida devuelve los tiempos en cadenas de caracteres, se convierten a float
    datos = parseSalidaFloat(salida)

    if len(datos) > 0:
        pintarECDF(datos, PATH + "ECDF_interarrival_UDP_dst.png", "ECDF de los tiempos entre llegadas para el flujo UDP, con el puerto dado como source", "Interarrival (s)","Probabilidad")
