#!/bin/bash
#
#$ -cwd
#$ -j y
#$ -S /bin/bash
#$ -cwd
#$ -o salida.out
#$ -pe openmp 2
#
export OMP_NUM_THREADS=$NSLOTS
export PATH=$PATH:/share/apps/tools/valgrind/bin:/share/apps/tools/gnuplot/bin

echo "Ejecutando el script $1..."
source $1
