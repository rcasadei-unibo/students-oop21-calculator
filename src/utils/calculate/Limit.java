package utils.calculate;

import java.util.List;

/**
 * @author pesic
 *
 */
public class Limit implements Algorithm<Double> {
    private Expression expression;
	private static final double DISTANCE = 5;
	private double x0;
	//it could be refactored a template method
	private double calculateLimitBelow() {
		for (double x = x0 - DISTANCE; x <= x0; x = x0 - ((x0 - x) / DISTANCE)) {
            if (expression.getResult().getNumericResult(x) == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else if (expression.getResult().getNumericResult(x) == Double.NEGATIVE_INFINITY) {
                return Double.NEGATIVE_INFINITY;
            } else if (Double.isNaN(expression.getResult().getNumericResult(x))) {
                return expression.getResult().getNumericResult(x0 + ((x0 - x) * DISTANCE));
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
	
	private double calculateLimitAbove() {
		for (double x = x0 + DISTANCE; x >= x0; x = x0 - ((x0 - x) / DISTANCE)) {
            if (expression.getResult().getNumericResult(x) == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else if (expression.getResult().getNumericResult(x) == Double.NEGATIVE_INFINITY) {
                return Double.NEGATIVE_INFINITY;
            } else if (Double.isNaN(expression.getResult().getNumericResult(x))) {
                return expression.getResult().getNumericResult(x0 + ((x0 - x) * DISTANCE));
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
	
    @Override
    public void setParameters(final List<String> parameters) {
        if (parameters.isEmpty()) {
            throw new IllegalStateException("Not enough parameters");
        }
        this.x0 = Double.parseDouble(parameters.get(0));
    }

    @Override
    public Double calculate(final Expression expr) {
        expression = expr;
        final double aroundBelow = calculateLimitBelow();
        final double aroundAbove = calculateLimitAbove();
        return aroundBelow == aroundAbove ? aroundAbove : Double.NaN;
    }
}
