package model.calculators;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Type;
/**
 * 
 * Static factory for basic operators and scientific operators such as sin, cos, ln, exc...
 *
 */
public final class ScientificCalculatorModelFactory {
    private ScientificCalculatorModelFactory() {
    }
    /**
     * @return a map containing the operator name and a function that applies the before-mentioned operator.
     */
    public static CalculatorModel create() {
        final Map<String, CCBinaryOperator> binaryOpMap = new HashMap<>(Map.of("ʸ√x", new CCBinaryOperator((n, i) -> Math.pow(i, 1 / n), 6, Type.RIGHT),
                     "xʸ", new CCBinaryOperator((n, e) -> Math.pow(n, e), 5, Type.RIGHT)));
        final Map<String, CCUnaryOperator> unaryOpMap = new HashMap<>(Map.of("log", new CCUnaryOperator((n) -> Math.log10(n), 1, null),
                     "ln", new CCUnaryOperator((n) -> Math.log(n), 1, null),
                     "Abs", new CCUnaryOperator((n) -> Math.abs(n), 1, null),
                     "!n", new CCUnaryOperator((n) -> (double) LongStream.rangeClosed(1, Math.round(n)).reduce(1, (long n1, long n2) -> n1 * n2), 1, null),
                     "Sin", new CCUnaryOperator((n) -> Math.sin(n), 1, null),
                     "Cos", new CCUnaryOperator((n) -> Math.cos(n), 1, null),
                     "Tan", new CCUnaryOperator((n) -> Math.tan(n), 1, null),
                     "Csc", new CCUnaryOperator((n) -> 1 / Math.sin(n), 1, null),
                     "Sec", new CCUnaryOperator((n) -> 1 / Math.cos(n), 1, null),
                     "Cot", new CCUnaryOperator((n) -> Math.cos(n) / Math.sin(n), 1, null)));
        binaryOpMap.putAll(StandardCalculatorModelFactory.create().getBinaryOpMap());
        unaryOpMap.putAll(StandardCalculatorModelFactory.create().getUnaryOpMap());
        return new CalculatorModelTemplate(binaryOpMap, unaryOpMap);
    }
}
