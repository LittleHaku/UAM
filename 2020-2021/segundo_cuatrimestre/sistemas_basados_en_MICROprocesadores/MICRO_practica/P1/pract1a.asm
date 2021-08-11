; Autor Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es
; Doble Grado.

;************************************************************************** 
; SBM 2020. ESTRUCTURA BÁSICA DE UN PROGRAMA EN ENSAMBLADOR 
;************************************************************************** 
; DEFINICION DEL SEGMENTO DE DATOS 
DATOS SEGMENT 
	;-- rellenar con los datos solicitados 
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
	
	; Para comprobar los ejercicios se ha mirado el registro que se quiere escribir.
	
	; Comprobado con el debugger que guarda 009B en AX.
	MOV AX, 9BH ; Ej 1.
	; Comprobado con el debugger que guarda 00B2 en BX.
	; Se aniade un 0 al B2H para que el se identifique como una constante numerica.
	MOV BL, 0B2H ; Ej 2.
	; Comprobado con el debugger que guarda 3412 en CX.
	MOV CX, 3412H ; Ej 3.
	; Comprobado con el debugger que guarda 0049 en DS. He tenido que guardar la constante en el registro general AX antes que en DS.  
	Mov AX, 01001001b ; Ej 4.
	MOV DS, AX 
	; Comprobado con el debugger que guarda 3412 en DX.
	MOV DX, CX ; Ej 5.
	; Comprobado con el debugger que guarda 0102 en BX. 01 en BH Y 02 EN BL.
	; Se hacen dos MOV porque uno solo no bastaria al ser little endian.
	; Se guardan dos constantes 01 y 02 para ver que se leen bien.
	MOV AX, 5523H ; Ej 6.
	MOV ES, AX
	MOV BYTE PTR ES:[5H], 01H
	MOV BYTE PTR ES:[6H], 02H
	MOV BH, ES:[5H]
	MOV BL, ES:[6H]
	; Comprobado con el debugger que guarda 34 en la posicion de memoria 70008.
	; Se ha ido a goto: es:8 en el panel de memoria para ver el Byte escrito.
	MOV AX, 7000H ; Ej 7.
	MOV ES, AX
	MOV ES:[8H], CH
	; Comprobado con el debugger que guarda 1234 en AX.
	; Se guardan una constante 1234 para ver que se leen bien.
	MOV WORD PTR [SI], 1234H ; Ej 8.
	MOV AX, [SI]
	; Comprobado con el debugger que guarda 4321 en BX.
	; Se guarda una constante 4321 para ver que se leen bien.
	MOV WORD PTR [BP + 10], 4321H ; Ej 9.
	MOV BX, [BP + 10]
	
	
	
	
	
	; FIN DEL PROGRAMA 
	MOV AX, 4C00H 
	INT 21H 
INICIO ENDP 
; FIN DEL SEGMENTO DE CODIGO 
CODE ENDS 
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION 
END INICIO