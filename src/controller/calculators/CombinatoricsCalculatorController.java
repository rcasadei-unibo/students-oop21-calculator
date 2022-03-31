package controller.calculators;

import model.calculators.CombinatoricsCalculatorModel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorController implements CalculatorController {
    /**
     * 
     */
    private final CalculatorTemplate controller;
    /**
     * 
     */
    public CombinatoricsCalculatorController() {
        this.controller = new CalculatorTemplate(new CombinatoricsCalculatorModel());
    }
    /**
     * 
     * @return ciao.
     */
    public CalculatorTemplate getController() {
        return this.controller;
    }
}
