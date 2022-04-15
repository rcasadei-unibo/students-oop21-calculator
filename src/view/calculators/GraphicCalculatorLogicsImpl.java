package view.calculators;

import java.util.ArrayList;
import java.util.List;

import controller.calculators.CalculatorController;
import utils.calculate.Tokenizer;
import view.components.FunctionGrapher;
/**
 * 
 * 
 *
 */
public class GraphicCalculatorLogicsImpl implements GraphicCalculatorLogics {
    private final CalculatorController controller;
    private final List<Double> results = new ArrayList<>();
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
    public void setEquation(final String eq) {
        results.clear();
        double x = -FunctionGrapher.LIMIT;
        List<String> temp;
        while (x <= FunctionGrapher.LIMIT) {
            final Tokenizer tok = new Tokenizer(eq.replace("x", "(" + Double.toString(x) + ")"));
            temp = tok.getListSymbol();
            for (final var s : temp) {
                controller.getManager().memory().read(s);
            }
            this.controller.getManager().engine().calculate();
            results.add(Double.valueOf(this.controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b)));
            this.controller.getManager().memory().clear();
            x += FunctionGrapher.PRECISION;
        }
    }

    /**
     * @return a List<Double> containing the all the y associated with Precision
     */
    public List<Double> getResult() {
        return this.results;
    }
}
