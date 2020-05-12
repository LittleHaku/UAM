/**
 * @brief It declares the tests for the game module
 *
 * @file game_test.h
 * @author Leandro Garcia, Fabian Gutierrez, Junco de las Heras & Marta Vaquerizo
 * @version 2.0
 * @date 13-12-2019
 * @copyright GNU Public License
 */
#ifndef GAME_TEST_H
#define GAME_TEST_H

/**
 * @test Prueba la función de creación de un juego
 * @pre Nada
 * @post Un puntero no nulo al juego creado
 */
void test_game_create();
/**
 * @test Prueba la función de liberación de memoria de un juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_destroy();
/**
 * @test Prueba la función de liberación de memoria de un juego
 * @pre Un puntero no nulo a un juego
 * @post Un status OK
 */
void test2_game_destroy();
/**
 * @test Prueba la función que determina si un juego ha acabado
 * @pre Un puntero nulo
 * @post Un boolean FALSE
 */
void test_game_is_over();
/**
 * @test Prueba la función que imprime la información sobre un juego
 * @pre Un puntero nulo
 * @post Nada impreso
 */
void test1_game_print_data();
/**
 * @test Prueba la función que imprime la información sobre un juego
 * @pre Un puntero no nulo a un juego
 * @post Un puntero no nulo a un juego y la información del juego por pantalla
 */
void test2_game_print_data();
/**
 * @test Prueba la función que devuelve un puntero al espacio de una id
 * @pre Una id no válida
 * @post Un puntero nulo
 */
void test1_game_get_space();
/**
 * @test Prueba la función que devuelve un puntero al espacio de una id
 * @pre Una id válida
 * @post Un puntero no nulo al espacio
 */
void test2_game_get_space();
/**
 * @test Prueba la función que devuelve un puntero al enlace de una id
 * @pre Una id no válida
 * @post Un puntero nulo
 */
void test1_game_get_link();
/**
 * @test Prueba la función que devuelve un puntero al enlace de una id
 * @pre Una id válida
 * @post Un puntero no nulo al enlace
 */
void test2_game_get_link();
/**
 * @test Prueba la función que devuelve un puntero al jugador del juego
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test1_game_get_player();
/**
 * @test Prueba la función que devuelve un puntero al jugador del juego
 * @pre Una no nulo al juego
 * @post Un puntero no nulo al jugador
 */
void test2_game_get_player();
/**
 * @test Prueba la función que devuelve un puntero al objeto de una id
 * @pre Una id no válida
 * @post Un puntero nulo
 */
void test1_game_get_object();
/**
 * @test Prueba la función que devuelve un puntero al objeto de una id
 * @pre Una id válida
 * @post Un puntero no nulo al objeto
 */
void test2_game_get_object();
/**
 * @test Prueba la función que devuelve un arreglo con los objetos del juego
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test1_game_get_objects();
/**
 * @test Prueba la función que devuelve un arreglo con los objetos del juego
 * @pre Un puntero no nulo al juego
 * @post Un puntero no nulo a los objetos
 */
void test2_game_get_objects();
/**
 * @test Prueba la función que devuelve el último valor del dado
 * @pre Un puntero nulo
 * @post Un entero -1
 */
void test1_game_get_last_roll();
/**
 * @test Prueba la función que devuelve el último valor del dado
 * @pre Un puntero no nulo al juego
 * @post Un entero positivo
 */
void test2_game_get_last_roll();
/**
 * @test Prueba la función que devuelve el último comando introducido
 * @pre Un puntero nulo
 * @post Un T_Command NO_CMD
 */
void test1_game_get_last_command();
/**
 * @test Prueba la función que devuelve el último comando introducido
 * @pre Un puntero no nulo al juego
 * @post Un T_Command no nulo
 */
void test2_game_get_last_command();
/**
 * @test Prueba la función que devuelve la última descripción solicitada
 * @pre Un puntero nulo
 * @post Un puntero a char nulo
 */
void test1_game_get_last_description();
/**
 * @test Prueba la función que devuelve la última descripción solicitada
 * @pre Un puntero no nulo al juego
 * @post Un puntero a char con la última descripción (será vacía)
 */
void test2_game_get_last_description();
/**
 * @test Prueba la función que devuelve añade un espacio al juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_add_space();
/**
 * @test Prueba la función que devuelve añade un espacio al juego
 * @pre Un puntero no nulo a un espacio
 * @post Un juego con 2 espacios
 */
void test2_game_add_space();
/**
 * @test Prueba la función que devuelve añade un enlace al juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_add_link();
/**
 * @test Prueba la función que devuelve añade un enlace al juego
 * @pre Un puntero no nulo a un enlace
 * @post Un juego con 2 enlaces
 */
void test2_game_add_link();
/**
 * @test Prueba la función que devuelve añade un objeto al juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_add_object();
/**
 * @test Prueba la función que devuelve añade un objeto al juego
 * @pre Un puntero no nulo a un objeto
 * @post Un juego con 2 objetos
 */
