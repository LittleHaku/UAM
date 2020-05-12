/**
 * @brief It implements the command interpreter
 *
 * @file command.c
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 3.0
 * @date 29-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <strings.h>
#include "command.h"

#define CMD_LENGHT 30
#define N_CMD 19

char *cmd_to_str[N_CMD] = {"No command", "Unknown", "exit", "next", "back", "take", "drop", "roll", "left", "right", "up", "down", "move", "inspect", "turnon", "turnoff", "load", "save", "open"};
char *short_cmd_to_str[N_CMD] = {"", "", "e", "n", "b", "t", "dr", "rl", "l", "r", "u", "d", "m", "i", "ton", "toff", "ld", "s", "o"};

T_Command command_get_user_input() {
    T_Command cmd = NO_CMD;
    char input[CMD_LENGHT] = "";
    int i = UNKNOWN - NO_CMD + 1;

    if (scanf("%s", input) > 0) {
        cmd = UNKNOWN;
        while (cmd == UNKNOWN && i < N_CMD) {
            if (!strcasecmp(input, short_cmd_to_str[i]) || !strcasecmp(input, cmd_to_str[i])) {
                cmd = i + NO_CMD;
            } else {
                i++;
            }
        }
    }
    return cmd;
}
