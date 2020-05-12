/**
 * @brief It tests the graphic engine interface.
 *
 * @file graphic_engine_test.c
 * @author Fabian Gutierrez
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "graphic_engine.h"
#include "graphic_engine_test.h"
#include "test.h"
#include "game_management.h"
#include "game.h"

#define MAX_TESTS 5

/**
 * @brief Funcion principal de pruebas para el modulo Graphic Engine.
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
    Graphic_engine *ge = NULL;

    if (argc < 2) {
        printf("Running all test for module Graphic Engine:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\n", test);
            exit(EXIT_SUCCESS);
        }
    }

    ge = graphic_engine_create();

    if (all || test == 1) test_graphic_engine_create(ge);
    if (all || test == 2) test1_graphic_engine_paint_game(ge);
    if (all || test == 3) test2_graphic_engine_paint_game(ge);
    if (all || test == 4) test1_graphic_engine_print_file(ge);
    if (all || test == 5) test2_graphic_engine_print_file(ge);

    PRINT_PASSED_PERCENTAGE;

    graphic_engine_destroy(ge);
    return 0;
}

void test_graphic_engine_create(Graphic_engine *ge) {
    PRINT_TEST_RESULT(ge != NULL);
}

void test1_graphic_engine_paint_game(Graphic_engine *ge) {
    graphic_engine_paint_game(ge, NULL, OK);
    PRINT_TEST_RESULT(ge != NULL);
}

void test2_graphic_engine_paint_game(Graphic_engine *ge) {
    Game *game = NULL;

    if ((game = game_create()) == NULL) {
        printf("ERROR: no se pudo crear el juego.\n");
        return;
    }
    game_management_load(game, "TestFiles/prueba2.dat");
    if (game == NULL) {
        printf("ERROR: no se pudo cargar el juego.\n");
        return;
    }

    graphic_engine_paint_game(ge, game, OK);
    PRINT_TEST_RESULT(ge != NULL);
    game_destroy(game);
}

void test1_graphic_engine_print_file(Graphic_engine *ge) {
    Game *game = NULL;

    if ((game = game_create()) == NULL) {
        printf("ERROR: no se pudo crear el juego.\n");
        return;
    }
    game_management_load(game, "TestFiles/prueba2.dat");
    if (game == NULL) {
        printf("ERROR: no se pudo cargar el juego.\n");
        return;
    }

    graphic_engine_print_file(ERROR, game, NULL);
    PRINT_TEST_RESULT(TRUE);
    game_destroy(game);
}

void test2_graphic_engine_print_file(Graphic_engine *ge) {
    Game *game = NULL;
    FILE *f = NULL;
    char output[WORD_SIZE] = "", aux[WORD_SIZE] = "";

    if ((game = game_create()) == NULL) {
        printf("ERROR: no se pudo crear el juego.\n");
        return;
    }
    game_management_load(game, "TestFiles/prueba2.dat");
    if (game == NULL) {
        printf("ERROR: no se pudo cargar el juego.\n");
        return;
    }
    f = fopen("TestFiles/graphic_engine_print_file_test.log", "w");
    if (f == NULL) {
        game_destroy(game);
        return;
    }

    graphic_engine_print_file(game_update(game, EXIT), game, f);
    fclose(f);
    /*Output*/
    f = fopen("TestFiles/graphic_engine_print_file_test.log", "r");
    fscanf(f, "%s", aux);
    sprintf(output, "%s", aux);
    fscanf(f, "%s", aux);
    sprintf(output + strlen(output), " %s", aux);
    fclose(f);

    printf("%s\n", output);

    PRINT_TEST_RESULT(!strcmp(output, "exit: OK"));
    game_destroy(game);
}
