/**
 * @brief It defines a space
 *
 * @file space.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 18-10-2019
 * @copyright GNU Public License
 */

#ifndef SPACE_H
#define SPACE_H

#include "set.h"

typedef struct _Space Space;

#define MAX_SPACES 16384
#define MAX_COL 31
#define MAX_ROW 7

/**
 * @brief Creates new space
 *
 * space_create Allocates memory for a new Space element
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param id the Id for the new Space
 * @return new Space element address, or NULL in case of failure
 */
Space* space_create(Id id);

/**
 * @brief Frees memory associated with a Space
 *
 * space_destroy Frees memory occupied by a Space element
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the Space to be freed
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_destroy(Space* space);

/**
 * @brief Returns the space's Id
 *
 * space_get_id Returns the Id field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the Space to be evaluated
 * @return the space's Id
 */
Id space_get_id(Space* space);

/**
 * @brief Changes the space's name
 *
 * space_set_name Changes the name field to a new one
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the Space to be updated
 * @param name the new name for the Space
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_name(Space* space, char* name);

/**
 * @brief Returns the space's name
 *
 * space_get_name Returns the name field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the space to be evaluated
 * @return the space's name, NULL in case of failure
 */
const char* space_get_name(Space* space);

/**
 * @brief Changes the space's north
 *
 * space_set_north Changes the north field to a new one
 *
 * @author Fabian Gutierrez
 * @date 04-10-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the north field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_north(Space* space, Id id);

/**
 * @brief Returns the space's north
 *
 * space_get_north Returns the north field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the space to be evaluated
 * @return the space's north, NO_ID in case of failure
 */
Id space_get_north(Space* space);

/**
 * @brief Changes the space's south
 *
 * space_set_south Changes the south field to a new one
 *
 * @author Fabian Gutierrez
 * @date 04-10-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the south field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_south(Space* space, Id id);

/**
 * @brief Returns the space's south
 *
 * space_get_south Returns the south field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the space to be evaluated
 * @return the space's south, NO_ID in case of failure
 */
Id space_get_south(Space* space);

/**
 * @brief Changes the space's east
 *
 * space_set_east Changes the east field to a new one
 *
 * @author Fabian Gutierrez
 * @date 04-10-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the east field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_east(Space* space, Id id);

/**
 * @brief Returns the space's east
 *
 * space_get_east Returns the east field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the space to be evaluated
 * @return the space's east, NO_ID in case of failure
 */
Id space_get_east(Space* space);

/**
 * @brief Changes the space's west
 *
 * space_set_west Changes the west field to a new one
 *
 * @author Fabian Gutierrez
 * @date 04-10-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the west field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_west(Space* space, Id id);

/**
 * @brief Returns the space's west
 *
 * space_get_west Returns the west field from a Space
 *
 * @author Profesores PPROG
 * @date 13-01-2015
 *
 * @param space the space to be evaluated
 * @return the space's west, NO_ID in case of failure
 */
Id space_get_west(Space* space);

/**
 * @brief Changes the space's up
 *
 * space_set_up Changes the up field to a new one
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the up field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_up(Space* space, Id id);

/**
 * @brief Returns the space's up
 *
 * space_get_up Returns the up field from a Space
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the space to be evaluated
 * @return the space's up, NO_ID in case of failure
 */
Id space_get_up(Space *space);

/**
 * @brief Changes the space's down
 *
 * space_set_down Changes the down field to a new one
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the Space to be updated
 * @param id the new Id for the down field
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_down(Space* space, Id id);

/**
 * @brief Returns the space's down
 *
 * space_get_down Returns the down field from a Space
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the space to be evaluated
 * @return the space's down, NO_ID in case of failure
 */
Id space_get_down(Space *space);

/**
 * @brief Adds a new object
 *
 * space_add_object Adds a new object to the objects set
 *
 * @author Fabian Gutierrez
 * @date 11-10-2019
 *
 * @param space the Space to be updated
 * @param object_id the new Id for the objects set
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_add_object(Space* space, Id object_id);

/**
 * @brief Deletes an object from a space
 *
 * space_del_object Deletes the object given if it exists
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the Space to be updated
 * @param id the id of the object that is going to be deleted
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_del_object(Space* space, Id id);

/**
 * @brief Returns the space's list of objects
 *
 * space_get_objects Returns the objects field from a Space
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the space to be evaluated
 * @return an array with Ids from all space's objects, NULL in case of failure
 */
Id* space_get_objects(Space* space);

/**
 * @brief Returns the space's number of objects
 *
 * space_get_nelems Returns the number of elements in space's set of objects
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the space to be evaluated
 * @return the space's number of objects, -1 in case of failure
 */
int space_get_nelems(Space* space);

/**
 * @brief Changes the space's graphic description
 *
 * space_set_gdesc Changes a row ofthe gdesc field to a new one
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the Space to be updated
 * @param gdesc the new graphic description row for the space
 * @param row the row to update
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_gdesc(Space* space, char* gdesc, int row);

/**
 * @brief Returns the space's graphic description
 *
 * space_get_gdesc Returns a row from the gdesc field of a Space
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the space to be evaluated
 * @param row the row's number
 * @return a row from the space's graphic description, NULL in case of failure
 */
char* space_get_gdesc(Space* space, int row);

/**
 * @brief Searches a given Id in a Space
 *
 * space_has_object Searches the given Id in the space's set of objects
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the Space to be evaluated
 * @param id the Id to be searched
 * @return TRUE if the Id is in the space, FALSE otherwise
 */
BOOL space_has_object(Space* space, Id id);

/**
 * @brief Changes the space's description
 *
 * space_set_description Changes the description field to a new one
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param space the Space to be updated
 * @param desc the new description for the space
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_description(Space* space, char* desc);

/**
 * @brief Returns the space's description
 *
 * space_get_description Returns the full_desc field of a Space
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param space the space to be evaluated
 * @return the space's description, NULL in case of failure
 */
const char* space_get_description(Space* space);

/**
 * @brief Changes the space's full description
 *
 * space_set_full_desc Changes the full_desc field to a new one
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the Space to be updated
 * @param desc the new full description for the space
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_full_desc(Space* space, char* desc);

/**
 * @brief Returns the space's full description
 *
 * space_get_full_desc Returns the description field of a Space
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the space to be evaluated
 * @return the space's full description, NULL in case of failure
 */
const char* space_get_full_desc(Space* space);

/**
 * @brief Changes the space's light status
 *
 * space_set_full_desc Changes the light field to a new one
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the Space to be updated
 * @param desc the new full light status for the space
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_set_light(Space *space, BOOL light);

/**
 * @brief Returns the space's light status
 *
 * space_is_lit Returns the light field of a Space
 *
 * @author Leandro Garcia
 * @date 28-11-2019
 *
 * @param space the space to be evaluated
 * @return the space's light status, NULL in case of failure
 */
BOOL space_is_lit(Space *space);

/**
 * @brief Prints space information
 *
 * space_print Prints data of the fields of a given Space
 *
 * @author Leandro Garcia
 * @date 11-10-2019
 *
 * @param space the Space to be printed
 * @return OK in case of success, ERROR otherwise
 */
STATUS space_print(Space* space);

#endif
