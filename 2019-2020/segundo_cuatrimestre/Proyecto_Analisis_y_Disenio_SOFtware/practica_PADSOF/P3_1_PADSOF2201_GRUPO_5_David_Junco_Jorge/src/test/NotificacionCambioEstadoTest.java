package test;

import static org.junit.Assert.*;
import org.junit.Test;

import colectivoCiudadano.*;
import proyecto.*;
import notificacion.*;

/**
 * Test de prueba para la NotificacionCambioEstado
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionCambioEstadoTest {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	Proyecto p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	NotificacionCambioEstado n = new NotificacionCambioEstado(p, "ACEPTADO");

	/**
	 * Test method for {@link Notificacion#getCuerpo()}.
	 */
	@Test
	public void testGetCuerpo() {
		if (n.getCuerpo().contentEquals("El proyecto \"mi proyecto\" ha pasado al estado de ACEPTADO") == false)
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
