package controller.temp;
/**
 * 
 */
public abstract class AbstractCalculator {
    public abstract double applyBinaryOperation(final String op, final Double a, final Double b) ;
       

    /**
     */
    public abstract int getPrecedence(final String token) ;

    /**
     * @return "left" or "right"
     */
    public abstract String getType(String token);

    /**
     */
    public abstract Double applyUnaryOperation(final String token,final double a) ;

    /**
     */
    public abstract boolean isUnaryOperator(final String token);

    /**
     */
    public abstract boolean isBinaryOperator(final String token);
}
