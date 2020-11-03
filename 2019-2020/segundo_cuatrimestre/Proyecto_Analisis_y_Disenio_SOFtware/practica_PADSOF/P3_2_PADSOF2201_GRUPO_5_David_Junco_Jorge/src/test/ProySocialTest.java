package test;

import static org.junit.Assert.fail;

import org.junit.Test;

import exceptions.ValueTooLongException;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.proyecto.ProySocial;

/**
 * Test de prueba para el ProySocial
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class ProySocialTest {
    Ciudadano c = new Ciudadano("ciu", "ciu", "123");
    ProySocial p;

    /**
     * Test method for {@link ProySocial#getGrupo()}.
     */
    @Test
    public void testGetGrupo() {
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	if (p.getGrupo().equals("grupo 1") == false) fail("Falla getGrupo");
    }

    /**
     * Test method for {@link ProySocial#isNacional()}.
     */
    @Test
    public void testIsNacional() {
	try {
	    p = new ProySocial("mi proyecto", "desc", 69, c, "grupo 1", true);
	} catch (ValueTooLongException e) {
	    fail(e.getMessage());
	}
	if (p.isNacional() == false) fail("Fail isNacional");
    }

}