package utils.ast;

/**
 *Operaion inteface used for calculating derivateves and geneal results.
 *
 */
public interface Operation {
	/**
	 * @param val
	 * @return the numerical result of he expression given the value
	 */
	Double getNumericResult(Double val);
	
	/**
	 * @return he derivative
	 */
	Operation getDerivative();
}
