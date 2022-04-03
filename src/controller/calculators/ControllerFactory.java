package controller.calculators;

import model.calculators.CalculatorModel;
import model.manager.ManagerModelInterface.Calculator;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public interface ControllerFactory {
    /**
     * 
     * @param model
     * @param calcName
     * @return MISSING JAVADOC.
     */
    CalculatorController createController(CalculatorModel model, Calculator calcName);
}
