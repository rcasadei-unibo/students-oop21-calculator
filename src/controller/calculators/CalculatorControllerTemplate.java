package controller.calculators;

import java.util.Map;

import javax.swing.JPanel;

import controller.manager.CCManager;
import model.calculators.CalculatorModel;
import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Calculators;
import utils.Type;
import view.calculators.CombinatoricsCalculatorPanel;
import view.calculators.StandardCalculatorPanel;
import view.components.CCDisplay;

/**
 * MISSING JAVADOC.
 */
public final class CalculatorControllerTemplate {
    /**
     * 
     * MISSING JAVADOC.
     *
     */
    private final CalculatorModel model;
    private JPanel panel;
    private CCManager manager;
    private CCDisplay display;
    private final Calculators calcType;
    /**
     * 
     * @param model
     * @param calcType
     */
    public CalculatorControllerTemplate(final CalculatorModel model, final Calculators calcType) {
        this.model = model;
        this.calcType = calcType;
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
    public double applyBinaryOperation(final String op, final Double a, final Double b) {
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
        this.selectGUI();
    }
    private void selectGUI() {
        switch (this.calcType) {
        case STANDARD:
            this.panel = new StandardCalculatorPanel(this);
            break;
        case ADVANCED:
            break;
        case COMBINATORICS:
            this.panel = new CombinatoricsCalculatorPanel(this);
            break;
        case GRAPHIC:
            break;
        case PROGRAMMER:
            break;
        case SCIENTIFIC:
            break;
        default:
            break;
        }
    }
    /**
     * 
     * @param display
     */
    public void setDisplay(final CCDisplay display) {
        this.display = display;
    }
    /**
     * 
     * @return display
     */
    public CCDisplay getDisplay() {
        return this.display;
    }
    /**
     * 
     * @return MISSING.
     */
    public CCManager getManager() {
        return this.manager;
    }
}
