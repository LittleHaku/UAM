package metricSystems.si.time;

/**
 * Esta clase modeliza la unidad segundo, que pertenece al sistema metrico
 * internacional de tiempo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Second extends SiTimeMetricSystem {

	/**
	 * Constructor de second.
	 * 
	 * @param tipoMedida String que indica que mide el segundo.
	 * @param value      entero que indica lo que vale el segundo respecto a la
	 *                   unidad base del sistema internacional de tiempo.
	 */
	public Second(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de segundo.
	 * 
	 */
	@Override
	public String abbrev() {
		return "s";
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
