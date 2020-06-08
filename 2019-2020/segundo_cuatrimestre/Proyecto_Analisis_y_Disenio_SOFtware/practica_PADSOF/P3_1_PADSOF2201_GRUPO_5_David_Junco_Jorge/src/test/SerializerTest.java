package test;

import static org.junit.Assert.*;

import org.junit.Test;

import aplicacion.Aplicacion;
import aplicacion.Serializer;
import proyecto.Proyecto;

/**
 * Test de prueba para el Serializer
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class SerializerTest {

	/**
	 * Test method for {@link aplicacion.Serializer#guardarValores()} and
	 * {@link aplicacion.Serializer#recuperarValores()}
	 */
	@Test
	public void testFuncionalidad() {
		Serializer s = new Serializer();
		int a1 = Aplicacion.getApoyosMinimos();
		int a2 = Proyecto.getId_siguiente();

		s.guardarValores();
		Aplicacion.setApoyosMinimos(1000);
		Proyecto.setId_siguiente(10000);
		s.recuperarValores();
		if (Aplicacion.getApoyosMinimos() != a1) {
			fail("Fallo al guardar y recuperar los apoyos");
		}
		if (Proyecto.getId_siguiente() != a2) {
			fail("Fallo al guardar y recuperar el id siguiente de proyecto");
		}

	}

}
