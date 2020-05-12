/**
 * @brief It declares the tests for the game_management module
 *
 * @file game_management_test.h
 * @author Marta Vaquerizo
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */
#ifndef GAME_MANAGEMENT_TEST_H
#define GAME_MANAGEMENT_H

/**
 * @test Prueba la función de creación de game a partir de un fichero
 * @pre Se pasa un juego nulo, y se carga.
 * @post Que la funcion ha dado ERROR
 */
void test1_game_management_load();

/**
 * @test Prueba la función de creación de game a partir de un fichero
 * @pre Se pasa un fichero con dos espacios y dos links
 * @post Que el número de spaces que tiene el game es 2
 */
void test2_game_management_load();

/**
 * @test Prueba la función de guardar una partida en un fichero
 * @pre Se crea game con un fichero vacío, se carga, y se guarda
 * @post Que la funcion ha dado ERROR
 */
void test1_game_management_save();

/**
 * @test Prueba la función de guardar una partida en un fichero
 * @pre Se crea game con un fichero con dos espacios y dos links, y se guardan en otro fichero
 * @post Que el número de spaces que tiene el game es 2
 */
void test2_game_management_save();

#endif
