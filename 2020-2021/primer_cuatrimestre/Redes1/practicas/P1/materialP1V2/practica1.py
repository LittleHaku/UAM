'''
    practica1.py
    Muestra el tiempo de llegada de los primeros 50 paquetes a la interfaz especificada
    como argumento y los vuelca a traza nueva con tiempo actual

    Autor: Junco de las Heras <junco.heras@estudiante.uam.es> y Marta Vaquerizo <marta.vaquerizo@estudiante.uam.es>
    2020 EPS-UAM
'''

from rc1_pcap import *
import sys
import binascii
import signal
import argparse
from argparse import RawTextHelpFormatter
import time
import logging


ETH_FRAME_MAX = 1514
PROMISC = 1
NO_PROMISC = 0
TO_MS = 10
num_paquete = 0
TIME_OFFSET = 30*60

def signal_handler(nsignal,frame):
	logging.info('Control C pulsado')
	if handle:
		pcap_breakloop(handle)
		

def procesa_paquete(us,header,data):
	global num_paquete, pdumper	
	
	logging.info('Nuevo paquete de {} bytes capturado en el timestamp UNIX {}.{}'.format(header.len,header.ts.tv_sec,header.ts.tv_usec))
	
	header.ts.tv_sec += TIME_OFFSET # Aumentamos TIME_OFFSET el tiempo del paquete.
	
	num_paquete += 1
	#TODO imprimir los N primeros bytes
	for i in range(0, min(args.nbytes, header.caplen)):
		if i%16 > 0: # Separar los Bytes con espacios, omitiendo el espacio del principio.
			print(" ", end='')
		print("0x{:02x}".format(data[i]), end='') # Imprime el dato.
		if i%16 == 15 and i is not args.nbytes-1: # Imprime nueva linea cada 16 Bytes.
			print("")

	print("\n") # Acabar con nueva linea + 1 linea de separacion entre paquetes.


	#Escribir el trafico al fichero de captura con el offset temporal.
	if pdumper is not None:
		pcap_dump(pdumper, header, data)

	
if __name__ == "__main__":
	global pdumper,args,handle
	parser = argparse.ArgumentParser(description='Captura tráfico de una interfaz ( o lee de fichero) y muestra la longitud y timestamp de los 50 primeros paquetes',
	formatter_class=RawTextHelpFormatter)
	parser.add_argument('--file', dest='tracefile', default=False,help='Fichero pcap a abrir')
	parser.add_argument('--itf', dest='interface', default=False,help='Interfaz a abrir')
	# Si no se introduce el argumento --nbytes se considera el de por defecto 14.
	parser.add_argument('--nbytes', dest='nbytes', type=int, default=14,help='Número de bytes a mostrar por paquete') 
	parser.add_argument('--debug', dest='debug', default=False, action='store_true',help='Activar Debug messages')
	args = parser.parse_args()

	if args.debug:
		logging.basicConfig(level = logging.DEBUG, format = '[%(asctime)s %(levelname)s]\t%(message)s')
	else:
		logging.basicConfig(level = logging.INFO, format = '[%(asctime)s %(levelname)s]\t%(message)s')

	if args.tracefile is False and args.interface is False:
		logging.error('No se ha especificado interfaz ni fichero')
		parser.print_help()
		sys.exit(-1)

	signal.signal(signal.SIGINT, signal_handler)

	errbuf = bytearray()
	handle = None
	pdumper = None
	desc = None
	
	#TODO abrir la interfaz especificada para captura o la traza.
	if args.interface is not False:
		handle = pcap_open_live(args.interface,ETH_FRAME_MAX,NO_PROMISC,TO_MS,errbuf)

		#TODO abrir un dumper para volcar el tráfico (si se ha especificado interfaz). 			
		segundos_actual = int(time.time()) # Se obtiene la fecha actual en segundos.
		nombre = "captura."+args.interface+"."+str(segundos_actual)+".pcap" 

		desc = pcap_open_dead(DLT_EN10MB,ETH_FRAME_MAX)
		pdumper = pcap_dump_open(desc, nombre) 
	else: # caso args.tracefile is not False.
		handle = pcap_open_offline(args.tracefile,errbuf)
		if len(errbuf) > 3: # Si pcap_open_offline funciona errbuf retorna con len = 3.
			print("Error abriendo el fichero: " + str(errbuf))
			sys.exit(-1)

	ret = pcap_loop(handle,-1,procesa_paquete,None)
	if ret == -1:
		logging.error('Error al capturar un paquete')
	elif ret == -2:
		logging.debug('pcap_breakloop() llamado')
	elif ret == 0:
		logging.debug('No mas paquetes o limite superado')
	logging.info('{} paquetes procesados'.format(num_paquete))

	#TODO si se ha creado un dumper cerrarlo.
	if handle is not None:
		pcap_close(handle)
	if desc is not None:
		pcap_close(desc)
	if pdumper is not None:
		pcap_dump_close(pdumper)

