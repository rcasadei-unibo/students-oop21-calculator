package controller.calculators;

import java.util.Map;

import javax.swing.JPanel;

import controller.manager.CCManager;
import model.calculators.CalculatorModel;
import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import model.manager.ManagerModelInterface.Calculator;
import utils.Type;
import view.calculators.CombinatoricsCalculatorPanel;
import view.calculators.StandardCalculatorPanel;
import view.components.CCDisplay;

/**
 * MISSING JAVADOC.
 */
public final class CalculatorControllerTemplate implements CalculatorController {
    /**
     * 
     * MISSING JAVADOC.
     *
     */
    private final CalculatorModel model;
    private JPanel panel;
    private CCManager manager;
    private CCDisplay display;
    private final Calculator calcType;
    /**
     * 
     * @param model
     * @param calcType
     */
    public CalculatorControllerTemplate(final CalculatorModel model, final Calculator calcType) {
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
    @Override
    public JPanel getGUI() {
        return this.panel;
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
