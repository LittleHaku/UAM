package metricSystems.si.time;

/**
 * Esta clase modeliza la unidad hora, que pertenece al sistema metrico
 * internacional de tiempo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Hour extends SiTimeMetricSystem {
	/**
	 * Constructor de hour.
	 * 
	 * @param tipoMedida String que indica que mide la hora.
	 * @param value      entero que indica lo que vale la hora respecto a la unidad
	 *                   base del sistema internacional de tiempo.
	 */
	public Hour(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de hora.
	 * 
	 */
	@Override
	public String abbrev() {
		return "h";
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
