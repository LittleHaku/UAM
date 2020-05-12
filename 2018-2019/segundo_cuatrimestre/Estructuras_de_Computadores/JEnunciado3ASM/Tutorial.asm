######################################################################
## File: Tutorial.asm
## Description: Example of an assembler program
## Date: 2016-03-07
## Authors: Sofía Martínez (2019),Alberto Sánchez (2012, 2016), Ángel de Castro (2014)
## Subject: E.C. 1º grado
## Laboratory group:
## Theory group:
## Task: 3
## Exercise: 0
######################################################################

#############################
# Equivalent in C code
# if (a < b) 
#	c = b;
# else
#	c = a;
# while (1);
#############################

.text # Start of user code section
main:	lw $t0, a  # We save in the register $t0 the variable a. Standard format is lw $rt, imm($rs), but it also possible some variations like lw $rt, label
	lw $t1, b # We save om the register $t1 the variable b
	slt $t2, $t0, $t1 # $t2 will be 1 if a<b
	beq $t2, $zero, falso # If $t2 is 0, then a<b is not true, then we go to else
	addi $t3, $t1, 0 # Main branch of if, $t3 is the c variable
	j finCondicion # Jump to the end of loop, and else is not executed
falso:	addi $t3, $t0, 0 # Branch of else

finCondicion: sw $t3, c # We save in memory the value of register $t3

bucle: j bucle
	
	
.data  # Start of user data section
a: 20 #a Variable, is a word. It also possible to write a: .word 5, but is reduntant because by default it is a word.
b: 10  # b Variable
c: 0  # c Variable


