package metricSystems.imperial.length;

/**
 * Esta clase modeliza la unidad pie, que pertenece al sistema metrico imperial
 * de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Foot extends ImperialLengthMetricSystem {

	/**
	 * Constructor de foot.
	 * 
	 * @param tipoMedida String que indica que mide el pie (foot).
	 * @param value      entero que indica lo que vale el pie respecto a la unidad
	 *                   base del sistema imperial de longitud.
	 */
	public Foot(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de foot.
	 */
	@Override
	public String abbrev() {
		return "ft";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un String indicando la abreviatura de foot y el Quantity.
	 */
	@Override
	public String toString() {
		return this.abbrev() + " " + getQuantity();
	}
}
