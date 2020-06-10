package util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import element.ConstrainedGraph;
import element.Node;

/**
 * Esta clase es un comparador de grafos que permiten chequear propiedades.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <V> el tipo de dato que guardan los nodos
 * @param <T> el tipo de dato que guardan los enlaces
 */
public class BlackBoxComparator<V, T> implements Comparator<ConstrainedGraph<V, T>> {
	private final Map<Criteria, Predicate<Node<V>>> criterios;

	/**
	 * Constructor que inicializa el hashmap criterios.
	 */
	public BlackBoxComparator() {
		criterios = new HashMap<>();
	}

	/**
	 * {@inheritDoc} Compara dos grafos que permiten chequear propiedades, basandose
	 * en que le mayor cumple mas propiedades (sus nodos)
	 */
	@Override
	public int compare(ConstrainedGraph<V, T> g1, ConstrainedGraph<V, T> g2) {
		int cont1 = 0, cont2 = 0;

		for (Criteria c : criterios.keySet()) {
			if (c.equals(Criteria.EXISTENTIAL)) {
				if (g1.exists(criterios.get(c))) {
					cont1++;
				}
				if (g2.exists(criterios.get(c))) {
					cont2++;
				}
			} else if (c.equals(Criteria.UNITARY)) {
				if (g1.one(criterios.get(c))) {
					cont1++;
				}
				if (g2.one(criterios.get(c))) {
					cont2++;
				}
			} else {
				if (g1.forAll(criterios.get(c))) {
					cont1++;
				}
				if (g2.forAll(criterios.get(c))) {
					cont2++;
				}
			}
		}
		return cont1 - cont2;
	}

	/**
	 * Sirve para añadir una propiedad.
	 * 
	 * @param criterio tipo de propiedad
	 * @param pred     expresion lambda, la propiedad
	 * @return el comparador, para facilitar la programacion fluida.
	 */
	public BlackBoxComparator<V, T> addCriteria(Criteria criterio, Predicate<Node<V>> pred) {
		criterios.put(criterio, pred);
		return this;
	}
}
