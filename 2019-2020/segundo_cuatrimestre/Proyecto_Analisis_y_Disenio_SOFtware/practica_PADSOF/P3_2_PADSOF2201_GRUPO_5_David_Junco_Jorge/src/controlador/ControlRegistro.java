package controlador;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import componentes.IconDialog;
import exceptions.EmptyFieldException;
import exceptions.IllegalActionException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import vista.Ventana;
import vista.VistaRegistroSesion;

/**
 * Controlador de la vista {@link VistaRegistroSesion}.
 * <p>
 * Este controlador gestiona los eventos de acción y de ratón que se generen en
 * su vista
 * 
 * @see VistaRegistroSesion
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlRegistro extends Controlador {

    /* Vista del controlador */
    private final VistaRegistroSesion vista;

    /**
     * Constructor del ControlRegistro
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlRegistro(Ventana frame, Aplicacion modelo) {
	super("registro", frame, modelo);
	this.vista = (VistaRegistroSesion) frame.getVista(this.getId());
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
		    registro();
		} catch (VisualException ex) {
		    ex.showDialog(frame);
		    return;
		}

		String desc = "Su petición de registro ha sido enviada al administrador. "
			+ "Podra iniciar sesión en cuanto al administrador acepte su solicitud";

		Consumer<ActionEvent> action = event -> {
		    frame.showVista("inicial");
		};

		IconDialog.successDialog(frame, "Registro solicitado al administrador", desc, action);

		break;
	    }

	    case "exit": {
		frame.showVista("inicial");
		break;
	    }
	}
    }

    /**
     * Funcionalidad para cuando se pulsa el boton de registrarse.
     * 
     * @throws VisualException se lanza cuando hay cualquier error.
     */
    private void registro() throws VisualException {
	String user = vista.getUser();
	String pass = vista.getPass();
	String pass2 = vista.getPass2();
	String nif = vista.getNif();

	if (user.isEmpty()) {
	    throw new EmptyFieldException("nombre de usuario");
	}
	if (pass.isEmpty()) {
	    throw new EmptyFieldException("contraseña");
	}
	if (pass2.isEmpty()) {
	    throw new EmptyFieldException("contraseña repetida");
	}
	if (nif.isEmpty()) {
	    throw new EmptyFieldException("NIF");
	}
	if (!pass.equals(pass2)) {
	    throw new IllegalActionException("Error en contraseña repetida", "Las contraseñas no coinciden.");
	}

	modelo.registro(user, pass, nif);

	vista.clearFields();
    }

    @Override
    public void initialise() {
	vista.clearFields();
    }
}
