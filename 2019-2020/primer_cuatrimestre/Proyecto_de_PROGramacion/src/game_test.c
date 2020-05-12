/**
 * @brief It tests the game interface.
 *
 * @file game_test.c
 * @author Fabian Gutierrez
 * @version 2.0
 * @date 16-11-2019
 * @copyright GNU Public License
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "game.h"
#include "game_test.h"
#include "test.h"

#define MAX_TESTS 62

/**
Funciones privadas
 */
Game* game_test_create();
void game_test_clean_up(Space*, Link*, Object*, Player*, Die*);

/**
 * @brief Funcion principal de pruebas para el modulo Graphic Engine.
 *
 * Dos modos de ejecucion:
 *   1.-Si se ejecuta sin parametros se ejecutan todas las pruebas
 *   2.-Si se ejecuta con un numero entre 1 y el numero de pruebas solo ejecuta
 *      la prueba indicada
 *
 */
int main(int argc, char** argv) {

    int test = 0;
    int all = 1;

    if (argc < 2) {
        printf("Running all test for module Game:\n");
    } else {
        test = atoi(argv[1]);
        all = 0;
        printf("Running test %d:\t", test);
        if (test < 1 || test > MAX_TESTS) {
            printf("Error: unknown test %d\n", test);
            exit(EXIT_SUCCESS);
        }
    }

    if (all || test == 1) test_game_create();
    if (all || test == 2) test1_game_destroy();
    if (all || test == 3) test2_game_destroy();
    if (all || test == 4) test_game_is_over();
    if (all || test == 5) test1_game_print_data();
    if (all || test == 6) test2_game_print_data();
    if (all || test == 7) test1_game_get_space();
    if (all || test == 8) test2_game_get_space();
    if (all || test == 9) test1_game_get_link();
    if (all || test == 10) test2_game_get_link();
    if (all || test == 11) test1_game_get_player();
    if (all || test == 12) test2_game_get_player();
    if (all || test == 13) test1_game_get_object();
    if (all || test == 14) test2_game_get_object();
    if (all || test == 15) test1_game_get_objects();
    if (all || test == 16) test2_game_get_objects();
    if (all || test == 17) test1_game_get_last_roll();
    if (all || test == 18) test2_game_get_last_roll();
    if (all || test == 19) test1_game_get_last_command();
    if (all || test == 20) test2_game_get_last_command();
    if (all || test == 21) test1_game_get_last_description();
    if (all || test == 22) test2_game_get_last_description();
    if (all || test == 23) test1_game_add_space();
    if (all || test == 24) test2_game_add_space();
    if (all || test == 25) test1_game_add_link();
    if (all || test == 26) test2_game_add_link();
    if (all || test == 27) test1_game_add_object();
    if (all || test == 28) test2_game_add_object();
    if (all || test == 29) test1_game_set_player();
    if (all || test == 30) test2_game_set_player();
    if (all || test == 31) test1_game_set_die();
    if (all || test == 32) test2_game_set_die();
    if (all || test == 33) test1_game_find_object();
    if (all || test == 34) test2_game_find_object();
    if (all || test == 35) test1_game_get_spaces();
    if (all || test == 36) test2_game_get_spaces();
    if (all || test == 37) test1_game_get_links();
    if (all || test == 38) test2_game_get_links();
    if (all || test == 39) test1_game_get_last_str();
    if (all || test == 40) test2_game_get_last_str();
    if (all || test == 41) test1_game_get_last_full_desc();
    if (all || test == 42) test2_game_get_last_full_desc();
    if (all || test == 43) test1_game_get_num_spaces();
    if (all || test == 44) test2_game_get_num_spaces();
    if (all || test == 45) test1_game_get_num_links();
    if (all || test == 46) test2_game_get_num_links();
    if (all || test == 47) test1_game_get_num_objects();
    if (all || test == 48) test2_game_get_num_objects();
    if (all || test == 49) test1_game_space_is_lit();
    if (all || test == 50) test2_game_space_is_lit();
    if (all || test == 51) test1_object_is_hidden();
    if (all || test == 52) test2_object_is_hidden();
    if (all || test == 53) test1_game_set_last_change();
    if (all || test == 54) test2_game_set_last_change();
    if (all || test == 55) test1_game_get_last_change();
    if (all || test == 56) test2_game_get_last_change();

    PRINT_PASSED_PERCENTAGE;

    return 0;
}

void test_game_create() {
    Game *g = NULL;
    PRINT_TEST_RESULT((g = game_create()) != NULL);
    game_destroy(g);
}

void test1_game_destroy() {
    PRINT_TEST_RESULT(game_destroy(NULL) == ERROR);
}

void test2_game_destroy() {
    Game *g = NULL;
    g = game_test_create();
    PRINT_TEST_RESULT(game_destroy(g) == OK);
}

void test_game_is_over() {
    PRINT_TEST_RESULT(game_is_over(NULL) == FALSE);
}

void test1_game_print_data() {
    game_print_data(NULL);
    PRINT_TEST_RESULT(TRUE);
}

