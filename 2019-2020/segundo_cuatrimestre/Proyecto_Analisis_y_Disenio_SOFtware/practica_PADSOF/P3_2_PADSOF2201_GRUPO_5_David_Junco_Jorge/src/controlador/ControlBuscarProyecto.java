package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Set;

import modelo.aplicacion.Aplicacion;
import modelo.proyecto.Proyecto;
import vista.Ventana;
import vista.VistaBuscarProyecto;

/**
 * Controlador de la vista {@link VistaBuscarProyecto}.
 * <p>
 * Este controlador gestiona los eventos de acción y de ratón que se generen en
 * su vista
 * 
 * 
 * @see VistaBuscarProyecto
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlBuscarProyecto extends Controlador {

    /* Vista del controlador */
    private final VistaBuscarProyecto vista;
    /* Array con los datos para la tabla */
    private String[][] rows;

    /**
     * Constructor del ControlBuscarProyecto
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlBuscarProyecto(Ventana frame, Aplicacion modelo) {
	super("buscarProyecto", frame, modelo);

	this.vista = (VistaBuscarProyecto) frame.getVista(this.getId());
    }

    /**
     * Manejador de los botones. {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	    case "buscar": {

		String filtro = vista.getTextBuscador();
		vista.setTextBuscador("");
		String[][] rows_filtro = new String[rows.length][2];

		int i = 0;
		if (filtro.isEmpty() == true) {
		    rows_filtro = rows;
		    i = rows.length;
		} else {
		    int flag = 0;
		    for (String[] r : rows) {
			for (String c : r) {
			    if (c.toLowerCase().contains(filtro.toLowerCase()) == true) {
				flag = 1;
			    }
			}
			if (flag == 1) {
			    flag = 0;
			    rows_filtro[i] = r;
			    i++;

			}
		    }

		}

		String[][] rows_fin = new String[i][2];
		for (int j = 0; j < i; j++) {
		    rows_fin[j] = rows_filtro[j];

		}

		vista.setProyectos(rows_fin);

		// frame.showPanel("buscarProyecto");
		break;

	    }
	    case "back": {
		frame.showVista("panelDeControl");
		break;

	    }
	    default: {
		System.out.println(e.getActionCommand());

	    }
	}

    }

    /**
     * Gestiona los clicks que se hagan en la tabla de datos {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() > 1) {
	    String selectedData = vista.getSelectedProyecto();

	    if (selectedData != null) {
		((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto"))
			.setData(Integer.parseInt(selectedData));

		frame.showVistaDialog("infoProyecto");
	    }
	}
    }

    @Override
    public void initialise() {
	Set<Proyecto> proyectos = modelo.getProyectosAprobados();
	rows = new String[proyectos.size()][2];

	int i = 0;
	for (Proyecto p : proyectos) {

	    rows[i][0] = Integer.toString(p.getId());
	    rows[i][1] = p.getTitulo();
	    i++;
	}

	vista.setProyectos(rows);
    }
}
