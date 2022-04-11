package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.IntStream;

import utils.CalcException;
import utils.ast.Operation;

/**
 * @author pesic
 *
 */
public class Integrator implements Algorithm{
	
	private Expression expression;
	private Double lowBound;
	private Double upperBound;
	private static final int STEPS = 500;
	
	private void  parameterDefined() {
        if (lowBound == null || upperBound == null) {
            throw new IllegalArgumentException("Argument should be defined");
        }
	}
	
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
        if (parameters.size() < 2 ) {
            throw new CalcException("Not enough parameters");
        }
        try {
            this.lowBound = Double.parseDouble(parameters.get(0));
            this.upperBound = Double.parseDouble(parameters.get(1));
        } catch (NumberFormatException e) {
            throw new CalcException("Bad format Number, only numbers are accepted");
        }
    }

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

	
}
