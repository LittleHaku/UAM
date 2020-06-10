package metricSystems.si.length.converters;

import magnitude.IMagnitude;
import magnitude.Magnitude;
import magnitude.exceptions.UnknownUnitException;
import metricSystems.IMetricSystem;
import metricSystems.IMetricSystemConverter;
import metricSystems.imperial.length.ImperialLengthMetricSystem;
import metricSystems.si.length.SiLengthMetricSystem;
import units.IPhysicalUnit;

/**
 * Esta clase modeliza el converter entre el sistema imperial y el internacional
 * de longitud. Se ha seguido el patron Singleton en esta clase, ya que solo hay
 * un objeto de este tipo. Esta clase tiene una variable factor, que hace el
 * cambio entre los sistemas metricos.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Imperial2SiLengthConverter implements IMetricSystemConverter {
	private final static double factor = 1.0 / 3.280839895;
	public final static Imperial2SiLengthConverter SYSTEM = new Imperial2SiLengthConverter();

	/**
	 * Constructor por defecto.
	 */
	public Imperial2SiLengthConverter() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve el sistema metrico fuente, en este caso el imperial de
	 *         longitud.
	 */
	@Override
	public IMetricSystem sourceSystem() {
		return ImperialLengthMetricSystem.SYSTEM;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve el sistema metrico destino, en este caso el internacional de
	 *         longitud.
	 */
	@Override
	public IMetricSystem targetSystem() {
		return SiLengthMetricSystem.SYSTEM;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la magnitud transformada a la unidad pasada por argumento.
	 *         Lanza la excepcion UnknownException cuando el sistema metrico de la
	 *         unidad pasada por argumento no es igual al del destino del converter.
	 * @param from magnitud a transformar.
	 * @param to   unidad a la que se quiere pasar la magnitud.
	 */
	@Override
	public IMagnitude transformTo(IMagnitude from, IPhysicalUnit to) throws UnknownUnitException {
		if (to.getMetricSystem().equals(targetSystem()) == false) {
			throw new UnknownUnitException(from.getUnit(), to);
		}
		double value = (from.getValue() * from.getUnit().getQuantity().getValue() * factor)
				/ (to.getQuantity().getValue());
		return new Magnitude(value, to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve el converter entre el sistema internacional de longitud y el
	 *         imperial de longitud.
	 */
	@Override
	public IMetricSystemConverter reverse() {
		return new SiLength2ImperialConverter();
	}
}
