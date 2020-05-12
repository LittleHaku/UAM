######################################################################
## File: ProgramaMips.s
## Description: Programa de prueba para el Micro Mips
## Date last modification: 2018-04-06
## Authors: Alberto Sánchez (2013-2018), Ángel de Castro (2014-2015)
## Subject: C.E. 1º grado
## Laboratory group: 
## Theory group: 
## Laboratory: 4
## Excercise: 3
######################################################################

.text # Beginning of the section with the user code
main:	addi $1, $0, 4 # Load 4 in $1
	ori $2, $0, 15 # Load x0F in $2
	andi $3, $2, 4 # Load 4 in $3
	addi $4, $3, -20 # Load -16 (0xFFFFFFF0) and save in $4
	slti $5, $0, 0x7FFF # Load 1 in $5
	slti $5, $0, -1 # Load 0 in $5
	slt $6, $4, $1 # Load 1 in $6
	
	lw $7, datoA # Load datoA in $7. Load 10
	lw $8, datoA($1) # Load datoA+4 (datoB) in $8. Load 9
	lw $9, datoC # Load datoC in $9. Load 9
	sub $10, $8, $7 # $10=$8-$7. Load -1
	
	beq $1, $4, nosalta
	beq $8, $9, salta

nosalta: add $10, $0, $0 # It shouldn't be executed (it would be 0 in $10)
salta:	addi $11, $10, 2 # Addition with negative inmeadiate data. Load 1 in $11
	add $12, $0, $1 # Load 4 in $12
	sw $12, resultado # Save $12 in result. Save 4
	lw $13, resultado # Load result in $13. Load 4
	nor $14, $1, $1 # Load 0xFFFFFFFB en $14
	and $14, $14, $0 # Load 0 in $14
	or $15, $1, $2 # Load 0x0000000F in $15
fin:	j fin	
	

.data
.align 2
datoA: .word 10
datoB: .word 9 
datoC: .word 9 
resultado: .space 4
