package componentes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Es un diálogo informativo
 * <p>
 * Este diálogo contiene un título, una descripción y un botón para cerrarlo.
 * <br>
 * Opcionalmente, también se puede aportar un <code> Consumer </code> en su
 * creación. <br>
 * En tal caso, este se ejecutará cuando se presion el botón, (además de cerrar
 * el diálogo)
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class InformationDialog extends AbstractDialog {
    private static final long serialVersionUID = -5588614671207316732L;

    /* Botón del diálogo */
    private final MButton exit;

    /**
     * Crea un nuevo diálogo de información
     * 
     * @param owner  Ventana dueña de este diálogo
     * @param title  Título del diálogo
     * @param desc   Descripción del diálogo
     * @param action Acción a realizar cuando se presione el botón
     */
    public InformationDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> action) {
	super(owner, title, desc);
	exit = new MButton("Volver");
	if (action != null) exit.addActionListener(e -> action.accept(e));
	exit.addActionListener(e -> {
	    setVisible(false);
	    owner.setEnabled(true);
	    dispose();
	    owner.requestFocus();
	});
	JPanel jp = new JPanel(new BorderLayout());
	jp.add(exit, BorderLayout.CENTER);
	super.setContent(jp);
    }

    /**
     * Crea un nuevo diálogo de información
     * 
     * @param owner Ventana dueña de este diálogo
     * @param title Título del diálogo
     * @param desc  Descripción del diálogo
     */
    public InformationDialog(JFrame owner, String title, String desc) {
	this(owner, title, desc, null);
    }

}
