package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import exceptions.ValueTooLongException;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificacionCambioEstado;
import modelo.proyecto.Distrito;
import modelo.proyecto.ProyInfraestructura;
import modelo.proyecto.ProySocial;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;

/**
 * Test de prueba para el Ciudadano
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class CiudadanoTest {

    Ciudadano c = new Ciudadano("ciu", "ciu", "123");

    /**
     * Test method for
     * {@link modelo.colectivoCiudadano.Ciudadano#validarCredenciales(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testInicSesion() {
	if (c.validarCredenciales("ciu", "ciu") == false) fail("No se puede iniciar sesion");
    }

    /**
     * Test method for {@link modelo.colectivoCiudadano.Ciudadano#added(Colectivo)}.
     */
    @Test
    public void testAdded() {
	Colectivo co = new Colectivo(c, "col");
	c.added(co);

	if (c.getPertenece().contains(co) == false || c.getColectRepresentados().contains(co) == false)
	    fail("No se inserta bien");
    }

    /**
     * Test method for
     * {@link modelo.colectivoCiudadano.Ciudadano#removed(Colectivo)}.
     */
    @Test
    public void testRemoved() {
	Colectivo co = new Colectivo(c, "col");
	c.added(co);
	c.removed(co);

	if (c.getPertenece().contains(co) == true) fail("No se borra bien");
    }

    /**
     * Test method for
     * {@link modelo.colectivoCiudadano.Ciudadano#getProyPropuestos()}
     */
    @Test
    public void testProyPropuesto() {
	Ciudadano c = new Ciudadano("c", "c", "12345678A");
	Proyecto p = null;
	try {
	    p = new ProySocial("t", "t", 0, c, "t", false);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	p.setEstado(EstadoProyecto.EN_VOTACION);
	p.getCreador().proponeProyecto(p);

	if (!c.getProyPropuestos().contains(p)) {
	    fail("No se guardan los proyectos propuestos");
	}
    }

    /**
     * Test method for
     * {@link modelo.colectivoCiudadano.Ciudadano#addNotificacion(Notificacion)}.
     */
    @Test
    public void testAddNotificacion() {
	ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
	Proyecto p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	NotificacionCambioEstado n = new NotificacionCambioEstado(p, "notificacion");
	c.addNotificacion(n);
    }

    /**
     * Test method for
     * {@link modelo.colectivoCiudadano.Ciudadano#getTodosProyPropuestos()}
     */
    @Test
    public void testTodosProyPropuesto() {
	Ciudadano c = new Ciudadano("c", "c", "12345678A");
	Proyecto p = null;
	try {
	    p = new ProySocial("t", "t", 0, c, "t", false);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	Colectivo col1 = new Colectivo(c, "nom");
	Proyecto p2 = null;
	try {
	    p2 = new ProySocial("r", "r", 0, col1, "r", false);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	p.setEstado(EstadoProyecto.EN_VOTACION);
	p.getCreador().proponeProyecto(p);

	p2.setEstado(EstadoProyecto.EN_VOTACION);
	p2.getCreador().proponeProyecto(p2);

	if (!c.getTodosProyPropuestos().contains(p) || !c.getTodosProyPropuestos().contains(p2)) {
	    fail("No se guardan todos los proyectos propuestos");
	}
    }

}
