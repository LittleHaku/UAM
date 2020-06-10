package rules;

import java.util.Observable;
import java.util.Observer;

/**
 * Es la clase de reglas de tipo Trigger, implementa el Observador
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> es el tipo generico de variable que se puede Observar
 */
public class TriggeredRule<T extends Observable> extends Rule<T> implements Observer {
	private T variable;
	@SuppressWarnings("unused")
	private String method;

	/**
	 * Es el constructor
	 * 
	 * @param name nombre de la regla
	 */
	public TriggeredRule(String name) {
		super(name, "es una TriggeredRule");
	}

	/**
	 * Crea una regla de tipo Trigger
	 * 
	 * @param <T>  es el tipo generico de variable que se puede Observar
	 * @param name nombre de la regla
	 * @return una nueva instancia de esta clase, para poder seguir invocando
	 *         metodos
	 */
	static public <T extends Observable> TriggeredRule<T> trigRule(String name) {
		return new TriggeredRule<T>(name);
	}

	/**
	 * Añade este observador al observable variable
	 * 
	 * @param variable es la variable que va a ser Observada
	 * @param method   el nombre del metodo que esta observando
	 * @return this, para poder seguir invocando metodos
	 */
	public TriggeredRule<T> trigger(T variable, String method) {
		this.variable = variable;
		this.method = method;
		variable.addObserver(this);
		return this;
	}

	/**
	 * {@inheritDoc} Es la funcion que se llama cuando el observado hace un cambio
	 * 
	 */
	@Override
	public void update(Observable obs, Object arg) {
		this.getAccion().accept(variable);
	}
}
