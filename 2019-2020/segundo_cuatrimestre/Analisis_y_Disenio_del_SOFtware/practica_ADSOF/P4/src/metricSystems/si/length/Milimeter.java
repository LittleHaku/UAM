package metricSystems.si.length;

/**
 * Esta clase modeliza la unidad milimetro, que pertenece al sistema metrico
 * internacional de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Milimeter extends SiLengthMetricSystem {

	/**
	 * Constructor de milimeter.
	 * 
	 * @param tipoMedida String que indica que mide el milimetro.
	 * @param value      entero que indica lo que vale el milimetro respecto a la
	 *                   unidad base del sistema internacional de longitud.
	 */
	public Milimeter(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de milimetro.
	 */
	@Override
	public String abbrev() {
		return "mm";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un String con la abreviatura y el quantity.
	 */
	@Override
	public String toString() {
		return this.abbrev() + " " + getQuantity();
	}
}
