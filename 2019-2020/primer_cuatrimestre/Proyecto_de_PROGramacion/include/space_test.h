/**
 * @brief It declares the tests for the space module
 *
 * @file space_test.h
 * @author Leandro Garcia
 * @version 2.0
 * @date 22-11-19
 * @copyright GNU Public License
 */

#ifndef SPACE_TEST_H
#define SPACE_TEST_H

/**
 * @test Prueba la función de creación de un espacio
 * @pre Un identificador como parámetro
 * @post Un puntero no nulo al espacio creado
 */
void test1_space_create();

/**
 * @test Prueba la función de creación de un espacio
 * @pre Un identificador como parámetro
 * @post El identificador introducido
 */
void test2_space_create();

/**
 * @test Prueba la función de cambiar nombre
 * @pre Un nuevo nombre
 * @post Retorno: OK
 */
void test1_space_set_name();

/**
 * @test Prueba la función de cambiar nombre
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_space_set_name();

/**
 * @test Prueba la función de cambiar el norte
 * @pre Un nuevo id para el norte
 * @post Retorno: OK
 */
void test1_space_set_north();

/**
 * @test Prueba la función de cambiar el norte
 * @pre Un nuevo id para el norte y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_north();

/**
 * @test Prueba la función de cambiar el sur
 * @pre Un nuevo id para el sur
 * @post Retorno: OK
 */
void test1_space_set_south();

/**
 * @test Prueba la función de cambiar el sur
 * @pre Un nuevo id para el sur y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_south();

/**
 * @test Prueba la función de cambiar el este
 * @pre Un nuevo id para el este
 * @post Retorno: OK
 */
void test1_space_set_east();

/**
 * @test Prueba la función de cambiar el este
 * @pre Un nuevo id para el este y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_east();

/**
 * @test Prueba la función de cambiar el oeste
 * @pre Un nuevo id para el oeste
 * @post Retorno: OK
 */
void test1_space_set_west();

/**
 * @test Prueba la función de cambiar el oeste
 * @pre Un nuevo id para el oeste y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_west();

/**
 * @test Prueba la función de obtener el nombre
 * @pre Espacio a evaluar
 * @post Recibir nombre del espacio
 */
void test1_space_get_name();

/**
 * @test Prueba la función de obtener el nombre
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_space_get_name();

/**
 * @test Prueba la función de obtener el norte
 * @pre Espacio a evaluar
 * @post Recibir norte del espacio
 */
void test1_space_get_north();

/**
 * @test Prueba la función de obtener el norte
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_north();

/**
 * @test Prueba la función de obtener el sur
 * @pre Espacio a evaluar
 * @post Recibir sur del espacio
 */
void test1_space_get_south();

/**
 * @test Prueba la función de obtener el sur
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_south();

/**
 * @test Prueba la función de obtener el este
 * @pre Espacio a evaluar
 * @post Recibir este del espacio
 */
void test1_space_get_east();

/**
 * @test Prueba la función de obtener el este
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_east();

/**
 * @test Prueba la función de obtener el oeste
 * @pre Espacio a evaluar
 * @post Recibir oeste del espacio
 */
void test1_space_get_west();

/**
 * @test Prueba la función de obtener el oeste
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_west();

/**
 * @test Prueba la función de obtener el id
 * @pre Espacio a evaluar
 * @post Recibir id del espacio
 */
void test1_space_get_id();

/**
 * @test Prueba la función de obtener el id
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_id();

/**
 * @test Prueba la función de añadir objetos
 * @pre Espacio y objeto a añadir
 * @post Recibir OK
 */
void test1_space_add_object();

/**
 * @test Prueba la función de añadir objetos
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_space_add_object();

/**
 * @test Prueba la función de eliminar objetos
 * @pre Espacio y objeto a eliminar
 * @post Recibir OK
 */
void test1_space_del_object();

/**
 * @test Prueba la función de eliminar objetos
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_space_del_object();

/**
 * @test Prueba la función de obtener todos los objetos
 * @pre Espacio a evaluar
 * @post Recibir el array de objetos
 */
void test1_space_get_objects();

/**
 * @test Prueba la función de obtener todos los objetos
 * @pre Espacio a evaluarNULL como parametro
 * @post Recibir NULL
 */
void test2_space_get_objects();

/**
 * @test Prueba la función de obtener el numero de elementos
 * @pre Espacio a evaluar
 * @post Recibir el numero de elementos adecuado
 */
void test1_space_get_nelems();

/**
 * @test Prueba la función de obtener el numero de elementos
 * @pre NULL como parametro
 * @post Recibir -1
 */
