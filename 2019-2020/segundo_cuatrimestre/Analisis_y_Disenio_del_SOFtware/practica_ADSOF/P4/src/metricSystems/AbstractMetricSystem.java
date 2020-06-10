package metricSystems;

import java.util.ArrayList;
import java.util.Collection;

import units.IPhysicalUnit;
import units.Quantity;

/**
 * Clase abstracta que contiene lo comun a todos los sistemas metricos.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public abstract class AbstractMetricSystem implements IPhysicalUnit, IMetricSystem {
	private Quantity q;
	private static Collection<IMetricSystemConverter> converter = new ArrayList<IMetricSystemConverter>();

	/**
	 * Asigna la quantity al sistema.
	 * 
	 * @param tipoMedida String con la identificacion de la quantity.
	 * @param value      valor de la unidad respecto a la base.
	 */
	public void setQuantity(String tipoMedida, double value) {
		this.q = new Quantity(tipoMedida, value);
	}

	/**
	 * aniade un elemento a converter
	 * 
	 * @param m el elemento a aniadir
	 */
	public static void addConverter(IMetricSystemConverter m) {
		converter.add(m);
	}

	/**
	 * es el getter de converter
	 * 
	 * @return converter
	 */
	public Collection<IMetricSystemConverter> getConverter() {
		return converter;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve el quantity
	 */
	@Override
	public Quantity getQuantity() {
		return q;
	}

}
