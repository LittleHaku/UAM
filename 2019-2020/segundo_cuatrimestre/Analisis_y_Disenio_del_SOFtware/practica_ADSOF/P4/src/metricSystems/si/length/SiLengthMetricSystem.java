package metricSystems.si.length;

import java.util.*;
import magnitude.exceptions.QuantityException;
import metricSystems.AbstractMetricSystem;
import metricSystems.IMetricSystem;
import metricSystems.IMetricSystemConverter;
import metricSystems.imperial.length.ImperialLengthMetricSystem;
import metricSystems.si.length.converters.SiLength2ImperialConverter;
import units.*;

/**
 * Esta clase modeliza el sistema metrico internacional de longitud, es decir,
 * que mide longuitudes, y por eso el tipoMedida es "L". Ademas contiene tres
 * unidades Meter, Kilometer y Milimeter, tomando el metro como la unidad base
 * del sistema. Se ha seguido el patron Singleton en esta clase, ya que solo hay
 * un objeto de este tipo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class SiLengthMetricSystem extends AbstractMetricSystem {
	private final static String tipoMedida = "L";
	public final static SiLengthMetricSystem KILOMETER = new Kilometer(tipoMedida, 1000);
	public final static SiLengthMetricSystem METER = new Meter(tipoMedida, 1);
	public final static SiLengthMetricSystem MILIMETER = new Milimeter(tipoMedida, 0.001);
	public final static SiLengthMetricSystem SYSTEM = new SiLengthMetricSystem();

	/**
	 * Constructor por defecto.
	 */
	protected SiLengthMetricSystem() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve true si se puede transformar la unidad que llama a la
	 *         funcion en la unidad pasada por argumento. False en otro caso.
	 * @param u unidad destino.
	 */
	@Override
	public boolean canTransformTo(IPhysicalUnit u) {
		if ((u instanceof SiLengthMetricSystem) || (u.getMetricSystem().getConverter(this.getMetricSystem()) != null)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Transforma la cantidad con la unidad que llama a la funcion en
	 * la unidad pasada por argumento, siempre que se pueda transformar.
	 * 
	 * @param d cantidad a transformar.
	 * @param u unidad a la que se va a pasar la cantidad.
	 * @return cantidad en la unidad u pasada por argumento.
	 */
	@Override
	public double transformTo(double d, IPhysicalUnit u) throws QuantityException {
		if (canTransformTo(u) == false) {
			throw new QuantityException(u, this);
		}
		if (u instanceof ImperialLengthMetricSystem) {
			return ((d * getQuantity().getValue()) / u.getQuantity().getValue())
					* SiLength2ImperialConverter.getFactor();
		}
		return (d * getQuantity().getValue()) / u.getQuantity().getValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la unidad base del sistema, que en este caso es el metro.
	 */
	@Override
	public IPhysicalUnit base() {
		return METER;
	}

	/**
	 * {@inheritDoc} Crea una coleccion de unidades, y aniade las de este sistema:
	 * meter, kilometer y milimeter. Devuelve la coleccion.
	 * 
	 * @return Coleccion con las unidades del sistema internacional de longitud.
	 */
	@Override
	public Collection<IPhysicalUnit> units() {
		Collection<IPhysicalUnit> l = new ArrayList<>();
		l.add(KILOMETER);
		l.add(METER);
		l.add(MILIMETER);
		return l;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return No se devuelve nada, ya que se va a sobreescribir en las clases
	 *         hijas.
	 */
	@Override
	public String abbrev() {
		return null;
	}

	/**
	 * Aniade el converter pasado por argumento y su reverse, es decir, donde el
	 * sistema internacional de longitud es el destino.
	 * 
	 * @param sc converter a aniadir
	 */
	public static void registerConverter(IMetricSystemConverter sc) {
		if (sc.sourceSystem() instanceof SiLengthMetricSystem) {
			addConverter(sc);
			addConverter(sc.reverse());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Converter con sistema metrico fuente el internacional de longitud, y
	 *         destino el pasado por argumento.
	 * @param to Sistema metrico destino del converter que se quiere obtener.
	 * 
	 */
	@Override
	public IMetricSystemConverter getConverter(IMetricSystem to) {
		for (IMetricSystemConverter sc : getConverter()) {
			if (sc.targetSystem().base().equals(to.base()) == true) {
				return sc;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve el sistema, el imperial en este caso.
	 */
	@Override
	public IMetricSystem getMetricSystem() {
		return SYSTEM;
	}
}
