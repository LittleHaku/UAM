#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_CADENA 32
#define INTENTOS 6
#define TAM_VOCABULARIO 8


int main(void){
    srand(time(0));
    
    /*Declaracion de variables*/
    int i=0, flag=0, j=0, fallos=0, numintro=0, aciertoLetra=0;
    char palabra[MAX_CADENA]={0}, intento[MAX_CADENA]={0}, introducidas[MAX_CADENA]={0}, intentoLetra, intentoPalabra[MAX_CADENA]={0}, temp;
    
    /*Declaracion del diccionario*/
    const char VOCABULARIO[TAM_VOCABULARIO][MAX_CADENA] = {"vehiculo", "edificio", "camiseta", "boligrafo", "murcielago", "dromedario", "melocoton", "mermelada"};
    int elegida=rand()%TAM_VOCABULARIO;
    
    /*Inicializacion de variables dependientes*/
    strcpy(palabra, VOCABULARIO[elegida]);
    for(i=0;i<strlen(palabra);i++){
        intento[i]='_';
    }
    
    
    do{
        /*Mostrar los datos*/
        printf("\n\n\n\nTus letras introducidas son: ");
        for(i=0;i<MAX_CADENA;i++){
            if(!introducidas[i]){break;}
            printf("%c", introducidas[i]);
        }   
        printf("\nTus letras encontradas son: %s", intento);
        
        /*Acoplamiento de la nueva letra, repeticiones de letra y fallos*/
        printf("\nPalabra de %d letras. %d fallos de %d. Introduce una letra: ", (int)strlen(palabra), fallos, INTENTOS);
        scanf(" %c", &temp);
        for(i=0;i<MAX_CADENA;i++){
            if(introducidas[i]==temp){break;}
            if(!introducidas[i]){introducidas[numintro]=temp;numintro++;break;}
        }
        
        for(i=0;i<strlen(palabra);i++){
            flag=0;
            for(j=0;j<MAX_CADENA;j++){
                if(!introducidas[j]){break;}
                if(introducidas[j]==palabra[i]&&intento[i]!=palabra[i]){flag=1;}
            }
            if(flag){intento[i]=palabra[i];aciertoLetra=1;}
        }
        /*Mostrar antes de pedir palabra*/
        printf("\nTus letras introducidas son: ");
        for(i=0;i<MAX_CADENA;i++){
            if(!introducidas[i]){break;}
            printf("%c", introducidas[i]);
        }   
        /*Mostrar y pedir palabra*/
        printf("\nTus letras encontradas son: %s", intento);
        printf("\n¿Cuál es la palabra a adivinar? ");
        scanf("%s", intentoPalabra);
        if(strcmp(palabra, intentoPalabra)){printf("Esa no es la palabra. Sigue intentándolo.");}
        if(strcmp(palabra, intento)&&strcmp(palabra, intentoPalabra)&&!aciertoLetra){fallos++;}
        aciertoLetra=0;
        
    }while(strcmp(palabra, intento)&&strcmp(palabra, intentoPalabra)&&fallos<INTENTOS);
    
    /*Final*/
    if(fallos>=INTENTOS){printf("\n\n¡¡Ahorcado!! Lo siento, has perdido. La palabra era: %s", palabra);}
    else{printf("\n¡¡Enhorabuena, has acertado la palabra %s!!", palabra);}

    return 0;
}
