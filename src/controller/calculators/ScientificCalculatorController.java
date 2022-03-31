package controller.calculators;

import model.calculators.ScientificCalculatorModel;
import view.calculators.ScientificCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ScientificCalculatorController extends CalculatorTemplate {
    /**
     * 
     */
    public ScientificCalculatorController() {
        this.setController(new ScientificCalculatorModel(), new ScientificCalculatorPanel());
    }

}
