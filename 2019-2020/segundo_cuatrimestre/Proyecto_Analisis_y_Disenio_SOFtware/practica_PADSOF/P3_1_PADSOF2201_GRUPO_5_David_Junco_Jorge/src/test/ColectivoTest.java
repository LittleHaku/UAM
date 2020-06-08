package test;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;

import colectivoCiudadano.*;

/**
 * Test de prueba para el Colectivo
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ColectivoTest {
	Ciudadano c = new Ciudadano("ciu", "ciu", "1234");
	Colectivo co = new Colectivo(c, "colectivoo!");

	/**
	 * Testea el metodo de aniadir ElementoColectivo del Colectivo. No se pueden
	 * aniadir Ciudadanos si estos perteneces a un Colectivo padre o hijo Test
	 * method for {@link Colectivo#add(ElementoColectivo)}.
	 */
	@Test
	public void testAdd() {
		Colectivo co2 = new Colectivo(c, "colectivo2!");
		Ciudadano c2 = new Ciudadano("ciu2", "ciu2", "12342");
		co.add(co2);
		co.add(c2);
		Set<Ciudadano> h = co.getDirectosCiudadanos();
		Set<Colectivo> h2 = co.getSubcolectivos();
		if (h.contains(c2) == false)
			fail("Fallo en Add ciudadano");
		if (h2.contains(co2) == false)
			fail("Fallo en Add colectivo");

		Ciudadano c3 = new Ciudadano("ciu3", "ciu3", "12343");
		co2.add(c3);
		co.add(c3);
		h = co.getDirectosCiudadanos();
		if (h.contains(c3))
			fail("Fallo en Add mas de los que debe, col hijo");

		Ciudadano c4 = new Ciudadano("ciu4", "ciu4", "12344");
		co.add(c4);
		co2.add(c4);
		h = co2.getDirectosCiudadanos();
		if (h.contains(c4))
			fail("Fallo en Add mas de los que debe, col padre");
	}

	/**
	 * Test method for {@link Colectivo#remove(ElementoColectivo)}.
	 */
	@Test
	public void testRemove() {
		co.remove(c);
		Set<Ciudadano> h = co.getDirectosCiudadanos();
		if (h.contains(c) == false)
			fail("Fallo en remove, no se puede eliminar al representante");
		Ciudadano c2 = new Ciudadano("ciu2", "ciu2", "12342");
		co.add(c2);
		co.remove(c2);

		Set<Ciudadano> h2 = co.getDirectosCiudadanos();
		if (h2.contains(c2) == true)
			fail("Fallo en remove, no se borra bien");
	}

	/**
	 * Test method for {@link Colectivo#getRepresentante()}.
	 */
	@Test
	public void testGetRepresentante() {
		if (co.getRepresentante().equals(c) == false)
			fail("Fallo en representante");
	}

	/**
	 * Comprueba que se pueden sacar los Ciudadanos tanto del Colectivo como de sus
	 * Subcolectivos. Test method for {@link Colectivo#getTodosCiudadanos()}.
	 */
	@Test
	public void testGetTodosCiudadanos() {
		Colectivo co2 = new Colectivo(c, "colectivo2!");
		Ciudadano c2 = new Ciudadano("ciu2", "ciu2", "12342");
		co.add(co2);
		co2.add(c2);
		Set<Ciudadano> h = co.getTodosCiudadanos();
		if (h.contains(c) == false) {
			fail("Fail en getTodosCiudadanos de Colectivo");
		}
		if (h.contains(c2) == false) {
			fail("Fail en getTodosCiudadanos de subcolectivo");
		}
	}

}
