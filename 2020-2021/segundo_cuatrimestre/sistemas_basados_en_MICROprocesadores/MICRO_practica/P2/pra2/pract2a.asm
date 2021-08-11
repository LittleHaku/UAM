; Autor: Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
; Grupo 2301 Doble Grado.
; Práctica 2, ejercicio a

; DEFINICION DEL SEGMENTO DE DATOS
DATOS SEGMENT
	; DEFINICION DE LOS VECTORES
	vector1 db 1,2,2,4
	vector2 db 4,2,5,1
	vector3 db 3,2,4,1
	errorNumDif DB "FUERA DEL CONJUNTO: 1,2,3,4", 13, 10, '$'
	salidaCorrecta DB "CORRECTO", 13, 10, '$'
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
		
		MOV BX, OFFSET vector1
		CALL IN_OUT ; Procesa el vector1.
		
		MOV BX, OFFSET vector2
		CALL IN_OUT ; Procesa el vector2.
		
		MOV BX, OFFSET vector3
		CALL IN_OUT ; Procesa el vector3.
		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
		
	INICIO ENDP
	;ESPACIO PARA SUBRUTINAS
	; Escribe salidaCorrecta si los numeros del vector
	; con base en BX pertenecen al [1, 4], sino errorNumDif.
	IN_OUT PROC
		MOV CX, 4 ; El vector tiene dimension 4.
		
		BUCLE_IN_OUT:
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-1] ; Procesa los numeros de uno en uno.
		CMP AL, 4
		JG INVALID ; Si AL > 4 es INVALID.
		DEC CX
		JCXZ VALID ; Si ha procesado los 4 numeros es VALID
		JMP BUCLE_IN_OUT ; Repite bucle.
		
		
		INVALID: ; Imprime errorNumDif.
		MOV AH, 09H
		MOV DX, OFFSET errorNumDif
		INT 21H
		
		RET
		
		VALID: ; Imprime salidaCorrecta.
		MOV AH, 09H
		MOV DX, OFFSET salidaCorrecta
		INT 21H
		
		RET
	
	ENDP IN_OUT
	
	
	
; FIN DEL SEGMENTO DE CODIGO
CODE ENDS
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION
END INICIO