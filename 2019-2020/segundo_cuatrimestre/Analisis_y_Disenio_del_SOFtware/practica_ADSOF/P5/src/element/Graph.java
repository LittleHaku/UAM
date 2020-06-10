package element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase representa un grafo, con una serie de nodos y enlaces entre ellos.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <V> tipo de elemento que identifica a los nodos.
 * @param <E> tipo de elemento que identifica a los enlaces.
 */
public class Graph<V, E> implements Collection<Node<V>> {
	protected Collection<Node<V>> lNode;
	private Collection<Edge<E>> lEdge;

	/**
	 * Contructor del grafo, donde se inicializan la lista de nodos y de enlaces.
	 */
	public Graph() {
		lNode = new ArrayList<>();
		lEdge = new ArrayList<>();
	}

	/**
	 * añade todos los nodos de la lista pasada por argumento al grafo.
	 * 
	 * @param l lista de nodos a añadir
	 * @return this para poder seguir invocando metodos
	 */
	public Graph<V, E> addAll(List<Node<V>> l) {
		for (Node<V> n : l) {
			if (lNode.contains(n) == false) {
				if (n.setGraph(this)) {
					lNode.add(n);
				}
			}
		}
		return this;
	}

	/**
	 * Conecta los nodos from y to mediante el enlace weight.
	 * 
	 * @param from   nodo de partida
	 * @param weight valor del enlace
	 * @param to     nodo destino
	 * @return this para poder seguir invocando metodos
	 */
	public Graph<V, E> connect(Node<V> from, E weight, Node<V> to) {
		lEdge.add(new Edge<>(from, weight, to));
		return this;
	}

	/**
	 * devuelve si hay una conexion directa entre n y other
	 * 
	 * @param n     el nodo
	 * @param other el valor del otro nodo
	 * @return true si en nodo esta conectado con otro nodo con valor other, false
	 *         sino
	 */
	public boolean isConnectedTo(Node<V> n, V other) {
		for (Edge<E> e : lEdge) {
			if (e.getFrom().getIndex() == n.getIndex() && e.getTo().getValue().equals(other)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * devuelve si hay una conexion directa entre n y other
	 * 
	 * @param n     el primer nodo
	 * @param other el otro nodo
	 * @return true si en nodo esta conectado con otro nodo other, false sino
	 */
	public boolean isConnectedTo(Node<V> n, Node<V> other) {
		for (Edge<E> e : lEdge) {
			if (e.getFrom().getIndex() == n.getIndex() && e.getTo().getIndex() == other.getIndex()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve los vecinos de this
	 * 
	 * @param n el nodo a buscar los vecinos
	 * @return una lista de los nodos vecinos de this
	 */
	public List<Node<?>> neighbours(Node<V> n) {
		List<Node<?>> l = new ArrayList<>();
		for (Edge<E> e : lEdge) {
			if (e.getFrom().getIndex() == n.getIndex()) {
				if (l.contains(e.getTo()) == false) {
					l.add(e.getTo());
				}
			}
		}
		return l;
	}

	/**
	 * Devuelve todos los valores de las aristas de n y other
	 * 
	 * @param n     el primer nodo
	 * @param other el otro nodo que tiene una posible conexion con this
	 * @return una lista de valores de las aristas
	 */
	public List<Object> getEdgeValues(Node<V> n, Node<V> other) {
		List<Object> l = new ArrayList<>();
		for (Edge<E> e : lEdge) {
			if (e.getFrom().getIndex() == n.getIndex() && e.getTo().getIndex() == other.getIndex()) {
				l.add(e.getWeight());
			}
		}
		return l;
	}

	/**
	 * {@inheritDoc} Devuelve el numero de nodos del grafo.
	 */
	@Override
	public int size() {
		return lNode.size();
	}

	/**
	 * {@inheritDoc} Devuelve true si el grafo esta vacio, false en el caso
	 * contrario.
	 */
	@Override
	public boolean isEmpty() {
		return lNode.isEmpty();
	}

	/**
	 * {@inheritDoc} Devuelve true si o esta en el grafo, false en otro caso.
	 */
	@Override
	public boolean contains(Object obj) {
		return lNode.contains(obj);
	}

	/**
	 * {@inheritDoc} Devuelve el iterador del grafo.
	 */
	@Override
	public Iterator<Node<V>> iterator() {
		return this.lNode.iterator();
	}

	/**
	 * {@inheritDoc} Devuelve los nodos en formato array.
	 */
	@Override
	public Object[] toArray() {
		return lNode.toArray();
	}

	/**
	 * {@inheritDoc} Devuelve los nodos en formato array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return lNode.toArray(a);
	}

	/**
	 * {@inheritDoc} Añade un nodo al grafo.
	 */
	@Override
	public boolean add(Node<V> e) {
		return lNode.add(e);
	}

	/**
	 * {@inheritDoc} Quita el nodo pasado por argumento del grafo. Tambien borra las
	 * aristas que involucren al nodo a borrar.
	 */
	@Override
	public boolean remove(Object obj) {
		Node<?> n = (Node<?>) obj;
		// Borra las aristas que involucren al nodo a borrar.
		lEdge = lEdge.stream()
				.filter(e -> e.getFrom().getIndex() != n.getIndex() && e.getTo().getIndex() != n.getIndex())
				.collect(Collectors.toList());

		return lNode.remove(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return lNode.containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(Collection<? extends Node<V>> c) {
		return lNode.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean flag = false;
		for (Object obj : c) {
			if (this.remove(obj)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return lNode.retainAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		lNode.clear();
		lEdge.clear();
	}

	/**
	 * {@inheritDoc} Devuelve un String con la informacion del grafo.
	 */

	@Override
	public String toString() {
		String ret = "";
		ret += "Nodes:\n";
		for (Node<V> n : lNode) {
			ret += " " + n + "\n";
		}
		ret += "Edges:\n";
		for (Edge<E> e : lEdge) {
			ret += " " + e + "\n";
		}
		return ret;
	}
}
