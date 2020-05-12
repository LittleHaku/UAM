#include <stdio.h>
#include <stdlib.h>


 int main(int argc, char** argv) {
    
     
    char palabra[5];
    
    printf("Escribe una letra: ");
    scanf("%c", &palabra[0]);
    
    for(int i = 1;i<5;i++){
        printf("Escribe una letra: ");
        scanf("\n%c", &palabra[i]);
    }

    
    for(int i = 0;i<5;i++){
        palabra[i]= ((palabra[i]+3-97)%25)+97;
    }
    
    printf("%s", palabra);
    
    
    
    
    return (EXIT_SUCCESS);
}


