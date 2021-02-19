# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

# Importante! Este script se ejecuta tras haber ejecutado el script mult_cache_misses.sh, ya que este script cambia el 
# formato en el que están escritos los datos.


#!/bin/bash

# inicializar variables
#Nuestra variable P es (5%7 + 4) = 9.
TamCacheIni=1024
TamCacheFin=8192

P=9
Ninicio=$((1 + 8*P))
Npaso=32
Nfinal=$((1 + 32*(P+1)))
Num_N=$((((Nfinal - Ninicio)/Npaso) +1))
Asociatividad=4
#ficheros salida
#datos
fDATnr=cache_Asociativa_vias_$(($Asociatividad))_normal_read.dat
fDATnw=cache_Asociativa_vias_$(($Asociatividad))_normal_write.dat
fDATtr=cache_Asociativa_vias_$(($Asociatividad))_trasp_read.dat
fDATtw=cache_Asociativa_vias_$(($Asociatividad))_trasp_write.dat

#gráficas
fPNGnr=cache_Asociativa_vias_$(($Asociatividad))_lectura_normal.png
fPNGnw=cache_Asociativa_vias_$(($Asociatividad))_escritura_normal.png
fPNGtr=cache_Asociativa_vias_$(($Asociatividad))_lectura_trasp.png
fPNGtw=cache_Asociativa_vias_$(($Asociatividad))_escritura_trasp.png

# Borra los archivos temporales y las graficas.
rm -f $fDATnr $fDATnw $fDATtr $fDATtw $fPNGnr $fPNGnw $fPNGtr $fPNGtw
touch $fDATnr $fDATnw $fDATtr $fDATtw 

echo "Ejecutando mult_cache_misses_graphics.sh ..."

# Se procesan los cache_<tamanio>.dat y se rescriben en un fichero temporal.

for ((TamCache = TamCacheIni; TamCache <= TamCacheFin; TamCache += TamCache)); do
	#arrays donde se van a guardar los datos
	declare -a nr=()
	declare -a nw=()
	declare -a trr=()
	declare -a tw=()
	echo "$TamCache"
	declare -a tamMatriz=()
	
	iteracion=1
	for ((N = Ninicio; N <= Nfinal; N += Npaso)); do
		#se leen de los ficheros generados en el apartado (2), y se obtienen los datos que 
		#se necesitan para las gráficas.
		nr+=($(cat "cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $2}')) #se obtiene D1r del programa normal
		nw+=($(cat "cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $3}')) #se obtiene D1w del programa normal
		trr+=($(cat "cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $4}')) #se obtiene D1r del programa trasp
		tw+=($(cat "cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat" | head -n $iteracion | tail -n 1 | awk '{print $5}')) #se obtiene D1w del programa trasp
		tamMatriz+=($N)
		iteracion=$((iteracion + 1))
	done

	#se guardan los datos deseados en los ficheros de salida 
	echo "$TamCache 	${nr[@]}" >> $fDATnr  	# se guardan el número de fallos de lectura (D1r) de normal de cada N
	echo "$TamCache 	${nw[@]}" >> $fDATnw  	# se guardan el número de fallos de escritura (D1w) de normal de cada N
	echo "$TamCache 	${trr[@]}" >> $fDATtr	# se guardan el número de fallos de lectura (D1r) de trasp de cada N
	echo "$TamCache 	${tw[@]}" >> $fDATtw	# se guardan el número de fallos de escritura (D1w) de trasp de cada N


done

# Dibujando las graficas de los archivos temporales. (Se necesitan 9 entradas en cache_<tamanio>.dat para que no de ningun warning)
echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "Normal Read Misses"
set ylabel "Number of misses (units)"
set xlabel "Cache Size (Bytes)"
set term png
set output "$fPNGnr"
plot for [i=2:$Num_N + 1] "$fDATnr" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Trasp Read Misses"
set output "$fPNGtr"
plot for [i=2:$Num_N+1] "$fDATtr" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Normal Write Misses"
set output "$fPNGnw"
plot for [i=2:$Num_N+1] "$fDATnw" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
set title "Trasp Write Misses"
set output "$fPNGtw"
plot for [i=2:$Num_N+1] "$fDATtw" using 1:i w l title sprintf("N= %s", word("${tamMatriz[@]}", i-1))
quit
END_GNUPLOT

# Borra los archivos temporales.
rm -f $fDATnr $fDATnw $fDATtr $fDATtw 
