package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
//TODO MISSING JAVADOC.
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class StandardCalculatorModel extends CalculatorModel {
    /**
     * Contains the following operations:
     * -sum, subtraction, multiplication, division, modulo.
     * -square root, square, inverse.
     *
     */
    public StandardCalculatorModel() {
       //TODO change operation names'
       //TODO add precedence and Type
       super(Map.of("sum", new CCBinaryOperator((n1, n2) -> sum(n1, n2), 0, null),
                    "sub", new CCBinaryOperator((n1, n2) -> sub(n1, n2), 0, null),
                    "mult", new CCBinaryOperator((n1, n2) -> mult(n1, n2), 0, null),
                    "div", new CCBinaryOperator((n1, n2) -> div(n1, n2), 0, null),
                    "modulo", new CCBinaryOperator((n1, n2) -> modulo(n1, n2), 0, null)),
                     Map.of(
                    "inverse", new CCUnaryOperator((n) -> inverse(n), 0, null),
                    "root", new CCUnaryOperator((n) -> root(n), 0, null),
                    "square", new CCUnaryOperator((n) -> square(n), 0, null)));

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
