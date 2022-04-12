package controller.calculators;

import model.calculators.CalculatorModel;

/**
 * 
 * Simple Factory that creates a controller given the model.
 *
 */
public interface ControllerFactory {

    /**
     * 
     * @param model the model of the calculator handled by the controller
     * @return a controller given the model
     */
    CalculatorController createController(CalculatorModel model);
}
