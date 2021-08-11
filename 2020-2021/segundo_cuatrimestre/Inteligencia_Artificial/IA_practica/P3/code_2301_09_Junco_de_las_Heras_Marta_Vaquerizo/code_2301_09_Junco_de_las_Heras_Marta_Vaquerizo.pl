/* 
* Authors:
* Junco de las Heras Valenzuela junco.heras\@estudiante.uam.es
* Marta Vaquerizo Núñez marta.vaquerizo\@estudiante.uam.es
* Grupo: Doble Grado, 2301.
* Pareja nº 09.
*/

write_log(S) :- open('error_logs.txt', append, Out), write(Out, S), write(Out, '\n'), close(Out).

/***************
* EJERCICIO 1. sum_pot_prod\/4
*
*	ENTRADA:
*		X: Vector de entrada de numeros de valor real.
*		Y: Vector de entrada de numeros de valor real.
*		Potencia: Numero de valor entero, potencia.
*	SALIDA:
*		Resultado: Numero de valor real resultado de la operacion sum_pot_prod. 
*
****************/

/* Comprobacion de errores. */
sum_pot_prod_rec(_, _, Potencia, _) :- Potencia < 0, print("ERROR 1.1 Potencia."), !, fail.
sum_pot_prod_rec([], [_| _], _, _) :- print("ERROR 1.2 Longitud."), !, fail.
sum_pot_prod_rec([_|_], [], _, _) :- print("ERROR 1.2 Longitud."), !, fail.

/* Caso base. */
sum_pot_prod_rec([], [], _, Resultado) :- Resultado is 0.

/* Recursion. */
sum_pot_prod_rec([A|B], [C|D], Potencia, Resultado) :-
	Q is (A*C)^Potencia,
	sum_pot_prod(B, D, Potencia, Resultado2),
	Resultado is Q + Resultado2.

/* Inicia la recursion. */
sum_pot_prod(X, Y, Potencia, Resultado) :- sum_pot_prod_rec(X, Y, Potencia, Resultado).

/***************
* EJERCICIO 2. segundo_penultimo\/3
*
*       ENTRADA:
*               L: Lista de entrada de numeros de valor real.
*       SALIDA:
*               X : Numero de valor real. Segundo elemento.
*		Y : Numero de valor real. Penultimo elemento.
*
****************/

/* Asigna la variable Y. */
segundo_penultimo_rec2([A, _|[]], _, Y) :- Y is A.
segundo_penultimo_rec2([_|B], X, Y) :- segundo_penultimo_rec2(B, X, Y).

/* Asigna la variable X. */
segundo_penultimo_rec1([A,B|C], X, Y) :- X is B, segundo_penultimo_rec2([A,B|C], X, Y). 

/* Comprobacion de errores. */
segundo_penultimo_rec1([_|[]], _, _) :- print('ERROR 2.1 Longitud.'), !, fail. 
segundo_penultimo_rec1([], _, _) :- print('ERROR 2.1 Longitud.'), !, fail. 

/* Inicia la recursion. */
segundo_penultimo(L, X, Y) :- segundo_penultimo_rec1(L, X, Y).

/***************
* EJERCICIO 3. sublista\/5
*
*       ENTRADA:
*		L: Lista de entrada de cadenas de texto.
*		Menor: Numero de valor entero, indice inferior.
*		Mayor: Numero de valor entero, indice superior.
*		E: Elemento, cadena de texto.
*       SALIDA:
*		Sublista: Sublista de salida de cadenas de texto.
*
****************/

/* Si el indice Mayor se sale de la lista. */
sublista_rec([], 1, Mayor, _, _) :- 
	Mayor \== 0,
	print('ERROR 3.1 Indices.'), !, fail.

/* Caso base de la recursion. */
sublista_rec(_, 1, 0, -1, Sublista) :- Sublista = []. 

/* Si al acabar no se ha encontrado E, devolver error. */
sublista_rec(_, 1, 0, E, _) :-
	E \== -1,
	print('ERROR 3.1 Elemento.'), !, fail.

/* Si se encuentra E, entonces actualizarlo y marcarlo como -1. */
sublista_rec([E|B], 1, Mayor, E, Sublista) :- 
	Mayor1 is Mayor - 1,
	sublista_rec(B, 1, Mayor1, -1, Sublista2), 
	Sublista = [E|Sublista2]. 

