package componentes;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * Esta clase representa un diálogo con dos opciones entre las que elegir.
 * <p>
 * La utilidad principal de esta clase es crear diálogos que representen avisos
 * <br>
 * en los que se puede o bien continuar o cancelar la acción.
 * <p>
 * 
 * Por ejemplo, los diálgos de advertencia son un ejemplo de diálogo en el que
 * <br>
 * el usuario tiene dos opciones. Puede o bien continuar con el proceso tras
 * <br>
 * ver la advertencia o bien cancelarlo.
 * <p>
 * Si el usuario decide seguir adelante se ejeturá un acción determinada. <br>
 * Si decide cancelar el proceso, simplemente se cerrará la ventana.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
public class BiOptionIconDialog extends IconDialog {
    private static final long serialVersionUID = -3901432187536032085L;

    /* Accion a ejecutar si se sigue adelante */
    private final Consumer<ActionEvent> f;

    /* Botón de aceptar */
    private MButton cont;

    public BiOptionIconDialog(JFrame owner, String title, String desc, String icon, Consumer<ActionEvent> f) {
	super(owner, title, desc, icon);
	this.f = f;
    }

    @Override
    protected JPanel getButtonPanel() {

	cont = new MButton("Confirmar");
	cont.addActionListener(e -> {
	    this.setVisible(false);
	    super.owner.setEnabled(true);
	    this.dispose();
	    super.owner.requestFocus();
	    f.accept(e);

	});

	JPanel jp = new JPanel(new GridBagLayout());
	jp.setBackground(new Color(0, 0, 0, 0));
	jp.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 10;
	gbc.insets = new Insets(0, 0, 0, 5);
	jp.add(super.ret, gbc);
	gbc.gridx = 1;
	gbc.insets = new Insets(0, 5, 0, 0);
	jp.add(cont, gbc);
	return jp;
    }

    /**
     * Crea un nuevo diálogo de advertencia y lo hace visible
     * <p>
     * Los dialogos de éxito son instancias de <code> BiOptionDialog </code> que
     * utilizan <br>
     * el icono de advertencia definido en <code> ComponentConstants</code> como
     * icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param f     Acción adicional a realizar si se decide continuar con el
     *              proceso
     */
    public static void warningDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> f) {
	BiOptionIconDialog id = new BiOptionIconDialog(owner, title, desc, ComponentConstants.I_WARNING, f);
	id.setVisible(true);
	id.requestFocus();
    }

    /**
     * Crea un nuevo diálogo de error y lo hace visible
     * <p>
     * Los dialogos de error son instancias de <code> BiOptionDialog </code> que
     * utilizan <br>
     * el icono de error definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param f     Acción adicional a realizar si se decide continuar con el
     *              proceso
     */
    public static void errorDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> f) {
	ChoiceDialog id = new ChoiceDialog(owner, title, desc, ComponentConstants.I_ERROR, f);
	id.setVisible(true);
    }

    /**
     * Crea un nuevo diálogo de éxito y lo hace visible
     * <p>
     * Los dialogos de éxito son instancias de <code> BiOptionDialog </code> que
     * utilizan <br>
     * el icono de éxito definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param f     Acción adicional a realizar si se decide continuar con el
     *              proceso
     */
    public static void successDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> f) {
	ChoiceDialog id = new ChoiceDialog(owner, title, desc, ComponentConstants.I_SUCCESS, f);
	id.setVisible(true);
    }
}
