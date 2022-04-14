package controller.calculators;

import controller.manager.CCManager;
import utils.Type;

/**
 * 
 * Generic Controller used by Standard, Scientific, Combinatorics calculators.
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
     * 
     * @param op string representing the operation
     * @return a integer representing the precedence of the operation
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
     * @return manager used by the controller
     */
    CCManager getManager();
}
