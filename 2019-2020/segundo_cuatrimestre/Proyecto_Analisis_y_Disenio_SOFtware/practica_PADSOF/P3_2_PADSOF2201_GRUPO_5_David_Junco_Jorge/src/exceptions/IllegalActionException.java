package exceptions;

/**
 * Excepción que se produce cuando se realiza un acción no permitida
 * <p>
 * La excepción incluye dos campos, el problema que se ha causado y una solución
 * al mismo
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class IllegalActionException extends VisualException {
    private static final long serialVersionUID = 5669578829371436459L;

    public IllegalActionException(String problema, String solucion) {
	super(problema, solucion);
    }
}
