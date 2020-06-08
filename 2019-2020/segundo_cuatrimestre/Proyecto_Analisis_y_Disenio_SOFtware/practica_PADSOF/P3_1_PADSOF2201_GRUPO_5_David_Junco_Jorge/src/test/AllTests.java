package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Ejecuta todos los tests
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	AplicacionTest.class,
	AdministradorTest.class,
	CiudadanoTest.class,
	ColectivoTest.class,
	NotificacionTest.class,
	ProyInfraestructuraTest.class,
	NotificacionCambioEstadoTest.class,
	NotificacionCreacionProyectoTest.class,
	NotificacionRegistroTest.class,
	ProySocialTest.class,
	ProyectoTest.class,
	UsuarioTest.class,
	SerializerTest.class
})
public class AllTests {

}
