; Autor: Junco de las Heras Valenzuela, junco.heras@estudiante.uam.es
; Grupo 2301 Doble Grado.
; Práctica 2, ejercicio D.

NUM_VECTORES EQU 4 ; AQUI INDICAMOS EL NUMERO DE VECTORES QUE VAMOS A ANALIZAR
NUM_COMPONENTES EQU 4 ; INDICAMOS EL NUMERO DE ELEMENTOS EN CADA VECTOR
NUM_TOTAL_ELEMENTOS EQU NUM_VECTORES * NUM_COMPONENTES ; NUMERO TOTAL DE ELEMENTOS SUMANDO TODOS LOS VECTORES
NUM_MAXIMO_DE_COMPONENTE EQU 4 ; LIMITE SUPERIOR QUE PUEDEN TOMAR LOS ELEMENTOS
NUM_MINIMO_DE_COMPONENTE EQU 1 ; LIMITE INFERIOR QUE PUEDEN TOMAR LOS ELEMENTOS

; DEFINICION DEL SEGMENTO DE DATOS
DATOS SEGMENT
	vector1 db 1,2,3,4
	vector2 db 3,4,1,2
	vector3 db 2,1,4,3
	vector4 db 4,3,2,1
	conversionASCI DB " ", 13, 10, '$' ; CADENA DONDE SE VAN A GUARDAR LAS CONVERSIONES DE LOS NUMEROS
	imprimirFilasSI DB "FILAS VALIDAS", 13, 10, '$'
	imprimirFilasNO DB "FILAS NO VALIDAS", 13, 10, '$'
	imprimirColumnasSI DB "COLUMNAS VALIDAS", 13, 10, '$'
	imprimirColumnasNO DB "COLUMNAS NO VALIDAS", 13, 10, '$'
	imprimirRegionesSI DB "REGIONES VALIDAS $"
	imprimirRegionesNO DB "REGIONES NO VALIDAS $"
	imprimirIntroduceVector DB "Introduce un vector de 4 numeros del 0 al 9", 13, 10, '$'
	imprimirVectorNO DB "VECTOR INCORRECTO", 13, 10, '$'
	converted_str DB 6 DUP(0); 2^16 = 65536, 5 CHARS + '$'
	read_buffer DB 10 DUP(0)
DATOS ENDS
;**************************************************************************
; DEFINICION DEL SEGMENTO DE PILA
PILA SEGMENT STACK "STACK"
	DB 40H DUP (0) ;ejemplo de inicialización, 64 bytes inicializados a 0
