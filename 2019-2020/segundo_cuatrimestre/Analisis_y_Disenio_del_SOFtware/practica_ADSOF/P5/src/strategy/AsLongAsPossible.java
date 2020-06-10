package strategy;

/**
 * Esta clase representa la estrategia de ejecutar la regla siempre y cuando la
 * ejecucion anterior haya producido algun cambio
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> es el tipo generico de Strategy
 */
public class AsLongAsPossible<T> implements Strategy<T> {

	/**
	 * es el constructor
	 */
	public AsLongAsPossible() {
	}

	/**
	 * {@inheritDoc} devuelve true siempre que lastExecution sea true
	 */
	@Override
	public boolean executeMore(boolean lastExecution) {
		return lastExecution == true;
	}
}
