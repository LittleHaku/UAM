/**
 * @brief It implements a textual graphic engine
 *
 * @file graphic_engine.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 04-12-2019
 * @copyright GNU Public License
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "screen.h"
#include "dialogue.h"
#include "game_rules.h"
#include "graphic_engine.h"

#define BUF 511
#define CELL_HEIGHT (4 + MAX_ROW)
#define CELL_WIDTH (2 + MAX_COL)

/**
 * @brief The Graphic_engine structure
 *
 * It stores the different areas the graphic engine manages
 */
struct _Graphic_engine {
    Area *map, *descript, *banner, *help, *feedback; /*!< Pointers to each Area*/
};

/**
Private functions
 */
void graphic_engine_object2str(Game *game, Space *space, char *obj_str);
Id graphic_engine_other_side(Game *game, Id link_id, Id space_id);
void graphic_engine_paint_links(char *str, Graphic_engine *ge, Game* game, Space* space, char link_dest);
void graphic_engine_paint_cell(Graphic_engine *ge, Game *game, Space *space, Id id_spc, Id id_lnk, char back_curr_next);

/**
Graphic Engine interface implementation
 */
Graphic_engine* graphic_engine_create() {
    static Graphic_engine *ge = NULL;

    if (ge != NULL) {
        return ge;
    }

    screen_init();
    ge = (Graphic_engine *) malloc(sizeof (Graphic_engine));
    /*game_name_len = 16*/
    ge->map      = screen_area_init(1                          , 1                  , 3 * (CELL_WIDTH)                     , 3 * CELL_HEIGHT);
    ge->descript = screen_area_init(3 * (CELL_WIDTH) + 2       , 1                  , SCREEN_MAX_STR - 3 - 3 * (CELL_WIDTH), 3 * CELL_HEIGHT);
    ge->banner   = screen_area_init((SCREEN_MAX_STR - 16 - 2)/2, 3 * CELL_HEIGHT + 2, 16 + 2                               , 1              );
    ge->help     = screen_area_init(1                          , 3 * CELL_HEIGHT + 3, SCREEN_MAX_STR - 2                   , 2              );
    ge->feedback = screen_area_init(1                          , 3 * CELL_HEIGHT + 7, SCREEN_MAX_STR - 2                   , 4              );

    return ge;
}

void graphic_engine_destroy(Graphic_engine *ge) {
    if (ge == NULL) {
        return;
    }

    screen_area_destroy(ge->map);
    screen_area_destroy(ge->descript);
    screen_area_destroy(ge->banner);
    screen_area_destroy(ge->help);
    screen_area_destroy(ge->feedback);

    screen_destroy();
    free(ge);
    ge = NULL;
}

