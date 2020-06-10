package units;

import magnitude.exceptions.*;
import metricSystems.IMetricSystem;

/**
 * Interfaz de Unidad, que define las funciones necesarias para definir una
 * unidad.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public interface IPhysicalUnit {
	/**
	 * 
	 * @param u unidad destino
	 * @return true si se puede transformar a la pasada por argumento (pertenecen al
	 *         mismo sistema metrico o existe un converter entre ellos), false en
	 *         otro caso.
	 */
	boolean canTransformTo(IPhysicalUnit u);

	/**
	 * 
	 * @param d cantidad a transformar
	 * @param u unidad destino
	 * @return la cantidad d pasada a la unidad u.
	 * @throws QuantityException cuando no se puede transformar.
	 */
	double transformTo(double d, IPhysicalUnit u) throws QuantityException;

	/**
	 * 
	 * @return el quantity de la unidad.
	 */
	Quantity getQuantity();

	/**
	 * 
	 * @return abreviatura de la unidad.
	 */
	String abbrev();

	/**
	 * 
	 * @return sistema metrico al que pertenece la unidad.
	 */
	IMetricSystem getMetricSystem();
}
