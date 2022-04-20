package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.ast.Operation;

/**
 * Integrator class.
 *
 */
public class Integrator implements Algorithm {
	private Expression expression;
	private CCEngine engine;
	private Double lowBound;
	private Double upperBound;
	private static final int STEPS = 500;
	
	private void  parameterDefined() {
        if (lowBound == null || upperBound == null) {
            throw new IllegalArgumentException("Argument should be defined");
        }
	}
	
	/** It uses the Trapezoidal algorithm for calculating single definite integrals https://en.wikipedia.org/wiki/Trapezoidal_rule.
	 * @return the result of the integral
	 * @throws CalcException
	 */
	private double trapezoidalAlgorithm() throws CalcException {
        final double h = (upperBound - lowBound) / STEPS;
        final Operation func = expression.getResult();
        double result = 0.5 * func.getNumericResult(lowBound) + 0.5 * func.getNumericResult(upperBound);
        result = IntStream.range(1, STEPS).mapToDouble(i -> i).reduce(result, (acc, o2) -> acc + func.getNumericResult(lowBound + o2 * h));
        result = result * h;
        return result;
    }
	
    @Override
    public void setParameters(final List<String> parameters) throws CalcException {
        if (parameters.size() < 2) {
            throw new CalcException("Not enough parameters");
        }
        try {
            final String params1 = this.preprocessParameter(parameters.get(0));
            final String params2 = this.preprocessParameter(parameters.get(1));
            this.lowBound = Double.parseDouble(new Expression(params1, engine, false).getResult().getNumericResult(0.0).toString());
            this.upperBound = Double.parseDouble(new Expression(params2, engine, false).getResult().getNumericResult(0.0).toString());
        } catch (IllegalArgumentException | CalcException e) {
            throw new CalcException("Bad format Number, only numbers are accepted");
        }
    }

    /**
     * @param expr
     * @return the result of the integral
     * @throws CalcException
     */
    private Double calc(final Expression expr) throws CalcException {
        expression = expr;
        return trapezoidalAlgorithm();
    }

    @Override
    public String calculate(final Expression expr) throws CalcException {
        parameterDefined();
        return calc(expr).toString();
    }

    @Override
    public void unsetParameters() {
        this.lowBound = null;
        this.upperBound = null;
    }

    @Override
    public List<String> getParameters() {
        return List.of(String.valueOf(lowBound), String.valueOf(upperBound));
    }

    @Override
    public void setEngine(final CCEngine engine) {
        this.engine = engine;
    }

	
}
