package element;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Esta clase hereda de Graph, ya que es un grafo que permite chequear
 * propiedades.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <V> el tipo de dato que guardan los nodos
 * @param <E> el tipo de dato que guardan los enlaces
 */
public class ConstrainedGraph<V, E> extends Graph<V, E> {
	Node<V> witness;

	/**
	 * Constructor de la clase padre.
	 */
	public ConstrainedGraph() {
		super();
	}

	/**
	 * Chequea si todos los nodos cumplen la propiedad pasada por argumento.
	 * 
	 * @param pred expresion lambda donde se indica la propiedad
	 * @return true si todos los nodos cumplen la propiedad, false en caso
	 *         contrario.
	 */
	public boolean forAll(Predicate<Node<V>> pred) {
		int cont = 0;

		for (Node<V> n : super.lNode) {
			if (pred.test(n)) {
				cont++;
			}
		}
		return cont == this.size();
	}

	/**
	 * Chequea si todos al menos un nodo cumple la propiedad pasada por argumento.
	 * 
	 * @param pred expresion lambda donde se indica la propiedad
	 * @return true si al menos un nodo cumple la propiedad, false en caso
	 *         contrario.
	 */
	public boolean exists(Predicate<Node<V>> pred) {
		witness = null; // reseteo de witness para que no se guarde lo anterior
		for (Node<V> n : super.lNode) {
			if (pred.test(n)) {
				witness = n;
				return true;
			}
		}

		return false;
	}

	/**
	 * Chequea si solo un nodo cumplen la propiedad pasada por argumento.
	 * 
	 * @param pred expresion lambda donde se indica la propiedad
	 * @return true si solo un nodo cumple la propiedad, false en caso contrario.
	 */
	public boolean one(Predicate<Node<V>> pred) {
		int cont = 0;
		for (Node<V> n : super.lNode) {
			if (pred.test(n)) {
				cont++;
				if (cont > 1) {
					return false;
				}
			}
		}

		return cont == 1;
	}

	/**
	 * Devuelve el valor opcional, donde se guarda uno de los nodos que, en una
	 * propiedad existencial, cumple la propiedad.
	 * 
	 * @return el valor opcional
	 */
	public Optional<Node<V>> getWitness() {
		return Optional.ofNullable(witness);
	}
}
