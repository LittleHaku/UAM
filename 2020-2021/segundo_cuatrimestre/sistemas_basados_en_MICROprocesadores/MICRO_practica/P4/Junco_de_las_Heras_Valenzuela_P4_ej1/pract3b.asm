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

; DEFINICION DEL SEGMENTO DE CODIGO COMO PUBLICO
_TEXT SEGMENT BYTE PUBLIC 'CODE'
    ASSUME CS:_TEXT

	; COMIENZO DEL PROCEDIMIENTO PRINCIPAL
	_MAIN PROC 
		;COMIENZO DEL PROGRAMA 
		; PUBLIC _funcion		
		; .asm para que compile el makefile en el ej 1.
		
	_MAIN ENDP 

	;_funcion proc far
	
	
	;	ret
	; _funcion endp

	
_TEXT ENDS 
END