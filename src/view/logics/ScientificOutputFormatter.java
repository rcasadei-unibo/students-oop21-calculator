package view.logics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.calculators.CalculatorController;
import model.calculators.ScientificCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import view.components.CCDisplay;

public class ScientificOutputFormatter extends StandardOutputFormatter{
    private final CalculatorController controller = Calculator.SCIENTIFIC.getController();
    private final CCDisplay display;
    {
        this.setAppearanceMap();
    }
    /**
     * 
     * @param display
     */
    public ScientificOutputFormatter(CCDisplay display) {
        super(display);
        this.display = display;
    }
    /**
     * This will update the display using the format() method's output.
     */
    @Override
    public void updateDisplay() {
        this.display.updateText(this.format());
    }
    @Override
    public void updateDisplayUpperText() {
        this.display.updateUpperText(this.format().concat(" ="));
    }
    @Override
    public String format() {
        final List<String> output = this.replaceOp();
        return this.getString(output);
    }
    private List<String> replaceOp() {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        return state.stream().map((str) -> {
                if (controller.isBinaryOperator(str) || controller.isUnaryOperator(str)) {
                    return appearanceMap.get(str);
                }
                return str;
        }).collect(Collectors.toList());
    }
    private String getString(final List<String> input) {
        return input.stream().reduce("", (a, b) -> a + b);
    }
    private void setAppearanceMap() {
        ScientificCalculatorModelFactory.create().getUnaryOpMap().forEach((str, op) -> {
            switch (str) {
                case "factorial":
                    appearanceMap.put(str, "!");
                    break;
                default:
                    break;
            }
        });
        ScientificCalculatorModelFactory.create().getBinaryOpMap().forEach((str, op) -> {
            switch (str) {
                case "test":
                    break;
                default:
                    appearanceMap.put(str, str);
                    break;
            }
        });
    }

}
