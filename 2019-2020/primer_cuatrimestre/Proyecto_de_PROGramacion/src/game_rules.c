/**
 * @brief It implements the game rules interface
 *
 * @file game_rules.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 12-12-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "types.h"
#include "game.h"
#include "game_rules.h"
#include "object.h"

#define GRULES_TURNS 3
#define NUMBER_FUNCTIONS 6

/**
 * @brief The Game rules structure
 *
 * It stores the different elements the game rules manages
 */
struct _grules {
    Game *game; /*!< The game to change*/
    Object **object; /*!< All the game's objects*/
    Link **link; /*!< All the game's links*/
    Space **space; /*!< All the game's spaces*/
    Die *die;
    int turns; /*!< Turn number*/
};

/**
Private functions
 */
void game_rules_parse(grules *gr, int n, void* o);

/**
Game interface implementation
 */
grules *game_rules_ini(Game *g) {
    grules *gr = NULL;
    if (g == NULL)
        return NULL;
    gr = (grules*) malloc(sizeof (grules));
    if (gr == NULL)
        return NULL;

    gr->object = game_get_objects(g);
    if (gr->object == NULL) {
        free(gr);
        return NULL;
    }

    gr->link = game_get_links(g);
    if (gr->link == NULL) {
        free(gr);
        return NULL;
    }

    gr->space = game_get_spaces(g);
    if (gr->space == NULL) {
        free(gr);
        return NULL;
    }

    gr->die = die_create(42, 0, NUMBER_FUNCTIONS - 1);
    if (!gr->die) {
        free(gr);
        return NULL;
    }

    gr->game = g;
    gr->turns = 0;

    return gr;
}

void game_rules_destroy(grules *gr) {
    if (gr == NULL) {
        return;
    }
    die_destroy(gr->die);
    free(gr);
}

void game_rules_random_action(grules *gr) {
    int num, num_objects, num_links, num_spaces;
    void *o;

    if (gr == NULL || GRULES_TURNS == 0) {
        return;
    }

    if (gr->turns++ % GRULES_TURNS) {
        game_set_last_change(gr->game, "");
        return;
    }

    die_roll(gr->die);
    num = die_get_last_roll(gr->die);
    switch (num) {
        case 0:
            num_objects = game_get_num_objects(gr->game);
            if (num_objects == 0)
                return;
            o = gr->object[rand() % num_objects];
            if (object_get_hidden(o) == TRUE) {
                return;
            }
            if (player_has_object(game_get_player(gr->game), object_get_id(o)) == TRUE) {
                return;
            }
            if (object_set_hidden(o, TRUE) == ERROR) {
                return;
            }
            break;
        case 1:
            num_objects = game_get_num_objects(gr->game);
            if (num_objects == 0)
                return;
            o = gr->object[rand() % num_objects];
            if (object_set_hidden(o, FALSE) == ERROR) {
                return;
            }
            break;
        case 2:
            num_links = game_get_num_links(gr->game);
            if (num_links == 0)
                return;
            o = gr->link[rand() % num_links];
            if (link_set_open(o, FALSE) == ERROR) {
                return;
            }
            break;
        case 3:
            num_links = game_get_num_links(gr->game);
            if (num_links == 0)
                return;
            o = gr->link[rand() % num_links];
            if (link_set_open(o, TRUE) == ERROR) {
                return;
            }
            break;
        case 4:
            num_spaces = game_get_num_spaces(gr->game);
            if (num_spaces == 0)
                return;
            o = gr->space[rand() % num_spaces];
            if (space_set_light(o, FALSE) == ERROR) {
                return;
            }
            break;
        case 5:
            num_spaces = game_get_num_spaces(gr->game);
            if (num_spaces == 0)
                return;
            o = gr->space[rand() % num_spaces];
            if (space_set_light(o, TRUE) == ERROR) {
                return;
            }
            break;

        default:
            return;
    }

    game_rules_parse(gr, num, o);
    return;
}

/***Private functions***/
void game_rules_parse(grules *gr, int n, void* o) {
    char str[WORD_SIZE];

    switch (n) {
        case 0:
            if (sprintf(str, "It seems that the %s has been hidden...", object_get_name((Object*) o)) < 0) {
                return;
            }
            break;
        case 1:
            if (sprintf(str, "Look, a %s has appeared!", object_get_name((Object*) o)) < 0) {
                return;
            }
            break;
        case 2:
            if (strncpy(str, "That sounds like a door closing...", WORD_SIZE) == NULL) {
                return;
            }
            break;
        case 3:
            if (strncpy(str, "It seems that some door has been opened.", WORD_SIZE) == NULL) {
                return;
            }
            break;
        case 4:
            if (strncpy(str, "Is someone turning off the lights?", WORD_SIZE) == NULL) {
                return;
            }
            break;
        case 5:
            if (strncpy(str, "Maybe someone has turned on a light.", WORD_SIZE) == NULL) {
                return;
            }
            break;

        default:
            return;
    }

    game_set_last_change(gr->game, str);
}
