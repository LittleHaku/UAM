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
import javax.swing.SwingConstants;

import componentes.AbstractControlPanel;
import componentes.ComponentConstants;
import componentes.MButton;
import componentes.MTable;
import controlador.Controlador;

/**
 * Es la vista del panel de control
 * <p>
 * En el panel del panel de control se accede a las distintas acciones que puede
 * tomar un ciudadano
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaPanelDeControl extends AbstractControlPanel {
    private static final long serialVersionUID = -3456742214611301643L;

    private final MTable colectivos;
    private final MTable proyectos;
    private final MButton b1;
    private final MButton b2;
    private final MButton b3;
    private final MButton b4;
    private final MButton b5;
    private final MButton b6;

    /**
     * Crea una nueva vista de Panel de Control
     */
    public VistaPanelDeControl() {
	super("panelDeControl", new Dimension(900, 500));

	JLabel head1 = new JLabel("Mis Colectivos");
	JLabel head2 = new JLabel("Mis Proyectos");
	head1.setForeground(Color.black);
	head2.setForeground(Color.black);
	head1.setFont(ComponentConstants.F_HEADING);
	head2.setFont(ComponentConstants.F_HEADING);
	head1.setVerticalAlignment(SwingConstants.TOP);
	head2.setVerticalAlignment(SwingConstants.TOP);
	head1.setHorizontalAlignment(SwingConstants.CENTER);
	head2.setHorizontalAlignment(SwingConstants.CENTER);

	List<String> c1 = new ArrayList<>();
	c1.add("Nombre");
	c1.add("Supercolectivo");
	c1.add("Rol");

	List<String> c2 = new ArrayList<>();
	c2.add("Id");
	c2.add("Nombre");
	c2.add("Rol");

	colectivos = new MTable(c1);
	colectivos.setColumnTargetWidth(2, 50);
	proyectos = new MTable(c2);
	proyectos.setColumnTargetWidth(0, 50);
	colectivos.setColumnTargetWidth(2, 100);

	JScrollPane js1 = new JScrollPane(colectivos);
	js1.getViewport().setBackground(Color.white);
	js1.getVerticalScrollBar().setUnitIncrement(2);
	JScrollPane js2 = new JScrollPane(proyectos);
	js2.getViewport().setBackground(Color.white);
	js2.getVerticalScrollBar().setUnitIncrement(2);

	b1 = new MButton("Solicitar informes de afinidad", "Informes de afinidad");
	b2 = new MButton("Salir del colectivo", "Salir");
	b3 = new MButton("Crear un nuevo colectivo", "Crear");
	b4 = new MButton("Apuntarse a un colectivo", "Apuntarse");

	b1.setActionCommand("informeAfinidad");
	b2.setActionCommand("salirColectivo");
	b2.setEnabled(false);
	b3.setActionCommand("crearColectivo");
	b4.setActionCommand("apuntarseColectivo");

	b5 = new MButton("Proponer Proyecto", "Proponer");
	b6 = new MButton("Buscar Proyecto", "Buscar");

	b5.setActionCommand("propProyecto");
	b6.setActionCommand("buscarProyecto");

	JPanel bot = new JPanel(new GridBagLayout());
	bot.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.gridwidth = 2;
	gbc.insets = new Insets(0, 10, 0, 10);
	bot.add(head1, gbc);
	gbc.gridx = 2;
	bot.add(head2, gbc);
	gbc.gridy = 1;
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridx = 0;
	gbc.weighty = 1;
	bot.add(js1, gbc);
	gbc.gridx = 2;
	gbc.weightx = 2;
	gbc.gridheight = 2;
	bot.add(js2, gbc);
	gbc.gridx = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weighty = 0;
	gbc.weightx = 1;
	gbc.insets = new Insets(0, 10, 5, 5);
	gbc.gridy++;
	bot.add(b1, gbc);
	gbc.insets = new Insets(0, 5, 5, 10);
	gbc.gridx++;
	bot.add(b2, gbc);
	gbc.gridx = 0;
	gbc.insets = new Insets(5, 10, 10, 5);
	gbc.gridy++;
	bot.add(b3, gbc);
	gbc.gridx++;
	gbc.insets = new Insets(5, 5, 10, 10);
	bot.add(b4, gbc);
	gbc.gridx++;
	gbc.insets = new Insets(5, 10, 10, 5);
	bot.add(b5, gbc);
	gbc.gridx++;
	gbc.insets = new Insets(5, 5, 10, 10);
	bot.add(b6, gbc);
	gbc.gridx++;

	super.setContent(bot);

    }

    @Override
    public void setControlador(Controlador al) {
	b1.addActionListener(al);
	b2.addActionListener(al);
	b3.addActionListener(al);
	b4.addActionListener(al);
	b5.addActionListener(al);
	b6.addActionListener(al);
	super.setControlador(al);
	colectivos.getSelectionModel().addListSelectionListener(al);
	proyectos.addMouseListener(al);
    }

    public void setSalirColectivoEnable(boolean enabled) {
	b2.setEnabled(enabled);
    }

    /**
     * Establece el texto de las columnas de la tabla de Colectivos
     * 
     * @param nombres  Nombres de los colectivos
     * @param supercol Nombres de los colectivos padre
     * @param roles    Para cada colectivo, el rol del ciudadano en el colectivo
     */
    public void setColectivos(List<String> nombres, List<String> supercol, List<String> roles) {
	colectivos.setData(nombres, supercol, roles);
    }

    /**
     * Establece el texto de las columnas de la tabla de proyectos
     * 
     * @param ids     Ids de los proyectos
     * @param nombres Nombres de los proyectos
     * @param roles   Participación del ciudadano en los proyectos
     */
    public void setProyectos(List<String> ids, List<String> nombres, List<String> roles) {

	proyectos.setData(ids, nombres, roles);
    }

    /**
     * Establece el texto del titulo con el nombre del usuario
     * 
     * @param name Nombre del ciudadano
     */
    public void setNombre(String name) {
	super.setTitle("Panel de control de " + name);
    }

    /**
     * Devuelve el nombre del colectivo seleccionado
     * 
     * @return String con el colectivo seleccionado
     */
    public String getNameColectivoSelected() {
	int i = colectivos.getSelectedRow();
	if (i == -1) return "";
	else return (String) colectivos.getValueAt(i, 0);
    }

    /**
     * Devuelve la id del proyecto seleccionado
     * 
     * @return int con la id del proyecto seleccionado
     */
    public int getIdProyectoSelected() {
	int row = proyectos.getSelectedRow();
	if (row == -1) return -1;
	else return Integer.parseInt((String) proyectos.getValueAt(row, 0));
    }
}
