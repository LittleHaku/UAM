.text

main:
	#Carga en registros x e y
	lw $s0, x
	lw $s1, y
	
	#Push x
	sw $s0, ($sp)
	addi $sp, $sp, -4
	
	#Push y
	sw $s1, ($sp)
	addi $sp, $sp, -4
	
	jal calculaSumaMult
	
	#Pop $s2 = retorno
	addi $sp, $sp, 4 
	lw $s2, ($sp)
	
	#Guarda el resultado en r
	sw $s2, r
	

#Mata al proceso
whileTrue: j whileTrue
	
	
calculaSumaMult:
	#Pop a = y = $t0
	addi $sp, $sp, 4 
	lw $t0, ($sp)
	
	#Pop b = x = $t1
	addi $sp, $sp, 4 
	lw $t1, ($sp)
	
	#a + b
	add $t2, $t0, $t1
	#(a+b)<<1 == (a+b)*2
	sll $t2, $t2, 1
	
	#Push (a+b)*2
	sw $t2, ($sp)
	addi $sp, $sp, -4
		
	jr $ra
	
	
	

.data
x: 10 #s0
y: 4 #s1
r: 0 #s2