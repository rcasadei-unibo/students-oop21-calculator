package controller.calculators;

import java.util.Map;


import controller.manager.CCManager;
import model.calculators.CalculatorModel;
import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Type;
import view.components.CCDisplay;

/**
 * 
 * Implementation of the generic Controller.
 * 
 */
public class CalculatorControllerTemplate implements CalculatorController {
    /**
     * 
     * MISSING JAVADOC.
     *
     */
    private final CalculatorModel model;
    private CCManager manager;
    private CCDisplay display;

    /**
     * 
     * @param model the model of the calculator handled by the controller
     */
    public CalculatorControllerTemplate(final CalculatorModel model) {
        this.model = model;
    }

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

    @Override
    public double applyBinaryOperation(final String op, final Double a, final Double b) {
        return this.getBinaryOperators().get(op).apply(a, b);
    }

    @Override
    public int getPrecedence(final String op) {
        return this.getBinaryOperators().get(op).getPrecedence();
    }

    @Override
    public Type getType(final String op) {
        return this.getBinaryOperators().get(op).getType();
    }

    @Override
    public double applyUnaryOperation(final String op, final double a) {
        return this.getUnaryOperators().get(op).apply(a);
    }

    @Override
    public boolean isUnaryOperator(final String op) {
        return this.getUnaryOperators().containsKey(op);
    }

    @Override
    public boolean isBinaryOperator(final String op) {
        return this.getBinaryOperators().containsKey(op);
    }

    @Override
    public void setManager(final CCManager mng) {
        this.manager = mng;
    }

    @Override
    public void setDisplay(final CCDisplay display) {
        this.display = display;
    }

    @Override
    public CCDisplay getDisplay() {
        return this.display;
    }

    @Override
    public CCManager getManager() {
        return this.manager;
    }
}
