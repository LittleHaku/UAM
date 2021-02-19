#!/bin/bash
#
#$ -S /bin/bash
#$ -cwd
#$ -o salida.out
#$ -j y

export OMP_NUM_THREADS=$NSLOTS

export PATH=$PATH:/share/apps/tools/valgrind/bin:/share/apps/tools/gnuplot/bin

echo "Ejecutando script $1.."
source $1
