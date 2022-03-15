package utils;

import java.util.Map;

import javax.swing.JPanel;

import controller.manager.CCManager;
import model.calculators.CalculatorModel;

/**
 * MISSING JAVADOC.
 */
public abstract class AbstractCalculator {
    /**
     * 
     * MISSING JAVADOC.
     *
     */
    protected CalculatorModel model;
    protected JPanel panel;
    protected CCManager manager;
    /**
     * 
     * @param model
     * @param panel
     */
    public abstract void setCalculator();  
    /**
     * 
     * @return a map containing all the binary operators of the mounted calculator
     */
    private Map<String, CCBinaryOperator> getBinaryOperators() {
        return this.model.getBinaryOpMap();
    }
    /**
     * 
     * @return a map containing all the unary operators of the mounted calculator
     */
    private Map<String, CCUnaryOperator> getUnaryOperators() {
        return this.model.getUnaryOpMap();
    }
    /**
     * 
     * @return view component of the calculator
     */
    public JPanel getGUI() {
        return this.panel;
    }
    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @param b second operand
     * @return result of the given operation
     */
    protected double applyBinaryOperation(final String op, final Double a, final Double b) {
        return this.getBinaryOperators().get(op).apply(a, b);
    }
    /**
     * @param op string representing the operation
     * @return precedence
     */
    public int getPrecedence(final String op) {
        return this.getBinaryOperators().get(op).getPrecedence();
    }

    /**
     * @param op string representing the operation
     * @return the type of association of the operation(LEFT or RIGHT)
     */
    public Type getType(final String op) {
        return this.getBinaryOperators().get(op).getType();
    }

    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @return result of the given operation
     */
    public Double applyUnaryOperation(final String op, final double a) {
        return this.getUnaryOperators().get(op).apply(a);
    }

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is unary
     */
    public boolean isUnaryOperator(final String op) {
        return this.getUnaryOperators().containsKey(op);
    }

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is binary
     */
    public boolean isBinaryOperator(final String op) {
        return this.getBinaryOperators().containsKey(op);
    }

    /**
     * 
     * @param mng manager of the system
     */
    public void setManager(final CCManager mng) {
        this.manager = mng;
    }
}
