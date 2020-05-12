#include <stdio.h>
#include <stdlib.h>

#include <string.h>
int main() {

    char frase[256];
    printf("Escriba una frase para que contemos cuantas letras tiene: ");
    scanf("%s", frase);

    int v[256];
    for(int i=0;i<255;i++){
        v[i]=0;
    }
    
    for(int i=0;i<strlen(frase);i++){
        if(v[(int)frase[i]]!=0){continue;}
            for(int j=i;j<strlen(frase);j++){
                if(frase[i]==frase[j]){
                    printf("%c", frase[i]);
                    v[(int)frase[i]]+=1;
                }
            }        
    
    }

    for(int i=65;i<123;i++){
        if(v[i]!=0){
            printf("La letra %c ha sido repetida %d veces\n", i, v[i]);
        }
    }

  
    printf("%s", frase);
    
    return 0;
}

