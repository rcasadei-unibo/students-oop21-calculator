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
    private List<String> eq = new ArrayList<>();
    private final CalculatorController controller;
    private List<Double> results;
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
        final Tokenizer tok = new Tokenizer(eq);
        this.eq = tok.getListSymbol();
        this.replaceXValue();
    }
    /**
     * 
     * 
     */
    private void replaceXValue() {
        int x = Math.negateExact(FunctionGrapher.LIMIT);
        this.results = new ArrayList<>();
        String temp;
        while (x <= FunctionGrapher.LIMIT) {
            temp = eq.stream().reduce("", (a, b) -> a + b).replace("x", Double.toString(x));
            controller.getManager().memory().read(temp);
            this.addResult();
            x += FunctionGrapher.PRECISION;
        }
    }
    /**
    *
    */
   private void addResult() {
       this.controller.getManager().engine().calculate();
       results.add(Double.valueOf(this.controller.getManager().memory().getCurrentState().get(0)));
       this.controller.getManager().memory().clear();
   }
   /**
    * @param value
    * @return aaaaaaaa
    */
    public List<Double> getResult(final double value) {
        return results;
    }
}
