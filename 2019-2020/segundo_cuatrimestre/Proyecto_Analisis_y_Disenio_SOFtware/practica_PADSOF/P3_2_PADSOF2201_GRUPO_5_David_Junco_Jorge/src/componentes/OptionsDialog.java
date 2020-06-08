package componentes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * OptionDialog representa un diálogo en el que hay distintas acciones que se
 * pueden tomar
 * <p>
 * Este diálogo contiene un titulo, una descripción y un conjunto de botones,
 * uno por cada acción disponible
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class OptionsDialog extends AbstractDialog {
    private static final long serialVersionUID = 7379881866177179612L;

    /* Lista de botones */
    private final List<JButton> buttons;

    /**
     * Crea un nuevo diálogo con opciones
     * 
     * @param owner       Ventana dueña del diálogo
     * @param title       Titulo del diálogo
     * @param desc        Texto del cuerpo del diálogo
     * @param actions     Lista de las acciones disponibles. Cada valor será el
     *                    texto en un botón y su comando de acción
     * @param controlador Controlador del diálogo
     */
    public OptionsDialog(JFrame owner, String title, String desc, List<String> actions, ActionListener controlador) {
	super(owner, title, desc);

	buttons = new ArrayList<>();
	for (String s : actions) {
	    MButton b = new MButton(s);
	    b.setActionCommand(s);
	    b.addActionListener(e -> {
		this.setVisible(false);
		this.dispose();
		controlador.actionPerformed(e);
	    });
	    buttons.add(b);
	}

	JPanel jp = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 0;
	for (int i = 0; i < (actions.size() / 2) * 2; i += 2) {
	    gbc.gridx = 0;
	    gbc.insets = new Insets(0, 0, 5, 5);
	    jp.add(buttons.get(i), gbc);
	    gbc.gridx = 1;
	    gbc.insets = new Insets(0, 5, 5, 0);
	    jp.add(buttons.get(i + 1), gbc);
	    gbc.gridy++;
	}
	if (actions.size() % 2 == 1) {
	    gbc.gridwidth = 2;
	    gbc.gridx = 0;
	    jp.add(buttons.get(actions.size() - 1), gbc);
	}
	super.setContent(jp);
    }
}
