package view.logics;

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
public class StandardOutputFormatter implements OutputFormatter {
    private final CalculatorController controller = Calculator.STANDARD.getController();
    private final CCDisplay display;
    private boolean validSyntax = true;
    private final Map<String, String> appearanceMap = new HashMap<>();
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
    public String format() {
        final List<String> output = this.replaceBinaryOp();
        
        //IDEA :
        /*se il numero premuto prima è un numero allora l'operatore unario wrappa il contenuto con il proprio unario
         * 1+2 => 1/(1+2)       1+2 => (1+2)^2
         * 
         * se il numero premuto prima non è un numero allora l'operatore unario viene concatenato con una parentesi
         * 1+ => 1+(1/(  ))        1+ => 1+( )^2
         * 
         */
        
        return this.validSyntax ? this.getString(output) : "Syntax error";
    }
    private List<String> replaceBinaryOp() {
        return controller.getManager().memory().getCurrentState().stream().map((str) -> {
            if (this.controller.isBinaryOperator(str)) {
                return this.appearanceMap.get(str);
            } else {
                return str;
            }
        }).collect(Collectors.toList());
    }
    private String getString(final List<String> input) {
        return input.stream().reduce("", (a, b) -> a + b);
    }
    /**
     * @return whether or not there has been an invalid syntax.
     */
    public boolean isValidSyntax() {
        return this.validSyntax;
    }
    private void setAppearanceMap() {
        StandardCalculatorModelFactory.create().getUnaryOpMap().forEach((str, op) -> {
            switch (str) {
                case "square":
                    appearanceMap.put(str, "^2");
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
