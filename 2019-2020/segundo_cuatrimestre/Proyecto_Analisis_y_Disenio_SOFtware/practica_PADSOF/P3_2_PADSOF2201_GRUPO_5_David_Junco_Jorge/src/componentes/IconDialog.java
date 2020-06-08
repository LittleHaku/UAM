package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import componentes.IconButton.FillMode;

/**
 * El IconDialog representa un diálogo un icono
 * <p>
 * En particular, contiene los siguientes elementos:
 * <ul>
 * <li>Un título
 * <li>Un icono a la izquierda del título
 * <li>Una descripción
 * <li>Un botón para cerrarlo
 * </ul>
 * 
 * La utilidad principal de esta clase es generar los diálogos de éxito o de
 * error <br>
 * que aparecen para confirmar las acciones realizadas o para indicar<br>
 * que se ha producido un error.
 * <p>
 * Cabe destacar que se pueden añadir una acción adicional al botón que
 * utilizamos <br>
 * para cerrar el diálogo utilizando el constructor adecuado.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class IconDialog extends JDialog implements ComponentListener {
    private static final long serialVersionUID = -32161598706704235L;

    /* Componentes del diálogo */
    protected final JLabel title;
    protected final JTextArea body;
    protected final IconButton image;
    protected final JFrame owner;

    protected final MButton ret;

    /**
     * Crea un nuevo diálogo con los datos indicados
     * 
     * @param owner Ventana dueña del diálogo
     * @param tit   Texto del títululo del diálogo
     * @param desc  Texto de la descripción
     * @param icon  Ruta de la imagen que aparece a la izquierda del título
     */
    public IconDialog(JFrame owner, String tit, String desc, String icon) {
	super(owner);
	this.owner = owner;
	this.setResizable(false);
	this.setUndecorated(true);
	this.setModal(true);
	this.title = new JLabel(tit);
	this.title.setFont(ComponentConstants.F_HEADING);
	this.body = new JTextArea(desc);
	this.body.setLineWrap(true);
	this.body.setWrapStyleWord(true);
	this.body.setForeground(ComponentConstants.C_FORE_DIALOG);
	this.body.setBackground(new Color(0, 0, 0, 0));
	this.body.setOpaque(false);
	this.body.setBorder(BorderFactory.createEmptyBorder());
	this.body.setEditable(false);
	this.image = new IconButton(icon);
	image.setFillMode(FillMode.HEIGHT);

	ret = new MButton("Volver");
	ret.addActionListener(e -> {
	    this.setVisible(false);
	    owner.setEnabled(true);
	    this.dispose();
	    owner.requestFocus();

	});

	JPanel jp = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.insets = new Insets(5, 10, 5, 10);
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	gbc.weightx = 1;
	gbc.weighty = 1;
	gbc.fill = GridBagConstraints.BOTH;

	jp.add(this.image, gbc);
	gbc.weightx = 0;
	gbc.gridx = 1;
	gbc.insets = new Insets(30, 10, 30, 20);
	jp.add(this.title, gbc);
	gbc.gridx = 0;
	gbc.gridwidth = 2;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.gridy++;
	gbc.insets = new Insets(10, 10, 10, 10);
	jp.add(this.body, gbc);
	gbc.gridy++;
	jp.add(getButtonPanel(), gbc);

	jp.setBackground(ComponentConstants.C_DIALOG);
	jp.setBorder(ComponentConstants.C_BORDER);
	this.setContentPane(jp);

	image.repaint(0);
	this.revalidate();
	this.pack();
	Dimension d = this.getPreferredSize();
	d.width += 55;
	this.setSize(d);
	this.setLocationRelativeTo(owner);

	this.addComponentListener(this);

	/* Mantiene la posición del diálogo respecto a la ventana */
	owner.addComponentListener(new ComponentAdapter() {

	    @Override
	    public void componentMoved(ComponentEvent e) {
		setLocationRelativeTo(owner);

	    }
	});

    }

    /**
     * Crea un nuevo diálogo con icono y añade una acción adicional al botón de
     * salir
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Texto del título del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param icon  Ruta de la imagen que aparece a la izquierda del titulo
     * @param f     Accion adicional que se ejecutará cuando se haga click en el
     *              botón
     */
    public IconDialog(JFrame owner, String title, String desc, String icon, Consumer<ActionEvent> f) {
	this(owner, title, desc, icon);
	ret.addActionListener(e -> f.accept(e));
    }

    /**
     * Establece los botones del panel
     * <p>
     * La sobreescritura de este método en las subclases permite modificar <br>
     * los contenidos del diálogo sin tener que volver a insertar todos los <br>
     * componentes en el panel.
     * <p>
     * EL panel devuelto por este método será el que ocupe la parte inferior del
     * diálogo <br>
     * Como mínimo, es necesario que contenga un botón que cierre el diálogo ya que
     * de lo <br>
     * contrario, esta no se podrá cerrar
     * 
     * @return Panel de los botones del diálogo
     */
    protected JPanel getButtonPanel() {
	JPanel jp = new JPanel(new BorderLayout());
	jp.add(ret, BorderLayout.CENTER);
	return jp;
    }

    @Override
    public void componentResized(ComponentEvent e) {
	int w = (int) (title.getPreferredSize().getWidth() + image.getPreferredSize().getWidth() + 60);
	setSize(new Dimension(w, getHeight()));

    }

    @Override
    public void componentShown(ComponentEvent e) {
	componentResized(e);
	ret.requestFocus();
	owner.setEnabled(false);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
	owner.setEnabled(true);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * Crea un nuevo diálogo de error y lo hace visible
     * <p>
     * Los dialogos de error son instancias de <code> IconDialog </code> que
     * utilizan <br>
     * el icono de error definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param f     Acción adicional a realizar cuando se cierre el diálogo
     */
    public static void errorDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> f) {
	IconDialog id = new IconDialog(owner, title, desc, ComponentConstants.I_ERROR, f);
	id.setVisible(true);
    }

    /**
     * Crea un nuevo diálogo de error y lo hace visible
     * <p>
     * Los dialogos de error son instancias de <code> IconDialog </code> que
     * utilizan <br>
     * el icono de error definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     */
    public static void errorDialog(JFrame owner, String title, String desc) {
	IconDialog id = new IconDialog(owner, title, desc, ComponentConstants.I_ERROR);
	id.setVisible(true);
    }

    /**
     * Crea un nuevo diálogo de éxito y lo hace visible
     * <p>
     * Los dialogos de éxito son instancias de <code> IconDialog </code> que
     * utilizan <br>
     * el icono de éxito definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     * @param f     Acción adicional a realizar cuando se cierre el diálogo
     */
    public static void successDialog(JFrame owner, String title, String desc, Consumer<ActionEvent> f) {
	IconDialog id = new IconDialog(owner, title, desc, ComponentConstants.I_SUCCESS, f);
	id.setVisible(true);
    }

    /**
     * Crea un nuevo diálogo de éxito y lo hace visible
     * <p>
     * Los dialogos de éxito son instancias de <code> IconDialog </code> que
     * utilizan <br>
     * el icono de éxito definido en <code> ComponentConstants</code> como icono.
     * 
     * @param owner Ventana dueña del diálogo
     * @param title Titulo del diálogo
     * @param desc  Texto de la descripción del diálogo
     */
    public static void successDialog(JFrame owner, String title, String desc) {
	IconDialog id = new IconDialog(owner, title, desc, ComponentConstants.I_SUCCESS);
	id.setVisible(true);
    }

}
