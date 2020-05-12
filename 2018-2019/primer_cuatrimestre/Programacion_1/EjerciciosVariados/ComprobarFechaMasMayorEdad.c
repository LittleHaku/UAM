#include <stdio.h>
#include <stdlib.h>

int main() {
    int day, mes, year, i, fecha[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    printf("Ingrese el dia mes año: ");
    scanf("%d %d %d", &day, &mes, &year);
    if(year%4==0&&((year%100==0 && year%400==0) || year%100!=0)){ //si es bisiesto
        fecha[1]=29;
    }
    if(mes<1||mes>12||year<1900||day<1||day>31){
        printf("Fecha no valida");
        return 0;
    }
    if(day>fecha[mes-1]){
        printf("Fecha no valida");
        return 0;
    }
    if(year<2000||(year==2000&&mes<9)||(year==2000&&mes&&9&&day<=25)){
        printf("Eres mayor de edad");
    }
    else{
        printf("No eres mayor de edad");
    }
  
    return 0;
}




