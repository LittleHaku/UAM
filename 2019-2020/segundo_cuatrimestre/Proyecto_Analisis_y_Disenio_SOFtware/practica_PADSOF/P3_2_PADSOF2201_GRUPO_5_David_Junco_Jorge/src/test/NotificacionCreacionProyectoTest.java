package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import exceptions.ValueTooLongException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificacionCreacionProyecto;
import modelo.proyecto.ProySocial;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;

/**
 * Test de prueba para la NotificacionCreacionProyecto
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionCreacionProyectoTest {

    /**
     * Test method for
     * {@link modelo.notificacion.NotificacionCreacionProyecto#aceptar()}.
     */
    @Test
    public void testAceptar() {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	NotificacionCreacionProyecto n = new NotificacionCreacionProyecto(p);
	n.aceptar();
	if (!p.getEstado().equals(Proyecto.EstadoProyecto.EN_VOTACION)) {
	    fail("fail en test");
	}
    }

    /**
     * Test method for
     * {@link modelo.notificacion.NotificacionCreacionProyecto#rechazar(Aplicacion, java.lang.String)}.
     */
    @Test
    public void testRechazar() {
	Aplicacion a = Aplicacion.getInstance();
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	NotificacionCreacionProyecto n = new NotificacionCreacionProyecto(p);

	p.setEstado(EstadoProyecto.EN_VOTACION);
	p.getCreador().proponeProyecto(p);

	a.addCiudadano(c);
	a.addProyecto(p);

	n.rechazar(a, "");

	if (!p.getCreador().getRepresentante().getNotificaciones()
		.contains(new Notificacion("El proyecto \"mi proyecto\" ha sido rechazado alegando: \n"))) {
	    fail("fallo al enviar la notificación de rechazo");
	}
	if (a.encontrarProyecto(p.getTitulo()).contains(p)) {
	    fail("fallo al eliminar el proyecto rechazado");
	}

    }

    /**
     * Test method for
     * {@link modelo.notificacion.NotificacionCreacionProyecto#getProyecto()}.
     */
    @Test
    public void testGetProyecto() {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	NotificacionCreacionProyecto n = new NotificacionCreacionProyecto(p);
	if (n.getProyecto().equals(p) == false) {
	    fail("fail en getProyecto");
	}
    }

}
