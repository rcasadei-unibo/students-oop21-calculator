package utils;

import java.util.List;
import java.util.function.Supplier;

import controller.calculators.CalculatorAdvancedController;
import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;

/**
 * Command Factory produces commands that in this case have they have logic
 * integrated in it.
 *
 */
public final class CommandFactory {

    private CommandFactory() {
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
     * @param text
     * @param constraints
     * @param sup
     * @param controller
     * @return Given a particular text that respect the constraints the command
     *         returns the string + symbol
     */
    public static Command insert(final String text, final List<String> constraints, final Supplier<String> sup,
            final CalculatorAdvancedController controller) {
        return new Command() {

            @Override
            public String execute() {
                if (!constraints.isEmpty() && constraints.contains(text)) {
                    controller.read(text + sup.get());
                } else {
                    controller.read(text);
                }
                return controller.getCurrentState();

            }

        };
    }

    /**
     * @param controller
     * @param params
     * @return the result of the expression
     */
    public static Command calculate(final CalculatorAdvancedController controller, final List<String> params) {
        return new Command() {

            @Override
            public String execute() {
                String result;
                boolean isError = false;
                try {
                    controller.setParameters(params);
                    result = controller.calculate();
                } catch (IllegalArgumentException | CalcException e) {
                    isError = true;
                    result = "Syntax Error";
                }
                if (!isError) {
                    controller.readAll(result);
                }

                return result;

            }
        };
    }

    /**
     * @param controller
     * @return is used for retrieving the previous state
     */
    public static Command previousState(final CalculatorAdvancedController controller) {
        return new Command() {

            @Override
            public String execute() {
                final TypeAlgorithm type = controller.getPreviousTypeOp();
                if (TypeAlgorithm.DERIVATE.equals(type)) {
                    return "d/x(" + controller.getPreviousOp() + ")";
                } else if (TypeAlgorithm.INTEGRATE.equals(type)) {
                    return "\u222B " + "["
                            + controller.getPreviousParameters().stream().reduce("",
                                    (o1, o2) -> o1.isEmpty() ? o1 + o2 : o1 + "," + o2)
                            + "](" + controller.getPreviousOp() + ")d/x";
                } else {
                    return "limX-->" + controller.getPreviousParameters().stream().reduce("",
                            (o1, o2) -> o1.isEmpty() ? o1 + o2 : o1 + "," + o2) + "(" + controller.getPreviousOp() + ")";
                }
            }
        };
    }

    /**
     * @param controller
     * @param text
     * @return the last expression with result
     */
    public static Command addToHistory(final CalculatorAdvancedController controller, final String text) {
        return new Command() {

            @Override
            public String execute() {
                controller.addToHistory(text);
                return text;
            }
        };
    }

    /**
     * @param controller
     * @return the last item eliminated
     */
    public static Command deleteLast(final CalculatorAdvancedController controller) {
        return new Command() {

            @Override
            public String execute() {
                final String lastItem = String.valueOf(controller.getCurrentState().isEmpty() ? "" : controller.getCurrentState().charAt(0));
                controller.deleteLast();
                return lastItem;
            }
        };
    }
    /**
     * @param controller
     * @param type
     * @return the chosen type
     */
    public static Command selectedOperation(final CalculatorAdvancedController controller, final TypeAlgorithm type) {
        return new Command() {

            @Override
            public String execute() {
                controller.setOperation(type);
                return type.toString();
            }
        };
    }
}
