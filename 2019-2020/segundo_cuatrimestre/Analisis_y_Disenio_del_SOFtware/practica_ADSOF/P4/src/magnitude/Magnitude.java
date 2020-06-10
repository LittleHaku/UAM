package magnitude;

import magnitude.exceptions.QuantityException;
import magnitude.exceptions.UnknownUnitException;
import metricSystems.IMetricSystemConverter;
import units.*;

/**
 * Esta clase modeliza una magnitud. Una magnitud tiene dos atributos, un valor
 * y una unidad, e implementa la interfaz de Magnitude. Es por ello que
 * implementa las funciones de esta interfaz.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Magnitude implements IMagnitude {
	private double valor;
	private IPhysicalUnit unit;

	/**
	 * Constructor de magnitud
	 * 
	 * @param valor un valor determinado.
	 * @param m     unidad de la magnitud.
	 */
	public Magnitude(double valor, IPhysicalUnit m) {
		super();
		this.valor = valor;
		this.unit = m;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param m la magnitud a sumar
	 * @return una magnitud que es this + m
	 * @throws QuantityException cuando no se puede transformar.
	 */
	@Override
	public IMagnitude add(IMagnitude m) throws QuantityException {
		if (m.getUnit().canTransformTo(this.unit) == true) {
			Magnitude mag = (Magnitude) ((Magnitude) m).transformTo(unit);
			return new Magnitude(valor + mag.getValor(), this.unit);
		} else {
			throw new QuantityException(this.unit, m.getUnit());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param m la magnitud a substraer
	 * @return una magnitud que es this - m
	 * @throws QuantityException cuando no se puede transformar.
	 */
	@Override
	public IMagnitude subs(IMagnitude m) throws QuantityException {
		if (m.getUnit().canTransformTo(this.unit) == true) {
			Magnitude mag = (Magnitude) ((Magnitude) m).transformTo(unit);
			return new Magnitude(valor - mag.getValor(), this.unit);
		} else {
			throw new QuantityException(m.getUnit(), this.unit);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param c la unidad fisica a transformar
	 * @return la transformacion de this a la unidad c
	 * @throws QuantityException cuando no se puede transformar.
	 */
	@Override
	public IMagnitude transformTo(IPhysicalUnit c) throws QuantityException {
		// si no se puede transformar
		if (this.unit.canTransformTo(c) == false) {
			throw new UnknownUnitException(this.unit, c);
		}
		if (unit instanceof CompositeUnit) {
			return new Magnitude(((CompositeUnit) unit).transformTo(valor, c), unit);
		}

		// si el sistema metrico es el mismo
		if (this.getUnit().getMetricSystem().base().equals(c.getMetricSystem().base())) {
			double value = (this.valor * this.unit.getQuantity().getValue()) / (c.getQuantity().getValue());
			return new Magnitude(value, c);
		}

		IMetricSystemConverter u = this.unit.getMetricSystem().getConverter(c.getMetricSystem());
		// si no hay un sistema conversor
		if (u == null) {
			throw new UnknownUnitException(this.unit, c);
		}
		return u.transformTo(this, c);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Unidad de la magnitud.
	 */
	@Override
	public IPhysicalUnit getUnit() {
		return this.unit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return Valor de la magnitud.
	 */
	@Override
	public double getValue() {
		return this.valor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return String con la abreviatura de la unidad de la magnitud y lo que mide,
	 *         es decir, Quantity.
	 */
	@Override
	public String toString() {
		if (this.unit instanceof ICompositeUnit) {
			return this.valor + " " + "[" + this.unit.abbrev() + "]";
		}
		return this.valor + " " + "[" + this.unit.abbrev() + " " + this.unit.getQuantity() + "]";
	}
}
