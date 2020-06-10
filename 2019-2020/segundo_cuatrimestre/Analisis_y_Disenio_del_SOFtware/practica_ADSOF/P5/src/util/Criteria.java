package util;

/**
 * Esta enumeración tiene los tres criterios que se pueden dar en una colección
 * al aplicar una regla ("ppredicate"). El primero, existential, al menos uno de
 * los elementos cumple la regla. El segundo, unitary, existe un único elemento
 * que cumple la regla. Y el tercero, universal, todos los elementos cumplen la
 * regla.
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public enum Criteria {
	EXISTENTIAL, UNITARY, UNIVERSAL
}
