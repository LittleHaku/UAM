package test;

import static org.junit.Assert.*;
import org.junit.Test;

import colectivoCiudadano.*;
import java.util.*;
import proyecto.*;

/**
 * Test de prueba para el ProyInfraestructura
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ProyInfraestructuraTest {
	Ciudadano c = new Ciudadano("ciu", "ciu", "123");
	ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));
	ProyInfraestructura p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");

	/**
	 * Test method for {@link ProyInfraestructura#getDistritos()}.
	 */
	@Test
	public void testGetDistritos() {
		if (p.getDistritos().equals(ad) == false)
			fail("Falla getDistritos");
	}

	/**
	 * Test method for {@link ProyInfraestructura#getImagen()}.
	 */
	@Test
	public void testGetImagen() {
		if (p.getImagen().equals("mi imagen") == false)
			fail("Falla getImagen");
	}

}
