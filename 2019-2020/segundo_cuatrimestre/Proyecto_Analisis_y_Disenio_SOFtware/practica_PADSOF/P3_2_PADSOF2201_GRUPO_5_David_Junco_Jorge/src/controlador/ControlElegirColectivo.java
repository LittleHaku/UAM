package controlador;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import componentes.IconDialog;
import exceptions.EmptyFieldException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import modelo.proyecto.Proyecto;
import vista.Ventana;
import vista.VistaElegirColectivo;

/**
 * Controlador de la vista {@link VistaElegirColectivo}.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlElegirColectivo extends Controlador {
    private final VistaElegirColectivo vista;

    /**
     * Constructor del ControlelegirColectivo
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlElegirColectivo(Ventana frame, Aplicacion modelo) {
	super("elegirColectivo", frame, modelo);

	this.vista = (VistaElegirColectivo) frame.getVista(this.getId());
    }

    /**
     * {@inheritDoc} Manejador de los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {

	    case "apoyarProyecto": {
		Colectivo col;
		Proyecto p = ((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto")).getProyecto();

		try {
		    col = modelo.encontrarColectivo(checkColectivo());
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		    break;
		}

		if (p.addApoyos(col)) {
		    IconDialog.successDialog(frame, "Proyecto Apoyado", "Proyecto apoyado correctamente");
		} else {
		    IconDialog.errorDialog(frame, "Proyecto No Apoyado", "No se ha podido apoyar al proyecto");
		}
		((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto")).setData(p.getId());

		frame.getVistaDialog().dispose();
		break;
	    }
	    case "crearProyecto": {

		Proyecto p;
		Colectivo col;
		try {
		    col = modelo.encontrarColectivo(checkColectivo());
		    p = ((ControlCrearProyecto) frame.getControl().getControlador("crearProyecto")).crearProy(col);
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		    break;
		} catch (IOException e3) {
		    new VisualException(e3).showDialog(frame);
		    break;
		}

		Consumer<ActionEvent> exitAction = event -> {
		    frame.getVistaDialog().dispose();
		    frame.showVista("panelDeControl");
		};

		if (modelo.addProyectoParaAutorizar(p) == true) {
		    IconDialog.successDialog(frame, "Proyecto Propuesto", "Proyecto propuesto correctamente",
			    exitAction);
		} else {
		    IconDialog.errorDialog(frame, "Error creando proyecto", "El proyecto no se ha podido proponer");
		}

		break;

	    }
	}
    }

    @Override
    public void initialise() {
	vista.clearColectivo();
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	Set<Colectivo> col = c.getPertenece();
	List<String> nombres = new ArrayList<>();
	for (Colectivo co : col) {
	    nombres.add(co.getName());
	    if (co.getRepresentante().equals(c)) {
		vista.addColectivo(co.getName());
	    }
	}
    }

    /**
     * Inicializa los datos cuando se quiere que no aparezcan los colectivos que no
     * hayan apoyado al proyecto que se pasa como argumento
     * 
     * @param p Proyecto que se va a apoyar
     */
    public void initialise_filtro(Proyecto p) {
	vista.clearColectivo();
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	Set<Colectivo> col = c.getPertenece();
	List<String> nombres = new ArrayList<>();
	for (Colectivo co : col) {
	    nombres.add(co.getName());
	    if (co.getRepresentante().equals(c)) {
		if (p.getApoyos().contains(co) == false) {
		    vista.addColectivo(co.getName());
		}
	    }
	}
    }

    /**
     * Devuelve el colectivo seleccionado, en caso de que no haya ninguno lanza una
     * excepcion
     * 
     * @return el nombre del colectivo seleccionado
     * 
     * @throws VisualException se lanza cuando hay un error en los campos de la
     *                         vista
     */
    public String checkColectivo() throws VisualException {
	String colectivo = vista.getNombreColectivo();

	if (colectivo.equals("")) {
	    throw new EmptyFieldException("campo de Colectivos", "el Colectivo");
	}

	return colectivo;
    }

}
