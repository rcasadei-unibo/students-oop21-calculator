package utils.calculate;

/**
 * @author pesic
 *
 */
public class Integrator {
	
	private Expression expression = new Expression();
	private TrapezoidalAlgorithm alg = new TrapezoidalAlgorithm();
	
	public double Integrate(String expr ,double lowerBound, double upperBound) {
		expression.setExpr(expr);
		return alg.execute(expression, lowerBound, upperBound);
	}
	
	public static void main(String[] args) {
		var integrator = new Integrator();
		System.out.println(integrator.Integrate("x",0, 1));
		
	}
	
}
