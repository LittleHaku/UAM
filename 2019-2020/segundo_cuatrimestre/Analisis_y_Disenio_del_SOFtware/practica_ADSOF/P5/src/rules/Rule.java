package rules;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Esta clase simula un regla parametrizada, definida por un nombre, y la regla
 * en sí.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> Elemento sobre el que se va a aplicar la regla.
 */
public class Rule<T> {
	@SuppressWarnings("unused")
	private String nombre;
	@SuppressWarnings("unused")
	private String rule;
	private Predicate<T> condicion;
	private Consumer<T> accion;

	/**
	 * Constructor de Rule.
	 * 
	 * @param nombre nombre de la regla
	 * @param rule   cual es la regla
	 */
	protected Rule(String nombre, String rule) {
		this.nombre = nombre;
		this.rule = rule;
	}

	/**
	 * Crea una regla parametrizada.
	 * 
	 * @param <T>    elemento sobre el que se va a aplicar la regla.
	 * @param nombre nombre de la regla.
	 * @param rule   la regla
	 * @return una nueva regla con los parametros pasados.
	 */
	public static <T> Rule<T> rule(String nombre, String rule) {
		return new Rule<T>(nombre, rule);
	}

	/**
	 * Guarda la condición pasada por argumento.
	 * 
	 * @param pred expresión lambda
	 * @return la regla con la nueva condición, para concatenar otra posible llamada
	 *         a otro metodo de Rule.
	 */
	public Rule<T> when(Predicate<T> pred) {
		this.condicion = pred;
		return this;
	}

	/**
	 * Guarda la acción que la regla ejecuta si se cumple la condición.
	 * 
	 * @param pred expresión lambda
	 * @return la regla con la nueva accion, para concatenar otra posible llamada a
	 *         otro metodo de Rule.
	 */
	public Rule<T> exec(Consumer<T> pred) {
		this.accion = pred;
		return this;
	}

	/**
	 * @return la condición
	 */
	public Predicate<T> getCondicion() {
		return condicion;
	}

	/**
	 * @return la accion a realizar por la regla.
	 */
	public Consumer<T> getAccion() {
		return accion;
	}
}
