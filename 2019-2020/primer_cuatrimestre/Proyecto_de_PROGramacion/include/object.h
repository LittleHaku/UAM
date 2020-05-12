/**
 * @brief It defines the functions in the object interface.
 *
 * @file object.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 24-09-2019
 * @copyright GNU Public License
 */

#ifndef OBJECT_H
#define OBJECT_H

#include "types.h"

#define MAX_OBJECTS 1000

typedef struct _Object Object;

/**
 * @brief Creates new object
 *
 * object_create Allocates memory for a new object element
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param id the Id for the new object
 * @return new object element address, or NULL in case of failure
 */
Object* object_create(Id id);

/**
 * @brief Frees memory associated with an object
 *
 * object_destroy Frees memory occupied by an object element
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param object the object to be freed
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_destroy(Object *obj);

/**
 * @brief Returns the object's Id
 *
 * object_get_id Returns the Id field from an object
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param object the object to be evaluated
 * @return the object's Id
 */
Id object_get_id(Object *obj);

/**
 * @brief Ghanges the object's name
 *
 * object_set_name Changes the name field to a new one
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param object the object to be updated
 * @param name the new name for the object
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_name(Object *obj, char* name);

/**
 * @brief Returns the object's name
 *
 * object_get_name Returns the name field from an object
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param object the object to be evaluated
 * @return the object's name, NULL in case of failure
 */
const char* object_get_name(Object *obj);

/**
 * @brief Ghanges the object's description
 *
 * object_set_description Changes the description field to a new one
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param obj the Object to be updated
 * @param desc the new description for the object
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_description(Object *obj, char* desc);

/**
 * @brief Returns the object's description
 *
 * object_get_description Returns the description field of an Object
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param obj the object to be evaluated
 * @return the object's description, NULL in case of failure
 */
const char* object_get_description(Object *obj);

/**
 * @brief Prints object information
 *
 * object_print Prints data of the fields of a given object
 *
 * @author Leandro Garcia
 * @date 24-09-2019
 *
 * @param object the object to be printed
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_print(Object *obj);
/**
 * @brief Set movable of the obj
 *
 * object_set_movable Set movable of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be movable
 * @param movable the next movable status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_movable(Object *obj, BOOL movable);
/**
 * @brief Get movable of the obj
 *
 * object_is_movable Get movable of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be movable
 * @return TRUE if the obj is movable FALSE otherwise
 */
BOOL object_is_movable(Object *obj);
/**
 * @brief Set moved of the obj
 *
 * object_set_movable Set moved of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be moved
 * @param moved the next movable status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_moved(Object *obj, BOOL moved);
/**
 * @brief Get movable of the obj
 *
 * object_get_moved Get movable of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be moved
 * @return TRUE if the obj is moved FALSE otherwise
 */
BOOL object_get_moved(Object *obj);
/**
 * @brief Set hidden of the obj
 *
 * object_set_hidden Set hidden of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be hidden
 * @param hidden the next hidden status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_hidden(Object *obj, BOOL hidden);
/**
 * @brief Get hidden of the obj
 *
 * object_get_hidden Get hidden of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be hidden
 * @return TRUE if the obj is hidden FALSE otherwise
 */
BOOL object_get_hidden(Object *obj);
/**
 * @brief Set open of the obj
 *
 * object_set_open Set open as de Id of the link to the object
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be opened
 * @param open the next movable opened
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_open(Object *obj, Id open);
/**
 * @brief Get open of the obj
 *
 * object_get_open get open of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be opened
 * @return the id of the link that the object can open, NO_ID otherwise
 */
Id object_get_open(Object *obj);
/**
 * @brief Set illuminate of the obj
 *
 * object_set_illuminate Set illuminate of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param illuminate the object to be illuminate
 * @param movable the next movable status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_illuminate(Object *obj, BOOL illuminate);
/**
 * @brief Get illuminate of the obj
 *
 * object_can_illuminate Get illuminate of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be illuminate
 * @param movable the next movable status
 * @return TRUE if the obj is illuminate FALSE otherwise
 */
BOOL object_can_illuminate(Object *obj);
/**
 * @brief Set turnedon of the obj
 *
 * object_set_turnedon Set turnedon of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be turnedon
 * @param turnedon the next turnedon status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_turnedon(Object *obj, BOOL turndeon);
/**
 * @brief Get turnedon of the obj
 *
 * object_is_turnedon Get turnedon of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be turnedon
 * @return TRUE if the obj is turnedon FALSE otherwise
 */
BOOL object_is_turnedon(Object *obj);
/**
 * @brief Set full_desc of the obj
 *
 * object_set_full_desc Set full_desc of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be full_desc
 * @param desc the next full_desc status
 * @return OK in case of success, ERROR otherwise
 */
STATUS object_set_full_desc(Object* obj, char* desc);
/**
 * @brief Get full_desc of the obj
 *
 * object_get_full_desc Get full_desc of the obj
 *
 * @author Junco de las Heras Valenzuela
 * @date 29-11-2019
 *
 * @param object the object to be full_desc
 * @return const char* the full desc of the object
 */
const char* object_get_full_desc(Object* obj);


#endif
