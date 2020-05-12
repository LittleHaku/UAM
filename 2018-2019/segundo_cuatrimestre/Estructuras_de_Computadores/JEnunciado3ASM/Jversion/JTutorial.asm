.text

main: add $t1, $t2, $1
	lw $t0, a
	lw $t1, b
	
	slt $t2, $t0, $t1
	beq $t2, $0, else
	sw $t1, c
	j finIf
	
else:	sw $t0, c

finIf: j finIf




.data 
a: 20
b: 10
c: 0