Junco de las Heras junco.heras@estudiante.uam.es 2201 07-04-2020
Marta Vaquerizo marta.vaquerizo@estudiante.uam.es 2201 07-04-2020

Para compilar se debe ejecutar make all, o make nombre_fichero si es solo un ejercicio.
Para ejecutar un fichero basta con hacer make run_nombre_fichero o make runv_nombre_fichero si se quiere usar Valgrind.
El makefile hace gcc -Wall -pedantic nombre_fichero && ./a.out, no hace codigo objeto porque no es necesario.
Tambien tiene un clean, que borra los temporales

Listado de los archivo de codigo entregados:

el makefile

shm_reader.c
shm_writer.c
shm_concurrence.c
shm_concurrence_solved.c
shm_open.c
shm_producer.c
shm_consumer.c
shm_histograma.c
shm_histograma.h
shm_producer_file.c
shm_consumer_file.c
mq_receptor.c
mq_sender.c
mq_injector.c
mq_workers_pool.c
mq_workers_pool_timed.c

También hay archivo .txt:
texto.txt -> el mq_injector.c lo usa para escribir mensajes con
	     la información que éste contenga.

data.txt -> resultado al ejecutar shm_consumer, útil para hacer
	    el histograma pedido.

data2.txt -> resultado al ejecutar shm_consumer_file, útil para hacer
	    el histograma pedido.

README.txt -> este documento.

Memoria.pdf -> La memoria esta en formato PDF

bin/ -> Directorio que guarda los ejecutables que se crean al compilar desde el makefile.

histograma.png -> el histograma que hemos incluido en la memoria.
