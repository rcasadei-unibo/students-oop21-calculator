package model.calculators;

import java.util.Map;
import java.util.stream.LongStream;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorModel extends CalculatorModel {
    /**
     * ScientificCalculatorModel contains all the operations of StandardCalculatorModel
     * and new operations unique to a scientific calculator.
     *
     */
    private StandardCalculatorModel standard = new StandardCalculatorModel();
    /**
     * 
     */
    public ScientificCalculatorModel() {
        super(Map.of("log", new CCBinaryOperator((n, b) -> Math.log(n) / Math.log(b), 0, null),
                     "nthRoot", new CCBinaryOperator((n, i) -> Math.pow(n, 1 / i), 0, null),
                     "potenza", new CCBinaryOperator((n, e) -> Math.pow(n, e), 0, null)),
              Map.of("ln", new CCUnaryOperator((n) -> Math.log(n), 0, null),
                     "abs", new CCUnaryOperator((n) -> Math.abs(n), 0, null),
                     "factorial", new CCUnaryOperator((n) -> (double) LongStream.rangeClosed(1, Math.round(n)).reduce(1, (long n1, long n2) -> n1 * n2), 0, null),
                     "sin", new CCUnaryOperator((n) -> Math.sin(n), 0, null),
                     "cos", new CCUnaryOperator((n) -> Math.cos(n), 0, null),
                     "tan", new CCUnaryOperator((n) -> Math.tan(n), 0, null),
                     "csc", new CCUnaryOperator((n) -> 1 / Math.sin(n), 0, null),
                     "sec", new CCUnaryOperator((n) -> 1 / Math.cos(n), 0, null),
                     "cot", new CCUnaryOperator((n) -> Math.cos(n) / Math.sin(n), 0, null)));

        this.addOperations(standard.getUnaryOpMap(), standard.getBinaryOpMap());
    }
}
