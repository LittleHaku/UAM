; Autor: Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
; Grupo 2301 Doble Grado.
; Práctica 2, ejercicio b.

; DEFINICION DEL SEGMENTO DE DATOS
DATOS SEGMENT
	; DEFINICION DE LOS VECTORES
	vector1 db 1,2,2,4
	vector2 db 4,2,5,1
	vector3 db 3,2,4,1
	salidaError DB "REPETICION", 13, 10, '$'
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
		CALL EJERCICIO_B ; Procesa el vector1.
		
		MOV BX, OFFSET vector2
		CALL EJERCICIO_B ; Procesa el vector2.
		
		MOV BX, OFFSET vector3
		CALL EJERCICIO_B; Procesa el vector3.
		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
		
	INICIO ENDP
	;ESPACIO PARA SUBRUTINAS
	; Escribe salidaCorrecta si los numeros del vector
	; con base en BX no tienen elementos repetidos, sino escribe salidaError.
	EJERCICIO_B PROC
		; BUCLE_EXTERIOR itera desde [BX][3] HASTA [BX][1].
		; El pseudocodigo seria, si nuestro vector es v,
		; for(i = n-1; i > 0; i--) for(j = i-1; j >= 0; j--) if(v[i] == v[j]) salidaError. 
		MOV CX, 4 ; El vector tiene dimension 4.
		
		INC CX
		BUCLE_EXTERIOR:
		DEC CX ; CX ES EL CONTADOR DEL BUCLE EXTERIOR.
		JCXZ VALID
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-1] ; Elemento v[i] para comparar.
		
		MOV DX, CX
		; COMPARA LOS ELEMENTOS [BX][0] HASTA [BX][SI-2] CON [BX][SI-1].
		BUCLE_INTERIOR:
		DEC DX ; DX ES EL CONTADOR DEL BUCLE INTERIOR.
		JZ BUCLE_EXTERIOR
		MOV SI, DX
		CMP AL, BYTE PTR [BX][SI-1] ; Elemento v[j] para comparar.
		JZ INVALID
		JMP BUCLE_INTERIOR
			
		VALID: ; Imprime salidaCorrecta.
		MOV AH, 09H
		MOV DX, OFFSET salidaCorrecta
		INT 21H
		
		RET
		
		INVALID: ; Imprime salidaError.
		MOV AH, 09H
		MOV DX, OFFSET salidaError
		INT 21H
		
		RET			
	EJERCICIO_B ENDP
	
	
	
; FIN DEL SEGMENTO DE CODIGO
CODE ENDS
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION
END INICIO