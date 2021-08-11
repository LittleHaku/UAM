; Autor Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es
; Grupo: Doble Grado 2301

;************************************************************************** 
; SBM 2020. ESTRUCTURA BÁSICA DE UN PROGRAMA EN ENSAMBLADOR 
;************************************************************************** 
; DEFINICION DEL SEGMENTO DE DATOS 
DATOS SEGMENT 
	;-- rellenar con los datos solicitados 
MARCADOR DB 0H
BEBE DW 0CAFEH
TABLA200 DB 200 DUP(0)
ERRORTOTAL1 DB "Este programa se cuelga siempre."
DATOS ENDS 
;************************************************************************** 
; DEFINICION DEL SEGMENTO DE PILA 
PILA SEGMENT STACK "STACK" 
	DB 40H DUP (0) ;ejemplo de inicialización, 64 bytes inicializados a 0 
PILA ENDS 
;************************************************************************** 
; DEFINICION DEL SEGMENTO EXTRA 
EXTRA SEGMENT 
	RESULT DW 0,0 ;ejemplo de inicialización. 2 PALABRAS (4 BYTES) 
EXTRA ENDS 
;************************************************************************** 
; DEFINICION DEL SEGMENTO DE CODIGO 
CODE SEGMENT 
ASSUME CS: CODE, DS: DATOS, ES: EXTRA, SS: PILA 
; COMIENZO DEL PROCEDIMIENTO PRINCIPAL 
INICIO PROC 
	; INICIALIZA LOS REGISTROS DE SEGMENTO CON SU VALOR  
	MOV AX, DATOS 
	MOV DS, AX 
	MOV AX, PILA 
	MOV SS, AX 
	MOV AX, EXTRA 
	MOV ES, AX 
	MOV SP, 64 ; CARGA EL PUNTERO DE PILA CON EL VALOR MAS ALTO 
	; FIN DE LAS INICIALIZACIONES 
	; COMIENZO DEL PROGRAMA 
	
	; El tercer caracter es el de offset 2, la 't'.
	MOV AL, ERRORTOTAL1[2]
	; La posicion 63 (que no 63H pues se sale de la tabla200) es un offset 62.
	; Se puede comprobar en el debugger accediendo en el panel de memoria a ds:41.
	MOV TABLA200[62], AL
	; La posicion 23 es el offset 22.
	; Se puede comprobar en el debugger accediendo en el panel de memoria a ds:1A.
	MOV AX, BEBE[0]
	MOV WORD PTR TABLA200[22], AX
	; Se puede comprobar en el debugger accediendo en el panel de memoria a ds:0.
	MOV MARCADOR[0], AH
	MOV AL, AL
	

	; FIN DEL PROGRAMA 
	MOV AX, 4C00H 
	INT 21H 
INICIO ENDP 
; FIN DEL SEGMENTO DE CODIGO 
CODE ENDS 
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION 
END INICIO