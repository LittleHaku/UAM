/**
 * @brief It implements the space data type
 *
 * @file space.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 02-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "types.h"
#include "space.h"
#include "set.h"

/**
 * @brief The Space structure
 *
 * It stores the different elements the space manages
 */
struct _Space {
    Id id; /*!< Space's Id*/
    char name[WORD_SIZE + 1]; /*!< Space's name*/
    Id north; /*!< Space's north Id*/
    Id south; /*!< Space's south Id*/
    Id east; /*!< Space's east Id*/
    Id west; /*!< Space's west Id*/
    Id up; /*!< Space's up Id*/
    Id down; /*!< Space's down Id*/
    Set* objects; /*!< Space's object Id*/
    char gdesc[MAX_ROW][MAX_COL + 1]; /*!< Space's graphic description*/
    char description[WORD_SIZE]; /*!< Space's description*/
    char full_desc[WORD_SIZE]; /*!< Space's detailed description*/
    BOOL light; /*!< True if the space is illuminated*/
};

/**
Game interface implementation
 */
Space* space_create(Id id) {
    int i;

    Space *new_space = NULL;

    if (id == NO_ID) {
        return NULL;
    }

    new_space = (Space *) malloc(sizeof (Space));

    if (new_space == NULL) {
        return NULL;
    }
    new_space->id = id;

    new_space->name[0] = '\0';

    new_space->north = NO_ID;
    new_space->south = NO_ID;
    new_space->east = NO_ID;
    new_space->west = NO_ID;
    new_space->up = NO_ID;
    new_space->down = NO_ID;

    new_space->objects = set_create();
    if (new_space->objects == NULL) {
        free(new_space);
        return NULL;
    }

    for (i = 0; i < MAX_ROW; i++) {
        new_space->gdesc[i][0] = '\0';
    }

    new_space->description[0] = '\0';
    new_space->full_desc[0] = '\0';
    new_space->light = TRUE;

    return new_space;
}

STATUS space_destroy(Space* space) {
    if (!space) {
        return ERROR;
    }

    set_destroy(space->objects);

    free(space);
    space = NULL;

    return OK;
}

STATUS space_set_name(Space* space, char* name) {
    if (!space || !name) {
        return ERROR;
    }

    if (!strcpy(space->name, name)) {
        return ERROR;
    }

    return OK;
}

STATUS space_set_north(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->north = id;
    return OK;
}

STATUS space_set_south(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->south = id;
    return OK;
}

STATUS space_set_east(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->east = id;
    return OK;
}

STATUS space_set_west(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->west = id;
    return OK;
}

STATUS space_set_up(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->up = id;
    return OK;
}

STATUS space_set_down(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    space->down = id;
    return OK;
}

STATUS space_add_object(Space* space, Id object_id) {
    if (!space) {
        return ERROR;
    }

    if (!set_add(space->objects, object_id)) {
        return ERROR;
    }

    return OK;
}

STATUS space_set_gdesc(Space* space, char* gdesc, int row) {
    if (!space || !gdesc || row >= MAX_ROW || row < 0) {
        return ERROR;
    }

    strncpy(space->gdesc[row], gdesc, MAX_COL);
    space->gdesc[row][MAX_COL] = '\0';

    return OK;
}

const char* space_get_name(Space* space) {
    if (!space) {
        return NULL;
    }

    return space->name;
}

Id space_get_id(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->id;
}

Id space_get_north(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->north;
}

Id space_get_south(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->south;
}

Id space_get_east(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->east;
}

Id space_get_west(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->west;
}

Id space_get_up(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->up;
}

Id space_get_down(Space* space) {
    if (!space) {
        return NO_ID;
    }

    return space->down;
}

Id* space_get_objects(Space* space) {
    if (!space) {
        return NULL;
    }

    return set_get_array(space->objects);
}

STATUS space_del_object(Space* space, Id id) {
    if (!space) {
        return ERROR;
    }

    return set_del(space->objects, id);
}

int space_get_nelems(Space* space) {
    if (!space) {
        return -1;
    }

    return set_get_size(space->objects);
}

char* space_get_gdesc(Space* space, int row) {
    if (!space || row >= MAX_ROW) {
        return NULL;
    }

    return space->gdesc[row];
}

BOOL space_has_object(Space* space, Id id) {
    if (!space) {
        return FALSE;
    }

    return set_has_id(space->objects, id);
}

STATUS space_set_description(Space* space, char* desc) {
    if (space == NULL) {
        return ERROR;
    }

    if (!strncpy(space->description, desc, WORD_SIZE)) {
        return ERROR;
    }
    return OK;
}

const char* space_get_description(Space* space) {
    if (space == NULL) {
        return NULL;
    }

    return space->description;
}

STATUS space_set_full_desc(Space* space, char* desc) {
    if (space == NULL) {
        return ERROR;
    }

    if (!strncpy(space->full_desc, desc, WORD_SIZE)) {
        return ERROR;
    }
    return OK;
}

const char* space_get_full_desc(Space* space) {
    if (space == NULL) {
        return NULL;
    }

    return space->full_desc;
}

STATUS space_set_light(Space *space, BOOL light) {
    if (space == NULL) {
        return ERROR;
    }

    space->light = light;
    return OK;
}

BOOL space_is_lit(Space *space) {
    if (space == NULL) {
        return FALSE;
    }

    return space->light;
}

STATUS space_print(Space* space) {
    Id idaux = NO_ID;
    int i;

    if (!space) {
        return ERROR;
    }

    fprintf(stdout, "--> Space (Id: %ld; Name: %s)\n", space->id, space->name);

    idaux = space_get_north(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> North link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No north link.\n");
    }

    idaux = space_get_south(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> South link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No south link.\n");
    }

    idaux = space_get_east(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> East link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No east link.\n");
    }

    idaux = space_get_west(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> West link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No west link.\n");
    }

    idaux = space_get_up(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> Up link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No up link.\n");
    }

    idaux = space_get_down(space);
    if (NO_ID != idaux) {
        fprintf(stdout, "---> Down link: %ld.\n", idaux);
    } else {
        fprintf(stdout, "---> No down link.\n");
    }

    fprintf(stdout, "---> Objects:\n");
    if (!set_print(space->objects)) {
        return ERROR;
    }

    fprintf(stdout, "---> Graphic description:\n");
    for (i = 0; i < MAX_ROW; i++) {
        fprintf(stdout, "%s\n", space->gdesc[i]);
    }

    fprintf(stdout, "---> Description:\n");
    fprintf(stdout, "%s\n", space->description);

    fprintf(stdout, "---> Full description:\n");
    fprintf(stdout, "%s\n", space->full_desc);

    if (space->light == TRUE) {
        fprintf(stdout, "--->The space is illuminated\n");
    } else {
        fprintf(stdout, "--->The space is not illuminated\n");
    }

    return OK;
}
