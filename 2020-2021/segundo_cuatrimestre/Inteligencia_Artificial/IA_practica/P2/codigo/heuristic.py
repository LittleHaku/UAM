"""Heuristics to evaluate board.

    Authors:
        Fabiano Baroni <fabiano.baroni@uam.es>,
        Alejandro Bellogin <alejandro.bellogin@uam.es>
        Alberto Suárez <alberto.suarez@uam.es>

"""
# Authors:
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
# Grupo: Doble Grado, 2301.
# Pareja nº 09.



from __future__ import annotations  # For Python 3.7

from typing import Callable, Sequence

import numpy as np

from game import TwoPlayerGameState

from reversi import Reversi
import reversi


class Heuristic(object):
    """Encapsulation of the evaluation fucnction."""

    def __init__(
        self,
        name: str,
        evaluation_function: Callable[[TwoPlayerGameState], float],
    ) -> None:
        """Initialize name of heuristic & evaluation function."""
        self.name = name
        self.evaluation_function = evaluation_function

    def evaluate(self, state: TwoPlayerGameState) -> float:
        """Evaluate a state."""
        # Prevent modifications of the state.
        # Deep copy everything, except attributes related
        # to graphical display.
        state_copy = state.clone()
        return self.evaluation_function(state_copy)

    def get_name(self) -> str:
        """Name getter."""
        return self.name


def simple_evaluation_function(state: TwoPlayerGameState) -> float:
    """Return a random value, except for terminal game states."""
    state_value = 2*np.random.rand() - 1
    if state.end_of_game:
        scores = state.scores
        # Evaluation of the state from the point of view of MAX

        assert isinstance(scores, (Sequence, np.ndarray))
        score_difference = scores[0] - scores[1]

        if state.is_player_max(state.player1):
            state_value = score_difference
        elif state.is_player_max(state.player2):
            state_value = - score_difference
        else:
            raise ValueError('Player MAX not defined')

    return state_value


def complex_evaluation_function(state: TwoPlayerGameState) -> float:
    """Return zero, except for terminal game states."""
    state_value = 0
    if state.end_of_game:
        scores = state.scores
        # Evaluation of the state from the point of view of MAX

        assert isinstance(scores, (Sequence, np.ndarray))
        score_difference = scores[0] - scores[1]

        if state.is_player_max(state.player1):
            state_value = score_difference
        elif state.is_player_max(state.player2):
            state_value = - score_difference
        else:
            raise ValueError('Player MAX not defined')
    else:
        successors = state.game.generate_successors(state)

        # NOTE [ASG]: Remove commented code?
        """
        state_next = copy.deepcopy(state)
        state_next.next_player = state_next.game.opponent(
            state_next.next_player
        )
        successors_next = state.game.generate_successors(
            state_next
        )
        return len(successors_next) - len(successors)
        """
        # Minimize the number of your opponent moves (for MAX).
        score_difference = - len(successors)
        if state.is_player_max(state.player1):
            state_value = score_difference
        elif state.is_player_max(state.player2):
            state_value = - score_difference

    return state_value


heuristic = Heuristic(
    name='Simple heuristic',
    evaluation_function=simple_evaluation_function,
)
heuristic2 = Heuristic(
    name='Complex heuristic',
    evaluation_function=complex_evaluation_function,
)

# For testing purposes.
board_value1 = [[4000, -2,  3,  2,  2, 3, -2, 4000],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [2,   -1,  1,  1,  1,  1, -1,   2],
               [3,   -1,  2,  1,  1,  2, -1,   3],
               [-2,  -60, -1, -1, -1, -1, -60,  -2],
               [4000, -2,  3,  2,  2,  3, -2, 4000]]


def sol2_evaluation_function(state: TwoPlayerGameState) -> float:
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

    # Movilidad.
    mobility = -len(state.game._get_valid_moves(board_dict, state.player2.label))
    # mobility += len(state.game._get_valid_moves(board_dict, state.player1.label))

    # Obtiene valores predeterminados.
    ans = 0
    for i in range(8):
        for j in range(8):
            if board[i][j] == lab1:
                ans += board_value1[i][j]
            elif board[i][j] == lab2:
                ans -= board_value1[i][j]

    # Proporcion greedy-predeterminados.
    ret = 1.3 * ans + 0.9 * mobility

    # Si estamos jugando como player1.
    if state.is_player_max(state.player1):
        return ret
    # Sino invertir el resultado.
    return -ret


heuristic3 = Heuristic(
    name='Our heuristic',
    evaluation_function=sol2_evaluation_function,
)