void graphic_engine_paint_game(Graphic_engine *ge, Game *game, STATUS st) {
    int i = 0, last_roll = -1, num_obj = 0;
    Id id_act = NO_ID, id_back = NO_ID, id_next = NO_ID;
    Id id_back_space = NO_ID, id_next_space = NO_ID;
    Id id_obj = NO_ID, *p_obj = NULL;
    Space *space_back = NULL, *space_act = NULL, *space_next = NULL;
    Object **game_objects = NULL;
    Player *player = NULL;
    char str[BUF];
    char *feedback = NULL;

    if (ge == NULL || game == NULL) {
        return;
    }

    str[0] = '\0';
    player = game_get_player(game);

    /* Paint the in the map area */
    screen_area_clear(ge->map);
    if ((id_act = player_get_space(player)) != NO_ID) {
        space_act = game_get_space(game, id_act);
        id_back = space_get_north(space_act); /*Links' ids*/
        id_next = space_get_south(space_act);
        /*Cell width = 25*/

        /*Previous space*/
        if (id_back != NO_ID) {
            id_back_space = graphic_engine_other_side(game, id_back, id_act);
            space_back = game_get_space(game, id_back_space);
            graphic_engine_paint_cell(ge, game, space_back, id_back_space, id_back, 'b');
        }
        else { /*To keep the current space centered*/
            for (i = 0; i < CELL_HEIGHT; i++) {
                screen_area_puts(ge->map, " ");
            }
        }

        /*Current space*/
        if (id_act != NO_ID) {
            graphic_engine_paint_cell(ge, game, space_act, id_act, NO_ID, 'c');
        }

        /*Next space*/
        if (id_next != NO_ID) {
            id_next_space = graphic_engine_other_side(game, id_next, id_act);
            space_next = game_get_space(game, id_next_space);
            graphic_engine_paint_cell(ge, game, space_next, id_next_space, id_next, 'n');
        }
    }

    /* Paint the in the description area */
    screen_area_clear(ge->descript);
    sprintf(str, " Objects location:");
    screen_area_puts(ge->descript, str);

    str[0] = '\0';
    /*Looking for game objects*/
    num_obj = game_get_num_objects(game);
#ifdef DEBUG
    printf("num_obj:%d\n", num_obj);
#endif
    game_objects = game_get_objects(game);
    if (game_objects != NULL) {
        i = 0;
        for (i = 0; i < num_obj; i++) {
            id_obj = object_get_id(game_objects[i]);
            if (!player_has_object(player, id_obj) && !game_object_is_hidden(game, game_objects[i])) {
                sprintf(str + strlen(str), " %s:%s,", object_get_name(game_objects[i]), space_get_name(game_get_space(game, game_find_object(game, id_obj))));
            }
        }
        str[strlen(str) - 1] = '\0'; /*removes the last ","*/
    }
    screen_area_puts(ge->descript, str);
    screen_area_puts(ge->descript, " ");

    /*Painting player's objects*/
    sprintf(str, " Player objects:");
    p_obj = player_get_objects(player); /*The objects the player has*/
    if (p_obj == NULL || p_obj[0] == NO_ID) {
        sprintf(str + strlen(str), " none");
    }
    else {
        for (i = 0; p_obj[i] != NO_ID; i++) {
            sprintf(str + strlen(str), " %s,", object_get_name(game_get_object(game, p_obj[i])));
        }
        str[strlen(str) - 1] = '\0'; /*removes the last ","*/
    }
    screen_area_puts(ge->descript, str);
    screen_area_puts(ge->descript, " ");

    last_roll = game_get_last_roll(game);
    if (last_roll < 0) {
        sprintf(str, " Last die value: none");
    }
    else {
        sprintf(str, " Last die value: %d", last_roll);
    }
    screen_area_puts(ge->descript, str);
    screen_area_puts(ge->descript, " ");

    /*Painting descriptions*/
    screen_area_puts(ge->descript, " Descriptions:");
    sprintf(str, " %s", game_get_last_description(game));
    screen_area_puts(ge->descript, str);


    /* Paint the in the banner area */
    screen_area_puts(ge->banner, " Naughty and Nice ");

    /* Paint the in the help area */
    screen_area_clear(ge->help);
    sprintf(str, " The commands you can use are:");
    screen_area_puts(ge->help, str);

    sprintf(str,
    "     next or n, back or b, left or l, right or r, move or m, up or u, down or d, inspect or i, take or t, drop or dr, turnon or ton, turnoff or toff, open or o, roll or rl, save or s, load or ld, exit or e");

    screen_area_puts(ge->help, str);

    /* Paint the in the feedback area */
    feedback = dialogue_get_feedback(game, st);
    if (feedback != NULL) {
        screen_area_puts(ge->feedback, feedback);
        free(feedback);
    }
    sprintf(str, "%s", game_get_last_change(game));
    if (str[0] != '\0') {
        screen_area_puts(ge->feedback, str);
    }

    /* Dump to the terminal */
    screen_paint();
    printf("prompt:> ");
}

