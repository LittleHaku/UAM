.text

main: 
	#i = $s0 = 0
	addi $s0, $0, 0

	#$s1 = N
	lw $s1, N
	#N<<2
	sll $s1, $s1, 2

	#$s2 = ($s0 < $s1)
for:	slt $s2, $s0, $s1
	beq $s2, $0, endFor
	
	#$s3 = A(i)
	lw $s3, A($s0)
	
	#$s4 = B(i)
	lw $s4, B($s0)
	
	#B(i)<<2
	sll $s4, $s4, 2
	
	#res = A(i)+B(i)<<2
	add $s5, $s3, $s4
	
	#lo guarda
	sw $s5, C($s0)
	
	#++i
	add $s0, $s0, 4
	
	j for
	
endFor:
		
whileTrue: j whileTrue

.data
A:  2,  2, 4,  6, 5,  6, 7,  8,  9, 10
B: -1, -5, 4, 10, 1, -2, 5, 10, -10, 0
#C=-2,-18,20, 46, 9, -2,27, 48, -31,10 
C: .space 40
N: 10