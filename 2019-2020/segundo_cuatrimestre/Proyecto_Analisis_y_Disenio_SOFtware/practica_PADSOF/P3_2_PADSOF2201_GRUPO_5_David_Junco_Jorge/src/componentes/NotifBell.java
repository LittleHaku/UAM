package componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;

/**
 * NotifBell es la campana de notificaciones que aparece en el panel de control
 * <p>
 * Se trata de un botón que dibuja un icono de una campana junto con un
 * número.<br>
 * Este número puede indicar el número de notificaciones sin leer que hay en el
 * panel de notificaciones <br>
 * Además, por ser un botón, puede tener una acción asociada a él.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class NotifBell extends IconButton implements ComponentListener {
    private static final long serialVersionUID = -4544576936655643747L;

    /* Color del círculo sobre el que se dibuja el número */
    private static final Color CIRCLE_COLOR = new Color(250, 10, 10, 200);

    /* Número a dibujar */
    private int numero;

    /**
     * Crea un nuevo icono de campana de notificaciones
     * 
     * @param numero El número que aparecerá en el icono
     */
    public NotifBell(int numero) {
	super("icons/bell_s.png");
	this.numero = numero;
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

	int w = super.getW();

	Ellipse2D e = new Ellipse2D.Double(2, 2, w / 2.1, w / 2.1);
	g2d.setColor(CIRCLE_COLOR);
	g2d.fill(e);

	g2d.setColor(Color.white);
	Font cf = new Font("Arial Black", Font.BOLD, (int) (e.getHeight() * (2 / 3.0)));
	g2d.setFont(cf);
	FontMetrics fm = g2d.getFontMetrics();
	String target = Integer.toString(numero);
	if (numero > 99) target = "+";
	int py = (int) (e.getHeight() / 2.0) + fm.getAscent() / 2;
	int px = (int) (e.getHeight() / 2.0) - fm.stringWidth(target) / 2;
	g2d.drawString(target, px + 2, py);

    }

    /**
     * Establece el número que se dibuja sobre el icono de la campana
     * 
     * @param numero Numero a dibujar.
     */
    public void setNumero(int numero) {
	this.numero = numero;
    }
}