/* Coge todos los elementos hasta que Mayor sea 0. */
sublista_rec([A|B], 1, Mayor, E, Sublista) :- 
	A \== E,
	Mayor1 is Mayor - 1,
	sublista_rec(B, 1, Mayor1, E, Sublista2), 
	Sublista = [A|Sublista2]. 

/* Quita los elementos de la izq hasta llegar a Menor = 1. */
sublista_rec([_|B], Menor, Mayor, E, Sublista) :-
	Menor \== 1,
	Menor1 is Menor - 1, 
	Mayor1 is Mayor - 1, 
	sublista_rec(B, Menor1, Mayor1, E, Sublista).

/* Comprueba los indices. */
sublista(_, Menor, Mayor, _, _) :- Menor > Mayor, print('ERROR 3.1 Indices.'), !, fail.

/* Inicializa la recursion. */
sublista(L, Menor, Mayor, E, Sublista) :- Menor =< Mayor, sublista_rec(L, Menor, Mayor, E, Sublista).

/***************
* EJERCICIO 4. espacio_lineal\/4
*
*       ENTRADA:
*               Menor: Numero de valor entero, valor inferior del intervalo.
*               Mayor: Numero de valor entero, valor superior del intervalo.
*               Numero_elementos: Numero de valor entero, numero de valores de la rejilla.
*       SALIDA:
*               Rejilla: Vector de numeros de valor real resultante con la rejilla.
*
****************/


/* Casos base.*/
espacio_lineal_rec( _, Mayor, 1, Rejilla):- Rejilla = [Mayor].
espacio_lineal_rec( _, _, 0, Rejilla):- Rejilla = [].

/*Recursion*/
espacio_lineal_rec(Menor, Mayor, Numero_elementos, Rejilla) :- 
	Numero_elementos > 1,
	D is (Mayor-Menor)/(Numero_elementos - 1),
	Numero_elementos2 is Numero_elementos - 1, 
	Menor1 is Menor + D,
	espacio_lineal_rec(Menor1, Mayor, Numero_elementos2, Rejilla2),
	Rejilla = [Menor|Rejilla2].
		
/* Si los indices están mal*/
espacio_lineal(Menor, Mayor, _, _):- 
	Menor > Mayor,
	print('ERROR 4.1 Indices.'), !, fail.	
	
/* Inicializa la recursion. */
espacio_lineal(Menor, Mayor, Numero_elementos, Rejilla) :-
	Menor =< Mayor,
	espacio_lineal_rec(Menor, Mayor, Numero_elementos, Rejilla).

/***************
* EJERCICIO 5. normalizar\/2
*
*       ENTRADA:
*		Distribucion_sin_normalizar: Vector de numeros reales de entrada. Distribucion sin normalizar.
*       SALIDA:
*		Distribucion: Vector de numeros reales de salida. Distribucion normalizada.
*
****************/

/*calculo de la constante*/
suma_soporte([], Result) :- Result is 0.
suma_soporte([A|B], Result) :- 
	suma_soporte(B, Result2),
	Result is A+Result2.

/*Caso base*/
normalizar_rec([], _, Distribucion) :- Distribucion = [].

/* Si hay un num negativo. */
/* No puede haber un elemento que sea 0, en ese caso tambien se muestra el error Negativos. */
normalizar_rec([A|_], _, _) :- A =< 0, print('ERROR 5.1.  Negativos.'), !, fail.

/*Recursion*/
normalizar_rec([A|B], Suma, Distribucion):-
	Suma \== 0,
	A2 is A/Suma,
	normalizar_rec(B, Suma, Distribucion2),
	Distribucion = [A2|Distribucion2].

/* Inicializa la recursion. */
normalizar(Distribucion_sin_normalizar, Distribucion):- 
	suma_soporte(Distribucion_sin_normalizar, Suma),
	normalizar_rec(Distribucion_sin_normalizar, Suma, Distribucion).

/***************
* EJERCICIO 6. divergencia_kl\/3
*
*       ENTRADA:
*		D1: Vector de numeros de valor real. Distribucion.
*		D2: Vector de numeros de valor real. Distribucion.
*       SALIDA:
*		KL: Numero de valor real. Divergencia KL.
*
****************/

