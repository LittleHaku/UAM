package test;

import static org.junit.Assert.*;

import org.junit.Test;
import aplicacion.*;
import colectivoCiudadano.Ciudadano;
import notificacion.NotificacionRegistro;

/**
 * Test de prueba para el Administrador
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class AdministradorTest {

	/**
	 * Test method for
	 * {@link Administrador#validarCredenciales(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInicSesion() {
		Administrador a = new Administrador("admin", "admin");

		if (a.validarCredenciales("admin", "admin") == false)
			fail("fallo validarCredenciales");
	}

	/**
	 * Test method for {@link Administrador#addNotificacion(NotificacionAdmision)}.
	 */
	@Test
	public void testAddNotificacion() {
		Administrador a = new Administrador("admin", "admin");
		NotificacionRegistro nr = new NotificacionRegistro(new Ciudadano("a", "a", "19898989Z"));
		a.addNotificacion(nr);
		if (!a.getNotificaciones().contains(nr)) {
			fail("fallo añadir notificacion");
		}
	}

}
