package controlador;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import componentes.IconDialog;
import exceptions.EmptyFieldException;
import exceptions.InternalErrorException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import vista.Ventana;
import vista.VistaCrearColectivo;

/**
 * Controlador de la vista {@link VistaCrearColectivo}.
 * <p>
 * Este controlador gestiona los eventos de acción y de ratón que se generen en
 * su vista
 * 
 * @see VistaCrearColectivo
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlCrearColectivo extends Controlador {

    /* Vista del controlador */
    private final VistaCrearColectivo vista;

    /**
     * Constructor del ControlCrearColectivo
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlCrearColectivo(Ventana frame, Aplicacion modelo) {
	super("crearColectivo", frame, modelo);
	this.vista = (VistaCrearColectivo) frame.getVista(this.getId());
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
		    crearColectivo();
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		    return;
		}

		String desc = "Se ha creado el colectivo \"" + vista.getNombre() + "\"";

		String sname = vista.getNombreSubcolectivo();
		if (!sname.equals("")) {
		    desc += ". Es un subcolectivo de \"" + vista.getNombreSubcolectivo() + "\".";
		}

		Consumer<ActionEvent> exitAction = event -> {
		    frame.showVista("panelDeControl");
		    vista.clearFields();
		};

		IconDialog.successDialog(frame, "Colectivo creado", desc, exitAction);

		break;
	    }
	    case "exit": {

		frame.showVista("panelDeControl");
		break;
	    }
	}
    }

    /**
     * Funcionalidad para cuando se pulsa el boton de crear colectivo.
     * 
     * @throws VisualException se lanza cuando hay cualquier error.
     */
    private void crearColectivo() throws VisualException {
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();

	String nombre = vista.getNombre();
	boolean subcolecivo = vista.isSubcolectivo();
	String nameSub = vista.getNombreSubcolectivo();
	if (nombre.isEmpty()) {
	    throw new EmptyFieldException("nombre del colectivo");
	}
	Colectivo col = new Colectivo(c, nombre);
	modelo.addColectivo(col);

	if (subcolecivo) {
	    Colectivo colPadre = modelo.encontrarColectivo(nameSub);
	    if (colPadre == null) {
		throw new InternalErrorException("Colectivo no encontrado");
	    }
	    colPadre.add(col);
	}
    }

    @Override
    public void initialise() {
	vista.clearFields();
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();

	List<String> nombres = c.getPertenece().stream().filter(e -> e.getRepresentante().equals(c))
		.map(co -> co.getName()).collect(Collectors.toList());

	vista.setColectivos(nombres);
    }
}
