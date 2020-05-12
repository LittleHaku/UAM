/**
 * @brief It defines the player interface.
 *
 * @file player.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 03-11-2019
 * @copyright GNU Public License
 */

#ifndef PLAYER_H
#define PLAYER_H

#include "types.h"
#include "inventory.h"


/**
  @brief It defines Player structure
 */
typedef struct _Player Player;

/**
 * @brief Creates new player
 *
 * player_create Allocates memory for a new Player element
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param id the Id for the new Player
 * @param max_size the size of the player's inventory
 * @return new Player element address, or NULL in case of failure
 */
Player* player_create(Id id, int max_size);

/**
 * @brief Frees memory associated with a Player
 *
 * player_destroy Frees memory occupied by a Player element
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param player the Player to be freed
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_destroy(Player *player);

/**
 * @brief Ghanges the player's id
 *
 * player_set_id Changes the id field to a new one
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be updated
 * @param id the new id for the Player
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_set_id(Player *player, Id id);

/**
 * @brief Returns the player's Id
 *
 * player_get_id Returns the Id field from a Player
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be evaluated
 * @return the Player's Id
 */
Id player_get_id(Player *player);

/**
 * @brief Ghanges the player's name
 *
 * player_set_name Changes the name field to a new one
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be updated
 * @param name the new name for the Player
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_set_name(Player *player, char *name);

/**
 * @brief Returns the player's name
 *
 * player_get_name Returns the name field from a Player
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be evaluated
 * @return the player's name, NULL in case of failure
 */
const char* player_get_name(Player *player);

/**
 * @brief Changes the Player's space Id
 *
 * player_set_space Changes the space field to a new Id
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be updtaed
 * @param space_location the new Id for the space field
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_set_space(Player *player, Id space_location);
/**
 * @brief Returns the maximum number of objects that player can have
 *
 * player_get_num_max_objects Returns the maximum number of objects that player can have
 *
 * @author Marta Vaquerizo
 * @date 06-12-2019
 *
 * @param player the Player to be evaluated
 * @return the player's maximum number of objects, -1 in case of failure
 */
int player_get_num_max_objects(Player* player);
/**
 * @brief Returns the player's space Id
 *
 * player_get_space Returns the space field from a Player
 *
 * @author Junco de las Heras
 * @date 13-09-2019
 *
 * @param player the Player to be evaluated
 * @return the player's space, NULL in case of failure
 */
Id player_get_space(Player *player);

/**
 * @brief Changes the Player's maximum number of objects it can have
 *
 * player_set_num_max_objects Changes the maximum number of objects the player can have
 *
 * @author Marta Vaquerizo
 * @date 06-12-2019
 *
 * @param player the Player to be updtaed
 * @param num_objects, the number of objects to set
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_set_num_max_objects(Player *player, int num_objects);

/**
 * @brief Adds a new object
 *
 * player_insert_object Adds a new object to the inventory
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param player the Player to be updated
 * @param new_id the new Id for the inventory
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_insert_object(Player *player, Id new_id);

/**
 * @brief Deletes an object from a player's inventory
 *
 * player_delete_object Deletes the object given if the
 * player has it
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param player the Player to be updated
 * @param new_id the id of the object that is going to be deleted
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_delete_object(Player *player, Id new_id);

/**
 * @brief Returns the player's list of objects
 *
 * player_get_objects Returns the objects field from a Player
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param player the Player to be evaluated
 * @return an array with Ids from all player's objects, NULL in case of failure
 */
Id* player_get_objects(Player *player);

/**
 * @brief Searches for a given Id in a player's inventory
 *
 * player_has_object Looks for the given Id in the player's set of objects
 *
 * @author Fabian Gutierrez
 * @date 03-11-2019
 *
 * @param player the Player to be evaluated
 * @param id the Id to be searched
 * @return TRUE if the Id is in the inventory, FALSE otherwise
 */
BOOL player_has_object(Player* player, Id id);

/**
 * @brief Prints player information
 *
 * player_print Prints data of the fields of a given Player
 *
 * @author Junco de las Heras
 * @date 01-11-2019
 *
 * @param player the Player to be printed
 * @return OK in case of success, ERROR otherwise
 */
STATUS player_print(Player *player);

#endif
