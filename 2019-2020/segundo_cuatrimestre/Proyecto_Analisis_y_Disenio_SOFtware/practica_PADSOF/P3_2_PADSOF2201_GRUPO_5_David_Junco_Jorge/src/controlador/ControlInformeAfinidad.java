package controlador;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import componentes.InformationDialog;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import vista.Ventana;
import vista.VistaInformeAfinidad;

/**
 * Controlador de la vista {@link VistaInformeAfinidad}.
 * <p>
 * Este controlador gestiona los eventos de acción que se generen en su vista
 * 
 * @see VistaInformeAfinidad
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlInformeAfinidad extends Controlador {

    /* Vista del controlador */
    private final VistaInformeAfinidad vista;

    /* Cola de índices seleccionados */
    private Queue<Integer> selected;

    /**
     * Constructor del ControlInformeAfinidad
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlInformeAfinidad(Ventana frame, Aplicacion modelo) {
	super("informeAfinidad", frame, modelo);

	this.vista = (VistaInformeAfinidad) frame.getVista(this.getId());
	this.selected = new LinkedList<>();
    }

    /**
     * Manejador de los botones
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {

	/* Comprobamos si el evento viene de la lista de colectivos */
	if (e.getActionCommand().startsWith("box_")) {
	    int index = Integer.parseInt(e.getActionCommand().substring(4));
	    if (vista.getSelected(index)) {
		selected.add(index);
	    } else {
		selected.remove(index);
	    }

	    if (selected.size() > 2) {
		vista.setSelected(selected.poll(), false);
	    }

	    if (selected.size() == 2) {
		vista.enableButton(true);
	    }

	    else {
		vista.enableButton(false);
	    }
	    return;
	}

	/* Si no viene de la lista de colectivos, proviene de uno de los botones */
	switch (e.getActionCommand()) {

	    case "back":
		frame.showVista("panelDeControl");

		break;

	    case "solicitar":

		Ciudadano c = (Ciudadano) modelo.getUsrActivo();
		Set<Colectivo> col = c.getPertenece();
		List<Colectivo> l = col.stream().collect(Collectors.toList());
		if (selected.peek() == null) {
		    return;
		}
		Colectivo c1 = l.get(selected.poll());
		if (selected.peek() == null) {
		    return;
		}
		Colectivo c2 = l.get(selected.poll());
		Double res = modelo.getInformeAfinidad(c1, c2);
		String desc = "La afinidad entre los colectivos " + c1.getName() + " y " + c2.getName() + " es "
			+ Double.toString(res);
		InformationDialog id = new InformationDialog(frame, "Resultado del informe", desc,
			a -> frame.showVista("panelDeControl"));

		id.setVisible(true);
		break;

	}

    }

    @Override
    public void initialise() {
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	Set<Colectivo> col = c.getPertenece();
	List<String> nombres = new ArrayList<>();

	for (Colectivo co : col) {
	    nombres.add(co.getName());

	}
	vista.setData(nombres);
    }

}
