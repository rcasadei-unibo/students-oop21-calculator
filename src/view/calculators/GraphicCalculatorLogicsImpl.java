package view.calculators;

import java.util.ArrayList;
import java.util.List;

import controller.calculators.CalculatorController;
import utils.calculate.Tokenizer;
/**
 * 
 * 
 *
 */
public class GraphicCalculatorLogicsImpl implements GraphicCalculatorLogics {
    private List<String> eq = new ArrayList<>();
    private final CalculatorController controller;
    /**
     * 
     * @param controller
     */
    public GraphicCalculatorLogicsImpl(final CalculatorController controller) {
        this.controller = controller;
    }
    /**
     * 
     * @param eq
     */
    public void setEquation(final List<String> eq) {
        final Tokenizer tok = new Tokenizer(eq.stream().reduce("", (a, b) -> a + b));
        this.eq = tok.getListSymbol();
    }
    /**
     * @return aa
     */
    public Double getResult() {
        this.controller.getManager().engine().calculate();
        return Double.valueOf(this.controller.getManager().memory().getCurrentState().get(0));
    }
    /**
     * @param value
     * @return aa
     */
    public List<String> replaceXValue(final double value) {
        final List<String> temp = new ArrayList<>();
        List.of(eq.stream().reduce("", (a, b) -> a + b).replace("x", Double.toString(value)).split("")).forEach(e -> temp.add(e));
        return temp;
    }
}
