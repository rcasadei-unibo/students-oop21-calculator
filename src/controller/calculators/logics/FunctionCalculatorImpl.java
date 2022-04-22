package controller.calculators.logics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.calculators.CalculatorController;
import utils.calculate.Tokenizer;
/**
 * 
 * FunctionCalculator serves the purpose to take a F(x) function and 
 * track the results of the "x" substitution from a certain range of values.
 *
 */
public class FunctionCalculatorImpl implements FunctionCalculator {
    /**
     * PRECISION is the value the method "calculate" add to the value x everytime 
     * it calculates the equation.
     */
    public static final double PRECISION = 0.01;
    /**
     * RANGE is the limit from which we take the lesser x and the greater x.
     */
    public static final double RANGE = 100;
    private final CalculatorController controller;
    private final List<Double> results;
    /**
     * 
     * @param controller
     */
    public FunctionCalculatorImpl(final CalculatorController controller) {
        this.controller = controller;
        this.results = new ArrayList<>();
    }
    /**
     * 
     * @param eq the input string representing a function F(x)
     * 
     */
    public void calculate(final String eq) {
        this.results.clear();
        double x = -RANGE;
        List<String> temp;
        while (x <= RANGE) {
            temp = this.replace(eq, x);
            for (final var s: temp) {
                    this.controller.getManager().memory().read(s);
                }
            this.controller.getManager().engine().calculate();
            try {
                this.results.add(Double.valueOf(this.controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b)));
            } catch (IllegalArgumentException e) {
                x = RANGE;
                results.clear();
            }
            this.controller.getManager().memory().clear();
            x += FunctionCalculatorImpl.PRECISION;
        }
    }

    private List<String> replace(final String eq, final double value) {
        final Tokenizer tok = new Tokenizer(eq.replace("x", "(" + Double.toString(value) + ")"));
        try {
            return tok.getListSymbol();
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }
    /**
     * 
     * @return a list containing the results (in order)
     * @throws CalcException 
     */
    public List<Double> getResults() {
        return this.results;
    }
}
