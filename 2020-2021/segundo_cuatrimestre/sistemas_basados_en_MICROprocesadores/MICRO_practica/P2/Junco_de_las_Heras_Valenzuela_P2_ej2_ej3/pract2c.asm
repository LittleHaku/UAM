; Autor: Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
; Grupo 2301 Doble Grado.
; Práctica 2, ejercicio c.

; DEFINICION DEL SEGMENTO DE DATOS
DATOS SEGMENT
	; DEFINICION DE LOS VECTORES
	vector1 db 1,2,2,4
	vector2 db 4,2,5,1
	vector3 db 3,2,4,1
	salidaError1 DB "FUERA DEL CONJUNTO: 1,2,3,4", 13, 10, '$'
	salidaError2 DB "REPETICION", 13, 10, '$'
	salidaCorrecta DB "CORRECTO", 13, 10, '$'
	converted_str DB 6 DUP(0); 2^16 = 65536, 5 CHARS + '$'
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
		CALL EJERCICIO_C ; Procesa el vector1.
		
		MOV BX, OFFSET vector2
		CALL EJERCICIO_C ; Procesa el vector2.
		
		MOV BX, OFFSET vector3
		CALL EJERCICIO_C ; Procesa el vector3.
		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
		
	INICIO ENDP
	;ESPACIO PARA SUBRUTINAS
	
	; Imprime salidaError1 si hay algun numero fuera del conjunto y guarda en CX un 0. Sino, guarda
	; en CX un 1.
	OUT_ERR1 PROC
		MOV CX, 4 ; El vector tiene dimension 4.
		
		BUCLE_ERR1:
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-1] ; Procesa los numeros de uno en uno.
		CMP AL, 4
		JG INVALID ; Si AL > 4 es INVALID.
		DEC CX
		JCXZ VALID ; Si ha procesado los 4 numeros es VALID
		JMP BUCLE_ERR1; Repite bucle.
		
		
		INVALID: ; Imprime errorNumDif.
		MOV AH, 09H
		MOV DX, OFFSET salidaError1
		INT 21H
		
		MOV CX, 0
		RET
		
		VALID:
		
		MOV CX, 1
		RET	
	ENDP OUT_ERR1
	
	; Imprime salidaError1 si hay algun numero fuera del conjunto y guarda en CX un 0. Sino, guarda
	; en CX un 1.
	OUT_ERR2 PROC
		; BUCLE_EXTERIOR itera desde [BX][3] HASTA [BX][1].
		; El pseudocodigo seria, si nuestro vector es v,
		; for(i = n-1; i > 0; i--) for(j = i-1; j >= 0; j--) if(v[i] == v[j]) salidaError. 
		MOV CX, 4 ; El vector tiene dimension 4.
		
		INC CX
		BUCLE_EXTERIOR_ERR2:
		DEC CX ; CX ES EL CONTADOR DEL BUCLE EXTERIOR.
		JCXZ VALID_ERR2
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-1] ; Elemento v[i] para comparar.
		
		MOV DX, CX
		; COMPARA LOS ELEMENTOS [BX][0] HASTA [BX][SI-2] CON [BX][SI-1].
		BUCLE_INTERIOR_ERR2:
		DEC DX ; DX ES EL CONTADOR DEL BUCLE INTERIOR.
		JZ BUCLE_EXTERIOR_ERR2
		MOV SI, DX
		CMP AL, BYTE PTR [BX][SI-1] ; Elemento v[j] para comparar.
		JZ INVALID_ERR2
		JMP BUCLE_INTERIOR_ERR2
			
		VALID_ERR2:
		MOV CX, 1
		RET
		
		INVALID_ERR2: ; Imprime salidaError.
		MOV AH, 09H
		MOV DX, OFFSET salidaError2
		INT 21H
		
		MOV CX, 0
		RET
	OUT_ERR2 ENDP
	
	; Proceso que resuelve el ejercicio 3 para un vector con base BX.
	EJERCICIO_C PROC
		CALL IMPRIME_VECTOR ; Imprime los elementos del vector.
		
		CALL OUT_ERR1 ; Comprobar que los elementos pertenezcan a [1..4].
		JCXZ NOT_CORRECT ; Si imprime un fallo retornar del procedimiento.
		
		CALL OUT_ERR2 ; Comprobar que no haya elementos repetidos.
		JCXZ NOT_CORRECT ; Si imprime un fallo retornar del procedimiento.
		
		; Si se cumplen ambas condiciones.
		MOV AH, 09H ; Imprime salidaCorrecta.
		MOV DX, OFFSET salidaCorrecta
		INT 21H
		
		NOT_CORRECT:
		RET
	EJERCICIO_C ENDP
	
	; Proceso que dado en BX el offset de un vector de tamanio 4, imprime
	; en la pantalla su contenido.
	IMPRIME_VECTOR PROC
		MOV CX, 0
		
		BUCLE_EXTERIOR:		
		MOV SI, CX
		
		MOV AX, 0
		MOV AL, BYTE PTR[BX][SI] ; Lees el dato a imprimir.
		
		PUSH SI ; Imprime el caracter y guarda en pila los registros necesarios.
		PUSH CX
		PUSH BX
		CALL CONVERT_BIN_ASCII
		PUSH DS
		MOV DS, DX
		MOV AH, 09H
		MOV DX, BX
		INT 21H
		POP DS
		POP BX
		POP CX
		POP SI
		
		MOV AH, 02H ; Imprime un espacio de separacion.
		MOV DL, ' '
		INT 21H

		
		INC CX
		CMP CX, 4
		JNZ BUCLE_EXTERIOR
		
		RET
	IMPRIME_VECTOR ENDP
	
	; Recibe por argumento un numero binario en AX, y guardara en
	; memoria el codigo ASCII del numero + '$'. Devuelve DX:BX, 
	; donde DX es el segmento de la cadena resultado y BX su offset.
	CONVERT_BIN_ASCII PROC	
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
		
		RET
	CONVERT_BIN_ASCII ENDP	
	
	
; FIN DEL SEGMENTO DE CODIGO
CODE ENDS
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION
END INICIO