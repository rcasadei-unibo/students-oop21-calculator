package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;

import utils.ast.Operation;

/**
 * @author pesic
 *
 */
public class Integrator implements Algorithm<Double> {
	
	private Expression expression;
	private double lowBound;
	private double upperBound;
	private static final int STEPS = 500;
	
	private double trapezoidalAlgorithm() {
        final double h = (upperBound - lowBound) / STEPS;
        final Operation func = expression.getResult();
        double result = 0.5 * func.getNumericResult(lowBound) + 0.5 * func.getNumericResult(upperBound);
        result = IntStream.range(1, STEPS).mapToDouble(i -> i).reduce(result, (acc, o2) -> acc + func.getNumericResult(lowBound + o2 * h));
        result = result * h;
        return result;
    }
	
    @Override
    public void setParameters(final List<String> parameters) {
        if(parameters.size() < 2 ) {
            throw new IllegalStateException("Not enough parameters");
        }
        this.lowBound = Double.parseDouble(parameters.get(0));
        this.upperBound = Double.parseDouble(parameters.get(1));
        parameters.remove(0);
        parameters.remove(1);
    }

    @Override
    public Double calculate(final Expression expr) {
        expression = expr;
        return trapezoidalAlgorithm();
    }
	
}
