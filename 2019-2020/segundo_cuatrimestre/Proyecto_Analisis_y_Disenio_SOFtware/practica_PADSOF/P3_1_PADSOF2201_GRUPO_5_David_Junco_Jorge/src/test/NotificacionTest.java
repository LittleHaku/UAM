package test;

import static org.junit.Assert.*;
import org.junit.Test;

import notificacion.*;

/**
 * Test de prueba para la Notificacion
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionTest {
	Notificacion n = new Notificacion("ACEPTADO");

	/**
	 * Test method for {@link Notificacion#getCuerpo()}.
	 */
	@Test
	public void testGetCuerpo() {
		if (n.getCuerpo().contentEquals("ACEPTADO") == false)
			fail("Fail getCuerpo");
	}

	/**
	 * Test method for {@link Notificacion#isVisto()}.
	 */
	@Test
	public void testIsVisto() {
		if (n.isVisto() == true)
			fail("Fail isVisto");
	}

}