void test2_game_print_data() {
    Game *g = NULL;
    g = game_test_create();
    game_print_data(g);
    PRINT_TEST_RESULT(TRUE);
    game_destroy(g);
}

void test1_game_get_space() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_space(g, -1) == NULL);
    game_destroy(g);
}

void test2_game_get_space() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_space(g, 1) != NULL);
    game_destroy(g);
}

void test1_game_get_link() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_link(g, -1) == NULL);
    game_destroy(g);
}

void test2_game_get_link() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_link(g, 1) != NULL);
    game_destroy(g);
}

void test1_game_get_player() {
    PRINT_TEST_RESULT(game_get_player(NULL) == NULL);
}

void test2_game_get_player() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_player(g) != NULL);
    game_destroy(g);
}

void test1_game_get_object() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_object(g, -1) == NULL);
    game_destroy(g);
}

void test2_game_get_object() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_object(g, 1) != NULL);
    game_destroy(g);
}

void test1_game_get_objects() {
    PRINT_TEST_RESULT(game_get_objects(NULL) == NULL);
}

void test2_game_get_objects() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_objects(g) != NULL);
    game_destroy(g);
}

void test1_game_get_last_roll() {
    PRINT_TEST_RESULT(game_get_last_roll(NULL) == -1);
}

void test2_game_get_last_roll() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    game_update(g, ROLL);
    PRINT_TEST_RESULT(game_get_last_roll(g) > 0);
    game_destroy(g);
}

void test1_game_get_last_command() {
    PRINT_TEST_RESULT(game_get_last_command(NULL) == NO_CMD);
}

void test2_game_get_last_command() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    game_update(g, ROLL);
    PRINT_TEST_RESULT(game_get_last_command(g) == ROLL);
    game_destroy(g);
}

void test1_game_get_last_description() {
    PRINT_TEST_RESULT(game_get_last_description(NULL) == NULL);
}

void test2_game_get_last_description() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_last_description(g) != NULL);
    game_destroy(g);
}

void test1_game_add_space() {
    Space *s = NULL;
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_add_space(g, s) == ERROR);
    game_destroy(g);
}

void test2_game_add_space() {
    Space *s = NULL;
    Game *g = NULL;
    if ((s = space_create(2)) == NULL) return;
    if ((g = game_test_create()) == NULL || game_add_space(g, s) == ERROR) {
        if (s != NULL) space_destroy(s);
        if (g != NULL) game_destroy(g);
        PRINT_TEST_RESULT(FALSE);
        return;
    }
    PRINT_TEST_RESULT(game_get_num_spaces(g) == 2);
    game_destroy(g);
}

void test1_game_add_link() {
    Link *l = NULL;
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_add_link(g, l) == ERROR);
    game_destroy(g);
}

void test2_game_add_link() {
    Link *l = NULL;
    Game *g = NULL;
    if ((l = link_create(2)) == NULL) return;
    if ((g = game_test_create()) == NULL || game_add_link(g, l) == ERROR) {
        if (l != NULL) link_destroy(l);
        if (g != NULL) game_destroy(g);
        PRINT_TEST_RESULT(FALSE);
        return;
    }
    PRINT_TEST_RESULT(game_get_num_links(g) == 2);
    game_destroy(g);
}

void test1_game_add_object() {
    Object *o = NULL;
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_add_object(g, o) == ERROR);
    game_destroy(g);
}

void test2_game_add_object() {
    Object *o = NULL;
    Game *g = NULL;
    if ((o = object_create(2)) == NULL) return;
    if ((g = game_test_create()) == NULL || game_add_object(g, o) == ERROR) {
        if (o != NULL) object_destroy(o);
        if (g != NULL) game_destroy(g);
        PRINT_TEST_RESULT(FALSE);
        return;
    }
    PRINT_TEST_RESULT(game_get_num_objects(g) == 2);
    game_destroy(g);
}

void test1_game_set_player() {
    Player *p = NULL;
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_set_player(g, p) == ERROR);
    game_destroy(g);
}

void test2_game_set_player() {
    STATUS flag = ERROR;
    Player *p = NULL;
    Game *g = NULL;
    if ((p = player_create(2, 1)) == NULL) return;
    if ((g = game_test_create()) == NULL) {
        player_destroy(p);
        return;
    }
    PRINT_TEST_RESULT((flag = game_set_player(g, p)) == OK);
    game_destroy(g);
    if (flag == ERROR)
        player_destroy(p);
}

void test1_game_set_die() {
    Die *d = NULL;
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_set_die(g, d) == ERROR);
    game_destroy(g);
}

void test2_game_set_die() {
    STATUS flag = ERROR;
    Die *d = NULL;
    Game *g = NULL;
    if ((d = die_create(2, 1, 2)) == NULL) return;
    if ((g = game_test_create()) == NULL) {
        die_destroy(d);
        return;
    }
    PRINT_TEST_RESULT((flag = game_set_die(g, d)) == OK);
    game_destroy(g);
    if (flag == ERROR)
        die_destroy(d);
}

void test1_game_find_object() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_find_object(g, -1) == NO_ID);
    game_destroy(g);
}

void test2_game_find_object() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_find_object(g, 1) == 1);
    game_destroy(g);
}

