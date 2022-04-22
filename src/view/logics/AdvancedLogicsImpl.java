package view.logics;

import java.util.List;
import java.util.function.Supplier;

import controller.calculators.CalculatorAdvancedController;
import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;
import utils.CalcException;
import utils.calculate.ExternData;

/**
 * Implementation of the AdvancedLogics interface.
 *
 */
public class AdvancedLogicsImpl implements AdvancedLogics {
    private final CalculatorAdvancedController controller;
    /**
     * @param controller
     */
    public AdvancedLogicsImpl(final CalculatorAdvancedController controller) {
        this.controller = controller;
    }

    @Override
    public String insert(final String text, final List<String> constraints, final Supplier<String> sup) {
        if (!constraints.isEmpty() && constraints.contains(text)) {
            controller.read(text + sup.get());
        } else {
            controller.read(text);
        }
        return controller.getCurrentDisplay();
    }

    @Override
    public String calculate(final List<String> params) {
        boolean isInfinity = false;
        String result;
        boolean isError = false;
        try {
            controller.setParameters(params);
            result = controller.calculate();

        } catch (IllegalArgumentException | CalcException e) {
            isError = true;
            result = "Syntax Error";
        }

        try {
            final var num = Double.parseDouble(result);
            if (num >= new ExternData().getConstants().get(String.valueOf(Double.POSITIVE_INFINITY))) {
                isInfinity = true;
                result = "Infinity";
            }
        } catch (NumberFormatException e) {
            isInfinity = false;
        }
        if (!isError && !isInfinity) {
            controller.readAll(result);
        }
        return result;
    }

    @Override
    public String previousState() {
        final TypeAlgorithm type = controller.getPreviousTypeOp();
        if (TypeAlgorithm.DERIVATE.equals(type)) {
            return "d/x(" + controller.getPreviousOp() + ")";
        } else if (TypeAlgorithm.INTEGRATE.equals(type)) {
            return "\u222B " + "["
                    + controller.getPreviousParameters().stream().reduce("",
                            (o1, o2) -> o1.isEmpty() ? o1 + o2 : o1 + "," + o2)
                    + "](" + controller.getPreviousOp() + ")d/x";
        } else {
            return "limX-->" + "[" + controller.getPreviousParameters().get(0) + "] (" + controller.getPreviousOp() + ")";
        }
    }

    @Override
    public String addToHistory(final String text) {
        controller.addToHistory(text);
        return text;
    }

    @Override
    public String deleteLast() {
        final String lastItem = String.valueOf(controller.getCurrentState().isEmpty() ? "" : controller.getCurrentState().charAt(0));
        controller.deleteLast();
        return lastItem;
    }

    @Override
    public String selectedOperation(final TypeAlgorithm type) {
        controller.setOperation(type);
        return type.toString();
    }

}
