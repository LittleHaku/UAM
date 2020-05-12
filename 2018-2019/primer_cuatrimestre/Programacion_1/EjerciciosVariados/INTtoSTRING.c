#include <stdio.h>
#include <stdlib.h>
#include <string.h>



char *tochar(int num){
    int contador=0, num2=num, i;
    while(num2){
        contador++;
        num2/=10;        
    }
    char* resultado = calloc(contador+1, sizeof(char*));
    resultado[contador]='\0';
    for(i=0;i<contador;i++){
        resultado[contador-i-1]= num%10 + 48;
        num/=10;
    }
    return resultado;
}

int main(void) {
    char final[200]={0};

    
    strcat(final, "(");
    strcat(final, tochar(33));
    strcat(final, " ");
    strcat(final, tochar(44));
    strcat(final, ")");

    
    printf("\n%s", final);
    
    return 0;
}

