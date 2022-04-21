package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import model.manager.EngineModelInterface.Calculator;
import view.logics.CreateButton;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorPanel extends StandardCalculatorPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1795172134074823312L;
    private static final CalculatorController CONTROLLER = Calculator.SCIENTIFIC.getController();
    /**
     * 
     */
    public ScientificCalculatorPanel() {
        super();
        this.add(this.getScientificOperators(), BorderLayout.WEST);
    }

    private JPanel getScientificOperators() {
       final JPanel scientificOperators = new JPanel();
       scientificOperators.setLayout(new GridLayout(4, 3));
       scientificOperators.add(CreateButton.createOpButton("^", "^", "^", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("nthRoot", "nthRoot", "nthRoot", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("ln", "ln", "ln", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("log", "log", "log", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("Abs", "abs", "abs", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("!", "factorial", "!", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("sin", "sin", "sin", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("cos", "cos", "cos", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("tan", "tan", "tan", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("csc", "csc", "csc", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("sec", "sec", "sec", CONTROLLER, getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("cot", "cot", "cot", CONTROLLER, getDisplay()));
       return scientificOperators;
    }

}
