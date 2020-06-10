package jUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import element.ConstrainedGraph;
import element.Node;

/**
 * Test de la clase ConstrainedGraph
 * 
 * @author Junco de las Heras and Marta Vaquerizo
 *
 */
class ConstrainedGraphTest {
	private ConstrainedGraph<Integer, Integer> g;
	private Node<Integer> n1;
	private Node<Integer> n2;
	private Node<Integer> n3;
	private Node<Integer> n4;

	/**
	 * {@inheritDoc} Se ejecuta antes de cada funci�n.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		g = new ConstrainedGraph<Integer, Integer>();
		n1 = new Node<Integer>(1);
		n2 = new Node<Integer>(2);
		n3 = new Node<Integer>(3);
		n4 = new Node<Integer>(3);
		g.addAll(Arrays.asList(n1, n2, n3));
		g.connect(n1, 1, n2);
		g.connect(n1, 7, n3);
		g.connect(n2, 1, n3);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n exists
	 */
	@Test
	void testExists() {
		boolean flag = g.exists(n -> n.isConnectedTo(n2));
		assertSame(flag, true);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n exists
	 */
	@Test
	void test2Exists() {
		boolean flag = g.exists(n -> n.isConnectedTo(n4));
		assertSame(flag, false);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n forAll
	 */
	@Test
	void testForAll() {
		boolean flag = g.forAll(n -> n.equals(n3) || n.isConnectedTo(n3));
		assertSame(flag, true);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n forAll
	 */
	@Test
	void test2ForAll() {
		boolean flag = g.forAll(n -> n.isConnectedTo(n1));
		assertSame(flag, false);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n one
	 */
	@Test
	void testOne() {
		boolean flag = g.one(n -> n.isConnectedTo(n2));
		assertSame(flag, true);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n one
	 */
	@Test
	void test2One() {
		boolean flag = g.one(n -> n.isConnectedTo(n4));
		assertSame(flag, false);
	}
}
