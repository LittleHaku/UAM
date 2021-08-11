; Autor Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es
; Grupo: Doble Grado 2301

;************************************************************************** 
; DEFINICION DEL SEGMENTO DE DATOS
DATA SEGMENT
	MODO_VIDEO DB 0
DATA ENDS

;************************************************************************** 
; DEFINICION DEL SEGMENTO DE CODIGO 
CODIGO SEGMENT
	ASSUME CS: CODIGO, DS: DATA
	
	MAIN PROC
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
		
		; Se guarda la pos del jugador dos en CX.
		MOV CH, 100 ; Y.
		MOV CL, 200 ; X.
		MOV AX, CX		
		INT 57H
		
		; Un bucle que lee teclas hasta que se pulse la q.
		; Nota, es case sensitive.
		BUCLE_TECLADO:
			; Comprueba si ha habido un keystroke.
			MOV AH, 01H
			INT 16H
			; Si no, repetir la espera.
			JZ BUCLE_TECLADO
			; Si si, leer la tecla y quitarla del bufer.
			MOV AH, 00H
			INT 16H

			; Se comprueba pulsacion del primer jugador.
			CMP AL, 'a'
			JNE NO_A
			CMP BL, 10
			JBE NO_A ; BL <= 10 .
			ADD BL, -10 ; Numeros de X mas bajos.
			NO_A:
			CMP AL, 'd'
			JNE NO_D
			CMP BL, 230
			JAE NO_D ; BL >= 230.
			ADD BL, 10 ; Numeros de X mas altos.
			NO_D:
			CMP AL, 'w'
			JNE NO_W
			CMP BH, 10
			JBE NO_W ; BH <= 10.
			ADD BH, -10 ; Numeros de Y mas bajos.
			NO_W:
			CMP AL, 's'
			JNE NO_S
			CMP BH, 170
			JAE NO_S ; BH >= 170.
			ADD BH, 10 ; Numeros de Y mas altos.
			NO_S:
			
			; Se comprueba pulsacion del primer jugador.
			CMP AL, 'j'
			JNE NO_J
			CMP CL, 10
			JBE NO_J ; CL <= 10.
			ADD CL, -10 ; Numeros de X mas bajos.
			NO_J:
			CMP AL, 'l'
			JNE NO_L
			CMP CL, 230
			JAE NO_L ; CL >= 230.
			ADD CL, 10 ; Numeros de X mas altos.
			NO_L:
			CMP AL, 'i'
			JNE NO_I
			CMP CH, 10
			JBE NO_I ; CH <= 10.
			ADD CH, -10 ; Numeros de Y mas bajos.
			NO_I:
			CMP AL, 'k'
			JNE NO_K
			CMP CH, 170
			JAE NO_K ; CH >= 170.
			ADD CH, 10 ; Numeros de Y mas altos.
			NO_K:
			
			; Se acaba el bucle.
			CMP AL, 'q'
			JE FIN_BUCLE_TECLADO
			
			; Vuelve a pintar al jugador 1.
			MOV AX, BX
			INT 55H
			
			; Vuelve a pintar al jugador 2.
			MOV AX, CX
			INT 57H
			
			JMP BUCLE_TECLADO
		FIN_BUCLE_TECLADO:

		; Restauramos el modo de video.
		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h

		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
	
	MAIN ENDP
CODIGO ENDS
END MAIN
