package utils;
import java.util.function.BinaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CCBinaryOperator {
    private final BinaryOperator<Double> operator;
    private final int precedence;
    private final Type type;
    /**
     * 
     * @param operator
     * @param precedence
     * @param type
     */
    public CCBinaryOperator(final BinaryOperator<Double> operator, final int precedence, final Type type) {
        this.operator = operator;
        this.precedence = precedence;
        this.type = type;
    }
    /**
    * 
    * @param a
    * @param b
    * @return MISSING.
    */
    public double apply(final double a, final double b) {
        return this.operator.apply(a, b);
    }
    /**
     * 
     * @return MISSING.
     */
    public int getPrecedence() {
        return precedence;
    }
    /**
     * 
     * @return MISSING.
     */
    public Type getType() {
        return type;
    }
}
