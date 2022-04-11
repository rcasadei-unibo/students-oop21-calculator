package view.calculators;
import controller.calculators.CalculatorController;
import utils.CalcException;
/**
 * 
 *  MISSING JAVADOC.
 *
 */
public class CombinatoricsLogicsImpl implements CombinatoricsLogics {
    private final CalculatorController controller = Calculator.COMBINATORICS.getController();
    private String opString = "";
    private String opFormat = "";
    @Override
    public String numberAction(final String btnText) {
        controller.getManager().memory().read(btnText);
        try {
            return this.getDisplayText();
        } catch (CalcException e1) {
            this.clearStrings();
            controller.getManager().memory().clear();
            return "Invalid Operation";
        }
    }
    @Override
    public String calculateAction() {
        String adder = "";
        try {
            adder = controller.isBinaryOperator(opString) ? controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1] : "";
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.clearStrings();
            controller.getManager().memory().clear();
        }
        adder += opString.isBlank() ? "" : ") =";
        final String result = opFormat + adder;
        this.clearStrings();
        controller.getManager().engine().calculate();
        return result;
    }
    @Override
    public String getStream() {
        return controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b);
    }
    @Override
    public String backspaceAction() {
        try {
            if (controller.getManager().memory().getCurrentState().get(controller.getManager().memory().getCurrentState().size() - 1).length() > 1) {
                this.clearStrings();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
        controller.getManager().memory().deleteLast();
        try {
            return this.getDisplayText();
        } catch (CalcException e1) {
            this.clearStrings();
            controller.getManager().memory().clear();
            return " ";
        }
    }
    @Override
    public String opAction(final String btnName, final String opName) {
        final String closer = controller.isBinaryOperator(opName) ? ", " : "";
        opFormat = btnName + "(" + controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b) + closer;
        controller.getManager().memory().read(opName);
        opString = opName;
        return opFormat;
    }
    private String getDisplayText() throws CalcException {
        if (!opFormat.isBlank()) {
            if (!opString.isBlank() && controller.isBinaryOperator(opString)) {
                try {
                    return opFormat + controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1];
                } catch (ArrayIndexOutOfBoundsException e3) {
                    return opFormat;
                }
            } else {
                throw new CalcException("Invalid operation");
            }
        } else {
            return controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b);
        }
    }
    private void clearStrings() {
        this.opFormat = "";
        this.opString = "";
    }
}
