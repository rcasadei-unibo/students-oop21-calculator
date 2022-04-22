package view.logics;

import java.util.List;
import java.util.function.Supplier;

import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;

/**
 * Interface used by the AdvancedCalculatorPanel so it can interact with the model.
 *
 */
public interface AdvancedLogics {

    /**
     * @param text : the text on the button
     * @param constraints : a list of strings that should follow a constraint 
     * @param sup : returns a symbol if the text follows a constraint 
     * @return Given a particular text that respect the constraints the command
     *         returns the string + symbol
     */
    String insert(String text, List<String> constraints, Supplier<String> sup);

    /**
     * @param params : parameters for the calculation if needed e.g. limits needs 1 parameter and integrals need 2
     * @return the result of the expression
     */
    String calculate(List<String> params);

    /**
     * @return Is used for retrieving the previous state
     */
    String previousState();

    /**
     * @param text : expression = result
     * @return the last expression with result
     */
    String addToHistory(String text);

    /**
     * @return the last item eliminated
     */
    String deleteLast();

    /**
     * @param type : Type of operation chosen (Derivative, Integrate, Limit)
     * @return the chosen type
     */
    String selectedOperation(TypeAlgorithm type);
}
