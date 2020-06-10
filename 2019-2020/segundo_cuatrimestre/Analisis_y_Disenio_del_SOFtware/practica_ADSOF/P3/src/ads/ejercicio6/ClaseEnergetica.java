package ads.ejercicio6;

/**
 * Es la enumeracion de la ClaseEnergetica
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public enum ClaseEnergetica {
	A(4), B(3), C(2), D(1), Desconocida(0);

	private final int valor;

	private ClaseEnergetica(int c) {
		valor = c;
	}

	public int valor() {
		return valor;
	}
}
