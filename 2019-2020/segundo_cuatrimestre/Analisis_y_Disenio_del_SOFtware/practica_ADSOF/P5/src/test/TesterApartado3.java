package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import producto.Producto;
import rules.Rule;
import rules.RuleSet;

/**
 * Es el Test del apartado 3, debe producir la salida esperada
 * 
 * @author Junco de las Heras y Marta Vaquerizo y Profesores ADSOF
 *
 */
public class TesterApartado3 {
	public static void main(String... args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		RuleSet<Producto> rs = new RuleSet<Producto>(); // Un conjunto de reglas aplicables sobre Productos

		rs.add(Rule.<Producto>rule("r1", "Rebaja un 10% los productos con fecha de caducidad cercana o pasada").when(
				pro -> Producto.getDateDiff(Calendar.getInstance().getTime(), pro.getCaducidad(), TimeUnit.DAYS) < 2)
				.exec(pro -> pro.setPrecio(pro.getPrecio() - pro.getPrecio() * 0.1)))
				.add(Rule.<Producto>rule("r2", "Rebaja un 5% los productos que valen más de 10 euros")
						.when(pro -> pro.getPrecio() > 10)
						.exec(pro -> pro.setPrecio(pro.getPrecio() - pro.getPrecio() * 0.05)));

		List<Producto> str = Arrays.asList(new Producto(10, sdf.parse("15/04/2020")), // parseamos a un Date
				new Producto(20, sdf.parse("20/03/2021")));

		rs.setExecContext(str); // indicamos que el conjunto de reglas rs se ejecutará sobre str
		rs.process(); // ejecutamos el conjunto de reglas

		System.out.println(str); // imprimimos str
	}
}
