import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Esta clas mantiene un conjunto de números enteros
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 */
public class Ordena {
    // usamos un conjunto ordenado, que implementa TreeSet
    private SortedSet<Integer> numeros = new TreeSet<>();

    /**
     * Constructor, con el array de cadenas
     * 
     * @param cadenas a insertar, tras convertir a números
     */
    public Ordena(String... cadenas) {
        for (String s : cadenas) {
            int n = Integer.parseInt(s);
            numeros.add(n);
        }
    }

    /**
     * @return numeros
     */
    public SortedSet<Integer> getNumeros() {
        return numeros;
    }

    /**
     * @return Cadena que representa este objeto
     */
    public String toString() {
        return "Hay " + numeros.size() + " números" + ": " + numeros;
    }

    /**
     * Punto de entrada a la aplicación Este método ordena los números de la linea
     * de comando
     * 
     * @param args Los argumentos de la linea de comando. Se esperan enteros, como
     *             cadenas
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Se espera al menos dos números como parámetros");
        } else {
            Ordena c = new Ordena(args);
            System.out.println(c);
        }
    }
}