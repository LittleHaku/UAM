# Ejemplo script, para P4 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

#!/bin/bash
# Numero de procesadores.
C=8
Ninicio=1
Nfinal=$((2*C))
VtamanioInicial=30000000
VtamanioFinal=$((VtamanioInicial+120000000))
Vsalto=30000000
# Numero de veces que se hace la media de cada ejecucion.
Nmedia=5
fDATserie=pescalar_serie.dat
fDATpar=pescalar_par.dat

echo "Ejecutando pescalar.sh ..."

rm -f $fDATserie $fDATpar
touch $fDATserie $fDATpar


for ((Vtamanio = VtamanioInicial ; Vtamanio <= VtamanioFinal ; Vtamanio += Vsalto)); do
	str1="$Vtamanio	"
	str2="$Vtamanio	"
	# Version paralela se ejecuta por cada numero de hilo.
	for ((N = Ninicio ; N <= Nfinal ; N += 1)); do
		# Suma el tiempo de Nmedia ejecuciones.
		pescalarContPar=0
		for((cont = 0 ; cont < Nmedia ; cont += 1)); do
			pescalarContPar=$(echo $pescalarContPar + $(./pescalar_par4 $Vtamanio $N | grep 'Tiempo' | awk '{print $2}') | bc)
		done

		#  Divide el sumatorio entre $Nmedia. 	
		pescalarTime2=$(echo scale=6\; $pescalarContPar / $Nmedia | bc)
		str2="$str2$pescalarTime2 "
	done
	# La version serie no depende del numero de hilos, hacer solo 1 columna.
	pescalarContSerie=0
	for((cont = 0 ; cont < Nmedia ; cont += 1)); do
		pescalarContSerie=$(echo $pescalarContSerie + $(./pescalar_serie $Vtamanio | grep 'Tiempo' | awk '{print $2}') | bc)
	done
	pescalarTime1=$(echo scale=6\; $pescalarContSerie / $Nmedia | bc)
	str1="$str1$pescalarTime1"
	echo $str1 >> $fDATserie
	echo $str2 >> $fDATpar
done

