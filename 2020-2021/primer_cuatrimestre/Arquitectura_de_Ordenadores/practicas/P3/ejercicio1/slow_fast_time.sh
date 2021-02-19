# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

# Nuestra variable P es (5%7 + 4) = 9.
P=9
Ninicio=$((1 + 8*P)) #$((2000 + 512*P))
Npaso=64
Nfinal=$((1 + 256*(P + 1)))
fDAT=time_slow_fast.dat
fPNG=time_slow_fast.png
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=3


# borrar el fichero DAT y el fichero PNG
rm -f $fDAT $fPNG

# generar el fichero DAT vacío
touch $fDAT

echo "Ejecutando slow_fast_time.sh ..."

# Se ejecutara 512/64 + 1 = 9 veces
for ((N = Ninicio ; N <= Nfinal ; N += Npaso)); do

	# ejecutar los programas slow y fast consecutivamente con tamaño de matriz N
	# para cada uno, filtrar la línea que contiene el tiempo y seleccionar la
	# tercera columna (el valor del tiempo). Dejar los valores en variables
	# para poder imprimirlos en la misma línea del fichero de datos.
	# suma el resultado de ejecutar $Nmedia veces el respectivo programa para luego hacer una media.
	slowCont=0
	fastCont=0
	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		slowCont=$(echo $slowCont + $(./slow $N | grep 'time' | awk '{print $3}') | bc)
		fastCont=$(echo $fastCont + $(./fast $N | grep 'time' | awk '{print $3}') | bc)
	done

	#  Divide el sumatorio entre $Nmedia 	
	slowTime=$(echo scale=6\; $slowCont / $Nmedia | bc)
	fastTime=$(echo scale=6\; $fastCont / $Nmedia | bc)

	echo "$N	$slowTime	$fastTime" >> $fDAT
done


echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada.
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "Slow-Fast Execution Time"
set ylabel "Execution time (s)"
set xlabel "Matrix Size (units)"
set key right bottom
set grid
set term png
set output "$fPNG"
plot "$fDAT" using 1:2 with lines lw 2 title "slow", \
     "$fDAT" using 1:3 with lines lw 2 title "fast"
quit
END_GNUPLOT
