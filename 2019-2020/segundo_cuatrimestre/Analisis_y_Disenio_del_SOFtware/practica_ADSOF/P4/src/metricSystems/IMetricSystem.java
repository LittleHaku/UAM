package metricSystems;

import java.util.Collection;

import units.IPhysicalUnit;

/**
 * Interfaz de un sistema metrico. Contiene las funciones esenciales para
 * trabajar con un sistema metrico.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public interface IMetricSystem {

	/**
	 * 
	 * @return unidad base del sistema metrico
	 */
	IPhysicalUnit base();

	/**
	 * 
	 * @return unidades que forman el sistema metrico.
	 */
	Collection<IPhysicalUnit> units();

	/**
	 * Devuelve un converter que puede convertir magnitudes del sistema que llama a
	 * la funcion a magnitudes del pasado por parametro.
	 * 
	 * @param to Sistema metrico al que se podria convertir el que llama a la
	 *           funcion.
	 * @return Converter entre el sistema metrico que llama a la funcion y el del
	 *         parametro.
	 */
	IMetricSystemConverter getConverter(IMetricSystem to);
}
