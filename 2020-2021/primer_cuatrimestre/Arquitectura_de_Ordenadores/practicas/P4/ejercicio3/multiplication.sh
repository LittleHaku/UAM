# Ejemplo script, para P4 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

P=6
C=4
Ninicio=1
Nfinal=$((248*P))
Nsalto=64
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=3

#ficheros de salida
#datos
fDAT=multiplication_time.dat
fpDAT=multiplication_speed.dat

#gráficas
fPNG=multiplication_time.png
fpPNG=multiplication_speed.png

echo "Ejecutando multiplication.sh ..."

rm -f $fDAT $fpDAT $fPNG $fpPNG
touch $fDAT $fpDAT

for ((N = Ninicio ; N <= Nfinal ; N += Nsalto)); do

	# Suma el tiempo de Nmedia ejecuciones.
	serieCont=0
	parCont=0
	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		serieCont=$(echo $serieCont + $(./multiplication_slow $N | grep 'time' | awk '{print $3}') | bc)
		parCont=$(echo $parCont + $(./multiplication_slow_par3 $N $C | grep 'time' | awk '{print $3}') | bc)
	done

	#  Divide el sumatorio entre $Nmedia. 	
	serieTime=$(echo scale=6\; $serieCont / $Nmedia | bc)
	parTime=$(echo scale=6\; $parCont / $Nmedia | bc)

	#aceleracion
	speed=$(echo scale=6\; $serieTime / $parTime | bc)

	echo "$N $serieTime $parTime" >> $fDAT
	echo "$N $speed" >> $fpDAT
done

echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada.
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "Execution Time"
set ylabel "Execution time (s)"
set xlabel "Matrix Size (units)"
set key right bottom
set grid
set term png
set output "$fPNG"
plot "$fDAT" using 1:2 with lines lw 2 title "serie", \
     "$fDAT" using 1:3 with lines lw 2 title "parallel"
set title "Speed-up"
set ylabel "Speed (s)"
set xlabel "Matrix Size (units)"
set term png
set output "$fpPNG"
plot "$fpDAT" using 1:2 with lines lw 2 title "serie vs parallel"
quit
END_GNUPLOT

