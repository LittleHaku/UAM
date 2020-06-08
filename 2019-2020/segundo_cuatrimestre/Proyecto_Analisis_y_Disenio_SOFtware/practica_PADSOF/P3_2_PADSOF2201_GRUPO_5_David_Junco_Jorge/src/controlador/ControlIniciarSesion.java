package controlador;

import java.awt.event.ActionEvent;

import exceptions.EmptyFieldException;
import exceptions.IllegalActionException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.aplicacion.Usuario;
import modelo.colectivoCiudadano.Ciudadano;
import vista.Ventana;
import vista.VistaIniciarSesion;

/**
 * Controlador de la vista {@link VistaIniciarSesion}.
 * <p>
 * Este controlador gestiona los eventos de acción que se generen en su vista
 * 
 * @see VistaIniciarSesion
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlIniciarSesion extends Controlador {
    /* Vista del controlador */
    private final VistaIniciarSesion vista;

    /**
     * Constructor del ControlIniciarSesion
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlIniciarSesion(Ventana frame, Aplicacion modelo) {
	super("iniSesion", frame, modelo);

	this.vista = (VistaIniciarSesion) frame.getVista(this.getId());
    }

    /**
     * Manejador de los botones
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	    case "submit": {
		try {
		    inicSesion();
		} catch (VisualException ex) {
		    ex.showDialog(frame);
		}

		break;
	    }
	    case "exit": {
		frame.showVista("inicial");
		break;
	    }
	}
    }

    /**
     * Funcionalidad para cuando se pulsa el botón de iniciar sesión
     * 
     * @throws VisualException se lanza cuando hay cualquier error.
     */
    private void inicSesion() throws VisualException {
	String user = vista.getUser();
	String pass = vista.getPass();

	if (user.isEmpty()) {
	    throw new EmptyFieldException("nombre de usuario", "nombre de usuario o NIF");
	}
	if (pass.isEmpty()) {
	    throw new EmptyFieldException("contraseña", "contraseña");
	}

	Usuario u = modelo.iniciarSesion(user, pass);

	if (u instanceof Ciudadano) {
	    Ciudadano c = (Ciudadano) u;
	    if (!c.isRegistroAprobado()) {
		throw new IllegalActionException("Credenciales incorrectos",
			"El nombre de usuario o la contraseña son incorrectos");
	    } else {

		frame.showVista("panelDeControl");
	    }

	} else {
	    frame.showVista("panelAdministrador");
	}
    }
}
