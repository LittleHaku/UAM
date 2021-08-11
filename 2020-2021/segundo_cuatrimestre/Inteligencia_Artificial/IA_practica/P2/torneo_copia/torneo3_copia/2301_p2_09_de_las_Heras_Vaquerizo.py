# Authors:
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
# Grupo: Doble Grado, 2301.
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


board_value1 = [[400, -2,  3,  2,  2, 3, -2, 400],
               [-2,  -6, -1, -1, -1, -1, -6,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -6, -1, -1, -1, -1, -6,  -2],
               [400, -2,  3,  2,  2,  3, -2, 400]]

board_value2 = [[400, -4, 3,  2,  2,  3, -4, 400],
               [-4,  -7, -1, -1, -1, -1, -7,  -4],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-4,  -7, -1, -1, -1, -1, -7,  -4],
               [400, -3,  3,  2,  2,  3, -4, 400]]

board_value3 = [[400, -2,  3,  2,  2, 3, -2, 400],
               [-2,  -5, -1, -1, -1, -1, -5,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -5, -1, -1, -1, -1, -5,  -2],
               [400, -2,  3,  2,  2,  3, -2, 400]]


class Solution1(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution1"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        # Heuristica creada para Reversi 8x8.
        if not isinstance(state.game, Reversi):
            return 0

        # Si acaba el juego (posicion final), devolver el num de fichas total.
        if state.end_of_game:
            ans = state.scores[0] - state.scores[1]
            if state.is_player_max(state.player1):
                return ans
            return -ans

        # Inicializacion de variables.
        lab1 = state.player1.label
        lab2 = state.player2.label
        board = state.board
        board_dict = state.board
        if isinstance(state.board, dict):
            board = reversi.from_dictionary_to_array_board(state.board, 8, 8)
        if not isinstance(state.board, dict):
            board_dict = reversi.from_array_to_dictionary_board(state.board)

        # Mobilidad.
        mobility = -len(state.game._get_valid_moves(board_dict, state.player2.label))

        # Obtiene valores predeterminados.
        ans = 0
        for i in range(8):
            for j in range(8):
                if board[i][j] == lab1:
                    ans += board_value1[i][j]
                elif board[i][j] == lab2:
                    ans -= board_value1[i][j]

        # Proporcion greedy-predeterminados.
        ret = 1.3*ans + mobility

        # Introduce aleatoriedad para mejorar el juego.
        ret += 2*np.random.random() - 1

        # Si estamos jugando como player1.
        if state.is_player_max(state.player1):
            return ret
        # Sino invertir el resultado.
        return -ret


class Solution2(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution2"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        # Heuristica creada para Reversi 8x8.
        if not isinstance(state.game, Reversi):
            return 0

        # Si acaba el juego (posicion final), devolver el num de fichas total.
        if state.end_of_game:
            ans = state.scores[0] - state.scores[1]
            if state.is_player_max(state.player1):
                return ans
            return -ans

        # Inicializacion de variables.
        lab1 = state.player1.label
        lab2 = state.player2.label
        board = state.board
        board_dict = state.board
        if isinstance(state.board, dict):
            board = reversi.from_dictionary_to_array_board(state.board, 8, 8)
        if not isinstance(state.board, dict):
            board_dict = reversi.from_array_to_dictionary_board(state.board)

        # Mobilidad.
        mobility = -len(state.game._get_valid_moves(board_dict, state.player2.label))

        # Obtiene valores predeterminados.
        ans = 0
        for i in range(8):
            for j in range(8):
                if board[i][j] == lab1:
                    ans += board_value2[i][j]
                elif board[i][j] == lab2:
                    ans -= board_value2[i][j]

        # Proporcion greedy-predeterminados.
        ret = 1.3*ans + 1.2*mobility

        # Introduce aleatoriedad para mejorar el juego.
        ret += 2*np.random.random() - 1

        # Si estamos jugando como player1.
        if state.is_player_max(state.player1):
            return ret
        # Sino invertir el resultado.
        return -ret

class Solution3(StudentHeuristic):
    def get_name(self) -> str:
        return "2301_09_solution3"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        # Heuristica creada para Reversi 8x8.
        if not isinstance(state.game, Reversi):
            return 0

        # Si acaba el juego (posicion final), devolver el num de fichas total.
        if state.end_of_game:
            ans = state.scores[0] - state.scores[1]
            if state.is_player_max(state.player1):
                return ans
            return -ans

        # Inicializacion de variables.
        lab1 = state.player1.label
        lab2 = state.player2.label
        board = state.board
        board_dict = state.board
        if isinstance(state.board, dict):
            board = reversi.from_dictionary_to_array_board(state.board, 8, 8)
        if not isinstance(state.board, dict):
            board_dict = reversi.from_array_to_dictionary_board(state.board)

        # Mobilidad de TU jugador.
        mobility = len(state.game._get_valid_moves(board_dict, state.player1.label))

        # Obtiene valores predeterminados.
        ans = 0
        for i in range(8):
            for j in range(8):
                if board[i][j] == lab1:
                    ans += board_value3[i][j]
                elif board[i][j] == lab2:
                    ans -= board_value3[i][j]

        # Proporcion greedy-predeterminados.
        ret = ans + mobility

        # Introduce aleatoriedad para mejorar el juego.
        ret += 2*np.random.random() - 1

        # Si estamos jugando como player1.
        if state.is_player_max(state.player1):
            return ret
        # Sino invertir el resultado.
        return -ret
