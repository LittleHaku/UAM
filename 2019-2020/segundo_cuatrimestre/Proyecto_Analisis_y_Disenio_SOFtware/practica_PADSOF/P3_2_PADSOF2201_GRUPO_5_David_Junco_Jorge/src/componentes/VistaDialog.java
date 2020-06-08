package componentes;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vista.Ventana;
import vista.Vista;
import vista.VistaInfoProyecto;

/**
 * VistaDialog es un diálogo que contiene una vista.
 * <p>
 * Este diálog nos permite que las mismas vistas que podemos utilizar en la
 * {@link Ventana} puedan aparecer en un diálogo. <br>
 * El objetivo es que la ventana contendrá un diálogo de este tipo, de manera
 * que pueda "pasarle" una vista para que la muestre
 * <p>
 * Un ejemplo de su uso es el diálogo que muestra la información de los
 * proyectos. El JPanel que se muestra es {@link VistaInfoProyecto} <br>
 * Sin embargo, en vez de sustituir a la vista que se está mostrando en la
 * ventana, se abre un <code> VistaDialog </code> que muestra el panel. <br>
 * Además, de este modo, al cerrar el panel volvemos al punto en el que
 * estábamos en la aplicación.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaDialog extends JDialog {
    private static final long serialVersionUID = 1059075633127488591L;

    /**
     * Crea un nuevo diálogo con la vista dada
     * 
     * @param v       Vista a mostrar
     * @param ventana Ventana que es dueña del diálogo
     */
    public VistaDialog(Vista v, JFrame ventana) {
	super(ventana);
	this.setContentPane(v);
	this.setModal(true);
	this.revalidate();
	this.pack();
	this.setSize(v.getPredefSize());
    }
}