void graphic_engine_print_file(STATUS st, Game *game, FILE *f) {
    char str[BUF], str2[BUF];
    T_Command last_cmd = UNKNOWN;
    extern char *cmd_to_str[];

    if (game == NULL || f == NULL) {
        return;
    }

    last_cmd = game_get_last_command(game);
    strcpy(str2, game_get_last_str(game));
    sprintf(str, " ");
    if (last_cmd != NO_CMD) {
        sprintf(str, "%s", cmd_to_str[last_cmd - NO_CMD]);
        if (str2[0] != '\0') {
            sprintf(str + strlen(str), " %s", str2);
        }
        if (st == ERROR) {
            sprintf(str + strlen(str), ": ERROR");
        }
        else {
            sprintf(str + strlen(str), ": OK");
        }
    }

    if (f != NULL && strcmp(str, " ")) { /*If there is an output stream*/
        fprintf(f, "%s\n", str);
    }
}

/* Private functions implementation */
void graphic_engine_object2str(Game* game, Space* space, char* obj_str) {
    int i = 0, len = 0;
    Id *objects = NULL;
    Object *obj_aux = NULL;
    const char *aux = NULL;

    if (game == NULL || space == NULL || obj_str == NULL) {
        return;
    }

    objects = space_get_objects(space);
    len = space_get_nelems(space);
    if (objects != NULL && len > 0 && game_space_is_lit(game, space)) {
        do {
            obj_aux = game_get_object(game, objects[i]);
            aux = object_get_name(obj_aux);
            if (aux == NULL) {  /*End of array*/
                obj_str[0] = '\0';
                return;
            }
            i++;
        } while (game_object_is_hidden(game, obj_aux));   /*Looks for first visible object*/
        sprintf(obj_str, "%s", aux); /*Prints first (visible) object*/

        for (; i < len; i++) {
            obj_aux = game_get_object(game, objects[i]);
            aux = object_get_name(obj_aux);
            if (aux == NULL) {
                obj_str[0] = '\0';
                return;
            }
            if (!game_object_is_hidden(game, obj_aux)) {
                sprintf(obj_str + strlen(obj_str), ", %s", aux); /*Prints at the end*/
            }
        }
    }
    else {
        obj_str[0] = '\0';
    }
}

Id graphic_engine_other_side(Game* game, Id link_id, Id space_id) {
    Id other_id = NO_ID;

    if (game == NULL || link_id == NO_ID || space_id == NO_ID) {
        return NO_ID;
    }

    if ((other_id = link_get_space1(game_get_link(game, link_id))) == space_id) {
        other_id = link_get_space2(game_get_link(game, link_id));
    }

    return other_id;
}

void graphic_engine_paint_links(char *str, Graphic_engine *ge, Game* game, Space* space, char link_dest) {
    Id id_west = NO_ID, id_east = NO_ID, id_spc = NO_ID, id_aux = NO_ID;
    Link *link_west = NULL, *link_east = NULL;
    Space *space_aux = NULL;
    char str_aux[BUF], spc_str[BUF];

    id_spc = space_get_id(space);
    id_west = space_get_west(space);
    id_east = space_get_east(space);
    link_west = game_get_link(game, space_get_west(space));
    link_east = game_get_link(game, space_get_east(space));

    if (link_dest == 'l') { /*Printing links*/
        if (link_west != NULL) {
            sprintf(str_aux, "%24s", link_get_name(link_west));
        }
        else {
            sprintf(str_aux, "                        ");
        }

        sprintf(str_aux + strlen(str_aux), "%s", str);

        if (link_east != NULL) {
            sprintf(str_aux + strlen(str_aux), " %.24s", link_get_name(link_east));
        }
    }
    else {    /*Printing destinations*/
        if ((id_aux = graphic_engine_other_side(game, id_west, id_spc)) != NO_ID) {
            space_aux = game_get_space(game, id_aux);
            if (game_space_is_lit(game, space_aux)) {
                strncpy(spc_str, space_get_name(space_aux), BUF - 1);
            }
            else {
                strncpy(spc_str, "???", BUF - 1);
            }
            if (link_is_open(link_west)) {
                sprintf(str_aux, "%20s <--", spc_str);
            }
            else {
                sprintf(str_aux, "%20s x--", spc_str);
            }
        }
        else {
            sprintf(str_aux, "                        ");
        }

        sprintf(str_aux + strlen(str_aux), "%s", str);

        if ((id_aux = graphic_engine_other_side(game, id_east, id_spc)) != NO_ID) {
            space_aux = game_get_space(game, id_aux);
            if (link_is_open(link_east)) {
                if (game_space_is_lit(game, space_aux)) {
                    sprintf(str_aux + strlen(str_aux), " --> %.20s", space_get_name(space_aux));
                }
                else {
                    sprintf(str_aux + strlen(str_aux), " --> %.20s", "???");
                }
            }
            else {
                if (game_space_is_lit(game, space_aux)) {
                    sprintf(str_aux + strlen(str_aux), " --x %.20s", space_get_name(space_aux));
                }
                else {
                    sprintf(str_aux + strlen(str_aux), " --x %.20s", "???");
                }
            }
        }
    }

    screen_area_puts(ge->map, str_aux);
}

