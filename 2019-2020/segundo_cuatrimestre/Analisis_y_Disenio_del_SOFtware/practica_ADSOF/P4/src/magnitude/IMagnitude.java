package magnitude;

import magnitude.exceptions.QuantityException;
import units.IPhysicalUnit;

/**
 * Interfaz de Magnitud, que define las funciones necesarias para definir una
 * magnitud.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public interface IMagnitude {
	/**
	 * Suma la magnitud m con la que llama a la funcion.
	 * 
	 * @param m Magnitud con la que se va a sumar
	 * @return la magnitud suma.
	 * @throws QuantityException cuando no son del mismo sistema las magnitudes que
	 *                           se quieren sumar.
	 */
	IMagnitude add(IMagnitude m) throws QuantityException;

	/**
	 * Resta la magnitud m con la que llama a la funcion.
	 * 
	 * @param m magnitud con la que se va a restar.
	 * @return la magnitud resta.
	 * @throws QuantityException cuando no son del mismo sistema las magnitudes que
	 *                           se quieren restar.
	 */
	IMagnitude subs(IMagnitude m) throws QuantityException;

	/**
	 * Tranforma la magnitud a otra con la unidad pasada por argumento.
	 * 
	 * @param c Unidad a la que se quiere transformar la magnitud.
	 * @return la magnitud con la nueva unidad.
	 * @throws QuantityException cuando no son del mismo sistema las magnitudes.
	 */
	IMagnitude transformTo(IPhysicalUnit c) throws QuantityException;

	/**
	 * 
	 * @return Unidad de la magnitud.
	 */
	IPhysicalUnit getUnit();

	/**
	 * 
	 * @return valor de la magnitud.
	 */
	double getValue();
}
