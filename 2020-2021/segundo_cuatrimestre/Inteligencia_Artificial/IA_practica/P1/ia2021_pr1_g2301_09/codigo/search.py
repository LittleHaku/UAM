# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).

# Authors:
# Junco de las Heras Valenzuela junco.heras@estudiante.uam.es
# Marta Vaquerizo Núñez marta.vaquerizo@estudiante.uam.es
# Pareja nº 09.

"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def searchGoalState(problem, structure):
    """
        problem: El problema de busqueda a resolver.
        structure: La estructura de datos, en la que se guardan los nodos a explorar, ejemplo: pila, cola...

        Devuelve una lista con las acciones que llevan al estado objetivo.

        Nota:
            Los elementos de abiertos tienen la forma (a, b, c) donde:
            a son las coordenadas (x, y) de la casilla.
            b es una lista que guarda el camino desde la casilla inicial hasta la casilla actual.
            c es el coste acumulado desde la casilla inicial hasta la casilla actual.

        Se ha creado esta funcion para ser lo mas generica posible y asi no repetir codigo en las funciones a desarrollar despues.
    """
    abiertos = structure
    # Se obtiene la estado de busqueda inicial.
    inicial = problem.getStartState()
    # Si la estructura es una Cola de Prioridad y no tiene funcion (para A*) entonces el abierto tiene 3 elementos.
    if isinstance(structure, util.PriorityQueue) and not isinstance(structure, util.PriorityQueueWithFunction):
        abiertos.push((inicial, []), 1)
    # Sino, solo tiene 2.
    else:
        abiertos.push((inicial, []))
    cerrados = []
    while abiertos.isEmpty() is False:
        state = abiertos.pop()
        # Si el estado de busqueda es meta se devuelve la lista con los movimientos a hacer.
        if problem.isGoalState(state[0]) is True:
            return state[1]
        if state[0] not in cerrados:
            cerrados.append(state[0])
            sucesores = problem.getSuccessors(state[0])
            for suc in sucesores:
                # Cada sucesor se aniade a la lista de abiertos segun el tipo de estructura que se ha proporcionado.
                if isinstance(structure, util.PriorityQueue) and not isinstance(structure,
                                                                                util.PriorityQueueWithFunction):
                    abiertos.push((suc[0], state[1] + [suc[1]]), problem.getCostOfActions(state[1]) + suc[2])
                else:
                    abiertos.push((suc[0], state[1] + [suc[1]]))
    # En caso de que no haya solucion el Pacman se para.
    from game import Directions
    return [Directions.STOP]


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.
    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.
    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:
    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))
    """
    "*** YOUR CODE HERE ***"

    return searchGoalState(problem, util.Stack())


def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"

    return searchGoalState(problem, util.Queue())


def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"

    return searchGoalState(problem, util.PriorityQueue())


def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"

    "Se crea la función evaluación para la cola de prioridad."
    def priority_function(x): return heuristic(x[0], problem) + problem.getCostOfActions(x[1])

    return searchGoalState(problem, util.PriorityQueueWithFunction(priority_function))


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
