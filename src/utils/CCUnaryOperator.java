package utils;

import java.util.function.UnaryOperator;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CCUnaryOperator {
    private final UnaryOperator<Double> operator;
    private final int precedence;
    private final Type type;
    /**
     * 
     * @param operator
     * @param precedence
     * @param type
     */
    public CCUnaryOperator(final UnaryOperator<Double> operator, final int precedence, final Type type) {
        this.operator = operator;
        this.precedence = precedence;
        this.type = type;
    }
    /**
    * 
    * @param a
    * @return MISSING.
    */
    public double apply(final double a) {
        return this.operator.apply(a);
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
