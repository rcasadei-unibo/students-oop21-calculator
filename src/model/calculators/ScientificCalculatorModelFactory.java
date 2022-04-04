package model.calculators;

import java.util.Map;
import java.util.stream.LongStream;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Type;

/**
 * 
 * MISSING JAVADOC.
 *
 */

public final class ScientificCalculatorModelFactory {
    /**
     * ScientificCalculatorModel contains all the operations of StandardCalculatorModel
     * and new operations unique to a scientific calculator.
     *
     */
    private ScientificCalculatorModelFactory() {
    }
    /**
     * @return CalculatorModelTemplate
     */
    public static CalculatorModel create() {
        final Map<String, CCBinaryOperator> binaryOpMap = Map.of("log", new CCBinaryOperator((n, b) -> Math.log(n) / Math.log(b), 4, null),
                     "nthRoot", new CCBinaryOperator((n, i) -> Math.pow(n, 1 / i), 6, Type.RIGHT),
                     "potenza", new CCBinaryOperator((n, e) -> Math.pow(n, e), 5, Type.RIGHT));
        final Map<String, CCUnaryOperator> unaryOpMap = Map.of("ln", new CCUnaryOperator((n) -> Math.log(n), 1, null),
                     "abs", new CCUnaryOperator((n) -> Math.abs(n), 1, null),
                     "factorial", new CCUnaryOperator((n) -> (double) LongStream.rangeClosed(1, Math.round(n)).reduce(1, (long n1, long n2) -> n1 * n2), 1, null),
                     "sin", new CCUnaryOperator((n) -> Math.sin(n), 1, null),
                     "cos", new CCUnaryOperator((n) -> Math.cos(n), 1, null),
                     "tan", new CCUnaryOperator((n) -> Math.tan(n), 1, null),
                     "csc", new CCUnaryOperator((n) -> 1 / Math.sin(n), 1, null),
                     "sec", new CCUnaryOperator((n) -> 1 / Math.cos(n), 1, null),
                     "cot", new CCUnaryOperator((n) -> Math.cos(n) / Math.sin(n), 1, null));
        binaryOpMap.putAll(StandardCalculatorModelFactory.create().getBinaryOpMap());
        unaryOpMap.putAll(StandardCalculatorModelFactory.create().getUnaryOpMap());

        return new CalculatorModelTemplate(binaryOpMap, unaryOpMap);
    }
}
