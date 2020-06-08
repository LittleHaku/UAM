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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import componentes.AbstractControlPanel;
import componentes.ComponentConstants;
import componentes.MButton;
import componentes.MTable;
import controlador.Controlador;

/**
 * Es la vista vista del panel de administrador.
 * <p>
 * En el panel del administrador se accede a las distintas acciones que puede
 * tomar el administrador
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaPanelAdministrador extends AbstractControlPanel {
    private static final long serialVersionUID = 1550289240463519359L;

    private final MButton estadoCiu;
    private final MButton nvotos;
    private final MButton guardar;
    private final MButton cargar;
    private final MButton reset;
    private final MTable ciudadanos;

    /**
     * Crea una nueva vista de panel del administador
     */
    public VistaPanelAdministrador() {
	super("panelAdministrador", new Dimension(900, 500));
	JLabel head1 = new JLabel("Ciudadanos registrados");
	head1.setFont(ComponentConstants.F_HEADING);
	head1.setVerticalAlignment(SwingConstants.TOP);
	head1.setHorizontalAlignment(SwingConstants.CENTER);

	List<String> tabHead = new ArrayList<>();
	tabHead.add("Nombre");
	tabHead.add("NIF");
	tabHead.add("Estado");

	ciudadanos = new MTable(tabHead);
	ciudadanos.setBackground(Color.white);
	JScrollPane js = new JScrollPane(ciudadanos);
	js.getVerticalScrollBar().setUnitIncrement(2);
	js.getViewport().setBackground(Color.white);

	estadoCiu = new MButton("Cambiar estado del ciudadano", "Cambiar estado");
	estadoCiu.setEnabled(false);
	nvotos = new MButton("Cambiar apoyos mínimos para enviar un proyecto", "Cambiar apoyos mínimos");

	estadoCiu.setActionCommand("cambiarEstado");
	nvotos.setActionCommand("cambiarNVotos");

	guardar = new MButton("Exportar datos", "Exportar");
	guardar.setActionCommand("guardar");

	cargar = new MButton("Importar datos", "Importar");
	cargar.setActionCommand("cargar");

	reset = new MButton("Restablecer datos", "Restablecer");
	reset.setActionCommand("reset");

	JPanel bot = new JPanel(new GridBagLayout());
	bot.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridwidth = 3;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(10, 10, 10, 10);

	bot.add(head1, gbc);
	gbc.gridy++;
	gbc.weighty = 1;
	bot.add(js, gbc);

	gbc.gridy++;
	gbc.weighty = 0;
	gbc.gridwidth = 3;
	bot.add(estadoCiu, gbc);
	gbc.gridy++;
	gbc.insets = new Insets(0, 10, 10, 10);
	bot.add(nvotos, gbc);

	gbc.insets = new Insets(0, 10, 10, 5);
	gbc.gridy++;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.gridwidth = 1;
	gbc.weighty = 0;
	bot.add(guardar, gbc);

	gbc.insets = new Insets(0, 5, 10, 5);
	gbc.gridx++;
	bot.add(cargar, gbc);

	gbc.insets = new Insets(0, 5, 10, 10);
	gbc.gridx++;
	bot.add(reset, gbc);

	super.setTitle("Panel de Control del Administrador");
	super.setContent(bot);
    }

    @Override
    public void setControlador(Controlador al) {

	estadoCiu.addActionListener(al);
	nvotos.addActionListener(al);
	ciudadanos.getSelectionModel().addListSelectionListener(al);
	guardar.addActionListener(al);
	cargar.addActionListener(al);
	reset.addActionListener(al);
	super.setControlador(al);
    }

    /**
     * Devuelve el nombre del usuario seleccionado
     * 
     * @return nombre del usuario
     */
    public String getNameUsuarioSeleccionado() {
	ListSelectionModel sm = ciudadanos.getSelectionModel();
	if (sm.isSelectionEmpty()) return null;
	else {
	    return ciudadanos.getModel().getValueAt(sm.getMinSelectionIndex(), 0).toString();
	}

    }

    /**
     * Activa o desactiva el boton estadoCiu (Cambio de estado del ciudadano)
     * 
     * @param b activa (true) o desactiva el boton (false)
     */
    public void setCambioEstadoEnable(boolean b) {
	estadoCiu.setEnabled(b);
    }

    /**
     * Establece el texto de las columnas de la table de ciudadanos
     * 
     * @param nombres Nombres de los ciudadanos
     * @param nifs    NIFs de los ciudadanos
     * @param estados Estados de los ciudadanos
     */
    public void setCiudadanos(List<String> nombres, List<String> nifs, List<String> estados) {
	ciudadanos.setData(nombres, nifs, estados);
    }

}
