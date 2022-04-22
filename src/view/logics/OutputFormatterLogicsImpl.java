package view.logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import controller.calculators.CalculatorController;
import model.calculators.ScientificCalculatorModelFactory;
import view.components.CCDisplay;
/**
 * 
 *
 */
public class OutputFormatterLogicsImpl implements OutputFormatterLogics {
    private final Map<String, String> appearanceMap = new HashMap<>();
    private final CalculatorController controller;
    private final CCDisplay display;
    /**
     * 
     * @param controller
     * @param display
     * 
     */
    public OutputFormatterLogicsImpl(final CalculatorController controller, final CCDisplay display) {
        this.setAppearanceMap();
        this.controller = controller;
        this.display = display;
    }

    @Override
    public void updateDisplay() {
        this.display.updateText(this.format());
    }

    @Override
    public String format() {
        final List<String> output = this.replaceOp();
        return this.getStringOf(output);
    }

    @Override
    public void updateDisplayUpperText() {
        if (!this.format().isEmpty()) {
            this.display.updateUpperText(this.format().concat(" ="));
        }
    }

    @Override
    public void addResult(final String history) {
        if (this.checkForError(history)) {
            this.controller.getManager().memory().addResult(history.concat(" = ").concat(this.format()));
        }
    }

    private void setAppearanceMap() {
        ScientificCalculatorModelFactory.create().getUnaryOpMap().forEach((str, op) -> {
            switch (str) {
            case "x²":
                appearanceMap.put(str, "²");
                break;
            case "1/x":
                appearanceMap.put(str, "1/");
                break;
            case "factorial":
                appearanceMap.put(str, "!");
                break;
            default:
                appearanceMap.put(str, str);
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

    private List<String> replaceOp() {
        final List<String> state = new ArrayList<>(controller.getManager().memory().getCurrentState());
        return state.stream().map((str) -> {
                if (this.controller.isBinaryOperator(str) || this.controller.isUnaryOperator(str)) {
                    return appearanceMap.get(str);
                }
                return str;
        }).collect(Collectors.toList());
    }

    private String getStringOf(final List<String> input) {
        return input.stream().reduce("", (a, b) -> a + b);
    }
}
