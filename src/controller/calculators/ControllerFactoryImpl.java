package controller.calculators;
import model.calculators.CalculatorModelBuilder;
import model.manager.ManagerModelInterface.Calculator;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ControllerFactoryImpl implements ControllerFactory {

    @Override
    public CalculatorController createController(final CalculatorModelBuilder model, final Calculator calcName) {
        return new CalculatorControllerTemplate(model.getModel(), calcName);
    }

}
