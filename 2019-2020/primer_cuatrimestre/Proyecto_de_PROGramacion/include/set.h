/**
 * @brief It defines the functions in the set interface.
 *
 * @file set.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 11-10-2019
 * @copyright GNU Public License
 */

#ifndef SET_H_
#define SET_H_

#include "types.h"

#define MAX_SET 100

typedef struct _Set Set;

/**
* @brief Creates new set
*
* set_create Allocates memory for a new Set element
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @return new Set element address, or NULL in case of failure
*/
Set* set_create ();

/**
* @brief Frees memory associated with a Set
*
* set_destroy Frees memory occupied by a Set element
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be freed
* @return OK in case of success, ERROR otherwise
*/
STATUS set_destroy(Set *set);

/**
* @brief Returns Set's array of Ids
*
* set_get_array Returns all the ids in a set
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be evaluated
* @return the set's list of ids, NULL in case of failure
*/
Id* set_get_array(Set *set);

/**
* @brief Returns the Set's number of elements
*
* set_get_id Returns the nelems field from a Set
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be evaluated
* @return the set's number of elements, -1 in case of failure
*/
int set_get_size(Set *set);

/**
* @brief Adds a new Id to a Set
*
* set_add Adds a new Id to a set and increases its number of elements
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be updated
* @param id the new id to insert
* @return OK in case of success, ERROR otherwise
*/
STATUS set_add(Set *set, Id id);


/**
* @brief Deletes an Id from a set
*
* set_del Deletes an Id in a set if it exists
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be updated
* @param id the id to delete
* @return OK in case of success, ERROR otherwise
*/
STATUS set_del(Set *set, Id id);

/**
* @brief Searches a given Id in a Set
*
* set_has_id Searches the given Id in the set's list of elements
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be evaluated
* @param id the Id to be searched
* @return TRUE if the Id is in the set, FALSE otherwise
*/
BOOL set_has_id(Set *set, Id id);

/**
* @brief Prints set information
*
* set_print Prints data from the fields of a given set
*
* @author Leandro Garcia
* @date 11-10-2019
*
* @param set the Set to be printed
* @return OK in case of success, ERROR otherwise
*/
STATUS set_print(Set *set);

#endif
