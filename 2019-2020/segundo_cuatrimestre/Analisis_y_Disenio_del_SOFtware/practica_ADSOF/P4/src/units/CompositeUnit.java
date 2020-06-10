package units;

import magnitude.exceptions.QuantityException;
import metricSystems.IMetricSystem;

/**
 * Esta clase implementa ICompositeUnit, tiene los atributos: Operator o,
 * IPhysicalUnit l e IPhysicalUnit r.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class CompositeUnit implements ICompositeUnit {
	private Operator o;
	private IPhysicalUnit l;
	private IPhysicalUnit r;

	/**
	 * @param l la unidad izquierda
	 * @param o la operacion entre unidades l y r
	 * @param r la unidad derecha
	 */
	public CompositeUnit(IPhysicalUnit l, Operator o, IPhysicalUnit r) {
		this.l = l;
		this.o = o;
		this.r = r;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operator getOperator() {
		return o;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPhysicalUnit getLeftUnit() {
		return l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPhysicalUnit getRightUnit() {
		return r;
	}

	/**
	 * {@inheritDoc} Ambas magnitudes para poder ser transformables deben de ser
	 * compuestas
	 */
	@Override
	public boolean canTransformTo(IPhysicalUnit u) {
		// Ambas magnitudes deben de ser compuestas
		if (!(u instanceof ICompositeUnit)) {
			return false;
		}

		return l.canTransformTo(((ICompositeUnit) u).getLeftUnit())
				&& r.canTransformTo(((ICompositeUnit) u).getRightUnit());
	}

	/**
	 * {@inheritDoc} primero transforma l en u.l y luego r en u.r, y el orden
	 * depende de Operador o
	 */
	@Override
	public double transformTo(double d, IPhysicalUnit u) throws QuantityException {
		if (canTransformTo(u) == false) {
			throw new QuantityException(u, this);
		}
		double intermedio = l.transformTo(d, ((ICompositeUnit) u).getLeftUnit());
		if (o == Operator.DIV) {
			// notice the units are in inversed order
			return ((ICompositeUnit) u).getRightUnit().transformTo(intermedio, r);
		}
		if (o == Operator.MUL) {
			return r.transformTo(intermedio, ((ICompositeUnit) u).getRightUnit());
		}
		// Operation not implemented
		throw new QuantityException(this, u);
	}

	/**
	 * {@inheritDoc} Las unidades compuestas no tienen Quantity. LLamar a abbrev()
	 * 
	 * @return null
	 */
	@Override
	public Quantity getQuantity() {
		// Las unidades compuestas no tienen Quantity. LLamar a abbrev()
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return la concatenacion de l.abbrev y r.abbrev
	 */
	@Override
	public String abbrev() {
		return l.abbrev() + " " + o + " " + r.abbrev();
	}

	/**
	 * {@inheritDoc} Las unidades compuestas no tienen MetricSystem
	 * 
	 * @return null
	 */
	@Override
	public IMetricSystem getMetricSystem() {
		// Las unidades compuestas no tienen MetricSystem
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "asdf";
	}
}
