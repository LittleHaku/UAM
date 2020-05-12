/**
 * @brief It defines a link
 *
 * @file link.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 03-11-19
 * @copyright GNU Public License
 */

#ifndef LINK_H
#define LINK_H

#include "types.h"

#define MAX_LINKS 16384

typedef struct _Link Link;

/**
 * @brief Creates new link
 *
 * link_create Allocates memory for a new Link element
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param id the Id for the new Link
 * @return new Link element address, or NULL in case of failure
 */
Link* link_create(Id id);

/**
 * @brief Frees memory associated with a Link
 *
 * link_destroy Frees memory occupied by a Link element
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be freed
 */
void link_destroy(Link *link);

/**
 * @brief Returns the link's Id
 *
 * link_get_id Returns the Id field from a Link
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be evaluated
 * @return the Link's Id, NO_ID in case of failure
 */
Id link_get_id(Link *link);

/**
 * @brief Ghanges the link's first space
 *
 * link_set_space1 Changes the space1 field to a new one
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be updated
 * @param space the new Space for the Link's first space
 * @return OK in case of success, ERROR otherwise
 */
STATUS link_set_space1(Link *link, Id space);

/**
 * @brief Returns the link's first space
 *
 * link_get_space1 Returns the space1 field from a Link
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be evaluated
 * @return the link's first space, NO_ID in case of failure
 */
Id link_get_space1(Link *link);

/**
 * @brief Ghanges the link's second space
 *
 * link_set_space2 Changes the space2 field to a new one
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be updated
 * @param space the new Space for the Link's second space
 * @return OK in case of success, ERROR otherwise
 */
STATUS link_set_space2(Link *link, Id space);

/**
 * @brief Returns the link's second space
 *
 * link_get_space2 Returns the space2 field from a Link
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be evaluated
 * @return the link's second space, NO_ID in case of failure
 */
Id link_get_space2(Link *link);

/**
 * @brief Ghanges the link's status
 *
 * link_set_open Changes the open field to a new one
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be updated
 * @param open the new boolean for the Link's open field
 * @return OK in case of success, ERROR otherwise
 */
STATUS link_set_open(Link *link, BOOL open);

/**
 * @brief Set the name to link
 *
 * link_set_name Changes the name field to a new one
 *
 * @author Junco de las Heras Valenzuela
 * @date 08-11-2019
 *
 * @param link the link to set the name
 * @param name the name to be set
 * @return OK if it could set the name, ERROR otherwise
 */
STATUS link_set_name(Link *link, char *name);

/**
 * @brief Get the name to link
 *
 * link_get_name Returns the name field from a Link
 *
 * @author Marta Vaquerizo Núñez
 * @date 08-11-2019
 *
 * @param link the link to get the name
 * @return the name of the link, NULL otherwise
 */
char* link_get_name(Link *link);

/**
 * @brief Returns the Link's status
 *
 * link_is_open Returns the open field from a Link
 *
 * @author Lenadro Garcia
 * @date 03-11-19
 *
 * @param link the Link to ve evaluated
 * @return the Link's open field, FALSE in case of failure
 */
BOOL link_is_open(Link *link);

/**
 * @brief Prints link information
 *
 * link_print Prints data from the fields of a given Link
 *
 * @author Leandro Garcia
 * @date 03-11-2019
 *
 * @param link the Link to be printed
 * @return OK in case of success, ERROR otherwise
 */
STATUS link_print(Link *link);

#endif
