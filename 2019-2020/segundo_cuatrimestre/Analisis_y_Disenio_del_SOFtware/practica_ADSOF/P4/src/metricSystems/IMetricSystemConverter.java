package metricSystems;

import magnitude.IMagnitude;
import magnitude.exceptions.UnknownUnitException;
import metricSystems.IMetricSystem;
import units.IPhysicalUnit;

/**
 * Interfaz que modeliza un converter entre dos sistemas metricos.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public interface IMetricSystemConverter {
	/**
	 * 
	 * @return sistema metrico origen.
	 */
	IMetricSystem sourceSystem();

	/**
	 * 
	 * @return sistema metrico destino.
	 */
	IMetricSystem targetSystem();

	/**
	 * Transforma la megnitud pasada por argumento a otra con la unidad pasada por
	 * argumento.
	 * 
	 * @param from magnitud a transformar.
	 * @param to   unidad a la que se va a pasar la magnitud pasada por argumento
	 *             "from".
	 * @return magnitud tranformada con la unidad pasada por argumento "to".
	 * @throws UnknownUnitException cuando los sistemas metricos de la magnitud y de
	 *                              la unidad son distintos y no existe un converter
	 *                              entre ellos.
	 */
	IMagnitude transformTo(IMagnitude from, IPhysicalUnit to) throws UnknownUnitException;

	/**
	 * 
	 * @return converter entre el sistema metrico destino y el source.
	 */
	IMetricSystemConverter reverse();
}
