package metricSystems.si.length;

/**
 * Esta clase modeliza la unidad kilometro, que pertenece al sistema metrico
 * internacional de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Kilometer extends SiLengthMetricSystem {

	/**
	 * Constructor de kilometer.
	 * 
	 * @param tipoMedida String que indica que mide el kilometro.
	 * @param value      entero que indica lo que vale el kilometro respecto a la
	 *                   unidad base del sistema internacional de longitud.
	 */
	public Kilometer(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de kilometro.
	 * 
	 */
	@Override
	public String abbrev() {
		return "km";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un String con la abreviatura y el Quantity
	 */
	@Override
	public String toString() {
		return this.abbrev() + " " + getQuantity();
	}
}
