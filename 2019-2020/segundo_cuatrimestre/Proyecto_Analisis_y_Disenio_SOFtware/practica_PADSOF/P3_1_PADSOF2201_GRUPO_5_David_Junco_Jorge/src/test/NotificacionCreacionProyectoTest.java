package test;

import static org.junit.Assert.*;
import org.junit.Test;

import aplicacion.Aplicacion;
import colectivoCiudadano.*;
import proyecto.*;
import notificacion.*;

/**
 * Test de prueba para la NotificacionCreacionProyecto
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class NotificacionCreacionProyectoTest {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	Proyecto p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	NotificacionCreacionProyecto n = new NotificacionCreacionProyecto(p);

	/**
	 * Test method for {@link notificacion.NotificacionCreacionProyecto#aceptar()}.
	 */
	@Test
	public void testAceptar() {
		n.aceptar();
		if (!p.getEstado().equals(Proyecto.EstadoProyecto.EN_VOTACION)) {
			fail("fail en test");
		}
	}

	/**
	 * Test method for
	 * {@link notificacion.NotificacionCreacionProyecto#rechazar(Aplicacion, java.lang.String)}.
	 */
	@Test
	public void testRechazar() {
		Aplicacion a = new Aplicacion();
		Ciudadano c = new Ciudadano("ciu", "ciu", "123");
		Proyecto p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
		NotificacionCreacionProyecto n = new NotificacionCreacionProyecto(p);

		a.addCiudadano(c);
		a.addProyecto(p);

		n.rechazar(a, "");

		if (!p.getCreador().getRepresentante().getNotificaciones()
				.contains(new Notificacion("El proyecto ha sido rechazado alegando: "))) {
			fail("fallo al enviar la notificación de rechazo");
		}
		if (a.encontrarProyecto(p.getTitulo()).contains(p)) {
			fail("fallo al eliminar el proyecto rechazado");
		}

	}

	/**
	 * Test method for
	 * {@link notificacion.NotificacionCreacionProyecto#getProyecto()}.
	 */
	@Test
	public void testGetProyecto() {
		if (n.getProyecto().equals(p) == false) {
			fail("fail en getProyecto");
		}
	}

}
