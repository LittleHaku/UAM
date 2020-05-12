/*SUDOKU 3X3*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define DIMENSION 3*3

int TB[DIMENSION][DIMENSION] = {
    
{0,0,0,0,8,0,0,0,2},
{0,0,7,2,0,9,8,1,4},
{0,2,8,7,4,5,3,0,0},
{6,7,3,9,0,0,4,2,0},
{8,9,4,3,0,2,6,0,1},
{2,1,5,0,0,0,9,3,0},
{0,0,2,8,9,3,0,4,0},
{0,8,0,5,0,6,2,0,0},
{0,0,0,0,2,0,0,0,0}

}, clavado[DIMENSION][DIMENSION]={0}, base[DIMENSION][DIMENSION]={
{1,1,1,2,2,2,3,3,3},  
{1,1,1,2,2,2,3,3,3},  
{1,1,1,2,2,2,3,3,3},  
{4,4,4,5,5,5,6,6,6},    
{4,4,4,5,5,5,6,6,6},    
{4,4,4,5,5,5,6,6,6},    
{7,7,7,8,8,8,9,9,9},  
{7,7,7,8,8,8,9,9,9},  
{7,7,7,8,8,8,9,9,9}  
};

void mostrarTablero(){
    printf("\nTABLERO: ");
    int i, j;
    for(i=0;i<DIMENSION;i++){
        printf("\nROW %d: |", i);
        for(j=0;j<DIMENSION;j++){
            printf("%d|", TB[i][j]);
        }
    }
}


int check(int x, int y){
    int i, j;
    for(i=0;i<DIMENSION;i++){
        if((TB[x][y]==TB[x][i]) &&(y!=i)){
            return 0;
        }
    }
    for(i=0;i<DIMENSION;i++){
        if((TB[x][y]==TB[i][y]) &&(x!=i)){
            return 0;
        }
    }
    for(i=0;i<DIMENSION;i++){
        for(j=0;j<DIMENSION;j++){
            if((base[x][y]==base[i][j])&&(x!=i || y!=j)&&(TB[x][y]==TB[i][j])){
                return 0;
            }
        }
    }
    return 1;
}
void checkTotal(){
    int i, j;
    for(i=0;i<DIMENSION;i++){
        for(j=0;j<DIMENSION;j++){
            if(TB[i][j]){
                if(!check(i,j)){
                    printf("IMPOSIBLE %d %d", i, j);return;
                }
            }
        }
    }
    printf("\nComprobacion correcta");
}
void back(int x, int y, int z){
    if(x==0&&y==-1&&z==-1){printf("\nNO HAY SOLUCION");return;}
    if(x==8&&y==9){printf("\nFINALIZADO CORRECTAMENTE");return;}
    
    if(y==-1&&z==-1){x--;y=8;}
    else if(y==9&&z==1){x++;y=0;}

    if(x<0||x>8||y<0||y>8){printf("SEGFAULT");exit(0);}
    if(clavado[x][y]){
        if(z==1){back(x,y+1,1);}
        else{back(x,y-1,-1);}    
    }
    else{ 
        if(TB[x][y]<9){
            TB[x][y]++;
            if(check(x,y)){
                back(x,y+1,1);
            }
            else{
                back(x,y,1);
            }
        }
        else{ 
            TB[x][y]=0;
            back(x,y-1,-1);
        }    
    }  
}

int main(){
    int i=2,j=2,x=1,y=1;
    
    for(i=0;i<DIMENSION;i++){
        for(j=0;j<DIMENSION;j++){
            if(TB[i][j]){
                clavado[i][j]++;
            }
        }
    }
    
    checkTotal();
    mostrarTablero();

    back(0,0,1);
    
    checkTotal();
    mostrarTablero();

    return 0;
}
