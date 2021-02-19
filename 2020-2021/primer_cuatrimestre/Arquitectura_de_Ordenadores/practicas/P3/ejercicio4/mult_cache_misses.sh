# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

#Nuestra variable P es (5%7 + 4) = 9.
P=9
# variable que toma los valores 1024, 2048, 4096, 8192
TamCache=8192
Ninicio=$((1 + 4*P))
Npaso=32
Nfinal=$((1 + 32*(P+1)))
AsociatividadIni=1
AsociatividadFinal=4
Asociatividad=1
# Valor decimal de 8MB.
ochoMB=8388608
# Nombre de ficheros temporales
cacheNormal=cache_n.dat
cacheTrasp=cache_t.dat

# borrar el fichero DAT
while [[ $Asociatividad -le $AsociatividadFinal ]]; do
	fDATa=cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat
	rm -f $fDATa
	Asociatividad=$((Asociatividad + Asociatividad))
done

Asociatividad=1

fDATa=cache_Directa_$TamCache.dat
rm -f $fDATa

echo "Ejecutando mult_cache_misses.sh ..."

declare -a tams_matriz=()
for ((Asocitividad; Asociatividad <= AsociatividadFinal; Asociatividad += Asociatividad)); do
	for ((N = Ninicio ; N <= Nfinal ; N += Npaso)); do
		valgrind --tool=cachegrind --cachegrind-out-file=$cacheNormal --I1=$TamCache,$Asociatividad,64 --D1=$TamCache,$Asociatividad,64 --LL=$ochoMB,1,64 ./multiplication_slow $N
		valgrind --tool=cachegrind --cachegrind-out-file=$cacheTrasp --I1=$TamCache,$Asociatividad,64 --D1=$TamCache,$Asociatividad,64 --LL=$ochoMB,1,64 ./multiplication_fast $N
		normalMisses_r=$(cg_annotate $cacheNormal | head -n 18 | tail -n 1 | awk '{print $5}') 
		traspMisses_r=$(cg_annotate $cacheTrasp | head -n 18 | tail -n 1 | awk '{print $5}')
		normalMisses_w=$(cg_annotate $cacheNormal | head -n 18 | tail -n 1 | awk '{print $8}') 
		traspMisses_w=$(cg_annotate $cacheTrasp | head -n 18 | tail -n 1 | awk '{print $8}')

		num=1
		if [[ $Asociatividad -eq $num ]]; then
			fDATaux=aux_cache_Directa_$TamCache.dat
			fDAT=cache_Directa_$TamCache.dat
		else
			fDATaux=aux_cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat
			fDAT=cache_Asociativa_vias_$(($Asociatividad))_$TamCache.dat
		fi

		echo "$N	$normalMisses_r	$normalMisses_w	$traspMisses_r	$traspMisses_w" >> $fDATaux
	done
	# Copia el fichero $fDAT pero sin comas, para que no haya problemas con GNUPLot.
	cat $fDATaux | tr -d ',' > $fDAT
	rm -f $fDATaux
done

# borrar el fichero DAT
rm -f $cacheNormal $cacheTrasp


