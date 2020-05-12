/**
 * @brief It declares the tests for the graphic_engine module
 *
 * @file graphic_engine_test.h
 * @author Fabian Gutierrez
 * @version 2.0
 * @date 15-11-2019
 * @copyright GNU Public License
 */
#ifndef GRAPHIC_ENGINE_TEST_H
#define GRAPHIC_ENGINE_TEST_H

/**
 * @test Prueba la función de creación de un motor gráfico
 * @pre Nada
 * @post Un puntero no nulo al motor creado
 */
void test_graphic_engine_create(Graphic_engine *ge);
/**
 * @test Prueba la función de pintar un juego
 * @pre Un puntero a Game nulo como parámetro
 * @post Un puntero no nulo al motor creado y ningún dibujo
 */
void test1_graphic_engine_paint_game(Graphic_engine *ge);
/**
 * @test Prueba la función de pintar un juego
 * @pre Un puntero a Game cargado desde fichero como parámetro
 * @post Un puntero no nulo al motor creado y el dibujo del juego
 */
void test2_graphic_engine_paint_game(Graphic_engine *ge);
/**
 * @test Prueba la función de imprimir en fichero el desarrollo
 * de un juego
 * @pre Un puntero a FILE nulo como parámetro
 * @post Ningún fichero de salida
 */
void test1_graphic_engine_print_file(Graphic_engine *ge);
/**
 * @test Prueba la función de imprimir en fichero el desarrollo
 * de un juego
 * @pre Punteros a Game y a FILE no nulos como parámetro
 * @post Un fichero de salida con una instrucción
 */
void test2_graphic_engine_print_file(Graphic_engine *ge);

#endif
