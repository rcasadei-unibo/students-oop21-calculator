package controller.calculators;
import model.calculators.CalculatorModel;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ControllerFactoryImpl implements ControllerFactory {

    @Override
    public CalculatorController createController(final CalculatorModel model) {
        return new CalculatorControllerTemplate(model);
    }

}
