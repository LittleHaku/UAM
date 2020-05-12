/**
 * @brief It tests object module
 *
 * @file object_test.c
 * @author Leandro Garcia & Junco de las Heras Valenzuela
 * @version 3.0
 * @date 22-11-19
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "object.h"
#include "object_test.h"
#include "test.h"

#define MAX_TESTS 40

/**
 * @brief Funcion principal de pruebas para el modulo Space.
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
        printf("Running all test for module Object:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_object_create();
    if (all || test == 2) test2_object_create();
    if (all || test == 3) test1_object_get_id();
    if (all || test == 4) test2_object_get_id();
    if (all || test == 5) test1_object_set_name();
    if (all || test == 6) test2_object_set_name();
    if (all || test == 7) test1_object_get_name();
    if (all || test == 8) test2_object_get_name();
    if (all || test == 9) test1_object_set_description();
    if (all || test == 10) test2_object_set_description();
    if (all || test == 11) test1_object_get_description();
    if (all || test == 12) test2_object_get_description();
    if (all || test == 13) test1_object_print();
    if (all || test == 14) test2_object_print();
    if (all || test == 15) test1_object_set_movable();
    if (all || test == 16) test2_object_set_movable();
    if (all || test == 17) test1_object_is_movable();
    if (all || test == 18) test2_object_is_movable();
    if (all || test == 19) test1_object_set_moved();
    if (all || test == 20) test2_object_set_moved();
    if (all || test == 21) test1_object_get_moved();
    if (all || test == 22) test2_object_get_moved();
    if (all || test == 23) test1_object_set_hidden();
    if (all || test == 24) test2_object_get_hidden();
    if (all || test == 25) test1_object_set_open();
    if (all || test == 26) test2_object_set_open();
    if (all || test == 27) test1_object_get_open();
    if (all || test == 28) test2_object_get_open();
    if (all || test == 29) test1_object_set_illuminate();
    if (all || test == 30) test2_object_set_illuminate();
    if (all || test == 31) test1_object_can_illuminate();
    if (all || test == 32) test2_object_can_illuminate();
    if (all || test == 33) test1_object_set_turnedon();
    if (all || test == 34) test2_object_set_turnedon();
    if (all || test == 35) test1_object_is_turnedon();
    if (all || test == 36) test2_object_is_turnedon();
    if (all || test == 37) test1_object_set_full_desc();
    if (all || test == 38) test2_object_set_full_desc();
    if (all || test == 39) test1_object_get_full_desc();
    if (all || test == 40) test2_object_get_full_desc();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_object_create() {
    Object *o;
    o = object_create(1);
    PRINT_TEST_RESULT(o != NULL);
    object_destroy(o);
}

void test2_object_create() {
    int result;
    result = object_create(NO_ID) == NULL;
    PRINT_TEST_RESULT(result);
}

void test1_object_get_id() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_get_id(o) == 1;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_get_id() {
    Object *o = NULL;
    int result;
    result = object_get_id(o) == NO_ID;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_set_name() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_set_name(o, "Obj") == OK;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_set_name() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_set_name(o, NULL) == ERROR;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_get_name() {
    Object *o;
    int result;
    o = object_create(1);
    object_set_name(o, "Obj");
    result = strcmp(object_get_name(o), "Obj") == 0;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_get_name() {
    Object *o;
    int result;
    o = object_create(1);
    result = !strcmp(object_get_name(o), "");
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_set_description() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_set_description(o, "Obj") == OK;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_set_description() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_set_description(o, NULL) == ERROR;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_get_description() {
    Object *o;
    int result;
    o = object_create(1);
    object_set_description(o, "Obj");
    result = strcmp(object_get_description(o), "Obj") == 0;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_get_description() {
    Object *o;
    int result;
    o = object_create(1);
    result = !strcmp(object_get_description(o), "");
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_print() {
    Object *o;
    int result;
    o = object_create(1);
    result = object_print(o) == OK;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test2_object_print() {
    Object *o = NULL;
    int result;
    result = object_print(o) == ERROR;
    PRINT_TEST_RESULT(result);
    object_destroy(o);
}

void test1_object_set_movable() {
    Object *o = NULL;
    o = object_create(1);


    PRINT_TEST_RESULT(object_set_movable(o, TRUE) == OK);
    object_destroy(o);
}

void test2_object_set_movable() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_movable(NULL, TRUE) == ERROR);
    object_destroy(o);
}

void test1_object_is_movable() {
    Object *o = NULL;
    o = object_create(1);
    object_set_movable(o, TRUE);

    PRINT_TEST_RESULT(object_is_movable(o) == TRUE);
    object_destroy(o);
}

void test2_object_is_movable() {
    Object *o = NULL;
    o = object_create(1);
    object_set_movable(o, TRUE);

    PRINT_TEST_RESULT(object_is_movable(NULL) == FALSE);
    object_destroy(o);
}

void test1_object_set_moved() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_moved(o, TRUE) == OK);
    object_destroy(o);
}

void test2_object_set_moved() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_moved(NULL, TRUE) == ERROR);
    object_destroy(o);
}

void test1_object_get_moved() {
    Object *o = NULL;
    o = object_create(1);
    object_set_moved(o, TRUE);

    PRINT_TEST_RESULT(object_get_moved(o) == TRUE);
    object_destroy(o);
}

void test2_object_get_moved() {
    Object *o = NULL;
    o = object_create(1);
    object_set_moved(o, TRUE);

    PRINT_TEST_RESULT(object_get_moved(NULL) == FALSE);
    object_destroy(o);
}

void test1_object_set_hidden() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_open(o, 1) == OK);
    object_destroy(o);
}

void test2_object_get_hidden() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_open(NULL, 1) == ERROR);
    object_destroy(o);
}

void test1_object_set_open() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_open(o, 1) == OK);
    object_destroy(o);
}

void test2_object_set_open() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_open(NULL, 1) == ERROR);
    object_destroy(o);
}

void test1_object_get_open() {
    Object *o = NULL;
    o = object_create(1);
    object_set_open(o, 1);

    PRINT_TEST_RESULT(object_get_open(o) == 1);
    object_destroy(o);
}

void test2_object_get_open() {
    Object *o = NULL;
    o = object_create(1);
    object_set_open(o, 1);

    PRINT_TEST_RESULT(object_get_open(NULL) == NO_ID);
    object_destroy(o);
}

void test1_object_set_illuminate() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_illuminate(o, TRUE) == OK);
    object_destroy(o);
}

void test2_object_set_illuminate() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_illuminate(NULL, TRUE) == ERROR);
    object_destroy(o);
}

void test1_object_can_illuminate() {
    Object *o = NULL;
    o = object_create(1);
    object_set_illuminate(o, TRUE);

    PRINT_TEST_RESULT(object_can_illuminate(o) == TRUE);
    object_destroy(o);
}

void test2_object_can_illuminate() {
    Object *o = NULL;
    o = object_create(1);
    object_set_illuminate(o, TRUE);

    PRINT_TEST_RESULT(object_can_illuminate(NULL) == FALSE);
    object_destroy(o);
}

void test1_object_set_turnedon() {
    Object *o = NULL;
    o = object_create(1);
    object_set_illuminate(o, TRUE);
    object_set_turnedon(o, TRUE);

    PRINT_TEST_RESULT(object_set_turnedon(o, TRUE) == OK);
    object_destroy(o);
}

void test2_object_set_turnedon() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_turnedon(NULL, TRUE) == ERROR);
    object_destroy(o);
}

void test1_object_is_turnedon() {
    Object *o = NULL;
    o = object_create(1);
    object_set_illuminate(o, TRUE);
    object_set_turnedon(o, TRUE);

    PRINT_TEST_RESULT(object_is_turnedon(o) == TRUE);
    object_destroy(o);
}

void test2_object_is_turnedon() {
    Object *o = NULL;
    o = object_create(1);
    object_set_turnedon(o, TRUE);

    PRINT_TEST_RESULT(object_is_turnedon(NULL) == FALSE);
    object_destroy(o);
}

void test1_object_set_full_desc() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_full_desc(o, "increible") == OK);
    object_destroy(o);
}

void test2_object_set_full_desc() {
    Object *o = NULL;
    o = object_create(1);

    PRINT_TEST_RESULT(object_set_full_desc(NULL, "increible") == ERROR);
    object_destroy(o);
}

void test1_object_get_full_desc() {
    Object *o = NULL;
    o = object_create(1);
    object_set_full_desc(o, "increible");

    PRINT_TEST_RESULT(strcmp(object_get_full_desc(o), "increible") == 0);
    object_destroy(o);
}

void test2_object_get_full_desc() {
    Object *o = NULL;
    o = object_create(1);
    object_set_full_desc(o, "");

    PRINT_TEST_RESULT(strcmp(object_get_full_desc(o), "") == 0);
    object_destroy(o);
}
