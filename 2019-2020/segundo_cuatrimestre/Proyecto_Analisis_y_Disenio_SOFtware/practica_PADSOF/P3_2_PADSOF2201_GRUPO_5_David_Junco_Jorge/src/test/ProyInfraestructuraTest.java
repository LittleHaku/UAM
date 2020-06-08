package test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import exceptions.ValueTooLongException;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.proyecto.Distrito;
import modelo.proyecto.ProyInfraestructura;

/**
 * Test de prueba para el ProyInfraestructura
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ProyInfraestructuraTest {
    Ciudadano c = new Ciudadano("ciu", "ciu", "123");
    ArrayList<Distrito> ad = new ArrayList<>(Arrays.asList(new Distrito("tetuan")));

    /**
     * Test method for {@link ProyInfraestructura#getDistritos()}.
     */
    @Test
    public void testGetDistritos() {
	ProyInfraestructura p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	if (p.getDistritos().equals(ad) == false) fail("Falla getDistritos");
    }

    /**
     * Test method for {@link ProyInfraestructura#getImagen()}.
     */
    @Test
    public void testGetImagen() {
	ProyInfraestructura p = null;
	try {
	    p = new ProyInfraestructura("mi proyecto", "mi desc", 69, c, ad, "mi imagen");
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	if (p.getImagen().equals("mi imagen") == false) fail("Falla getImagen");
    }

}
