/**
 * @brief It tests space module
 *
 * @file space_test.c
 * @author Leandro Garcia
 * @version 2.0
 * @date 22-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "space.h"
#include "space_test.h"
#include "test.h"

#define MAX_TESTS 60

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
        printf("Running all test for module Space:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\t", test);
            exit(EXIT_SUCCESS);
        }
    }


    if (all || test == 1) test1_space_create();
    if (all || test == 2) test2_space_create();
    if (all || test == 3) test1_space_set_name();
    if (all || test == 4) test2_space_set_name();
    if (all || test == 5) test1_space_set_north();
    if (all || test == 6) test2_space_set_north();
    if (all || test == 7) test1_space_set_south();
    if (all || test == 8) test2_space_set_south();
    if (all || test == 9) test1_space_set_east();
    if (all || test == 10) test2_space_set_east();
    if (all || test == 11) test1_space_set_west();
    if (all || test == 12) test2_space_set_west();
    if (all || test == 13) test1_space_get_name();
    if (all || test == 14) test2_space_get_name();
    if (all || test == 15) test1_space_get_north();
    if (all || test == 16) test2_space_get_north();
    if (all || test == 17) test1_space_get_south();
    if (all || test == 18) test2_space_get_south();
    if (all || test == 19) test1_space_get_east();
    if (all || test == 20) test2_space_get_east();
    if (all || test == 21) test1_space_get_west();
    if (all || test == 22) test2_space_get_west();
    if (all || test == 23) test1_space_get_id();
    if (all || test == 24) test2_space_get_id();
    if (all || test == 25) test1_space_add_object();
    if (all || test == 26) test2_space_add_object();
    if (all || test == 27) test1_space_del_object();
    if (all || test == 28) test2_space_del_object();
    if (all || test == 29) test1_space_get_objects();
    if (all || test == 30) test2_space_get_objects();
    if (all || test == 31) test1_space_get_nelems();
    if (all || test == 32) test2_space_get_nelems();
    if (all || test == 33) test1_space_set_gdesc();
    if (all || test == 34) test2_space_set_gdesc();
    if (all || test == 35) test1_space_get_gdesc();
    if (all || test == 36) test2_space_get_gdesc();
    if (all || test == 37) test1_space_has_object();
    if (all || test == 38) test2_space_has_object();
    if (all || test == 39) test1_space_set_description();
    if (all || test == 40) test2_space_set_description();
    if (all || test == 41) test1_space_get_description();
    if (all || test == 42) test2_space_get_description();
    if (all || test == 43) test1_space_print();
    if (all || test == 44) test2_space_print();
    if (all || test == 45) test1_space_set_up();
    if (all || test == 46) test2_space_set_up();
    if (all || test == 47) test1_space_set_down();
    if (all || test == 48) test2_space_set_down();
    if (all || test == 49) test1_space_get_up();
    if (all || test == 50) test2_space_get_up();
    if (all || test == 51) test1_space_get_down();
    if (all || test == 52) test2_space_get_down();
    if (all || test == 53) test1_space_set_full_desc();
    if (all || test == 54) test2_space_set_full_desc();
    if (all || test == 55) test1_space_get_full_desc();
    if (all || test == 56) test2_space_get_full_desc();
    if (all || test == 57) test1_space_set_light();
    if (all || test == 58) test2_space_set_light();
    if (all || test == 59) test1_space_is_lit();
    if (all || test == 60) test2_space_is_lit();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_space_create() {
    int result = 0;
    Space *s;
    s = space_create(5);
    if (s != NULL) {
        result = 1;
    }
    PRINT_TEST_RESULT(result);
    space_destroy(s);
}

void test2_space_create() {
    Space *s;
    s = space_create(4);
    PRINT_TEST_RESULT(space_get_id(s) == 4);
    space_destroy(s);
}

void test1_space_set_name() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_name(s, "hola") == OK);
    space_destroy(s);
}

void test2_space_set_name() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_name(s, "hola") == ERROR);
}

void test1_space_set_north() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_north(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_north() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_north(s, 4) == ERROR);
}

void test1_space_set_south() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_south(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_south() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_south(s, 4) == ERROR);
}

void test1_space_set_east() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_east(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_east() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_east(s, 4) == ERROR);
}

void test1_space_set_west() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_west(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_west() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_west(s, 4) == ERROR);
}

void test1_space_add_object() {
    Space *s;
    s = space_create(1);
    PRINT_TEST_RESULT(space_add_object(s, 5) == OK);
    space_destroy(s);
}

void test2_space_add_object() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_add_object(s, 5) == ERROR);
}

void test1_space_get_name() {
    Space *s;
    s = space_create(1);
    space_set_name(s, "adios");
    PRINT_TEST_RESULT(strcmp(space_get_name(s), "adios") == 0);
    space_destroy(s);
}

void test2_space_get_name() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_name(s) == NULL);
}

void test1_space_get_objects() {
    PRINT_TEST_RESULT(space_get_objects(NULL) == NULL);
}

void test2_space_get_objects() {
    Space *s;
    s = space_create(1);
    space_add_object(s, 5);
    PRINT_TEST_RESULT(space_get_objects(s) != NULL);
    space_destroy(s);
}

void test1_space_get_north() {
    Space *s;
    s = space_create(5);
    space_set_north(s, 4);
    PRINT_TEST_RESULT(space_get_north(s) == 4);
    space_destroy(s);
}

void test2_space_get_north() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_north(s) == NO_ID);
}

void test1_space_get_south() {
    Space *s;
    s = space_create(5);
    space_set_south(s, 2);
    PRINT_TEST_RESULT(space_get_south(s) == 2);
    space_destroy(s);
}

void test2_space_get_south() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_south(s) == NO_ID);
}

void test1_space_get_east() {
    Space *s;
    s = space_create(5);
    space_set_east(s, 1);
    PRINT_TEST_RESULT(space_get_east(s) == 1);
    space_destroy(s);
}

void test2_space_get_east() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_east(s) == NO_ID);
}

void test1_space_get_west() {
    Space *s;
    s = space_create(5);
    space_set_west(s, 6);
    PRINT_TEST_RESULT(space_get_west(s) == 6);
    space_destroy(s);
}

void test2_space_get_west() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_west(s) == NO_ID);
}

void test1_space_get_id() {
    Space *s;
    s = space_create(25);
    PRINT_TEST_RESULT(space_get_id(s) == 25);
    space_destroy(s);
}

void test2_space_get_id() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_id(s) == NO_ID);
}

void test1_space_del_object() {
    Space *s;
    s = space_create(5);
    space_add_object(s, 6);
    PRINT_TEST_RESULT(space_del_object(s, 6) == OK);
    space_destroy(s);
}

void test2_space_del_object() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_del_object(s, 6) == ERROR);
}

void test1_space_set_gdesc() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_gdesc(s, "hola", 2) == OK);
    space_destroy(s);
}

void test2_space_set_gdesc() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_gdesc(s, "hola", 2) == ERROR);
}

void test1_space_get_gdesc() {
    Space *s;
    s = space_create(1);
    space_set_gdesc(s, "adios", 1);
    PRINT_TEST_RESULT(strcmp(space_get_gdesc(s, 1), "adios") == 0);
    space_destroy(s);
}

void test2_space_get_gdesc() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_gdesc(s, 2) == NULL);

}

void test1_space_has_object() {
    Space *s;
    int result = 1;
    s = space_create(5);
    if (space_has_object(s, 5) != FALSE) {
        result = 0;
    }
    space_add_object(s, 5);
    if (space_has_object(s, 5) != TRUE) {
        result = 0;
    }
    PRINT_TEST_RESULT(result);
    space_destroy(s);
}

void test2_space_has_object() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_has_object(s, 5) == FALSE);
}

void test1_space_set_description() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_description(s, "hola") == OK);
    space_destroy(s);
}

void test2_space_set_description() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_description(s, "hola") == ERROR);
}

void test1_space_get_description() {
    Space *s;
    s = space_create(1);
    space_set_description(s, "adios");
    PRINT_TEST_RESULT(strcmp(space_get_description(s), "adios") == 0);
    space_destroy(s);
}

void test2_space_get_description() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_description(s) == NULL);
}

void test1_space_print() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_print(s) == OK);
    space_destroy(s);
}

void test2_space_print() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_print(s) == ERROR);
}

void test1_space_get_nelems() {
    Space *s;
    s = space_create(5);
    space_add_object(s, 5);
    PRINT_TEST_RESULT(space_get_nelems(s) == 1);
    space_destroy(s);
}

void test2_space_get_nelems() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_nelems(s) == -1);
}

void test1_space_set_up() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_up(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_up() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_up(s, 4) == ERROR);
}

void test1_space_set_down() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_down(s, 4) == OK);
    space_destroy(s);
}

void test2_space_set_down() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_down(s, 4) == ERROR);
}

void test1_space_get_up() {
    Space *s;
    s = space_create(5);
    space_set_up(s, 4);
    PRINT_TEST_RESULT(space_get_up(s) == 4);
    space_destroy(s);
}

void test2_space_get_up() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_up(s) == NO_ID);
}

void test1_space_get_down() {
    Space *s;
    s = space_create(5);
    space_set_down(s, 2);
    PRINT_TEST_RESULT(space_get_down(s) == 2);
    space_destroy(s);
}

void test2_space_get_down() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_down(s) == NO_ID);
}

void test1_space_set_full_desc() {
    Space *s;
    s = space_create(5);
    PRINT_TEST_RESULT(space_set_full_desc(s, "hola") == OK);
    space_destroy(s);
}

void test2_space_set_full_desc() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_full_desc(s, "hola") == ERROR);
}

void test1_space_get_full_desc() {
    Space *s;
    s = space_create(1);
    space_set_full_desc(s, "adios");
    PRINT_TEST_RESULT(strcmp(space_get_full_desc(s), "adios") == 0);
    space_destroy(s);
}

void test2_space_get_full_desc() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_get_full_desc(s) == NULL);
}

void test1_space_set_light() {
    Space *s;
    s = space_create(1);
    PRINT_TEST_RESULT(space_set_light(s, TRUE) == OK);
    space_destroy(s);
}

void test2_space_set_light() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_set_light(s, TRUE) == ERROR);
    space_destroy(s);
}

void test1_space_is_lit() {
    Space *s;
    int flag = 1;
    s = space_create(1);
    if (space_is_lit(s) == FALSE) flag = 0;
    space_set_light(s, FALSE);
    if (space_is_lit(s) == TRUE) flag = 0;
    PRINT_TEST_RESULT(flag);
    space_destroy(s);
}

void test2_space_is_lit() {
    Space *s = NULL;
    PRINT_TEST_RESULT(space_is_lit(s) == FALSE);
}
