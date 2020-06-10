package metricSystems.si.length;

/**
 * Esta clase modeliza la unidad metro, que pertenece al sistema metrico
 * internacional de longitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Meter extends SiLengthMetricSystem {
	/**
	 * Constructor de meter.
	 * 
	 * @param tipoMedida String que indica que mide el metro.
	 * @param value      entero que indica lo que vale el metro respecto a la unidad
	 *                   base del sistema internacional de longitud.
	 */
	public Meter(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura del metro.
	 */
	@Override
	public String abbrev() {
		return "m";
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
