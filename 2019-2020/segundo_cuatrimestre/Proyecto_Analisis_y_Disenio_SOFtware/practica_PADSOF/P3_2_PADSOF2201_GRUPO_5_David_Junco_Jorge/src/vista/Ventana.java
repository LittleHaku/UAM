package vista;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import componentes.ComponentConstants;
import componentes.VistaDialog;
import controlador.Controlador;
import controlador.ControladorPrincipal;

/**
 * Ventana principal de la aplicación
 * <p>
 * Esta ventana contiene todas las vistas y las puede mostrar de dos formas
 * distintas:
 * <ul>
 * <li>Haciendo que la vista sea el panel del <code> JFrame </code>
 * <li>Crendo un {@link VistaDialog} para que este muestre la vista
 * </ul>
 * 
 * En ambos casos, es la ventana la que guarda las vistas.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class Ventana extends JFrame implements ComponentListener {
    private static final long serialVersionUID = -6047474634910141274L;

    /* Controlador de la ventana */
    private ControladorPrincipal control;
    /* Vistas de la ventana */
    private Map<String, Vista> vistas;

    /* Siguiente panel a mostrar si hay alguna pendiente */
    private String nextVista;

    /* Dialogo para mostrar vistas */
    private VistaDialog jd;

    /**
     * Crea una nueva ventana con todas las vistas inicializadas
     * <p>
     * Todas las vistas de esta ventana serán vistas nuevas, que aún no <br>
     * tienen el controlador asignado. Este se asignará en {@link #setControlador}
     */
    public Ventana() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.addComponentListener(this);
	this.setLayout(new FlowLayout());
	vistas = new HashMap<>();

	addVista(new VistaInicial());
	addVista(new VistaIniciarSesion());
	addVista(new VistaRegistroSesion());
	addVista(new VistaCrearColectivo());
	addVista(new VistaApuntarseColectivo());
	addVista(new VistaPanelDeControl());
	addVista(new VistaBuscarProyecto());
	addVista(new VistaPanelAdministrador());
	addVista(new VistaCrearProyecto());
	addVista(new VistaElegirColectivo());
	addVista(new VistaInfoProyecto());
	addVista(new VistaInformeAfinidad());

	this.showVista("inicial");

	try {
	    this.setIconImage(ImageIO.read(Paths.get(ComponentConstants.I_LOGO).toFile()));
	} catch (IOException e) {
	    System.err.println(e);
	}

    }

    /*
     * Añade una vista al mapa de vistas
     */
    private void addVista(Vista v) {
	vistas.put(v.getId(), v);
    }

    /**
     * Establece el controlador de esta ventana
     * <p>
     * Asimismo, se enlazan todas las vistas con sus respectivos controladores
     * 
     * @param controlador Controlador de la ventana
     */
    public void setControlador(ControladorPrincipal controlador) {
	this.control = controlador;
	this.addWindowListener(controlador);

	for (String key : vistas.keySet()) {
	    vistas.get(key).setControlador(control.getControlador(key));
	}

    }

    /**
     * Devuelve la vista identificada con este id
     * 
     * @param id Id de la vista buscada
     * @return Vista que responde a este id
     */
    public Vista getVista(String id) {
	return vistas.get(id);
    }

    /**
     * Devuelve el controlador de la ventana
     * 
     * @return El controlador de la ventana
     */
    public ControladorPrincipal getControl() {
	return control;
    }

    /**
     * Muestra una vista en un diálogo
     * 
     * @param carta Vista a mostrar
     */
    public void showVistaDialog(String carta) {
	Vista v = vistas.get(carta);
	jd = new VistaDialog(v, this);
	jd.setLocationRelativeTo(this);
	jd.setVisible(true);

	v.repaint();
	v.validate();
	v.madeVisible();
	jd.revalidate();
	jd.repaint();
    }

    /**
     * Devuelve el valor de la VistaDialog
     * 
     * @return jd: VistaDialog
     */
    public VistaDialog getVistaDialog() {
	return jd;
    }

    /**
     * Muestra una vista en esta ventana
     * <p>
     * Si el tamaño de la ventana ha de cambiar para acomodarse a la nueva vista,
     * <br>
     * El cambio de panel se producirá cuando el cambio de tamaño ya se haya
     * producido, <br>
     * a fin de que la vista a mostrar aparezca en una ventana con las dimensiones
     * esperadas. <br>
     * 
     * @param carta Identificador de la vista a mostrar
     */
    public void showVista(String carta) {
	if (control != null) {
	    Controlador c = control.getControlador(carta);
	    if (c != null) c.initialise();
	}

	Vista v = vistas.get(carta);
	this.setResizable(v.isResizeable());
	Dimension d = this.getSize();
	if (this.getSize().equals(v.getPredefSize())) {
	    this.setContentPane(v);
	    vistas.get(carta).madeVisible();

	} else {
	    this.setSize(v.getPredefSize());

	    this.setPreferredSize(v.getPredefSize());
	    if (this.isShowing()) {
		Point p = this.getLocation();
		p.x += (d.width - v.getPredefSize().width) / 2;
		p.y += (d.height - v.getPredefSize().height) / 2;
		this.setLocation(p);
	    } else {
		this.setLocationRelativeTo(null);
	    }
	    nextVista = carta;
	}

    }

    @Override
    public void componentResized(ComponentEvent e) {
	/*
	 * Si hay alguna vista pendiente, la mostrarmos ahora ya que la ventana ya ha
	 * cambiado de tamaño
	 */
	if (nextVista != null) {
	    Vista v = vistas.get(nextVista);
	    this.setContentPane(v);
	    this.pack();
	    this.repaint();
	    v.madeVisible();
	}
	nextVista = null;

    }

    @Override
    public void componentMoved(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void requestFocus() {
	if (jd != null && jd.isVisible()) {
	    jd.requestFocus();
	} else {
	    super.requestFocus();
	}
    }
}
