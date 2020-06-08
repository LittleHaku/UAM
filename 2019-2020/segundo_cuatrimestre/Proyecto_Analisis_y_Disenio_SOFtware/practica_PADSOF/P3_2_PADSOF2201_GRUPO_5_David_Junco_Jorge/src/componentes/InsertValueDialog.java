package componentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Diálogo con un campo de entrada de texto
 * <p>
 * Este diálogo contiene un título, descripción, un campo de texto en que se
 * puede introducir el texto y un botón para cerrarlo <br>
 * El botón que aparece en el diálogo tiene asignada la acción de cerrar el
 * diálogo.<br>
 * Además, se puede añadir una acción adicional usando el constructor que
 * incluye un <code> Consumer </code> como argumento.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class InsertValueDialog extends AbstractDialog {
    private static final long serialVersionUID = -2037288160186109240L;
    private final JButton action;
    private final JTextField input;

    /**
     * Crea un nuevo diálogo de entrada de texto
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Descripción del diálogo
     * @param al    Acción adicional a realizar al hacer click en el botón
     */
    public InsertValueDialog(JFrame owner, String title, String desc, Consumer<String> al) {
	super(owner, title, desc);

	input = new JTextField();
	this.action = new MButton("Establecer");
	this.action.addActionListener(e -> al.accept(input.getText()));
	this.action.addActionListener(e -> {
	    this.setVisible(false);
	    this.dispose();
	    owner.setEnabled(true);
	    owner.requestFocus();
	});

	input.addActionListener(e -> this.action.doClick());
	JPanel jp = new JPanel(new GridBagLayout());
	jp.setOpaque(false);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridy = 0;
	gbc.gridx = 0;
	gbc.ipady = 7;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.weighty = 0;

	jp.add(input, gbc);
	gbc.ipady = 0;
	gbc.insets = new Insets(10, 0, 0, 0);
	gbc.gridy++;
	jp.add(action, gbc);

	this.setBackground(ComponentConstants.C_DIALOG);
	super.setContent(jp);

	/* Dimension mínima */
	if (getPreferredSize().getWidth() < 300) {
	    this.setSize(new Dimension(300, getPreferredSize().height));
	    this.setLocationRelativeTo(owner);
	}
    }

    /**
     * Establece el texto del campo
     * <p>
     * Puede utilizarse para establecer el texto inicalmente
     * 
     * @param text Texto del campo de texto del diálogo
     */
    public void setTextFieldText(String text) {
	this.input.setText(text);
    }
}
