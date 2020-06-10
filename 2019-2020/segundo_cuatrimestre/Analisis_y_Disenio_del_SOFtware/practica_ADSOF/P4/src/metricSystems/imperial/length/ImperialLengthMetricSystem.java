package metricSystems.imperial.length;

import java.util.ArrayList;

import java.util.Collection;

import magnitude.exceptions.QuantityException;
import metricSystems.AbstractMetricSystem;
import metricSystems.IMetricSystem;
import metricSystems.IMetricSystemConverter;
import units.IPhysicalUnit;

/**
 * Esta clase modeliza el sistema metrico imperial de longuitud, es decir, que
 * mide longuitudes, y por eso el tipoMedida es "L". Ademas contiene tres
 * unidades Foot, Mile y Yard, tomando foot como la unidad base del sistema. Se
 * ha seguido el patron Singleton en esta clase, ya que solo hay un objeto de
 * este tipo.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class ImperialLengthMetricSystem extends AbstractMetricSystem {
	private final static String tipoMedida = "L";
	public final static ImperialLengthMetricSystem FOOT = new Foot(tipoMedida, 1);
	public final static ImperialLengthMetricSystem MILE = new Mile(tipoMedida, 5280);
	public final static ImperialLengthMetricSystem YARD = new Yard(tipoMedida, 3);
	public final static ImperialLengthMetricSystem SYSTEM = new ImperialLengthMetricSystem();

	/**
	 * Constructor por defecto
	 */
	protected ImperialLengthMetricSystem() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve true si se puede transformar la unidad que llama a la
	 *         funcion en la unidad pasada por argumento. False en otro caso.
	 * @param u cantidad en la unidad u.
	 */
	@Override
	public boolean canTransformTo(IPhysicalUnit u) {
		if ((u instanceof ImperialLengthMetricSystem)
				|| (u.getMetricSystem().getConverter(this.getMetricSystem()) != null)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Transforma la cantidad con la unidad que llama a la funcion en
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
	 * decir, donde el sistema imperial de longitudes el destino.
	 * 
	 * @param sc converter a aniadir.
	 */
	public static void registerConverter(IMetricSystemConverter sc) {
		if (sc.sourceSystem() instanceof ImperialLengthMetricSystem) {
			addConverter(sc);
			addConverter(sc.reverse());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Devuelve la unidad base del sistema, que en este caso es el pie.
	 */
	@Override
	public IPhysicalUnit base() {
		return FOOT;
	}

	/**
	 * {@inheritDoc} Crea una coleccion de unidades, y aniade las de este sistema:
	 * foot, mile y yard.
	 * 
	 * @return Coleccion con las unidades del sistema imperial de longitud.
	 */
	@Override
	public Collection<IPhysicalUnit> units() {
		Collection<IPhysicalUnit> l = new ArrayList<>();
		l.add(FOOT);
		l.add(MILE);
		l.add(YARD);
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
	 * {@inheritDoc}
	 * 
	 * @return Converter con sistema metrico fuente el imperial de longitud, y
	 *         destino el pasado por argumento.
	 * @param to Sistema metrico destino del converter que se quiere obtener.
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
