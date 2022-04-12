package controller.calculators;
import model.calculators.CalculatorModel;

/**
 * 
 * Implementation of the factory used to create the controller for Standard, Scientific, Combinatorics calculators.
 *
 */
public class ControllerFactoryImpl implements ControllerFactory {

    @Override
    public CalculatorController createController(final CalculatorModel model) {
        return new CalculatorControllerTemplate(model);
    }
}
