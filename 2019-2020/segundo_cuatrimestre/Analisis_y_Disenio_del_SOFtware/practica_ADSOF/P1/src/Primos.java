import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Esta clase mantiene un conjunto de números enteros
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 */
public class Primos {
    private SortedSet<Integer> primos = new TreeSet<>();
    private int max = 1;

    public Primos() {
    }

    /**
     * @return SortedSet de los primos hasta n
     */
    public SortedSet<Integer> getPrimos() {
        return primos;
    }

    public String toString() {
        return "Primos hasta " + max + " = " + primos;
    }

    /**
     * @param n el numero que se quiere ver si es primo
     * @return true si n es primo, sino false
     */
    public boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        if (n > max) {
            actualizaPrimos(n);
        }
        return primos.contains(n);
    }

    /**
     * @param n el numero que se quiere saber si es primo, sabiendo que los que hay
     *          en primos si los son.
     * @return true si n es primo, sino false
     */
    public boolean compruebaPrimo(int n) {
        int sqr = (int)(Math.sqrt(n)) + 3;
        for (Integer p : primos) {
            if (n % p == 0) {
                return false;
            }
            //If p is greater than the square root of n, then no more factors found, break
            if(p > sqr) {
                break;
            } 
        }
        return true;
    }

    /**
     * añade todos los primos en el set desde max+1 hasta n, incluido
     * 
     * @param n el numero tal que todos los primos hasta n pasen a estar en el
     *          SortedSet
     */
    public void actualizaPrimos(int n) {
        for (int i = max + 1; i <= n; i++) {
            if (compruebaPrimo(i) == true) {
                primos.add(i);
            }
        }
        max = n;
    }

    /**
     * Calcula los divisores de un numero n
     * 
     * @param n numero no primo del que se quieren sacar los divisores
     * 
     * @return SortedSet de los divisores del numero n
     */
    public SortedSet<Integer> divisoresPrimos(int n) {
        SortedSet<Integer> s = new TreeSet<>();
        if (n < 1) {
            return s;
        }

        for (int p : primos) {
            // No hay mas divisores
            if (p > n) {
                break;
            }
            // p es divisor
            if (n % p == 0) {
                s.add(p);
            }
        }
        return s;
    }

    /**
     * @param args Los argumentos de la linea de comando. Se esperan enteros, como
     *             cadenas
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Se necesitan más argumentos");
            return;
        }

        Primos p = new Primos();

        for (String cad : args) {
            int n = Integer.parseInt(cad);
            if (p.esPrimo(n)) {
                System.out.println(n + " es primo");
            } else {
                System.out.print(n + " no es primo, divisores: ");
                SortedSet<Integer> s = p.divisoresPrimos(n);
                System.out.println(s);
            }
        }

        System.out.println(p);
    }
}