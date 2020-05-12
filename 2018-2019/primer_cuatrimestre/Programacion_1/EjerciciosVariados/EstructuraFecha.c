#include <stdio.h>
#include <stdlib.h>

int diaMes[12]={31,29,31,30,31,31,30,31,30,31,30,31};

typedef struct{
    int dia;
    int mes;
    int anyo;
}Fecha;

void incrementaDia(Fecha *x){
    if(x->dia < diaMes[x->mes -1]){
    x->dia++;
    }
    else{
        x->dia = 1;
        if(x->mes <12){
        x->mes++;
        }
        else{
            x->mes=1;
            x->anyo++;
        }
      }
}

int main() {
    Fecha t;
    do{
    printf("Dia Mes Anyo: ");
    scanf("%d %d %d", &t.dia, &t.mes, &t.anyo);
    }while(t.dia<1||t.dia>diaMes[t.mes-1]||t.mes<1||t.mes>12||t.anyo<1);
    if(t.anyo%4==0&&(t.anyo%100!=0||t.anyo%400==0)){
        diaMes[1]=29;
    }
    incrementaDia(&t);
    printf("Dia: %d Mes: %d Anyo: %d", t.dia, t.mes, t.anyo);

    return 0;
}
