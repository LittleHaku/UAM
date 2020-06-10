package jUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import producto.Producto;
import rules.Rule;
import rules.RuleSet;

/**
 * Test de la clase ConstrainedGraph
 * 
 * @author Junco de las Heras and Marta Vaquerizo
 *
 */
class RuleSetTest {
	private SimpleDateFormat sdf;
	private RuleSet<Producto> rs;
	private List<Producto> str;

	/**
	 * {@inheritDoc} Se ejecuta antes de cada funci�n.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		rs = new RuleSet<Producto>();
		rs.add(Rule.<Producto>rule("r1", "Rebaja un 10% los productos con fecha de caducidad cercana o pasada").when(
				pro -> Producto.getDateDiff(Calendar.getInstance().getTime(), pro.getCaducidad(), TimeUnit.DAYS) < 2)
				.exec(pro -> pro.setPrecio(pro.getPrecio() - pro.getPrecio() * 0.1)))
				.add(Rule.<Producto>rule("r2", "Rebaja un 5% los productos que valen más de 10 euros")
						.when(pro -> pro.getPrecio() > 10)
						.exec(pro -> pro.setPrecio(pro.getPrecio() - pro.getPrecio() * 0.05)));

		str = Arrays.asList(new Producto(10, sdf.parse("15/04/2020")), new Producto(20, sdf.parse("20/03/2021")));
		rs.setExecContext(str);
	}

	/**
	 * {@inheritDoc} Comprueba la funci�n process
	 */
	@Test
	void testProcess() {
		boolean flag = rs.process();
		assertSame(flag, true);
	}
}
