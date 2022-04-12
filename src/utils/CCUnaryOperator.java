package utils;

import java.util.function.UnaryOperator;

/**
 * 
 * The generic operator for binary operations. It contains a UnaryOperator(with his function), a int for the precedence and a Type for the type of association.
 *
 */
public class CCUnaryOperator {
    private final UnaryOperator<Double> operator;
    private final int precedence;
    private final Type type;
    /**
     * 
     * @param operator a UnaryOperator that contains the right Function for this operator
     * @param precedence the level of precedence of the operator
     * @param type the type of association of the operator
     */
    public CCUnaryOperator(final UnaryOperator<Double> operator, final int precedence, final Type type) {
        this.operator = operator;
        this.precedence = precedence;
        this.type = type;
    }
    /**
    * 
    * @param a the operand
    * @return the result of the operation
    */
    public double apply(final double a) {
        return this.operator.apply(a);
    }
    /**
     * 
     * @return the level of precedence of the operator
     */
    public int getPrecedence() {
        return precedence;
    }
    /**
     * 
     * @return the type of association of the operator
     */
    public Type getType() {
        return type;
    }
}
