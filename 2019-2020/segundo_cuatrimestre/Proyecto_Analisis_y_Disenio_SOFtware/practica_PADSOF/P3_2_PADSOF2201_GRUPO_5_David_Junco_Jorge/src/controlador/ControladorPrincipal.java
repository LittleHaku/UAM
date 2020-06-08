package controlador;

import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import vista.Ventana;

/**
 * Controlador principal de la aplicación
 * <p>
 * Este es el controlador de la ventana. Además, guarda los controladores de
 * todas las vistas. <br>
 * Estos se pueden obtener conociendo el id del mismo o se pueden pedir todos
 * los controladores <br>
 * registrados
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControladorPrincipal extends Controlador {

    /* Mapa con todos los controladores de las vistas */
    private final Map<String, Controlador> controladores;

    /**
     * Crea un nuevo controlador principal
     * <p>
     * Además inicializa todos los controladores de todas las vistas y los guarda en
     * el nuevo controlador principal
     * 
     * @param frame  Ventana de la que es controlador
     * @param modelo Modelo del que es controlador
     */
    public ControladorPrincipal(Ventana frame, Aplicacion modelo) {
	super("principal", frame, modelo);
	controladores = new HashMap<>();

	insertarControlador(new ControlIniciarSesion(frame, modelo));
	insertarControlador(new ControlRegistro(frame, modelo));
	insertarControlador(new ControlCrearColectivo(frame, modelo));
	insertarControlador(new ControlApuntarseColectivo(frame, modelo));
	insertarControlador(new ControlInicial(frame, modelo));
	insertarControlador(new ControlPanelControl(frame, modelo));
	insertarControlador(new ControlBuscarProyecto(frame, modelo));
	insertarControlador(new ControlPanelAdministrador(frame, modelo));
	insertarControlador(new ControlCrearProyecto(frame, modelo));
	insertarControlador(new ControlElegirColectivo(frame, modelo));
	insertarControlador(new ControlInformeAfinidad(frame, modelo));
	insertarControlador(new ControlInfoProyecto(frame, modelo));

    }

    /*
     * Inserta un controlador en el mapa
     */
    private void insertarControlador(Controlador c) {
	controladores.put(c.getId(), c);
    }

    /**
     * Devuelve el controlador que tiene el id especificado
     * <p>
     * El uso principal de este método es obtener el controlador correspondiente a
     * una vista <br>
     * dada. Para ello nos aprovecharemos de que los controladores tienen el mismo
     * identificador <br>
     * que la vista a la que corresponden.
     * 
     * @param id Identificador del controlador
     * 
     * @return Controlador que tiene ese identificador
     */
    public Controlador getControlador(String id) {
	return controladores.get(id);
    }

    /**
     * Devuelve una colección con los controladores de todas las vistas
     * 
     * @return Colección con los controladores de todas las vistas
     */
    public Collection<Controlador> getAllControlador() {
	return controladores.values();
    }

    /**
     * Devuelve la ventana que controla este controlador
     * 
     * @return Ventana que controla este controlador
     */
    public Ventana getFrame() {
	return frame;
    }

    /**
     * Devuelve el modelo de datos que utiliza este controlador
     * 
     * @return Modelo de datos del controlador
     */
    public Aplicacion getModelo() {
	return modelo;
    }

    @Override
    public void windowClosing(WindowEvent e) {

	/*
	 * Al cerrar la ventana, guardamos los datos en el archivo de guardado por
	 * defecto. De este modo, podemos estar seguros de que no se pierden datos y
	 * garantizar la persistencia
	 */
	try {
	    Aplicacion.saveToFile(Paths.get("save.save").toFile());
	} catch (Exception e2) {
	    new VisualException(e2).showDialog(frame);
	}
    }

}
