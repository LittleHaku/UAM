/**
 * @brief It declares the tests for the player module
 *
 * @file player_test.h
 * @author Junco de las Heras Valenzuela
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */

#ifndef PLAYER_TEST_H
#define PLAYER_TEST_H

/**
 * @test Prueba la función de creación de un player
 * @pre Un identificador como parámetro
 * @post Un puntero no nulo al objeto creado
 */
void test1_player_create();
/**
 * @test Prueba la función de creación de un player
 * @pre Un identificador como parámetro
 * @post Un puntero nulo al objeto creado
 */
void test2_player_create();
/**
 * @test Prueba la función de set id
 * @pre Un player como parámetro
 * @post retorno de set OK
 */
void test1_player_set_id();
/**
 * @test Prueba la función de set id
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_set_id();
/**
 * @test Prueba la función de get id
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_get_id();
/**
 * @test Prueba la función de get id
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_get_id();
/**
 * @test Prueba la función de set name
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_set_name(); /**
 * @test Prueba la función de set name
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_set_name();
/**
 * @test Prueba la función de get name
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_get_name(); /**
 * @test Prueba la función de get name
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_get_name();
/**
 * @test Prueba la función de set space
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_set_space();
/**
 * @test Prueba la función de set space
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_set_space();
/**
 * @test Prueba la función de get space
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_get_space();
/**
 * @test Prueba la función de get space
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_get_space();
/**
 * @test Prueba la función de insert object
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_insert_object();
/**
 * @test Prueba la función de insert object
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_insert_object();
/**
 * @test Prueba la función de delete object
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_delete_object();
/**
 * @test Prueba la función de delete object
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_delete_object();
/**
 * @test Prueba la función de get objects
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_get_objects(); /**
 * @test Prueba la función de get objects
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_get_objects();
/**
 * @test Prueba la función de has object
 * @pre Un player como parámetro
 * @post retorno de player OK
 */
void test1_player_has_object();
/**
 * @test Prueba la función de has object
 * @pre Un player como parámetro
 * @post retorno de player ERROR
 */
void test2_player_has_object();

#endif
