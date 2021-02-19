# Ejemplo script, para P4 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

P=6
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=3

fDAT=edgeDetector_time_O3.dat
fsDAT=edgeDetector_speed_O3.dat

rm -f $fDAT $fsDAT
touch $fDAT $fsDAT

declare -a imagenes=("SD.jpg" "HD.jpg" "FHD.jpg" "4k.jpg" "8k.jpg")
declare -a datosSerie=()
declare -a datosPar=()

declare -a speedUpSerie=()
declare -a speedUpPar=()

echo "Ejecutando tablas_edgeDetector_O3.sh ..."
for imagen in "${imagenes[@]}"; do
	
	echo "$imagen"
	serieCont=0
	parCont=0

	# Suma el tiempo de Nmedia ejecuciones.
	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		serieCont=$(echo $serieCont + $(./edgeDetector $imagen | grep 'Tiempo' | awk '{print $2}') | bc)
		parCont=$(echo $parCont + $(./edgeDetector_par $imagen | grep 'Tiempo' | awk '{print $2}') | bc)
	done

	#  Divide el sumatorio entre $Nmedia. 	
	parTime=$(echo scale=6\; $parCont / $Nmedia | bc)
	serieTime=$(echo scale=6\; $serieCont / $Nmedia  | bc)

	datosSerie+=($serieTime)
	datosPar+=($parTime)

	speedUpSerie+=(1)
	#old/new
	speed=$(echo scale=6\; $serieTime / $parTime | bc)
	speedUpPar+=($speed)
done

echo "${datosSerie[@]}" >> $fDAT
echo "${datosPar[@]}" >> $fDAT

echo "${speedUpSerie[@]}" >> $fsDAT
echo "${speedUpPar[@]}" >> $fsDAT