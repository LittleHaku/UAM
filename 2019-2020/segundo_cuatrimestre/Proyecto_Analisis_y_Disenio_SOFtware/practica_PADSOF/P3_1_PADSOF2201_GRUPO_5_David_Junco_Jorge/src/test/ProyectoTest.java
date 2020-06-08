package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import colectivoCiudadano.*;
import es.uam.eps.sadp.grants.CCGG;
import es.uam.eps.sadp.grants.InvalidIDException;
import es.uam.eps.sadp.grants.InvalidRequestException;
import fechaSimulada.FechaSimulada;
import notificacion.NotificacionCambioEstado;
import proyecto.*;
import proyecto.Proyecto.EstadoProyecto;

/**
 * Test de prueba para Proyecto
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ProyectoTest {
	Ciudadano c 	= new Ciudadano("ciu", "ciu", "123");
	ProySocial p 	= new ProySocial("my proyecto", "desc", 69, c, "grupo 1", true);
	ProySocial p2 	= new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);

	/**
	 * Test method for {@link Proyecto#enviar()}.
	 */
	@Test
	public void testenviar() {

		p2.setEstado(EstadoProyecto.PROPUESTO);
		try {
			if (p2.enviar() == true) {
				fail("Error en la condición de enviar proyectos");
			}
		} catch (IOException e) {
			fail("Ha habido un problema con la conexión al sistema externo");
		} catch (InvalidRequestException e) {
			fail("InvalidRequestEception al enviar");
		}

		p2.setEstado(EstadoProyecto.EN_VOTACION);

		try {
			if (p2.enviar() == false) {
				fail("No se pueden enviar proyectos correctamente");
			}
		} catch (IOException e) {
			fail("Ha habido un problema con la conexión al sistema externo");
		} catch (InvalidRequestException e) {
			fail("InvalidRequestEception al enviar");
		}

	}

	/**
	 * Test method for {@link Proyecto#consultar()}.
	 * 
	 * @throws InvalidRequestException si no se ha enviado el proyecto al CCGG
	 * @throws IOException             si hay un problema con la conexion al sistema
	 *                                 externo
	 *
	 */
	@Test
	public void testconsultar() throws IOException, InvalidRequestException {

		ProySocial p3 = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
		p3.setEstado(EstadoProyecto.EN_VOTACION);

		try {
			p3.enviar();
		} catch (IOException e) {
			fail("Ha habido un problema con la conexión al sistema externo");
		} catch (InvalidRequestException e) {
			fail("InvalidRequestEception al enviar");
		}

		// p3.setEstado(EstadoProyecto.ENVIADO);

		FechaSimulada.restablecerHoyReal();
		FechaSimulada.avanzar(60); // Según la documentación, sabemos seguro que ya se han decidido
		CCGG.getGateway().setDate(FechaSimulada.getHoy());

		try {
			if (p3.consultar() == null) {
				fail("No se pueden consultar proyectos correctamente1");
			}

		} catch (IOException e) {
			fail("Ha habido un problema con la conexión al sistema externo");
		} catch (InvalidIDException e1) {
			fail("Error al solicitar la información al sistema externo: " + e1.getMessage());
		}

	}

	/**
	 * Test method for {@link proyecto.Proyecto#addSubscrito(Ciudadano)} and
	 * {@link proyecto.Proyecto#setEstado(EstadoProyecto)}
	 */
	@Test
	public void testAddSubscrito() {
		Ciudadano c = new Ciudadano("a", "a", "12345678A");
		p.setEstado(EstadoProyecto.EN_VOTACION);
		p.addSubscrito(c);
		if (!p.getSubscritos().contains(c)) {
			fail("No se añaden nuevos subscriptores");
		}
	}

	/**
	 * Test method for {@link proyecto.Proyecto#setEstado(EstadoProyecto)} <br>
	 * Depends on {@link proyecto.Proyecto#addSubscrito(Ciudadano)}
	 */
	@Test
	public void testSetEstado() {
		Ciudadano c = new Ciudadano("a", "a", "12345678A");
		p.setEstado(EstadoProyecto.EN_VOTACION);
		p.addSubscrito(c);
		p.setEstado(EstadoProyecto.ENVIADO);

		if (p.getEstado() != EstadoProyecto.ENVIADO) {
			fail("No se actualizan los estados");
		}

		if (!c.getNotificaciones().contains(new NotificacionCambioEstado(p, EstadoProyecto.ENVIADO.toString()))) {
			fail("No se envian las notificaciones");
		}

	}

	/**
	 * Test method for {@link proyecto.Proyecto#isApoyable()}.
	 */
	@Test
	public void testisApoyable() {

		p2.setEstado(EstadoProyecto.EN_VOTACION);

		if (p2.isApoyable() == false) {
			fail("No consulta si un proyecto se puede apoyar de manera correcta");
		}

		p2.setEstado(EstadoProyecto.PROPUESTO);

		if (p2.isApoyable() == true) {
			fail("No consulta si un proyecto se puede apoyar de manera correcta");
		}

		p2.setCaducado(true);

		if (p2.isApoyable() == true) {
			fail("No consulta si un proyecto se puede apoyar de manera correcta");
		}

	}

	/**
	 * Test method for {@link proyecto.Proyecto#getNumApoyos()}.
	 */
	@Test
	public void testgetNumApoyos() {

		p2.setEstado(EstadoProyecto.EN_VOTACION);
		Ciudadano c = new Ciudadano("a", "a", "12345678A");
		Ciudadano c2 = new Ciudadano("aa", "aa", "12345678A");
		Ciudadano c3 = new Ciudadano("aaa", "aa", "12345678A");
		Colectivo cc = new Colectivo(c, "col");
		Colectivo ccc = new Colectivo(c, "col");
		cc.add(c2);
		cc.add(ccc);
		ccc.add(c3);

		if (p2.getNumApoyos() != 1) {
			fail("No se registra el apoyo al proponer");
		}

		p2.addApoyos(c);
		if (p2.getNumApoyos() != 2) {
			fail("No se registra el apoyo directo");
		}

		p2.addApoyos(cc);
		if (p2.getNumApoyos() != 4) {
			fail("No se registra el apoyo de ciudadanos de subcolectivos");
		}

		p2.addApoyos(ccc);
		if (p2.getNumApoyos() != 4) {
			fail("Se repiten apoyos de subcolectivos");
		}

		p2.addApoyos(c3);
		if (p2.getNumApoyos() != 4) {
			fail("Se repiten apoyos de ciudadanos");
		}

		c3.setBloqueado(true);
		if (p2.getNumApoyos() != 3) {
			fail("Se cuentan los apoyos de ciudadanos bloqueados");
		}

	}

	/**
	 * Test method for {@link proyecto.Proyecto#compareTo(Proyecto)}.
	 */
	@Test
	public void testcompareTo() {

		ProySocial p4 = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);

		if (p4.compareTo(p) < 0) {
			fail("No se comparan proyectos correctamente");
		}

	}

}
