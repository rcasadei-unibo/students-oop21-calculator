package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import utils.CreateButton;
import view.components.Graph;
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

    /**
     * 
     * @param controller
     * 
     */
    public ScientificCalculatorPanel(final CalculatorController controller) {
        super(controller);
        this.add(this.getScientificOperators(), BorderLayout.WEST);
        this.add(getScientificOperators());
    }

    private JPanel getScientificOperators() {
       final JPanel scientificOperators = new JPanel();
       scientificOperators.setLayout(new GridLayout(4, 3));
       scientificOperators.add(CreateButton.createOpButton("^", "^", "^", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("nthRoot", "nthRoot", "nthRoot", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("ln", "ln", "ln", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("log", "log", "log", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("Abs", "abs", "abs", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("!", "factorial", "!", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("sin", "sin", "sin", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("cos", "cos", "cos", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("tan", "tan", "tan", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("csc", "csc", "csc", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("sec", "sec", "sec", getController(), getDisplay()));
       scientificOperators.add(CreateButton.createOpButton("cot", "cot", "cot", getController(), getDisplay()));
       return scientificOperators;
    }

}
