/*********************************************************************
 * pract3b.c
 *
 * Sistemas Basados en Microprocesador
 * 2020-2021
 * Practica 3
 *
 * author: Junco de las Heras Valenzuela. junco.heras@estudiante.uam.es.
 * group: 2301 Doble grado.
 *
 *
 *********************************************************************/
 
#include <stdio.h>
#include <stdlib.h>

#define MAXCHAR 1000

#define OK 0
#define ERR -1


int encuentraSubcadena(unsigned char *str, unsigned char *substr);


//////////////////////////////////////////////////////////////////////////
///// -------------------------- MAIN ------------------------------ /////
//////////////////////////////////////////////////////////////////////////
int main( void ){
	// Declaracion de variables
	unsigned char opcion = 'e';
	char ret = 2;
	unsigned char str[MAXCHAR] = {0};
	unsigned char substr[MAXCHAR] = {0};
	unsigned char cuenta[11] = {0}; // 10 caracteres + \0
	
	//Solicita una opcion al usuario
	printf("Elija la opcion que desea probar: \n");
	printf("a - encuentraSubcadena \n");
	printf("b - calculaSegundoDC  \n");
	scanf("%c", &opcion);
	
	//Comprueba es una opcion valida del menu
	if (opcion >= 'a' && opcion <= 'b'){
		printf("-Opcion Elegida --> %c -\n",opcion);
	}else{
		printf("-Opcion %c: incorrecta fuera de [a,b]\n Bye\n",opcion);
		return ERR;
	}
	
	//Dependiendo de la opcion elegida ejecuta una funcion ensamblador u otra
	switch(opcion){		
		// Opcion de comprobar si una substr esta en una str.
		case 'a': 
				printf("Introduzca separado por espacios la str y la substr (Ejemplo 'hola ol'): \n");
				scanf("%s %s", &str, &substr);
				ret = encuentraSubcadena(str, substr);
				if(ret == ERR)
					printf("Error, la substr no esta en la str.\n");
				else 
					printf("Substring encontrada en el indice %d.\n", ret);	
				break;
		// Opcion de calcular el digito de control en una cuenta bancaria de 10 digitos.
		case 'b': 
				printf("Introduzca el numero de cuenta, 10 digitos (Ejemplo '0438853602'): \n");
				while(1) {
					scanf("%10s", &cuenta); // Solo lee los primeros 10 caracteres.
					if(strlen(cuenta) == 10) // Pide input de nuevo si la str es < 10 caracteres.
						break;
					printf("Introduce 10 caracteres.\n");
				}
				ret = calculaSegundoDC(cuenta);
				printf("Digito de control: %d.\n", ret);	
				break;
		
		default:
			break;			
	}
	
	return OK;
}