#!/usr/bin/python3

import sys
import signal
TIMEOUT = 1 # seconds
signal.signal(signal.SIGALRM, input)
signal.alarm(TIMEOUT)

print("Inicio")
print("Script Python\n")


print("\n\nRecibido por ARGV:")
for line in sys.argv:
    print(line)
print("Fin de datos")


print("\n\nFin del script")
