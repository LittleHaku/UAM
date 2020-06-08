package componentes;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;

/**
 * Es la representación gráfica de una notificación
 * <p>
 * Este panel contiene toda la información que contiene la notificación:
 * <ul>
 * <li>El texto de la notificación
 * <li>Todas las acciones que se pueden tomar con respecto a la notificación
 * <br>
 * toman forma de botones
 * <li>Si la notificación no ha sido marcada como vista (click) esta tiene <br>
 * un recuadro alrededor para indicar que no ha sido leída.
 * </ul>
 * 
 * Para procesar las acciones de los botones de la notificación, se debe de
 * añadir un <br>
 * action listener utilizando {@link #setControlador(ActionListener al)}. <br>
 * En la descripción de ese método también aparecen más detalles sobre los tipos
 * de eventos <br>
 * que este panel genera.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class NotifComponent extends JPanel implements MouseListener {
    private static final long serialVersionUID = -2539918428262678050L;

    /* Arco del rectángulo redondeado que sirve de fondo para la notificación */
    private static final double ARC = 10.0;
    /* Color de fondo de la notificación */
    private static final Color backColor = Color.white;

    /* Componentes */
    private final JTextArea tArea;
    private final List<MButton> buttons;

    /* Texto de la notificación */
    private final String text;
    /*
     * Lista de las acciones (actionCommand) que realizan los botones de la
     * notificación
     */
    private final List<String> actions;
    /* Si la notificación ha sido vista o no */
    private boolean seen = false;
    /* si la notificación puede ser eliminada sin tomar ninguna acción */
    private boolean discardable = true;
    /* Si el ratón está dentro de la notificación */
    private boolean mouseIn = false;
    /* Indice de la notificación en el panel. Se puede entender como un ID */
    private int index;
    /* ActionListener responsable de recoger los eventos de esta notificación */
    private ActionListener control;

    /**
     * Crea un nuevo panel de notificación
     * <p>
     * Este panel representa una sola notificación con los valores dados
     * 
     * @param index       Índice de la notificación en el panel que la contiene (es
     *                    un identificador)
     * @param text        Texto del cuerpo de la notificación
     * @param actions     Lista de acciones que se pueden realizar en esta
     *                    notificación. Cada elemento será el titulo y el comando de
     *                    un botón
     * @param seen        Si la notificación está marcada como vista
     * @param discardable Si la notificación puede ser elminada sin tomar ninguna
     *                    acción (solo eliminarla)
     * 
     */
    public NotifComponent(int index, String text, List<String> actions, boolean seen, boolean discardable) {
	this.text = text;
	this.index = index;
	this.seen = seen;
	this.discardable = discardable;
	this.actions = new ArrayList<>(actions);
	this.setBackground(backColor);
	this.setOpaque(false);

	/*
	 * El siguiente bloque crea una JTextArea personalizada que ignora todo tipo de
	 * interacción con el ratón.
	 * 
	 * Esto es necesario para que el botón de eliminar notificación aparezca cuando
	 * debe aparecer
	 */
	tArea = new JTextArea(this.text) {
	    private static final long serialVersionUID = -3908836429427751874L;

	    @Override
	    public synchronized void addMouseListener(MouseListener l) {
	    }

	    @Override
	    public synchronized void addMouseMotionListener(MouseMotionListener l) {
	    }

	    @Override
	    public synchronized void addMouseWheelListener(MouseWheelListener l) {
	    }

	    @Override
	    public void addNotify() {
		disableEvents(
			AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		super.addNotify();
	    }

	};
	tArea.setEditable(false);
	tArea.setFocusable(false);
	tArea.setRequestFocusEnabled(false);
	tArea.setLineWrap(true);
	tArea.setWrapStyleWord(true);
	tArea.setBorder(null);
	tArea.setBackground(backColor);
	ToolTipManager.sharedInstance().unregisterComponent(tArea);
	tArea.setDragEnabled(false);

	buttons = new ArrayList<>();
	for (String s : this.actions) {
	    MButton m = new MButton(s);
	    m.setActionCommand("N_" + index + "_" + s);
	    m.setHeightAdjust(-2);
	    m.addMouseListener(this);
	    buttons.add(m);

	}

	this.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridy = 0;
	gbc.gridx = 0;
	gbc.weightx = 1;
	gbc.weighty = 0;
	gbc.gridwidth = 2;
	gbc.gridheight = 1;
	gbc.insets = new Insets(10, 10, 10, 10);
	gbc.fill = GridBagConstraints.BOTH;
	this.add(tArea, gbc);
	gbc.insets = new Insets(0, 7, 5, 7);
	gbc.gridy++;
	if (buttons.size() % 2 == 1) {
	    if (buttons.size() == 1) {
		gbc.insets = new Insets(0, 7, 7, 7);
	    }

	    this.add(buttons.get(0), gbc);
	    gbc.gridy++;
	}

	gbc.gridwidth = 1;
	for (int i = buttons.size() % 2; i < buttons.size(); i += 2) {
	    gbc.gridx = 0;
	    if (i == buttons.size() - 2) {
		gbc.insets = new Insets(0, 7, 7, 7);
	    }
	    this.add(buttons.get(i), gbc);
	    gbc.gridx = 1;
	    this.add(buttons.get(i + 1), gbc);
	    gbc.gridy++;
	}
	this.addMouseListener(this);

    }

    /**
     * Establece el controlador para la notificación
     * <p>
     * Los eventos que este recibirá comenzarán por <code>"N_"</code>, seguido del
     * número de notificación en el panel. <br>
     * A continuación, otro guión bajo y el comando de acción correspondiente a la
     * acción tomada.
     * <p>
     * Además de los comandos de acción definidos en la lista que se pasa al
     * construir el panel, contamos con otros dos:
     * <ul>
     * <li><code> "Click" </code>que representa que se ha hecho click en un punto de
     * la notificación que no tiene otra acción asociada.<br>
     * Puede utilizars para marcar la notificación como leida
     * <li><code> "Discard" </code> que representa que se ha hecho click en el botón
     * de eliminar notificación
     * </ul>
     * 
     * @param al Controlador
     */
    public void setControlador(ActionListener al) {
	buttons.forEach(e -> e.addActionListener(al));
	control = al;
    }

    /**
     * Establece si la notificación ha sido vista (o leída)
     * 
     * @param seen Si la notificación ha sido leída
     */
    public void setSeen(boolean seen) {
	this.seen = seen;
    }

    @Override
    public void paint(Graphics g) {
	if (!this.isVisible()) return;
	int w = this.getWidth();
	int h = this.getHeight();

	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	RoundRectangle2D rd = new RoundRectangle2D.Double(2, 2, w - 4, h - 4, ARC, ARC);
	g2d.setColor(this.getBackground());
	g2d.fill(rd);

	/* Pintamos el borde */
	if (!seen) {
	    g2d.setColor(new Color(0x3297FD));
	    g2d.setStroke(new BasicStroke(2));
	    g2d.draw(rd);
	} else {
	    g2d.setColor(Color.gray);
	    g2d.setStroke(new BasicStroke(1));
	    g2d.draw(rd);
	}

	paintChildren(g);

	/* Pintamos el aspa de cerrar, sobre los hijos */
	if (discardable && mouseIn) {

	    Ellipse2D el = new Ellipse2D.Double(w - 20 - 5, 5, 20, 20);
	    g2d.setColor(new Color(50, 50, 50, 200));
	    g2d.fill(el);
	    g2d.setColor(backColor);
	    g2d.setStroke(new BasicStroke(1f));
	    g2d.drawLine(w - 14 - 5, 11, w - 11, 19);
	    g2d.drawLine(w - 14 - 5, 19, w - 11, 11);
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
	/*
	 * Si hemos hecho click en el panel, miramos si ha sido sobre el aspa de
	 * eliminar
	 */
	if ((e.getComponent() instanceof JPanel)) {
	    if (discardable) {
		repaint();
		Point2D p = e.getPoint();
		Ellipse2D ell = new Ellipse2D.Double(getWidth() - 30 - 5, 5, 30, 30);
		if (ell.contains(p)) {
		    control.actionPerformed(new ActionEvent(e.getSource(), 0, "N_" + index + "_Discard"));
		    e.consume();
		    return;
		}
	    }
	}
	/*
	 * Si no hemos click en el aspa de eliminar y no estamos en un botón, es un
	 * click general
	 */
	if (!(e.getComponent() instanceof JButton)) {
	    control.actionPerformed(new ActionEvent(e.getSource(), 0, "N_" + index + "_Click"));
	    e.consume();
	}

    }

    @Override
    public void mouseEntered(MouseEvent e) {
	mouseIn = true;
	repaint();

    }

    @Override
    public void mouseExited(MouseEvent e) {
	mouseIn = false;
	repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

}