PILA ENDS
;**************************************************************************
; DEFINICION DEL SEGMENTO EXTRA
EXTRA SEGMENT
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
		
		MOV BX, 0
		CALL INPUT_MATRIX ; Lee la matriz.
		
		MOV BX, OFFSET vector1
		CALL IMPRIME_MATRIZ ; Imprime la matriz.
		
		MOV BX, OFFSET vector1
		CALL COMPRUEBA_FILAS ; Comprueba las filas de la matriz.
		
		MOV BX, OFFSET vector1
		CALL COMPRUEBA_COLUMNAS ; Comprueba las columnas de la matriz.
		
		MOV BX, OFFSET vector1
		CALL COMPRUEBA_REGIONES ; Comprueba las regiones de la matriz.
		
		; FIN DEL PROGRAMA 
		MOV AX, 4C00H 
		INT 21H 
	INICIO ENDP
	
	; Proceso que dado BX el offset de una matriz de 4x4 posiciones lo lee por teclado.
	; Repite pedir el input hasta que cada vector sean 4 numeros del 0 al 9 
	; separados por un espacio entre ellos.
	INPUT_MATRIX PROC
		CALL INPUT_VECTOR ; Lee el 1º vector.
		ADD BX, 4
		CALL INPUT_VECTOR ; Lee el 2º vector.
		ADD BX, 4
		CALL INPUT_VECTOR ; Lee el 3º vector.
		ADD BX, 4
		CALL INPUT_VECTOR ; Lee el 4º vector.
		RET	
	INPUT_MATRIX ENDP
	
	; Proceso que dado BX el offset de un vector de 4 posiciones lo lee por teclado.
	; Repite pedir el input hasta que el vector sean 4 numeros del 0 al 9 
	; separados por un espacio entre ellos.
	INPUT_VECTOR PROC
		MOV AH, 09H
		MOV DX, OFFSET imprimirIntroduceVector
		INT 21H
		
		MOV AH, 0AH
		MOV DX, OFFSET read_buffer
		MOV read_buffer[0], 8 ; read at most 8 Bytes only.
		INT 21H
		
		CMP read_buffer[1], 7
		JNE REPEAT_INPUT
		
		MOV AH, read_buffer[2] ; 1º Num.
		CMP AH, 30H ; Char num < 0.
		JL REPEAT_INPUT
		CMP AH, 39H ; Char num > 9.
		JG REPEAT_INPUT
		ADD AH, -30H ; Convert ASCII to num.
		MOV vector1[BX], AH ; Save the 1º num.

		MOV AH, read_buffer[3] ; 1º space.
		CMP AH, 20H ; Char != space.
		JNE REPEAT_INPUT
		
		MOV AH, read_buffer[4] ; 2º Num.
		CMP AH, 30H ; Char num < 0.
		JL REPEAT_INPUT
		CMP AH, 39H ; Char num > 9.
		JG REPEAT_INPUT
		ADD AH, -30H ; Convert ASCII to num.
		MOV vector1[BX+1], AH ; Save the 2º num.

		MOV AH, read_buffer[5] ; 2º space.
		CMP AH, 20H ; Char != space.
		JNE REPEAT_INPUT
		
		MOV AH, read_buffer[6] ; 3º Num.
		CMP AH, 30H ; Char num < 0.
		JL REPEAT_INPUT
		CMP AH, 39H ; Char num > 9.
		JG REPEAT_INPUT
		ADD AH, -30H ; Convert ASCII to num.
		MOV vector1[BX+2], AH ; Save the 3º num.

		MOV AH, read_buffer[7] ; 3º space.
		CMP AH, 20H ; Char != space.
		JNE REPEAT_INPUT
		
		MOV AH, read_buffer[8] ; 4º Num.
		CMP AH, 30H ; Char num < 0.
		JL REPEAT_INPUT
		CMP AH, 39H ; Char num > 9.
		JG REPEAT_INPUT
		ADD AH, -30H ; Convert ASCII to num.
		MOV vector1[BX+3], AH ; Save the 4º num.
		
		; The input vector is correct.		
		RET
		
		REPEAT_INPUT:
		MOV AH, 09H
		MOV DX, OFFSET imprimirVectorNO
		INT 21H
		JMP INPUT_VECTOR	
	INPUT_VECTOR ENDP
	
	; Proceso que dado en BX el offset de una matriz de 4 vectores de tamanio 4, imprime
	; imprimirRegionesNO si no se cumple las reglas, en caso contrario imprime imprimirRegionesSI.
	COMPRUEBA_REGIONES PROC
		CALL COMPRUEBA_REGION ; Upper left sub-matrix.
		JCXZ INVALID_REGIONES
		
		ADD BX, 2
		CALL COMPRUEBA_REGION ; Upper right sub-matrix.
		JCXZ INVALID_REGIONES
		
		ADD BX, 6
		CALL COMPRUEBA_REGION ; Lower left sub-matrix.
		JCXZ INVALID_REGIONES
		
		ADD BX, 2
		CALL COMPRUEBA_REGION ; Lower right sub-matrix.
		JCXZ INVALID_REGIONES
			
	
		; Valid Regions.
		MOV AH, 09H
		MOV DX, OFFSET imprimirRegionesSI
		INT 21H
		RET
		
		INVALID_REGIONES:
		MOV AH, 09H
		MOV DX, OFFSET imprimirRegionesNO
		INT 21H
		RET
	COMPRUEBA_REGIONES ENDP
	
	; Proceso que dado en BX el offset de una region 2x2 devuelva en CX = 1 si la region es valida
	; en caso contrario guarda en CX el valor 0.
	COMPRUEBA_REGION PROC
		; Si la region es [A B]
		;                 [C D].
		MOV AL, DS:[BX] 
		CMP AL, DS:[BX+1] ; Compara A con B.
		JE INVALID_REGION
		CMP AL, DS:[BX+4] ; Compara A con C.
		JE INVALID_REGION
		CMP AL, DS:[BX+5] ; Compara A con D.
		JE INVALID_REGION
		
		MOV AL, DS:[BX+1]
		CMP AL, DS:[BX+4] ; Compara B con C.
		JE INVALID_REGION
		CMP AL, DS:[BX+5] ; Compara B con D.
		JE INVALID_REGION
		
		MOV AL, DS:[BX+4]
		CMP AL, DS:[BX+5] ; Compara C con D.
		JE INVALID_REGION
		
		; Valid region.
		MOV CX, 1
		RET
		
		INVALID_REGION:
		MOV CX, 0
		RET	
	COMPRUEBA_REGION ENDP
	
	; Proceso que dado en BX el offset de una matriz de 4 vectores de tamanio 4, imprime
	; imprimirFilasNO si no se cumple las reglas, en caso contrario imprime imprimirFilasSI.
	COMPRUEBA_FILAS PROC
		MOV CX, 0
		BUCLE_COMPRUEBA_FILAS:
		PUSH CX

		CALL OUT_ERR1_FILAS
		JCXZ FIN_COMPRUEBA_FILAS
		CALL OUT_ERR2_FILAS
		JCXZ FIN_COMPRUEBA_FILAS
		ADD BX, 4

		POP CX
		INC CX
		CMP CX, 4
		JNZ BUCLE_COMPRUEBA_FILAS
		
		
		MOV AH, 09H
		MOV DX, OFFSET imprimirFilasSI
		INT 21H
		RET
		
		FIN_COMPRUEBA_FILAS:
		POP CX ; CLEAR THE STACK.
		RET
	COMPRUEBA_FILAS ENDP
	
	; Proceso que dado en BX el offset de una matriz de 4 vectores de tamanio 4, imprime
	; imprimirColumnasNO si no se cumple las reglas, en caso contrario imprime imprimirColumnasSI.
	COMPRUEBA_COLUMNAS PROC
		MOV CX, 0
		BUCLE_COMPRUEBA_COLUMNAS:
		PUSH CX

		CALL OUT_ERR1_COLUMNAS
		JCXZ FIN_COMPRUEBA_COLUMNAS
		CALL OUT_ERR2_COLUMNAS
		JCXZ FIN_COMPRUEBA_COLUMNAS
		ADD BX, 1

		POP CX
		INC CX
		CMP CX, 4
		JNZ BUCLE_COMPRUEBA_COLUMNAS
		
		
		MOV AH, 09H
		MOV DX, OFFSET imprimirColumnasSI
		INT 21H
		RET
		
		FIN_COMPRUEBA_COLUMNAS:
		POP CX; CLEAR THE STACK.
		RET	
	COMPRUEBA_COLUMNAS ENDP
	
	; Proceso que dado en BX el offset de una matriz de 4 vectores de tamanio 4, imprime
	; en la pantalla su contenido.
	IMPRIME_MATRIZ PROC
		PUSH BX
		CALL IMPRIME_VECTOR
		POP BX
		MOV AH, 02H ; Imprime un \r de separacion.
		MOV DL, 13
		INT 21H
		MOV AH, 02H ; Imprime un \n de separacion.
		MOV DL, 10
		INT 21H

		ADD BX, 4
		PUSH BX
		CALL IMPRIME_VECTOR
		POP BX
		MOV AH, 02H ; Imprime un \r de separacion.
		MOV DL, 13
		INT 21H
		MOV AH, 02H ; Imprime un \n de separacion.
		MOV DL, 10
		INT 21H

		ADD BX, 4
		PUSH BX
		CALL IMPRIME_VECTOR
		POP BX
		MOV AH, 02H ; Imprime un \r de separacion.
		MOV DL, 13
		INT 21H
		MOV AH, 02H ; Imprime un \n de separacion.
		MOV DL, 10
		INT 21H


		ADD BX, 4
		PUSH BX
		CALL IMPRIME_VECTOR
		POP BX
		MOV AH, 02H ; Imprime un \r de separacion.
		MOV DL, 13
		INT 21H
		MOV AH, 02H ; Imprime un \n de separacion.
		MOV DL, 10
		INT 21H
		
		RET
	IMPRIME_MATRIZ ENDP 
	
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

	; Imprime imprimirFilasNO si hay algun numero fuera del conjunto y guarda en CX un 0. Sino, guarda
	; en CX un 1.
	OUT_ERR1_FILAS PROC
		MOV CX, 4 ; El vector tiene dimension 4.
		
		BUCLE_ERR1:
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-1] ; Procesa los numeros de uno en uno.
		CMP AL, 4
		JG INVALID ; Si AL > 4 es INVALID.
		DEC CX
		JCXZ VALID ; Si ha procesado los 4 numeros es VALID
		JMP BUCLE_ERR1; Repite bucle.
		
		
		INVALID: ; Imprime imprimirFilasNO.
		MOV AH, 09H
		MOV DX, OFFSET imprimirFilasNO
		INT 21H
		
		MOV CX, 0
		RET
		
		VALID:
		
		MOV CX, 1
		RET	
	ENDP OUT_ERR1_FILAS
	
	; Imprime imprimirFilasNO si hay algun numero repetido y guarda en CX un 0. Sino, guarda
	; en CX un 1.
	OUT_ERR2_FILAS PROC
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
		MOV DX, OFFSET imprimirFilasNO
		INT 21H
		
		MOV CX, 0
		RET
	OUT_ERR2_FILAS ENDP	
	
	; Imprime imprimirColumnasNO si hay algun numero fuera del conjunto y guarda en CX un 0. Sino, guarda
	; en CX un 1. BX es BX+i donde i es la columna que se quiere comprobar (0..3).
	OUT_ERR1_COLUMNAS PROC
		MOV CX, 16 ; El vector tiene dimension 4.
		
		BUCLE_ERR1_COLUMNAS:
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-4] ; Procesa los numeros de uno en uno.
		CMP AL, 4
		JG INVALID_COLUMNAS ; Si AL > 4 es INVALID.
		ADD CX, -4
		JCXZ VALID_COLUMNAS ; Si ha procesado los 4 numeros es VALID
		JMP BUCLE_ERR1_COLUMNAS; Repite bucle.
		
		
		INVALID_COLUMNAS: ; Imprime imprimirColumnasNO.
		MOV AH, 09H
		MOV DX, OFFSET imprimirColumnasNO
		INT 21H
		
		MOV CX, 0
		RET
		
		VALID_COLUMNAS:
		
		MOV CX, 1
		RET	
	ENDP OUT_ERR1_COLUMNAS
	
	; Imprime imprimirColumnasNO si hay algun numero repetido y guarda en CX un 0. Sino, guarda
	; en CX un 1. Va recorriendo columnas de abajo hacia arriba. El vector v representa una columna.
	OUT_ERR2_COLUMNAS PROC
		; BUCLE_EXTERIOR itera desde [BX][3] HASTA [BX][1].
		; El pseudocodigo seria, si nuestro vector es v,
		; for(i = n-1; i > 0; i--) for(j = i-1; j >= 0; j--) if(v[i] == v[j]) salidaError. 
		MOV CX, 16 ; La matriz tiene dimension 4x4.
		
		ADD CX, 4
		BUCLE_EXTERIOR_ERR2_COLUMNAS:
		ADD CX, -4 ; CX ES EL CONTADOR DEL BUCLE EXTERIOR.
		JCXZ VALID_ERR2_COLUMNAS
		MOV SI, CX
		MOV AL, BYTE PTR [BX][SI-4] ; Elemento v[i] para comparar.
		
		MOV DX, CX
		; COMPARA LOS ELEMENTOS [BX][0] HASTA [BX][SI-2] CON [BX][SI-1].
		BUCLE_INTERIOR_ERR2_COLUMNAS:
		ADD DX, -4 ; DX ES EL CONTADOR DEL BUCLE INTERIOR.
		JZ BUCLE_EXTERIOR_ERR2_COLUMNAS
		MOV SI, DX
		CMP AL, BYTE PTR [BX][SI-4] ; Elemento v[j] para comparar.
		JZ INVALID_ERR2_COLUMNAS
		JMP BUCLE_INTERIOR_ERR2_COLUMNAS
			
		VALID_ERR2_COLUMNAS:
		MOV CX, 1
		RET
		
		INVALID_ERR2_COLUMNAS: ; Imprime salidaError.
		MOV AH, 09H
		MOV DX, OFFSET imprimirColumnasNO
		INT 21H
		
		MOV CX, 0
		RET
	OUT_ERR2_COLUMNAS ENDP	
	
	; FIN DEL SEGMENTO DE CODIGO
CODE ENDS
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION
END INICIO