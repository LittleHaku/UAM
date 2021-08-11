;**************************************************************************
; MBS 2021. LABORATORY ASSIGNMENT 2
; Author: Junco de las Heras Valenzuela, junco.heras@estudiante.uam.es
; Group: 2301, Doble Grado.
; Task: 2
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
	; VECTOR DEFINITION
	vector1 DB 1,2,2,4
	vector2 DB 4,2,5,1
	vector3 DB 3,2,4,1
	errorNumDif DB "Outside the set: [1,4]", 13, 10, '$'
	correctOutput DB "Correct",13,10,'$'
DATA ENDS
;**************************************************************************
; STACK SEGMENT DEFINITION
STACKSEG SEGMENT STACK "STACK"
	DB 40H DUP (0) ; initialization example, 64 bytes initialize to 0
STACKSEG ENDS
;**************************************************************************
; EXTRA SEGMENT DEFINITION
EXTRA SEGMENT
	RESULT DW 0,0 ; initialization example. 2 WORDS (4 BYTES)
EXTRA ENDS
;**************************************************************************
; CODE SEGMENT DEFINITION
CODE SEGMENT
	ASSUME CS: CODE, DS: DATA, ES: EXTRA, SS: STACKSEG
	; BEGINNING OF MAIN PROCEDURE
	BEGIN PROC
		; INITIALIZE THE SEGMENT REGISTER WITH ITS VALUE
		MOV AX, DATA
		MOV DS, AX
		MOV AX, STACKSEG
		MOV SS, AX
		MOV AX, EXTRA
		MOV ES, AX
		MOV SP, 64 ; LOAD A STACK POINTER WITH THE HIGHEST VALUE
		; END OF INITIALIZATIONS
		; BEGINNING OF THE PROGRAMME
		; -- fill with the requested instructions.
		
		MOV BX, OFFSET vector1
		CALL IN_OUT ; Procesa el vector1.
		
		MOV BX, OFFSET vector2
		CALL IN_OUT ; Procesa el vector2.
		
		MOV BX, OFFSET vector3
		CALL IN_OUT ; Procesa el vector3.
		
		; END OF THE PROGRAMME
		MOV AX, 4C00H
		INT 21H
	BEGIN ENDP
	; SPACE FOR SUBROUTINES
	; -- write your subroutines here
	
	; Escribe correctOutput si los numeros del vector
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
		
		VALID: ; Imprime correctOutput.
		MOV AH, 09H
		MOV DX, OFFSET correctOutput
		INT 21H
		
		RET
	
	ENDP IN_OUT
	
	
	; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN