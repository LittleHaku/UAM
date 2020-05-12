#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define longitud 2000
#define MAXfactorial 1000

int v[5][longitud];
int contador = 0;

//Muestra en pantalla el array que le pasan quitando los ceros de la izq
void mostrar(int num1){
    int FirstDigit=0;
    contador++;
    printf("\n %d: ", contador);
    for(int i=longitud-1;i>=0;i--){
        if(!FirstDigit && !v[num1][i]){continue;}
        else if(!FirstDigit && v[num1][i]){FirstDigit=1;}
        printf("%d", v[num1][i]);
    }   
}
//Asegura que en el vector que le pasan solo tiene nums de 1 digito
void check(int num1){
    for(int i=0;i<longitud-1;i++){
        if(v[num1][i]>9){
            v[num1][i]-=10;
            v[num1][i+1]++;
            check(num1);
        }
    }
}
int main(void){
    //Inicializar las variables
    int i, j;
    for(i=0;i<5;i++){
       for(j=0;j<longitud;j++){
           v[i][j]=0;
       }        
    }
    v[4][0]=1;
 
    
    /*
     * Factorial hasta 1000 max, llega a 818
     * v0 es el num del factorial
     * v0,1 es el primer digito del num a calcular
     * v0,2 es el segundo
     * v0,3 el tercero... 
     * v1 es la primera fila de la multiplicacion del primer digito
     * v2 es la segunda fila...
     * v3 es el tercero
     * v4 es el resultado final
     */
    for(j=0;j<MAXfactorial;j++){
        v[0][0]++;
        check(0);
        for(i=0;i<longitud;i++){
            v[1][i] = v[4][i] * v[0][0];
            v[2][i+1] = v[4][i] * v[0][1];
            v[3][i+2] = v[4][i] * v[0][2];
        }
        check(1);
        check(2);
        check(3);
        for(i=0;i<longitud;i++){
         v[4][i] = v[1][i]+v[2][i]+v[3][i];   
        }
        check(4);
        mostrar(4);     
    }
   
    return 0;
}

