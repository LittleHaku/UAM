package componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel que contiene una imagen.
 * <p>
 * La imagen se dibujará siempre en el centro del panel, tomando las mayores
 * dimensiones posibles. <br>
 * Sin embargo, a diferencia del <code>IconButton</code>, no modifica el tamaño
 * asignado por el Layout Manager. <br>
 * Por el contrario, se limita a dibujar la imagen centrada y lo más grande
 * posible
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
public class ImagePanel extends JPanel {
    private static final long serialVersionUID = -5032728808653201687L;

    /* Imagen del panel */
    private BufferedImage icon;
    /* Altura y anchura de la imagen que se dibuja */
    private int h;
    private int w;

    /**
     * Crea un nuevo panel de imagen
     * 
     * @param image Ruta de la imagen que se carga en el panel
     */
    public ImagePanel(String image) {
	this.setOpaque(false);
	try {
	    icon = ImageIO.read(new File(image));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Establece la imagen que se dibuja en el panel
     * 
     * @param image Ruta de la imagen que se carga en el panel
     * @throws IOException Si no se ha podido encontrar o leer el archivo
     */
    public void setImage(String image) throws IOException {
	icon = ImageIO.read(new File(image));
    }

    /**
     * Dibuja la imagen en el panel
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {
	int bh = icon.getHeight();
	int bw = icon.getWidth();
	int marg = 2;
	h = getHeight() - marg;
	w = (int) (bw * (h / (double) bh));
	if (w > this.getWidth() - marg) {
	    w = this.getWidth() - marg;
	    h = (int) (bh * (w / (double) bw));
	}
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	Shape s = g2d.getClip();
	Shape border = new RoundRectangle2D.Double((getWidth() - w) / 2, (getHeight() - h) / 2, w, h, 10, 10);
	g2d.setClip(border);
	g2d.drawImage(icon, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
	g2d.setClip(s);
	g2d.setStroke(new BasicStroke(1));
	g2d.setColor(Color.gray);
	g2d.draw(border);

    }

}
