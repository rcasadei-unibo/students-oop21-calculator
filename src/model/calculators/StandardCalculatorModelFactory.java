package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Type;
//TODO MISSING JAVADOC.
/**
 *
 * 
 *
 */
public final class StandardCalculatorModelFactory {
    private StandardCalculatorModelFactory() {
    }
    /**
     * @return x
     */
    public static CalculatorModel create() {
        final Map<String, CCBinaryOperator> binaryOpMap = Map.of(
                "+", new CCBinaryOperator((n1, n2) -> sum(n1, n2), 2, Type.LEFT),
                "-", new CCBinaryOperator((n1, n2) -> sub(n1, n2), 2, Type.LEFT),
                "×", new CCBinaryOperator((n1, n2) -> mult(n1, n2), 2, Type.LEFT),
                "÷", new CCBinaryOperator((n1, n2) -> div(n1, n2), 3, Type.LEFT),
                "%", new CCBinaryOperator((n1, n2) -> modulo(n1, n2), 3, Type.LEFT)
                          );
        final Map<String, CCUnaryOperator> unaryOpMap = Map.of(
                "1/x", new CCUnaryOperator((n) -> inverse(n), 4, Type.RIGHT),
                "√", new CCUnaryOperator((n) -> root(n), 4, Type.RIGHT),
                "x²", new CCUnaryOperator((n) -> square(n), 4, null)
                  );
        return new CalculatorModelTemplate(binaryOpMap, unaryOpMap);
    }
    private static double sum(final double n1, final double n2) {
        return n1 + n2;
    }
    private static double sub(final double n1, final double n2) {
        return n1 - n2;
    }
    private static double mult(final double n1, final double n2) {
        return n1 * n2;
    }
    private static double div(final double n1, final double n2) {
        return n1 / n2;
    }
    private static double modulo(final double n1, final double n2) {
        return n1 % n2;
    }
    private static double root(final double n1) {
        return Math.sqrt(n1);
    }
    private static double square(final double n1) {
        return (n1) * (n1);
    }
    private static double inverse(final double n1) {
        return 1 / n1;
    }

}
