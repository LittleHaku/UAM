/**
 * @brief It defines the dialogue/feedback interface
 * @file dialogue.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 3.0
 * @date 29-11-2019
 * @copyright GNU Public License
 */

#ifndef DIALOGUE_H
#define DIALOGUE_H

#include "game.h"

/**
 * @brief Allocates memory and returns feedback related to the last command
 *
 * dialoge_get_feedback Allocates memory for a feedback message according to
 * the last command input by the user and its success (or failure)
 *
 * @author Fabian Gutierrez
 * @date 29-11-2019
 *
 * @param game the game to be evaluated
 * @param st the status of the last command
 *
 * @return new feedback string's address
 */
char* dialogue_get_feedback(Game* game, STATUS st);

#endif
