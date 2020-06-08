package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.notificacion.NotificacionRegistro;

/**
 * Test de prueba para la NotificacionRegistro
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionRegistroTest {
    Ciudadano c = new Ciudadano("ciu", "ciu", "123");
    NotificacionRegistro n = new NotificacionRegistro(c);

    /**
     * Test method for {@link modelo.notificacion.NotificacionRegistro#aceptar()}.
     */
    @Test
    public void testAceptar() {
	n.aceptar();
	if (c.isRegistroAprobado() == false) {
	    fail("fallo al aprobar el registro del ciudadano");
	}
    }

    /**
     * Test method for
     * {@link modelo.notificacion.NotificacionRegistro#rechazar(Aplicacion, java.lang.String)}.
     */
    @Test
    public void testRechazar() {
	Aplicacion a = Aplicacion.getInstance();
	a.addCiudadano(c);
	n.rechazar(a, null);

	if (a.encontrarCiudadano(c.getName()) != null && a.encontrarCiudadano(c.getName()).equals(c)) {
	    fail("fallo al eliminar al ciudadano rechazado");
	}
    }

    /**
     * Test method for
     * {@link modelo.notificacion.NotificacionRegistro#getCiudadano()}.
     */
    @Test
    public void testGetCiudadano() {
	if (n.getCiudadano().equals(c) == false) {
	    fail("fial getCiudadano");
	}
    }

}
