; Autor Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es
; Grupo: Doble Grado 2301


;************************************************************************** 
; DEFINICION DEL SEGMENTO DE CODIGO 
CODE SEGMENT 
	ASSUME CS: CODE

	ORG 256	
	INICIO: JMP MAIN

	AUTOR DB "Autor: Junco de las Heras Valenzuela. ", 13, 10, "$"
	CORREO DB "Correo: junco.heras@estudiante.uam.es", 13, 10, "$"
	INSTRUCCIONES DB "Use los argumentos /I para instalar y /D para desinstalar.", 13, 10, "$"
	INSTALANDO DB "Instalando vector de interrupcion", 13, 10, "$"
	DESINSTALANDO DB "Desinstalando vector de interrupcion", 13, 10, "$"
	NO_INSTALADO DB "El driver NO esta instalado", 13, 10, "$"
	SI_INSTALADO DB "El driver SI esta instalado", 13, 10, "$"
	; Guarda la direccion de la interrupcion 1CH para restaurarla.
	OLD_OFFSET_1CH DW 0
	OLD_SEGMENT_1CH DW 0
	; Firma digital para saber si el driver esta instalado o no.
	CONTADOR_1CH DB 0
	FIRMA DW 0CAF3H

	; Interrupcion 55H, argumentos de entrada AL la posicion X donde pintar, y AH la posicion Y donde pintar.
	; Pinta un cuadrado 10x10 con el pixel mas pequeño en (AL, AH), de color azul.
	RSI55 PROC FAR
		; Guarda los registros en pila.
		PUSH SI
		PUSH DI
		PUSH BX
		PUSH CX
		PUSH DX
		PUSH AX
		
		; Comprobamos que el modo de video es el correcto.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		CMP AL, 0DH ; 320x200 16 color graphics (VGA)
		JNE FIN_55
		
		; Restauramos AX y lo volvemos a guardar.
		POP AX
		PUSH AX
		
		; Si se sale de 240x180.
		CMP AL, 240 ; La X.
		JAE FIN_55
		CMP AH, 180 ; La Y.
		JAE FIN_55
		
		; Dos bucles anidados que pintan todo un cuadrado 10x10.
		MOV SI, 10 ; i, desde 10 hasta 1.
		BUCLE_EXTERNO_55:
			MOV DI, 10 ; j, desde 10 hasta 1.
			BUCLE_INTERNO_55:
				
				; Recupera AX de la pila para leer el valor.
				POP AX
				;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
				MOV CH, 0
				MOV CL, AL ; posicion X donde pintar
				DEC CX ; Para que el pixel mas bajo sea (AL, AH).
				ADD CX, DI ; X+j-1
				MOV DH, 0
				MOV DL, AH ; posicion Y donde pintar
				DEC DX ; Para que el pixel mas bajo sea (AL, AH).
				ADD DX, SI ; Y+i-1
				; Guarda de nuevo AX en la pila.
				PUSH AX
				
				mov ah, 0Ch
				MOV AL, 1H ; El color a pintar. (1 AZUL).
				mov bh, 00h ; numero pagina (dejar siempre a cero)
				INT 10H
			
				DEC DI
				JNZ BUCLE_INTERNO_55
		
			DEC SI
			JNZ BUCLE_EXTERNO_55
		
		FIN_55:
		; Recupera los valores de la pila.
		POP AX
		POP DX
		POP CX
		POP BX
		POP DI
		POP SI
		
		IRET
	RSI55 ENDP
	
	; Interrupcion 56H, argumentos de entrada AL la posicion X donde pintar, y AH la posicion Y donde pintar.
	; Pinta un cuadrado 10x10 con el pixel mas pequeño en (AL, AH), de color negro.
	RSI56 PROC FAR
		; Guarda los registros en pila.
		PUSH SI
		PUSH DI
		PUSH BX
		PUSH CX
		PUSH DX
		PUSH AX
		
		; Comprobamos que el modo de video es el correcto.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		CMP AL, 0DH ; 320x200 16 color graphics (VGA)
		JNE FIN_56
		
		; Restauramos AX y lo volvemos a guardar.
		POP AX
		PUSH AX
		
		; Si se sale de 240x180.
		CMP AL, 240 ; La X.
		JAE FIN_56
		CMP AH, 180 ; La Y.
		JAE FIN_56
		
		; Dos bucles anidados que pintan todo un cuadrado 10x10.
		MOV SI, 10 ; i, desde 10 hasta 1.
		BUCLE_EXTERNO_56:
			MOV DI, 10 ; j, desde 10 hasta 1.
			BUCLE_INTERNO_56:
				
				; Recupera AX de la pila para leer el valor.
				POP AX
				;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
				MOV CH, 0
				MOV CL, AL ; posicion X donde pintar
				DEC CX ; Para que el pixel mas bajo sea (AL, AH).
				ADD CX, DI ; X+j-1
				MOV DH, 0
				MOV DL, AH ; posicion Y donde pintar
				DEC DX ; Para que el pixel mas bajo sea (AL, AH).
				ADD DX, SI ; Y+i-1
				; Guarda de nuevo AX en la pila.
				PUSH AX
				
				mov ah, 0Ch
				MOV AL, 0H ; El color a pintar. (0 NEGRO).
				mov bh, 00h ; numero pagina (dejar siempre a cero)
				INT 10H
			
				DEC DI
				JNZ BUCLE_INTERNO_56
		
			DEC SI
			JNZ BUCLE_EXTERNO_56
		
		FIN_56:
		; Recupera los valores de la pila.
		POP AX
		POP DX
		POP CX
		POP BX
		POP DI
		POP SI
		
		IRET
	RSI56 ENDP
	
	; Interrupcion 57H, argumentos de entrada AL la posicion X donde pintar, y AH la posicion Y donde pintar.
	; Pinta un cuadrado 10x10 con el pixel mas pequeño en (AL, AH), de color rojo.
	RSI57 PROC FAR
		; Guarda los registros en pila.
		PUSH SI
		PUSH DI
		PUSH BX
		PUSH CX
		PUSH DX
		PUSH AX
		
		; Comprobamos que el modo de video es el correcto.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		CMP AL, 0DH ; 320x200 16 color graphics (VGA)
		JNE FIN_57
		
		; Restauramos AX y lo volvemos a guardar.
		POP AX
		PUSH AX
		
		; Si se sale de 240x180.
		CMP AL, 240 ; La X.
		JAE FIN_57
		CMP AH, 180 ; La Y.
		JAE FIN_57
		
		; Dos bucles anidados que pintan todo un cuadrado 10x10.
		MOV SI, 10 ; i, desde 10 hasta 1.
		BUCLE_EXTERNO_57:
			MOV DI, 10 ; j, desde 10 hasta 1.
			BUCLE_INTERNO_57:
				
				; Recupera AX de la pila para leer el valor.
				POP AX
				;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
				MOV CH, 0
				MOV CL, AL ; posicion X donde pintar
				DEC CX ; Para que el pixel mas bajo sea (AL, AH).
				ADD CX, DI ; X+j-1
				MOV DH, 0
				MOV DL, AH ; posicion Y donde pintar
				DEC DX ; Para que el pixel mas bajo sea (AL, AH).
				ADD DX, SI ; Y+i-1
				; Guarda de nuevo AX en la pila.
				PUSH AX
				
				mov ah, 0Ch
				MOV AL, 4H ; El color a pintar. (4 ROJO).
				mov bh, 00h ; numero pagina (dejar siempre a cero)
				INT 10H
			
				DEC DI
				JNZ BUCLE_INTERNO_57
		
			DEC SI
			JNZ BUCLE_EXTERNO_57
		
		FIN_57:
		; Recupera los valores de la pila.
		POP AX
		POP DX
		POP CX
		POP BX
		POP DI
		POP SI
		
		IRET
	RSI57 ENDP
	
	; Interrupcion 58H, argumentos de entrada AL la posicion X donde pintar, y AH la posicion Y donde pintar.
	; Pinta un cuadrado 10x10 con el pixel mas pequeño en (AL, AH), de color amarillo.
	RSI58 PROC FAR
		; Guarda los registros en pila.
		PUSH SI
		PUSH DI
		PUSH BX
		PUSH CX
		PUSH DX
		PUSH AX
		
		; Comprobamos que el modo de video es el correcto.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		CMP AL, 0DH ; 320x200 16 color graphics (VGA)
		JNE FIN_58
		
		; Restauramos AX y lo volvemos a guardar.
		POP AX
		PUSH AX
		
		; Si se sale de 240x180.
		CMP AL, 240 ; La X.
		JAE FIN_58
		CMP AH, 180 ; La Y.
		JAE FIN_58
		
		; Dos bucles anidados que pintan todo un cuadrado 10x10.
		MOV SI, 10 ; i, desde 10 hasta 1.
		BUCLE_EXTERNO_58:
			MOV DI, 10 ; j, desde 10 hasta 1.
			BUCLE_INTERNO_58:
				
				; Recupera AX de la pila para leer el valor.
				POP AX
				;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
				MOV CH, 0
				MOV CL, AL ; posicion X donde pintar
				DEC CX ; Para que el pixel mas bajo sea (AL, AH).
				ADD CX, DI ; X+j-1
				MOV DH, 0
				MOV DL, AH ; posicion Y donde pintar
				DEC DX ; Para que el pixel mas bajo sea (AL, AH).
				ADD DX, SI ; Y+i-1
				; Guarda de nuevo AX en la pila.
				PUSH AX
				
				mov ah, 0Ch
				MOV AL, 0EH ; El color a pintar. (0EH amarillo).
				mov bh, 00h ; numero pagina (dejar siempre a cero)
				INT 10H
			
				DEC DI
				JNZ BUCLE_INTERNO_58
		
			DEC SI
			JNZ BUCLE_EXTERNO_58
		
		FIN_58:
		; Recupera los valores de la pila.
		POP AX
		POP DX
		POP CX
		POP BX
		POP DI
		POP SI
		
		IRET
	RSI58 ENDP
	
	; Incrementa en 1 una variable global CONTADOR_1CH cada vez que se llama.
	RSI_1C PROC FAR
		PUSH AX
		
		MOV AL, BYTE PTR CONTADOR_1CH
		INC AL
		MOV BYTE PTR CONTADOR_1CH, AL
		
		POP AX
		
		IRET
	RSI_1C ENDP

	; Te instala la interrupcion 55H y la 57H.
	INSTALADOR PROC
		; Escribe instalando.
		MOV AH, 09H
		MOV DX, OFFSET INSTALANDO
		INT 21H
		
		; Guarda la direccion de 1CH.
		MOV AX, 0
		MOV ES, AX
		MOV AX, ES:[1CH*4]
		MOV WORD PTR OLD_OFFSET_1CH, AX
		MOV AX, ES:[1CH*4+2]
		MOV WORD PTR OLD_SEGMENT_1CH, AX
	
		
		; Instala la interrupcion 55H.
		MOV AX, 0
		MOV ES, AX
		MOV CX, OFFSET RSI55
		MOV BX, CS
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV ES:[55H*4], CX
		MOV ES:[55H*4+2], BX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Instala la interrupcion 56H.
		MOV AX, 0
		MOV ES, AX
		MOV CX, OFFSET RSI56
		MOV BX, CS
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV ES:[56H*4], CX
		MOV ES:[56H*4+2], BX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL

		; Instala la interrupcion 57H.
		MOV AX, 0
		MOV ES, AX
		MOV CX, OFFSET RSI57
		MOV BX, CS
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV ES:[57H*4], CX
		MOV ES:[57H*4+2], BX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Instala la interrupcion 58H.
		MOV AX, 0
		MOV ES, AX
		MOV CX, OFFSET RSI58
		MOV BX, CS
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV ES:[58H*4], CX
		MOV ES:[58H*4+2], BX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Instala la interrupcion 1CH.
		MOV AX, 0
		MOV ES, AX
		MOV CX, OFFSET RSI_1C
		MOV BX, CS
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV ES:[1CH*4], CX
		MOV ES:[1CH*4+2], BX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
	
		
		MOV DX, OFFSET INSTALADOR
		INT 27H
		
		RET
	INSTALADOR ENDP

	DESINSTALADOR PROC
		PUSH AX
		PUSH BX
		PUSH CX
		PUSH DX
		PUSH ES
		PUSH DS
	
		; Escribe desinstalando.
		MOV AH, 09H
		MOV DX, OFFSET DESINSTALANDO
		INT 21H
		
		; Guarda en la variable la antigua direccion de 1CH.
		MOV AX, 0
		MOV ES, AX
		MOV SI, WORD PTR ES:[55H*4]
		MOV AX, WORD PTR ES:[55H*4+2]
		
		MOV ES, AX
		MOV BX, WORD PTR ES:[SI-5]
		MOV WORD PTR OLD_SEGMENT_1CH, BX
		MOV BX, WORD PTR ES:[SI-7]
		MOV WORD PTR OLD_OFFSET_1CH, BX
		
		MOV AX, WORD PTR OLD_OFFSET_1CH
		CMP AX, 0
		JNE ESTA_INSTALADO
		MOV AX, WORD PTR OLD_SEGMENT_1CH
		CMP AX, 0
		JNE ESTA_INSTALADO
		
		; Si no esta instalado no desinstalar.
		JMP FIN_DESINSTALADOR
		
		ESTA_INSTALADO:
		
		; Desinstala la 55H.
		MOV CX, 0
		MOV DS, CX
		MOV ES, DS:[55H*4+2]
		MOV BX, ES:[2CH]
		MOV AH, 49H
		INT 21H
		MOV ES, BX
		INT 21H
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV DS:[55H*4], CX
		MOV DS:[55H*4+2], CX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Desinstala la 56H.
		MOV CX, 0
		MOV DS, CX
		MOV ES, DS:[56H*4+2]
		MOV BX, ES:[2CH]
		MOV AH, 49H
		INT 21H
		MOV ES, BX
		INT 21H
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV DS:[56H*4], CX
		MOV DS:[56H*4+2], CX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Desinstala la 57H.
		MOV CX, 0
		MOV DS, CX
		MOV ES, DS:[57H*4+2]
		MOV BX, ES:[2CH]
		MOV AH, 49H
		INT 21H
		MOV ES, BX
		INT 21H
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV DS:[57H*4], CX
		MOV DS:[57H*4+2], CX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Desinstala la 58H.
		MOV CX, 0
		MOV DS, CX
		MOV ES, DS:[58H*4+2]
		MOV BX, ES:[2CH]
		MOV AH, 49H
		INT 21H
		MOV ES, BX
		INT 21H
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV DS:[58H*4], CX
		MOV DS:[58H*4+2], CX
		IN AL, 21H ; STI
		AND AL, 11111110b ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		; Desinstala la 1CH.
		MOV CX, 0
		MOV DS, CX
		MOV ES, DS:[1CH*4+2]
		MOV BX, ES:[2CH]
		MOV AH, 49H
		INT 21H
		MOV ES, BX
		INT 21H
		IN AL, 21H ; CLI
		OR AL, 00000001b ; Pone a 1 el ultimo bit.
		OUT 21H, AL
		MOV CX, WORD PTR OLD_OFFSET_1CH
		MOV DS:[1CH*4], CX
		MOV CX, WORD PTR OLD_SEGMENT_1CH
		MOV DS:[1CH*4+2], CX
		IN AL, 21H ; STI
		AND AL, 11111110B ; Pone a 0 el ultimo bit.
		OUT 21H, AL
		
		FIN_DESINSTALADOR:
		
		POP DS
		POP ES
		POP DX
		POP CX
		POP BX
		POP AX
		
		RET
	DESINSTALADOR ENDP

	MAIN PROC 
		MOV SI, 82H ; 82H CAMPO DEL PSP. (Primer char de los argumentos de entrada).
		; Comprobar si se quiere instalar.
		CMP BYTE PTR ES:[SI], '/'
		JNE NO_INSTALADOR
		CMP BYTE PTR ES:[SI+1], 'I'
		JNE NO_INSTALADOR
		CMP BYTE PTR ES:[SI+2], 13 ; EL '\n'.
		JNE NO_INSTALADOR	
		
		CALL INSTALADOR
		JMP FIN_INICIO
		
		; Comprobar si se quiere desinstalar.
		NO_INSTALADOR:
		CMP BYTE PTR ES:[SI], '/'
		JNE NO_DESINSTALADOR
		CMP BYTE PTR ES:[SI+1], 'D'
		JNE NO_DESINSTALADOR	
		CMP BYTE PTR ES:[SI+2], 13 ; EL '\n'.
		JNE NO_DESINSTALADOR
		
		CALL DESINSTALADOR
		JMP FIN_INICIO
		
		; Mostrar informacion y el estado del driver.
		NO_DESINSTALADOR:
		
		; Escribe el autor.
		MOV AH, 09H
		MOV DX, OFFSET AUTOR
		INT 21H
		
		; Escribe el correo.
		MOV AH, 09H
		MOV DX, OFFSET CORREO
		INT 21H
		
		; Escribe las instrucciones.
		MOV AH, 09H
		MOV DX, OFFSET INSTRUCCIONES
		INT 21H
		
		; Comprueba si el driver esta instalado o no.
		MOV AX, 0
		MOV ES, AX
		MOV SI, WORD PTR ES:[55H*4]
		MOV AX, WORD PTR ES:[55H*4+2]
		
		; Se comprueba si el vector de interrupcion es 0h:0h
		CMP SI, 0H
		JNE BYTE_BAJO_NO_CERO
		CMP AX, 0H
		JE NO_ESTA_INSTALADO
		
		BYTE_BAJO_NO_CERO:
		
		; Se copmrueba si la interrupcion instalada es la nuestra.
		MOV ES, AX
		MOV BX, WORD PTR ES:[SI-2]
		
		CMP BX, FIRMA ; Se comprueba con la firma.
		JNE NO_ESTA_INSTALADO
		
		; Caso de que si este instalado.
		MOV AH, 09H
		MOV DX, OFFSET SI_INSTALADO
		INT 21H
		JMP FIN_INICIO
		
		; Caso de que no este instalado.
		NO_ESTA_INSTALADO:
		MOV AH, 09H
		MOV DX, OFFSET NO_INSTALADO
		INT 21H
		
		
		FIN_INICIO: ; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
		
	MAIN ENDP 

	; FIN DEL SEGMENTO DE CODIGO 
CODE ENDS 

; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION 
END INICIO