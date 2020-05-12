/**
 * @brief It defines the game_management interface
 *
 * @file game_management.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 06-12-2019
 * @copyright GNU Public License
 */

#ifndef GAME_MANAGEMENT_H
#define GAME_MANAGEMENT_H

#include "game.h"
#define MAX_SET 100

/**
 * @brief Creates new Game based on a file
 *
 * game_management_load Creates a new Game element and fills its
 * fields based on an input file
 *
 * @author Marta Vaquerizo
 * @date 02-11-2019
 *
 * @param filename the filename of the file with creation instructions
 * @return new Game element address, or NULL in case of failure
 */
STATUS game_management_load(Game* game, char* filename);
/**
 * @brief Saves the current Game
 *
 * game_management_save Saves the current Game
 *
 * @author Marta Vaquerizo
 * @date 06-12-2019
 *
 * @param filename the filename of the file where the game is gonna be saved
 * @param game the game is saved
 * @return OK is it has been saved well, or ERROR in case of failure
 */
STATUS game_management_save(Game *game, char *filename);

#endif