void graphic_engine_paint_cell(Graphic_engine *ge, Game *game, Space *space, Id id_spc, Id id_lnk, char back_curr_next) {
    char str[BUF], obj_str[BUF], spc_str[BUF];
    char *gdesc[MAX_ROW];
    Link *link_aux = NULL;
    int i = 0;
    /*Cell width = 19*/

    graphic_engine_object2str(game, space, obj_str);

    if (game_space_is_lit(game, space)) {  /*Copies space name if lit*/
        strncpy(spc_str, space_get_name(space), BUF - 1);
    }
    else {
        strncpy(spc_str, "???", BUF - 1);
    }

    for (i = 0; i < MAX_ROW; i++) {
        gdesc[i] = space_get_gdesc(space, i); /*Copies gdesc*/
    }
    if (back_curr_next == 'n') {
        link_aux = game_get_link(game, id_lnk);
        if (link_is_open(link_aux)) {
            sprintf(str, "                                         v %.24s", link_get_name(link_aux));
        }
        else {
            sprintf(str, "                                         x %.24s", link_get_name(link_aux));
        }
        screen_area_puts(ge->map, str);
    }

    /*Painting west and east links*/
    if (back_curr_next != 'b') {
        graphic_engine_paint_links(" +-------------------------------+", ge, game, space, 'l');
    }

    /*Painting west and east destinations*/
    if (back_curr_next != 'b') {
        if (back_curr_next == 'c') {
            sprintf(str, " | 8D %27s|", spc_str);
        }
        else {
            sprintf(str, " |%31s|", spc_str);
        }
        graphic_engine_paint_links(str, ge, game, space, 'd');
    }
    else {
        sprintf(str, "                         |%31s|", spc_str);
        screen_area_puts(ge->map, str);
    }

    /*Painting gdesc*/
    if (game_space_is_lit(game, space)) {
        for (i = 0; i < MAX_ROW; i++) {
            sprintf(str, "                         |%-31s|", gdesc[i]);
            screen_area_puts(ge->map, str);
        }
    }
    else {
        for (i = 0; i < MAX_ROW; i++) {
            sprintf(str, "                         |                               |");
            screen_area_puts(ge->map, str);
        }
    }

    /*Painting west and east links for back*/
    if (back_curr_next == 'b') {
        sprintf(str, " |%-31s|", obj_str);
        graphic_engine_paint_links(str, ge, game, space, 'l');
    }
    else {
        sprintf(str, "                         |%-31s|", obj_str);
        screen_area_puts(ge->map, str);
    }

    if (back_curr_next == 'n') {    /*Next space is done*/
        return;
    }
    /*Painting west and east destinations for back*/
    if (back_curr_next == 'b') {
        graphic_engine_paint_links(" +-------------------------------+", ge, game, space, 'd');
    }
    else {
        screen_area_puts(ge->map, "                         +-------------------------------+");
    }

    if (back_curr_next == 'b') {
        link_aux = game_get_link(game, id_lnk);
        if (link_is_open(link_aux)) {
            sprintf(str, "                                        ^ %.24s", link_get_name(link_aux));
        }
        else {
            sprintf(str, "                                        x %.24s", link_get_name(link_aux));
        }
        screen_area_puts(ge->map, str);
    }
}
