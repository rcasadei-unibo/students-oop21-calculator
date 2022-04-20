package view.logics;

import controller.calculators.CalculatorController;
import model.manager.EngineModelInterface.Calculator;

public class ScientificInputFormatter implements InputFormatterLogics {
    private final CalculatorController controller = Calculator.SCIENTIFIC.getController();
    
    @Override
    public void read(String input) {
        controller.getManager().memory().read(input);
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
