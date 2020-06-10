package magnitude.exceptions;

import units.*;

/**
 * Esta clase hereda de la clase Exception, y se ha creado para poder lanzar una
 * excepcion cuando se intenta trabajar (transformaciones, sumas o restas) con
 * dos magnitudes o unidades de quantities diferentes. Por ejemplo, una unidad
 * sea de tiempo y la otra de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class QuantityException extends Exception {
	private static final long serialVersionUID = -5215407219982368449L;
	private String err;

	/**
	 * Constructor de la excepcion, que guarda la cadena que se va a imprimir cuando
	 * las unidades no tengan el mismo quantity .
	 * 
	 * @param u1 Una unidad
	 * @param u2 Otra unidad
	 */
	public QuantityException(IPhysicalUnit u1, IPhysicalUnit u2) {
		super();
		setErr("Quantities " + u1.getQuantity() + " and " + u2.getQuantity() + " are not compatible");
	}

	/**
	 * @param _err guarda el mensaje de error
	 */
	public void setErr(String _err) {
		err = _err;
	}

	/**
	 * Imprime el mensaje de error.
	 */
	public String toString() {
		return err;
	}
}
