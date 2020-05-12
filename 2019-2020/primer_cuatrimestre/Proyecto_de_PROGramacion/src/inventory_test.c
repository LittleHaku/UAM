/**
 * @brief It declares the tests for the inventory module
 *
 * @file inventory_test.c
 * @author Junco de las Heras Valenzuela
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "inventory.h"
#include "inventory_test.h"
#include "test.h"

#define MAX_TESTS 16

/**
 * @brief Funcion principal de pruebas para el modulo Inventory.
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


    if (all || test == 1) test1_inventory_create();
    if (all || test == 2) test2_inventory_create();
    if (all || test == 3) test1_inventory_get_max_size();
    if (all || test == 4) test2_inventory_get_max_size();
    if (all || test == 5) test1_inventory_set_max_size();
    if (all || test == 6) test2_inventory_set_max_size();
    if (all || test == 7) test1_inventory_get_size();
    if (all || test == 8) test2_inventory_get_size();
    if (all || test == 9) test1_inventory_get_ids();
    if (all || test == 10) test2_inventory_get_ids();
    if (all || test == 11) test1_inventory_has_id();
    if (all || test == 12) test2_inventory_has_id();
    if (all || test == 13) test1_inventory_insert();
    if (all || test == 14) test2_inventory_insert();
    if (all || test == 15) test1_inventory_delete();
    if (all || test == 16) test2_inventory_delete();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_inventory_create() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory != NULL);
    inventory_destroy(inventory);
}

void test2_inventory_create() {
    Inventory *inventory = NULL;
    inventory = inventory_create(-5);
    PRINT_TEST_RESULT(inventory == NULL);
    inventory_destroy(inventory);
}

void test1_inventory_get_max_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory_get_max_size(inventory) == 5);
    inventory_destroy(inventory);
}

void test2_inventory_get_max_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(55);
    PRINT_TEST_RESULT(inventory_get_max_size(inventory) == 55);
    inventory_destroy(inventory);
}

void test1_inventory_set_max_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_set_max_size(inventory, 6);
    PRINT_TEST_RESULT(inventory_get_max_size(inventory) == 6);
    inventory_destroy(inventory);
}

void test2_inventory_set_max_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_set_max_size(inventory, -6);
    PRINT_TEST_RESULT(inventory_get_max_size(inventory) != -6);
    inventory_destroy(inventory);
}

void test1_inventory_get_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory_get_size(inventory) == 0);
    inventory_destroy(inventory);
}

void test2_inventory_get_size() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_insert(inventory, 2);
    PRINT_TEST_RESULT(inventory_get_size(inventory) == 1);
    inventory_destroy(inventory);
}

void test1_inventory_get_ids() {
    Id *id;
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    id = inventory_get_ids(inventory);
    PRINT_TEST_RESULT(id != NULL);
    inventory_destroy(inventory);
}

void test2_inventory_get_ids() {
    Id *id;
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_insert(inventory, 2);
    id = inventory_get_ids(inventory);
    PRINT_TEST_RESULT(id != NULL);
    inventory_destroy(inventory);
}

void test1_inventory_has_id() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory_has_id(inventory, 5) == FALSE);
    inventory_destroy(inventory);
}

void test2_inventory_has_id() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_insert(inventory, 2);
    PRINT_TEST_RESULT(inventory_has_id(inventory, 2) == TRUE);
    inventory_destroy(inventory);
}

void test1_inventory_insert() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory_insert(inventory, 3) == OK);
    inventory_destroy(inventory);
}

void test2_inventory_insert() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    PRINT_TEST_RESULT(inventory_insert(inventory, NO_ID) == ERROR);
    inventory_destroy(inventory);
}

void test1_inventory_delete() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_insert(inventory, 3);
    PRINT_TEST_RESULT(inventory_delete(inventory, 3) == OK);
    inventory_destroy(inventory);
}

void test2_inventory_delete() {
    Inventory *inventory = NULL;
    inventory = inventory_create(5);
    inventory_insert(inventory, 4);
    PRINT_TEST_RESULT(inventory_delete(inventory, 3) == ERROR);
    inventory_destroy(inventory);
}
