/**
 * @brief It implements the set interface
 *
 * @file set.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 10-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>

#include "set.h"

/**
 * @brief The Set structure
 *
 * It stores the different elements the set manages
 */
struct _Set {
    Id list[MAX_SET]; /*!<Set's id array*/
    int size; /*!<Set's number of elements*/
};

/**
Set interface implementation
 */
Set* set_create() {
    Set *set;
    int i;

    set = (Set*) malloc(sizeof (Set));
    if (set == NULL) {
        return NULL;
    }

    set->size = 0;
    for (i = 0; i < MAX_SET; i++) {
        set->list[i] = NO_ID;
    }

    return set;
}

STATUS set_destroy(Set* set) {
    if (set == NULL) {
        return ERROR;
    }

    free(set);
    return OK;
}

Id* set_get_array(Set *set) {
    if (set == NULL) {
        return NULL;
    }

    return set->list;
}

int set_get_size(Set *set) {
    if (set == NULL) {
        return -1;
    }

    return set->size;
}

STATUS set_add(Set* set, Id id) {
    int i;

    if (set == NULL) {
        return ERROR;
    }

    if (set->size == MAX_SET) {
        return ERROR;
    }

    for (i = 0; i < set->size; i++) {
        if (set->list[i] == id) {
            return ERROR;
        }
    }

    set->list[set->size++] = id;


    return OK;
}

STATUS set_del(Set* set, Id id) {
    int i;

    if (set == NULL) {
        return ERROR;
    }

    for (i = 0; i < set->size; i++) {
        if (set->list[i] == id) {
            set->list[i] = set->list[--set->size];
            set->list[set->size] = NO_ID;
            return OK;
        }
    }
    return ERROR;
}

BOOL set_has_id(Set *set, Id id) {
    int i;

    if (set == NULL) {
        return FALSE;
    }

    for (i = 0; i < set->size; i++) {
        if (set->list[i] == id) {
            return TRUE;
        }
    }

    return FALSE;
}

STATUS set_print(Set* set) {
    int i;

    if (set == NULL) {
        return ERROR;
    }

    printf("-->Set:\n");
    for (i = 0; i < set->size; i++) {
        printf("--->%ld\n", set->list[i]);
    }

    return OK;
}
