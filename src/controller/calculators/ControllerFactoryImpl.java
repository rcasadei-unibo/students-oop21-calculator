package controller.calculators;
import model.calculators.CalculatorModel;
import model.manager.ManagerModelInterface.Calculator;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ControllerFactoryImpl implements ControllerFactory {

    @Override
    public CalculatorController createController(final CalculatorModel model, final Calculator calcName) {
        return new CalculatorControllerTemplate(model, calcName);
    }

}
