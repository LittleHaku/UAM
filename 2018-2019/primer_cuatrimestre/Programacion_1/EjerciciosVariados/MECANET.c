#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <termios.h>

int mygetch();

#define NUM_LETRAS 50
#define RECORD 204

int main() {
    int letra, i, k, j, cont=0;
    char c, str1[NUM_LETRAS+1] = {0};
    srand(time(NULL));



    for (i = 0; i < 2000000; i++) {
        for (j = 0; j < NUM_LETRAS; j++) {
            letra = (rand() % 26) + 'a';
            str1[j] = letra;
        }
        printf("\n\n\n\n\n\n\n\n\n\n\n\n\n%s\n\n\n\n\n\n\n\n\n\n\n\n\n", str1);
        for (j = 0; j < NUM_LETRAS; j++) {
            c = mygetch();
            if((char)c=='/'){
                printf("PULSACIONES: %d", cont);
                return 0;
            }
            printf("%c", c);

            if (c != str1[j]) {
                for (k = 0; k < 2000; k++) {
                    printf("ERROR");
                    
                }
                break;
            }
            cont++;
        }
    }

    return 0;
}

int mygetch() {

    struct termios oldt, newt;
    int ch;

    tcgetattr(STDIN_FILENO, &oldt);

    newt = oldt;
    newt.c_lflag &= ~(ICANON | ECHO);

    tcsetattr(STDIN_FILENO, TCSANOW, &newt);

    ch = getchar();

    tcsetattr(STDIN_FILENO, TCSANOW, &oldt);

    return ch;
}