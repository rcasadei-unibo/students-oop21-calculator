package controller.calculators;

import model.calculators.CombinatoricsCalculatorModelBuilder;
import utils.Calculators;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorControllerBuilder implements CalculatorControllerBuilder {
    /**
     * 
     */
    private final CalculatorControllerTemplate controller;
    /**
     * 
     */
    public CombinatoricsCalculatorControllerBuilder() {
        this.controller = new CalculatorControllerTemplate(new CombinatoricsCalculatorModelBuilder().getModel(), Calculators.COMBINATORICS);
    }
    @Override
    public CalculatorControllerTemplate getController() {
        return this.controller;
    }
}
