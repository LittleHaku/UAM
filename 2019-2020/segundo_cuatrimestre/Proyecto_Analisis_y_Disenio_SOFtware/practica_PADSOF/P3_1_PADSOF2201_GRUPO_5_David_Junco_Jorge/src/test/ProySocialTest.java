package test;

import static org.junit.Assert.*;
import org.junit.Test;

import proyecto.*;
import colectivoCiudadano.*;

/**
 * Test de prueba para el ProySocial
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ProySocialTest {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);

	/**
	 * Test method for {@link ProySocial#getGrupo()}.
	 */
	@Test
	public void testGetGrupo() {
		if (p.getGrupo().equals("grupo 1") == false)
			fail("Falla getGrupo");
	}

	/**
	 * Test method for {@link ProySocial#isNacional()}.
	 */
	@Test
	public void testIsNacional() {
		if (p.isNacional() == false)
			fail("Fail isNacional");
	}

}
