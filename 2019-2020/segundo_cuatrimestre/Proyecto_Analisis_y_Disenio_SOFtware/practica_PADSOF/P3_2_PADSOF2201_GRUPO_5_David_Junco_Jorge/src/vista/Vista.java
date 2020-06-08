package vista;

import java.awt.Dimension;

import javax.swing.JPanel;

import componentes.ComponentConstants;
import controlador.Controlador;

/**
 * La vista representa un panel de la ventana.
 * <p>
 * Todas ellas tienen en común tres características:
 * <ul>
 * <li>El tamaño ideal de la ventana, definido en la creación de la vista
 * <li>Si la ventana debe poder de tamaño o no cuando está mostrando esta vista
 * (por defecto todas pueden)
 * <li>El identificador de la vista
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public abstract class Vista extends JPanel {
    private static final long serialVersionUID = 3143413341266609521L;

    /* Tamaño predefinido de la ventana */
    private Dimension predefSize;

    /* Si la ventana se puede redimensionar o no */
    private boolean resizeable = true;

    /* Identificador de la ventana */
    private final String id;

    /**
     * Crea una nueva vista con los datos dados
     * 
     * @param id         Identificador de la vista
     * @param predefSize Tamaño predefinido para esta vista
     */
    public Vista(String id, Dimension predefSize) {
	this.id = id;
	this.setBackground(ComponentConstants.C_VISTA);
	this.predefSize = predefSize;

	/**
	 * La barra superior en macOS es mucho más fina. Por tanto reducimos la altura
	 * de la vista para compensar la diferencia
	 */
	String ss = System.getProperty("os.name");
	if (ss != null) {
	    if (ss.toLowerCase().contains("mac") || ss.toLowerCase().contains("darwin")) {
		this.predefSize.height -= 20;
	    }
	}

    }

    /**
     * Establece el controlador para la vista
     * <p>
     * El controlador dado gestionará todos los eventos generados en la vista <br>
     * a excepción del unos pocos que modifiquen aspectos puramente estéticos <br>
     * y que no requieran el acceso al modelo en ningún caso.
     * 
     * @param e Controlador para la vista
     */
    public abstract void setControlador(Controlador e);

    /**
     * Acciones a realizar cuando esta vista se hace visible
     */
    public void madeVisible() {
    }

    /**
     * Establece el tamaño predefinido
     * 
     * @param predefSize Tamaño predefinido
     */
    public void setPredefSize(Dimension predefSize) {
	this.predefSize = predefSize;
    }

    /**
     * Establece si la ventana se podrá redimensionar
     * 
     * @param resizeable Si la ventana se puede redimensionar
     */
    public void setResizeable(boolean resizeable) {
	this.resizeable = resizeable;
    }

    /**
     * Devuelve el id de la vista
     * 
     * @return Id de la vista
     */
    public String getId() {
	return id;
    }

    /**
     * Devuelve el tamaño predefinido
     * 
     * @return Tamaño predefinido
     */
    public Dimension getPredefSize() {
	return predefSize;
    }

    /**
     * Devuelve si la ventana que contiene esta vista debe de poder redimensionarse
     * 
     * @return Si la ventana debe de poder redimensionanarse
     */
    public boolean isResizeable() {
	return resizeable;
    }

}
