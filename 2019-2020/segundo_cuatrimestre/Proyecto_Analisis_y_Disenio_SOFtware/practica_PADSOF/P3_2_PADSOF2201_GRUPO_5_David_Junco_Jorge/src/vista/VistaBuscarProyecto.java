package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import componentes.MTable;
import controlador.Controlador;

/**
 * Vista correspondiente a buscar un proyecto
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaBuscarProyecto extends Vista {
    private static final long serialVersionUID = 7587810784501210744L;

    /* Componentes */
    private final IconButton back;
    private final MTable proyectos;
    private final MButton b;
    private final JTextField buscador;

    /**
     * Crea una nueva vista de buscar proyecto
     */
    public VistaBuscarProyecto() {
	super("buscarProyecto", new Dimension(900, 500));

	back = new IconButton(ComponentConstants.I_ARROW);
	back.setActionCommand("back");

	JLabel title = new JLabel("Buscar Proyecto");
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setFont(ComponentConstants.F_TITLE);

	buscador = new JTextField(60);
	buscador.setHorizontalAlignment(SwingConstants.LEFT);

	List<String> head = new ArrayList<>();
	head.add("ID");
	head.add("Nombre");

	proyectos = new MTable(head);
	// proyectos.setColumnTargetWidth(0, 20);

	ListSelectionModel rowSelectionModel = proyectos.getSelectionModel();
	rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	JScrollPane js = new JScrollPane(proyectos);
	js.getVerticalScrollBar().setUnitIncrement(2);
	js.getViewport().setBackground(Color.white);

	b = new MButton("Buscar");
	b.setMaximumSize(new Dimension(10, 10));
	b.setActionCommand("buscar");
	buscador.addActionListener(e -> b.doClick());

	JPanel top = new JPanel(new GridBagLayout());
	top.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy = 0;
	gbc.weighty = 0;
	gbc.weightx = 0;
	gbc.insets = new Insets(10, 10, 10, 0);
	top.add(back, gbc);

	gbc.insets = new Insets(13, 10, 13, 0);
	gbc.weightx = 1;
	gbc.gridx++;
	top.add(title, gbc);

	JPanel bot = new JPanel(new GridBagLayout());
	bot.setOpaque(false);
	bot.setLayout(new GridBagLayout());

	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridwidth = 1;
	gbc.gridy = 2;
	gbc.weightx = 1;
	gbc.insets = new Insets(10, 10, 10, 10);
	bot.add(buscador, gbc);

	gbc.weightx = 0;
	gbc.gridwidth = 1;
	gbc.gridx++;
	bot.add(b, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridheight = 2;
	gbc.gridwidth = 2;
	gbc.gridx--;
	gbc.gridy += 2;
	gbc.weighty = 1;
	gbc.weightx = 1;
	bot.add(js, gbc);

	this.setLayout(new GridBagLayout());
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.fill = GridBagConstraints.BOTH;
	this.add(top, gbc);
	gbc.gridy += 2;
	gbc.weighty = 1;
	gbc.gridwidth = 1;
	this.add(bot, gbc);

    }

    @Override
    public void setControlador(Controlador al) {
	b.addActionListener(al);
	back.addActionListener(al);
	proyectos.addMouseListener(al);
    }

    /**
     * Establece el texto del campo de búsqueda
     * 
     * @param text Nuevo texto para el campo de búsqueda
     */
    public void setTextBuscador(String text) {
	buscador.setText(text);
    }

    /**
     * Establece los datos de la tabla de proyecto
     * 
     * @param rows Array de datos
     */
    public void setProyectos(Object[][] rows) {
	Object[] cabecera = { "ID", "Nombre" };
	DefaultTableModel def_model = (DefaultTableModel) proyectos.getModel();
	proyectos.clearSelection();
	def_model.setDataVector(rows, cabecera);

    }

    /**
     * Devuelve el texto del buscador
     * 
     * @return Texto del buscador
     */
    public String getTextBuscador() {
	return buscador.getText();
    }

    /**
     * Devuelve el proyecto seleccionado
     * 
     * @return Proyecto seleccionado
     */
    public String getSelectedProyecto() {
	if (proyectos.getSelectedRow() == -1) return null;
	return (String) proyectos.getValueAt(proyectos.getSelectedRow(), 0);
    }

    @Override
    public void madeVisible() {
	buscador.requestFocus();
    }
}
