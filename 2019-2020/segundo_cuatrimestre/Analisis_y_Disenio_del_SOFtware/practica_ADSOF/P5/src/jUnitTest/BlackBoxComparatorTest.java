package jUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import element.ConstrainedGraph;
import element.Node;
import util.BlackBoxComparator;
import util.Criteria;

/**
 * Test de la clase BlackBoxComparator
 * 
 * @author Junco de las Heras and Marta Vaquerizo
 *
 */
class BlackBoxComparatorTest {
	private ConstrainedGraph<Integer, Integer> g1;
	private ConstrainedGraph<Integer, Integer> g2;
	private BlackBoxComparator<Integer, Integer> bbc;

	/**
	 * {@inheritDoc} Se ejecuta antes de cada funci�n.
	 */
	@BeforeEach
	public void setUp() {
		g1 = new ConstrainedGraph<Integer, Integer>();
		g2 = new ConstrainedGraph<Integer, Integer>();

		g1.addAll(Arrays.asList(new Node<Integer>(4)));

		Node<Integer> n1 = new Node<Integer>(1);
		Node<Integer> n2 = new Node<Integer>(2);
		Node<Integer> n3 = new Node<Integer>(3);
		g2.addAll(Arrays.asList(n1, n2, n3));
		g2.connect(n1, 1, n2);
		g2.connect(n1, 7, n3);
		g2.connect(n2, 1, n3);
		bbc = new BlackBoxComparator<Integer, Integer>();
		bbc.addCriteria(Criteria.EXISTENTIAL, n -> n.isConnectedTo(1))
				.addCriteria(Criteria.UNITARY, n -> n.neighbours().isEmpty())
				.addCriteria(Criteria.UNIVERSAL, n -> n.getValue().equals(4));
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n compare
	 */
	@Test
	void testCompare() {
		List<ConstrainedGraph<Integer, Integer>> cgs = Arrays.asList(g1, g2);
		Collections.sort(cgs, bbc);

		assertEquals(g2, cgs.get(0));
	}
}
