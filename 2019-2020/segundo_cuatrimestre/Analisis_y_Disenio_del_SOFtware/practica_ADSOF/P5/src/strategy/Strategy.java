package strategy;

/**
 * Esta interfaz es una interfaz funcional en la que el metodo que se tiene que
 * implementar es executeMore, la estrategia a seguir
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> es el parametro generico de Strategy
 */
@FunctionalInterface
public interface Strategy<T> {
	/**
	 * 
	 * @param lastExecution es el resultado de la ultima ejecucion, true si ha
	 *                      habido algun cambio, false sino.
	 * @return true si tiene que haber una proxima ejecucion, false sino.
	 */
	public boolean executeMore(boolean lastExecution);
}
