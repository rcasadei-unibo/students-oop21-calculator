package controller.calculators;

import model.calculators.CalculatorModel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public interface ControllerFactory {
    /**
     * 
     * @param model
     * @return MISSING JAVADOC.
     */
    CalculatorController createController(CalculatorModel model);
}
