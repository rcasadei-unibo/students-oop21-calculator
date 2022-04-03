package controller.calculators;

import model.calculators.ScientificCalculatorModel;
import utils.AbstractCalculator;
import view.calculators.ScientificCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ScientificCalculatorController extends AbstractCalculator {
    /**
     * 
     */
    public ScientificCalculatorController() {
        this.model = new ScientificCalculatorModel();
        this.panel = new ScientificCalculatorPanel(this);
    }

}
