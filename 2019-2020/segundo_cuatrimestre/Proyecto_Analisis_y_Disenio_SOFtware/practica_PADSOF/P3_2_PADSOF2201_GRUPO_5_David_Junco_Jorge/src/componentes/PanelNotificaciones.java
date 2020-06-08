package componentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Este panel representa una lista de notificaciones
 * <p>
 * Para ello coloca todas las notificaciones en un {@link JScrollPane} y
 * gestiona la segunda parte de la animación. <br>
 * La animación de la que se encarga este panel consiste en mostrar todas las
 * notificaciones, una tras otra, con un <br>
 * ligero retardo entre ellas.
 * <p>
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class PanelNotificaciones extends JPanel implements ComponentListener {
    private static final long serialVersionUID = -498032858804826136L;

    /* Panel que contiene todas las notificaciones */
    private final JPanel jpa;
    /* Panel total */
    private JPanel completo;
    /* ScrollPane con las notificaciones */
    private JScrollPane jsp;
    /* Lista de las notificaciones */
    private List<NotifComponent> notifs;

    /* Timer para mostrar las notificaciones */
    private Timer notificationShow;
    /* Indice de la siguiente notificación a mostrar durante la animación */
    private int tindex = 0;

    /* Controlador para las animaciones */
    private ActionListener controlador;

    /**
     * Crea un nuevo panel de notificaciones vacío.
     * <p>
     * Inicializa todos los componentes y el temporizador. <br>
     * Sin embargo la animación de mostrar las notificaciones solo comenzará cuando
     * se llame al <br>
     * método {@link #setVisible}.
     * 
     */
    public PanelNotificaciones() {
	super();
	this.setOpaque(false);
	notifs = new ArrayList<>();

	jpa = new JPanel();

	jpa.setLayout(new BoxLayout(jpa, BoxLayout.Y_AXIS));
	jpa.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	jpa.setOpaque(false);

	JPanel j2 = new JPanel(new BorderLayout());
	j2.setOpaque(false);
	j2.add(jpa, BorderLayout.NORTH);
	jsp = new JScrollPane(j2, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	jsp.addComponentListener(this);
	jsp.setWheelScrollingEnabled(true);
	jsp.getVerticalScrollBar().setUnitIncrement(5);
	jsp.setBorder(null);
	jsp.getViewport().setOpaque(false);
	jsp.setViewportBorder(null);
	JLabel head = new JLabel("Notificaciones");
	head.setFont(new Font("Helvetica", Font.BOLD, 20));
	head.setHorizontalAlignment(SwingConstants.CENTER);

	completo = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.weighty = 0;
	gbc.insets = new Insets(0, 10, 10, 10);
	completo.add(head, gbc);
	gbc.gridy = 1;
	gbc.weighty = 1;
	completo.add(jsp, gbc);

	this.setLayout(new BorderLayout());
	this.add(completo, BorderLayout.CENTER);
	completo.setOpaque(false);
	initalizeTimer();
    }

    /**
     * Inicializa el temporizador, estableciendo su intervalo <br>
     * y la acción que se ejecuta cada intervalo.
     */
    private void initalizeTimer() {
	notificationShow = new Timer(100, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (tindex >= notifs.size()) {
		    notificationShow.stop();

		} else {
		    notifs.get(tindex).setVisible(true);
		    tindex++;
		    jpa.setPreferredSize(null);
		    jpa.revalidate();
		    jsp.revalidate();
		    revalidate();
		    repaint();
		}
	    }
	});
    }

    /**
     * Establece los datos de las notificaciones
     * <p>
     * Todas las listas deben de tener la misma longitud
     * 
     * @param texts       Lista con los textos de las notificaciones
     * @param actions     Lista de las acciones de las notificaciones
     * @param seen        Lista de los boleanos que indican si la notificación ha
     *                    sido leída
     * @param discardable Lista de los boleanos que indican si la notificación puede
     *                    ser eliminada
     */
    public void setData(List<String> texts, List<List<String>> actions, List<Boolean> seen, List<Boolean> discardable) {

	List<Integer> l = new ArrayList<>();
	l.add(texts.size());
	l.add(actions.size());
	l.add(seen.size());
	l.add(discardable.size());

	Collections.reverse(texts);
	Collections.reverse(actions);
	Collections.reverse(seen);
	Collections.reverse(discardable);

	int m = Collections.min(l);
	int val = jsp.getVerticalScrollBar().getValue();
	notifs = new ArrayList<>();
	for (int i = 0; i < m; ++i) {
	    NotifComponent nf = new NotifComponent(i, texts.get(i), actions.get(i), seen.get(i), discardable.get(i));
	    notifs.add(nf);
	    nf.setControlador(controlador);
	}
	jpa.removeAll();
	for (NotifComponent nf : notifs) {
	    jpa.add(nf);
	    jpa.add(Box.createRigidArea(new Dimension(0, 10)));
	}
	jpa.add(Box.createGlue());
	jpa.setPreferredSize(null);

	revalidate();
	repaint();

	jsp.getVerticalScrollBar().setValue(val);

    }

    /**
     * Establece el controlador de las notificaciones
     * 
     * @param controlador Controlador de las notificiones
     */
    public void setControlador(ActionListener controlador) {
	this.controlador = controlador;
    }

    /**
     * Establece la visibilidad y inicia o finaliza la animación
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void setVisible(boolean aFlag) {

	if (aFlag) {
	    jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    tindex = 0;
	    notificationShow.start();
	} else {
	    notificationShow.stop();
	    jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    notifs.forEach(e -> e.setVisible(false));
	}

    }

    @Override
    public Dimension getMinimumSize() {
	return new Dimension(0, 0);
    }

    @Override
    public void componentResized(ComponentEvent e) {
	if (!notificationShow.isRunning()) {
	    if (jsp.getWidth() < getMinimumSize().width - 20) {
		jsp.setSize(new Dimension(getMinimumSize().width - 20, jsp.getHeight()));
	    }
	    int w = jsp.getWidth() - 20;
	    jpa.setPreferredSize(new Dimension(w, jpa.getPreferredSize().height));
	    jpa.revalidate();
	}
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

}
