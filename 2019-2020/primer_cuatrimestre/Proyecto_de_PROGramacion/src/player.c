/**
 * @brief It implements the player interface
 *
 * @file player.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 03-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "player.h"
#include "inventory.h"

/**
 * @brief The Player structure
 *
 * It stores the different elements the player manages
 */
struct _Player {
    Id id; /*<! Id of the player*/
    char name[WORD_SIZE + 1]; /*<! name of the player*/
    Id space_location; /*<! Id of the location of the player*/
    Inventory* player_inventory; /*<! inventory carried by the player*/
};

Player* player_create(Id id, int max_size) {
    Player *player = NULL;

    if (id == NO_ID || max_size < 0)
        return NULL;

    if ((player = (Player*) malloc(sizeof (Player))) == NULL)
        return NULL;

    player->id = id;
    player->name[0] = '\0';
    player->space_location = NO_ID;
    player->player_inventory = inventory_create(max_size);
    if (!player->player_inventory) {
        free(player);
        return NULL;
    }

    return player;

}

STATUS player_destroy(Player *player) {
    if (!player) {
        return ERROR;
    }
    if (inventory_destroy(player->player_inventory) == ERROR) {
        free(player);
        return ERROR;
    }
    free(player);

    return OK;
}

STATUS player_set_id(Player *player, Id id) {
    if (!player || id == NO_ID) {
        return ERROR;
    }

    player->id = id;

    return OK;
}

Id player_get_id(Player *player) {
    if (!player) {
        return NO_ID;
    }

    return player->id;
}

STATUS player_set_name(Player *player, char *name) {
    if (!player || !name) {
        return ERROR;
    }

    if (!strcpy(player->name, name)) {
        return ERROR;
    }

    return OK;
}

int player_get_num_max_objects(Player* player) {
    if (!player) return -1;
    return inventory_get_max_size(player->player_inventory);
}

const char* player_get_name(Player *player) {
    if (!player) {
        return ERROR;
    }

    return player->name;
}

STATUS player_set_space(Player *player, Id space_location) {
    if (!player || space_location == NO_ID) {
        return ERROR;
    }

    player->space_location = space_location;

    return OK;
}

STATUS player_set_num_max_objects(Player *player, int num_objects) {
    if (!player || num_objects < 0) return ERROR;

    return inventory_set_max_size(player->player_inventory, num_objects);
}

Id player_get_space(Player *player) {
    if (!player) {
        return NO_ID;
    }

    return player->space_location;
}

STATUS player_insert_object(Player *player, Id new_id) {
    if (!player || new_id == NO_ID) {
        return ERROR;
    }

    return inventory_insert(player->player_inventory, new_id);
}

STATUS player_delete_object(Player *player, Id new_id) {
    if (!player || new_id == NO_ID) {
        return ERROR;
    }

    return inventory_delete(player->player_inventory, new_id);
}

Id* player_get_objects(Player *player) {
    if (!player) {
        return NULL;
    }

    return inventory_get_ids(player->player_inventory);
}

BOOL player_has_object(Player* player, Id id) {
    if (!player) {
        return FALSE;
    }
    return inventory_has_id(player->player_inventory, id);
}

STATUS player_print(Player* player) {
    if (!player) {
        return ERROR;
    }

    fprintf(stdout, "--> Player (Id: %ld; Name: %s; Id_location: %ld)\n", player->id, player->name, player->space_location);
    fprintf(stdout, "--->Player objects:\n");
    inventory_print(player->player_inventory);

    return OK;
}
