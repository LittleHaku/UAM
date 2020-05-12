/**
 * @brief It implements the inventory interface
 *
 * @file inventory.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 26-10-2019
 * @copyright GNU Public License
 */

#include <stdlib.h>
#include <stdio.h>
#include "inventory.h"
#include "set.h"

/**
 * @brief It defines inventory structure
 */
struct _Inventory {
    int max_size; /*<! maximum capacity of the inventory*/
    Set *s; /*<! set of ids*/
};

Inventory* inventory_create(int max_size) {
    Inventory *inventory;
    if (max_size < 0 || max_size > MAX_SET)
        return NULL;
    inventory = (Inventory*) malloc(sizeof (Inventory));
    if (!inventory)
        return NULL;
    inventory->max_size = max_size;
    inventory->s = set_create();
    if (!inventory->s) {
        free(inventory);
        return NULL;
    }
    return inventory;
}

STATUS inventory_destroy(Inventory* inventory) {
    if (!inventory)
        return ERROR;
    if (set_destroy(inventory->s) == ERROR) {
        free(inventory);
        return ERROR;
    }
    free(inventory);
    return OK;
}

int inventory_get_max_size(Inventory* inventory) {
    if (!inventory)
        return ERROR;
    return inventory->max_size;
}

STATUS inventory_set_max_size(Inventory* inventory, int new_size) {
    int tam;
    if (!inventory || new_size < 0 || new_size > MAX_SET)
        return ERROR;
    tam = inventory_get_size(inventory);

    /*You have more elements than the new size*/
    if (new_size < tam)
        return ERROR;
    inventory->max_size = new_size;
    return OK;
}

int inventory_get_size(Inventory* inventory) {
    if (!inventory)
        return ERROR;
    return set_get_size(inventory->s);
}

Id* inventory_get_ids(Inventory* inventory) {
    if (!inventory)
        return NULL;
    return set_get_array(inventory->s);
}

BOOL inventory_has_id(Inventory *inventory, Id id) {
    if (!inventory) {
        return FALSE;
    }
    return set_has_id(inventory->s, id);
}

STATUS inventory_insert(Inventory* inventory, Id id) {
    if (!inventory || id == NO_ID) {
        return ERROR;
    }
    if (inventory_get_size(inventory) == inventory->max_size) {
        return ERROR;
    }
    return set_add(inventory->s, id);
}

STATUS inventory_delete(Inventory* inventory, Id id) {
    if (!inventory)
        return ERROR;
    return set_del(inventory->s, id);
}

STATUS inventory_print(Inventory* inventory) {
    if (!inventory)
        return ERROR;
    printf("-->Inventory:\n");
    printf("--->Max_size = %d\n", inventory->max_size);
    return set_print(inventory->s);
}
