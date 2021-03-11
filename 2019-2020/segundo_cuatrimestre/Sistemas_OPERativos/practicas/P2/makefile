CC = gcc
CFLAGS = -Wall -pedantic

run_ejercicio_kill:
	@$(CC) $(CFLAGS) ejercicio_kill.c

runv_ejercicio_kill:
	@$(CC) $(CFLAGS) ejercicio_kill.c

run_ejercicio_captura:
	@$(CC) $(CFLAGS) ejercicio_captura.c && ./a.out

runv_ejercicio_captura:
	@$(CC) $(CFLAGS) ejercicio_captura.c && valgrind --leak-check=full ./a.out

run_ejercicio_captura_mejorado:
	@$(CC) $(CFLAGS) ejercicio_captura_mejorado.c && ./a.out

runv_ejercicio_captura_mejorado:
	@$(CC) $(CFLAGS) ejercicio_captura_mejorado.c && valgrind --leak-check=full ./a.out

run_ejercicio_sigset:
	@$(CC) $(CFLAGS) ejercicio_sigset.c && ./a.out

runv_ejercicio_sigset:
	@$(CC) $(CFLAGS) ejercicio_sigset.c && valgrind --leak-check=full ./a.out

run_ejercicio_alarm:
	@$(CC) $(CFLAGS) ejercicio_alarm.c && ./a.out

runv_ejercicio_alarm:
	@$(CC) $(CFLAGS) ejercicio_alarm.c && valgrind --leak-check=full ./a.out

run_ejercicio_prottemp:
	@$(CC) $(CFLAGS) ejercicio_prottemp.c && ./a.out 20 1

runv_ejercicio_prottemp:
	@$(CC) $(CFLAGS) ejercicio_prottemp.c && valgrind --leak-check=full ./a.out 3 2

run_ejercicio_sem:
	@$(CC) $(CFLAGS) ejercicio_sem.c -lpthread && ./a.out

runv_ejercicio_sem:
	@$(CC) $(CFLAGS) ejercicio_sem.c -lpthread && valgrind --leak-check=full --track-origins=yes ./a.out

run_ejercicio_sem_signal:
	@$(CC) $(CFLAGS) ejercicio_sem_signal.c -lpthread && ./a.out

runv_ejercicio_sem_signal:
	@$(CC) $(CFLAGS) ejercicio_sem_signal.c -lpthread && valgrind --leak-check=full ./a.out

run_ejercicio_alternar:
	@$(CC) $(CFLAGS) ejercicio_alternar.c -lpthread && ./a.out

runv_ejercicio_alternar:
	@$(CC) $(CFLAGS) ejercicio_alternar.c -lpthread && valgrind --leak-check=full ./a.out

run_ejercicio_prottemp_mejorado:
	@$(CC) $(CFLAGS) ejercicio_prottemp_mejorado.c -lpthread && ./a.out 3 4

runv_ejercicio_prottemp_mejorado:
	@$(CC) $(CFLAGS) ejercicio_prottemp_mejorado.c -lpthread && valgrind --leak-check=full ./a.out 1 2

run_ejercicio_prottemp_mejorado_op:
	@$(CC) $(CFLAGS) ejercicio_prottemp_mejorado_op.c -lpthread && ./a.out 3 4

runv_ejercicio_prottemp_mejorado_op:
	@$(CC) $(CFLAGS) ejercicio_prottemp_mejorado_op.c -lpthread && valgrind --leak-check=full ./a.out 1 2

run_ejercicio_lect_escr:
	@$(CC) $(CFLAGS) ejercicio_lect_escr.c -lpthread && ./a.out

runv_ejercicio_lect_escr:
	@$(CC) $(CFLAGS) ejercicio_lect_escr.c -lpthread && valgrind --leak-check=full ./a.out

clean:
	@rm -rf ~* *~ a.out
