package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * AbstractDialog representa un diálogo básico
 * <p>
 * Este diálogo tiene:
 * <ul>
 * <li>Titulo
 * <li>Descripción
 * <li>Panel para componentes adicionales
 * </ul>
 * Sin embargo, no permite el uso de iconos.Por tanto, nos servirá <br>
 * como base para los diálogos que no utilicen iconos
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public abstract class AbstractDialog extends JDialog {
    private static final long serialVersionUID = -32161598706704235L;

    /* Componentes del diálogo */
    private final JLabel title;
    private final JTextArea body;
    private final JPanel content;

    /**
     * Crea un nuevo diálogo abstracto
     * 
     * @param owner Dueño del diálogo
     * @param title Título del diálogo
     * @param desc  Texto de la descripción
     */
    public AbstractDialog(JFrame owner, String title, String desc) {
	super(owner);
	setUndecorated(true);
	this.setModal(true);

	this.title = new JLabel(title);
	this.title.setFont(ComponentConstants.F_HEADING);

	this.body = new JTextArea(desc);
	this.body.setLineWrap(true);
	this.body.setWrapStyleWord(true);
	this.body.setForeground(ComponentConstants.C_FORE_DIALOG);
	this.body.setBackground(new Color(0, 0, 0, 0));
	this.body.setBorder(null);
	this.body.setEditable(false);

	this.content = new JPanel(new BorderLayout());
	this.content.setOpaque(false);

	JPanel jp = new JPanel(new GridBagLayout());
	jp.setBackground(ComponentConstants.C_DIALOG);
	jp.setBorder(ComponentConstants.C_BORDER);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.HORIZONTAL;

	jp.add(this.title, gbc);
	gbc.gridy++;
	jp.add(this.body, gbc);
	gbc.gridy++;
	jp.add(this.content, gbc);

	this.setContentPane(jp);
	this.pack();
	this.setSize(this.getPreferredSize());
	this.setLocationRelativeTo(owner);

	/*
	 * Este listener hace que el diálogo siempre se mantenga centrado respecto al
	 * padre Es necesario ya que en algunas plataformas el JFrame puede moverse
	 * aunque el diálogo sea modal
	 * 
	 */
	owner.addComponentListener(new ComponentAdapter() {

	    @Override
	    public void componentMoved(ComponentEvent e) {
		setLocationRelativeTo(owner);

	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		componentMoved(e);

	    }

	});
    }

    /**
     * Establece el contenido del diálogo
     * <p>
     * Los contenidos de este panel son los comonentes que se mostrarán en el
     * diálogo además del título y la descripción
     * 
     * @param content Contenido del diálogo
     */
    protected void setContent(JPanel content) {
	this.content.add(content, BorderLayout.CENTER);

	this.pack();
	this.setSize(this.getPreferredSize());

	this.setLocationRelativeTo(this.getOwner());
	getOwner().setEnabled(false);
    }

}
