package main;

import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import javax.swing.UIManager;

import componentes.ComponentConstants;
import controlador.ControladorPrincipal;
import es.uam.eps.sadp.grants.CCGG;
import exceptions.VisualException;
import fechaSimulada.FechaSimulada;
import modelo.aplicacion.Aplicacion;
import vista.Ventana;

/**
 * Es la clase Main que crea una Ventana y un Controlador. Luego hace visible la
 * ventana y esta, junto con el Controlador y el modelo con patrón Singleton
 * ejecutan la funcionalidad pedida.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class Main {
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		try {

		    /*
		     * Establecemos el look and feel y un par de propiedades que pueden mejorar la
		     * apariencia de la aplicación
		     */
		    try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			System.setProperty("awt.useSystemAAFontSettings", "on");
			System.setProperty("swing.aatext", "true");
		    } catch (Exception e) {
			System.err.println("Look and feel not set up");
			e.printStackTrace();

		    }

		    Field[] f = ComponentConstants.class.getFields();
		    for (Field field : f) {
			if (field.getName().startsWith("I_")) {
			    if (!Files.exists(Paths.get((String) field.get(ComponentConstants.class)),
				    LinkOption.NOFOLLOW_LINKS)) {
				System.err.println("Faltan los iconos");
				System.exit(1);
			    }
			}
		    }

		    FechaSimulada.restablecerHoyReal();
		    CCGG.getGateway().setDate(FechaSimulada.getHoy());

		    Ventana frame = new Ventana();

		    Aplicacion ap = Aplicacion.getInstance();
		    try {
			Aplicacion.loadFromFile(Paths.get("save.save").toFile());
		    } catch (Exception e) {
			String desc = "El archivo de guardado por defecto es save.save. Como no se ha podido"
				+ " acceder a él, se ha creado una sesión nueva. \nSi desea cargar algún archivo de datos, puede hacerlo desde el panel de control del administrador.";
			new VisualException("No se ha encontrado el archivo de guardado por defecto", desc)
				.showDialog(frame);
		    }

		    ControladorPrincipal cont = new ControladorPrincipal(frame, ap);
		    frame.setControlador(cont);
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
}
