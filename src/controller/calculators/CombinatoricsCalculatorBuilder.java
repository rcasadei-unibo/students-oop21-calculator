package controller.calculators;

import model.calculators.CombinatoricsCalculatorModel;
import utils.Calculators;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorBuilder implements CalculatorBuilder {
    /**
     * 
     */
    private final CalculatorControllerTemplate controller;
    /**
     * 
     */
    public CombinatoricsCalculatorBuilder() {
        this.controller = new CalculatorControllerTemplate(new CombinatoricsCalculatorModel(), Calculators.COMBINATORICS);
    }
    @Override
    public CalculatorControllerTemplate getController() {
        return this.controller;
    }
}
