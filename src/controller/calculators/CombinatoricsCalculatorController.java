package controller.calculators;

import model.calculators.CombinatoricsCalculatorModel;
import utils.AbstractCalculator;
import view.calculators.CombinatoricsCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorController extends AbstractCalculator {
    /**
     * 
     */
    public CombinatoricsCalculatorController() {
        this.model = new CombinatoricsCalculatorModel();
        this.panel = new CombinatoricsCalculatorPanel(this);
    }
}
