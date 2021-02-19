# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

# Importante! Antes de ejecutar este script, se debe ejecutar el script mult_time.sh.

#!/bin/bash

# Inicializar variables.
# Nuestra variable P es (5%7 + 4) = 9.
P=9
Ninicio=$((1 + 8*P))
Npaso=32
Nfinal=$((1 + 64*(P + 1)))

# fichero salida de datos.
fDAT=mult.dat

# gráficas.
fPNGCacheRead=mult_cache_read.png
fPNGCacheWrite=mult_cache_write.png

# borrar el fichero DAT.
rm -f $fDAT $fPNGCacheRead $fPNGCacheWrite

# generar el fichero DAT vacío.
touch $fDAT
# Nombre de ficheros temporales.
cacheNormal=cache_normal.dat
cacheTrasp=cache_trasp.dat 

echo "Running mult_misses.sh..."

# Es la variale que guarda el numero de iteracion actual.
iteracion=1
for ((N = Ninicio ; N <= Nfinal ; N += Npaso)); do
	valgrind --tool=cachegrind --cachegrind-out-file=$cacheNormal ./multiplication_slow $N
	valgrind --tool=cachegrind --cachegrind-out-file=$cacheTrasp  ./multiplication_fast $N
	
	normalMisses_r=$(cg_annotate $cacheNormal | head -n 18 | tail -n 1 | awk '{print $5}') 
	traspMisses_r=$(cg_annotate $cacheTrasp | head -n 18 | tail -n 1 | awk '{print $5}')
	normalMisses_w=$(cg_annotate $cacheNormal | head -n 18 | tail -n 1 | awk '{print $8}') 
	traspMisses_w=$(cg_annotate $cacheTrasp | head -n 18 | tail -n 1 | awk '{print $8}')

	# Del fichero generado en el apartado 1 con el script "normal_trasp_time.sh" se consiguen ambos tiempos.
	# Se selecciona la $iteracion linea.
	normalTime=$(cat "mult_time.dat" | head -n $iteracion | tail -n 1 | awk '{print $2}')
	traspTime=$(cat "mult_time.dat" | head -n $iteracion | tail -n 1 | awk '{print $3}')

	iteracion=$((iteracion + 1))

	echo "$N $normalTime $normalMisses_r $normalMisses_w $traspTime $traspMisses_r $traspMisses_w" >> $fDAT
done

# Borra los ficheros auxiliares.
rm -f $cacheNormal $cacheTrasp 

# Copia el fichero $fDAT pero sin comas, para que no haya problemas con GNUPLot.
fDATplot=mult_sin_comas.dat
cat $fDAT | tr -d ',' > $fDATplot

echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT".
gnuplot << END_GNUPLOT
set title "Normal Multiplication Misses"
set ylabel "Number of misses (units)"
set xlabel "Matrix Size (units)"
set term png
set title "Cache Reads Misses"
set output "$fPNGCacheRead"
plot "$fDATplot" using 1:3 w l title "normal", \
 	 "$fDATplot" using 1:6 w l title "trasp"
set title "Cache Write Misses"
set output "$fPNGCacheWrite"
plot "$fDATplot" using 1:4 w l title "normal", \
 	 "$fDATplot" using 1:7 w l title "trasp"
quit
END_GNUPLOT

# Borra el fichero auxiliar.
rm -f $fDATplot
