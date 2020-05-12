/**
 * @brief It implements the dialogue/feedback interface
 * @file dialogue.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 3.0
 * @date 06-12-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dialogue.h"
#include "command.h"

/**
Private functions
 */
int dialogue_check_couple_repetition(T_Command cmd, char* last_str, const char *new_str); /*Checks repetition for binary commands*/

/**
Dialogue interface implementation
 */
char* dialogue_get_feedback(Game *game, STATUS st) {
    static int count = 0;
    static T_Command last_cmd = NO_CMD;
    static char last_str[WORD_SIZE] = "";
    int def_flag = 0;
    T_Command cmd = NO_CMD;
    char *feedback = NULL;
    const char *str = NULL, *desc = NULL;

    if (game == NULL) {
        return NULL;
    }
    if ((feedback = (char*) malloc(WORD_SIZE * sizeof (char))) == NULL) {
        return NULL;
    }
    feedback[0] = '\0';
    cmd = game_get_last_command(game);
    str = game_get_last_str(game);

#ifdef DEBUG
    printf("Repeticiones: %d\n", count);
#endif
    if (cmd != UNKNOWN && last_cmd == cmd && st == ERROR && dialogue_check_couple_repetition(cmd, last_str, str)) {
        if (++count > 1) {
            sprintf(feedback, "You've tried this before %d times without success. Try something else.", count);
            return feedback;
        }
    } else {
        count = 0;
    }
    last_cmd = cmd;
    strncpy(last_str, str, WORD_SIZE - 1);

    switch (cmd) {
        case UNKNOWN:
            sprintf(feedback, "This is not a valid action. Try again.");
            break;

        case EXIT:
            sprintf(feedback, "See you later!");
            break;

        case TAKE:
            if (st == OK) {
                sprintf(feedback, "You've taken a(n) %s.", str);
            } else {
                sprintf(feedback, "You can't take that %s (hint: Is it even here? Is it movable? Are your hands full?).", str);
            }
            break;

        case DROP:
            if (st == OK) {
                sprintf(feedback, "You've dropped a(n) %s.", str);
            } else {
                sprintf(feedback, "You can't drop that %s.", str);
            }
            break;

        case ROLL:
            if (st == OK) {
                sprintf(feedback, "You've rolled the die. See if you got lucky.");
            } else {
                sprintf(feedback, "You can't roll the die.");
            }
            break;

        case INSPECT:
            if (st == OK) {
                sprintf(feedback, "You've taken a closer look to the %s: ", str);
                desc = game_get_last_full_desc(game);
                if (desc != NULL) {
                    sprintf(feedback + strlen(feedback), "%s", desc);
                } else {
                    sprintf(feedback + strlen(feedback), "You can't see, its pitch black.");
                }
            } else {
                sprintf(feedback, "You can't inspect the %s (currently).", str);
            }
            break;

        case OPEN:
            if (st == OK) {
                sprintf(feedback, "You've opened the %s.", str);
            } else {
                sprintf(feedback, "You can't open the %s.", str);
            }
            break;

        case TURNON:
            if (st == OK) {
                sprintf(feedback, "You've turned on the %s.", str);
            } else {
                sprintf(feedback, "You can't turn on the %s.", str);
            }
            break;

        case TURNOFF:
            if (st == OK) {
                sprintf(feedback, "You've turned off the %s.", str);
            } else {
                sprintf(feedback, "You can't turn off the %s.", str);
            }
            break;

            /*Movement commands*/
        case BACK:
        case NEXT:
        case RIGHT:
        case LEFT:
        case UP:
        case DOWN:
        case MOVE:
            if (cmd == MOVE && str == NULL) {
                return NULL;
            }

            if (st == OK) {
                sprintf(feedback, "You've moved ");
            } else {
                sprintf(feedback, "You can't move ");
            }
            /*Printing movement direction*/
            if (cmd == BACK || !strcmp(str, "north") || !strcmp(str, "n")) {
                sprintf(feedback + strlen(feedback), "north.");
            }
            if (cmd == NEXT || !strcmp(str, "south") || !strcmp(str, "s")) {
                sprintf(feedback + strlen(feedback), "south.");
            }
            if (cmd == RIGHT || !strcmp(str, "east") || !strcmp(str, "e")) {
                sprintf(feedback + strlen(feedback), "east.");
            }
            if (cmd == LEFT || !strcmp(str, "west") || !strcmp(str, "w")) {
                sprintf(feedback + strlen(feedback), "west.");
            }
            if (cmd == UP || !strcmp(str, "up") || !strcmp(str, "u")) {
                sprintf(feedback + strlen(feedback), "up.");
            }
            if (cmd == DOWN || !strcmp(str, "down") || !strcmp(str, "d")) {
                sprintf(feedback + strlen(feedback), "down.");
            }
            /*Destination description*/
            if (st == OK) {
                desc = game_get_last_description(game);
                if (desc != NULL && desc[0] != '\0') {
                    sprintf(feedback + strlen(feedback), " Now you are in %s.", desc);
                } else {
                    sprintf(feedback + strlen(feedback), " Now you are in... You can't see, its pitch black.");
                }
            }
            break;

        case SAVE:
            if (st == OK) {
                sprintf(feedback, "Game saved successfully at %s.", str);
            } else {
                sprintf(feedback, "Game save failed");
            }
            break;
        case LOAD:
            if (st == OK) {
                sprintf(feedback, "Game loaded successfully from %s.", str);
            } else {
                sprintf(feedback, "Game load failed (hint: Does the file exist?)");
            }
            break;

        default:
            def_flag = 1;
            break;
    }

    if (def_flag) {
        free(feedback);
        return NULL;
    }

    return feedback;
}

/* Private functions implementation */
int dialogue_check_couple_repetition(T_Command cmd, char* last_str, const char *new_str) {
    if (cmd != TAKE && cmd != DROP && cmd != INSPECT && cmd != MOVE) { /*Not one of the couple commands*/
        return 1;
    }
    if (last_str[0] == '\0') { /*Not called before*/
        return 0;
    }
    return !strcmp(last_str, new_str);
}
