/**
 * @brief It defines the functions in the die interface.
 *
 * @file die.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 11-10-2019
 * @copyright GNU Public License
 */

#ifndef DIE_H
#define DIE_H

#include "types.h"

typedef struct _Die Die;

/**
 * @brief Creates new die
 *
 * die_create Allocates memory for a new die element
 *
 * @author Marta Vaquerizo
 * @date 10-10-2019
 *
 * @param id the die's id
 * @param min the minimum value of a roll
 * @param max the maximum value of a roll
 * @return new die element address, or NULL in case of failure
 */
Die* die_create(Id id, int min, int max);

/**
 * @brief Frees memory associated with a die
 *
 * die_destroy Frees memory occupied by a die element
 *
 * @author Fabian Gutierrez
 * @date 11-10-2019
 *
 * @param die the die to be freed
 * @return OK in case of success, ERROR otherwise
 */
STATUS die_destroy(Die* die);

/**
 * @brief Rolls the die
 *
 * die_roll Selects one of the possible values of the die at random
 *
 * @author Fabian Gutierrez
 * @date 11-10-2019
 *
 * @param die the die to be rolled
 * @return OK in case of success, ERROR otherwise
 */
STATUS die_roll(Die* die);

/**
 * @brief Prints die information
 *
 * die_print Prints data of the fields of a given die
 *
 * @author Marta Vaquerizo
 * @date 11-10-2019
 *
 * @param die the die to be printed
 * @return OK in case of success, ERROR otherwise
 */
STATUS die_print(Die* die);

/**
 * @brief Retrieves the last roll of a die
 *
 * die_get_last_roll Returns the last value obtained by rolling the die
 *
 * @author Fabian Gutierrez
 * @date 11-10-2019
 *
 * @param game the game to be evaluated
 * @return the last roll value, a negative number in case of error
 */
int die_get_last_roll(Die* die);
#endif
