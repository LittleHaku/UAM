;*********************************************************************
;* 
;*
;* Sistemas Basados en Microprocesador
;* 2020-2021
;* Practica 3a
;* 
;*
;* author: Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es.
;* group: 2301 Doble grado.
;*
;* notas: ejemplo vacío
;*********************************************************************/
_DATA SEGMENT WORD
	MODO_VIDEO DB 0
_DATA ENDS

; DEFINICION DEL SEGMENTO DE CODIGO COMO PUBLICO
_TEXT SEGMENT BYTE PUBLIC 'CODE'
    ASSUME CS:_TEXT, DS:_DATA

	; COMIENZO DEL PROCEDIMIENTO PRINCIPAL
	_MAIN PROC 
		;COMIENZO DEL PROGRAMA 
		PUBLIC _pintaPixel
		PUBLIC _pintaCuadrado
		
	_MAIN ENDP 

	_pintaPixel PROC FAR
		PUSH BP
		MOV BP, SP
		
		; Comprobamos la X.
		MOV AX, [BP+8]
		CMP AX, 640
		JG FAIL_PIXEL
		CMP AX, 0
		JL FAIL_PIXEL
		
		; Comprobamos la Y.
		MOV AX, [BP+10]
		CMP AX, 480
		JG FAIL_PIXEL
		CMP AX, 0
		JL FAIL_PIXEL
		
		; Guardamos modo video para restaurarlo.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		MOV MODO_VIDEO,AL ; SALVO EL MODO VIDEO Y LO ALMACENO EN AL

		; Ponemos modo video.
		mov ah, 00h ; configuramos entrada a modo video
		mov al, 12h ; 640x480 16 color graphics (VGA)
		int 10h
		
		; Pintamos pixel.
		;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
		mov ah, 0Ch
		; lee de la pila el valor apropiado para el color
		mov bh, 00h ; numero pagina (dejar siempre a cero)
		MOV AL, BYTE PTR [BP+6] ; El color a pintar.
		MOV CX, WORD PTR [BP+8] ; posicion X donde pintar
		MOV DX, WORD PTR [BP+10] ; posicion Y donde pintar
		int 10h
	
		; Esperamos activamente.
		;Int15H espera activa en microsegundos: 1 millon us = 1 segundo
		MOV     CX, 2Dh ; CX:DX forman el tiempo de espera: 1 second = F:4240H --> 3 seconds 2D:C6C0 h
		MOV     DX, 0C6C0h
		MOV     AH, 86H ;int 15h con AH=86h para espera de microsegundos en cx:dx
		INT     15H


		; Restauramos el modo de video.
		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h
		
		POP BP
		MOV AX, 0H
		ret
		
		FAIL_PIXEL:
		POP BP
		MOV AX, -1
		RET
	_pintaPixel ENDP
	
	_pintaCuadrado PROC FAR
		PUSH BP
		MOV BP, SP
		
		; Comprobamos el tamanio.
		MOV CX, [BP+8]
		CMP CX, 480
		JG FAIL_CUADRADO
		CMP CX, 1
		JL FAIL_CUADRADO
		
		; Comprobamos la X.
		MOV AX, [BP+10]
		MOV BX, 640
		SUB BX, CX ; Resta el tamanio a 640. 
		CMP AX, BX
		JG FAIL_CUADRADO
		CMP AX, 0
		JL FAIL_CUADRADO
		
		; Comprobamos la Y.
		MOV AX, [BP+12]
		MOV BX, 480
		SUB BX, CX ; Resta el tamanio a 480. 
		CMP AX, BX
		JG FAIL_CUADRADO
		CMP AX, 0
		JL FAIL_CUADRADO
		
		JMP VALID_CUADRADO
		
		FAIL_CUADRADO:
		POP BP
		MOV AX, -1
		RET
		
		VALID_CUADRADO:
		
		; Guardamos modo video para restaurarlo.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		MOV MODO_VIDEO,AL ; SALVO EL MODO VIDEO Y LO ALMACENO EN AL

		; Ponemos modo video.
		mov ah, 00h ; configuramos entrada a modo video
		mov al, 12h ; 640x480 16 color graphics (VGA)
		int 10h
		
		; Pintamos cuadrado. El contador del bucle es SI porque no queda otro registro libre.
		MOV SI, WORD PTR [BP+8]
		DEC SI ; 0..TAMANIO-1.
		
		BUCLE_CUADRADO:
			; Pintamos una linea del cuadrado.
			;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
			mov ah, 0Ch
			; lee de la pila el valor apropiado para el color
			mov bh, 00h ; numero pagina (dejar siempre a cero)
			MOV AL, BYTE PTR [BP+6] ; El color a pintar.
			MOV CX, WORD PTR [BP+10] ; posicion X donde pintar
			ADD CX, SI
			MOV DX, WORD PTR [BP+12] ; posicion Y donde pintar
			int 10h
			
			; Pintamos una linea del cuadrado.
			;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
			mov ah, 0Ch
			; lee de la pila el valor apropiado para el color
			mov bh, 00h ; numero pagina (dejar siempre a cero)
			MOV AL, BYTE PTR [BP+6] ; El color a pintar.
			MOV CX, WORD PTR [BP+10] ; posicion X donde pintar
			MOV DX, WORD PTR [BP+12] ; posicion Y donde pintar
			ADD DX, SI
			int 10h
			
			; Pintamos una linea del cuadrado.
			;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
			mov ah, 0Ch
			; lee de la pila el valor apropiado para el color
			mov bh, 00h ; numero pagina (dejar siempre a cero)
			MOV AL, BYTE PTR [BP+6] ; El color a pintar.
			MOV CX, WORD PTR [BP+10] ; posicion X donde pintar
			ADD CX, WORD PTR [BP+8]
			MOV DX, WORD PTR [BP+12] ; posicion Y donde pintar
			ADD DX, SI
			int 10h
			
			; Pintamos una linea del cuadrado.
			;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
			mov ah, 0Ch
			; lee de la pila el valor apropiado para el color
			mov bh, 00h ; numero pagina (dejar siempre a cero)
			MOV AL, BYTE PTR [BP+6] ; El color a pintar.
			MOV CX, WORD PTR [BP+10] ; posicion X donde pintar
			ADD CX, SI
			MOV DX, WORD PTR [BP+12] ; posicion Y donde pintar
			ADD DX, WORD PTR [BP+8]
			int 10h
		
			DEC SI
			CMP SI, -1
			JNE BUCLE_CUADRADO

	
		; Esperamos activamente.
		;Int15H espera activa en microsegundos: 1 millon us = 1 segundo
		MOV     CX, 2Dh ; CX:DX forman el tiempo de espera: 1 second = F:4240H --> 3 seconds 2D:C6C0 h
		MOV     DX, 0C6C0h
		MOV     AH, 86H ;int 15h con AH=86h para espera de microsegundos en cx:dx
		INT     15H


		; Restauramos el modo de video.
		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h
		
		POP BP
		MOV AX, 0H
		ret	
	_pintaCuadrado ENDP
	
	
_TEXT ENDS 
END