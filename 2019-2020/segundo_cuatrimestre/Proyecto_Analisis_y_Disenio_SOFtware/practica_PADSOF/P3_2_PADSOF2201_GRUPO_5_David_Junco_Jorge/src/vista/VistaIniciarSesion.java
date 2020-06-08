package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.ComponentConstants;
import componentes.IconButton;
import componentes.MButton;
import controlador.Controlador;

/**
 * Es la vista vista de iniciar sesión. En un JTextField hay que escribir el
 * nombre o el NIF, en el otro la contraseña, para poder acceder a la
 * aplicacion.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaIniciarSesion extends Vista {
    private static final long serialVersionUID = -1866079390057764118L;
    private final JPanel top;
    private final JPanel bot;
    private final JLabel title;
    private final JLabel txtUser;
    private final JTextField user;
    private final JLabel txtPass;
    private final JPasswordField pass;
    private final MButton submit;
    private final IconButton exit;

    /**
     * Crea la VistaIniciarSesion
     */
    public VistaIniciarSesion() {
	super("iniSesion", new Dimension(400, 240));
	super.setResizeable(false);
	this.setLayout(new GridBagLayout());

	// Components
	top = new JPanel();
	top.setLayout(new GridBagLayout());
	top.setOpaque(false);
	bot = new JPanel();
	bot.setLayout(new GridBagLayout());
	bot.setOpaque(false);

	exit = new IconButton(ComponentConstants.I_ARROW);
	exit.setActionCommand("exit");

	title = new JLabel("Inicio de sesión");
	title.setFont(ComponentConstants.F_TITLE);
	title.setHorizontalAlignment(SwingConstants.CENTER);
	title.setForeground(Color.black);
	txtUser = new JLabel("Usuario (o NIF):");
	txtUser.setHorizontalAlignment(SwingConstants.RIGHT);
	txtUser.setFont(ComponentConstants.F_SUB_SUB_HEAD);
	txtUser.setForeground(Color.black);
	txtPass = new JLabel("Contraseña:");
	txtPass.setHorizontalAlignment(SwingConstants.RIGHT);
	txtPass.setFont(ComponentConstants.F_SUB_SUB_HEAD);
	txtPass.setForeground(Color.black);

	submit = new MButton("Entrar");
	submit.setActionCommand("submit");
	pass = new JPasswordField();
	pass.addActionListener(e -> submit.doClick());
	user = new JTextField();
	user.addActionListener(e -> pass.requestFocus());

	// Header
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	top.add(exit, gbc);
	gbc.insets = new Insets(10, 30, 10, 10);
	gbc.weightx = 1;
	top.add(title, gbc);

	// Content
	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.weighty = 0;
	gbc.weightx = 0;
	gbc.ipady = 5;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(0, 10, 5, 0);
	bot.add(txtUser, gbc);
	gbc.insets = new Insets(0, 10, 5, 10);
	gbc.weightx = 1;
	gbc.gridx++;
	bot.add(user, gbc);

	gbc.gridy++;
	gbc.gridx = 0;
	gbc.weightx = 0;
	gbc.insets = new Insets(0, 10, 5, 0);
	bot.add(txtPass, gbc);
	gbc.insets = new Insets(0, 10, 5, 10);
	gbc.weightx = 1;
	gbc.gridx++;
	bot.add(pass, gbc);

	gbc.gridx = 0;
	gbc.gridwidth = 2;
	gbc.gridy++;
	gbc.weighty = 0;
	gbc.weightx = 1;
	gbc.insets = new Insets(10, 10, 5, 10);
	bot.add(submit, gbc);

	// Final
	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.insets = new Insets(0, 0, 10, 0);
	gbc.gridy = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weightx = 1;
	gbc.weighty = 0;
	this.add(top, gbc);

	gbc.gridy = 1;
	gbc.insets = new Insets(0, 0, 0, 0);
	gbc.weighty = 0;
	this.add(bot, gbc);

	gbc.gridy++;
	gbc.weighty = 1;
	this.add(new JLabel(), gbc);
    }

    @Override
    public void setControlador(Controlador e) {
	submit.addActionListener(e);
	exit.addActionListener(e);
    }

    /**
     * Getter del nombre de usuario escrito
     * 
     * @return el nombre de usuario
     */
    public String getUser() {
	return user.getText();
    }

    /**
     * Getter de la contraseña escrito
     * 
     * @return la contraseña
     */
    public String getPass() {
	return new String(pass.getPassword());
    }

    /**
     * {@inheritDoc} Añade ActionListeners
     */
    @Override
    public void madeVisible() {
	user.setText("");
	pass.setText("");
	user.requestFocus();
    }
}
