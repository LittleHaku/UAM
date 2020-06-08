package componentes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * IconButton representa un botón que contiene una imagen
 * <p>
 * Cuando un IconButton se renderiza, solo se muestra la imagen, <br>
 * sin ningún tipo de fondo o borde. <br>
 * Al ser un botón, pueden desencadenarse acciones al hacer click sobre la
 * imagen <br>
 * tal y como ocurriría en un botón.
 * <p>
 * Asimismo, los IconButton se redimensionan para ocupar el espacio que el
 * Layout Manager les asigne. <br>
 * En relación al espacio que ocupan, pueden operar en tres modos, descritos en
 * la enumeración <code> FillMode </code>
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class IconButton extends JButton implements ComponentListener {
    private static final long serialVersionUID = 4937104013174354277L;
    /* Imágen del botón */
    private BufferedImage icon;

    /* Anchura y altura de la imagen que se está dibujando */
    private int h;
    private int w;

    /* Modo de relleno */
    private FillMode fMode = FillMode.BOTH;

    /**
     * Establece la política de relleno del IconButton
     * <p>
     * La política de relleno indicará cómo se redimensiona el botón <br>
     * cuando el espacio adjudicado por el Layout Manager no encaja con <br>
     * las proporciones de la imágen
     * 
     * 
     * @author David del Val
     * @author Junco de las Heras
     * @author Jorge Fernandez
     *
     * @version 26-04-20
     *
     */
    public enum FillMode {
	/**
	 * El icono no superará ni la anchura ni la altura asignadas
	 */
	BOTH,
	/**
	 * El icono adoptará al altura asingada y la anchura se optendrá de las
	 * proporciones de la imagen
	 */
	HEIGHT,
	/**
	 * El icono adoptará al anchura asingada y la altura se optendrá de las
	 * proporciones de la imagen
	 */
	WIDTH;
    };

    /**
     * Crea un nuevo IconButton
     * 
     * @param image Ruta de la imagen que dibujará el IconButton
     */
    public IconButton(String image) {
	super();
	try {
	    icon = ImageIO.read(new File(image));
	} catch (IOException e) {
	    e.printStackTrace();

	}
	this.setOpaque(false);
	this.addComponentListener(this);
	setHorizontalAlignment(SwingConstants.LEFT);
    }

    /**
     * Establece la imágen en el IconButton
     * 
     * @param image Ruta de la imagen que dibujará el IconButton
     * 
     * @throws IOException Si no se puede acceder a la ruta especificada o no se
     *                     puede leer el archivo
     */
    public void setImage(String image) throws IOException {
	icon = ImageIO.read(new File(image));
    }

    @Override
    public void paint(Graphics g) {
	if (h == 0 && w == 0) calculateHW();
	if (icon == null) return;
	Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

	if (getHorizontalAlignment() == SwingConstants.CENTER)
	    g2d.drawImage(icon, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h, null);
	else g2d.drawImage(icon, 0, 0, w, h, null);

    }

    /**
     * Realiza los cálculos necesarios para establecer las dimensiones de la imagen
     * <p>
     * El objetivo de este método es que la proporción de la imagen original siempre
     * sea mantenida <br>
     * Este método debe ser llamado cuando se redimensiona el componente, ya que las
     * dimensiones tendrán que recalcularse <br>
     * 
     */
    private void calculateHW() {
	int bh = icon.getHeight();
	int bw = icon.getWidth();
	h = getHeight();
	w = (int) (bw * (h / (double) bh));
	if (w > this.getWidth()) {
	    w = this.getWidth();
	    h = (int) (bh * (w / (double) bw));
	}
	if (fMode == FillMode.HEIGHT) {
	    h = getHeight();
	    w = (int) (bw * (h / (double) bh));
	} else if (fMode == FillMode.WIDTH) {
	    w = getWidth();
	    h = (int) (bh * (w / (double) bw));
	}

	setPreferredSize(new Dimension(h, w));
    }

    /**
     * Devuelve la altura de la imagen que se está dibujando
     * <p>
     * Hay que tener en cuenta que, dependiendo de la política de relleno, <br>
     * este valor puede ser distinto a la altura del botón
     * 
     * @return Altura de la imagen
     */
    public int getH() {
	return h;
    }

    /**
     * Devuelve la anchura de la imagen que se está dibujando
     * <p>
     * Hay que tener en cuenta que, dependiendo de la política de relleno, <br>
     * este valor puede ser distinto a la anchura del botón
     * 
     * @return Anchura de la imagen
     */
    public int getW() {
	return w;
    }

    /**
     * Establece la política de relleno
     * 
     * @param f Nueva política de relleno
     */
    public void setFillMode(FillMode f) {
	fMode = f;
    }

    @Override
    public void componentResized(ComponentEvent e) {
	calculateHW();
	revalidate();
	repaint();
    }

    @Override
    public void componentShown(ComponentEvent e) {
	componentResized(e);

    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