/* Error si es <= 0.0 alguno de los elementos. */
divergencia_kl_rec([A|_], _, _) :- 
	A =< 0.0,
	print('ERROR 6.1.  Divergencia KL no definida.'), !, fail.

divergencia_kl_rec(_, [B|_], _) :- 
	B =< 0.0, 
	print('ERROR 6.1.  Divergencia KL no definida.'), !, fail.

/* Caso base. */
divergencia_kl_rec([], [], KL) :- KL is 0.

/* Recursion. */
divergencia_kl_rec([A|B], [C|D], KL) :- 
	Q is A*log(A/C),
	divergencia_kl_rec(B, D, KL2),
	KL is Q + KL2.

/* Error si la suma de los elementos no da 1. */
divergencia_kl(D1, _, _) :-
	suma_soporte(D1, Suma),
	Suma \== 1.0,
	print('ERROR 6.2.  Divergencia KL definida solo para distribuciones.'), !, fail.

divergencia_kl(_, D2, _) :-
	suma_soporte(D2, Suma),
	Suma \== 1.0,
	print('ERROR 6.2.  Divergencia KL definida solo para distribuciones.'), !, fail.

/* Inicializa la recursion. */
divergencia_kl(D1, D2, KL) :- divergencia_kl_rec(D1, D2, KL).

/***************
* EJERCICIO 7. producto_kronecker\/3
*
*       ENTRADA:
*		Matriz_A: Matriz de numeros de valor real.
*		Matriz_B: Matriz de numeros de valor real.
*       SALIDA:
*		Matriz_bloques: Matriz de bloques (matriz de matrices) de numeros reales.
*
****************/

/* Caso base. */
producto_kronecker_rec4(_, [], Matriz_bloques) :- Matriz_bloques = [].

/* Error si un elemento es menor que cero. */
producto_kronecker_rec4(ElementoA, _, _) :-
	ElementoA < 0,
	print('ERROR 7.1.  Elemento menor que cero.'), !, fail.
	
producto_kronecker_rec4(_, [ElementoB|_], _) :-
	ElementoB < 0,
	print('ERROR 7.1.  Elemento menor que cero.'), !, fail.


/* Recursion. */
/* Elemento por fila */
producto_kronecker_rec4(ElementoA, [ElementoB|Rest], Matriz_bloques) :-
	ElementoA >= 0,
	ElementoB >= 0,
	Resultado is ElementoA*ElementoB,
	producto_kronecker_rec4(ElementoA, Rest, Matriz_bloquesRest),
	Matriz_bloques = [Resultado|Matriz_bloquesRest].

/* Elemento por matriz. */
producto_kronecker_rec3(_, [], Matriz_bloques) :- Matriz_bloques = [].

producto_kronecker_rec3(ElementoA, [Fila1B|Rest], Matriz_bloques) :-
	producto_kronecker_rec4(ElementoA, Fila1B, Matriz_bloquesFirst),
	producto_kronecker_rec3(ElementoA, Rest, Matriz_bloquesRest),
	Matriz_bloques = [Matriz_bloquesFirst|Matriz_bloquesRest].

/* Fila por matriz. */
producto_kronecker_rec2([], _, Matriz_bloques) :- Matriz_bloques = [].

producto_kronecker_rec2([Elemento1A|Rest], Matriz_B, Matriz_bloques) :-
	producto_kronecker_rec3(Elemento1A, Matriz_B, Matriz_bloquesFirst),
	producto_kronecker_rec2(Rest, Matriz_B, Matriz_bloquesRest),
	Matriz_bloques = [Matriz_bloquesFirst|Matriz_bloquesRest].

/* Matriz por matriz. */
producto_kronecker_rec1([], _, Matriz_bloques) :- Matriz_bloques = [].

producto_kronecker_rec1([Fila1A|Rest], Matriz_B, Matriz_bloques) :-
	producto_kronecker_rec2(Fila1A, Matriz_B, Matriz_bloquesFirst),
	producto_kronecker_rec1(Rest, Matriz_B, Matriz_bloquesRest),
	Matriz_bloques = [Matriz_bloquesFirst|Matriz_bloquesRest].

/* Inicializa la recursion. */
producto_kronecker(Matriz_A, Matriz_B, Matriz_bloques) :- producto_kronecker_rec1(Matriz_A, Matriz_B, Matriz_bloques).

