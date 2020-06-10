package element;

import java.util.List;

/**
 * Esta clase representa un nodo de tipo generico V. Cada nodo tiene un
 * INDEX_COUNT propio para identificarse de los demas
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * 
 * @param <V> tipo generico del nodo
 */
public class Node<V> {
	private static int INDEX_COUNT = 0;
	private final int index;
	private V nodeValue;
	/**
	 * es null si el nodo no pertenece a ningún grafo, sino es el grafo al que
	 * pertenece
	 */
	private Graph<V, ?> graph;

	/**
	 * Es el constructor
	 * 
	 * @param value el valor inicial del nodo
	 */
	public Node(V value) {
		this.index = INDEX_COUNT++;
		nodeValue = value;
	}

	/**
	 * Es el setter del graph donde esta el nodo guardado
	 * 
	 * @param graph el graph del que el nodo forma parte
	 * @return true si el nodo no estaba en otro grafo, false sino
	 */
	public boolean setGraph(Graph<V, ?> graph) {
		if (this.graph != null) {
			return false;
		}
		this.graph = graph;
		return true;
	}

	/**
	 * devuelve si hay una conexion directa entre this y other
	 * 
	 * @param other el valor del otro nodo
	 * @return true si en nodo esta conectado con otro nodo con valor other, false
	 *         sino
	 */
	public boolean isConnectedTo(V other) {
		if (graph == null) {
			return false;
		}
		return graph.isConnectedTo(this, other);
	}

	/**
	 * devuelve si hay una conexion directa entre this y other
	 * 
	 * @param other el otro nodo
	 * @return true si en nodo esta conectado con otro nodo other, false sino
	 */
	public boolean isConnectedTo(Node<V> other) {
		if (graph == null) {
			return false;
		}
		return graph.isConnectedTo(this, other);
	}

	/**
	 * Devuelve los vecinos de this
	 * 
	 * @return una lista de los nodos vecinos de this
	 */
	public List<Node<?>> neighbours() {
		return graph.neighbours(this);
	}

	/**
	 * Devuelve todos los valores de las aristas de this y other
	 * 
	 * @param other el otro nodo que tiene una posible conexion con this
	 * @return una lista de valores de las aristas
	 */
	public List<Object> getEdgeValues(Node<V> other) {
		if (graph == null) {
			return null;
		}
		return graph.getEdgeValues(this, other);
	}

	/**
	 * Es el getter del value
	 * 
	 * @return el valor del nodo
	 */
	public V getValue() {
		return nodeValue;
	}

	/**
	 * Es el setter del value
	 * 
	 * @param t el nuevo valor
	 */
	public void setValue(V t) {
		this.nodeValue = t;
	}

	/**
	 * Es el getter del index
	 * 
	 * @return el index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * {@inheritDoc} Es el toString del nodo
	 * 
	 */
	@Override
	public String toString() {
		return index + " [" + nodeValue + "]";
	}
}
