package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import colectivoCiudadano.*;
import notificacion.*;
import proyecto.*;

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
	 * {@link colectivoCiudadano.Ciudadano#validarCredenciales(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInicSesion() {
		if (c.validarCredenciales("ciu", "ciu") == false)
			fail("No se puede iniciar sesion");
	}

	/**
	 * Test method for {@link colectivoCiudadano.Ciudadano#added(Colectivo)}.
	 */
	@Test
	public void testAdded() {
		Colectivo co = new Colectivo(c, "col");
		c.added(co);

		if (c.getPertenece().contains(co) == false || c.getRepresenta().contains(co) == false)
			fail("No se inserta bien");
	}

	/**
	 * Test method for {@link colectivoCiudadano.Ciudadano#removed(Colectivo)}.
	 */
	@Test
	public void testRemoved() {
		Colectivo co = new Colectivo(c, "col");
		c.added(co);
		c.removed(co);

		if (c.getPertenece().contains(co) == true)
			fail("No se borra bien");
	}
	
	/**
	 * Test method for {@link colectivoCiudadano.Ciudadano#getProyPropuestos()}
	 */
	@Test
	public void testProyPropuesto() {
		Ciudadano c = new Ciudadano("c","c","12345678A");
		Proyecto p = new ProySocial("t", "t", 0, c, "t", false);

		if(!c.getProyPropuestos().contains(p)) {
			fail("No se guardan los proyectos propuestos");
		}
	}

	/**
	 * Test method for
	 * {@link colectivoCiudadano.Ciudadano#addNotificacion(Notificacion)}.
	 */
	@Test
	public void testAddNotificacion() {
		ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
		Proyecto p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");

		NotificacionCambioEstado n = new NotificacionCambioEstado(p, "notificacion");
		c.addNotificacion(n);
	}
	
	/**
	 * Test method for {@link colectivoCiudadano.Ciudadano#getTodosProyPropuestos()}
	 */
	@Test
	public void testTodosProyPropuesto() {
		Ciudadano c = new Ciudadano("c","c","12345678A");
		Proyecto p = new ProySocial("t", "t", 0, c, "t", false);
		
		Colectivo col1= new Colectivo(c,"nom");
		Proyecto p2 = new ProySocial("r", "r", 0, col1, "r", false);
		
		if(!c.getTodosProyPropuestos().contains(p) || !c.getTodosProyPropuestos().contains(p2) ) {
			fail("No se guardan todos los proyectos propuestos");
		}
	}

}
