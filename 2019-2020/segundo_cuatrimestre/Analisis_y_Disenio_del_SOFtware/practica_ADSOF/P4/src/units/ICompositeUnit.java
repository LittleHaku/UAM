package units;

/**
 * Esta interfaz sirve para modelar unidades compuestas.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public interface ICompositeUnit extends IPhysicalUnit {
	/**
	 * @return el operador que relaciona la IPhysicalUnitLeft y la
	 *         IPhysicalUnitRight
	 */
	Operator getOperator();

	/**
	 * @return la IPhysicalUnitLeft
	 */
	IPhysicalUnit getLeftUnit();

	/**
	 * @return la IPhysicalUnitRight
	 */
	IPhysicalUnit getRightUnit();

	/**
	 * Es el tipo enumerado de Operator. Esta implementado Div y MUl
	 *
	 */
	enum Operator {
		DIV("/"), MUL("*");

		String symbol;

		Operator(String s) {
			symbol = s;
		}

		public String toString() {
			return symbol;
		}
	}
}
