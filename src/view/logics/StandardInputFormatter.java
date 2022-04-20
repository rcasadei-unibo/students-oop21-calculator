package view.logics;

import java.util.List;

import controller.calculators.CalculatorController;
import model.manager.EngineModelInterface.Calculator;
import java.util.ArrayList;
/**
 * This class handles the user's input to be processed.
 */
public class StandardInputFormatter implements InputFormatterLogics {
    private final CalculatorController controller = Calculator.STANDARD.getController();
    @Override
    public void read(final String input) {
        /*se il numero premuto prima è un numero allora l'operatore unario wrappa il contenuto con il proprio unario
         * 1+2 => 1+(1/(2))       1+2 => 1+((2)^2)
         * se il numero premuto prima non è un numero allora l'operatore unario viene concatenato con una parentesi
         * 1+ => 1+(1/(1))        1+ => 1+(0)^2
         */
        if (controller.isUnaryOperator(input)) {
            if (this.isPreviousValueANumber()) {
                this.wrapNumberInOperator(input);
            } else {
                this.readInvalidOperand(input);
            }
        } else {
            controller.getManager().memory().read(input);
        }
    }
    private void wrapNumberInOperator(final String input) {
        
    }
    private void readInvalidOperand(final String input) {

        switch (input) {
            case "square":
                controller.getManager().memory().read("(");
                controller.getManager().memory().read("0");
                controller.getManager().memory().read(")");
                controller.getManager().memory().read(input);
                break;
            case "1/x":
                controller.getManager().memory().read("(");
                controller.getManager().memory().read(input);
                controller.getManager().memory().read("1");
                controller.getManager().memory().read(")");
                break;
            case "√":
                controller.getManager().memory().read("(");
                controller.getManager().memory().read(input);
                controller.getManager().memory().read("0");
                controller.getManager().memory().read(")");
                break;
            default :
                break;
        }

    }
    private boolean isPreviousValueANumber() {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        try {
            Double.parseDouble(!state.isEmpty() ? state.get(state.size() - 1) : "X"); //1 significa che lo state
            return true;                                                              //è vuoto e si può aggiungere
        } catch (NumberFormatException e) {                                           //un unario correttamente
            return false;
        }
    }
    @Override
    public void deleteLast() {
        controller.getManager().memory().deleteLast();
    }
    @Override
    public void calculate() {
        controller.getManager().engine().calculate();
    }
}
