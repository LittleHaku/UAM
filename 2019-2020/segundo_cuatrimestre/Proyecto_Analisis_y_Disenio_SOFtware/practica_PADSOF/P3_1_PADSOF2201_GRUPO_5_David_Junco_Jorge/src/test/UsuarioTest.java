package test;

import static org.junit.Assert.*;

import org.junit.Test;

import colectivoCiudadano.*;

/**
 * Test de prueba para Usuario
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class UsuarioTest {

	Ciudadano c = new Ciudadano("ciu", "ciu", "123");

	/**
	 * Test method for {@link aplicacion.Usuario#equals(Object)}.
	 */
	@Test
	public void testequals() {
		Ciudadano c2 = new Ciudadano("ciu", "ciu", "123");

		if (c.equals(c2) == false)
			fail("No se comparan usuarios correctamente");
	}

}
