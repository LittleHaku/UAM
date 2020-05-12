/**
 * @brief It tests the dialogue interface.
 *
 * @file dialogue_test.c
 * @author Fabian Gutierrez
 * @version 2.0
 * @date 02-12-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "dialogue.h"
#include "dialogue_test.h"
#include "test.h"
#include "game_management.h"
#include "game.h"

#define MAX_TESTS 3

int main(int argc, char** argv) {

    int test = 0;
    int all = 1;

    if (argc < 2) {
        printf("Running all test for module Dialogue:\n");
    }
    else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\n", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test1_dialogue_get_feedback();
    if (all || test == 2) test2_dialogue_get_feedback();
    if (all || test == 3) test3_dialogue_get_feedback();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test1_dialogue_get_feedback() {
    char *feedback = dialogue_get_feedback(NULL, OK);
    PRINT_TEST_RESULT(feedback == NULL);
    free(feedback);
}
void test2_dialogue_get_feedback() {
    char *feedback = NULL;
    Game *game = NULL;

    if ((game = game_create()) == NULL) {
        printf("ERROR: no se pudo crear el juego.\n");
        return;
    }
    game_management_load(game, "TestFiles/prueba2.dat");
    
    feedback = dialogue_get_feedback(game, game_update(game, UNKNOWN));
    
    PRINT_TEST_RESULT(!strcmp(feedback, "This is not a valid action. Try again."));
    game_destroy(game);
    free(feedback);
}
void test3_dialogue_get_feedback() {
    char *feedback = NULL;
    Game *game = NULL;

    if ((game = game_create()) == NULL) {
        printf("ERROR: no se pudo crear el juego.\n");
        return;
    }
    game_management_load(game, "TestFiles/prueba2.dat");
    
    feedback = dialogue_get_feedback(game, game_update(game, EXIT));
    
    PRINT_TEST_RESULT(!strcmp(feedback, "See you later!"));
    game_destroy(game);
    free(feedback);
}