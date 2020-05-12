/**
 * @brief It declares the tests for the object module
 *
 * @file object_test.h
 * @author Leandro Garcia & Junco de las Heras Valenzuela
 * @version 3.0
 * @date 22-11-19
 * @copyright GNU Public License
 */

#ifndef OBJECT_TEST_H
#define OBJECT_TEST_H

/**
 * @test Prueba la función de creación de un objeto
 * @pre Un identificador como parámetro
 * @post Un puntero no nulo al objeto creado
 */
void test1_object_create();

/**
 * @test Prueba la función de creación de un objeto
 * @pre NO_ID como parametro
 * @post Recibir NULL
 */
void test2_object_create();

/**
 * @test Prueba la función de obtener el id
 * @pre Objeto a evaluar
 * @post Recibir id del objeto
 */
void test1_object_get_id();

/**
 * @test Prueba la función de obtener el id
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_object_get_id();

/**
 * @test Prueba la función de cambiar nombre
 * @pre Un nuevo nombre
 * @post Retorno: OK
 */
void test1_object_set_name();

/**
 * @test Prueba la función de cambiar nombre
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_object_set_name();

/**
 * @test Prueba la función de obtener el nombre
 * @pre Objeto a evaluar
 * @post Recibir nombre del objeto
 */
void test1_object_get_name();

/**
 * @test Prueba la función de obtener el nombre
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_object_get_name();

/**
 * @test Prueba la función de cambiar la descripcion
 * @pre Una nueva descripcion
 * @post Retorno: OK
 */
void test1_object_set_description();

/**
 * @test Prueba la función de cambiar la descripcion
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_object_set_description();

/**
 * @test Prueba la función de obtener la descripcion
 * @pre Objeto a evaluar
 * @post Recibir la descripcion del objeto
 */
void test1_object_get_description();

/**
 * @test Prueba la función de obtener la descripcion
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_object_get_description();

/**
 * @test Prueba la función de imprimir
 * @pre Objeto a imprimir
 * @post Recibir OK
 */
void test1_object_print();

/**
 * @test Prueba la función de imprimir
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_object_print();

void test1_object_set_movable();
void test2_object_set_movable();
void test1_object_is_movable();
void test2_object_is_movable();
void test1_object_set_moved();
void test2_object_set_moved();
void test1_object_get_moved();
void test2_object_get_moved();
void test1_object_set_hidden();
void test2_object_get_hidden();
void test1_object_set_open();
void test2_object_set_open();
void test1_object_get_open();
void test2_object_get_open();
void test1_object_set_illuminate();
void test2_object_set_illuminate();
void test1_object_can_illuminate();
void test2_object_can_illuminate();
void test1_object_set_turnedon();
void test2_object_set_turnedon();
void test1_object_is_turnedon();
void test2_object_is_turnedon();
void test1_object_set_full_desc();
void test2_object_set_full_desc();
void test1_object_get_full_desc();
void test2_object_get_full_desc();


#endif
