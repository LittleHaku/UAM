package exceptions;

/**
 * Excepción que se produce cuando un campo tiene datos de distinto tipo a los
 * que se esperan
 * <p>
 * Por ejemplo, puede producirse si se introduce texto en un campo que espera
 * números
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class FormatException extends VisualException {
    private static final long serialVersionUID = 7832275715670344079L;

    /**
     * Crea una nueva excepción de formato
     * 
     * @param campo Campo donde ha ocurrido el problema
     * @param tipo  Tipo de dato que se debería haber introducido
     */
    public FormatException(String campo, String tipo) {
	super("Error en el formato del campo " + campo, "Debe introducir un numero de tipo " + tipo);
    }
}
