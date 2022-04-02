package utils.calculate;


/**
 * @author pesic
 *
 */
public class Derivate {
	
	private Expression expression = new Expression();
	
	public String SymbolicDerivate(String expr) {
		expression.setExpr(expr);
		var result = expression.getDerivative().toString();
		expression.setExpr(result);
		
		return expression.getResult().toString();
		
	}
	
	public static void main(String[] args) {
		var derivative = new Derivate();
		System.out.println(derivative.SymbolicDerivate("5x+7x"));
	}
}
