package complejos.pruebas;

import static org.junit.Assert.*;
import org.junit.Test;
import complejos.Complejo;

public class ComplejoTest {

	@Test
	public void testGetReal() {
	    // Crear los objetos necesarios para la prueba
	    Complejo c1 = new Complejo(3, 5);
	    Complejo c2 = new Complejo(1, -1);

	    // Comprobar que el resultado es correcto utilizando aserciones
	    assertEquals(3, c1.getReal(), 0);
	    assertEquals(1, c2.getReal(), 0);
	}

	@Test
	public void testGetImaginario() {
	    // Crear los objetos necesarios para la prueba
	    Complejo c1 = new Complejo(3, 5);
	    Complejo c2 = new Complejo(1, -1);

	    // Comprobar que el resultado es correcto utilizando aserciones
	    assertEquals(5, c1.getImaginario(),  0);
	    assertEquals(-1, c2.getImaginario(),  0);
	}

	  @Test
	  public void testSumar() {
	    // Crear los objetos necesarios para la prueba
	    Complejo c1 = new Complejo(3, 5);
	    Complejo c2 = new Complejo(1, -1);

	    // Ejecutar la operacion a probar
	    Complejo resultado = c1.sumar(c2);

	    // Comprobar que el resultado es correcto
	    // utilizando aserciones
	    assertEquals(4f, resultado.getReal(),        0);
	    assertEquals(4f, resultado.getImaginario(),  0);
	  }
	  
	  @Test
	  public void testProducto() {
	    // Crear los objetos necesarios para la prueba
	    Complejo c1 = new Complejo(3, 4);
	    Complejo c2 = new Complejo(2, -6);

	    // Ejecutar la operacion a probar
	    Complejo resultado = c1.producto(c2);

	    // Comprobar que el resultado es correcto
	    // utilizando aserciones
	    assertEquals(30f, resultado.getReal(),        0);
	    assertEquals(-10f, resultado.getImaginario(),  0);
	  }

	  @Test
	  public void testDivididoPor() {
		    // Crear los objetos necesarios para la prueba
		    Complejo c1 = new Complejo(3, 5);
		    Complejo c2 = new Complejo(1, -1);

		    // Ejecutar la operacion a probar
		    Complejo resultado = c1.divididoPor(c2);

		    // Comprobar que el resultado es correcto
		    // utilizando aserciones
		    assertEquals(-1, resultado.getReal(),       0);
		    assertEquals(4f, resultado.getImaginario(), 0);
		    
		    // Crear los objetos necesarios para la prueba
		    c1 = new Complejo(2, 3);
		    c2 = new Complejo(4, -5);

		    // Ejecutar la operacion a probar
		    resultado = c1.divididoPor(c2);

		    // Comprobar que el resultado es correcto
		    // utilizando aserciones
		    assertEquals(-7/41f, resultado.getReal(),    0);
		    assertEquals(22/41f, resultado.getImaginario(), 0);
		  }

}
