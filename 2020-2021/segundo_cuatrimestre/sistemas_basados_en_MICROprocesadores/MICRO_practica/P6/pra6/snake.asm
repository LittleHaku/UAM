; Autor Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es
; Grupo: Doble Grado 2301

;************************************************************************** 
; DEFINICION DEL SEGMENTO DE DATOS
DATA SEGMENT
	MODO_VIDEO DB 0
	; Longitud de la serpiente.
	LEN1 DW 0
	; Primer byte de la serpiente. (Se incrementa de 2 en 2).
	FIRST1 DW 0
	; Todos los cuadrados que componen de la serpiente. Es una
	; lista en la que se inserta al final y se borra al principio.
	; 2000 elementos es suficiente pues el modo de video es 320x200 y cada
	; cuadrado es de 10x10 pixeles asi que como mucho (es una cota superior) hay 320*2 = 640
	; bloques, y cada bloque se puede representar como un word.
	BUFFER1 DW 2000 DUP(0H)
	; Ultima direccion de movimiento, 'w','a','s','d'.
	LAST_DIR1 DB 0
	; Lo mismo pero para el segundo jugador.
	LEN2 DW 0
	FIRST2 DW 0
	BUFFER2 DW 2000 DUP(0H)
	; Ultima direccion de movimiento, 'i','j','k','l'.
	LAST_DIR2 DB 0
	; Segundos que han transcurrido, cuando llegue a 10 se resetea.
	SECONDS_ELAPSED_MOD10 DW 0
	; Segundos que han transcurrido, cuando llegue a 15 se resetea.
	SECONDS_ELAPSED_MOD15 DW 0
	; 0 Si todavia no se sabe quien gana, 1 si gana el jugador 1, 2 si gana el jugador 2.
	JUGADOR_GANADOR DB 0
	; String del jugador ganador.
	GANA1 DB 'Gana el primer jugador (Azul).', 13, 10, '$'
	GANA2 DB 'Gana el segundo jugador (Rojo).', 13, 10, '$'
	PUNTUACION_1_STR DB 'El jugador uno (Azul) tiene una puntuacion de: ', '$'
	PUNTUACION_1 DB 0
	PUNTUACION_2_STR DB 13, 10, 'El jugador dos (Rojo) tiene una puntuacion de: ', '$'
	PUNTUACION_2 DB 0
	; Numero de recompensas activas en la pantalla.
	NUMERO_RECOMPENSAS DW 0
	; Para mostrar el numero de la puntuacion se convierte a ASCII.
	converted_str DB 6 DUP(0); 2^16 = 65536, 5 CHARS + '$'
	; Ticks que se necesitan para hacer un desplazamiento automatico.
	; Al principio empieza como 18 ticks que corresponden a un segundo, pero luego
	; se va decrementando, para que 17 ticks sean un segundo etc, eso simula subir la velocidad.
	; Aproximadamente 1/17, 1/16... es 0.1, por lo que aumenta un 10% la velocidad.
	VELOCIDAD DW 18
DATA ENDS

