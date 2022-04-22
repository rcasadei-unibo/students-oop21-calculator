package utils;

import java.util.List;
import java.util.function.Supplier;

import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;
import view.logics.AdvancedLogics;
/**
 * Command Factory produces commands that in this case have they have logic
 * integrated in it.
 *
 */
public class CommandFactory {
    private final AdvancedLogics receiver;

    /**
     *
     * @param receiver the business logic
     */
    public CommandFactory(final AdvancedLogics receiver) {
        this.receiver = receiver;
    }

    /**
     * Command Interface.
     *
     */
    public interface Command {
        /**
         * @return the needed string
         */
        String execute();
    }

    /**
     * Inserts the text in the memory.
     * @param text
     * @param constraints
     * @param sup
     * @return Given a particular text that respect the constraints the command
     *         returns the string + symbol
     */
    public Command insert(final String text, final List<String> constraints, final Supplier<String> sup) {
        return new Command() {

            @Override
            public String execute() {
                return receiver.insert(text, constraints, sup);
            }

        };
    }

    /**
     * Calcualates the expression.
     * @param params
     * @return the result of the expression
     */
    public Command calculate(final List<String> params) {
        return new Command() {

            @Override
            public String execute() {
               return receiver.calculate(params);
            }
        };
    }

    /**
     * @return is used for retrieving the previous state
     */
    public Command previousState() {
        return new Command() {

            @Override
            public String execute() {
                return receiver.previousState();
            }
        };
    }

    /**
     * @param text
     * @return the last expression with result
     */
    public Command addToHistory(final String text) {
        return new Command() {

            @Override
            public String execute() {
                return receiver.addToHistory(text);
            }
        };
    }

    /**
     * @return the last item eliminated
     */
    public Command deleteLast() {
        return new Command() {

            @Override
            public String execute() {
                return receiver.deleteLast();
            }
        };
    }
    /**
     * @param type
     * @return the chosen type
     */
    public Command selectedOperation(final TypeAlgorithm type) {
        return new Command() {

            @Override
            public String execute() {
                return receiver.selectedOperation(type);
            }
        };
    }
}
