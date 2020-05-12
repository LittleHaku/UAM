#include <stdio.h>
#include <stdlib.h>

int main(void) {
    //Declaracion de variables
    int n1, n2, i=2, menor;
    
    //Input numeros positivos
    do{
    printf("Escribe un numero: ");
    scanf("%d", &n1);
    }while(n1<=0);
    do{
    printf("Escribe otro numero: ");
    scanf("%d", &n2);
    }while(n2<=0);
    
    //Detecta el menor numero
    if(n1<n2){menor=n1;}
    else{menor=n2;}
    
    //Busca si hay algun mcd
    while(i<=menor){
        if(n1%i == 0 && n2%i == 0){break;}
        i++;
    }
    
    //Muestra en pantalla el resultado
    if(i==menor+1){printf("Los numeros %d y %d son primos relativos", n1, n2);}
    else{printf("Los numeros %d y %d no son primos relativos, pues %d divide a ambos", n1, n2, i);}
    
    return 0;
}