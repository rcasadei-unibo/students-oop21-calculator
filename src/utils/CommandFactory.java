package utils;

import java.util.List;
import java.util.function.Supplier;

import controller.calculators.CalculatorAdvancedController;
import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;

/**
 * @author pesic
 *
 */
public class CommandFactory {

    /**
     * @author pesic
     *
     */
    public interface Command {
        String execute();
    }

    /**
     * @param text
     * @param constraints
     * @param sup
     * @param controller
     * @return c
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
     * @return c
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
                } catch (CalcException e) {
                    isError = true;
                    result = e.getMessage();
                } catch (IllegalArgumentException e) {
                    isError = true;
                    result = "Syntax Error";
                }
                if (!isError) {
                    controller.read(result);
                }

                return result;

            }
        };
    }

    /**
     * @param controller
     * @return c
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
}
