# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

#Nuestra variable P es (5%7 + 4) = 9.
P=9
# variable que toma los valores 1024, 2048, 4096, 8192
TamCache=4096
Ninicio=$((2000 + 512*P))
Npaso=64
Nfinal=$((2000 + 512*(P + 1)))

fDAT=cache_$TamCache.dat
# Valor decimal de 8MB.
ochoMB=8388608
# Nombre de ficheros temporales
cacheSlow=cache_s.dat
cacheFast=cache_f.dat

# borrar el fichero DAT
rm -f $fDAT 

# generar el fichero DAT vacío
touch $fDAT

echo "Ejecutando slow_fast_misses.sh ..."

declare -a tams_matriz=()
for ((N = Ninicio ; N <= Nfinal ; N += Npaso)); do
	valgrind --tool=cachegrind --cachegrind-out-file=$cacheSlow --I1=$TamCache,1,64 --D1=$TamCache,1,64 --LL=$ochoMB,1,64 ./slow $N
	valgrind --tool=cachegrind --cachegrind-out-file=$cacheFast --I1=$TamCache,1,64 --D1=$TamCache,1,64 --LL=$ochoMB,1,64 ./fast $N
	slowMisses_r=$(cg_annotate $cacheSlow | head -n 18 | tail -n 1 | awk '{print $5}') 
	fastMisses_r=$(cg_annotate $cacheFast | head -n 18 | tail -n 1 | awk '{print $5}')
	slowMisses_w=$(cg_annotate $cacheSlow | head -n 18 | tail -n 1 | awk '{print $8}') 
	fastMisses_w=$(cg_annotate $cacheFast | head -n 18 | tail -n 1 | awk '{print $8}')

	echo "$N	$slowMisses_r	$slowMisses_w	$fastMisses_r	$fastMisses_w" >> $fDAT
done

# borrar el fichero DAT
rm -f $cacheSlow $cacheFast


