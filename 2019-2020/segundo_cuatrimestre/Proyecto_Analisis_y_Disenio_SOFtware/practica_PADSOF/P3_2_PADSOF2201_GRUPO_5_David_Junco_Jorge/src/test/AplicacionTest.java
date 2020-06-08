package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import exceptions.ValueTooLongException;
import fechaSimulada.FechaSimulada;
import modelo.aplicacion.Administrador;
import modelo.aplicacion.Aplicacion;
import modelo.aplicacion.Usuario;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import modelo.notificacion.NotificacionCreacionProyecto;
import modelo.notificacion.NotificacionRegistro;
import modelo.proyecto.Distrito;
import modelo.proyecto.ProyInfraestructura;
import modelo.proyecto.ProySocial;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;

/**
 * Test de prueba para Aplicacion
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class AplicacionTest {

    Aplicacion app = Aplicacion.getInstance();
    Ciudadano c = new Ciudadano("ciu", "ciu", "123");

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#iniciarSesion(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testiniciarSesion() {

	app.addCiudadano(c);
	Usuario u = null;
	try {
	    u = app.iniciarSesion(c.getName(), c.getPassword());
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}
	if (u == null) {
	    fail("No se puede iniciar sesion");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#cerrarSesion()}.
     */
    @Test
    public void testcerrarSesion() {

	try {
	    app.iniciarSesion(c.getName(), c.getPassword());
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	app.cerrarSesion();

	if (app.getUsrActivo() != null) {
	    fail("No se puede cerrar sesion");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#actualizarCaducados()}.
     */
    @Test
    public void testactualizarCaducados() {

	FechaSimulada.restablecerHoyReal();

	ProySocial p = null;
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	p.setEstado(EstadoProyecto.EN_VOTACION);
	app.addProyecto(p);

	FechaSimulada.avanzar(-40);

	p.setUltimoApoyo(FechaSimulada.getHoy());

	app.actualizarCaducados();

	if (p.isCaducado() == false) {
	    fail("No funciona el sistema de proyectos caducados correctamente");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#modoAdministrador()}.
     */
    @Test
    public void testModoAdministrador() {

	try {
	    app.iniciarSesion("admin", "admin");
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	if (app.modoAdministrador() == false) {
	    fail("El modo administrador no funciona correctamente");
	}
    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#registro(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testregistro() {
	Ciudadano c2 = new Ciudadano("ciu2", "ciu2", "1234");

	try {
	    app.registro(c2.getName(), c2.getPassword(), c2.getNif());
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	Administrador admin = null;
	try {
	    admin = (Administrador) app.iniciarSesion("admin", "admin");
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	if (app.encontrarCiudadano(c2.getName()) == null) {
	    fail("No se puede registrar un nuevo usuario");
	}

	if (admin.getNotificaciones().contains(new NotificacionRegistro(c2)) == false) {
	    fail("No se añaden norificaciones correctamente");
	}
    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#addProyectoParaAutorizar(Proyecto)}.
     */
    @Test
    public void testaddProyectoParaAutorizar() {
	ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
	Proyecto p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	Administrador admin = null;
	try {
	    admin = (Administrador) app.iniciarSesion("admin", "admin");
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	app.addProyectoParaAutorizar(p);

	if (app.getTodosProyectos().contains(p) == false) {
	    fail("No se puede añadir proyectos para autorizar");
	}

	if (admin.getNotificaciones().contains(new NotificacionCreacionProyecto(p)) == false) {
	    fail("No se añaden norificaciones correctamente");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#addColectivo(Colectivo)}.
     */
    @Before
    public void testaddColectivo() {
	Colectivo col = new Colectivo(c, "col_1");
	try {
	    app.addColectivo(col);
	} catch (Exception ex) {
	    /*
	     * No se lanza fail porque es un @Before y se testea tanto como que lance
	     * excepciones como que no.
	     */
	}
	if (app.getColectivos().contains(col) == false) {
	    fail("No se puede añadir Colectivos a la app");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#addProyecto(Proyecto)}.
     */
    @Before
    public void testaddProyecto() {
	ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
	Proyecto p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto 2", "mi desc", 100, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	app.addProyecto(p);

	if (app.getTodosProyectos().contains(p) == false) {
	    fail("No se puede añadir proyectos a la app");
	}
    }

    /**
     * Test method for {@link modelo.aplicacion.Aplicacion#addCiudadano(Ciudadano)}.
     */
    @Before
    public void testaddCiudadano() {
	app.addCiudadano(c);

	if (app.getTodosUsuarios().contains(c) == false) {
	    fail("No se puede añadir ciudadanos a la app");
	}
    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#getInformeAfinidad(Colectivo, Colectivo)}.
     */
    @Test
    public void testgetInformeAfinidad() {
	Colectivo col2 = new Colectivo(c, "col_2");
	Colectivo col3 = new Colectivo(c, "col_3");

	try {
	    app.addColectivo(col2);
	    app.addColectivo(col3);
	} catch (Exception ex) {
	    fail("Se ha recibido una excepcion");
	}

	ProySocial p1 = null;
	ProySocial p2 = null;
	try {
	    p1 = new ProySocial("mi proyecto 1", "desc", 69, col2, "grupo 1", true);
	    p2 = new ProySocial("mi proyecto 2", "desc", 69, col3, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	app.addProyecto(p1);

	app.addProyecto(p2);

	if (app.getInformeAfinidad(col3, col2) != 0) {
	    fail("No se obtiene correctamente el informe de afinidad1");
	}

    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#isProyectoEnviable(Proyecto)}.
     */
    @Test
    public void testisProyectoEnviable() {
	ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
	Proyecto p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto", "mi desc", 100, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}

	if (app.isProyectoEnviable(p) == false) {
	    fail("No pueden enviar proyectos");
	}

    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#encontrarColectivo(java.lang.String)}.
     */
    @Test
    public void testencontrarColectivo() {

	if (app.encontrarColectivo("col_1") == null) {
	    fail("No puede buscar colectivos por su nombre");
	}

    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#encontrarProyecto(java.lang.String)}.
     */
    @Test
    public void testencontrarProyecto() {

	if (app.encontrarProyecto("mi proyecto") == null) {
	    fail("No puede buscar proyectos por su nombre");
	}

    }

    /**
     * Test method for
     * {@link modelo.aplicacion.Aplicacion#encontrarCiudadano(java.lang.String)}.
     */
    @Test
    public void testencontrarCiudadano() {
	app.addCiudadano(new Ciudadano("ciu", "ciu", "11"));
	if (app.encontrarCiudadano("ciu") == null) {
	    fail("No puede buscar ciudadanos por su nombre");
	}

    }

}
