/**
 * @brief It tests the die interface.
 *
 * @file die_test.c
 * @author Marta Vaquerizo
 * @version 2.0
 * @date 11-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "die.h"
#include "die_test.h"
#include "test.h"

#define MAX_TESTS 8

/**
 * @brief Funcion principal de pruebas para el modulo Die.
 *
 * Dos modos de ejecucion:
 *   1.-Si se ejecuta sin parametros se ejecutan todas las pruebas
 *   2.-Si se ejecuta con un numero entre 1 y el numero de pruebas solo ejecuta
 *      la prueba indicada
 *
 */
int main(int argc, char** argv) {

    int test = 0;
    int all = 1;

    if (argc < 2) {
        printf("Running all test for module Die:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_die_create();
    if (all || test == 2) test2_die_create();
    if (all || test == 3) test1_die_get_last_roll();
    if (all || test == 4) test2_die_get_last_roll();
    if (all || test == 5) test1_die_roll();
    if (all || test == 6) test2_die_roll();
    if (all || test == 7) test1_die_print();
    if (all || test == 8) test2_die_print();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_die_create() {
    Die *d = NULL;
    d = die_create(1, 1, 6);
    PRINT_TEST_RESULT(d != NULL);
    die_destroy(d);
}

void test2_die_create() {
    Die *d = NULL;
    d = die_create(NO_ID, 1, 6);
    PRINT_TEST_RESULT(d == NULL);
    die_destroy(d);
}

void test1_die_get_last_roll() {
    Die *d = NULL;
    d = die_create(1, 1, 6);
    die_roll(d);
    PRINT_TEST_RESULT(die_get_last_roll(d) < 7);
    die_destroy(d);
}

void test2_die_get_last_roll() {
    Die *d = NULL;
    die_roll(d);
    PRINT_TEST_RESULT(die_get_last_roll(d) == -1);
    die_destroy(d);
}

void test1_die_roll() {
    _Bool status;
    Die *d = NULL;
    status = die_roll(d);
    PRINT_TEST_RESULT(status == ERROR);
    die_destroy(d);
}

void test2_die_roll() {
    int i, suma = 0;
    double media = 0;
    Die *d = NULL;
    d = die_create(2, 1, 6);
    for (i = 0; i < 100; i++) {
        die_roll(d);
        suma += die_get_last_roll(d);
    }

    media = suma / i;

    PRINT_TEST_RESULT(media - 3.5 < 0.0001);
    die_destroy(d);
}

void test1_die_print() {
    Die *die;
    int result;
    die = die_create(1, 1, 6);
    die_roll(die);
    result = die_print(die) == OK;
    PRINT_TEST_RESULT(result);
    die_destroy(die);
}

void test2_die_print() {
    Die *die = NULL;
    int result;
    result = die_print(die) == ERROR;
    PRINT_TEST_RESULT(result);
    die_destroy(die);
}