/***************
* EJERCICIO 8a. distancia_euclidea\/3
*
*       ENTRADA:
*               X1: Vector de numeros de valor real. 
*               X2: Vector de numeros de valor real.
*       SALIDA:
*               D: Numero de valor real. Distancia euclidea.
*
****************/

/* Caso base. */
distancia_euclidea_rec([], [], D) :-
	D is 0.

/* Recursion. */
distancia_euclidea_rec([X1|Rest1], [X2|Rest2], D) :-
	distancia_euclidea_rec(Rest1, Rest2, D1),
	D2 is (X1-X2)*(X1-X2),
	D is D1+D2. 

/* Inicia la recursion. */
distancia_euclidea(X1, X2, D) :- 
	distancia_euclidea_rec(X1, X2, D1),
	D is sqrt(D1).

/***************
* EJERCICIO 8b. calcular_distancias\/3
*
*       ENTRADA:
*               X_entrenamiento: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector.
*               X_test: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector. Instancias sin etiquetar.
*       SALIDA:
*               Matriz_resultados: Matriz de numeros de valor real donde cada fila es un vector con la distancia de un punto de test al conjunto de entrenamiento X_entrenamiento.
*
****************/

/* Caso base. */
calcular_distancias_rec2([], _, Matriz_resultados) :- Matriz_resultados = [].

/* Recursion. */
calcular_distancias_rec2([ElementA|X_entrenamiento], ElementB, Matriz_resultados) :-
	calcular_distancias_rec2(X_entrenamiento, ElementB, Matriz_resultados1),
	distancia_euclidea(ElementA, ElementB, Resultado),
	Matriz_resultados = [Resultado|Matriz_resultados1].

calcular_distancias_rec1(_, [], Matriz_resultados) :- Matriz_resultados = [].

/* Por cada elemento de X_test. */
calcular_distancias_rec1(X_entrenamiento, [ElementB|Rest], Matriz_resultados) :-
	calcular_distancias_rec1(X_entrenamiento, Rest, Matriz_resultados1),
	calcular_distancias_rec2(X_entrenamiento, ElementB, Matriz_resultadosRest),
	Matriz_resultados = [Matriz_resultadosRest|Matriz_resultados1].

/* Inicializa la recursion. */
calcular_distancias(X_entrenamiento, X_test, Matriz_resultados) :- calcular_distancias_rec1(X_entrenamiento, X_test, Matriz_resultados).

/***************
* EJERCICIO 8c. predecir_etiquetas\/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Matriz_resultados: Matriz de numeros de valor real donde cada fila es un vector con la distancia de un punto de test al conjunto de entrenamiento X_entrenamiento.
*       SALIDA:
*               Y_test: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_test.
*
****************/

/* Caso base. */
predecir_etiquetas_rec(_, _, [], Y_test) :- Y_test = [].

/* Recursion. */
predecir_etiquetas_rec(Y_entrenamiento, K, [Fila|Matriz_resultados], Y_test) :-
	predecir_etiqueta(Y_entrenamiento, K, Fila, Y_test1),
	predecir_etiquetas_rec(Y_entrenamiento, K, Matriz_resultados, Y_test2),
	Y_test = [Y_test1|Y_test2].

/* Inicia la recursion. */
predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, Y_test) :-
	predecir_etiquetas_rec(Y_entrenamiento, K, Matriz_resultados, Y_test).

/***************
* EJERCICIO 8d. predecir_etiqueta\/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Vec_distancias: Vector de valores reales correspondiente a una fila de Matriz_resultados.
*       SALIDA:
*               Etiqueta: Elemento de valor alfanumerico.
*
****************/

/* Llama a calcular_K_etiquetas_mas_relevantes\/4 y luego a calcular_etiqueta_mas_relevante\/2. */ 
predecir_etiqueta(Y_entrenamiento, K, Vec_distancias, Etiqueta) :- 
	calcular_K_etiquetas_mas_relevantes(Y_entrenamiento, K, Vec_distancias, Etiqueta1),
	calcular_etiqueta_mas_relevante(Etiqueta1, Etiqueta).

