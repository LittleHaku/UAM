package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import componentes.MList;
import controlador.Controlador;

/**
 * Es la vista VistaCrearColectivo. Tiene un JTextField para escribir el nombre
 * del colectivo. También una lista de los colectivos de los que es
 * representante, para poder crear el colectivo como uno de sus subcolectivos.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaCrearColectivo extends Vista {
    private static final long serialVersionUID = -1866079390057764118L;

    /* Componentes de la vista */
    private final JTextField nombre;
    private final JRadioButton subcol;
    private final JRadioButton noSubcol;
    private final MButton submit;
    private final IconButton exit;
    private final JList<String> lc;

    /* Datos de la lista */
    private final DefaultListModel<String> dlm;

    /**
     * Crea la VistaCrearColectivo
     */
    public VistaCrearColectivo() {
	super("crearColectivo", new Dimension(600, 400));
	super.setLayout(new GridBagLayout());

	// Components

	dlm = new DefaultListModel<>();
	lc = new MList(dlm);
	lc.setEnabled(false);
	lc.setLayoutOrientation(JList.VERTICAL_WRAP);
	lc.setVisibleRowCount(-1);
	lc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane jsp = new JScrollPane(lc);

	submit = new MButton("Crear nuevo colectivo");
	submit.setActionCommand("submit");

	exit = new IconButton(ComponentConstants.I_ARROW);
	exit.setActionCommand("exit");

	JLabel title = new JLabel("Crear Colectivo");
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setFont(ComponentConstants.F_TITLE);

	JLabel txt1 = new JLabel("Nombre del colectivo");
	txt1.setFont(ComponentConstants.F_SUB_HEAD);

	nombre = new JTextField();
	nombre.addActionListener(e -> submit.doClick());

	JLabel txt2 = new JLabel("¿Quieres crealo como subcolectivo?");
	txt2.setFont(ComponentConstants.F_SUB_HEAD);

	JLabel txt3 = new JLabel("Seleccione el colectivo padre:");
	txt3.setFont(ComponentConstants.F_FOOTNOTE);
	txt3.setForeground(Color.gray);

	subcol = new JRadioButton("Si");
	subcol.addActionListener(e -> {
	    lc.setEnabled(true);
	    txt3.setForeground(Color.black);
	});
	subcol.setHorizontalAlignment(SwingConstants.CENTER);
	noSubcol = new JRadioButton("No");
	noSubcol.setHorizontalAlignment(SwingConstants.CENTER);
	noSubcol.addActionListener(e -> {
	    lc.setEnabled(false);
	    lc.clearSelection();
	    txt3.setForeground(Color.gray);
	});
	noSubcol.setSelected(true);

	ButtonGroup yn = new ButtonGroup();
	yn.add(subcol);
	yn.add(noSubcol);

	// Header
	JPanel top = new JPanel(new GridBagLayout());
	top.setOpaque(false);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(10, 10, 10, 0);
	top.add(exit, gbc);

	gbc.insets = new Insets(13, 10, 13, 0);
	gbc.weightx = 1;
	top.add(title, gbc);

	JPanel options = new JPanel(new GridBagLayout()); // Radio buttons
	options.setOpaque(false);
	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(10, 10, 10, 10);
	options.add(txt2, gbc);
	gbc.weightx = 1;
	gbc.gridx++;
	options.add(subcol, gbc);
	gbc.gridx++;
	options.add(noSubcol, gbc);

	// Content
	JPanel bot = new JPanel(new GridBagLayout());
	bot.setOpaque(false);
	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(10, 10, 0, 10);
	bot.add(txt1, gbc);
	gbc.weightx = 1;
	gbc.gridx = 1;
	bot.add(nombre, gbc);

	gbc.gridy++;
	gbc.gridx = 0;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.gridwidth = 2;
	bot.add(options, gbc);

	gbc.gridy++;
	gbc.gridx = 0;
	gbc.insets = new Insets(0, 10, 0, 0);
	gbc.gridwidth = 2;
	bot.add(txt3, gbc);

	gbc.gridy++;
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.weighty = 1;
	bot.add(jsp, gbc);

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridy++;
	gbc.weighty = 0;
	bot.add(submit, gbc);

	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.weighty = 0;
	this.add(top, gbc);
	gbc.weighty = 1;
	gbc.gridy++;
	this.add(bot, gbc);

    }

    @Override
    public void setControlador(Controlador e) {
	submit.addActionListener(e);
	exit.addActionListener(e);
    }

    /**
     * Getter del nombre
     * 
     * @return el nombre escrito
     */
    public String getNombre() {
	return nombre.getText();
    }

    /**
     * Getter del boton seleccionado
     * 
     * @return el boton seleccionado
     */
    public boolean isSubcolectivo() {
	return subcol.isSelected();
    }

    /**
     * Getter del elemento de la lista seleccionado
     * 
     * @return el elemento de la lista seleccionado
     */
    public String getNombreSubcolectivo() {
	if (lc.getSelectedValue() == null) return "";
	return lc.getSelectedValue();
    }

    /**
     * Establece los colectivos de la lista
     * 
     * @param cols Colectivos para la lista
     */
    public void setColectivos(Collection<String> cols) {
	dlm.clear();
	cols.forEach(e -> dlm.addElement(e));
	repaint();
    }

    /**
     * Borra el texto del nombre
     */
    public void clearFields() {
	nombre.setText("");
    }

    @Override
    public void madeVisible() {
	nombre.requestFocus();
    }
}
