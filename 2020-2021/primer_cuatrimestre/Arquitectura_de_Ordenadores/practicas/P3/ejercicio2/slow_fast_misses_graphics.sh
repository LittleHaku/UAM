# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

# Importante! Este script se ejecuta tras haber ejecutado el script slow_fast_misses.sh, ya que este script cambia el 
# formato en el que están escritos los datos.


#!/bin/bash

# inicializar variables
#Nuestra variable P es (5%7 + 4) = 9.
TamCacheIni=1024
TamCacheFin=8192

P=9
Ninicio=$((2000 + 512*P))
Nfinal=$((2000 + 512*(P+1)))
Npaso=64
Num_N=$((((Nfinal - Ninicio)/Npaso) +1))

#ficheros salida
#datos
fDATsr=cache_slow_read.dat
fDATsw=cache_slow_write.dat
fDATfr=cache_fast_read.dat
fDATfw=cache_fast_write.dat

#gráficas
fPNGsr=cache_lectura_slow.png
fPNGsw=cache_escritura_slow.png
fPNGfr=cache_lectura_fast.png
fPNGfw=cache_escritura_fast.png

# Borra los archivos temporales y las graficas.
rm -f $fDATsr $fDATsw $fDATfr $fDATfw $fPNGsr $fPNGsw $fPNGfr $fPNGfw
touch $fDATsr $fDATsw $fDATfr $fDATfw 

echo "Ejecutando slow_fast_misses_graphics.sh ..."

# Se procesan los cache_<tamanio>.dat y se rescriben en un fichero temporal.

for ((TamCache = TamCacheIni; TamCache <= TamCacheFin; TamCache += TamCache)); do
	#arrays donde se van a guardar los datos
	declare -a sr=()
	declare -a sw=()
	declare -a fr=()
	declare -a fw=()
	echo "$TamCache"
	declare -a tamMatriz=()

	iteracion=1
	for ((N = Ninicio; N <= Nfinal; N += Npaso)); do

		#se leen de los ficheros generados en el apartado (2), y se obtienen los datos que 
		#se necesitan para las gráficas.
		sr+=($(cat "cache_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $2}')) #se obtiene D1r del programa slow
		sw+=($(cat "cache_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $3}')) #se obtiene D1w del programa slow
		fr+=($(cat "cache_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $4}')) #se obtiene D1r del programa fast
		fw+=($(cat "cache_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $5}')) #se obtiene D1w del programa fast
		tamMatriz+=($N)
		iteracion=$((iteracion + 1))
	done

	#se guardan los datos deseados en los ficheros de salida 
	echo "$TamCache 	${sr[@]}" >> $fDATsr  	# se guardan el número de fallos de lectura (D1r) de slow de cada N
	echo "$TamCache 	${sw[@]}" >> $fDATsw  	# se guardan el número de fallos de escritura (D1w) de slow de cada N
	echo "$TamCache 	${fr[@]}" >> $fDATfr	# se guardan el número de fallos de lectura (D1r) de fast de cada N
	echo "$TamCache 	${fw[@]}" >> $fDATfw	# se guardan el número de fallos de escritura (D1w) de fast de cada N
done

# Dibujando las graficas de los archivos temporales. (Se necesitan 9 entradas en cache_<tamanio>.dat para que no de ningun warning)
echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "Slow Read Misses"
set ylabel "Number of misses (millons)"
set xlabel "Cache Size (Bytes)"
set term png
set output "$fPNGsr"
plot for [i=2:$Num_N + 1] "$fDATsr" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Fast Read Misses"
set output "$fPNGfr"
plot for [i=2:$Num_N+1] "$fDATfr" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Slow Write Misses"
set output "$fPNGsw"
plot for [i=2:$Num_N+1] "$fDATsw" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Fast Write Misses"
set output "$fPNGfw"
plot for [i=2:$Num_N+1] "$fDATfw" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
quit
END_GNUPLOT

# Borra los archivos temporales.
rm -f $fDATsr $fDATsw $fDATfr $fDATfw 
