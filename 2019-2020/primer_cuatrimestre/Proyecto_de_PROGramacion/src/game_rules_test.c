/**
 * @brief It test the game_rules module
 *
 * @file game_rules_test.c
 * @author Junco de las Heras Valenzuela
 * @version 1.0
 * @date 16-12-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "game.h"
#include "game_rules_test.h"
#include "test.h"
#include "game_rules.h"

#define MAX_TESTS 6

/**
 * @brief Funcion principal de pruebas para el modulo Game rules.
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
        printf("Running all test for module Game rules:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\n", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_game_rules_ini();
    if (all || test == 2) test2_game_rules_ini();
    if (all || test == 3) test3_game_rules_ini();
    if (all || test == 4) test1_game_rules_random_action();
    if (all || test == 5) test2_game_rules_random_action();
    if (all || test == 6) test3_game_rules_random_action();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_game_rules_ini() {
    grules *gr = NULL;
    Game *g = NULL;
    g = game_create();
    gr = game_rules_ini(NULL);

    PRINT_TEST_RESULT(gr == NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}

void test2_game_rules_ini() {
    grules *gr = NULL;
    Game *g = NULL;
    g = game_create();
    gr = game_rules_ini(g);

    PRINT_TEST_RESULT(gr != NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}

void test3_game_rules_ini() {
    grules *gr = NULL;
    Game *g = NULL;
    Object *o = NULL;
    g = game_create();
    o = object_create(5);
    game_add_object(g, o);
    gr = game_rules_ini(g);

    PRINT_TEST_RESULT(gr != NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}

void test1_game_rules_random_action() {
    grules *gr = NULL;
    Game *g = NULL;
    g = game_create();
    gr = game_rules_ini(g);
    game_rules_random_action(NULL);

    PRINT_TEST_RESULT(gr != NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}

void test2_game_rules_random_action() {
    grules *gr = NULL;
    Game *g = NULL;
    g = game_create();
    gr = game_rules_ini(g);
    game_rules_random_action(gr);

    PRINT_TEST_RESULT(gr != NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}

void test3_game_rules_random_action() {
    grules *gr = NULL;
    Game *g = NULL;
    g = game_create();
    gr = game_rules_ini(g);
    game_rules_random_action(gr);
    game_rules_random_action(gr);
    game_rules_random_action(gr);
    game_rules_random_action(gr);
    game_rules_random_action(gr);
    game_rules_random_action(gr);
    game_rules_random_action(gr);

    PRINT_TEST_RESULT(gr != NULL);
    game_destroy(g);
    game_rules_destroy(gr);
}
