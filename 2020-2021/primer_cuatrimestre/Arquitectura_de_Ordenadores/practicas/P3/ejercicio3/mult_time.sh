# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

# Inicializar variables.
# Nuestra variable P es (5%7 + 4) = 9.
P=9
Ninicio=$((1 + 8*P))
Npaso=32
Nfinal=$((1 + 64*(P + 1)))

fDAT=mult_time.dat
fPNG=mult_time.png
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=3

# borrar el fichero DAT y el fichero PNG
rm -f $fDAT $fPNG

# generar el fichero DAT vacío
touch $fDAT

echo "Running mult_time.sh..."

for ((N = Ninicio ; N <= Nfinal ; N += Npaso)); do
	# ejecutar los programas slow y fast consecutivamente con tamaño de matriz N
	# para cada uno, filtrar la línea que contiene el tiempo y seleccionar la
	# tercera columna (el valor del tiempo). Dejar los valores en variables
	# para poder imprimirlos en la misma línea del fichero de datos.
	# suma el resultado de ejecutar $Nmedia veces el respectivo programa para luego hacer una media.
	normalCont=0
	traspCont=0
	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		normalCont=$(echo $normalCont + $(./multiplication_slow $N | grep 'time' | awk '{print $3}') | bc)
		traspCont=$(echo $traspCont + $(./multiplication_fast $N | grep 'time' | awk '{print $3}') | bc)
	done

	normalTime=$(echo scale=6\; $normalCont / $Nmedia | bc)
	traspTime=$(echo scale=6\; $traspCont / $Nmedia | bc)


	echo "$N	$normalTime	$traspTime" >> $fDAT
done


echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada.
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "Normal-Traspose Execution Time"
set ylabel "Execution time (s)"
set xlabel "Matrix Size (units)"
set key right bottom
set grid
set term png
set output "$fPNG"
plot "$fDAT" using 1:2 with lines lw 2 title "normal", \
     "$fDAT" using 1:3 with lines lw 2 title "trasp"
quit
END_GNUPLOT
