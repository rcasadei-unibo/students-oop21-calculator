package utils.calculate;

import java.util.stream.IntStream;

import utils.ast.Operation;

/**
 * @author pesic
 *
 */
public class TrapezoidalAlgorithm {
	
	private int steps = 500;
	
	public double execute(Expression expression, double lowBound, double upperBound) {
		double h = (upperBound-lowBound)/steps;
		Operation func = expression.getResult();
		System.out.println(func.getNumericResult(lowBound));
		System.out.println(func.getNumericResult(upperBound));
		double result = 0.5*func.getNumericResult(lowBound)+0.5*func.getNumericResult(upperBound);
		result = IntStream.range(1, steps).mapToDouble(i->i).reduce(result, (acc, o2)->acc+func.getNumericResult(lowBound+o2*h));
		result = result * h;
		return result;
	}
}
