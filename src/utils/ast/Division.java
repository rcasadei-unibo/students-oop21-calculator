package utils.ast;

/**
 * @author pesic
 *
 */
public class Division extends BinaryOperation {

	/**
	 * @param left
	 * @param right
	 */
	public Division(Operation left, Operation right) {
		super(left, right);
	}

	/**
	 *
	 */
	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}
	
	/**
	 *
	 */
	public String toString(){
		return "(" + left.toString() + ")/(" + right.toString() + ")";
	}

	/**
	 *
	 */
	@Override
	public Double getNumericResult(Double val) {
		return left.getNumericResult(val) / right.getNumericResult(val);
	}
	
	/**
	 *
	 */
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Division)) return false;
		Division abs = (Division) o;
		return (left.equals(abs.left) && right.equals(abs.right));
	}
	
	/**
	 *
	 */
	public int hashCode(){
		return 67 * (left.hashCode() + right.hashCode());
	}

	@Override
	public Operation getDerivative() {
		return new Division(new Subtraction(new Product(left.getDerivative(), right), new Product(left, right.getDerivative())),
				new Pow(right, new Constant("2")));
	}
}
