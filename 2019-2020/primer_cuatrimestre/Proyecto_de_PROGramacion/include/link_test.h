/**
 * @brief It declares the tests for the link module
 *
 * @file link_test.h
 * @author Leandro Garcia
 * @version 2.0
 * @date 19-01-2016
 * @copyright GNU Public License
 */

#ifndef LINK_TEST_H
#define LINK_TEST_H

/**
 * @test Prueba la función de creación de un link
 * @pre Un identificador como parámetro
 * @post Un puntero no nulo al link creado
 */
void test1_link_create();

/**
 * @test Prueba la función de creación de un link
 * @pre Un identificador como parámetro
 * @post El identificador introducido
 */
void test2_link_create();

/**
 * @test Prueba la función de obtener el id
 * @pre Link a evaluar
 * @post Recibir id del link
 */
void test1_link_get_id();

/**
 * @test Prueba la función de obtener el id
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_link_get_id();

/**
 * @test Prueba la función de cambiar nombre
 * @pre Un nuevo nombre
 * @post Retorno: OK
 */
void test1_link_set_name();

/**
 * @test Prueba la función de cambiar nombre
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_link_set_name();

/**
 * @test Prueba la función de obtener el nombre
 * @pre Link a evaluar
 * @post Recibir nombre del link
 */
void test1_link_get_name();

/**
 * @test Prueba la función de obtener el nombre
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_link_get_name();

/**
 * @test Prueba la función de cambiar el espacio 1
 * @pre Link a actualizar y nuevo espacio
 * @post Recibir OK
 */
void test1_link_set_space1();

/**
 * @test Prueba la función de cambiar el espacio 1
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_link_set_space1();

/**
 * @test Prueba la función de obtener el espacio 1
 * @pre link a evaluar
 * @post Recibir el espacio 1
 */
void test1_link_get_space1();

/**
 * @test Prueba la función de obtener el espacio 1
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_link_get_space1();

/**
 * @test Prueba la función de cambiar el espacio 2
 * @pre Link a actualizar y nuevo espacio
 * @post Recibir OK
 */
void test1_link_set_space2();

/**
 * @test Prueba la función de cambiar el espacio 2
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_link_set_space2();

/**
 * @test Prueba la función de obtener el espacio 2
 * @pre link a evaluar
 * @post Recibir el espacio 2
 */
void test1_link_get_space2();

/**
 * @test Prueba la función de obtener el espacio 2
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_link_get_space2();

/**
 * @test Prueba la función de establecer el estado del link
 * @pre link a actualizar y nuevo estado (abierto o cerrado)
 * @post Recibir OK
 */
void test1_link_set_open();

/**
 * @test Prueba la función de establecer el estado del link
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_link_set_open();

/**
 * @test Prueba la función de obtener el estado del link
 * @pre Link a evaluar
 * @post Recibir el estado del link
 */
void test1_link_is_open();

/**
 * @test Prueba la función de obtener el estado del link
 * @pre NULL como parametro
 * @post Recibir FALSE
 */
void test2_link_is_open();

/**
 * @test Prueba la función de imprimir
 * @pre Link a imprimir
 * @post Recibir OK
 */
void test1_link_print();

/**
 * @test Prueba la función de imprimir
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_link_print();


#endif
