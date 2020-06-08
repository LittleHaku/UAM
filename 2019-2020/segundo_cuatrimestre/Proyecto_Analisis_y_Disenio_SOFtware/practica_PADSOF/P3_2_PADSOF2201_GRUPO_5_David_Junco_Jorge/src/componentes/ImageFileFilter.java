package componentes;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * La clase <code>ImageFileFilter</code> filtra las imágenes de entre los
 * elementos de un {@link JFileChooser}
 * <p>
 * Esta clase se utiliza para limitar los tipos de archivos que se pueden
 * aceptar en un {@link JFileChooser}. <br>
 * En concreto, en este caso, estamos aceptando solo las imágenes que podemos
 * utilizar en los proyectos de infraestructura
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ImageFileFilter extends FileFilter {
    /* Lista de formatos aceptados */
    private final List<String> imageExtensions = Arrays.asList(".jpeg", ".jpg", ".png");

    @Override
    public boolean accept(File f) {
	if (f.isDirectory()) {
	    return true;
	}
	return imageExtensions.stream().anyMatch(v -> f.getName().toLowerCase().endsWith(v));

    }

    @Override
    public String getDescription() {
	return "Image files";
    }

}