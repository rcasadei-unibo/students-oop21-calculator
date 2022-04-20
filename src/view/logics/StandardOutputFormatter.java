package view.logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.calculators.CalculatorController;
import model.calculators.StandardCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import view.components.CCDisplay;
/**
 * This class handles the display's output.
 */
public class StandardOutputFormatter implements OutputFormatterLogics {
    private final CalculatorController controller = Calculator.STANDARD.getController();
    private final CCDisplay display;
    /**
     * StandardCalculator's appearance map.
     */
    protected final Map<String, String> appearanceMap = new HashMap<>();
    {
        this.setAppearanceMap();
    }
    /**
     * 
     * @param display
     */
    public StandardOutputFormatter(final CCDisplay display) {
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
        StandardCalculatorModelFactory.create().getUnaryOpMap().forEach((str, op) -> {
            switch (str) {
                case "x²":
                    appearanceMap.put(str, "²");
                    break;
                case "1/x":
                    appearanceMap.put(str, "1/");
                    break;
                default:
                    appearanceMap.put(str, str);
                    break;
            }
        });
        StandardCalculatorModelFactory.create().getBinaryOpMap().forEach((str, op) -> {
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
