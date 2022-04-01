package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.Type;
//TODO MISSING JAVADOC.
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class StandardCalculatorModel extends CalculatorModelTemplate {
    /**
     * Contains the following operations:
     * -sum, subtraction, multiplication, division, modulo.
     * -square root, square, inverse.
     *
     */
    public StandardCalculatorModel() {
        super(Map.of("sum", new CCBinaryOperator((n1, n2) -> sum(n1, n2), 1, Type.LEFT),
                     "sub", new CCBinaryOperator((n1, n2) -> sub(n1, n2), 1, Type.LEFT),
                     "mult", new CCBinaryOperator((n1, n2) -> mult(n1, n2), 2, Type.LEFT),
                     "div", new CCBinaryOperator((n1, n2) -> div(n1, n2), 3, Type.LEFT),
                     "modulo", new CCBinaryOperator((n1, n2) -> modulo(n1, n2), 3, Type.LEFT)
                               ),
               Map.of("inverse", new CCUnaryOperator((n) -> inverse(n), 1, null),
                      "root", new CCUnaryOperator((n) -> root(n), 1, null),
                      "square", new CCUnaryOperator((n) -> square(n), 1, null)
                        ));
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
