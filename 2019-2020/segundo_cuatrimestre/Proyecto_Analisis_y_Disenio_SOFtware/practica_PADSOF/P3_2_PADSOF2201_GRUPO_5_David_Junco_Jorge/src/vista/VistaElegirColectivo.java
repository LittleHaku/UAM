package vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.MButton;
import componentes.MList;
import controlador.Controlador;

/**
 * Es la vista que permit seleccionar un colectivo de entre varias opciones
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaElegirColectivo extends Vista {
    private static final long serialVersionUID = -1324971110470498762L;

    private JLabel title;
    private final JLabel txt1;

    private final DefaultListModel<String> dlm;
    private final JList<String> lc;
    private final JScrollPane jsp;
    private MButton b_action;

    /**
     * Crea una nueva vista de elegir colectivo
     */
    public VistaElegirColectivo() {
	super("elegirColectivo", new Dimension(600, 400));

	title = new JLabel("Proponer Como Colectivo");
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setFont(ComponentConstants.F_TITLE);

	txt1 = new JLabel("Elegir Colectivo");
	txt1.setFont(ComponentConstants.F_SUB_HEAD);

	dlm = new DefaultListModel<>();
	lc = new MList(dlm);
	lc.setLayoutOrientation(JList.VERTICAL_WRAP);
	lc.setVisibleRowCount(-1);
	lc.setBackground(getBackground());
	lc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	jsp = new JScrollPane(lc);

	b_action = new MButton("Proponer Proyecto");
	b_action.setActionCommand("b_action");

	this.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy = 0;
	gbc.insets = new Insets(10, 10, 10, 50);
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.gridwidth = 1;
	this.add(title, gbc);

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridwidth = 2;
	gbc.gridx = 0;
	gbc.gridy++;
	gbc.weighty = 1;
	this.add(jsp, gbc);
	gbc.weighty = 0;
	gbc.gridy++;
	this.add(b_action, gbc);

    }

    @Override
    public void setControlador(Controlador e) {
	b_action.addActionListener(e);
    }

    /**
     * Añade un colectivo a la lista de colectivos
     * 
     * @param col Titulo del colectivo
     */
    public void addColectivo(String col) {
	dlm.addElement(col);
	repaint();
    }

    /**
     * Limpia los colectivos de la lista
     * 
     */
    public void clearColectivo() {
	dlm.clear();
    }

    /**
     * Establece el texto del titulo de la vista
     * 
     * @param text Titulo de la vista
     */
    public void setTextTitulo(String text) {
	title.setText(text);
    }

    /**
     * Establece el texto del botón de la vista
     * 
     * @param text Texto del boton
     */
    public void setTextButton(String text) {
	b_action.setText(text);
    }

    /**
     * Establece el Action Command del botón de la vista
     * 
     * @param text Action Command
     */
    public void setButtonActionCommand(String text) {
	b_action.setActionCommand(text);
    }

    /**
     * Devuelve el nombre del colectivo seleccionado
     * 
     * @return Nombre del Colectivo seleccionado
     */
    public String getNombreColectivo() {
	if (lc.getSelectedValue() == null) return "";
	return lc.getSelectedValue();
    }
}
