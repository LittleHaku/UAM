;*********************************************************************
;* 
;*
;* Sistemas Basados en Microprocesador
;* 2020-2021
;* Practica 3b
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
		PUBLIC _pintaListaPixeles
		
	_MAIN ENDP 
	_pintaListaPixeles PROC FAR
		PUSH BP
		MOV BP, SP
		
		; Guardamos modo video para restaurarlo.
		; usamos interrupción 10h para entrar en modo video
		MOV AH,0Fh ; Peticion de obtencion de modo de video
		INT 10h ; Llamada al BIOS
		MOV MODO_VIDEO,AL ; SALVO EL MODO VIDEO Y LO ALMACENO EN AL

		; Ponemos modo video.
		mov ah, 00h ; configuramos entrada a modo video
		mov al, 12h ; 640x480 16 color graphics (VGA)
		int 10h
		
		; Ponemos el fondo de color.
		; configuramos color fondo verde 0010b
		mov ah, 0bh
		mov bh, 00h
		MOV BL, BYTE PTR [BP+8] ; bgcolor.
		int 10h
		
		; Bucle, va iterando sobre cada pixel y lo pinta.
		MOV SI, WORD PTR [BP+6] ; Num pixeles
		BUCLE_LISTA:
			DEC SI
		
			; Pintamos el pixel en la posicion i.
			;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
			mov ah, 0Ch
			; lee de la pila el valor apropiado para el color
			mov bh, 00h ; numero pagina (dejar siempre a cero)
			
			MOV DI, BP
			ADD DI, 14 ; 4 de call, 2 de BP, 2 de numPixeles, 2 de bgcolor, 4 de tiempoEspera.
			LES DI, SS:[DI] ; en ES:[DI] es donde esta guardado ArrayPixelesX.
			ADD DI, SI ; Coge la posicion i-esima.
			ADD DI, SI ; Cada elemento ocupa 2 bytes.
			MOV CX, WORD PTR ES:[DI]
			
			MOV DI, BP
			ADD DI, 18 ; 14 de antes, 4 de ArrayPixelsX.
			LES DI, SS:[DI] ; en ES:[DI] es donde esta guardado ArrayPixelesY.
			ADD DI, SI ; Coge la posicion i-esima.
			ADD DI, SI ; Cada elemento ocupa 2 bytes.
			MOV DX, WORD PTR ES:[DI]
			
			MOV DI, BP
			ADD DI, 22 ; 18 de antes, 4 de ArrayColores.
			LES DI, SS:[DI] ; en ES:[DI] es donde esta guardado ArrayColores.
			ADD DI, SI ; Coge la posicion i-esima.
			MOV AL, BYTE PTR ES:[DI]
			
			int 10h
			
			CMP SI, 0
			JNE BUCLE_LISTA
	
	
		; Esperamos activamente.
		;Int15H espera activa en microsegundos: 1 millon us = 1 segundo
		MOV CX, WORD PTR [BP+12]
		MOV DX, WORD PTR [BP+10]
		MOV     AH, 86H ;int 15h con AH=86h para espera de microsegundos en cx:dx
		INT     15H


		; Restauramos el modo de video.
		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h
		
		POP BP
		MOV AX, 0H
		RET
	_pintaListaPixeles ENDP
	
_TEXT ENDS 
END