void test1_game_get_spaces() {
    PRINT_TEST_RESULT(game_get_spaces(NULL) == NULL);
}

void test2_game_get_spaces() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_spaces(g) != NULL);
    game_destroy(g);
}

void test1_game_get_links() {
    PRINT_TEST_RESULT(game_get_links(NULL) == NULL);
}

void test2_game_get_links() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_links(g) != NULL);
    game_destroy(g);
}

void test1_game_get_last_str() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_last_str(g) != NULL);
    game_destroy(g);
}

void test2_game_get_last_str() {
    PRINT_TEST_RESULT(game_get_last_str(NULL) == NULL);
}

void test1_game_get_last_full_desc() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL) return;
    PRINT_TEST_RESULT(game_get_last_full_desc(g) != NULL);
    game_destroy(g);
}

void test2_game_get_last_full_desc() {
    PRINT_TEST_RESULT(game_get_last_full_desc(NULL) == NULL);
}

void test1_game_get_num_spaces() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL)   return;
    PRINT_TEST_RESULT(game_get_num_spaces(g) == 1);
    game_destroy(g);
}

void test2_game_get_num_spaces() {
    PRINT_TEST_RESULT(game_get_num_spaces(NULL) == -1);
}

void test1_game_get_num_links() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL)   return;
    PRINT_TEST_RESULT(game_get_num_links(g) == 1);
    game_destroy(g);
}

void test2_game_get_num_links() {
    PRINT_TEST_RESULT(game_get_num_links(NULL) == -1);
}

void test1_game_get_num_objects() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL)   return;
    PRINT_TEST_RESULT(game_get_num_objects(g) == 1);
    game_destroy(g);
}

void test2_game_get_num_objects() {
    PRINT_TEST_RESULT(game_get_num_objects(NULL) == -1);
}

void test1_game_space_is_lit() {
    Game *g = NULL;
    Space *sp = NULL;
    if ((g = game_test_create()) == NULL)   return;
    sp = space_create(5);
    game_add_space(g, sp);
    PRINT_TEST_RESULT(game_space_is_lit(g, sp) == TRUE);
    game_destroy(g);
}

void test2_game_space_is_lit() {
    PRINT_TEST_RESULT(game_space_is_lit(NULL, NULL) == FALSE);
}

void test1_object_is_hidden() {
    Game *g = NULL;
    Object *o = NULL;
    if ((g = game_test_create()) == NULL)   return;
    o = object_create(18);
    game_add_object(g, o);
    PRINT_TEST_RESULT(game_object_is_hidden(g, o) == FALSE);
    game_destroy(g);
}

void test2_object_is_hidden() {
    PRINT_TEST_RESULT(game_object_is_hidden(NULL, NULL) == FALSE);
}

void test1_game_set_last_change() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL)   return;
    PRINT_TEST_RESULT(game_set_last_change(g, "Change") == OK);
    game_destroy(g);
}

void test2_game_set_last_change() {
    PRINT_TEST_RESULT(game_set_last_change(NULL, "Change") == ERROR);
}

void test1_game_get_last_change() {
    Game *g = NULL;
    if ((g = game_test_create()) == NULL)   return;
    PRINT_TEST_RESULT(game_get_last_change(g) != NULL);
    game_destroy(g);
}

void test2_game_get_last_change() {
    PRINT_TEST_RESULT(game_get_last_change(NULL) == NULL);
}

/**
Implementación de funciones privadas
 */
Game* game_test_create() {
    Game *g = NULL;
    Space *s = NULL;
    Link *l = NULL;
    Object *o = NULL;
    Player *p = NULL;
    Die *d = NULL;
    BOOL correct_set = FALSE;

    s = space_create(1);
    l = link_create(1);
    o = object_create(1);
    p = player_create(1, 1);
    d = die_create(1, 1, 2);
    if (s == NULL || l == NULL || o == NULL || p == NULL || d == NULL) {
        game_test_clean_up(s, l, o, p, d);
        return NULL;
    }

    if ((g = game_create()) == NULL) {
        game_test_clean_up(s, l, o, p, d);
        return NULL;
    }

    while (TRUE) {
        if (player_set_space(p, 1) == ERROR)        break;
        if (object_set_name(o, "Name") == ERROR)    break;
        if (space_add_object(s, 1) == ERROR)        break;
        if (space_set_description(s, "a") == ERROR) break;
        if (game_add_space(g, s) == ERROR) break;
        if (game_add_link(g, l) == ERROR) break;
        if (game_add_object(g, o) == ERROR) break;
        if (game_set_player(g, p) == ERROR) break;
        if (game_set_die(g, d) == ERROR) break;
        correct_set = TRUE;
    }

    if (correct_set) {
        return g;
    }

    game_destroy(g);
    game_test_clean_up(s, l, o, p, d);
    return NULL;
}

void game_test_clean_up(Space *s, Link *l, Object *o, Player* p, Die* d) {
    if (s != NULL) space_destroy(s);
    if (l != NULL) link_destroy(l);
    if (o != NULL) object_destroy(o);
    if (p != NULL) player_destroy(p);
    if (d != NULL) die_destroy(d);
}
