package controlador;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import componentes.IconDialog;
import es.uam.eps.sadp.grants.CCGG;
import fechaSimulada.FechaSimulada;
import modelo.aplicacion.Aplicacion;
import vista.Ventana;
import vista.VistaInicial;

/**
 * Controlador de la vista {@link VistaInicial}.
 * <p>
 * Este controlador gestiona los eventos de acción que se generen en su vista.
 * 
 * @see VistaInicial
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlInicial extends Controlador {
    /* Vista del controlador */
    private final VistaInicial vista;

    /**
     * Constructor del ControlInicial
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlInicial(Ventana frame, Aplicacion modelo) {
	super("inicial", frame, modelo);

	this.vista = (VistaInicial) frame.getVista(this.getId());
    }

    /**
     * Manejador de los botones
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {

	switch (e.getActionCommand()) {
	    case "iniSesion": {
		frame.showVista("iniSesion");
		break;
	    }
	    case "registro": {
		frame.showVista("registro");
		break;
	    }
	    case "avanzar": {
		FechaSimulada.avanzar(30);
		CCGG.getGateway().setDate(FechaSimulada.getHoy());

		Consumer<ActionEvent> action = event -> {
		    frame.showVista("inicial");
		};

		String desc = "Se ha avanzado hasta la fecha: " + FechaSimulada.getHoy();
		IconDialog.successDialog(frame, "Avanzar Fecha 30 Días", desc, action);
		break;
	    }

	}
    }
}
