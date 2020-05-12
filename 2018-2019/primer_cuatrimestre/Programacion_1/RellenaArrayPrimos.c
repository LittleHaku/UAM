#include <stdio.h>


int main(void){
    
    //RELLENA UN ARRAY CON LOS 200 PRIMEROS PRIMOS
    int i, contador=0, j, paso=0;
    
    int primos[200]={0};
    
    
    for(i=2;i<1320;i++){
        for(j=0;j<=contador;j++){
            if(primos[j]){
                if((i%primos[j])==0){break;}
                
            }
            if(j==contador){
                    printf("%d ", i);
                    primos[contador]=i;
                    paso=1;
            }
        }
        if(paso){contador++;paso=0;}
    }
    
    
return 0;
}


