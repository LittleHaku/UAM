package exceptions;

/**
 * Excepción que se produce cuando un campo está vacío.
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class EmptyFieldException extends VisualException {
    private static final long serialVersionUID = 9196469108015714801L;

    /**
     * Crea una nueva excepción de campo vacío
     * <p>
     * Esta excepción indicará que el campo <code>campo</code> debería contener
     * <code>content</code>
     * 
     * @param campo   Campo que está vacío
     * @param content Contenido que debería haber en el campo
     */
    public EmptyFieldException(String campo, String content) {
	super("El campo " + campo + " está vacío", "Debe indicar el " + content + " en el campo " + campo);
    }

    public EmptyFieldException(String campo) {
	this(campo, campo);
    }
}
