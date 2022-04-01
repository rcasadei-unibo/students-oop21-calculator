package controller.calculators;

import model.calculators.ScientificCalculatorModel;
import view.calculators.ScientificCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ScientificCalculatorController implements CalculatorControllerBuilder {
    /**
     * 
     */
    public ScientificCalculatorController() {
        this.setController(new ScientificCalculatorModel(), new ScientificCalculatorPanel());
    }

}
