package temp;
import java.util.function.BinaryOperator;

public class CCBinaryOperator{
	private String name;
	private BinaryOperator<Double> operator;
	private int precedence = 1;
	private String type;
	
	public CCBinaryOperator(BinaryOperator<Double> operator) {
		this.operator = operator;
	}
	
	public Double apply(Double a, Double b) {
		return this.operator.apply(a, b);
	}
}
