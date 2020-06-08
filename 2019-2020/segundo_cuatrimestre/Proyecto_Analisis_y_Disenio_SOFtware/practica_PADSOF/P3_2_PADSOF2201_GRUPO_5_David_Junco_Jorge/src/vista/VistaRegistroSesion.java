package vista;

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
 * Es la vista de registro de un nuevo ciudadano
 * <p>
 * En esta vista se recogen los datos del usuario que se quiere registrar
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VistaRegistroSesion extends Vista {
    private static final long serialVersionUID = -1866079390057764118L;

    private final JTextField user;
    private final JTextField nif;
    private final JPasswordField pass;
    private final JPasswordField pass2;
    private final MButton submit;
    private final IconButton exit;

    /**
     * Crea la VistaRegistroSesion
     */
    public VistaRegistroSesion() {
	super("registro", new Dimension(400, 320));

	super.setResizeable(false);
	this.setLayout(new GridBagLayout());

	// Components
	JPanel top = new JPanel();
	top.setOpaque(false);
	top.setLayout(new GridBagLayout());

	JPanel bot = new JPanel();
	bot.setLayout(new GridBagLayout());
	bot.setOpaque(false);
	exit = new IconButton(ComponentConstants.I_ARROW);
	exit.setActionCommand("exit");

	JLabel title = new JLabel("Registro");
	title.setFont(ComponentConstants.F_TITLE);
	title.setHorizontalAlignment(SwingConstants.CENTER);

	JLabel txtUser = new JLabel("Nombre:");
	txtUser.setHorizontalAlignment(SwingConstants.RIGHT);
	txtUser.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	JLabel txtNif = new JLabel("NIF:");
	txtNif.setHorizontalAlignment(SwingConstants.RIGHT);
	txtNif.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	JLabel txtPass = new JLabel("Contraseña:");
	txtPass.setHorizontalAlignment(SwingConstants.RIGHT);
	txtPass.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	JLabel txtPass2 = new JLabel("Repetir contraseña:");
	txtPass2.setHorizontalAlignment(SwingConstants.RIGHT);
	txtPass2.setFont(ComponentConstants.F_SUB_SUB_HEAD);

	submit = new MButton("Enviar solicitud de registro");
	submit.setActionCommand("submit");

	pass2 = new JPasswordField();
	pass2.addActionListener(e -> submit.doClick());
	pass = new JPasswordField();
	pass.addActionListener(e -> pass2.requestFocus());
	nif = new JTextField();
	nif.addActionListener(e -> pass.requestFocus());
	user = new JTextField();
	user.addActionListener(e -> nif.requestFocus());

	// Header
	GridBagConstraints gbc = new GridBagConstraints();

	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 0;
	gbc.weighty = 0;
	top.add(exit, gbc);

	gbc.weightx = 1;
	gbc.weighty = 0;
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
	bot.add(txtNif, gbc);
	gbc.insets = new Insets(0, 10, 5, 10);
	gbc.weightx = 1;
	gbc.gridx++;
	bot.add(nif, gbc);

	gbc.gridy++;
	gbc.gridx = 0;
	gbc.weightx = 0;
	gbc.insets = new Insets(0, 10, 5, 0);
	bot.add(txtPass, gbc);
	gbc.insets = new Insets(0, 10, 5, 10);
	gbc.weightx = 1;
	gbc.gridx++;
	bot.add(pass, gbc);

	gbc.gridy++;
	gbc.gridx = 0;
	gbc.weightx = 0;
	gbc.insets = new Insets(0, 10, 5, 0);
	bot.add(txtPass2, gbc);
	gbc.insets = new Insets(0, 10, 5, 10);
	gbc.weightx = 1;
	gbc.gridx++;
	bot.add(pass2, gbc);

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
     * Getter de la contraseña escrita
     * 
     * @return la contraseña
     */
    public String getPass() {
	return new String(pass.getPassword());
    }

    /**
     * Getter de la contraseña repetida escrita
     * 
     * @return la contraseña repetida
     */
    public String getPass2() {
	return new String(pass2.getPassword());
    }

    /**
     * Devuelve el NIF
     * 
     * @return NIF
     */
    public String getNif() {
	return nif.getText();
    }

    /**
     * Borra el texto de los campos
     */
    public void clearFields() {
	user.setText("");
	nif.setText("");
	pass.setText("");
	pass2.setText("");
    }

    @Override
    public void madeVisible() {
	user.requestFocus();
    }
}
