package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import controlador.Controlador;

/**
 * Es la vista en la que se consulta el informe de afinidad
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaInformeAfinidad extends Vista {
    private static final long serialVersionUID = 2031325232339674464L;
    private final IconButton back;
    private final JPanel boxes;
    private final MButton sol;
    private final JLabel l1, l2;

    private List<JCheckBox> selection;

    private ActionListener control;

    /**
     * Crea una nueva vista de informe de afinidad
     */
    public VistaInformeAfinidad() {
	super("informeAfinidad", new Dimension(600, 400));

	back = new IconButton(ComponentConstants.I_ARROW);
	back.setActionCommand("back");

	JLabel title = new JLabel("Informe de afinidad");
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setFont(ComponentConstants.F_TITLE);

	l1 = new JLabel("El informe de afinidad mide cuánto se apoyan unos colectivos a otros");

	l2 = new JLabel("Seleccione dos colectivos:");
	l2.setFont(ComponentConstants.F_SUB_HEAD);

	sol = new MButton("Solicitar informe");
	sol.setActionCommand("solicitar");
	sol.setEnabled(false);

	boxes = new JPanel();
	boxes.setLayout(new BoxLayout(boxes, BoxLayout.Y_AXIS));

	JScrollPane js = new JScrollPane(boxes);
	boxes.setOpaque(true);
	boxes.setBackground(Color.white);

	this.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(10, 10, 10, 0);
	this.add(back, gbc);

	gbc.insets = new Insets(13, 10, 13, 10);
	gbc.weightx = 1;
	this.add(title, gbc);

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridy++;
	this.add(l1, gbc);
	gbc.gridy++;
	this.add(l2, gbc);
	gbc.gridy++;
	gbc.weighty = 1;
	this.add(js, gbc);

	gbc.gridy++;
	gbc.weighty = 0;
	this.add(sol, gbc);
    }

    /**
     * Establece el texto de nombres de los colectivos de la lista
     * 
     * @param nombres Lista de nombres de los colectivos
     * 
     */
    public void setData(List<String> nombres) {
	selection = new ArrayList<>();
	for (int i = 0; i < nombres.size(); ++i) {
	    JCheckBox jc = new JCheckBox(nombres.get(i));
	    jc.setActionCommand("box_" + i);
	    jc.setBackground(new Color(0, 0, 0, 0));
	    jc.addActionListener(control);
	    selection.add(jc);
	}
	boxes.removeAll();
	for (JCheckBox jc : selection) {
	    boxes.add(jc);
	}
    }

    @Override
    public void setControlador(Controlador al) {
	this.control = al;
	sol.addActionListener(al);
	back.addActionListener(al);
    }

    /**
     * Establece la seleccion (selected) de la fila index de la lista
     * 
     * @param index    indice de la lista
     * @param selected true o false
     */
    public void setSelected(int index, boolean selected) {
	selection.get(index).setSelected(selected);
    }

    /**
     * Devuelve si la fila con el indice dado está seleccionada
     * 
     * @param index Índice de la fila por la que se pregunta
     * @return true si está seleccionada, false si no lo está
     */
    public boolean getSelected(int index) {
	return selection.get(index).isSelected();
    }

    /**
     * Activa o desactiva el boton sol
     * 
     * @param en activa (true) o desactiva el boton (false)
     */
    public void enableButton(boolean en) {
	sol.setEnabled(en);
    }
}
