package controller.temp;

import javax.swing.JPanel;

import controller.manager.CCManager;

/**
 * 
 */
public abstract class AbstractCalculator {
    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @param b second operand
     * @return result of the given operation
     */
    public abstract double applyBinaryOperation(String op, Double a, Double b);
    /**
     * @param op string representing the operation
     * @return precedence
     */
    public abstract int getPrecedence(String op);

    /**
     * @param op string representing the operation
     * @return "left" or "right" (dovrebbe essere una enum)
     */
    public abstract String getType(String op);

    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @return result of the given operation
     */
    public abstract Double applyUnaryOperation(String op, double a);

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is unary
     */
    public abstract boolean isUnaryOperator(String op);

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is binary
     */
    public abstract boolean isBinaryOperator(String op);

    /**
     * 
     * @param mng manager of the system
     */
    public abstract void setManager(CCManager mng);

    /**
     * 
     * @return view component of the calculator
     */
    public abstract JPanel getGUI();
}
