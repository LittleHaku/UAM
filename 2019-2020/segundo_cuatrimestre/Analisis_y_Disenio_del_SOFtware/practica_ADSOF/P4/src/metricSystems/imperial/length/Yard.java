package metricSystems.imperial.length;

/**
 * Esta clase modeliza la unidad yarda, que pertenece al sistema metrico
 * imperial de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Yard extends ImperialLengthMetricSystem {

	/**
	 * Constructor de yard.
	 * 
	 * @param tipoMedida String que indica que mide la yarda (yard).
	 * @param value      entero que indica lo que vale la yarda respecto a la unidad
	 *                   base del sistema imperial de longitud.
	 */
	public Yard(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de yarda.
	 */
	@Override
	public String abbrev() {
		return "yd";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un String con la abreviatura y el Quantity.
	 */
	@Override
	public String toString() {
		return this.abbrev() + " " + getQuantity();
	}
}
