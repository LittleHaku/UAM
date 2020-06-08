package controlador;

import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.function.Consumer;

import componentes.IconDialog;
import exceptions.IllegalActionException;
import exceptions.InternalErrorException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import vista.Ventana;
import vista.VistaApuntarseColectivo;

/**
 * Controlador de la vista {@link VistaApuntarseColectivo}
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlApuntarseColectivo extends Controlador {

    /* Vista del controlador */
    private final VistaApuntarseColectivo vista;

    /**
     * Constructor del ControlApuntarseColectivo
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlApuntarseColectivo(Ventana frame, Aplicacion modelo) {
	super("apuntarseColectivo", frame, modelo);
	this.vista = (VistaApuntarseColectivo) frame.getVista(this.getId());
    }

    /**
     * {@inheritDoc} Manejador de los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	    case "submit": {
		try {
		    apuntarseColectivo();
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		}
		break;
	    }
	    case "exit": {
		frame.showVista("panelDeControl");
		break;
	    }
	}
    }

    /**
     * Funcionalidad para cuando se pulsa el boton submit.
     * 
     * @throws VisualException se lanza cuando hay cualquier error. Es una excepcion
     *                         visual.
     */
    public void apuntarseColectivo() throws VisualException {
	String name = vista.getNombre();

	if (name.equals("")) {
	    throw new IllegalActionException("No hay selección de ningún colectivo", "Seleccione uno por favor.");
	}
	Colectivo col = modelo.encontrarColectivo(name);

	if (col == null) {
	    throw new InternalErrorException("Colectivo no encontrado");
	}
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();

	if (col.add(c) == false) {
	    throw new InternalErrorException("No se pudo apuntar al colectivo " + name + ".");
	}

	Consumer<ActionEvent> exitAction = event -> {
	    frame.showVista("panelDeControl");
	};

	IconDialog.successDialog(frame, "Colectivo creado", "Se ha apuntado al colectivo " + name + ".", exitAction);
    }

    @Override
    public void initialise() {
	vista.clearColectivo();
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	Set<Colectivo> totCol = modelo.getColectivos();
	for (Colectivo apCol : totCol) {
	    if (apCol.canAdd(c)) {
		vista.addColectivo(apCol.getName());
	    }
	}
    }
}
