/**
 * @brief It declares the tests for the die module
 *
 * @file die_test.h
 * @author Marta Vaquerizo
 * @version 2.0
 * @date 18-11-2019
 * @copyright GNU Public License
 */

#ifndef DIE_TEST_H
#define DIE_TEST_H

/**
 * @test Prueba la función de creación de un dado
 * @pre Un identificador, el mínimo y el máximo del intervalo de valores del dado como parámetros
 * @post Un puntero no nulo al dado creado
 */
void test1_die_create();

/**
 * @test Prueba la función de creación de un dado
 * @pre Un identificador que no funciona, el mínimo y el máximo del intervalo de valores del dado como parámetros
 * @post NULL, ya que el identificador no es válido
 */
void test2_die_create();
/**
 * @test Prueba la función de tirar dado
 * @pre Tira el dado que es NULL, y se guarda el valor que devuelve
 * @post ERROR, ya que el dado es NULL, no se ha creado
 */
void test1_die_roll();
/**
 * @test Prueba la función de tirar dado
 * @pre crea un dado con un identificador, el mínimo y el máximo del intervalo de valores del dado como parámetros,
 * y tira el dado
 * @post Valor del tiro menor o igual que el valor máximo.
 */
void test2_die_roll();
/**
 * @test Prueba la función de obtener la última tirada del dado
 * @pre Crea un dado con un identificador, el mínimo y el máximo del intervalo de valores del dado como parámetros,
 * y tira el dado
 * @post Valor del tiro menor o igual que el valor máximo.
 */
void test1_die_get_last_roll();
/**
 * @test Prueba la función de obtener la última tirada del dado
 * @pre Se crea el dado, se tira el dado muchas veces y se hace la media
 * @post Como el tiro del dado es equiprobable, la media es prácticamente igual a la suma de los
 * valores máximo y mínimo entre dos.
 */
void test2_die_get_last_roll();
/**
 * @test Prueba la función de imprimir dado
 * @pre Crea un dado con un identificador, el mínimo y el máximo del intervalo de valores del dado como parámetros,
 * se tira el dado, y se imprime
 * @post OK, ya que se ha creado un dado
 */
void test1_die_print();
/**
 * @test Prueba la función de imprimir dado
 * @pre Se pone el dado a NULL y se imprime
 * @post ERROR, ya que el dado el NULL
 */
void test2_die_print();
#endif
