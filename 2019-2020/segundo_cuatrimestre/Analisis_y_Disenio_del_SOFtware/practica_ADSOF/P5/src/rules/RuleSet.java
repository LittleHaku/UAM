package rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Esta clase es un conjunto de reglas.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 * @param <T> elemento sobre el que se van a aplicar las reglas.
 */
public class RuleSet<T> {
	private final Collection<Rule<T>> rules;
	private final Collection<T> elementos;

	/**
	 * Contructor de RuleSet, donde se inicializan el conjunto de reglas y los
	 * elementos sobre los que se van a aplicar las reglas del conjunto.
	 */
	public RuleSet() {
		rules = new ArrayList<>();
		elementos = new ArrayList<>();
	}

	/**
	 * Se añaden los elementos de la coleccion pasada por argumentos a elementos.
	 * 
	 * @param str coleccion de elementos a añadir.
	 */
	public void setExecContext(Collection<T> str) {
		this.elementos.addAll(str);
	}

	/**
	 * Se pasa por todos los elementos, probando todas las reglas del set, y si se
	 * cumple, se ejecuta.
	 * 
	 * @return true si alguna regla se cumple, false en el caso contrario.
	 */
	public boolean process() {
		boolean flag = false;
		for (T t : this.elementos) {
			for (Rule<T> r : rules) {
				if (r.getCondicion().test(t)) {
					flag = true;
					r.getAccion().accept(t);
				}
			}
		}
		return flag;
	}

	/**
	 * Se añade una regla.
	 * 
	 * @param exec la regla a añadir.
	 * @return la lista de reglas para facilitar la programacion fluida.
	 */
	public List<Rule<T>> add(Rule<T> exec) {
		if (!this.rules.contains(exec)) {
			this.rules.add(exec);
		}
		return (List<Rule<T>>) this.rules;
	}
}
