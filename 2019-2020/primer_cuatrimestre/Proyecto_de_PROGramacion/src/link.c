/**
 * @brief It implements the link data type
 *
 * @file link.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 03-11-19
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "link.h"

/**
 * @brief The Link structure
 *
 * It stores the different elements the link manages
 */
struct _Link {
    Id id; /*!< Link's is*/
    Id space1; /*!< Link's first space*/
    Id space2; /*!< Link's second space*/
    BOOL open; /*!< Link's status (open or closed)*/
    char name[WORD_SIZE + 1]; /*!< Link's name*/
};

/**
Link interface implementation
 */
Link* link_create(Id id) {
    Link *link;

    if (id == NO_ID) {
        return NULL;
    }

    link = (Link*) malloc(sizeof (Link));
    if (link == NULL) {
        return NULL;
    }

    link->id = id;
    link->space1 = NO_ID;
    link->space2 = NO_ID;
    link->open = TRUE;
    link->name[0] = '\0';

    return link;
}

void link_destroy(Link *link) {
    if (link != NULL) {
        free(link);
        link = NULL;
    }
}

Id link_get_id(Link *link) {
    if (link == NULL) {
        return NO_ID;
    }

    return link->id;
}

STATUS link_set_space1(Link *link, Id space) {
    if (link == NULL) {
        return ERROR;
    }

    link->space1 = space;
    return OK;
}

Id link_get_space1(Link *link) {
    if (link == NULL) {
        return NO_ID;
    }

    return link->space1;
}

STATUS link_set_space2(Link *link, Id space) {
    if (link == NULL) {
        return ERROR;
    }

    link->space2 = space;
    return OK;
}

Id link_get_space2(Link *link) {
    if (link == NULL) {
        return NO_ID;
    }

    return link->space2;
}

STATUS link_set_open(Link *link, BOOL open) {
    if (link == NULL) {
        return ERROR;
    }

    link->open = open;
    return OK;
}

STATUS link_set_name(Link *link, char *name) {
    if (!link || !name)
        return ERROR;
    if (strcpy(link->name, name) == NULL)
        return ERROR;
    return OK;
}

char* link_get_name(Link *link) {
    if (!link)
        return NULL;
    return link->name;
}

BOOL link_is_open(Link *link) {
    if (link == NULL) {
        return FALSE;
    }

    return link->open;
}

STATUS link_print(Link *link) {
    if (link == NULL) {
        return ERROR;
    }

    fprintf(stdout, "-->Link:\n");
    if (link->id == NO_ID) {
        fprintf(stdout, "--->Id: there is no id\n");
    } else {
        fprintf(stdout, "--->Id: %ld\n", link->id);
    }

    if (link->space1 == NO_ID) {
        fprintf(stdout, "--->Space 1: there is no space 1\n");
    } else {
        fprintf(stdout, "--->Space 1: %ld\n", link->space1);
    }

    if (link->id == NO_ID) {
        fprintf(stdout, "--->Space 2: there is no space 2\n");
    } else {
        fprintf(stdout, "--->Space 2: %ld\n", link->space2);
    }

    if (link->open == TRUE) {
        fprintf(stdout, "--->Link is open\n");
    } else {
        fprintf(stdout, "--->Link is closed\n");
    }

    return OK;
}
