package jUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Ejecuta todos los tests
 * 
 * @author Junco de las Heras and Marta Vaquerizo
 */
@RunWith(Suite.class)
@SuiteClasses({
	BlackBoxComparatorTest.class,
	ConstrainedGraphTest.class,
	RuleSetTest.class
})
public class AllTests {

}
