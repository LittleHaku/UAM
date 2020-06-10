package metricSystems.imperial.length;

/**
 * Esta clase modeliza la unidad milla, que pertenece al sistema metrico
 * imperial de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Mile extends ImperialLengthMetricSystem {
	/**
	 * Constructor de mile.
	 * 
	 * @param tipoMedida String que indica que mide la milla (mile).
	 * @param value      entero que indica lo que vale la milla respecto a la unidad
	 *                   base del sistema imperial de longitud.
	 */
	public Mile(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de milla.
	 */
	@Override
	public String abbrev() {
		return "ml";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un String con la abreviatura de milla y el Quantity.
	 */
	@Override
	public String toString() {
		return this.abbrev() + " " + getQuantity();
	}
}
