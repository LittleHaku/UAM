/**
 * @brief It defines the game loop
 *
 * @file game_loop.c
 * @author Leandro Garcia, Fabian Gutoerrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 30-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "graphic_engine.h"
#include "game_management.h"
#include "game_rules.h"

int game_loop_clean_up(FILE *f, int return_value, grules *gr);

int game_loop_clean_up(FILE *f, int return_value, grules *gr) {
    if (f != NULL) {
        fclose(f);
    }
    if (gr != NULL) {
        game_rules_destroy(gr);
    }
    return return_value;
}

int main(int argc, char *argv[]) {
    FILE *f = NULL;
    Game *game;
    T_Command command = NO_CMD;
    Graphic_engine *gengine;
    STATUS st = OK;
    grules *gr = NULL;

    if (argc != 2 && argc != 4 && argc != 5) {
        fprintf(stderr, "Use: %s <game_data_file> -l <output_file> -t\n", argv[0]);
        fprintf(stderr, "-l to generate game log (-t to not paint on screen).\n");
        return 1;
    }

    if (argc == 4 || argc == 5) {
        if (strcmp(argv[2], "-l") || (argc == 5 && strcmp(argv[4], "-t"))) {
            fprintf(stderr, "Use: %s <game_data_file> -l <output_file> -t\n", argv[0]);
            fprintf(stderr, "-l to generate game log (-t to not paint on screen).\n");
            return 1;
        }

        f = fopen(argv[3], "w");
        if (f == NULL) {
            return 1;
        }
    }

    game = game_create();
    if (game == NULL) {
        fprintf(stderr, "Error while initializing game.\n");
        return game_loop_clean_up(f, 1, gr);
    }

    if (game_management_load(game, argv[1]) == ERROR) {
        fprintf(stderr, "Error while loading game.\n");
        return game_loop_clean_up(f, 1, gr);
    }
#ifdef DEBUG
    game_print_data(game);
#endif
    if ((gengine = graphic_engine_create()) == NULL) {
        fprintf(stderr, "Error while initializing graphic engine.\n");
        game_destroy(game);
        return game_loop_clean_up(f, 1, gr);
    }

    if (((gr = game_rules_ini(game)) == NULL)) {
        fprintf(stderr, "Error while initializing game rules.\n");
        game_destroy(game);
        return game_loop_clean_up(f, 1, gr);
    }

    while ((command != EXIT) && !game_is_over(game)) {
        if (argc != 5) {
            graphic_engine_paint_game(gengine, game, st);
        }
        if (f != NULL) {
            graphic_engine_print_file(st, game, f);
        }
        command = command_get_user_input();
        st = game_update(game, command);
#ifndef NO_RULE
        game_rules_random_action(gr);
#endif
    }

    if (f != NULL) { /*If there is an output stream*/
        fprintf(f, "exit: OK\n");
    }

    game_destroy(game);
    graphic_engine_destroy(gengine);
    return game_loop_clean_up(f, 0, gr);
}
