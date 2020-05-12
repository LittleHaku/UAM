/**
 * @brief It tests link module
 *
 * @file link_test.c
 * @author Leandro Garcia
 * @version 2.0
 * @date 19-01-2016
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "link.h"
#include "link_test.h"
#include "test.h"

#define MAX_TESTS 22

/**
 * @brief Funcion principal de pruebas para el modulo Link.
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
        printf("Running all test for module link:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_link_create();
    if (all || test == 2) test2_link_create();
    if (all || test == 3) test1_link_get_id();
    if (all || test == 4) test2_link_get_id();
    if (all || test == 5) test1_link_set_name();
    if (all || test == 6) test2_link_set_name();
    if (all || test == 7) test1_link_get_name();
    if (all || test == 8) test2_link_get_name();
    if (all || test == 9) test1_link_set_space1();
    if (all || test == 10) test2_link_set_space1();
    if (all || test == 11) test1_link_get_space1();
    if (all || test == 12) test2_link_get_space1();
    if (all || test == 13) test1_link_set_space2();
    if (all || test == 14) test2_link_set_space2();
    if (all || test == 15) test1_link_get_space2();
    if (all || test == 16) test2_link_get_space2();
    if (all || test == 17) test1_link_set_open();
    if (all || test == 18) test2_link_set_open();
    if (all || test == 19) test1_link_is_open();
    if (all || test == 20) test2_link_is_open();
    if (all || test == 21) test1_link_print();
    if (all || test == 22) test2_link_print();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_link_create() {
    Link *lk;
    lk = link_create(1);
    PRINT_TEST_RESULT(lk != NULL);
    link_destroy(lk);
}

void test2_link_create() {
    int result;
    result = link_create(NO_ID) == NULL;
    PRINT_TEST_RESULT(result);
}

void test1_link_get_id() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_get_id(lk) == 1;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_get_id() {
    Link *lk = NULL;
    int result;
    result = link_get_id(lk) == NO_ID;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_set_name() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_set_name(lk, "Link") == OK;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_set_name() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_set_name(lk, NULL) == ERROR;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_get_name() {
    Link *lk;
    int result;
    lk = link_create(1);
    link_set_name(lk, "Link");
    result = strcmp(link_get_name(lk), "Link") == 0;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_get_name() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = !strcmp(link_get_name(lk), "");
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_set_space1() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_set_space1(lk, 1) == OK;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_set_space1() {
    Link *lk = NULL;
    int result;
    result = link_set_space1(lk, NO_ID) == ERROR;
    PRINT_TEST_RESULT(result);
}

void test1_link_get_space1() {
    Link *lk;
    int result;
    lk = link_create(1);
    link_set_space1(lk, 1);
    result = link_get_space1(lk) == 1;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_get_space1() {
    Link *lk = NULL;
    int result;
    result = link_get_space1(lk) == NO_ID;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_set_space2() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_set_space2(lk, 1) == OK;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_set_space2() {
    Link *lk = NULL;
    int result;
    result = link_set_space2(lk, NO_ID) == ERROR;
    PRINT_TEST_RESULT(result);
}

void test1_link_get_space2() {
    Link *lk;
    int result;
    lk = link_create(1);
    link_set_space2(lk, 1);
    result = link_get_space2(lk) == 1;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_get_space2() {
    Link *lk = NULL;
    int result;
    result = link_get_space2(lk) == NO_ID;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_set_open() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_set_open(lk, TRUE) == OK;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_set_open() {
    Link *lk = NULL;
    int result;
    result = link_set_open(lk, TRUE) == ERROR;
    PRINT_TEST_RESULT(result);
}

void test1_link_is_open() {
    Link *lk;
    int result;
    lk = link_create(1);
    link_set_open(lk, FALSE);
    result = link_is_open(lk) == FALSE;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_is_open() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_is_open(lk) == TRUE;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test1_link_print() {
    Link *lk;
    int result;
    lk = link_create(1);
    result = link_print(lk) == OK;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}

void test2_link_print() {
    Link *lk = NULL;
    int result;
    result = link_print(lk) == ERROR;
    PRINT_TEST_RESULT(result);
    link_destroy(lk);
}
