CC = gcc
CFLAGS = -Wall -pedantic

run_ejercicio3:
	@$(CC) $(CFLAGS) ejercicio3.c && ./a.out /etc/shadow

runv_ejercicio3:
	@$(CC) $(CFLAGS) ejercicio3.c && valgrind --leak-check=full ./a.out

run_ejercicio4:
	@$(CC) $(CFLAGS) ejercicio4.c && ./a.out

runv_ejercicio4:
	@$(CC) $(CFLAGS) ejercicio4.c && valgrind --leak-check=full ./a.out

run_ejemplo_hilos:
	@$(CC) $(CFLAGS) ejemplo_hilos.c -lpthread && ./a.out

runv_ejemplo_hilos:
	@$(CC) $(CFLAGS) ejemplo_hilos.c -lpthread && valgrind --leak-check=full ./a.out

run_ejercicio_hilos:
	@$(CC) $(CFLAGS) ejercicio_hilos.c -lpthread && ./a.out 6

runv_ejercicio_hilos:
	@$(CC) $(CFLAGS) ejercicio_hilos.c -lpthread && valgrind --leak-check=full ./a.out 3

run_ejemplo_fork:
	@$(CC) $(CFLAGS) ejemplo_fork.c && ./a.out

runv_ejemplo_fork:
	@$(CC) $(CFLAGS) ejemplo_fork.c && valgrind --leak-check=full ./a.out

run_ejercicio_arbol:
	@$(CC) $(CFLAGS) ejercicio_arbol.c && ./a.out

runv_ejercicio_arbol:
	@$(CC) $(CFLAGS) ejercicio_arbol.c && valgrind --leak-check=full ./a.out

run_ejemplo_malloc:
	@$(CC) $(CFLAGS) ejemplo_malloc.c && ./a.out

runv_ejemplo_malloc:
	@$(CC) $(CFLAGS) ejemplo_malloc.c && valgrind --leak-check=full ./a.out

run_ejercicio_shell:
	@$(CC) $(CFLAGS) ejercicio_shell.c && ./a.out

runv_ejercicio_shell:
	@$(CC) $(CFLAGS) ejercicio_shell.c && valgrind --leak-check=full ./a.out

run_ejercicio_shell_spawn:
	@$(CC) $(CFLAGS) ejercicio_shell_spawn.c && ./a.out

runv_ejercicio_shell_spawn:
	@$(CC) $(CFLAGS) ejercicio_shell_spawn.c && valgrind --leak-check=full ./a.out

run_ejemplo_descriptores:
	@$(CC) $(CFLAGS) ejemplo_descriptores.c && ./a.out

runv_ejemplo_descriptores:
	@$(CC) $(CFLAGS) ejemplo_descriptores.c && valgrind --leak-check=full ./a.out

run_ejemplo_buffer:
	@$(CC) $(CFLAGS) ejemplo_buffer.c && ./a.out

runv_ejemplo_buffer:
	@$(CC) $(CFLAGS) ejemplo_buffer.c && valgrind --leak-check=full ./a.out

run_ejemplo_pipe:
	@$(CC) $(CFLAGS) ejemplo_pipe.c && ./a.out

runv_ejemplo_pipe:
	@$(CC) $(CFLAGS) ejemplo_pipe.c && valgrind --leak-check=full ./a.out

run_ejercicio_pipes:
	@$(CC) $(CFLAGS) ejercicio_pipes.c && ./a.out

runv_ejercicio_pipes:
	@$(CC) $(CFLAGS) ejercicio_pipes.c && valgrind --leak-check=full ./a.out



clean:
	@rm -rf ~* *~
