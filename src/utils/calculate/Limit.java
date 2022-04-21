package utils.calculate;

import java.util.List;
import java.util.function.Predicate;

import controller.manager.CCEngine;
import utils.CalcException;

/**
 * Limit class.
 *
 */
public class Limit implements Algorithm {
    private Expression expression;
    private CCEngine engine;
	private static final double DISTANCE = 5;
	private static final double MINNUM = 1E-8;
	private Double x0;
	
	private void  parameterDefined() {
	    if (x0 == null) {
	        throw new IllegalArgumentException("Argument should be defined");
	    }
	}
	
	/**
	 * Calculates the limit numerically, i doens't work with all types of limits.
	 * @param cond : the condition for continuing evaluating the limit
	 * @param initValue : the value we start to evaluate the limit
	 * @return the result of the limit from above or below
	 * @throws CalcException
	 */
	private Double calculateLimit(final Predicate<Double> cond, final Double initValue) throws CalcException {
	    for (Double x = initValue; cond.test(x); x = x0 - ((x0 - x) / DISTANCE)) {
            if (expression.getResult().getNumericResult(x) == Double.POSITIVE_INFINITY) {
                return Double.POSITIVE_INFINITY;
            } else if (expression.getResult().getNumericResult(x) == Double.NEGATIVE_INFINITY) {
                return Double.NEGATIVE_INFINITY;
            } else if (Double.isNaN(expression.getResult().getNumericResult(x))) {
                return expression.getResult().getNumericResult(x0 + ((x0 - x) * DISTANCE));
            } else {
                if (x.equals(x0)) {
                    return expression.getResult().getNumericResult(x);
                } else if (x - x0 < MINNUM) {
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
            final String params = this.preprocessParameter(parameters.get(0));
            this.x0 = Double.parseDouble(new Expression(params, engine, false).getResult().getNumericResult(0.0).toString()) + MINNUM;
        } catch (NumberFormatException e) {
            throw new CalcException("Bad format Number, only numbers are accepted");
        }
    }

    private Double calc(final Expression expr) throws CalcException {
        expression = expr;
        final double aroundBelow = calculateLimit((num) -> num <= x0, x0 - DISTANCE);
        final double aroundAbove = calculateLimit((num) -> num >= x0, x0 + DISTANCE);
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

    @Override
    public List<String> getParameters() {
        return List.of(String.valueOf(x0));
    }

    @Override
    public void setEngine(final CCEngine engine) {
        this.engine = engine;
    }
}
