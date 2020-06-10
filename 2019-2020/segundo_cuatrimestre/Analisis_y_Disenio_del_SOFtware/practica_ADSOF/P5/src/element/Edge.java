package element;

/**
 * Es la clase que representa aristas de tipo generico E
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <E> es el tipo generico del valor entre dos nodos
 */
public class Edge<E> {
	private final Node<?> from;
	private final Node<?> to;
	private final E weight;

	/**
	 * Es el constructor
	 * 
	 * @param from   el nodo inicial de la arista
	 * @param weight el nodo final de la arista
	 * @param to     el valor que hay en la arista
	 */
	public Edge(Node<?> from, E weight, Node<?> to) {
		this.from = from;
		this.weight = weight;
		this.to = to;
	}

	/**
	 * Es el getter del nodo inicial
	 * 
	 * @return from
	 */
	public Node<?> getFrom() {
		return from;
	}

	/**
	 * Es el getter del nodo final
	 * 
	 * @return to
	 */
	public Node<?> getTo() {
		return to;
	}

	/**
	 * Es el getter del valor de la arista
	 * 
	 * @return weight
	 */
	public E getWeight() {
		return weight;
	}

	/**
	 * {@inheritDoc} es el toString de la arista
	 */
	@Override
	public String toString() {
		return "( " + from.getIndex() + " --" + weight + "--> " + to.getIndex() + " )";
	}
}
