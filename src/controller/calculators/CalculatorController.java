package controller.calculators;

import controller.manager.CCManager;
import utils.Type;
import view.components.CCDisplay;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public interface CalculatorController {

    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @param b second operand
     * @return result of the given operation
     */
    double applyBinaryOperation(String op, Double a, Double b);
    /**
     * @param op string representing the operation
     * @return precedence
     */
    int getPrecedence(String op);
    /**
     * @param op string representing the operation
     * @return the type of association of the operation(LEFT or RIGHT)
     */
    Type getType(String op);
    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @return result of the given operation
     */
    double applyUnaryOperation(String op, double a);
    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is unary
     */
    boolean isUnaryOperator(String op);
    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is binary
     */
    boolean isBinaryOperator(String op);
    /**
     * 
     * @param mng manager of the system
     */
    void setManager(CCManager mng);
    /**
     * 
     * @param display
     */
    void setDisplay(CCDisplay display);
    /**
     * 
     * @return display
     */
    CCDisplay getDisplay();
    /**
     * 
     * @return MISSING.
     */
    CCManager getManager();
}
