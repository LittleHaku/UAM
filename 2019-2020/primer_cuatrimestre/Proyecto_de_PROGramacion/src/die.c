/**
 * @brief It implements the die interface.
 * @file die.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 11-10-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "die.h"

/**
 * @brief The Die structure
 *
 * It stores the different attributes of a Die
 */
struct _Die {
    Id id; /*!<Die's id*/
    int min; /*!<Die's minimum value*/
    int max; /*!<Die's maximum value*/
    int last_roll; /*!<Value of the last roll of the die*/
};

/**
Private functions
 */
int die_rand_num(int inf, int sup); /*Generates random numbers between inf and sup*/

/* Private functions implementation */
int die_rand_num(int inf, int sup) {
    if (inf > sup) {
        return -1;
    }
    return (rand() / (RAND_MAX + 1.))*(sup - inf + 1) + inf;
}

/**
Die interface implementation
 */

Die* die_create(Id id, int min, int max) {
    Die* new_die = NULL;

    if (id == NO_ID || min < 0 || max < min) {
        return NULL;
    }

    new_die = (Die*) malloc(sizeof (Die));
    if (new_die == NULL) {
        return NULL;
    }

    new_die->id = id;
    new_die->min = min;
    new_die->max = max;
    new_die->last_roll = -1;

    srand(time(NULL));

    return new_die;
}

STATUS die_destroy(Die* die) {
    if (die == NULL) {
        return ERROR;
    }

    free(die);
    return OK;
}

STATUS die_roll(Die* die) {
    int num = -1;

    if (die == NULL) {
        return ERROR;
    }

    num = die_rand_num(die->min, die->max);
    if (num < 0) {
        return ERROR;
    }

    die->last_roll = num;
    return OK;
}

STATUS die_print(Die* die) {
    if (die == NULL) {
        return ERROR;
    }

    fprintf(stdout, "--> Die (Id: %ld; Min: %d; Max: %d; Last_roll: %d)\n",
            die->id, die->min, die->max, die->last_roll);
    return OK;
}

int die_get_last_roll(Die* die) {
    if (die == NULL) {
        return -1;
    }

    return die->last_roll;
}
