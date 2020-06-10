package units;

/**
 * Esta clase modeliza quantity, que tiene los atributos: value y tipoMedida.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Quantity {
	private String tipoMedida;
	private double value;

	/**
	 * Constructor de Quantity
	 * 
	 * @param _tipoMedida lo que va a medir la unidad (tiempo, longitud..).
	 * @param _value      el valor de la unidad en la base del sistema metrico al
	 *                    que pertenezca.
	 */
	public Quantity(String _tipoMedida, double _value) {
		tipoMedida = _tipoMedida;
		value = _value;
	}

	/**
	 * 
	 * @return valor de quantity
	 */
	public double getValue() {
		return value;
	}

	/**
	 * 
	 * @return tipoMedida
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve un string indicando el tipoMedida
	 */
	@Override
	public String toString() {
		return tipoMedida;
	}
}
