"""Illustration of tournament.

Authors:
    Alejandro Bellogin <alejandro.bellogin@uam.es>

"""
# Authors:
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
# Grupo: Doble Grado, 2301.
# Pareja nº 09.

from __future__ import annotations  # For Python 3.7

import numpy as np

from game import Player, TwoPlayerGameState, TwoPlayerMatch
from heuristic import simple_evaluation_function
from tictactoe import TicTacToe
from reversi import Reversi
from tournament import StudentHeuristic, Tournament
# Import the file that start with a number.
our_heuristics = __import__('2301_p2_09_de_las_Heras_Vaquerizo')


class Heuristic1(StudentHeuristic):
    def get_name(self) -> str:
        return "dummy"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        # Use an auxiliary function.
        return self.dummy(123)

    def dummy(self, n: int) -> int:
        return n + 4


class Heuristic2(StudentHeuristic):
    def get_name(self) -> str:
        return "random"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        return float(np.random.rand())


class Heuristic3(StudentHeuristic):
    def get_name(self) -> str:
        return "heuristic"

    def evaluation_function(self, state: TwoPlayerGameState) -> float:
        return simple_evaluation_function(state)


def create_match(player1: Player, player2: Player) -> TwoPlayerMatch:

    initial_player = player1
    dim_height = 8
    """dim_board = 3
    
    initial_board = np.zeros((dim_board, dim_board))

    game = TicTacToe(
        player1=player1,
        player2=player2,
        dim_board=dim_board,
    )"""
    # Reversi game.
    dim_width = 8
    initial_board = None  # Standard initial board.

    game = Reversi(
        player1=player1,
        player2=player2,
        height=dim_height,
        width=dim_width,
    )

    game_state = TwoPlayerGameState(
        game=game,
        board=initial_board,
        initial_player=initial_player,
    )
    # GUI = True or False.
    # Change max sec per move from 5 to 20
    return TwoPlayerMatch(game_state, max_sec_per_move=50, gui=True)


tour = Tournament(max_depth=3, init_match=create_match)
# strats = {'opt1': [Heuristic1], 'opt2': [Heuristic2], 'opt3': [Heuristic3]}
# Test our Heuristics
strats = {'opt1': [our_heuristics.Solution1], 'opt2': [our_heuristics.Solution2]}#, 'opt3': [Heuristic3]}

n = 1
scores, totals, names = tour.run(
    student_strategies=strats,
    increasing_depth=False,
    n_pairs=n,
    allow_selfmatch=False,
)

print(
    'Results for tournament where each game is repeated '
    + '%d=%dx2 times, alternating colors for each player' % (2 * n, n),
)

# print(totals)
# print(scores)

print('\ttotal:', end='')
for name1 in names:
    print('\t%s' % (name1), end='')
print()
for name1 in names:
    print('%s\t%d:' % (name1, totals[name1]), end='')
    for name2 in names:
        if name1 == name2:
            print('\t---', end='')
        else:
            print('\t%d' % (scores[name1][name2]), end='')
    print()