void test2_game_add_object();
/**
 * @test Prueba la función que devuelve asigna un jugador al juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_set_player();
/**
 * @test Prueba la función que devuelve asigna un jugador al juego
 * @pre Un puntero no nulo al jugador
 * @post Un status OK
 */
void test2_game_set_player();
/**
 * @test Prueba la función que devuelve asigna un dado al juego
 * @pre Un puntero nulo
 * @post Un status ERROR
 */
void test1_game_set_die();
/**
 * @test Prueba la función que devuelve asigna un dado al juego
 * @pre Un puntero no nulo al dado
 * @post Un status OK
 */
void test2_game_set_die();
/**
 * @test Prueba la función que devuelve la id del espacio donde se
 * encuentra un objeto de una id
 * @pre Una id no válida
 * @post Una id NO_ID
 */
void test1_game_find_object();
/**
 * @test Prueba la función que devuelve la id del espacio donde se
 * encuentra un objeto de una id
 * @pre Una id válida
 * @post Una id válida
 */
void test2_game_find_object();
/**
 * @test Prueba la función que devuelve un arreglo con los spaces del juego
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test1_game_get_spaces();
/**
 * @test Prueba la función que devuelve un arreglo con los spaces del juego
 * @pre Un puntero no nulo al juego
 * @post Un puntero no nulo a los objetos
 */
void test2_game_get_spaces();
/**
 * @test Prueba la función que devuelve un arreglo con los links del juego
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test1_game_get_links();
/**
 * @test Prueba la función que devuelve un arreglo con los links del juego
 * @pre Un puntero no nulo al juego
 * @post Un puntero no nulo a los objetos
 */
void test2_game_get_links();
/**
 * @test Prueba la función que devuelve la última entrada del usuario
 * @pre Un puntero no nulo al juego
 * @post La última cadena introducida
 */
void test1_game_get_last_str();
/**
 * @test Prueba la función que devuelve la última entrada del usuario
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test2_game_get_last_str();
/**
 * @test Prueba la función que devuelve la última descripción completa
 * @pre Un puntero no nulo al juego
 * @post La última descripción completa
 */
void test1_game_get_last_full_desc();
/**
 * @test Prueba la función que devuelve la última descripción completa
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test2_game_get_last_full_desc();
/**
 * @test Prueba la función que devuelve el número de espacios
 * @pre Un puntero no nulo al juego
 * @post El número de espacios
 */
void test1_game_get_num_spaces();
/**
 * @test Prueba la función que devuelve el número de espacios
 * @pre Un puntero nulo
 * @post -1
 */
void test2_game_get_num_spaces();
/**
 * @test Prueba la función que devuelve el número de enlaces
 * @pre Un puntero no nulo al juego
 * @post El número de enlaces
 */
void test1_game_get_num_links();
/**
 * @test Prueba la función que devuelve el número de enlaces
 * @pre Un puntero nulo
 * @post -1
 */
void test2_game_get_num_links();
/**
 * @test Prueba la función que devuelve el número de objetos
 * @pre Un puntero no nulo al juego
 * @post El número de objetos
 */
void test1_game_get_num_objects();
/**
 * @test Prueba la función que devuelve el número de objetos
 * @pre Un puntero nulo
 * @post -1
 */
void test2_game_get_num_objects();
/**
 * @test Prueba la función que devuelve si hay luz en un espacio
 * @pre Un puntero no nulo al juego y un espacio
 * @post Respuesta correcta al estado de la luz
 */
void test1_game_space_is_lit();
/**
 * @test Prueba la función que devuelve si hay luz en un espacio
 * @pre Dos punteros nulos
 * @post FALSE
 */
void test2_game_space_is_lit();
/**
 * @test Prueba la función que devuelve si un objeto está oculto
 * @pre Un puntero no nulo al juego y un objeto
 * @post Respuesta correcta al estado del objeto
 */
void test1_object_is_hidden();
/**
 * @test Prueba la función que devuelve si un objeto está oculto
 * @pre Dos punteros nulos
 * @post FALSE
 */
void test2_object_is_hidden();
/**
 * @test Prueba la función que actualiza el último cambio
 * @pre Un puntero no nulo al juego y un nuevo cambio
 * @post OK
 */
void test1_game_set_last_change();
/**
 * @test Prueba la función que actualiza el último cambio
 * @pre Un puntero nulo
 * @post ERROR
 */
void test2_game_set_last_change();
/**
 * @test Prueba la función que devuelve el último cambio
 * @pre Un puntero no nulo al juego
 * @post El último cambio sobre el juego
 */
void test1_game_get_last_change();
/**
 * @test Prueba la función que devuelve el último cambio
 * @pre Un puntero nulo
 * @post Un puntero nulo
 */
void test2_game_get_last_change();

#endif
