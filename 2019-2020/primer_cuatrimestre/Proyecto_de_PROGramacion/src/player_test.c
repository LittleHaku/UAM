/**
 * @brief It declares the tests for the player module
 *
 * @file player_test.c
 * @author Junco de las Heras Valenzuela
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "player.h"
#include "player_test.h"
#include "test.h"

#define MAX_TESTS 22

/**
 * @brief Funcion principal de pruebas para el modulo Player.
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
        printf("Running all test for module Player:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }



    if (all || test == 1) test1_player_create();
    if (all || test == 2) test2_player_create();
    if (all || test == 3) test1_player_set_id();
    if (all || test == 4) test2_player_set_id();
    if (all || test == 5) test1_player_get_id();
    if (all || test == 6) test2_player_get_id();
    if (all || test == 7) test1_player_set_name();
    if (all || test == 8) test2_player_set_name();
    if (all || test == 9) test1_player_get_name();
    if (all || test == 10) test2_player_get_name();
    if (all || test == 11) test1_player_set_space();
    if (all || test == 12) test2_player_set_space();
    if (all || test == 13) test1_player_get_space();
    if (all || test == 14) test2_player_get_space();
    if (all || test == 15) test1_player_insert_object();
    if (all || test == 16) test2_player_insert_object();
    if (all || test == 17) test1_player_delete_object();
    if (all || test == 18) test2_player_delete_object();
    if (all || test == 19) test1_player_get_objects();
    if (all || test == 20) test2_player_get_objects();
    if (all || test == 21) test1_player_has_object();
    if (all || test == 22) test2_player_has_object();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_player_create() {
    Player * player = NULL;
    player = player_create(1, 4);
    PRINT_TEST_RESULT(player != NULL);
    player_destroy(player);
}

void test2_player_create() {
    Player * player = NULL;
    player = player_create(1, -2);
    PRINT_TEST_RESULT(player == NULL);
    player_destroy(player);
}

void test1_player_set_id() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_id(player, 2) == OK);
    player_destroy(player);
}

void test2_player_set_id() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_id(player, NO_ID) == ERROR);
    player_destroy(player);
}

void test1_player_get_id() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_id(player, 3);
    PRINT_TEST_RESULT(player_get_id(player) == 3);
    player_destroy(player);
}

void test2_player_get_id() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_id(player, 33);
    PRINT_TEST_RESULT(player_get_id(player) == 33);
    player_destroy(player);
}

void test1_player_set_name() {
    char name[5] = "hola\0";
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_name(player, name) == OK);
    player_destroy(player);
}

void test2_player_set_name() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_name(player, NULL) == ERROR);
    player_destroy(player);
}

void test1_player_get_name() {
    char name[5] = "hola\0";
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_name(player, name);
    PRINT_TEST_RESULT(strcmp(player_get_name(player), name) == 0);
    player_destroy(player);
}

void test2_player_get_name() {
    char name[5] = "heey\0";
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_name(player, name);
    PRINT_TEST_RESULT(strcmp(player_get_name(player), name) == 0);
    player_destroy(player);
}

void test1_player_set_space() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_space(player, 3) == OK);
    player_destroy(player);
}

void test2_player_set_space() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_set_space(player, NO_ID) == ERROR);
    player_destroy(player);
}

void test1_player_get_space() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_space(player, 3);
    PRINT_TEST_RESULT(player_get_space(player) == 3);
    player_destroy(player);
}

void test2_player_get_space() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_set_space(player, 33);
    PRINT_TEST_RESULT(player_get_space(player) == 33);
    player_destroy(player);
}

void test1_player_insert_object() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_insert_object(player, 3) == OK);
    player_destroy(player);
}

void test2_player_insert_object() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_insert_object(player, NO_ID) == ERROR);
    player_destroy(player);
}

void test1_player_delete_object() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_insert_object(player, 3);
    PRINT_TEST_RESULT(player_delete_object(player, 3) == OK);
    player_destroy(player);
}

void test2_player_delete_object() {
    Player * player = NULL;
    player = player_create(1, 3);

    PRINT_TEST_RESULT(player_delete_object(player, 3) == ERROR);
    player_destroy(player);
}

void test1_player_get_objects() {
    Id* id;
    Player * player = NULL;
    player = player_create(1, 3);
    id = player_get_objects(player);
    PRINT_TEST_RESULT(id != NULL);
    player_destroy(player);
}

void test2_player_get_objects() {
    Id* id;
    Player * player = NULL;
    player = player_create(1, 3);
    id = player_get_objects(NULL);
    PRINT_TEST_RESULT(id == NULL);
    player_destroy(player);
}

void test1_player_has_object() {
    Player * player = NULL;
    player = player_create(1, 3);
    player_insert_object(player, 2);
    PRINT_TEST_RESULT(player_has_object(player, 2) == TRUE);
    player_destroy(player);
}

void test2_player_has_object() {
    Player * player = NULL;
    player = player_create(1, 3);
    PRINT_TEST_RESULT(player_has_object(player, 2) == FALSE);
    player_destroy(player);
}
