package vista;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.ImagePanel;
import componentes.MButton;
import controlador.Controlador;

/**
 * Es la vista que muestra la información de un proyecto.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaInfoProyecto extends Vista {
    private static final long serialVersionUID = -8292411798501170601L;

    private final JPanel mid;
    private final JPanel soc;
    private final JPanel bot;
    private final CardLayout cards;
    private final CardLayout cards2;
    private final JTextArea dist_p;
    private final MButton b_apoyar;
    private final MButton b_apoyar_col_creador;
    private final MButton b_apoyar_col_usr;
    private final MButton b_consultar;
    private final MButton b_sub;
    private final MButton b_consultar_ap;
    private final MButton b_enviar;
    private final JLabel titulo_p;
    private final JTextArea desc_p;
    private final JLabel estado_p;
    private final JLabel tipo_p;
    private final JLabel g_social;
    private final JLabel nac_int;
    private final JLabel importe_p;
    private final JLabel importe_adj;
    private final ImagePanel imagen_p;
    private final JLabel importe_text;
    private final JLabel importe_adj_text;

    /**
     * Crea una nueva vista de info Proyecto
     */
    public VistaInfoProyecto() {
	super("infoProyecto", new Dimension(900, 500));

	JLabel title = new JLabel("Titulo:");
	title.setHorizontalAlignment(SwingConstants.LEFT);
	title.setFont(ComponentConstants.F_HEADING);

	titulo_p = new JLabel();
	titulo_p.setHorizontalAlignment(SwingConstants.LEFT);
	titulo_p.setFont(ComponentConstants.F_TEXT_HEADING);

	JLabel desc_lb = new JLabel("Descripcion:");
	desc_lb.setHorizontalAlignment(SwingConstants.LEFT);
	desc_lb.setFont(ComponentConstants.F_SUB_HEAD);
	desc_p = new JTextArea();
	desc_p.setAlignmentY(SwingConstants.LEFT);
	desc_p.setText("");
	// desc_p.setFont(ComponentConstants.F_TEXT_SUB_SUB_HEAD);
	desc_p.setBackground(Color.white);
	desc_p.setLineWrap(true);
	desc_p.setEditable(false);
	desc_p.setWrapStyleWord(true);
	desc_p.setCaretPosition(0);

	JScrollPane js = new JScrollPane(desc_p);
	js.setBorder(BorderFactory.createLineBorder(Color.black));

	JLabel est_lb = new JLabel("Estado:");
	est_lb.setHorizontalAlignment(SwingConstants.RIGHT);
	est_lb.setFont(ComponentConstants.F_SUB_HEAD);
	estado_p = new JLabel();
	estado_p.setHorizontalAlignment(SwingConstants.LEFT);
	estado_p.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	JLabel s_title = new JLabel("Tipo de Proyecto:");
	s_title.setHorizontalAlignment(SwingConstants.RIGHT);
	s_title.setFont(ComponentConstants.F_SUB_HEAD);

	tipo_p = new JLabel();
	tipo_p.setHorizontalAlignment(SwingConstants.LEFT);
	tipo_p.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	importe_p = new JLabel();
	importe_p.setHorizontalAlignment(SwingConstants.CENTER);
	importe_p.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	importe_adj = new JLabel();
	importe_adj.setHorizontalAlignment(SwingConstants.RIGHT);
	importe_adj.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	importe_text = new JLabel("Importe:");
	importe_text.setHorizontalAlignment(SwingConstants.LEFT);
	importe_text.setFont(ComponentConstants.F_SUB_HEAD);

	importe_adj_text = new JLabel("Imp. Adjudicado:");
	importe_adj_text.setHorizontalAlignment(SwingConstants.LEFT);
	importe_adj_text.setFont(ComponentConstants.F_SUB_HEAD);

	b_sub = new MButton("Suscribirse");
	b_sub.setActionCommand("suscribirse");
	b_consultar_ap = new MButton("Consultar Apoyos");
	b_consultar_ap.setActionCommand("consultar_apoyos");
	b_enviar = new MButton("Enviar");
	b_enviar.setActionCommand("enviar");
	b_apoyar = new MButton("Apoyar Proyecto");
	b_apoyar.setActionCommand("apoyar");
	b_apoyar_col_creador = new MButton("Apoyar como Colectivo");
	b_apoyar_col_creador.setActionCommand("apoyar_col");
	b_apoyar_col_usr = new MButton("Apoyar como Colectivo");
	b_apoyar_col_usr.setActionCommand("apoyar_col");
	b_consultar = new MButton("Consultar Estado");
	b_consultar.setActionCommand("con_estado");

	// Infraestructura

	dist_p = new JTextArea();
	dist_p.setAlignmentY(SwingConstants.LEFT);
	dist_p.setBackground(Color.white);
	dist_p.setEditable(false);
	desc_p.setLineWrap(true);
	dist_p.setCaretPosition(0);
	dist_p.setText("");

	JScrollPane jsd = new JScrollPane(dist_p);
	jsd.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	jsd.getViewport().setBackground(Color.white);

	imagen_p = new ImagePanel(ComponentConstants.I_IMAGE);

	JLabel dist = new JLabel("Distritos Afectados:");
	dist.setHorizontalAlignment(SwingConstants.CENTER);
	dist.setFont(ComponentConstants.F_SUB_HEAD);

	// Social

	JLabel g_text = new JLabel("Grupo Social:");
	g_text.setHorizontalAlignment(SwingConstants.LEFT);
	g_text.setFont(ComponentConstants.F_SUB_HEAD);

	g_social = new JLabel();
	g_social.setHorizontalAlignment(SwingConstants.LEFT);
	g_social.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	JLabel imp_p = new JLabel("Alcance del Proyecto:");
	imp_p.setHorizontalAlignment(SwingConstants.LEFT);
	imp_p.setFont(ComponentConstants.F_SUB_HEAD);

	nac_int = new JLabel();
	nac_int.setHorizontalAlignment(SwingConstants.LEFT);
	nac_int.setFont(ComponentConstants.F_TEXT_SUB_HEAD);

	JPanel top = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	top.add(title, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx += 1;
	gbc.weightx = 0;
	gbc.insets = new Insets(10, 10, 10, 10);
	top.add(titulo_p, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx++;
	gbc.weightx = 10;
	gbc.gridwidth = 2;
	gbc.insets = new Insets(0, 0, 0, 0);
	top.add(new JLabel(), gbc);

	gbc.gridwidth = 1;
	gbc.gridx += 3;
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.anchor = GridBagConstraints.EAST;
	gbc.weightx = 0;
	gbc.insets = new Insets(10, 0, 10, 10);
	top.add(est_lb, gbc);

	gbc.gridwidth = 1;
	gbc.weightx = 0;
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.gridx++;
	top.add(estado_p, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridwidth = 7;
	gbc.gridy++;
	top.add(desc_lb, gbc);
	gbc.gridy++;

	gbc.weightx = 1;
	gbc.weighty = 10;
	gbc.insets = new Insets(10, 0, 10, 10);
	top.add(js, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.gridy += 3;
	top.add(importe_text, gbc);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx++;
	top.add(importe_p, gbc);

	gbc.gridx++;
	gbc.weightx = 0;
	top.add(importe_adj_text, gbc);
	gbc.fill = GridBagConstraints.VERTICAL;
	gbc.gridx++;
	top.add(importe_adj, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(10, 0, 10, 10);
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.gridx += 2;
	top.add(s_title, gbc);

	gbc.gridwidth = 1;
	gbc.gridx++;
	gbc.weighty = 0;
	top.add(tipo_p, gbc);

	cards = new CardLayout();
	mid = new JPanel(cards);

	soc = new JPanel(new GridBagLayout());

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.weightx = 0;
	gbc.weighty = 0;
	gbc.insets = new Insets(0, 10, 0, 10);
	soc.add(g_text, gbc);

	gbc.weightx = 0;
	gbc.gridx++;
	soc.add(g_social, gbc);

	gbc.gridx++;
	gbc.weightx = 1;
	soc.add(new JLabel(" "), gbc);

	gbc.gridx++;
	gbc.weightx = 0;
	soc.add(imp_p, gbc);

	gbc.gridx++;
	gbc.weightx = 0;
	gbc.insets = new Insets(0, 10, 0, 20);
	soc.add(nac_int, gbc);

	JPanel inf = new JPanel(new GridBagLayout());

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.gridwidth = 1;
	gbc.weighty = 0;
	gbc.gridx = 0;
	inf.add(dist, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridy++;
	gbc.weighty = 0.1;
	gbc.gridheight = 1;
	inf.add(jsd, gbc);

	gbc.fill = GridBagConstraints.BOTH;
	gbc.weighty = 1;
	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.gridheight = 2;
	gbc.weightx = 2;
	gbc.insets = new Insets(10, 10, 10, 20);
	inf.add(imagen_p, gbc);

	inf.setBackground(this.getBackground());
	soc.setBackground(this.getBackground());

	mid.add(soc, "social");
	mid.add(inf, "infraestructura");

	cards2 = new CardLayout();
	bot = new JPanel(cards2);
	bot.setOpaque(true);
	inf.setOpaque(true);
	mid.setOpaque(true);
	JPanel fila1 = new JPanel(new GridBagLayout());

	gbc.gridx = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weighty = 0;
	gbc.weightx = 1;
	gbc.insets = new Insets(0, 10, 5, 5);
	fila1.add(b_sub, gbc);

	gbc.gridx++;
	fila1.add(b_apoyar, gbc);

	gbc.gridx++;
	fila1.add(b_apoyar_col_usr, gbc);

	JPanel fila2 = new JPanel(new GridBagLayout());

	gbc.gridx = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weighty = 0;
	gbc.weightx = 1;
	gbc.insets = new Insets(0, 10, 5, 5);
	fila2.add(b_consultar_ap, gbc);

	gbc.gridx++;
	fila2.add(b_apoyar_col_creador, gbc);

	gbc.gridx++;
	fila2.add(b_enviar, gbc);

	gbc.gridx++;
	fila2.add(b_consultar, gbc);

	bot.add(fila2, "creador");
	bot.add(fila1, "no creador");
	fila2.setBackground(this.getBackground());
	fila1.setBackground(this.getBackground());

	top.setBackground(this.getBackground());
	bot.setBackground(this.getBackground());

	this.setLayout(new GridBagLayout());
	gbc.weightx = 1;
	gbc.gridheight = 1;
	gbc.gridx = 0;
	gbc.insets = new Insets(0, 20, 0, 10);
	gbc.gridy = 0;
	gbc.weighty = 10;
	gbc.fill = GridBagConstraints.BOTH;
	this.add(top, gbc);
	gbc.gridy = 1;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.insets = new Insets(0, 10, 0, 10);
	this.add(mid, gbc);
	gbc.weighty = 0;
	gbc.gridy++;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(0, 10, 10, 10);
	this.add(bot, gbc);

    }

    @Override
    public void setControlador(Controlador al) {

	b_apoyar.addActionListener(al);
	b_consultar_ap.addActionListener(al);
	b_consultar.addActionListener(al);
	b_enviar.addActionListener(al);
	b_sub.addActionListener(al);
	b_apoyar_col_creador.addActionListener(al);
	b_apoyar_col_usr.addActionListener(al);

    }

    /**
     * Establece el texto de los principales elementos de la vista:
     * 
     * @param tit     titulo del proyecto
     * @param estado  estado del proyecto
     * @param desc    descripcion del proyecto
     * @param importe importe del proyecto
     * 
     */
    public void setGeneral(String tit, String estado, String desc, double importe) {
	titulo_p.setText(tit);
	desc_p.setText(desc);

	importe_p.setText(String.format("%.2f €", importe));
	estado_p.setText(estado);
    }

    /**
     * Establece el texto de los elementos de un proyecto social
     * 
     * @param impacto del proyecto
     * @param gSocial grupo social del proyecto
     * 
     */
    public void setSocial(String impacto, String gSocial) {
	nac_int.setText(impacto);
	g_social.setText(gSocial);
	tipo_p.setText("Social");
	super.setPredefSize(new Dimension(750, 400));
	selectCard("social");
    }

    /**
     * Establece el texto de los elementos de un proyecto infraestructura
     * 
     * @param names   nombres de los distritos
     * @param path_im path de la imagen del proyecto
     * 
     */
    public void setInfra(List<String> names, String path_im) {
	// Set distritos
	StringBuilder sb = new StringBuilder();
	for (String s : names) {
	    sb.append("- ");
	    sb.append(s);
	    sb.append("\n");
	}
	dist_p.setText(sb.toString());

	// Set image
	try {
	    imagen_p.setImage(path_im);
	} catch (IOException ex) {
	    try {
		imagen_p.setImage(ComponentConstants.I_IMAGE);
	    } catch (IOException e) {
		return;
	    }
	    return;
	}
	tipo_p.setText("Infraestructura");
	super.setPredefSize(new Dimension(750, 600));
	selectCard("infraestructura");
    }

    /**
     * Selecciona el tipo de proyecto que se va a inspeccionar:
     * <ul>
     * <li>Infraestructura: "infraestructura"
     * <li>Social: "social"
     * </ul>
     * 
     * @param card carta que se quiere mostrar
     */
    public void selectCard(String card) {

	cards.show(mid, card);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridy = 1;

	gbc.insets = new Insets(0, 10, 0, 10);
	if (card.equals("social")) {
	    gbc.weighty = 0.1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    mid.setPreferredSize(new Dimension(100, 40));

	} else {
	    gbc.weighty = 20;
	    gbc.fill = GridBagConstraints.BOTH;
	    mid.setPreferredSize(new Dimension(1000, 200));
	}
	this.remove(mid);
	this.getLayout().removeLayoutComponent(mid);
	this.add(mid, gbc);
	this.revalidate();
    }

    /**
     * Activa o desactiva el boton Enviar
     * 
     * @param b b <code> true </code> para activar el botón y <code>false </code>
     *          para desactivarlo
     */
    public void setEnviarButton(boolean b) {
	b_enviar.setEnabled(b);
    }

    /**
     * Activa o desactiva el boton Apoyar
     * 
     * @param b b <code> true </code> para activar el botón y <code>false </code>
     *          para desactivarlo
     */
    public void setApoyarButton(boolean b) {
	b_apoyar.setEnabled(b);
    }

    /**
     * Activa o desactiva los botones Apoyar como colectivo
     * 
     * @param b <code> true </code> para activar el botón y <code>false </code> para
     *          desactivarlo
     */
    public void setApoyarColButton(boolean b) {
	b_apoyar_col_creador.setEnabled(b);
	b_apoyar_col_usr.setEnabled(b);
    }

    /**
     * Activa o desactiva el boton Suscribirse
     * 
     * @param b <code> true </code> para activar el botón y <code>false </code> para
     *          desactivarlo
     */
    public void setSubButton(boolean b) {
	b_sub.setEnabled(b);
    }

    /**
     * Activa o desactiva el boton Consultar
     * 
     * @param b <code> true </code> para activar el botón y <code>false </code> para
     *          desactivarlo
     */
    public void setConultarButton(boolean b) {
	b_consultar.setEnabled(b);
    }

    /**
     * Establece el texto del campo importe adjudicado
     * 
     * @param text Texto del campo importe adjudicado
     */
    public void setTextImporteAdj(String text) {
	importe_adj.setText(text);
    }

    /**
     * Establece el texto del campo estado
     * 
     * @param text Texto del campo de estado
     */
    public void setTextEstado(String text) {
	estado_p.setText(text);
    }

    /**
     * Selecciona el de fila de botones a mostrar:
     * <ul>
     * <li>-1: Sin botones, Administrador
     * <li>0: Botones para el creador del proyecto
     * <li>1: Botones para el resto de usuarios
     * </ul>
     * 
     * @param i tipo de fila que se va a mostrar
     */
    public void setFilaButtons(int i) {

	bot.setVisible(true);
	if (i == 1) {
	    cards2.show(bot, "no creador");
	}
	if (i == 0) {
	    cards2.show(bot, "creador");
	}
	if (i == -1) {
	    bot.setVisible(false);
	}
    }

    public void madeVisible() {
	this.revalidate();
	this.repaint();
	mid.revalidate();
	mid.repaint();
    }

}
