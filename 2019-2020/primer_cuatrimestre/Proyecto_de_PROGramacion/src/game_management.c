/**
 * @brief It implements the game_management interface
 *
 * @file game_management.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 06-12-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "game_management.h"

extern int errno;

/*Private Functions*/

STATUS game_management_load_spaces(Game* game, char* filename);
STATUS game_management_load_objects(Game* game, char* filename);
STATUS game_management_load_player(Game* game, char* filename);
STATUS game_management_load_links(Game* game, char* filename);
STATUS game_management_save_spaces(Game* game, char* filename);
STATUS game_management_save_objects(Game* game, char* filename);
STATUS game_management_save_player(Game* game, char* filename);
STATUS game_management_save_links(Game* game, char* filename);
STATUS game_management_save_clean_up(FILE* filename, STATUS return_value);

STATUS game_management_load(Game* game, char* filename) {
    if (!filename)
        return ERROR;

    /*Loading spaces and objects from file*/
    if (game_management_load_spaces(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_load_player(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_load_objects(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_load_links(game, filename) == ERROR) {
        return ERROR;
    }

    return OK;
}

STATUS game_management_save(Game *game, char *filename) {
    if (!game) return ERROR;

    if (game_management_save_spaces(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_save_links(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_save_objects(game, filename) == ERROR) {
        return ERROR;
    }

    if (game_management_save_player(game, filename) == ERROR) {
        return ERROR;
    }

    return OK;
}

/*********Private Functions' Implementation*********/

STATUS game_management_load_spaces(Game* game, char* filename) {
    FILE* file = NULL;
    char line[WORD_SIZE] = "";
    char name[WORD_SIZE] = "";
    char* toks = NULL;
    Id id = NO_ID, id_link_north = NO_ID, id_link_east = NO_ID, id_link_south = NO_ID, id_link_west = NO_ID;
    Id id_link_up = NO_ID, id_link_down = NO_ID;
    Space* space = NULL;
    STATUS status = OK;
    int i = 0, is_lit = FALSE;
    char gdesc[MAX_ROW][MAX_COL + 1];
    char desc[WORD_SIZE] = "\0";
    char full_desc[WORD_SIZE] = "\0";
    BOOL correct_set = FALSE;

    if (!game || !filename) {
        return ERROR;
    }

    file = fopen(filename, "r");
    if (file == NULL) {
        status = ERROR;
        return status;
    }

    /*initializing gdesc*/
    for (i = 0; i < MAX_ROW; i++) {
        strncpy(gdesc[i], "", MAX_COL);
    }

    while (fgets(line, WORD_SIZE, file)) {
        if (strncmp("#s:", line, 3) == 0) {
            id = NO_ID;
            id_link_north = NO_ID;
            id_link_east = NO_ID;
            id_link_south = NO_ID;
            id_link_west = NO_ID;
            id_link_up = NO_ID;
            id_link_down = NO_ID;
            toks = strtok(line + 3, "|");
            if (toks)
                id = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                strncpy(name, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_north = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_east = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_south = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_west = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_up = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                id_link_down = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                is_lit = atol(toks);
            for (i = 0; i < MAX_ROW; i++) {
                toks = strtok(NULL, "%");
                if (toks) {
                    strncpy(gdesc[i], toks, MAX_COL);
                    gdesc[i][MAX_COL] = '\0';
                } else {
                    /*toks != NULL if i == 0*/
                    for (; i >= 0; i--) {
                        gdesc[i][0] = '\0';
                    }
                    break;
                }
            }
            toks = strtok(NULL, "|");
            if (toks)
                strncpy(desc, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|");
            if (toks)
                strncpy(full_desc, toks, WORD_SIZE - 1);

#ifdef DEBUG
            printf("Space Leido: %ld|%s|%ld|%ld|%ld|%ld|%ld|%ld|%d\n", id, name, id_link_north, id_link_east, id_link_south, id_link_west, id_link_up, id_link_down, is_lit);
            printf("Descripcion grafica:\n");
            for (i = 0; i < MAX_ROW; i++) {
                printf("%s\n", gdesc[i]);
            }
            printf("Descripcion del space: %s\n", desc);
            printf("Full descripcion del space: %s\n", full_desc);
#endif
            space = space_create(id);
            if (space == NULL) {
                status = ERROR;
                break;
            }

            while (TRUE) {
                if (space_set_name(space, name) == ERROR) break;
                if (space_set_north(space, id_link_north) == ERROR) break;
                if (space_set_east(space, id_link_east) == ERROR) break;
                if (space_set_south(space, id_link_south) == ERROR) break;
                if (space_set_west(space, id_link_west) == ERROR) break;
                if (space_set_up(space, id_link_up) == ERROR) break;
                if (space_set_down(space, id_link_down) == ERROR) break;
                if (is_lit == 0) {
                    if (space_set_light(space, TRUE) == ERROR) break;
                } else {
                    if (space_set_light(space, FALSE) == ERROR) break;
                }
                for (i = 0; i < MAX_ROW; i++) {
                    if (space_set_gdesc(space, gdesc[i], i) == ERROR) break;
                }
                if (space_set_description(space, (char*) desc) == ERROR) break;
                if (space_set_full_desc(space, (char*) full_desc) == ERROR) break;
                if (game_add_space(game, space) == ERROR) break;
                correct_set = TRUE;
                break;
            }
            if (!correct_set) {
                space_destroy(space);
                status = ERROR;
                break;
            }
            space = NULL;
        }
    }

    if (ferror(file)) {
        fprintf(stderr, "%s\n", strerror(errno));
        status = ERROR;
    }

    fclose(file);

    return status;
}

STATUS game_management_load_objects(Game* game, char* filename) {
    FILE* file = NULL;
    char line[WORD_SIZE] = "";
    char name[WORD_SIZE] = "";
    char desc[WORD_SIZE] = "\0";
    char full_desc[WORD_SIZE] = "\0";
    char* toks = NULL;
    Id id = NO_ID, pos_ini = NO_ID, open_link = NO_ID;
    Object* object = NULL;
    Space* space_object = NULL;
    STATUS status = OK;
    BOOL correct_set = FALSE;
    int is_lit = 1, can_illuminate = 1, is_hidden = 1, is_moved = 1, is_movable = 1;
    Player* player = NULL;

    if (!game || !filename) {
        return ERROR;
    }

    file = fopen(filename, "r");
    if (file == NULL) {
        status = ERROR;
        return status;
    }

    while (fgets(line, WORD_SIZE, file)) {
        if (strncmp("#o:", line, 3) == 0) {
            id = NO_ID;
            pos_ini = NO_ID;
            open_link = NO_ID;
            is_lit = 1;
            can_illuminate = 1;
            is_hidden = 1;
            is_moved = 1;
            is_movable = 0;
            toks = strtok(line + 3, "|\n");
            if (toks)
                id = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                strncpy(name, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|\n");
            if (toks)
                pos_ini = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                is_hidden = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                can_illuminate = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                is_lit = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                open_link = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                is_movable = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                is_moved = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                strncpy(desc, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|\n");
            if (toks)
                strncpy(full_desc, toks, WORD_SIZE - 1);

#ifdef DEBUG
            printf("Object leido: %ld|%s|%ld|%s\n", id, name, pos_ini, desc);
            printf("Descripcion del object: %s\n", desc);
            printf("Full descripcion del object: %s\n", full_desc);
#endif
            object = object_create(id);
            if (object == NULL) {
                status = ERROR;
                break;
            }

            while (TRUE) {
                if (object_set_name(object, name) == ERROR) break;
                if (is_hidden == 0) {
                    if (object_set_hidden(object, TRUE) == ERROR) break;
                } else {
                    if (object_set_hidden(object, FALSE) == ERROR) break;
                }
                if (can_illuminate == 0) {
                    if (object_set_illuminate(object, TRUE) == ERROR) break;
                } else {
                    if (object_set_illuminate(object, FALSE) == ERROR) break;
                }

                if (is_lit == 0) {
                    if (object_set_turnedon(object, TRUE) == ERROR) break;
                } else {
                    if (object_set_turnedon(object, FALSE) == ERROR) break;
                }

                if (is_movable == 0) {
                    if (object_set_movable(object, TRUE) == ERROR) break;
                    else {
                        if (is_moved == 0) {
                            if (object_set_moved(object, TRUE) == ERROR) break;
                        } else {
                            if (object_set_moved(object, FALSE) == ERROR) break;
                        }
                    }
                } else {
                    if (object_set_movable(object, FALSE) == ERROR) break;
                    else {
                        if (object_set_moved(object, FALSE) == ERROR) break;
                    }
                }
                if (object_set_open(object, open_link) == ERROR) break;
                if (object_set_description(object, (char*) desc) == ERROR) break;
                if (object_set_full_desc(object, (char*) full_desc) == ERROR) break;
                if (pos_ini != NO_ID) {
                    space_object = game_get_space(game, pos_ini);
                    if (space_add_object(space_object, id) == ERROR) break;
                } else {
                    player = game_get_player(game);
                    if (player_insert_object(player, id) == ERROR) break;
                }
                if (game_add_object(game, object) == ERROR) break;
                correct_set = TRUE;
                break;
            }
            if (!correct_set) {
                object_destroy(object);
                status = ERROR;
                break;
            }
            object = NULL;

        }
    }

    if (ferror(file)) {
        fprintf(stderr, "%s\n", strerror(errno));
        status = ERROR;
    }

    fclose(file);

    return status;
}

STATUS game_management_load_player(Game* game, char* filename) {
    FILE* file = NULL;
    char line[WORD_SIZE] = "";
    char name[WORD_SIZE] = "";
    char* toks = NULL;
    Id id = NO_ID, pos_ini = NO_ID;
    int num_max_objects = 0;
    Player* player = NULL;
    STATUS status = OK;
    BOOL correct_set = FALSE;

    if (!game || !filename) {
        return ERROR;
    }

    file = fopen(filename, "r");
    if (file == NULL) {
        return ERROR;
    }

    while (fgets(line, WORD_SIZE, file)) {
        if (strncmp("#p:", line, 3) == 0) {
            id = NO_ID;
            pos_ini = NO_ID;
            toks = strtok(line + 3, "|\n");
            if (toks)
                id = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                strncpy(name, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|\n");
            if (toks)
                pos_ini = atol(toks);
            toks = strtok(NULL, "|\n");
            if (toks)
                num_max_objects = atol(toks);

#ifdef DEBUG
            printf("Player leido: %ld|%s|%ld|%d\n", id, name, pos_ini, num_max_objects);

#endif
            player = player_create(id, num_max_objects);
            if (player == NULL) {
                status = ERROR;
                break;
            }

            while (TRUE) {
                if (player_set_name(player, name) == ERROR) break;
                if (player_set_space(player, pos_ini) == ERROR) break;
                if (player_set_num_max_objects(player, num_max_objects) == ERROR) break;
                if (game_set_player(game, player) == ERROR) break;
                correct_set = TRUE;
                break;
            }
            if (!correct_set) {
                player_destroy(player);
                status = ERROR;
                break;
            }
            player = NULL;
        }
    }

    if (ferror(file)) {
        fprintf(stderr, "%s\n", strerror(errno));
        status = ERROR;
    }

    fclose(file);

    return status;
}

STATUS game_management_load_links(Game* game, char* filename) {
    FILE* file = NULL;
    char line[WORD_SIZE] = "";
    char name[WORD_SIZE] = "";
    char* toks = NULL;
    Id id = NO_ID, id_space1 = NO_ID, id_space2 = NO_ID;
    Link* link = NULL;
    STATUS status = OK;
    int status_link = 1; /*suponemos que esta cerrado */
    BOOL correct_set = FALSE;

    if (!game || !filename) {
        return ERROR;
    }

    file = fopen(filename, "r");
    if (file == NULL) {
        return ERROR;
    }

    while (fgets(line, WORD_SIZE, file)) {
        if (strncmp("#l:", line, 3) == 0) {
            toks = strtok(line + 3, "|");
            id = atol(toks);
            toks = strtok(NULL, "|\n");
            strncpy(name, toks, WORD_SIZE - 1);
            toks = strtok(NULL, "|\n");
            id_space1 = atol(toks);
            toks = strtok(NULL, "|\n");
            id_space2 = atol(toks);
            toks = strtok(NULL, "|\n");
            status_link = atol(toks);
#ifdef DEBUG
            printf("Link leido: %ld|%s|%ld|%ld|%d\n", id, name, id_space1, id_space2, status_link);
#endif
            link = link_create(id);
            if (link == NULL) {
                status = ERROR;
                break;
            }

            while (TRUE) {
                if (link_set_name(link, name) == ERROR) break;
                if (link_set_space1(link, id_space1) == ERROR) break;
                if (link_set_space2(link, id_space2) == ERROR) break;
                if (status_link == 0) {
                    if (link_set_open(link, TRUE) == ERROR) break;
                } else {
                    if (link_set_open(link, FALSE) == ERROR) break;
                }
                if (game_add_link(game, link) == ERROR) break;
                correct_set = TRUE;
                break;
            }
            if (!correct_set) {
                link_destroy(link);
                status = ERROR;
                break;
            }
            link = NULL;
        }
    }

    if (ferror(file)) {
        fprintf(stderr, "%s\n", strerror(errno));
        status = ERROR;
    }

    fclose(file);

    return status;
}

STATUS game_management_save_spaces(Game *game, char* filename) {
    FILE *f = NULL;
    int num_spaces = 0, i, j, is_lit;
    char name[WORD_SIZE];
    Id id = NO_ID, id_link_north = NO_ID, id_link_east = NO_ID, id_link_south = NO_ID, id_link_west = NO_ID;
    Id id_link_up = NO_ID, id_link_down = NO_ID;
    char gdesc[MAX_ROW][MAX_COL + 1];
    char desc[WORD_SIZE] = "\0";
    char full_desc[WORD_SIZE] = "\0";
    Space** spaces = NULL;
    BOOL is_lit_status = FALSE;

    if (!game || !filename) {
        return ERROR;
    }

    f = fopen(filename, "w");
    if (!f) return ERROR;

    num_spaces = game_get_num_spaces(game);
    spaces = game_get_spaces(game);
    if (!spaces) {
        return game_management_save_clean_up(f, ERROR);
    }

    for (i = 0; i < num_spaces; i++) {
        id = space_get_id(spaces[i]);
        strncpy(name, space_get_name(spaces[i]), WORD_SIZE - 1);
        id_link_north = space_get_north(spaces[i]);
        id_link_east = space_get_east(spaces[i]);
        id_link_south = space_get_south(spaces[i]);
        id_link_west = space_get_west(spaces[i]);
        id_link_up = space_get_up(spaces[i]);
        id_link_down = space_get_down(spaces[i]);

        is_lit_status = space_is_lit(spaces[i]);
        if (is_lit_status == FALSE)
            is_lit = 1;
        else
            is_lit = 0;

        for (j = 0; j < MAX_ROW; j++) {
            strncpy(gdesc[j], space_get_gdesc(spaces[i], j), MAX_COL);
        }

        strncpy(desc, space_get_description(spaces[i]), WORD_SIZE - 1);
        strncpy(full_desc, space_get_full_desc(spaces[i]), WORD_SIZE - 1);

        fprintf(f, "#s:%ld|%s|%ld|%ld|%ld|%ld|%ld|%ld|%d|%s|%s|%s|%s|%s|\n", id, name, id_link_north, id_link_east, id_link_south, id_link_west, id_link_up, id_link_down, is_lit, gdesc[0], gdesc[1], gdesc[2], desc, full_desc);
    }
    fprintf(f, "\n");

    return game_management_save_clean_up(f, OK);
}

STATUS game_management_save_objects(Game *game, char* filename) {
    FILE *f = NULL;
    int num_objects = 0, i, is_lit, can_illuminate, is_hidden, is_movable, is_moved;
    char name[WORD_SIZE];
    Id id = NO_ID, pos_ini_space = NO_ID, open_link = NO_ID;
    char desc[WORD_SIZE] = "\0";
    char full_desc[WORD_SIZE] = "\0";
    Object** objects = NULL;
    BOOL is_lit_status = FALSE, can_illuminate_status = FALSE, is_hidden_status = FALSE, is_moved_status, is_movable_status;

    if (!game || !filename) {
        return ERROR;
    }

    f = fopen(filename, "a");
    if (!f) return ERROR;

    num_objects = game_get_num_objects(game);
    objects = game_get_objects(game);
    if (!objects) {
        return game_management_save_clean_up(f, ERROR);
    }

    for (i = 0; i < num_objects; i++) {
        id = object_get_id(objects[i]);
        strncpy(name, object_get_name(objects[i]), WORD_SIZE - 1);
        pos_ini_space = game_find_object(game, id);

        is_hidden_status = object_get_hidden(objects[i]);
        if (is_hidden_status == FALSE) {
            is_hidden = 1;
        } else {
            is_hidden = 0;
        }

        can_illuminate_status = object_can_illuminate(objects[i]);
        if (can_illuminate_status == FALSE)
            can_illuminate = 1;
        else
            can_illuminate = 0;

        is_lit_status = object_is_turnedon(objects[i]);
        if (is_lit_status == FALSE)
            is_lit = 1;
        else
            is_lit = 0;

        open_link = object_get_open(objects[i]);

        is_movable_status = object_is_movable(objects[i]);
        if (is_movable_status == FALSE)
            is_movable = 1;
        else
            is_movable = 0;

        is_moved_status = object_get_moved(objects[i]);
        if (is_moved_status == FALSE)
            is_moved = 1;
        else
            is_moved = 0;

        strncpy(desc, object_get_description(objects[i]), WORD_SIZE - 1);
        strncpy(full_desc, object_get_full_desc(objects[i]), WORD_SIZE - 1);

        fprintf(f, "#o:%ld|%s|%ld|%d|%d|%d|%ld|%d|%d|%s|%s|\n", id, name, pos_ini_space, is_hidden, can_illuminate, is_lit, open_link, is_movable, is_moved, desc, full_desc);

    }
    fprintf(f, "\n");

    return game_management_save_clean_up(f, OK);

}

STATUS game_management_save_links(Game *game, char* filename) {
    FILE *f = NULL;
    int num_links = 0;
    char name[WORD_SIZE];
    Id id = NO_ID, id_space1 = NO_ID, id_space2 = NO_ID;
    Link** links = NULL;
    int is_open, i;
    BOOL link_status = FALSE;

    if (!game || !filename) {
        return ERROR;
    }

    f = fopen(filename, "a");
    if (!f) return ERROR;

    num_links = game_get_num_links(game);
    links = game_get_links(game);
    if (!links) {
        return game_management_save_clean_up(f, ERROR);
    }

    for (i = 0; i < num_links; i++) {
        id = link_get_id(links[i]);
        strncpy(name, link_get_name(links[i]), WORD_SIZE - 1);
        id_space1 = link_get_space1(links[i]);
        id_space2 = link_get_space2(links[i]);

        link_status = link_is_open(links[i]);
        if (link_status == FALSE)
            is_open = 1;
        else
            is_open = 0;

        fprintf(f, "#l:%ld|%s|%ld|%ld|%d|\n", id, name, id_space1, id_space2, is_open);
    }
    fprintf(f, "\n");

    return game_management_save_clean_up(f, OK);
}

STATUS game_management_save_player(Game *game, char* filename) {
    FILE *f = NULL;
    char name[WORD_SIZE];
    Id pos_ini_space = NO_ID, id = NO_ID;
    int num_max_objects = 0;
    Player* player = NULL;

    if (!game || !filename) {
        return ERROR;
    }

    f = fopen(filename, "a");
    if (!f) return ERROR;

    player = game_get_player(game);
    if (!player) {
        return game_management_save_clean_up(f, ERROR);
    }

    id = player_get_id(player);
    strncpy(name, player_get_name(player), WORD_SIZE - 1);
    pos_ini_space = player_get_space(player);
    num_max_objects = player_get_num_max_objects(player);

    fprintf(f, "#p:%ld|%s|%ld|%d|", id, name, pos_ini_space, num_max_objects);

    fprintf(f, "\n");

    return game_management_save_clean_up(f, OK);
}

STATUS game_management_save_clean_up(FILE* filename, STATUS return_value) {
    if (filename != NULL) {
        fclose(filename);
    }

    return return_value;
}