;************************************************************************** 
; DEFINICION DEL SEGMENTO DE CODIGO 
CODIGO SEGMENT
	ASSUME CS: CODIGO, DS: DATA
	
	MAIN PROC
		MOV AX, DATA
		MOV DS, AX
		
		; Guardamos modo video para restaurarlo.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		MOV MODO_VIDEO,AL ; SALVO EL MODO VIDEO Y LO ALMACENO EN AL

		; Ponemos modo video.
		mov ah, 00h ; configuramos entrada a modo video
		mov al, 0Dh ; 320x200 16 color graphics (VGA)
		int 10h

		
		; Se guarda la pos del jugador uno en BX.
		MOV BH, 100 ; Y.
		MOV BL, 50 ; X.
		MOV AX, BX
		INT 55H
		; Se guarda la primera posicion.
		MOV WORD PTR BUFFER1, AX
		; Se guarda que la serpiente comienza con 1 cuadrado de longitud.
		MOV AX, 1
		MOV WORD PTR LEN1, AX
		; Se guarda la direccion inicial, 'd'.
		MOV AL, 'd'
		MOV BYTE PTR LAST_DIR1, AL
		
		; Se guarda la pos del jugador dos en CX.
		MOV CH, 100 ; Y.
		MOV CL, 200 ; X.
		MOV AX, CX		
		INT 57H
		; Se guarda la primera posicion.
		MOV WORD PTR BUFFER2, AX
		; Se guarda que la serpiente comienza con 1 cuadrado de longitud.
		MOV AX, 1
		MOV WORD PTR LEN2, AX
		; Se guarda la direccion inicial, 'i'.
		MOV AL, 'i'
		MOV BYTE PTR LAST_DIR2, AL
		
		; Genera 2 recompensas al inicio.
		CALL GENERATE_REWARD
		CALL GENERATE_REWARD
		
		; Un bucle que lee teclas hasta que se pulse la q.
		; Nota, es case sensitive.
		BUCLE_TECLADO:
			; Comprueba si un jugador ha ganado.
			MOV AL, JUGADOR_GANADOR
			CMP AL, 0
			JE CHECK_TECLA_PULSADA ; El salto a FIN_BUCLE_TECLADO es largo.
			JMP FIN_BUCLE_TECLADO
			
			CHECK_TECLA_PULSADA:
		
			; Comprueba si ha habido un keystroke.
			MOV AH, 01H
			INT 16H
			; Si no, repetir la espera.
			JZ NO_TECLA_PULSADA ; Convertir salto en incondicional.
			JMP TECLA_PULSADA
			NO_TECLA_PULSADA:
			
			; Se comprueba si ha pasado un segundo o no.
			; Se lee el vector de interrupcion.
			MOV AX, 0
			MOV ES, AX
			MOV SI, WORD PTR ES:[55H*4]
			MOV AX, WORD PTR ES:[55H*4+2]
			
			; Se lee la variable CONTADOR_1CH en AL.
			; Se quitan las interrupciones para evitar problemas de concurrencia
			CLI
			MOV ES, AX
			MOV AL, BYTE PTR ES:[SI-3]
			STI
			
			MOV DX, WORD PTR VELOCIDAD
			
			; Si ha habido 18 ticks aproximadamente habra pasado 1 segundo.
			; Si no ha pasado se repite el bucle, y si ha pasado entonces se simula
			; la ultima tecla en LAST_DIR1 y LAST_DIR2.
			CMP AL, DL
			JNAE BUCLE_TECLADO
			
			; Aqui ha pasado un segundo.
			
			MOV AX, WORD PTR SECONDS_ELAPSED_MOD15
			INC AX
			CMP AX, 15 ; Cada 15 Secs se incrementa la velocidad.
			JNGE NO_HA_PASADO_15_SEC
			MOV AX, 0
			MOV WORD PTR SECONDS_ELAPSED_MOD15, AX
			MOV AX, WORD PTR VELOCIDAD
			DEC AX ; Se supone que AX nunca va a bajar de 0 ya que cuando
			; AX sea muy bajo la serpiente se movera muy rapido y se chocara inevitablemente.
			MOV WORD PTR VELOCIDAD, AX
			
			NO_HA_PASADO_15_SEC:
			MOV AX, WORD PTR SECONDS_ELAPSED_MOD15
			
			MOV AX, WORD PTR SECONDS_ELAPSED_MOD10
			INC AX
			CMP AX, 10 ; Cada 10 Secs se incrementa el tamanio. 
			JNGE NO_HA_PASADO_10_SEC
			
			; Se resetea el contador SECONDS_ELAPSED_MOD10.
			MOV AX, 0
			MOV WORD PTR SECONDS_ELAPSED_MOD10, AX
			
			CALL INCREMENTA_1
			CALL INCREMENTA_2
			
			
			
			NO_HA_PASADO_10_SEC:
			; Se actualiza SECONDS_ELAPSED_MOD10
			MOV WORD PTR SECONDS_ELAPSED_MOD10, AX
			
			; Se simula que se ha pulsado una tecla en la ultima posicion.
			MOV AL, BYTE PTR LAST_DIR1
			CALL EJECUTA_TECLA_1
			MOV AX, BX
			CALL MUEVE_1
			
			MOV AL, BYTE PTR LAST_DIR2
			CALL EJECUTA_TECLA_2
			MOV AX, CX
			CALL MUEVE_2
			
			; Se escribe un 0 en el contador de ticks.
			; Se lee el vector de interrupcion.
			MOV AX, 0
			MOV ES, AX
			MOV SI, WORD PTR ES:[55H*4]
			MOV AX, WORD PTR ES:[55H*4+2]
			
			; Se lee la variable CONTADOR_1CH en AL.
			; Se quitan las interrupciones para evitar problemas de concurrencia
			CLI
			MOV ES, AX
			MOV AX, 0
			MOV BYTE PTR ES:[SI-3], AL
			STI
			
			
			JMP BUCLE_TECLADO
			
			TECLA_PULSADA:
			; Si si, leer la tecla en AL y quitarla del bufer.
			MOV AH, 00H
			INT 16H

			CALL EJECUTA_TECLA_1
			CALL EJECUTA_TECLA_2
			
			; Se acaba el bucle.
			CMP AL, 'q'
			JE FIN_BUCLE_TECLADO
			
			; Vuelve a pintar al jugador 1.
			MOV AX, BX
			CALL MUEVE_1
			
			
			; Vuelve a pintar al jugador 2.
			MOV AX, CX
			CALL MUEVE_2
			
			JMP BUCLE_TECLADO
		FIN_BUCLE_TECLADO:

		; Restauramos el modo de video.
		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h

		; Mira si el jugador uno ha ganado.
		MOV AL, BYTE PTR JUGADOR_GANADOR
		CMP AL, 1
		JNE FIN_GANA2
		
		; Escribe el ganador 1.
		MOV AH, 09H
		MOV DX, OFFSET GANA1
		INT 21H
		JMP FIN_PROGRAMA
		
		FIN_GANA2:
		; Mira si el jugador dos ha ganado.
		MOV AL, BYTE PTR JUGADOR_GANADOR
		CMP AL, 2
		JNE FIN_PROGRAMA
		
		; Escribe el ganador 2.
		MOV AH, 09H
		MOV DX, OFFSET GANA2
		INT 21H

		FIN_PROGRAMA:
		
		; Escribe la puntuacion del jugador 1.
		MOV AH, 09H
		MOV DX, OFFSET PUNTUACION_1_STR
		INT 21H

		; Convierte el valor a ASCII.
		MOV AL, BYTE PTR PUNTUACION_1
		MOV AH, 0
		CALL CONVERT_BIN_ASCII
		MOV AX, DS
		PUSH DS
		; Imprime el resultado.
		MOV DS, DX
		MOV AH, 09H
		MOV DX, BX
		INT 21H
		
		POP AX
		MOV DS, AX
		
		; Escribe la puntuacion del jugador 2.
		MOV AH, 09H
		MOV DX, OFFSET PUNTUACION_2_STR
		INT 21H

		; Convierte el valor a ASCII.
		MOV AL, BYTE PTR PUNTUACION_2
		MOV AH, 0
		CALL CONVERT_BIN_ASCII
		MOV AX, DS
		PUSH DS
		; Imprime el resultado.
		MOV DS, DX
		MOV AH, 09H
		MOV DX, BX
		INT 21H
		
		POP AX
		MOV DS, AX
		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
	
	MAIN ENDP
	
	; Mueve el cuadrado siguiendo la tecla que se ha pulsado, introducida en AL.
	; Mueve el primer jugador.
	EJECUTA_TECLA_1 PROC
		; Se comprueba pulsacion del primer jugador.
		CMP AL, 'a'
		JNE NO_A
		MOV BYTE PTR LAST_DIR1, AL ; La ultima tecla pulsada es 'a'.
		CMP BL, 10
		JBE VA_A_GANAR2 ; BL <= 10 .
		ADD BL, -10 ; Numeros de X mas bajos.
		JMP TECLA_FUE_PULSADA_1
		NO_A:
		CMP AL, 'd'
		JNE NO_D
		MOV BYTE PTR LAST_DIR1, AL
		CMP BL, 230
		JAE VA_A_GANAR2 ; BL >= 230.
		ADD BL, 10 ; Numeros de X mas altos.
		JMP TECLA_FUE_PULSADA_1
		NO_D:
		CMP AL, 'w'
		JNE NO_W
		MOV BYTE PTR LAST_DIR1, AL
		CMP BH, 10
		JBE VA_A_GANAR2 ; BH <= 10.
		ADD BH, -10 ; Numeros de Y mas bajos.
		JMP TECLA_FUE_PULSADA_1
		NO_W:
		CMP AL, 's'
		JNE NO_S
		MOV BYTE PTR LAST_DIR1, AL
		CMP BH, 170
		JAE VA_A_GANAR2 ; BH >= 170.
		ADD BH, 10 ; Numeros de Y mas altos.
		JMP TECLA_FUE_PULSADA_1
		NO_S:
		RET
		TECLA_FUE_PULSADA_1:
		; Comprueba, en un movimiento legal, si ha habido una interseccion.
		CALL CHECK_INTERSECTION_1
		
		JMP FIN_EJECUTA_TECLA_1

		VA_A_GANAR2:
		MOV AL, 2
		MOV BYTE PTR JUGADOR_GANADOR, AL
		
		FIN_EJECUTA_TECLA_1:
		
		RET
	EJECUTA_TECLA_1 ENDP
	
	; Mueve el cuadrado siguiendo la tecla que se ha pulsado, introducida en AL.
	; Mueve el segundo jugador.
	EJECUTA_TECLA_2 PROC
		; Se comprueba pulsacion del segundo jugador.
		CMP AL, 'j'
		JNE NO_J
		MOV BYTE PTR LAST_DIR2, AL ; La ultima tecla pulsada es 'j'.
		CMP CL, 10
		JBE VA_A_GANAR1 ; CL <= 10.
		ADD CL, -10 ; Numeros de X mas bajos.
		JMP TECLA_FUE_PULSADA_2
		NO_J:
		CMP AL, 'l'
		JNE NO_L
		MOV BYTE PTR LAST_DIR2, AL
		CMP CL, 230
		JAE VA_A_GANAR1; CL >= 230.
		ADD CL, 10 ; Numeros de X mas altos.
		JMP TECLA_FUE_PULSADA_2
		NO_L:
		CMP AL, 'i'
		JNE NO_I
		MOV BYTE PTR LAST_DIR2, AL
		CMP CH, 10
		JBE VA_A_GANAR1 ; CH <= 10.
		ADD CH, -10 ; Numeros de Y mas bajos.
		JMP TECLA_FUE_PULSADA_2
		NO_I:
		CMP AL, 'k'
		JNE NO_K
		MOV BYTE PTR LAST_DIR2, AL
		CMP CH, 170
		JAE VA_A_GANAR1 ; CH >= 170.
		ADD CH, 10 ; Numeros de Y mas altos.
		JMP TECLA_FUE_PULSADA_2
		NO_K:
		RET
		
		TECLA_FUE_PULSADA_2:
		; Comprueba, en un movimiento legal, si ha habido una interseccion.
		CALL CHECK_INTERSECTION_2
		
		JMP FIN_EJECUTA_TECLA_2
		
		VA_A_GANAR1:
		MOV AL, 1
		MOV BYTE PTR JUGADOR_GANADOR, AL
		
		FIN_EJECUTA_TECLA_2:
		
		RET
	EJECUTA_TECLA_2 ENDP
	
	; Mueve una posicion la serpiente del jugador 1.
	; Pinta la nueva posicion que se ha movido (la apuntada en AX) de color
	; y la ultima posicion, que se va a borrar del buffer, de color negro.
	MUEVE_1 PROC
		PUSH AX
		PUSH BX
		PUSH SI
		
		
		MOV SI, WORD PTR FIRST1
		MOV BX, WORD PTR LEN1
		ADD BX, BX ; Cada posicion ocupa dos bytes.
		ADD SI, BX 
		MOV WORD PTR BUFFER1[SI], AX ; La nueva posicion.
		INT 55H ; La pinta de color.
		
		MOV SI, WORD PTR FIRST1
		MOV AX, WORD PTR BUFFER1[SI] ; La primera posicion, se borra.
		INT 56H ; La pinta de negro.
		ADD SI, 2
		MOV WORD PTR FIRST1, SI ; Actualizas la primera posicion.
		
		POP SI
		POP BX
		POP AX
		
		RET	
	MUEVE_1 ENDP
	
	; Mueve una posicion la serpiente del jugador 2.
	; Pinta la nueva posicion que se ha movido (la apuntada en AX) de color
	; y la ultima posicion, que se va a borrar del buffer, de color negro.
	MUEVE_2 PROC
		PUSH AX
		PUSH BX
		PUSH SI
		
		
		MOV SI, WORD PTR FIRST2
		MOV BX, WORD PTR LEN2
		ADD BX, BX ; Cada posicion ocupa dos bytes.
		ADD SI, BX 
		MOV WORD PTR BUFFER2[SI], AX ; La nueva posicion.
		INT 57H ; La pinta de color.
		
		MOV SI, WORD PTR FIRST2
		MOV AX, WORD PTR BUFFER2[SI] ; La primera posicion, se borra.
		INT 56H ; La pinta de negro.
		ADD SI, 2
		MOV WORD PTR FIRST2, SI ; Actualizas la primera posicion.
		
		POP SI
		POP BX
		POP AX
		
		RET	
	MUEVE_2 ENDP
	
	; Aumenta en 1 la longitud de la snake del primer jugador.
	INCREMENTA_1 PROC
		PUSH SI
		PUSH AX
		
		MOV SI, WORD PTR FIRST1
		; Pinta el ultimo cuadrado borrado (hay almenos uno pues se ha desplazado
		; varios movimientos al menos).
		MOV AX, WORD PTR BUFFER1[SI-2]
		INT 55H
	
		; Actualizas la posicion del primer cuadrado.
		ADD SI, -2
		MOV WORD PTR FIRST1, SI
		
		; Aumentas en 1 la longitud de la snake.
		MOV AX, WORD PTR LEN1
		INC AX
		MOV WORD PTR LEN1, AX
	
		POP AX
		POP SI
		
		RET
	INCREMENTA_1 ENDP
	
	; Aumenta en 1 la longitud de la snake del segundo jugador.
	INCREMENTA_2 PROC
		PUSH SI
		PUSH AX
		
		MOV SI, WORD PTR FIRST2
		; Pinta el ultimo cuadrado borrado (hay almenos uno pues se ha desplazado
		; varios movimientos al menos).
		MOV AX, WORD PTR BUFFER2[SI-2]
		INT 55H
	
		; Actualizas la posicion del primer cuadrado.
		ADD SI, -2
		MOV WORD PTR FIRST2, SI
		
		; Aumentas en 1 la longitud de la snake.
		MOV AX, WORD PTR LEN2
		INC AX
		MOV WORD PTR LEN2, AX
	
		POP AX
		POP SI
		
		RET
	INCREMENTA_2 ENDP
	
	; Comprueba si la cabeza del primer jugador va a colisionar su propia serpiente o con la
	; serpiente del rival.
	CHECK_INTERSECTION_1 PROC
		PUSH SI
		PUSH AX
		PUSH CX
		PUSH DX
		PUSH BX
		
		; Se lee un pixel de la pantalla.
		MOV AH, 0Dh ; Lectura de pixel.
		MOV CH, 0 ; La X.
		MOV CL, BL
		MOV DH, 0 ; La Y.
		MOV DL, BH
		MOV BH, 0H ; Page number, always 0.
		INT 10H
		CMP AL, 0
		; Si el pixel leido es el negro (0) entonces no ha habido interseccion.
		JE FIN_CHECK_INTERSECTION_1
		
		CMP AL, 0EH ; Si el pixel es amarillo gana una recompensa.
		JNE NO_RECOMPENSA_1
		
		MOV AL, BYTE PTR PUNTUACION_1
		ADD AL, 10; Gana 10 puntos.
		MOV BYTE PTR PUNTUACION_1, AL

		MOV AX, WORD PTR NUMERO_RECOMPENSAS
		DEC AX
		MOV WORD PTR NUMERO_RECOMPENSAS, AX
		CMP AX, 0
		JNZ FIN_CHECK_INTERSECTION_1 ; Si AX != 0 acaba.
		; Si AX == 0 generar dos nuevas recompensas.
		
		CALL GENERATE_REWARD
		CALL GENERATE_REWARD
		JMP FIN_CHECK_INTERSECTION_1
		
		
		NO_RECOMPENSA_1:
		
		; Carga en CX la posicion actual.
		POP BX
		PUSH BX
		MOV CX, BX
		
		; Carga en AX la ultima posicion en el bufer.
		MOV SI, WORD PTR FIRST1
		MOV BX, WORD PTR LEN1
		ADD BX, BX ; Cada posicion ocupa dos bytes.
		ADD SI, BX
		; Si no ha habido todavia movimiento y el buffer esta vacio
		CMP SI, 2
		JNGE FIN_CHECK_INTERSECTION_1
		ADD SI, -2 ; La ultima posicion usada.
		MOV AX, WORD PTR BUFFER1[SI]
		
		CMP AX, CX ; Compara AX (la ultima posicion) con CX (la posicion actual).
		JE FIN_CHECK_INTERSECTION_1 ; Si son iguales acabas, no se ha movido ahora la cabeza.
		
		; Sino, gana el jugador dos, ya que el uno se ha chocado.
		MOV AL, 2
		MOV BYTE PTR JUGADOR_GANADOR, AL
		
		
		FIN_CHECK_INTERSECTION_1:
		POP BX
		POP DX
		POP CX
		POP AX
		POP SI
	
		RET
	CHECK_INTERSECTION_1 ENDP
	
	; Comprueba si la cabeza del segundo jugador va a colisionar su propia serpiente o con la
	; serpiente del rival.
	CHECK_INTERSECTION_2 PROC
		PUSH SI
		PUSH AX
		PUSH BX
		PUSH DX
		PUSH CX
		
		; Se lee un pixel de la pantalla.
		MOV AH, 0Dh ; Lectura de pixel.
		MOV CL, CL
		MOV DH, 0 ; La Y.
		MOV DL, CH
		MOV CH, 0 ; La X.
		MOV BH, 0H ; Page number, always 0.
		INT 10H
		CMP AL, 0
		; Si el pixel leido es el negro (0) entonces no ha habido interseccion.
		JE FIN_CHECK_INTERSECTION_2
		
		CMP AL, 0EH ; Si el pixel es amarillo gana una recompensa.
		JNE NO_RECOMPENSA_2
		
		MOV AL, BYTE PTR PUNTUACION_2
		ADD AL, 10; Gana 10 puntos.
		MOV BYTE PTR PUNTUACION_2, AL

		MOV AX, WORD PTR NUMERO_RECOMPENSAS
		DEC AX
		MOV WORD PTR NUMERO_RECOMPENSAS, AX
		CMP AX, 0
		JNZ FIN_CHECK_INTERSECTION_2 ; Si AX != 0 acaba.
		; Si AX == 0 generar dos nuevas recompensas.
		
		CALL GENERATE_REWARD
		CALL GENERATE_REWARD
		JMP FIN_CHECK_INTERSECTION_2
		
		
		NO_RECOMPENSA_2:
		; Carga en CX la posicion actual.
		POP CX
		PUSH CX
		
		; Carga en AX la ultima posicion en el bufer.
		MOV SI, WORD PTR FIRST2
		MOV BX, WORD PTR LEN2
		ADD BX, BX ; Cada posicion ocupa dos bytes.
		ADD SI, BX
		; Si no ha habido todavia movimiento y el buffer esta vacio
		CMP SI, 2
		JNGE FIN_CHECK_INTERSECTION_2
		ADD SI, -2 ; La ultima posicion usada.
		MOV AX, WORD PTR BUFFER2[SI]
		
		CMP AX, CX ; Compara AX (la ultima posicion) con CX (la posicion actual).
		JE FIN_CHECK_INTERSECTION_2 ; Si son iguales acabas, no se ha movido ahora la cabeza.
		
		; Sino, gana el jugador uno, ya que el dos se ha chocado.
		MOV AL, 1
		MOV BYTE PTR JUGADOR_GANADOR, AL
		
		
		FIN_CHECK_INTERSECTION_2:
		POP CX
		POP DX
		POP BX
		POP AX
		POP SI
	
		RET
	CHECK_INTERSECTION_2 ENDP
	
	; Llamarla, sin argumentos, para generar de forma aleatoria 1 recompensa,
	; cuadrados de 10x10 amarillos dentro de la zona que se pueden mover las serpientes, 240x180.
	GENERATE_REWARD PROC
		PUSH AX
		PUSH BX 
		PUSH CX
		PUSH DX
	
		; Get System Time, deja en CX:DX el tiempo del sistema.
		; Como vamos a usar modulos bajos, solo usaremos DX.
		MOV AH, 00H
		INT 1AH
		
		; Generamos la X.
		MOV AX, DX
		XOR AX, 011001b ; Suma aleatoriedad.
		MOV BX, NUMERO_RECOMPENSAS
		ADD AX, BX
		ADD AX, BX
		ADD AX, BX
		MOV BX, 23 ; Modulo 23.
		MOV AH, 0
		DIV BL ; AH es el resto de 23. 
		INC AH ; Rango [1..23].
		MOV AL, AH
		MOV BX, 10
		MUL BL ; Rango [10..230].
		MOV CL, AL ; La x se guarda en CL.
		PUSH CX ; Se guarda porque CX se va a sobreescribir.
		
		MOV AH, 00H
		INT 1AH
		
		; Generamos la Y.
		MOV AX, DX
		XOR AX, 011001b ; Suma aleatoriedad.
		MOV BX, NUMERO_RECOMPENSAS
		ADD AX, BX
		ADD AX, BX
		ADD AX, BX
		MOV BX, 17 ; Modulo 17.
		MOV AH, 0
		DIV BL ; AH es el resto de 17. 
		INC AH ; Rango [1..17].
		MOV AL, AH
		MOV BX, 10
		MUL BL ; Rango [10..170].
		POP CX ; Se restaura CX.
		MOV CH, AL ; La x se guarda en CL.
		
		; Pintamos (X, Y) de color amarillo.
		MOV AX, CX
		INT 58H
		
		; Incrementa en 1 el numero de recompensas activas.
		MOV AX, WORD PTR NUMERO_RECOMPENSAS
		INC AX
		MOV WORD PTR NUMERO_RECOMPENSAS, AX
		
		POP DX
		POP CX
		POP BX
		POP AX
		
		RET	
	GENERATE_REWARD ENDP
	
	; Recibe por argumento un numero binario en AX, y guardara en
	; memoria el codigo ASCII del numero + '$'. Devuelve DX:BX, 
	; donde DX es el segmento de la cadena resultado y BX su offset.
	CONVERT_BIN_ASCII PROC
		PUSH AX
		PUSH CX
		PUSH SI
		
		MOV SI, 5 ; Es la ultima posicion de converted_str.
		MOV CX, 10 ; Convertimos a base 10.
		MOV BYTE PTR converted_str[SI], '$' ; FIN DE CADENA.
		DEC SI

		; Mientras el numero sea > 0 lo divide y guarda el mod 10 en memoria.
		BUCLE_EXTERIOR_BIN2ASCII:
			MOV DX, 0 ; Resetea DX.
			DIV CX ; Dividimos DX:AX por CX.
			
			ADD DX, 30H; convierte DX a ASCII.
			MOV BYTE PTR converted_str[SI], DL ; Lo guarda en memoria.
			DEC SI
			
			CMP AX, 0
			JNE BUCLE_EXTERIOR_BIN2ASCII
		
		; Guarda el segmento y el offset.
		MOV DX, SEG converted_str
		MOV BX, OFFSET converted_str
		INC SI
		ADD BX, SI
		
		POP SI
		POP CX
		POP AX
		
		RET
	CONVERT_BIN_ASCII ENDP
	
CODIGO ENDS
END MAIN
