# Ejemplo script, para P3 arq 2019-2020
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es

# Importante! Este script se ejecuta tras haber ejecutado el script pescalar.sh


#!/bin/bash

C=8
Ninicio=1
Nfinal=$((2*C))

tamSize=0

#ficheros salida
#datos
fDATgr1=pescalar_serie.dat
fDATgr2=pescalar_par.dat
#gráficas
fPNGgr1=pescalar_serie_graphic.png
fPNGgr2=pescalar_par_graphic.png

# Borra los archivos temporales y las graficas.
rm -f $fPNGgr1 $fPNGgr2

echo "Ejecutando pescalar_graphics.sh ..."

# Se guardan los tamanios de los vectores que se han guardado en tamMatriz
declare -a tamMatriz=()
for ((N = Ninicio ; N <= Nfinal ; N += 1)); do
	tamMatriz+=($N)
	tamSize=$((tamSize+1))
done

echo "Generating plot..."
# llamar a gnuplot para generar el gráfico y pasarle directamente por la entrada
# estándar el script que está entre "<< END_GNUPLOT" y "END_GNUPLOT"
gnuplot << END_GNUPLOT
set title "pescalar serie time"
set ylabel "tiempo (s)"
set xlabel "Tamanio vector (unidades)"
set term png
set output "$fPNGgr1"
plot "$fDATgr1" using 1:2 w l title "# hilos = 1"
set title "pescalar parallel time"
set output "$fPNGgr2"
plot for [i=2:$tamSize + 1] "$fDATgr2" using 1:i w l title sprintf("# hilos = %s", word("${tamMatriz[@]}", i-1))
quit
END_GNUPLOT

