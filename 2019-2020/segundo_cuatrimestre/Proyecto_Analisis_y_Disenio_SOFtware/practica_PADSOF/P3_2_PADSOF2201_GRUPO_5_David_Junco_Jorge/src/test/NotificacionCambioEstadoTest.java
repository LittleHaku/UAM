package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import exceptions.ValueTooLongException;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificacionCambioEstado;
import modelo.proyecto.ProySocial;

/**
 * Test de prueba para la NotificacionCambioEstado
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionCambioEstadoTest {

    /**
     * Test method for {@link Notificacion#getCuerpo()}.
     */
    @Test
    public void testGetCuerpo() {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	NotificacionCambioEstado n = new NotificacionCambioEstado(p, "ACEPTADO");
	if (n.getCuerpo().contentEquals("El proyecto \"mi proyecto\" ha pasado al estado de ACEPTADO") == false)
	    fail("Fail getCuerpo");
    }

    /**
     * Test method for {@link Notificacion#isVisto()}.
     */
    @Test
    public void testIsVisto() {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	NotificacionCambioEstado n = new NotificacionCambioEstado(p, "ACEPTADO");
	if (n.isVisto() == true) fail("Fail isVisto");
    }

}
