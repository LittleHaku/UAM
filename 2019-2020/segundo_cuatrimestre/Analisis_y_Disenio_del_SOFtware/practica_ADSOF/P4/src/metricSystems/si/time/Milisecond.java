package metricSystems.si.time;

/**
 * Esta clase modeliza la unidad milisegundo, que pertenece al sistema metrico
 * internacional de tiempo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Milisecond extends SiTimeMetricSystem {

	/**
	 * Constructor de milisecond.
	 * 
	 * @param tipoMedida String que indica que mide el milisegundo.
	 * @param value      entero que indica lo que vale el milisegundo respecto a la
	 *                   unidad base del sistema internacional de tiempo.
	 */
	public Milisecond(String tipoMedida, double value) {
		super();
		setQuantity(tipoMedida, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la abreviatura de milisegundo.
	 * 
	 */
	@Override
	public String abbrev() {
		return "ms";
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
