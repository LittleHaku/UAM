package vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import controlador.Controlador;

/**
 * Es la vista VistaApuntarseColectivo. Tiene una lista de colectivos a los que
 * no pertenece el ciudadano y de los que tiene que elegir uno para apuntarse.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaApuntarseColectivo extends Vista {
    private static final long serialVersionUID = -1866079390057764118L;
    /* Componentes */
    private final JPanel top;
    private final JPanel bot;
    private final JScrollPane jsp;
    private final JList<String> lc;
    private final JLabel title;
    private final JLabel txt;
    private final MButton submit;
    private final IconButton exit;

    /* Datos de la lista */
    private final DefaultListModel<String> dlm;

    /**
     * Crea la una nueva vista de apuntarse a colectivo
     */
    public VistaApuntarseColectivo() {
	super("apuntarseColectivo", new Dimension(600, 300));

	// Componentes
	exit = new IconButton(ComponentConstants.I_ARROW);
	exit.setActionCommand("exit");

	title = new JLabel("Apuntarse a colectivo");
	title.setFont(ComponentConstants.F_TITLE);
	title.setHorizontalAlignment(SwingConstants.CENTER);

	txt = new JLabel("Seleccione el colectivo a apuntase:");

	jsp = new JScrollPane();
	dlm = new DefaultListModel<>();
	lc = new JList<>(dlm);
	lc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	jsp.setViewportView(lc);

	submit = new MButton("Apuntarse");
	submit.setActionCommand("submit");

	this.setLayout(new GridBagLayout());
	top = new JPanel();
	top.setLayout(new GridBagLayout());
	top.setOpaque(false);
	bot = new JPanel();
	bot.setLayout(new GridBagLayout());
	bot.setOpaque(false);

	// Header
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	top.add(exit, gbc);

	gbc.weightx = 1;
	top.add(title, gbc);

	// Content
	gbc = new GridBagConstraints();
	gbc.insets = new Insets(10, 10, 0, 10);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	bot.add(txt, gbc);

	gbc.weighty = 10;
	gbc.gridy++;
	gbc.insets = new Insets(10, 10, 10, 10);
	bot.add(jsp, gbc);

	gbc.weighty = 0;
	gbc.gridy++;
	gbc.insets = new Insets(0, 10, 10, 10);
	bot.add(submit, gbc);

	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.fill = GridBagConstraints.BOTH;
	add(top, gbc);

	gbc.gridy++;
	gbc.weighty = 10;
	add(bot, gbc);
    }

    @Override
    public void setControlador(Controlador e) {
	submit.addActionListener(e);
	exit.addActionListener(e);
    }

    /**
     * Devuelve el nombre del colectivo seleccionado
     * 
     * @return Nombre del colectivo seleccionado
     */
    public String getNombre() {
	if (lc.getSelectedValue() == null) return "";
	return lc.getSelectedValue();
    }

    /**
     * Añade un colectivo a la lista
     * 
     * @param col Colectivo a añadir
     */
    public void addColectivo(String col) {
	dlm.addElement(col);
	repaint();
    }

    /**
     * Borra la lista de colectivos
     */
    public void clearColectivo() {
	dlm.clear();
    }

    @Override
    public void madeVisible() {
	if (dlm.isEmpty() == false) {
	    txt.setText("Seleccione el colectivo a apuntase:");
	    lc.setSelectedIndex(0);
	    submit.setEnabled(true);
	    submit.requestFocus();
	} else {
	    txt.setText("No hay colectivos disponibles.");
	    submit.setEnabled(false);
	}
    }
}
