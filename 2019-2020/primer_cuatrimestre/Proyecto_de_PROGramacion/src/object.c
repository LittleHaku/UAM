/**
 * @brief It implements the object interface
 * @file object.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 24-09-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "object.h"

/**
 * @brief The Object structure
 *
 * It stores the different elements the object manages
 */
struct _Object {
    Id id; /*!<Object's id*/
    char name[WORD_SIZE]; /*!<Object's name*/
    char description[WORD_SIZE]; /*!<Objects's description*/
    BOOL movable; /*!<Objects's movable*/
    BOOL moved; /*!<Objects's moved*/
    BOOL hidden; /*!<Objects's hidden*/
    Id open; /*!<Objects's open*/
    BOOL illuminate; /*!<Objects's illuminate*/
    BOOL turnedon; /*!<Objects's turnedon*/
    char full_desc[WORD_SIZE]; /*!<Objects's detailed description*/
};

/**
Game interface implementation
 */
Object* object_create(Id id) {
    Object *obj;

    if (id == NO_ID) {
        return NULL;
    }

    obj = (Object*) malloc(sizeof (Object));
    if (obj == NULL) {
        return NULL;
    }

    obj->id = id;
    if (!strncpy(obj->name, "\0", WORD_SIZE)) {
        free(obj);
        return NULL;
    }

    obj->name[0] = '\0';
    obj->description[0] = '\0';
    obj->full_desc[0] = '\0';

    obj->movable = FALSE;
    obj->moved = FALSE;
    obj->hidden = FALSE;
    obj->open = NO_ID;
    obj->illuminate = FALSE;
    obj->turnedon = FALSE;


    return obj;
}

STATUS object_destroy(Object *obj) {
    if (!obj) {
        return ERROR;
    }

    free(obj);
    obj = NULL;
    return OK;
}

Id object_get_id(Object *obj) {
    if (!obj) {
        return NO_ID;
    }

    return obj->id;
}

STATUS object_set_name(Object *obj, char* name) {
    if (obj == NULL || name == NULL) {
        return ERROR;
    }
    if (!strncpy(obj->name, name, WORD_SIZE)) {
        return ERROR;
    }
    return OK;
}

const char* object_get_name(Object *obj) {
    if (!obj) {
        return NULL;
    }

    return obj->name;
}

STATUS object_set_description(Object *obj, char* desc) {
    if (obj == NULL || desc == NULL) {
        return ERROR;
    }

    if (!strncpy(obj->description, desc, WORD_SIZE)) {
        return ERROR;
    }
    return OK;
}

const char* object_get_description(Object *obj) {
    if (obj == NULL) {
        return NULL;
    }

    return obj->description;
}

STATUS object_print(Object *obj) {
    if (obj == NULL) {
        return ERROR;
    }

    fprintf(stdout, "--> Object (Id: %ld; Name: %s)\n", obj->id, obj->name);
    fprintf(stdout, "movable = %d\n", obj->movable);
    fprintf(stdout, "moved = %d\n", obj->moved);
    fprintf(stdout, "hidden = %d\n", obj->hidden);
    fprintf(stdout, "open = %ld\n", obj->open);
    fprintf(stdout, "illuminate = %d\n", obj->illuminate);
    fprintf(stdout, "turnedon = %d\n", obj->turnedon);
    fprintf(stdout, "full_desc = %s\n", obj->full_desc);

    return OK;
}

STATUS object_set_movable(Object *obj, BOOL movable) {
    if (!obj)
        return ERROR;
    obj->movable = movable;

    return OK;
}

BOOL object_is_movable(Object *obj) {
    if (!obj)
        return FALSE;
    return obj->movable;
}

STATUS object_set_moved(Object *obj, BOOL moved) {
    if (!obj)
        return ERROR;
    obj->moved = moved;

    return OK;
}

BOOL object_get_moved(Object *obj) {
    if (!obj)
        return FALSE;

    return obj->moved;
}

STATUS object_set_hidden(Object *obj, BOOL hidden) {
    if (!obj)
        return ERROR;
    obj->hidden = hidden;

    return OK;
}

BOOL object_get_hidden(Object *obj) {
    if (!obj)
        return FALSE;

    return obj->hidden;
}

STATUS object_set_open(Object *obj, Id open) {
    if (!obj)
        return ERROR;
    obj->open = open;

    return OK;
}

Id object_get_open(Object *obj) {
    if (!obj)
        return NO_ID;

    return obj->open;
}

STATUS object_set_illuminate(Object *obj, BOOL illuminate) {
    if (!obj)
        return ERROR;
    obj->illuminate = illuminate;
    return OK;
}

BOOL object_can_illuminate(Object *obj) {
    if (!obj)
        return FALSE;

    return obj->illuminate;
}

STATUS object_set_turnedon(Object *obj, BOOL turnedon) {
    if (!obj)
        return ERROR;

    if (object_can_illuminate(obj) == FALSE && turnedon == TRUE)
        return ERROR;

    obj->turnedon = turnedon;

    return OK;
}

BOOL object_is_turnedon(Object *obj) {
    if (!obj)
        return FALSE;

    return obj->turnedon;
}

STATUS object_set_full_desc(Object* obj, char* desc) {
    if (obj == NULL) {
        return ERROR;
    }

    if (!strncpy(obj->full_desc, desc, WORD_SIZE)) {
        return ERROR;
    }
    return OK;
}

const char* object_get_full_desc(Object* obj) {
    if (obj == NULL) {
        return NULL;
    }

    return obj->full_desc;
}
