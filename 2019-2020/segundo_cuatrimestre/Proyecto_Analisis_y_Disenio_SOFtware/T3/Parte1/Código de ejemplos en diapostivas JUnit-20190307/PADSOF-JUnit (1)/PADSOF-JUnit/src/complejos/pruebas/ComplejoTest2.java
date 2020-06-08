package complejos.pruebas;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import complejos.Complejo;

public class ComplejoTest2 {
	  private Complejo c1;
	  private Complejo c2;

	  @Before
	  public void setUp() throws Exception {
	    c1 = new Complejo(3, 5);
	    c2 = new Complejo(1, -1);
	  }

	  @Test
	  public void testSumar() {
	    Complejo resultado = c1.sumar(c2);
	    assertEquals(4f, resultado.getReal(),       0);
	    assertEquals(4f, resultado.getImaginario(), 0);
	  }

	 @Test
	  public void testDividir() {
	    Complejo resultado = c1.divididoPor(c2);
	    assertEquals(-1f, resultado.getReal(), 	0);
	    assertEquals(4f, resultado.getImaginario(), 0);
	  }

	  @Test(expected = ArithmeticException.class)
	  public void testDividirPorCero(){
	    Complejo cero = new Complejo(0, 0);
	    Complejo resultado = c1.divididoPor(cero);
	  }


}
