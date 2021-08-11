;*********************************************************************
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

; DEFINICION DEL SEGMENTO DE CODIGO COMO PUBLICO
_TEXT SEGMENT BYTE PUBLIC 'CODE'
    ASSUME CS:_TEXT

	; COMIENZO DEL PROCEDIMIENTO PRINCIPAL
	_MAIN PROC 
		;COMIENZO DEL PROGRAMA 
		PUBLIC _encuentraSubcadena
		PUBLIC _calculaSegundoDC		
	_MAIN ENDP 

	; Recibe como argumentos un unsigned char* str y un unsigned char* substr.
	; Devuelve el indice del primer match de substr en str (con 0-indexed), en caso
	; de que no haya match devuelve -1.
	_encuentraSubcadena PROC FAR
		PUSH BP
		MOV BP, SP
		
		MOV CX, -1
		; Para cada indice CX de str probar si substr empieza ahi.
		BUCLE_EXTERIOR:
			INC CX
			
			MOV DI, BP
			ADD DI, 6
			LES DI, SS:[DI] ; Carga la primera posicion de str.
			ADD DI, CX ; Apunta a la posicion CX de str.
			
			MOV SI, BP
			ADD SI, 10
			LES SI, SS:[SI] ; Carga la primera posicio de substr.
			
			MOV AL, BYTE PTR ES:[DI] ; Si str[CX] = 0 es que no ha habido un match.
			CMP AL, 0
			JE FAIL_ENCUENTRASUBCADENA
			
			BUCLE_INTERIOR: ; Se comprueba str[DI] con substr[SI] y se va incrementando DI y SI.
				MOV AL, BYTE PTR ES:[DI] ; Carga en AL str[DI].
				MOV BL, BYTE PTR ES:[SI] ; Carga en BL substr[SI].
				
				CMP BL, 0
				JE SUCCESS_ENCUENTRASUBCADENA ; Si substr acaba entonces ha habido un match.
				
				CMP AL, BL
				JNE BUCLE_EXTERIOR ; Si str[DI] != substr[SI] no va a haber match.
				
				INC DI
				INC SI
				JMP BUCLE_INTERIOR ; DI++ y SI++, puede haber match.

		SUCCESS_ENCUENTRASUBCADENA: ; Devolver el primer indice donde ha habido match.
		POP BP
		MOV AX, CX
		RET
		
		FAIL_ENCUENTRASUBCADENA: ; Devolver -1, error, no ha habido match.
		POP BP
		MOV AX, -1
		RET
	_encuentraSubcadena ENDP
	
	; Recibe como argumento un numero bancario de 10 cifras (en ASCII) y
	; devuelve su numero de control.
	_calculaSegundoDC PROC FAR
		PUSH BP
		MOV BP, SP
		
		MOV DI, BP
		ADD DI, 6
		LES DI, SS:[DI] ; Apunta en la primera direccion de la str.
		
		MOV CX, 0
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 1º direccion de la str.
		SUB AL, 30H ; Transforma el caracter ascii en un numero.
		MOV BL, 1 ; Multiplica por el numero dado en el algoritmo.
		MUL BL
		ADD CL, AL ; Suma al acumulador CL.
		INC DI ; Apunta en la siguiente posicion de la str.
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 2º direccion de la str.
		SUB AL, 30H
		MOV BL, 2
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 3º direccion de la str.
		SUB AL, 30H
		MOV BL, 4
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 4º direccion de la str.
		SUB AL, 30H
		MOV BL, 8
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 5º direccion de la str.
		SUB AL, 30H
		MOV BL, 5
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 6º direccion de la str.
		SUB AL, 30H
		MOV BL, 10
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 7º direccion de la str.
		SUB AL, 30H
		MOV BL, 9
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 8º direccion de la str.
		SUB AL, 30H
		MOV BL, 7
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 9º direccion de la str.
		SUB AL, 30H
		MOV BL, 3
		MUL BL
		ADD CL, AL
		INC DI
		
		MOV AL, BYTE PTR ES:[DI] ; Lee la 10º direccion de la str.
		SUB AL, 30H
		MOV BL, 6
		MUL BL
		ADD CL, AL
		
		MOV AL, CL ; Pasa la suma acumulada de CL en AL.
		MOV BL, 11
		DIV BL ; Divide el acumulado en AL entre 11.
		MOV AL, AH
		MOV AH, 0
		MOV BL, -1
		MUL BL
		ADD AL, 11 ; AL = 11 + (-AL).
		
		CMP AL, 10
		JE RETURN_CASE10 ; Caso en el que el digito de control sea 10.
		
		POP BP
		RET
		
		RETURN_CASE10:
			POP BP
			MOV AX, 1 ; Si el digito de control es 10 tiene que devolver 1.
			RET
	_calculaSegundoDC ENDP
	
_TEXT ENDS 
END