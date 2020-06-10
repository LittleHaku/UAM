package magnitude.exceptions;

import units.IPhysicalUnit;

/**
 * Clase que hereda de QuantityException y se ha creado para poder lanzar una
 * excepcion cuando se intenta trabajar (transformaciones, sumas o restas) con
 * dos magnitudes o unidades de sistemas metricos diferentes, y no existe un
 * converter entre ellos. Por ejemplo, el internacional y el imperial.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class UnknownUnitException extends QuantityException {

	private static final long serialVersionUID = -32892L;

	/**
	 * Constructor de la excepcion, que guarda la cadena que se va a imprimir cuando
	 * las unidades no pertenezcan al mismo sistema metrico, y no existe un
	 * converter entre ellos.
	 * 
	 * @param u1 Una unidad
	 * @param u2 Otra unidad
	 */
	public UnknownUnitException(IPhysicalUnit u1, IPhysicalUnit u2) {
		super(u1, u2);
		setErr("Cannot transform " + u1.abbrev() + " " + u1.getQuantity() + " to " + u2.abbrev() + " "
				+ u2.getQuantity());
	}

}
