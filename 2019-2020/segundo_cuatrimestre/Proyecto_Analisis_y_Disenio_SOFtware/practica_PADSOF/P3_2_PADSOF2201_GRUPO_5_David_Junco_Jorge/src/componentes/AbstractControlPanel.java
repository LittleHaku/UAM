package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import componentes.IconButton.FillMode;
import controlador.Controlador;
import vista.Vista;

/**
 * AbstractControlPanel representa un panel de control sin definir su contenido
 * <p>
 * El contiene los elementos comunes a los paneles de control, es decir:
 * <ul>
 * <li>Botón de volver hacia atrás
 * <li>Título del Panel de Control
 * <li>Campana de las notificaciones
 * <li>Panel lateral de notificaciones
 * </ul>
 * 
 * Además, esta clase es la que gestiona la animación de apertura del panel <br>
 * de notificaciones desde el lateral. De este modo conseguimos separar los
 * demás <br>
 * componentes del panel de control del proceso de la animación.
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class AbstractControlPanel extends Vista {
    private static final long serialVersionUID = -2221088294190704979L;

    /* Componentes generales */
    private final JLabel titulo;
    private final JSplitPane jsp;
    protected final PanelNotificaciones pnotif;
    protected final NotifBell bell;
    protected final IconButton back;

    /* Componentes responsables de la animación */
    private Timer controlAnimacion;
    private boolean targetShowNotifications = false;
    private int notifWidth = 250;
    private int currentNotifWidth = 0;
    private boolean showingNotifications = false;
    private int notifStep;
    private int notifStepIni = 25;

    /* Contenido del panel de control */
    private JPanel content;

    /**
     * Crea un nuevo panel de control abstacto
     * <p>
     * Inicializa los elementos responsables de animación del panel <br>
     * de notificaciones y coloca todos los componentes en sus paneles. <br>
     * 
     * @param id        Identificador de la vista
     * @param predefDim Dimensiones predefinidas de la ventana
     */
    public AbstractControlPanel(String id, Dimension predefDim) {
	super(id, predefDim);
	titulo = new JLabel("Panel de Control");
	titulo.setHorizontalAlignment(SwingConstants.CENTER);
	titulo.setFont(ComponentConstants.F_TITLE);
	titulo.setForeground(Color.black);
	bell = new NotifBell(0);
	bell.setFillMode(FillMode.HEIGHT);
	back = new IconButton(ComponentConstants.I_DOOR);
	back.setActionCommand("exit");

	JPanel top = new JPanel(new GridBagLayout());
	top.setOpaque(false);
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 0.01;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(10, 10, 10, 0);

	top.add(back, gbc);
	gbc.insets = new Insets(13, 10, 13, 0);
	gbc.weightx = 1;
	gbc.gridx++;
	top.add(titulo, gbc);
	gbc.insets = new Insets(10, 10, 10, 0);
	gbc.gridx++;
	gbc.weightx = 0.01;
	top.add(bell, gbc);

	pnotif = new PanelNotificaciones();
	pnotif.setMinimumSize(new Dimension(notifWidth, this.getMinimumSize().height));

	jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, content, pnotif);
	jsp.setBorder(null);
	jsp.setResizeWeight(1.0);
	jsp.setDividerSize(0);
	jsp.setDividerLocation(1.0);

	this.setLayout(new GridBagLayout());
	gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridwidth = 1;
	this.add(top, gbc);
	gbc.gridy = 1;
	gbc.weighty = 1;
	gbc.fill = GridBagConstraints.BOTH;
	this.add(jsp, gbc);

	initializeAnimacion();
    }

    /**
     * Establece el controlador para el panel de control
     * <p>
     * Esta función debe de utilizarse cuando se instancie esta clase <br>
     * para que el controlador tenga acceso a las notificaciones y al botón de
     * atrás.
     * 
     * @param al Controlador del panel
     */
    @Override
    public void setControlador(Controlador al) {
	this.pnotif.setControlador(al);
	this.back.addActionListener(al);
    }

    /**
     * Establece el panel central del panel de control
     * <p>
     * Este es el panel que aparece debajo de la barra inicial. <br>
     * Este panel ocupará la mayor parte del panel de control <br>
     * y será desplazado a la izquierda por el panel de notifiaciones<br>
     * cuando este se abra
     * 
     * 
     * @param content Contenido del panel de control
     */
    public void setContent(JPanel content) {
	this.jsp.setLeftComponent(content);
	this.content = content;
    }

    /**
     * Establece el titulo del panel de control
     * <p>
     * El título es el texto que aparece en la parte superior de la vista
     * 
     * @param s Titulo del panel de control
     */
    public void setTitle(String s) {
	this.titulo.setText(s);
    }

    /**
     * Establece el contenido de las notificaciones
     * 
     * @param titulos     Texto de las notificaciones
     * @param acciones    Acciones disponibles para cada notificación
     * @param visto       Si cada notificación ha sido vista o no
     * @param discardable Si cada notificación puede ser elminada (sin aceptar ni
     *                    rechazar) o no
     */
    public void setNotificaciones(List<String> titulos, List<List<String>> acciones, List<Boolean> visto,
	    List<Boolean> discardable) {
	pnotif.setData(titulos, acciones, visto, discardable);
	int n = visto.stream().mapToInt(e -> (e ? 0 : 1)).sum();
	bell.setNumero(n);
	bell.repaint();
	if (titulos.size() == 0) {
	    closeNotifications();
	}

    }

    /**
     * Inicializa los elementos relacionados con la animación
     * <p>
     * Este método establece el temporizador que va movientodo los paneles <br>
     * y añade un listener para mantener los tamaños deseados al redimensionar <br>
     * la ventana
     */
    private void initializeAnimacion() {

	controlAnimacion = new Timer(20, e -> {

	    if (targetShowNotifications && notifWidth == currentNotifWidth) {
		controlAnimacion.stop();

		pnotif.setVisible(true);
	    } else if (!targetShowNotifications && currentNotifWidth == 0) {
		controlAnimacion.stop();
		showingNotifications = false;

	    } else {
		currentNotifWidth += notifStep;
		currentNotifWidth = Math.min(currentNotifWidth, notifWidth);
		currentNotifWidth = Math.max(currentNotifWidth, 0);
		jsp.setDividerLocation(jsp.getDividerLocation() - notifStep);
	    }
	});

	bell.addActionListener(e -> {

	    if (!targetShowNotifications) {
		openNotifications();

	    } else {
		closeNotifications();

	    }

	});

	jsp.addComponentListener(new ComponentAdapter() {

	    @Override
	    public void componentResized(ComponentEvent e) {
		if (!showingNotifications) {
		    jsp.setDividerLocation(e.getComponent().getWidth());
		} else {
		    jsp.setDividerLocation(e.getComponent().getWidth() - currentNotifWidth);
		}

	    }
	});
    }

    @Override
    public void madeVisible() {
	jsp.setDividerLocation(1.0);
    }

    /**
     * Provoca la apertura del panel de notificaciones
     */
    public void openNotifications() {
	pnotif.setVisible(false);
	targetShowNotifications = true;
	notifStep = notifStepIni;
	showingNotifications = true;

	controlAnimacion.stop();
	controlAnimacion.start();
    }

    /**
     * Provoca el cierre del panel de notificaciones
     */
    public void closeNotifications() {
	pnotif.setVisible(false);
	targetShowNotifications = false;
	notifStep = -notifStepIni;

	controlAnimacion.stop();
	controlAnimacion.start();
    }
}
