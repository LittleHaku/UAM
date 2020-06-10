package strategy;

/**
 * Esta clase representa la estrategia de ejecutar la regla solo 1 unica vez
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> es el tipo generico de Strategy
 */
public class Sequence<T> implements Strategy<T> {
	/**
	 * es el constructor por defecto
	 */
	public Sequence() {
	}

	/**
	 * {@inheritDoc} devuelve siempre false
	 */
	@Override
	public boolean executeMore(boolean lastExecution) {
		return false;
	}
}
