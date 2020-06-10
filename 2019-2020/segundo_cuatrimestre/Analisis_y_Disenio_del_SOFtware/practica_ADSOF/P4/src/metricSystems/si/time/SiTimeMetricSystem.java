package metricSystems.si.time;

import java.util.ArrayList;

import java.util.Collection;

import magnitude.exceptions.QuantityException;
import metricSystems.AbstractMetricSystem;
import metricSystems.IMetricSystem;
import metricSystems.IMetricSystemConverter;
import metricSystems.si.length.SiLengthMetricSystem;
import units.*;

/**
 * Esta clase modeliza el sistema metrico internacional de tiempo, es decir, que
 * mide longuitudes, y por eso el tipoMedida es "t". Ademas contiene tres
 * unidades second, milisecond y hour, tomando el segundo como la unidad base
 * del sistema. Se ha seguido el patron Singleton en esta clase, ya que solo hay
 * un objeto de este tipo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class SiTimeMetricSystem extends AbstractMetricSystem {
	private final static String tipoMedida = "t";
	public final static SiTimeMetricSystem SECOND = new Second(tipoMedida, 1);
	public final static SiTimeMetricSystem MILISECOND = new Milisecond(tipoMedida, 0.001);
	public final static SiTimeMetricSystem HOUR = new Hour(tipoMedida, 3600);
	public final static SiTimeMetricSystem SYSTEM = new SiTimeMetricSystem();

	/**
	 * Constructor por defecto.
	 */
	protected SiTimeMetricSystem() {
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
		if ((u instanceof SiTimeMetricSystem) == true
				|| (u.getMetricSystem().getConverter(this.getMetricSystem()) != null)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Transforma la cantidad d de la unidad que llama a la funcion en
	 * la unidad pasada por argumento, siempre que se pueda transformar.
	 * 
	 * @param d cantidad a transformar.
	 * @param u unidad a la que se quiere pasar la cantidad.
	 * @return cantidad en la unidad u.
	 */
	@Override
	public double transformTo(double d, IPhysicalUnit u) throws QuantityException {
		if (canTransformTo(u) == false) {
			throw new QuantityException(u, this);
		}
		return (d * getQuantity().getValue()) / u.getQuantity().getValue();
	}

	/**
	 * {@inheritDoc} Aniade el converter pasado por argumento y su reverse, es
	 * decir, donde el sistema internacional de tiempo es el destino.
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
	 * @return Devuelve la unidad base del sistema, que en este caso es el segundo.
	 */
	@Override
	public IPhysicalUnit base() {
		return SECOND;
	}

	/**
	 * {@inheritDoc} Crea una coleccion de unidades, y aniade las de este sistema:
	 * second, milisecond y hour. Devuelve la coleccion.
	 * 
	 * @return Coleccion con las unidades del sistema internacional de tiempo.
	 */
	@Override
	public Collection<IPhysicalUnit> units() {
		Collection<IPhysicalUnit> l = new ArrayList<>();
		l.add(HOUR);
		l.add(SECOND);
		l.add(MILISECOND);
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
	 * {@inheritDoc} Aniade el converter pasado por argumento y su reverse, es
	 * decir, donde el sistema internacional de tiempo es el destino.
	 * 
	 * @param to converter a aniadir
	 * @return Converter con sistema metrico fuente el internacional de tiempo, y
	 *         destino el pasado por argumento.
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
	 * @return Devuelve el sistema, en este caso el internacional de tiempo.
	 */
	@Override
	public IMetricSystem getMetricSystem() {
		return SYSTEM;
	}

}
