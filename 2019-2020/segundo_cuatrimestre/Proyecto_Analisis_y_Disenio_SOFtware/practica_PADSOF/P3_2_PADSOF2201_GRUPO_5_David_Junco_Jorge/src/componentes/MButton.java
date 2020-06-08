package componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

/**
 * MButton es un botón con algunas ligeras modificaciones.
 * <p>
 * Principalmente, se modifican los siguientes aspectos del diseño de un botón
 * estándard:
 * <ul>
 * <li>El botón adquiere una estética rectangular, sin border redondeados
 * <li>El borde del botón se ilumina cuando el ratón pasa sobre él
 * <li>Se puede ajustar la altura adicional del botón
 * <li>Se puede definir una versión más corta del texto del botón que sustituye
 * a la normal si esta no cabe
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class MButton extends JButton implements MouseListener, KeyListener, ActionListener {
    private static final long serialVersionUID = 2385496626135916081L;

    /* Color de fondo del botón */
    private static final Color BACK_COLOR = new Color(0xCCCCCC);

    /* Color del texto del botón */
    private static final Color FONT_COLOR = new Color(0x000000);

    /* Color de fondo cuando el botón está presionado */
    private static final Color SELECTED_BACK_COLOR = new Color(0xAAAAAA);

    /* Color de fondo cuando el botón tiene el foco */
    private static final Color FOCUS_BACK_COLOR = new Color(0xBBBBBB);

    /* Color del borde cuando el ratón pasa por el botón */
    private static final Color BORDER_COLOR = new Color(0x3297FD);

    /* Grosor del border */
    private static final int BORDER_WIDTH = 1;

    /* Estado del ratón */
    private boolean mouseHovering = false;
    private boolean mouseClicking = false;

    /* Altura extra */
    private int extraHeight = 2;

    /* Texto a dibujar si el normal es demasiado largo */
    private String shorthand = null;

    /**
     * Crea un nuevo botón
     * 
     * @param tit Texto que aparece en el botón en condiciones normales
     * @param sh  Texto que aparece si <code> tit </code> no cabe
     */
    public MButton(String tit, String sh) {
	super(tit);
	shorthand = sh;
	setForeground(FONT_COLOR);
	setBackground(BACK_COLOR);

	setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + extraHeight));
	addMouseListener(this);
	this.setFont(new Font("Arial", Font.PLAIN, 13));
	this.addKeyListener(this);
	this.addActionListener(this);
	this.setOpaque(false);
    }

    /**
     * Crea un nuevo botón
     * 
     * @param tit Texto que aparece en el botón
     */
    public MButton(String tit) {
	this(tit, null);
    }

    /**
     * Establece la altura adicional del botón
     * 
     * @param siz Altura adicional
     */
    public void setHeightAdjust(int siz) {

	extraHeight = siz;
	revalidate();
	repaint();
    }

    @Override
    public Dimension getPreferredSize() {
	Dimension d = super.getPreferredSize();
	return new Dimension(d.width, d.height + extraHeight);

    }

    @Override
    public Dimension getMinimumSize() {
	Dimension d = super.getMinimumSize();
	return new Dimension(d.width, d.height + extraHeight);
    }

    @Override
    public void paint(Graphics g) {
	int wid = getWidth();
	int hei = getHeight();

	g.setFont(this.getFont());
	FontMetrics fm = g.getFontMetrics();
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	RoundRectangle2D rR = new RoundRectangle2D.Float(BORDER_WIDTH, BORDER_WIDTH, (wid - 2 * BORDER_WIDTH),
		hei - 2 * BORDER_WIDTH, 0, 0);

	g2d.setColor(getBackground());
	if (!this.isEnabled()) {
	    g2d.setColor(new Color(200, 200, 200, 100));
	}
	if (this.isEnabled() && hasFocus()) {
	    g2d.setColor(FOCUS_BACK_COLOR);
	}

	if (mouseClicking) {
	    g2d.setColor(SELECTED_BACK_COLOR);
	}

	g2d.fill(rR);

	if (this.isEnabled() && (mouseHovering)) {
	    g2d.setColor(BORDER_COLOR);
	    g2d.setStroke(new BasicStroke(BORDER_WIDTH));
	    g2d.draw(rR);
	}

	g2d.setColor(getForeground());

	if (!this.isEnabled()) {
	    g2d.setColor(new Color(100, 100, 100, 100));
	}
	String txt = this.getText();

	g2d.setFont(this.getFont());
	if (fm.stringWidth(txt) > wid - 20) {
	    if (shorthand != null) {
		txt = shorthand;
	    }
	    if (fm.stringWidth(txt) > wid - 20) {
		String s2 = "";
		int i = 0;
		while (fm.stringWidth(s2) < wid - 20 - fm.stringWidth("...")) {
		    s2 += txt.charAt(i);
		    i++;
		}
		s2 += "...";
		txt = s2;
	    }
	}
	g2d.drawString(txt, wid / 2 - fm.stringWidth(txt) / 2, hei / 2 + fm.getAscent() / 2);
    }

    @Override
    public void mouseExited(MouseEvent e) {
	mouseHovering = false;
	repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	mouseHovering = true;
	repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	mouseClicking = false;
	repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
	mouseClicking = true;
	repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	    this.doClick();
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	mouseClicking = false;
	mouseHovering = false;
	repaint();
    }

}
