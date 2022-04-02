package controller.calculators;

import model.calculators.CalculatorModelBuilder;
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
    CalculatorController createController(CalculatorModelBuilder model, Calculator calcName);
}
