package exceptions;

/**
 * Esta excepción se produce cuando encontramos un error inesperado
 * <p>
 * Se utiliza en casos en los que el hecho de que este error se produzca <br>
 * indica una inconsistencia en el modelo de datos
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class InternalErrorException extends VisualException {
    private static final long serialVersionUID = 1L;

    /**
     * Crea un nueva excepción de error interno
     * 
     * @param reason Motivo por el que se ha producido la excepción
     */
    public InternalErrorException(String reason) {
	super("Error interno", reason);
    }
}
