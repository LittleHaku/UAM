;******************************************************************************* 
;* VGADDGA.asm
;*
;* Sistemas Basados en Microprocesador
;* 2020-2021
;* Practica 3
;* Ejemplo de uso Int10h e Int15h
;*
;* author: David Gonzalez-Arjona
;* author: Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es.
;* group: 2301 Doble grado.
;* Notas: Pinta un pixel blanco en medio de un fondo verde
;*******************************************************************************

; DEFINICION DEL SEGMENTO DE DATOS 

DATOS SEGMENT

	MODO_VIDEO DB 0

DATOS ENDS 


; DEFINICION DEL SEGMENTO DE PILA 

PILA    SEGMENT STACK "STACK" 
    DB   40H DUP (0) 
PILA ENDS 


; DEFINICION DEL SEGMENTO EXTRA 

EXTRA     SEGMENT 
   
   
EXTRA ENDS 


; DEFINICION DEL SEGMENTO DE CODIGO 

CODE    SEGMENT 
    ASSUME CS:CODE, DS:DATOS, ES: EXTRA, SS:PILA 

; COMIENZO DEL PROCEDIMIENTO PRINCIPAL 

DDGA PROC 
    ;INICIALIZA LOS REGISTROS DE SEGMENTO CON SUS VALORES 
    MOV AX, DATOS 
    MOV DS, AX 

    MOV AX, PILA 
    MOV SS, AX 

    MOV AX, EXTRA 
    MOV ES, AX 

    ; CARGA EL PUNTERO DE PILA CON EL VALOR MAS ALTO 
    MOV SP, 64 
	
		 ; usamos interrupción 10h para entrar en modo video
		 MOV AH,0Fh ; Peticion de obtencion de modo de video
		 INT 10h ; Llamada al BIOS
		 MOV MODO_VIDEO,AL ; SALVO EL MODO VIDEO Y LO ALMACENO EN AL
	
		 mov ah, 00h ; configuramos entrada a modo video
		 mov al, 12h ; 640x480 16 color graphics (VGA)
		 int 10h
		 
		 ; configuramos color fondo verde 0010b
		 mov ah, 0bh
		 mov bh, 00h
		 mov bl, 02h; 0 color negro 2 ; color verde
		 int 10h
		
		;Int10H pintar pixel --> AH=0Ch 	AL = Color, BH = NumPag, CX = x, DX = y
		mov ah, 0Ch
		; lee de la pila el valor apropiado para el color
		mov al, 0Fh ; color blanco 1111b
		mov bh, 00h ; numero pagina (dejar siempre a cero)
		mov cx,320 ; posicion X donde pintar
		mov dx,240 ; posicion Y donde pintar
		int 10h
	
		;Int15H espera activa en microsegundos: 1 millon us = 1 segundo
		MOV     CX, 2Dh ; CX:DX forman el tiempo de espera: 1 second = F:4240H --> 3 seconds 2D:C6C0 h
		MOV     DX, 0C6C0h
		MOV     AH, 86H ;int 15h con AH=86h para espera de microsegundos en cx:dx
		INT     15H


		mov ah, 00h ; restaurar configuracion entrada a modo video
		mov al, MODO_VIDEO ; 
		int 10h

		mov ax, 4c00h
		int 21h
		
DDGA ENDP 


; FIN DEL SEGMENTO DE CODIGO 
CODE ENDS 
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION 
END DDGA 