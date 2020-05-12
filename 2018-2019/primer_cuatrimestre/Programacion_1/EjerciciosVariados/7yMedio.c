#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define AS 1
#define DOS 2
#define TRES 3
#define CUATRO 4
#define CINCO 5
#define SEIS 6
#define SIETE 7
#define SOTA 0.5
#define CABALLO 0.5
#define REY 0.5

#define MAX_JUGADORES 4

int main(void){
    srand(time(0));
    
    /*Mazo*/
    double cartas[40] = {AS, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, SOTA, CABALLO, REY, AS, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, SOTA, CABALLO, REY,AS, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, SOTA, CABALLO, REY,AS, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, SOTA, CABALLO, REY};
    
    /*Variables*/
    int i, j, aleatorio1, aleatorio2, jugador=0, PrimeraCarta=0, numJugadores;
    double temp, puntuacion[MAX_JUGADORES]={0}, maxPuntuacion;
    char respuesta='s';
    
    /*Barajar*/
    for(i=0;i<100;i++){
        aleatorio1=rand()%40;
        aleatorio2=rand()%40;
        temp=cartas[aleatorio1];
        cartas[aleatorio1]=cartas[aleatorio2];
        cartas[aleatorio2]=temp;
    }
    /*Input num de jugadores*/
    do{
    printf("Cuantos jugadores (maximo %d)? ", MAX_JUGADORES);
    scanf("%d", &numJugadores);
    }while(numJugadores<1||numJugadores>MAX_JUGADORES);
    
    /*Juego*/
    for(jugador=0;jugador<numJugadores;jugador++){
        printf("Jugador %d ==========\n", jugador+1);
    
        do{
            puntuacion[jugador]+=cartas[PrimeraCarta%40];PrimeraCarta++;
        printf("Tu puntuacion parcial es %g", puntuacion[jugador]);
        if(puntuacion[jugador]>7.5){printf("\n¡¡Te has pasado!!¡Te quedas con %g puntos!\n", puntuacion[jugador]);break;}
        printf("\nQuieres carta (s/n)? ");
        scanf(" %c", &respuesta);
        if(respuesta=='n'){printf("Te has plantado!\n¡Te quedas con %g puntos!\n", puntuacion[jugador]);break;}
        }while(1);
    }
    /*Buscar mejor puntuacion*/
    for(i=0;i<numJugadores;i++){
        if(puntuacion[i]>maxPuntuacion&&puntuacion[i]<=7.5){maxPuntuacion=puntuacion[i];}
    }
    printf("\nLa mejor puntuacion es %g", maxPuntuacion);
    
    /*Mostrar ganador*/
    if(!maxPuntuacion){printf("\nTodos los jugadores se han pasado de 7.5");}
    else{
        printf("\nEl/los jugador(es) ganador(es) es/son:\n");
        for(i=0;i<numJugadores;i++){
            if(puntuacion[i]==maxPuntuacion){printf("Jugador %d", i+1);}
        }
    }
    return 0;
}
