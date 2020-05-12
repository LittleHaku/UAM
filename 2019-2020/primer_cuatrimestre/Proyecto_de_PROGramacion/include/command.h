/**
 * @brief It defines the command interpreter
 *
 * @file command.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 3.0
 * @date 29-11-2019
 * @copyright GNU Public License
 */

#ifndef COMMAND_H
#define COMMAND_H

/**
 * @brief List of possible commands
 */
typedef enum enum_Command {
    NO_CMD = -1, UNKNOWN, EXIT, NEXT, BACK, TAKE, DROP, ROLL, LEFT, RIGHT, UP, DOWN, MOVE, INSPECT, TURNON, TURNOFF, LOAD, SAVE, OPEN
} T_Command;

/**
 * @brief Gets user's command
 *
 * command_get_user_input Gets user's input and translates it into a
 * command
 *
 * @author Fabian Gutierrez
 * @date 24-10-2019
 *
 * @return command input by user
 */
T_Command command_get_user_input();

#endif
