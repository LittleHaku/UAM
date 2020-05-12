/**
 * @brief It tests the game_management interface
 *
 * @file game_management_test.c
 * @author Marta Vaquerizo
 * @version 1.0
 * @date 02-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "game_management.h"
#include "game.h"
#include "space.h"
#include "test.h"
#include "game_management_test.h"

#define MAX_TESTS 4

/**
 * @brief Funcion principal de pruebas para el modulo Game Management.
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

    if (argc < 5) {
        printf("Running all test for module Game Management:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 && test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_game_management_load();
    if (all || test == 2) test2_game_management_load();
    if (all || test == 3) test1_game_management_save();
    if (all || test == 4) test2_game_management_save();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_game_management_load() {
    Game *g = NULL;
    int result;
    result = game_management_load(g, "TestFiles/prueba1.dat") == ERROR;
    PRINT_TEST_RESULT(result);
    game_destroy(g);
}

void test2_game_management_load() {
    Game *g = NULL;
    g = game_create();
    game_management_load(g, "TestFiles/prueba2.dat");
    PRINT_TEST_RESULT(game_get_num_spaces(g) == 2);
    game_destroy(g);
}

void test1_game_management_save() {
    Game *g = NULL;
    int result;
    game_management_load(g, "TestFiles/prueba1.dat");
    result = game_management_save(g, "TestFiles/prueba3.dat") == ERROR;
    PRINT_TEST_RESULT(result);
    game_destroy(g);
}

void test2_game_management_save() {
    Game *g = NULL;
    g = game_create();
    game_management_load(g, "TestFiles/prueba2.dat");
    game_management_save(g, "TestFiles/prueba4.dat");
    PRINT_TEST_RESULT(game_get_num_spaces(g) == 2);
    game_destroy(g);
}
