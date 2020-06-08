package exceptions;

/**
 * Esta excepción se produce cuando un valor es demasiado largo
 * <p>
 * Por ejemplo, puede generse si un determinado atributo tiene un límite <br>
 * de longitud y este es excedido.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ValueTooLongException extends VisualException {
    private static final long serialVersionUID = -8866742011199603842L;

    /**
     * Crea una nueva excepción de longitud excedida
     * 
     * @param field  Campo o atributo cuya longitud se ha excedido
     * @param maxLen Longitud máxima de <code> field </code>
     */
    public ValueTooLongException(String field, int maxLen) {
	super(field + " demasiado largo", "Se ha excedido la longitud máxima del \"" + field.toLowerCase()
		+ "\" que es de " + maxLen + " caracteres");
    }
}
