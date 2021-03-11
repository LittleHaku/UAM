CC = gcc
CFLAGS = -Wall -pedantic

#para compilar nombre_fichero
#Para ejecutar: run_nombre_fichero, si se quiere ejecutar con Valgring runv_nombre_fichero
#Para limpiar: clean

all: shm_writer shm_reader shm_open shm_concurrence shm_concurrence_solved shm_producer shm_consumer shm_producer_file shm_consumer_file mq_sender mq_receptor mq_injector mq_workers_pool mq_workers_pool_timed
	@echo "compilando todo..."

### COMPILE ##################################################################

#Ejercicio 1
shm_writer:
	@$(CC) $(CFLAGS) shm_writer.c -lrt -o bin/writer.exe

shm_reader:
	@$(CC) $(CFLAGS) shm_reader.c -lrt -o bin/reader.exe

#Ejercicio 2
shm_open:
	@$(CC) $(CFLAGS) shm_open.c -lrt -o bin/open.exe

#Ejercicio 3
shm_concurrence:
	@$(CC) $(CFLAGS) shm_concurrence.c -lrt -lm -o bin/concurrence.exe

shm_concurrence_solved:
	@$(CC) $(CFLAGS) shm_concurrence_solved.c -lrt -lm -lpthread -o bin/concurrence_solved.exe

#Ejercicio 4
#TAD para el producer y el consumer
shm_histograma.o: shm_histograma.c shm_histograma.h
	@$(CC) $(CFLAGS) -o bin/shm_histograma.o -c shm_histograma.c
shm_producer: 
	@$(CC) $(CFLAGS) shm_producer.c -lpthread -lrt -o bin/producer.exe

shm_consumer: shm_histograma.o
	@$(CC) $(CFLAGS) shm_consumer.c  -lpthread -lrt -o bin/consumer.exe bin/shm_histograma.o

shm_producer_file:
	@$(CC) $(CFLAGS) shm_producer_file.c -lpthread -lrt -o bin/producer_file.exe 

shm_consumer_file: shm_histograma.o
	@$(CC) $(CFLAGS) shm_consumer_file.c -lpthread -lrt -o bin/consumer_file.exe bin/shm_histograma.o

#Ejercicio 5
mq_sender:
	@$(CC) $(CFLAGS) mq_sender.c -lrt -lm -o bin/sender.exe

mq_receptor:
	@$(CC) $(CFLAGS) mq_receptor.c -lrt -lm -o bin/receptor.exe

#Ejercicio 6
mq_injector:
	@$(CC) $(CFLAGS) mq_injector.c -lrt -o bin/injector.exe 

mq_workers_pool:
	@$(CC) $(CFLAGS) mq_workers_pool.c -lrt -o bin/workers_pool.exe

mq_workers_pool_timed:
	@$(CC) $(CFLAGS) mq_workers_pool_timed.c -lrt -o bin/workers_pool_timed.exe

### RUN ##################################################################

#Ejercicio 1
run_shm_writer: shm_writer
	./bin/writer.exe

run_shm_reader: shm_reader
	./bin/reader.exe

#Ejercicio 2
run_shm_open: shm_open
	./bin/open.exe

#Ejercicio 3
run_shm_concurrence: shm_concurrence
	./bin/concurrence.exe 2 2

run_shm_concurrence_solved: shm_concurrence_solved
	./bin/concurrence_solved.exe 2 2

#Ejercicio 4
run_shm_producer: shm_producer
	./bin/producer.exe 20 0

run_shm_consumer: shm_consumer
	./bin/consumer.exe

run_shm_producer_file: shm_producer_file
	./bin/producer_file.exe 20 0

run_shm_consumer_file: shm_consumer_file
	./bin/consumer_file.exe

#Ejercicio 5
run_mq_sender: mq_sender
	./bin/sender.exe 2 2

run_mq_receptor: mq_receptor
	./bin/receptor.exe 2 2

#Ejercicio 6
run_mq_injector: mq_injector
	./bin/injector.exe texto.txt /ej6

run_mq_workers_pool: mq_workers_pool
	./bin/workers_pool.exe 2 /ej6 o

run_mq_workers_pool_timed: mq_workers_pool_timed
	./bin/workers_pool_timed.exe 4 /ej6 o

### RUN VALGRIND ##################################################################

#Ejercicio 1
runv_shm_writer: shm_writer
	valgrind --leak-check=full ./bin/writer.exe

runv_shm_reader: shm_reader
	valgrind --leak-check=full ./bin/reader.exe

#Ejercicio 2
runv_shm_open: shm_open
	valgrind --leak-check=full ./bin/open.exe

#Ejercicio 3
runv_shm_concurrence: shm_concurrence
	valgrind --leak-check=full ./bin/concurrence.exe 2 2

runv_shm_concurrence_solved: shm_concurrence_solved
	valgrind --leak-check=full ./bin/concurrence_solved.exe 2 2

#Ejercicio 4
runv_shm_producer: shm_producer
	valgrind --leak-check=full ./bin/producer.exe 20 0

runv_shm_consumer: shm_consumer
	valgrind --leak-check=full ./bin/consumer.exe

runv_shm_producer_file: shm_producer_file
	valgrind --leak-check=full ./bin/producer_file.exe 20 0

runv_shm_consumer_file: shm_consumer_file
	valgrind --leak-check=full ./bin/consumer_file.exe

#Ejercicio 5
runv_mq_sender: mq_sender
	valgrind --leak-check=full ./bin/sender.exe 2 2

runv_mq_receptor: mq_receptor
	valgrind --leak-check=full ./bin/receptor.exe 2 2

#Ejercicio 6
runv_mq_injector: mq_injector
	valgrind --leak-check=full ./bin/injector.exe texto.txt /ej6

runv_mq_workers_pool: mq_workers_pool
	valgrind --leak-check=full ./bin/workers_pool.exe 2 /ej6 o

runv_mq_workers_pool_timed: mq_workers_pool_timed
	valgrind --leak-check=full ./bin/workers_pool_timed.exe 2 /ej6 o



clean:
	@rm -rf ~* *~ a.out *.exe *.o bin/*.exe bin/*.o
