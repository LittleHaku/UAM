/**
 * @brief It implements the game rules interface
 *
 * @file game_rules.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 12-12-2019
 * @copyright GNU Public License
 */

#ifndef GAME_RULES_H
#define GAME_RULES_H

#include "game.h"

typedef struct _grules grules;

/**
 * @brief Returns the Id of the objects that allows the player to see every other object
 * @author Leandro Garcia
 * @date 12-12-2019
 */
 #define SHOW_OBJECT 18     /*Id of an objects that reveals the other (while in hand)*/

/**
 * @brief Creates a _grules element
 *
 * game_rules_ini Creates a _grules element, allocating memory for it.
 *
 * @author Junco de las Heras Valenzuela
 * @date 12-12-2019
 *
 * @param game the game to create the game rules for
 * @return a pointer to _grules struct in case of success, NULL otherwise
 */
grules *game_rules_ini(Game *g);

/**
 * @brief Destroy a _grules element
 *
 * game_rules_destroy Free memory for a g_rules element.
 *
 * @author Leandro Garcia
 * @date 12-12-2019
 *
 * @param gr the _grules to free the memory
 * @return void, it doesn't return anything
 */
void game_rules_destroy(grules *gr);

/**
 * @brief It does a random action
 *
 * game_rules_random_action Does a random action in the game, it calls to random function with
 *    random parameter (as objects, spaces, links)
 *
 * @author Junco de las Heras Valenzuela
 * @date 12-12-2019
 *
 * @param gr the _grules to do the random action
 * @return void, it doesn't return anything
 */
void game_rules_random_action(grules *gr);


#endif
