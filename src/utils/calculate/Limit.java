package utils.calculate;

/**
 * @author pesic
 *
 */
public class Limit {
	private Expression expression = new Expression();
	private double distance = 5;
	//it could be refactored a ttemplate method
	private double calculateLimitBelow(String expr, double x0) {
		for (double x = x0 - distance; x <= x0; x = x0- ((x0 - x) / 10)) {
            if (expression.getResult().getNumericResult(x) == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else if (expression.getResult().getNumericResult(x) == Double.NEGATIVE_INFINITY) {
                return Double.NEGATIVE_INFINITY;
            } else if (Double.isNaN(expression.getResult().getNumericResult(x))) {
                return expression.getResult().getNumericResult(x0 + ((x0 - x) * 10));
            } else {
                if (x == x0) {
                    return expression.getResult().getNumericResult(x);
                } else if (x0 - x < 0.00000000001) {
                    x = x0;
                }

            }
        }
        return Double.NaN;
	}
	
	private double calculateLimitAbove(String expr, double x0) {
		for (double x = x0 + 10; x >= x0; x = x0- ((x0 - x) / 10)) {
            if (expression.getResult().getNumericResult(x) == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else if (expression.getResult().getNumericResult(x) == Double.NEGATIVE_INFINITY) {
                return Double.NEGATIVE_INFINITY;
            } else if (Double.isNaN(expression.getResult().getNumericResult(x))) {
                return expression.getResult().getNumericResult(x0 + ((x0 - x) * 10));
            } else {
                if (x == x0) {
                    return expression.getResult().getNumericResult(x);
                } else if (x - x0 < 0.00000000001) {
                    x = x0;
                }

            }
        }
        return Double.NaN;
	}
	
	public double calculateLimit(String expr, double x0) {
		expression.setExpr(expr);
		double aroundBelow = calculateLimitBelow(expr, x0);
	    double aroundAbove = calculateLimitAbove(expr, x0);
	    return aroundBelow == aroundAbove ? aroundAbove : Double.NaN;
	}
	
	public static void main(String[] args) {
		var limit = new Limit();
		System.out.println(limit.calculateLimit("sin(x)/x", 100000));
		
	}
}
