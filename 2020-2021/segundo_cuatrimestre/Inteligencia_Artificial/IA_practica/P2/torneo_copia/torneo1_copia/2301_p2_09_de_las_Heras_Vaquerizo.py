# Authors:
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
# Grupo: Doble Grado
# Pareja nº 09.

import time
from game import (
    TwoPlayerGameState,
)
from tournament import (
    StudentHeuristic,
)
from reversi import Reversi
import reversi

import numpy as np

x_square = [(2, 2), (2, 7), (7, 2), (7, 7)]
cor_square = [(1, 1), (1, 8), (8, 1), (8, 8)]
c_square = [(1, 2), (2, 1), (7, 1), (1, 7), (2, 8), (8, 2), (8, 7), (7, 8)]
edge_square = [(3, 1), (4, 1), (5, 1), (6, 1), (3, 8), (4, 8), (5, 8), (6, 8),
               (1, 3), (1, 4), (1, 5), (1, 6), (8, 3), (8, 4), (8, 5), (8, 6)]

def count_x_square(board, label1, label2):
    ans = 0
    for pos in x_square:
        lab = board.get(pos)
        if lab == label1:
            ans += 1
        elif lab == label2:
            ans -= 1
    return ans

def count_x_square_without_corner(board, label1, label2):
    ans = 0
    for i in range(4):
        lab = board.get(x_square[i])
        if lab == label1:
            # Penaliza si tiene la x square y no la esquina
            if board.get(cor_square[i]) != label1:
                ans += 1
            else:
                # Te beneficia cuantas menos x_square tengas.
                ans -= 1
        elif lab == label2:
            # Penaliza si tiene la x square y no la esquina
            if board.get(cor_square[i]) != label2:
                ans -= 1
            else:
                ans += 1

    return ans

def count_cor_square(board, label1, label2):
    ans = 0
    for pos in cor_square:
        lab = board.get(pos)
        if lab == label1:
            ans += 1
        elif lab == label2:
            ans -= 1
    return ans


def count_c_square(board, label1, label2):
    ans = 0
    for pos in c_square:
        lab = board.get(pos)
        if lab == label1:
            ans += 1
        elif lab == label2:
            ans -= 1
    return ans

def count_edge_square(board, label1, label2):
    ans = 0
    for pos in edge_square:
        lab = board.get(pos)
        if lab == label1:
            ans += 1
        elif lab == label2:
            ans -= 1
    return ans


class Solution1(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution1"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        # Heuristic for Reversi.
        if not isinstance(state.game, Reversi):
            return 0
        board = state.board
        #print("soo", state.next_player.label, state.is_player_max(state.next_player))
        if not isinstance(board, dict):
            board = reversi.from_array_to_dictionary_board(state.board)

        ans = 0
        num_jugada = state.scores[0] + state.scores[1]
        dif_jugada = state.scores[0] - state.scores[1]

        ans += -50 *count_x_square(board, state.player1.label, state.player2.label)
        ans += 200 * count_cor_square(board, state.player1.label, state.player2.label)
        ans += 3 * count_edge_square(board, state.player1.label, state.player2.label)
        ans += -5 * count_c_square(board, state.player1.label, state.player2.label)

        # If you are going to lose don't take this movement.
        if state.scores[0] < 2 and num_jugada > 8:
            ans = -99999999  # prácticamente -inf

        # Introduce random in our style.
        ans += 2*np.random.random()

        # Late game, the score is the chips you have.
        if num_jugada > 62:
            ans = dif_jugada

        # Calculas desde la vista del player1, si eres el devuelves ans.
        if state.next_player.label == state.player1.label:
            return ans
        # Sino inviertes el resultado (cambiando el signo).
        return -ans


class Solution2(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution2"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if not isinstance(state.game, Reversi):
            return 0
        board = state.board
        # print("soo", state.next_player.label, state.is_player_max(state.next_player))
        if not isinstance(board, dict):
            board = reversi.from_array_to_dictionary_board(state.board)

        ans = 0
        num_jugada = state.scores[0] + state.scores[1]
        dif_jugada = state.scores[0] - state.scores[1]

        ans += -70 * count_x_square(board, state.player1.label, state.player2.label)
        ans += 200 * count_cor_square(board, state.player1.label, state.player2.label)
        ans += 5 * count_edge_square(board, state.player1.label, state.player2.label)
        ans += -20 * count_c_square(board, state.player1.label, state.player2.label)

        # If you are going to lose don't take this movement.
        if state.scores[0] < 2 and num_jugada > 8:
            ans = -99999999  # prácticamente -inf

        # Introduce random in our style.
        ans += 2*np.random.random()

        # Late game, the score is the chips you have.
        if num_jugada > 62:
            ans = dif_jugada

        # Calculas desde la vista del player1, si eres el devuelves ans.
        if state.next_player.label == state.player1.label:
            return ans
        # Sino inviertes el resultado (cambiando el signo).
        return -ans


class Solution3(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution3"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        if not isinstance(state.game, Reversi):
            return 0
        board = state.board
        # print("soo", state.next_player.label, state.is_player_max(state.next_player))
        if not isinstance(board, dict):
            board = reversi.from_array_to_dictionary_board(state.board)

        ans = 0
        num_jugada = state.scores[0] + state.scores[1]
        dif_jugada = state.scores[0] - state.scores[1]

        ans += -70 * count_x_square_without_corner(board, state.player1.label, state.player2.label)
        ans += 200 * count_cor_square(board, state.player1.label, state.player2.label)
        ans += 10 * count_edge_square(board, state.player1.label, state.player2.label)
        ans += -20 * count_c_square(board, state.player1.label, state.player2.label)

        # If you are going to lose don't take this movement.
        if state.scores[0] < 2 and num_jugada > 8:
            ans = -99999999  # prácticamente -inf

        # Introduce random in our style.
        ans += 2*np.random.random()

        # Late game, the score is the chips you have.
        if num_jugada > 62:
            ans = dif_jugada

        # Calculas desde la vista del player1, si eres el devuelves ans.
        if state.next_player.label == state.player1.label:
            return ans
        # Sino inviertes el resultado (cambiando el signo).
        return -ans



