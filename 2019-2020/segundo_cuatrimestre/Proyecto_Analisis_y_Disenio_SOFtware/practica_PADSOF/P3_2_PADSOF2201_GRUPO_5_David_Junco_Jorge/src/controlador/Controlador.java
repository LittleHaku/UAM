package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.aplicacion.Aplicacion;
import vista.Ventana;

/**
 * Esta clase representa un controlador en el modelo vista controlador
 * <p>
 * Por tanto, implementa todas las interfaces que van a utilizar nuestos
 * controladores. <br>
 * Los métodos de gestión de eventos estarán vacíos por defecto. Por tanto, es
 * necesario <br>
 * que cada controlador sobreescriba todos los métodos correspondientes a los
 * eventos a <br>
 * los que quiere reaccionar.<br>
 * <p>
 * Todos los métodos que nos sean sobrescritos por la subclase estarán vacíos y
 * si son <br>
 * invocados, no se realizará ninguna acción.
 * <p>
 * Por otra parte, los controladores tienen un string que los identifica. <br>
 * Este string es además igual al identificador de la vista a la que
 * corresponden. <br>
 * Por tanto, puede identificarse la vista a la que corresponde cada controlador
 * utilizando el atributo <code> id </code>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
public abstract class Controlador implements ActionListener, MouseListener, ListSelectionListener, WindowListener {

    /* Ventana de la aplicación */
    protected final Ventana frame;
    /* Modelo de datos de la aplicación */
    protected final Aplicacion modelo;

    /* String que identifica al controlador */
    private final String id;

    /**
     * Crea un nuevo controlador con el identificador especificado
     * 
     * @param id     Identificador del controlador
     * @param frame  Ventana que controla el controlador (o en la que se encuentra
     *               la vista)
     * @param modelo Modelo de datos
     */
    public Controlador(String id, Ventana frame, Aplicacion modelo) {
	this.id = id;
	this.modelo = modelo;
	this.frame = frame;
    }

    /**
     * Devuelve el identificador de este controlador
     * 
     * @return Identificador del controlador
     */
    public String getId() {
	return id;
    }

    /**
     * Inicializa la vista correspondiente a este controlador.
     * <p>
     * Es decir, se actualizan los datos que se muestran en ella <br>
     * con datos nuevos procedentes del modelo. <br>
     * Este método será llamado cuando la vista se haga visible
     * 
     */
    public void initialise() {

    }

    /* Metodos vacíos que serán sobreescritos por las subclases */

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
