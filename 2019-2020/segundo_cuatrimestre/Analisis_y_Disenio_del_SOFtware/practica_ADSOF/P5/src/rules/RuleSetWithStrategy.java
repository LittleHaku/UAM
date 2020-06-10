package rules;

import strategy.Strategy;

/**
 * 
 * Clase que hereda de RuleSet y que ejecuta process() tantas veces como lo
 * indique la estrategia a seguir
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> es el parametro generico de RuleSet
 */
public class RuleSetWithStrategy<T> extends RuleSet<T> {
	private final Strategy<T> estrategia;

	/**
	 * es el constructor
	 * 
	 * @param estrategia el tipo de estrategia a seguir
	 */
	public RuleSetWithStrategy(Strategy<T> estrategia) {
		super();
		this.estrategia = estrategia;
	}

	/**
	 * {@inheritDoc} ejecuta el process() de RuleSet tantas veces como lo indique la
	 * estrategia a seguir
	 */
	@Override
	public boolean process() {
		while (estrategia.executeMore(super.process()))
			;
		return true;
	}
}
