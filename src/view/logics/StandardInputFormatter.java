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
        /*se il bottone premuto prima è un numero allora l'operatore unario wrappa il contenuto con il proprio unario
         * 1+2 => 1+(1/(2))       1+2 => 1+((2)^2)
         * se il bottone premuto prima non è un numero allora l'operatore unario viene concatenato con una parentesi
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
        this.checkUselessParenthesys();
    }
    /**
     * This methods removes any sequential ["(",")"].
     */
    private void checkUselessParenthesys() {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        for (int i = 0; i < state.size(); i++) {
            if (i != state.size() - 1) {
                if ("(".equals(state.get(i)) && ")".equals(state.get(i + 1))) {
                    state.remove(i);
                    state.remove(i);
                }
            }
        }
        controller.getManager().memory().clear();
        controller.getManager().memory().readAll(state);
    }
    private void wrapNumberInOperator(final String op) {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        int index = state.size() - 1;
        //itera finchè non trova un operatore
        while (index >= 0) {
            if (!this.isNumber(index)) {
                index++;
                break;
            }
            index--;
        }
        //piccolo fix sul indice
        index = index == -1 ? 0 : index;
        state.add(index, "(");
        //il "²" va messo dopo la parentesi
        if ("x²".equals(op)) {
            state.add(")");
            state.add(op);
        } else {
            state.add(index, op);
            state.add(")");
        }
        controller.getManager().memory().clear();
        controller.getManager().memory().readAll(state);
    }
    /**
     * This method handles unary invalid Operands.
     * @param op
     */
    private void readInvalidOperand(final String op) {
        switch (op) {
            case "x²":
                //fa legger al manager (0)²
                controller.getManager().memory().read("(");
                controller.getManager().memory().read("0");
                controller.getManager().memory().read(")");
                controller.getManager().memory().read(op);
                break;
            case "1/x":
              //fa legger al manager (1/1)
                controller.getManager().memory().read("(");
                controller.getManager().memory().read(op);
                controller.getManager().memory().read("1");
                controller.getManager().memory().read(")");
                break;
            case "√":
              //fa legger al manager √(0)
                controller.getManager().memory().read("(");
                controller.getManager().memory().read(op);
                controller.getManager().memory().read("0");
                controller.getManager().memory().read(")");
                break;
            default :
                break;
        }

    }
    private boolean isPreviousValueANumber() {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        return this.isNumber(state.size() - 1);
    }
    private boolean isNumber(final int i) {
        final List<String> state = controller.getManager().memory().getCurrentState();
        /*
         * Qui provo un parseDouble di tutti i valori che possono essere nello state
         * I numeri passano e ritornano true, Operatori e parentesi invece generano un 
         * eccezione NumberFormatException
         */
        try {
            if (state.isEmpty()) { //se lo state è vuoto allora devo ritornare che prima non c'è un numero
                return false;
            }
            if (".".equals(state.get(i)) || ")".equals(state.get(i))) {
               return true; 
            }
            Double.parseDouble(state.get(i));
            return true; 
        } catch (NumberFormatException e) {
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
