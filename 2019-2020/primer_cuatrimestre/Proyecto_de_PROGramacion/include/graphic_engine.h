/**
 * @brief It defines a textual graphic engine
 *
 * @file graphic_engine.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 1.0
 * @date 22-11-2019
 * @copyright GNU Public License
 */

#ifndef __GRAPHIC_ENGINE__
#define __GRAPHIC_ENGINE__

#include "game.h"

typedef struct _Graphic_engine Graphic_engine;

/**
 * @brief Creates new graphic engine
 *
 * graphic_engine_create Allocates memory for a new Graphic_engine element
 *
 * @author Profesores PPROG
 * @date 18-01-2017
 *
 * @return new Graphic_engine element address, or NULL in case of failure
 */
Graphic_engine* graphic_engine_create();

/**
 * @brief Frees memory associated with a graphic engine
 *
 * graphic_engine_destroy Frees the memory occupied by a Graphic_engine element
 *
 * @author Profesores PPROG
 * @date 18-01-2017
 *
 * @param ge the graphic engine to be destroyed
 */
void graphic_engine_destroy(Graphic_engine *ge);

/**
 * @brief Paints current state of the game
 *
 * graphic_engine_paint_game Paints on the screen the different areas
 * according to the current state of the game
 *
 * @author Fabian Gutierrez
 * @date 30-10-2019
 *
 * @param ge the graphic engine that will paint on screen
 * @param game the game to be painted
 * @param st the status of the last command
 */
void graphic_engine_paint_game(Graphic_engine *ge, Game *game, STATUS st);

/**
 * @brief Paints current state of the game in the file f
 *
 * graphic_engine_print_file Prints on the file the different results of the commands
 * according to the current state of the game
 *
 * @author Junco de las Heras Valenzuela
 * @date 21-11-2019
 *
 * @param st the status of the last command
 * @param game the game to be painted
 * @param f the output stream for the game log
 */
void graphic_engine_print_file(STATUS st, Game *game, FILE *f);

void graphic_engine_write_command(Graphic_engine *ge, char *str); /*Not implemented*/

#endif
