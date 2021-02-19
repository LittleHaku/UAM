# Ejemplo script, para P4 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash

P=6
Cinicio=1
Cfinal=4
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=3
N=1000

#ficheros de salida
fDAT=multiplication_time_$N.dat
fsDAT=multiplication_speed_$N.dat

echo "Ejecutando tablas.sh ..."

rm -f $fDAT $fsDAT
touch $fDAT $fsDAT

#arrays para guardar los tiempos y aceleraciones para cada versión
declare -a datosSerie=()
declare -a datosParUno=()
declare -a datosParDos=()
declare -a datosParTres=()

declare -a speedUpSerie=()
declare -a speedUpParUno=()
declare -a speedUpParDos=()
declare -a speedUpParTres=()

#como la versión serie solo tiene un hilo, se ejecuta una vez
serieCont=0
serieCont=$(echo $serieCont + $(./multiplication_slow $N | grep 'time' | awk '{print $3}') | bc)
serieTime=$(echo scale=6\; $serieCont | bc)

#cálculo de los tiempos para cada versión variando el número de hilos a lanzar de 1 a 4
for ((C = Cinicio ; C <= Cfinal ; C += 1)); do
	echo "$C"
	# Suma el tiempo de Nmedia ejecuciones.
	parUnoCont=0
	parDosCont=0
	parTresCont=0

	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		parUnoCont=$(echo $parUnoCont + $(./multiplication_slow_par1 $N $C | grep 'time' | awk '{print $3}') | bc)
		parDosCont=$(echo $parDosCont + $(./multiplication_slow_par2 $N $C | grep 'time' | awk '{print $3}') | bc)
		parTresCont=$(echo $parTresCont + $(./multiplication_slow_par3 $N $C | grep 'time' | awk '{print $3}') | bc)
	done

	#  Divide el sumatorio entre $Nmedia. 	
	parUnoTime=$(echo scale=6\; $parUnoCont / $Nmedia | bc)
	parDosTime=$(echo scale=6\; $parDosCont / $Nmedia | bc)
	parTresTime=$(echo scale=6\; $parTresCont / $Nmedia | bc)

	datosSerie+=($serieTime)
	datosParUno+=($parUnoTime)
	datosParDos+=($parDosTime)
	datosParTres+=($parTresTime)

	speedUpSerie+=(1)
	#cálculo de las aceleraciones: old/new
	speed=$(echo scale=6\; $serieTime / $parUnoTime | bc)
	speedUpParUno+=($speed)
	speed=$(echo scale=6\; $serieTime / $parDosTime | bc)
	speedUpParDos+=($speed)
	speed=$(echo scale=6\; $serieTime / $parTresTime | bc)
	speedUpParTres+=($speed)
done
#se guardan los datos en los ficheros
#de tiempos de ejecución
echo "${datosSerie[@]}" >> $fDAT
echo "${datosParUno[@]}" >> $fDAT
echo "${datosParDos[@]}" >> $fDAT
echo "${datosParTres[@]}" >> $fDAT

#de aceleraciones
echo "${speedUpSerie[@]}" >> $fsDAT
echo "${speedUpParUno[@]}" >> $fsDAT
echo "${speedUpParDos[@]}" >> $fsDAT
echo "${speedUpParTres[@]}" >> $fsDAT
