package vista;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import componentes.MTextField;
import controlador.Controlador;

/**
 * Es la vista de creación de proyectos
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaCrearProyecto extends Vista {
    private static final long serialVersionUID = 6093855846906439258L;

    /* Componentes */
    private final JPanel bot;
    private final Box c_box;
    private final CardLayout cards;
    private final IconButton back;
    private final MButton explorador;
    private final MButton prop_proy_ciu;
    private final MButton prop_proy_col;
    private final JTextField titulo_p;
    private final JTextField importe_p;
    private final JTextArea desc_p;
    private final JTextField path;
    private final JTextField g_social;
    private final JRadioButton tipo_inf;
    private final JRadioButton tipo_social;
    private final JRadioButton imp1;
    private final JRadioButton imp2;
    private final List<JCheckBox> checkboxes;

    /**
     * Crea una nueva vista de crear proyecto
     */
    public VistaCrearProyecto() {
	super("crearProyecto", new Dimension(1000, 600));

	back = new IconButton(ComponentConstants.I_ARROW);
	back.setActionCommand("back");

	JLabel title = new JLabel("Nuevo Proyecto");
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setFont(ComponentConstants.F_TITLE);

	titulo_p = new MTextField("Titulo");
	titulo_p.setHorizontalAlignment(SwingConstants.LEFT);

	importe_p = new MTextField("Importe (en €)");
	importe_p.setHorizontalAlignment(SwingConstants.LEFT);

	desc_p = new JTextArea();
	desc_p.setAlignmentY(SwingConstants.LEFT);
	desc_p.setText("Descripcion");
	desc_p.setLineWrap(true);
	desc_p.setWrapStyleWord(true);
	desc_p.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
		    if (e.getModifiers() > 0) {
			desc_p.transferFocusBackward();
		    } else {
			desc_p.transferFocus();
		    }
		    e.consume();
		}
	    }
	});

	JScrollPane js = new JScrollPane(desc_p);
	js.getVerticalScrollBar().setUnitIncrement(2);

	JLabel titLabel = new JLabel("Titulo:");
	titLabel.setHorizontalAlignment(SwingConstants.LEFT);
	titLabel.setFont(ComponentConstants.F_SUB_HEAD);

	JLabel descLabel = new JLabel("Descripción:");
	descLabel.setHorizontalAlignment(SwingConstants.LEFT);
	descLabel.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	JLabel s_title = new JLabel("Tipo de Proyecto:");
	s_title.setHorizontalAlignment(SwingConstants.LEFT);
	s_title.setFont(ComponentConstants.F_SUB_HEAD);

	JLabel impo_title = new JLabel("Importe:");
	impo_title.setHorizontalAlignment(SwingConstants.LEFT);
	impo_title.setFont(ComponentConstants.F_SUB_HEAD);

	tipo_inf = new JRadioButton("Infraestructura");
	tipo_inf.setActionCommand("infraestructura");
	tipo_social = new JRadioButton("Social");
	tipo_social.setActionCommand("social");
	tipo_inf.setSelected(true);

	ButtonGroup grupo_tipo = new ButtonGroup();
	grupo_tipo.add(tipo_inf);
	grupo_tipo.add(tipo_social);

	// Infraestructura

	JLabel el_foto = new JLabel("Elegir Foto:");
	el_foto.setHorizontalAlignment(SwingConstants.CENTER);
	el_foto.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	path = new MTextField("Ruta al archivo");
	path.setHorizontalAlignment(SwingConstants.LEFT);

	explorador = new MButton("Explorar Archivos");
	explorador.setMaximumSize(new Dimension(20, 100));
	explorador.setActionCommand("explorar archivos");

	JLabel dist = new JLabel("Distritos Afectados:");
	dist.setHorizontalAlignment(SwingConstants.CENTER);
	dist.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	checkboxes = new ArrayList<JCheckBox>();
	c_box = Box.createVerticalBox();

	JScrollPane jsd = new JScrollPane(c_box);
	jsd.getVerticalScrollBar().setUnitIncrement(2);
	jsd.setPreferredSize(new Dimension(140, 40));
	jsd.getViewport().setBackground(Color.white);

	// Social

	JLabel g_text = new JLabel("Grupo Social:");
	g_text.setHorizontalAlignment(SwingConstants.LEFT);
	g_text.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	g_social = new MTextField("Nombre del grupo social responsable");
	g_social.setPreferredSize(new Dimension(10, 30));
	g_social.setHorizontalAlignment(SwingConstants.LEFT);

	JLabel imp_p = new JLabel("Impacto Del Proyecto:");
	imp_p.setHorizontalAlignment(SwingConstants.LEFT);
	imp_p.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	imp1 = new JRadioButton("Nacional");
	imp2 = new JRadioButton("Internacional");
	imp1.setSelected(true);

	ButtonGroup grupo_imp = new ButtonGroup();
	grupo_imp.add(imp1);
	grupo_imp.add(imp2);

	prop_proy_ciu = new MButton("Proponer como Ciudadano");
	prop_proy_ciu.setActionCommand("proponer proyecto ciu");
	prop_proy_col = new MButton("Proponer como Colectivo");
	prop_proy_col.setActionCommand("proponer proyecto col");

	JPanel top = new JPanel(new GridBagLayout());
	top.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(10, 10, 15, 0);
	top.add(back, gbc);

	gbc.insets = new Insets(10, 10, 10, 0);
	gbc.weightx = 10;
	gbc.gridwidth = 5;
	top.add(title, gbc);

	gbc.gridx = 0;
	gbc.gridy += 1;
	gbc.weightx = 0;
	gbc.insets = new Insets(10, 10, 10, 0);
	gbc.gridwidth = 1;
	gbc.weighty = 0;
	top.add(titLabel, gbc);

	gbc.gridx++;
	gbc.weightx = 2;
	gbc.gridwidth = 4;
	gbc.insets = new Insets(10, 10, 10, 10);
	top.add(titulo_p, gbc);

	gbc.gridx = 0;
	gbc.gridwidth = 2;
	gbc.insets = new Insets(10, 10, 0, 10);
	gbc.gridy++;
	gbc.weightx = 0;
	gbc.weighty = 0;
	top.add(descLabel, gbc);

	gbc.gridwidth = 5;
	gbc.weightx = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy++;
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.weighty = 1;
	gbc.gridheight = 1;
	top.add(js, gbc);

	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weighty = 0;
	gbc.weightx = 0;
	gbc.gridy += 3;
	gbc.insets = new Insets(10, 10, 10, 0);
	top.add(impo_title, gbc);
	gbc.gridx++;
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.weightx = 1;
	top.add(importe_p, gbc);
	gbc.gridx++;
	gbc.weightx = 0;
	top.add(s_title, gbc);

	gbc.gridwidth = 1;
	gbc.gridx++;
	gbc.weightx = 0.5;
	top.add(tipo_inf, gbc);
	gbc.gridx++;
	top.add(tipo_social, gbc);

	cards = new CardLayout();
	bot = new JPanel(cards);
	bot.setOpaque(false);
	JPanel soc = new JPanel(new GridBagLayout());
	soc.setOpaque(false);
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.gridheight = 1;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(30, 10, 10, 10);
	soc.add(g_text, gbc);

	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.weightx = 1;
	gbc.gridwidth = 4;
	gbc.gridx++;
	soc.add(g_social, gbc);

	gbc.insets = new Insets(20, 10, 10, 30);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridwidth = 2;
	gbc.gridy++;
	gbc.gridx = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	soc.add(imp_p, gbc);

	gbc.insets = new Insets(20, 10, 10, 10);
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1;
	gbc.gridx = 2;
	soc.add(imp1, gbc);
	gbc.gridx++;
	soc.add(imp2, gbc);
	gbc.gridy++;
	gbc.weighty = 10;
	soc.add(new JLabel(), gbc);

	JPanel inf = new JPanel(new GridBagLayout());
	inf.setOpaque(false);
	gbc.weighty = 0;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 2;
	gbc.weightx = 1;
	gbc.insets = new Insets(10, 10, 10, 10);
	inf.add(el_foto, gbc);

	gbc.gridx = 0;
	gbc.gridy++;
	inf.add(path, gbc);

	gbc.gridx = 0;
	gbc.weighty = 0;
	gbc.gridy++;
	inf.add(explorador, gbc);
	gbc.gridy++;
	gbc.weighty = 1;
	inf.add(new JLabel(), gbc);

	JPanel inf2 = new JPanel(new GridBagLayout());
	inf2.setOpaque(false);
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.weightx = 1;
	gbc.gridwidth = 2;
	gbc.weighty = 0;
	gbc.gridx = 0;
	inf2.add(dist, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy++;
	gbc.weighty = 1;
	gbc.gridheight = 2;
	inf2.add(jsd, gbc);

	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, inf2, inf);
	split.setBorder(null);
	split.setDividerSize(0);
	split.setResizeWeight(0.5);
	split.setDividerLocation(-1);

	bot.add(split, "infraestructura");
	bot.add(soc, "social");

	this.setLayout(new GridBagLayout());
	gbc.gridwidth = 2;
	gbc.weightx = 1;
	gbc.gridx = 0;
	gbc.insets = new Insets(0, 10, 10, 10);
	gbc.gridy = 0;
	gbc.weighty = 1;
	gbc.gridheight = 4;
	gbc.fill = GridBagConstraints.BOTH;
	this.add(top, gbc);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy += 5;
	gbc.weighty = 1;
	this.add(bot, gbc);
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weighty = 0;
	gbc.gridy += 6;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.insets = new Insets(0, 10, 10, 10);
	this.add(prop_proy_ciu, gbc);
	gbc.gridx += 1;
	this.add(prop_proy_col, gbc);

    }

    @Override
    public void setControlador(Controlador al) {

	explorador.addActionListener(al);
	prop_proy_ciu.addActionListener(al);
	prop_proy_col.addActionListener(al);
	tipo_inf.addActionListener(al);
	tipo_social.addActionListener(al);
	imp1.addActionListener(al);
	imp2.addActionListener(al);
	back.addActionListener(al);
    }

    /**
     * Devuelve el texto del campo titulo_p (Titulo del proyecto)
     * 
     * @return Titulo del proyecto
     */
    public String getTitulo() {
	return titulo_p.getText();
    }

    /**
     * Establece el texto del campo titulo_p (Titulo del proyecto)
     * 
     * @param text Titulo del proyecto
     */
    public void setTextTitulo(String text) {
	titulo_p.setText(text);
    }

    /**
     * Devuelve el texto del campo desc_p (Descripcion del proyecto)
     * 
     * @return Descripcion del proyecto
     */
    public String getDesc() {
	return desc_p.getText();
    }

    /**
     * Establece el texto del campo desc_p (Descripcion del proyecto)
     * 
     * @param text Descripcion del proyecto
     */
    public void setTextDesc(String text) {
	desc_p.setText(text);
    }

    /**
     * Devuelve el texto del campo path (Path de la imagen del proyecto)
     * 
     * @return Path del proyecto
     */
    public String getPath() {
	return path.getText();
    }

    /**
     * Establece el texto del campo path (Path de la imagen del proyecto)
     * 
     * @param text Path del proyecto
     */
    public void setTextPath(String text) {
	path.setText(text);
    }

    /**
     * Devuelve el texto del campo importe_p (Importe del proyecto)
     * 
     * @return Importe del proyecto
     */
    public String getImporte() {
	return importe_p.getText();
    }

    /**
     * Establece el texto del campo importe_p (Importe del proyecto)
     * 
     * @param text Importe del proyecto
     */
    public void setTextImporte(String text) {
	importe_p.setText(text);
    }

    /**
     * Devuelve el texto del campo g_social (Grupo Social del proyecto)
     * 
     * @return Grupo Social del proyecto
     */
    public String getGrupoSocial() {
	return g_social.getText();
    }

    /**
     * Devuelve el si se ha seleccionado Nacional (true) o Internacional (false) en
     * el campo Impacto del Proyecto
     * 
     * @return true (Nacional) o false (Internacional)
     */
    public boolean getImpacto() {
	return imp1.isSelected();
    }

    /**
     * Devuelve el si se ha seleccionado Infraestructura (true) o Social (false) en
     * el campo Tipo de Proyecto
     * 
     * @return true (Infraestructura) o false (Social)
     */
    public boolean getTipoProyecto() {
	return tipo_inf.isSelected();
    }

    /**
     * Establece el texto del campo g_social (Grupo Social del proyecto)
     * 
     * @param text Grupo Social del proyecto
     */
    public void setTextGrupoSocial(String text) {
	g_social.setText(text);
    }

    /**
     * Activa o desactiva el boton Proponer como Colectivo
     * 
     * @param b <code> true </code> para activar el botón y <code>false </code> para
     *          desactivarlo
     */
    public void setProponerColButton(boolean b) {
	prop_proy_col.setEnabled(b);
    }

    /**
     * Selecciona el tipo de proyecto que se va a crear:
     * <ul>
     * <li>Infraestructura: "infraestructura"
     * <li>Social: "social"
     * </ul>
     * 
     * @param card Carta que se quiere mostrar
     */
    public void selectCard(String card) {
	cards.show(bot, card);
    }

    /**
     * Devuelve los distritos que se han seleccionado
     * 
     * @return Lista con los indices de los distritos seleccionados
     */
    public ArrayList<Integer> getSelectedDistritos() {
	ArrayList<Integer> dist = new ArrayList<>();

	int i = 0;
	for (JCheckBox c : checkboxes) {

	    if (c.isSelected()) {
		dist.add(i);
	    }
	    i++;
	}

	return dist;
    }

    /**
     * Establece el texto de las checkboxes
     * 
     * @param dist Lista con el nombre de los distritos
     */
    public void setDistritos(List<String> dist) {

	for (String s : dist) {
	    JCheckBox checkbox = new JCheckBox(s);
	    c_box.add(checkbox);
	    checkboxes.add(checkbox);
	}
    }

    /**
     * Limpia el texto de las checkboxes
     * 
     */
    public void clearDistritos() {
	for (JCheckBox c : checkboxes) {
	    c.setSelected(false);
	}
    }

    @Override
    public void madeVisible() {
	titulo_p.requestFocus();
    }
}
