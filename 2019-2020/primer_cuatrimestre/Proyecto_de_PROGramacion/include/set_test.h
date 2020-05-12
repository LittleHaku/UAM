/**
 * @brief It declares the tests for the set module
 *
 * @file set_test.h
 * @author Leandro Garcia
 * @version 2.0
 * @date 22-11-19
 * @copyright GNU Public License
 */

#ifndef SET_TEST_H
#define SET_TEST_H

/**
 * @test Prueba la función de creación de un set
 * @pre Llamada a la funcion
 * @post Un puntero no nulo al set creado
 */
void test1_set_create();

/**
 * @test Prueba la función de creación de un set
 * @pre Llamada a la funcion con NULL inicializado
 * @post Un puntero no nulo al set creado
 */
void test2_set_create();

/**
 * @test Prueba la función de obtener el array de datos
 * @pre Set a evaluar
 * @post Recibir el array de datos
 */
void test1_set_get_array();

/**
 * @test Prueba la función de obtener el array de datos
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_set_get_array();

/**
 * @test Prueba la función de obtener el numero de elementos
 * @pre Set a evaluar
 * @post Recibir el tamaño
 */
void test1_set_get_size();

/**
 * @test Prueba la función de obtener el numero de elementos
 * @pre NULL como parametro
 * @post Recibir -1
 */
void test2_set_get_size();

/**
 * @test Prueba la función de añadir elementos
 * @pre Set a actualizar e id a añadir
 * @post Recibir OK
 */
void test1_set_add();

/**
 * @test Prueba la función de añadir elementos
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_set_add();

/**
 * @test Prueba la función de eliminar elementos
 * @pre Set a actualizar y elemento a eliminar
 * @post Recibir OK
 */
void test1_set_del();

/**
 * @test Prueba la función de eliminar elementos
 * @pre Set a actualizar y elemento a eliminar que no se encuentra en el set
 * @post Recibir ERROR
 */
void test2_set_del();

/**
 * @test Prueba la función de comprobar si el set tiene un elemento
 * @pre Set a evaluar y elemento
 * @post Recibir correctamente si el id esta en el set
 */
void test1_set_has_id();

/**
 * @test Prueba la función de comprobar si el set tiene un elemento
 * @pre NULL y objeto a buscar
 * @post Recibir FALSE
 */
void test2_set_has_id();

/**
 * @test Prueba la función de imprimir
 * @pre Espacio a imprimir
 * @post Recibir OK
 */
void test1_set_print();

/**
 * @test Prueba la función de imprimir
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_set_print();

#endif
