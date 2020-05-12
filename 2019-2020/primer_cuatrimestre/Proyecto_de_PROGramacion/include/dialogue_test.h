/**
 * @brief It declares the tests for the dialogue module
 *
 * @file dialogue_test.h
 * @author Fabian Gutierrez
 * @version 2.0
 * @date 02-12-2019
 * @copyright GNU Public License
 */

#ifndef DIALOGUE_TEST_H
#define DIALOGUE_TEST_H

/**
 * @test Prueba la función de feedback
 * @pre Juego nulo
 * @post Una cadena nula
 */
void test1_dialogue_get_feedback();
/**
 * @test Prueba la función de feedback
 * @pre Juego no nulo, actualizado con UNKNOWN
 * @post "This is not a valid action. Try again."
 */
void test2_dialogue_get_feedback();
/**
 * @test Prueba la función de feedback
 * @pre Juego no nulo, actualizado con EXIT
 * @post "See you later!"
 */
void test3_dialogue_get_feedback();

#endif
