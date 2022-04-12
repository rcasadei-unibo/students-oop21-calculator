package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;

import utils.CalcException;

/**
 * @author pesic
 *
 */
public class Limit implements Algorithm {
    private Expression expression;
	private static final double DISTANCE = 5;
	private Double x0;
	
	private void  parameterDefined() {
	    if(x0.equals(null)) {
	        throw new IllegalArgumentException("Argument should be defined");
	    }
	}
	
	//it could be refactored a template method
	private double calculateLimitBelow() throws CalcException {
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
	
	private double calculateLimitAbove() throws CalcException {
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
    public void setParameters(final List<String> parameters) throws CalcException {
        if (parameters.isEmpty()) {
            throw new CalcException("Not enough parameters");
        }
        try {
            this.x0 = Double.parseDouble(parameters.get(0));
        } catch(NumberFormatException e) {
            throw new CalcException("Bad format Number, only numbers are accepted");
        }
    }

    private Double calc(final Expression expr) throws CalcException {
        expression = expr;
        final double aroundBelow = calculateLimitBelow();
        final double aroundAbove = calculateLimitAbove();
        return aroundBelow == aroundAbove ? aroundAbove : Double.NaN;
    }

    @Override
    public String calculate(final Expression expr) throws CalcException {
        parameterDefined();
        return calc(expr).toString();
    }

    @Override
    public void unsetParameters() {
        this.x0 = null;
    }
}
