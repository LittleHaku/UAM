/**
 * @brief It declares the tests for the inventory module
 *
 * @file inventory_test.h
 * @author Junco de las Heras Valenzuela
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */

#ifndef INVENTORY_TEST_H
#define INVENTORY_TEST_H

/**
 * @test Prueba la función de creación de un inventory
 * @pre Un identificador como parámetro
 * @post Un puntero no nulo al objeto creado
 */
void test1_inventory_create();
/**
 * @test Prueba la función de creación de un inventory
 * @pre Un identificador como parámetro
 * @post Un puntero nulo al objeto creado
 */
void test2_inventory_create();
/**
 * @test Prueba la función de get max size
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_get_max_size();
/**
 * @test Prueba la función de get max size
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_get_max_size();
/**
 * @test Prueba la función de set max size
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_set_max_size();
/**
 * @test Prueba la función de set max size
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_set_max_size();
/**
 * @test Prueba la función de get size
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_get_size();
/**
 * @test Prueba la función de get size
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_get_size();
/**
 * @test Prueba la función de get ids
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_get_ids();
/**
 * @test Prueba la función de get ids
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_get_ids();
/**
 * @test Prueba la función de has id
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_has_id();
/**
 * @test Prueba la función de has id
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_has_id();
/**
 * @test Prueba la función de insert
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_insert();
/**
 * @test Prueba la función de insert
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_insert();
/**
 * @test Prueba la función de delete
 * @pre Un inventory como parámetro
 * @post retorno de inventory OK
 */
void test1_inventory_delete();
/**
 * @test Prueba la función de delete
 * @pre Un inventory como parámetro
 * @post retorno de inventory ERROR
 */
void test2_inventory_delete();

#endif
