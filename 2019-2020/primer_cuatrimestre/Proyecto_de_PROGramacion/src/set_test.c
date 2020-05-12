/**
 * @brief It tests set module
 *
 * @file set_test.c
 * @author Leandro Garcia
 * @version 2.0
 * @date 19-01-2016
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "set.h"
#include "set_test.h"
#include "test.h"

#define MAX_TESTS 14

/**
 * @brief Funcion principal de pruebas para el modulo Set.
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
        printf("Running all test for module set:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_set_create();
    if (all || test == 2) test2_set_create();
    if (all || test == 3) test1_set_get_array();
    if (all || test == 4) test2_set_get_array();
    if (all || test == 5) test1_set_get_size();
    if (all || test == 6) test2_set_get_size();
    if (all || test == 7) test1_set_add();
    if (all || test == 8) test2_set_add();
    if (all || test == 9) test1_set_del();
    if (all || test == 10) test2_set_del();
    if (all || test == 11) test1_set_has_id();
    if (all || test == 12) test2_set_has_id();
    if (all || test == 13) test1_set_print();
    if (all || test == 14) test2_set_print();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_set_create() {
    Set *set;
    set = set_create();
    PRINT_TEST_RESULT(set != NULL);
    set_destroy(set);
}

void test2_set_create() {
    Set *set = NULL;
    set = set_create();
    PRINT_TEST_RESULT(set != NULL);
    set_destroy(set);
}

void test1_set_get_array() {
    Set *set;
    int result;
    set = set_create();
    result = set_get_array(set) != NULL;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_get_array() {
    int result;
    result = set_get_array(NULL) == NULL;
    PRINT_TEST_RESULT(result);
}

void test1_set_get_size() {
    Set *set;
    int result;
    set = set_create();
    result = set_get_size(set) == 0;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_get_size() {
    Set *set = NULL;
    int result;
    result = set_get_size(set) == -1;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test1_set_add() {
    Set *set;
    int result;
    set = set_create();
    result = set_add(set, 1) == OK;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_add() {
    Set *set;
    int result;
    set = set_create();
    set_add(set, 1);
    result = set_add(set, 1) == ERROR;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test1_set_del() {
    Set *set;
    int result;
    set = set_create();
    set_add(set, 1);
    result = set_del(set, 1) == OK;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_del() {
    Set *set;
    int result;
    set = set_create();
    result = set_del(set, 1) == ERROR;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test1_set_has_id() {
    Set *set;
    int result;
    set = set_create();
    result = 1;
    if (set_has_id(set, 1) != FALSE) {
        result = 0;
    }
    set_add(set, 1);
    if (set_has_id(set, 1) != TRUE) {
        result = 0;
    }
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_has_id() {
    int result;
    result = set_has_id(NULL, 1) == FALSE;
    PRINT_TEST_RESULT(result);
}

void test1_set_print() {
    Set *set;
    int result;
    set = set_create(1);
    result = set_print(set) == OK;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}

void test2_set_print() {
    Set *set = NULL;
    int result;
    result = set_print(set) == ERROR;
    PRINT_TEST_RESULT(result);
    set_destroy(set);
}
