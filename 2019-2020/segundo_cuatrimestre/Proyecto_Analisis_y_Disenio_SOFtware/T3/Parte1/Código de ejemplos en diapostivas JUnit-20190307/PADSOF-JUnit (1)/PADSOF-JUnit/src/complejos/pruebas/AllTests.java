package complejos.pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * La clase AllTests lanza todas las clases de prueba de la aplicacion
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ComplejoTest.class,
				ComplejoTest2.class
				// HabitualTest.class,
				// PujaTest.class,
				// VIPTest.class,
			})

public class AllTests {

}
