package utils;
import java.util.function.BinaryOperator;
/**
 * 
 * The generic operator for binary operations. It contains a BinaryOperator(with his BiFunction), a int for the precedence and a Type for the type of association.
 *
 */
public class CCBinaryOperator {
    private final BinaryOperator<Double> operator;
    private final int precedence;
    private final Type type;
    /**
     * 
     * @param operator a BinaryOperator that contains the right BiFunction for this operator
     * @param precedence the level of precedence of the operator
     * @param type the type of association of the operator
     */
    public CCBinaryOperator(final BinaryOperator<Double> operator, final int precedence, final Type type) {
        this.operator = operator;
        this.precedence = precedence;
        this.type = type;
    }
    /**
    * 
    * @param a the first operand
    * @param b the second operand
    * @return the result of the operation
    */
    public double apply(final double a, final double b) {
        return this.operator.apply(a, b);
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