/***************
* EJERCICIO 8e. calcular_K_etiquetas_mas_relevantes\/4
*
*       ENTRADA:
*               Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*               K: Numero de valor entero.
*               Vec_distancias: Vector de valores reales correspondiente a una fila de Matriz_resultados.
*       SALIDA:
*		K_etiquetas: Vector de valores alfanumericos de una distribucion categorica.
*
****************/

/* Caso base. */
calcular_K_etiquetas_mas_relevantes_rec2(_, 0, Ans) :- Ans = [].

/* Recursion, coge en Ans los K primeros elementos de la lista. */
calcular_K_etiquetas_mas_relevantes_rec2([[_,ElementA]|List], K, Ans) :-
	K1 is K-1,
	calcular_K_etiquetas_mas_relevantes_rec2(List, K1, Ans1),
	Ans = [ElementA|Ans1].

/* Por cada elemento de la matriz procesarlo como una fila. */
calcular_K_etiquetas_mas_relevantes_rec1([], [], ListOrdenada) :- ListOrdenada = [].

calcular_K_etiquetas_mas_relevantes_rec1([ElementA|RestA], [ElementB|RestB], ListOrdenada) :-
	calcular_K_etiquetas_mas_relevantes_rec1(RestA, RestB, ListOrdenada1),
	ListOrdenada = [[ElementB,ElementA]|ListOrdenada1].

/* Inicia la recursion. */
calcular_K_etiquetas_mas_relevantes(Y_entrenamiento, K, Vec_distancias, K_etiquetas) :- 
	calcular_K_etiquetas_mas_relevantes_rec1(Y_entrenamiento, Vec_distancias, ListUnificada),
	sort(ListUnificada, ListOrdenada),
	calcular_K_etiquetas_mas_relevantes_rec2(ListOrdenada, K, K_etiquetas).

/***************
* EJERCICIO 8f. calcular_etiqueta_mas_relevante\/2
*
*       ENTRADA:
*               K_etiquetas: Vector de valores alfanumericos de una distribucion categorica.
*       SALIDA:
*               Etiqueta: Elemento de valor alfanumerico.
*
****************/

/* Coge el elemento de K_etiquetas con mas frecuencia. */
calcular_etiqueta_mas_relevante(K_etiquetas, Etiqueta) :- 
	calcular_contadores(K_etiquetas, Contadores),
	last(Contadores, [_,Etiqueta]).

/* Caso base. */
calcular_contadores_rec2([], _, Ret) :- Ret is 0.

/* Recursion. */
calcular_contadores_rec2([ElementA|Rest], ElementB, Ret) :-
	ElementA \== ElementB,
	calcular_contadores_rec2(Rest, ElementB, Ret).

calcular_contadores_rec2([ElementA|Rest], ElementB, Ret) :-
	ElementA == ElementB,
	calcular_contadores_rec2(Rest, ElementB, Ret1),
	Ret is Ret1+1.

calcular_contadores_rec1(_, [], Ret) :- Ret = [].

calcular_contadores_rec1(List, [ElementB|Rest], Ret) :-
	calcular_contadores_rec2(List, ElementB, Answer),	
	calcular_contadores_rec1(List, Rest, Ret1),
	Ret = [[Answer, ElementB]|Ret1].

/* Inicia la recursion. */
calcular_contadores(List, Ret) :-
	sort(List, ListUniq),
	calcular_contadores_rec1(List, ListUniq, Ret1),
	sort(Ret1, Ret).

/***************
* EJERCICIO 8g. k_vecinos_proximos\/5
*
*       ENTRADA:
*		X_entrenamiento: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector.
*		Y_entrenamiento: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_entrenamiento.
*		K: Numero de valor entero.
*		X_test: Matriz de numeros de valor real donde cada fila es una instancia representada por un vector. Instancias sin etiquetar.
*       SALIDA:
*		Y_test: Vector de valores alfanumericos de una distribucion categorica. Cada etiqueta corresponde a una instancia de X_test.
*
****************/

/* LLama a calcular_distancias\/3 y luego a predecir_etiquetas\/4. */ 
k_vecinos_proximos(X_entrenamiento, Y_entrenamiento, K, X_test, Y_test) :-
	calcular_distancias(X_entrenamiento, X_test, Matriz_resultados),
	predecir_etiquetas(Y_entrenamiento, K, Matriz_resultados, Y_test).