void test2_space_get_nelems();

/**
 * @test Prueba la función de cambiar la descripcion grafica
 * @pre Una nueva fila de descripcion grafica
 * @post Retorno: OK
 */
void test1_space_set_gdesc();

/**
 * @test Prueba la función de cambiar la descripcion grafica
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_space_set_gdesc();

/**
 * @test Prueba la función de obtener la descripcion grafica
 * @pre Espacio a evaluar
 * @post Recibir la fila adecuada de la descripcion grafica
 */
void test1_space_get_gdesc();

/**
 * @test Prueba la función de obtener la descripcion grafica
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_space_get_gdesc();

/**
 * @test Prueba la función de comprobar si el espacio tiene un objeto
 * @pre Espacio a evaluar y objeto a buscar
 * @post Recibir una respuesta correcta de la busqueda
 */
void test1_space_has_object();

/**
 * @test Prueba la función de comprobar si el espacio tiene un objeto
 * @pre NULL y objeto a buscar
 * @post Recibir FALSE
 */
void test2_space_has_object();

/**
 * @test Prueba la función de cambiar la descripcion
 * @pre Una nueva descripcion
 * @post Retorno: OK
 */
void test1_space_set_description();

/**
 * @test Prueba la función de cambiar la descripcion
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_space_set_description();

/**
 * @test Prueba la función de obtener la descripcion
 * @pre Espacio a evaluar
 * @post Recibir la descripcion del espacio
 */
void test1_space_get_description();

/**
 * @test Prueba la función de obtener la descripcion
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_space_get_description();

/**
 * @test Prueba la función de imprimir
 * @pre Espacio a imprimir
 * @post Recibir OK
 */
void test1_space_print();

/**
 * @test Prueba la función de imprimir
 * @pre NULL como parametro
 * @post Recibir ERROR
 */
void test2_space_print();

/**
 * @test Prueba la función de cambiar el espacio superior
 * @pre Un nuevo id para el espacio superior
 * @post Retorno: OK
 */
void test1_space_set_up();

/**
 * @test Prueba la función de cambiar el espacio superior
 * @pre Un nuevo id para el espacio superior y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_up();

/**
 * @test Prueba la función de cambiar el espacio inferior
 * @pre Un nuevo id para el espacio inferior
 * @post Retorno: OK
 */
void test1_space_set_down();

/**
 * @test Prueba la función de cambiar el espacio inferior
 * @pre Un nuevo id para el espacio inferior y NULL como espacio
 * @post Retorno: ERROR
 */
void test2_space_set_down();

/**
 * @test Prueba la función de obtener el espacio superior
 * @pre Espacio a evaluar
 * @post Recibir espacio superior del espacio
 */
void test1_space_get_up();

/**
 * @test Prueba la función de obtener el espacio superior
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_up();

/**
 * @test Prueba la función de obtener el espacio inferior
 * @pre Espacio a evaluar
 * @post Recibir espacio inferior del espacio
 */
void test1_space_get_down();

/**
 * @test Prueba la función de obtener el espacio inferior
 * @pre NULL como parametro
 * @post Recibir NO_ID
 */
void test2_space_get_down();

/**
 * @test Prueba la función de cambiar la descripcion completa
 * @pre Una nueva descripcion completa
 * @post Retorno: OK
 */
void test1_space_set_full_desc();

/**
 * @test Prueba la función de cambiar la descripcion completa
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_space_set_full_desc();

/**
 * @test Prueba la función de obtener la descripcion completa
 * @pre Espacio a evaluar
 * @post Recibir la descripcion completa del espacio
 */
void test1_space_get_full_desc();

/**
 * @test Prueba la función de obtener la descripcion completa
 * @pre NULL como parametro
 * @post Recibir NULL
 */
void test2_space_get_full_desc();

/**
 * @test Prueba la función de cambiar el estado de la luz
 * @pre Una nuevo estado para la luz
 * @post Retorno: OK
 */
void test1_space_set_light();

/**
 * @test Prueba la función de cambiar el estado de la luz
 * @pre NULL como parametro
 * @post Retorno: ERROR
 */
void test2_space_set_light();

/**
 * @test Prueba la función de obtener el estado de la luz
 * @pre Espacio a evaluar
 * @post Recibir correctamente si el espacio está iluminado
 */
void test1_space_is_lit();

/**
 * @test Prueba la función de obtener el estado de la luz
 * @pre NULL como parametro
 * @post Retorno: FALSE
 */
void test2_space_is_lit();

#endif
