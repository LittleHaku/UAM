package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import controlador.Controlador;

/**
 * Es la vista vista inicial. Continene el nombre de la aplicacion y el logo.
 * Tambien los botones de iniciar sesion y registrarse.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaInicial extends Vista {
    private static final long serialVersionUID = 2629278403493442470L;
    private final MButton registro;
    private final MButton iniSesion;
    private final MButton avanzar;
    private final IconButton image;
    private final JTextPane title;

    /**
     * Crea la VistaInicial
     */
    public VistaInicial() {
	super("inicial", new Dimension(450, 600));
	this.setLayout(new GridBagLayout());
	this.setResizeable(false);

	// Components
	title = new JTextPane();
	title.setText("Ciudad de Proyectos y Colectivos");

	title.setForeground(Color.black);
	title.setBackground(new Color(0, 0, 0, 0));
	title.setOpaque(false);
	title.setEditable(false);
	title.setHighlighter(null);

	SimpleAttributeSet center = new SimpleAttributeSet();
	StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

	title.setParagraphAttributes(center, false);
	title.setFont(ComponentConstants.F_TITLE);
	int hAdjust = 5;
	registro = new MButton("Registrarse");
	registro.setActionCommand("registro");
	registro.setHeightAdjust(hAdjust);

	iniSesion = new MButton("Iniciar Sesión");
	iniSesion.setActionCommand("iniSesion");
	iniSesion.setHeightAdjust(hAdjust);

	avanzar = new MButton("Avanzar 30 Dias");
	avanzar.setActionCommand("avanzar");
	avanzar.setHeightAdjust(hAdjust);

	image = new IconButton(ComponentConstants.I_LOGO);
	image.setHorizontalAlignment(SwingConstants.CENTER);

	String ss = System.getProperty("os.name");

	/*
	 * Si es un mac, intentar utilizar el logo de más resolución, para aprovechar el
	 * antialiasing En los demás iconos no hay diferencia
	 */
	if (ss != null) {
	    if (ss.toLowerCase().contains("mac") || ss.toLowerCase().contains("darwin")) {
		try {
		    image.setImage("icons/logo_s.png");
		} catch (IOException e) {

		}
	    }
	}

	// Content
	JPanel bot = new JPanel(new GridBagLayout());
	bot.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;

	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.insets = new Insets(10, 10, 0, 10);
	gbc.gridy = 0;
	gbc.gridheight = 1;
	gbc.gridwidth = 1;

	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.gridwidth = 3;
	this.add(title, gbc);

	gbc.gridy = 1;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.weighty = 1;
	gbc.anchor = GridBagConstraints.CENTER;
	this.add(image, gbc);

	gbc.weighty = 0;
	gbc.gridy = 2;
	gbc.gridx = 0;
	gbc.gridwidth = 1;
	gbc.insets = new Insets(10, 10, 7, 5);
	this.add(registro, gbc);
	gbc.gridx++;
	gbc.insets = new Insets(10, 5, 7, 10);
	this.add(iniSesion, gbc);
	gbc.gridy++;
	gbc.gridx = 0;
	gbc.gridwidth = 2;
	gbc.insets = new Insets(0, 10, 10, 10);
	this.add(avanzar, gbc);

	gbc.gridx = 0;
	gbc.gridy++;
	gbc.gridwidth = 3;
	gbc.insets = new Insets(0, 0, 0, 0);
	this.add(bot, gbc);
    }

    @Override
    public void setControlador(Controlador a) {
	iniSesion.addActionListener(a);
	registro.addActionListener(a);
	avanzar.addActionListener(a);

    }
}
