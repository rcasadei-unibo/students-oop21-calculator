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
        this.unaryOpMap.putAll(Map.of("inverse", new CCUnaryOperator((n) -> this.inverse(n), 0, null),
                                      "root", new CCUnaryOperator((n) -> this.root(n), 0, null),
                                      "square", new CCUnaryOperator((n) -> this.square(n), 0, null)
                ));
        //TODO add precedence and Type
        this.binaryOpMap.putAll(Map.of("sum", new CCBinaryOperator((n1, n2) -> this.sum(n1, n2), 0, null),
                                       "sub", new CCBinaryOperator((n1, n2) -> this.sub(n1, n2), 0, null),
                                       "mult", new CCBinaryOperator((n1, n2) -> this.mult(n1, n2), 0, null),
                                       "div", new CCBinaryOperator((n1, n2) -> this.div(n1, n2), 0, null),
                                       "modulo", new CCBinaryOperator((n1, n2) -> this.modulo(n1, n2), 0, null)
                                       ));
    }
    private double sum(final double n1, final double n2) {
        return n1 + n2;
    }
    private double sub(final double n1, final double n2) {
        return n1 - n2;
    }
    private double mult(final double n1, final double n2) {
        return n1 * n2;
    }
    private double div(final double n1, final double n2) {
        return n1 / n2;
    }
    private double modulo(final double n1, final double n2) {
        return n1 % n2;
    }
    private double root(final double n1) {
        return Math.sqrt(n1);
    }
    private double square(final double n1) {
        return (n1) * (n1);
    }
    private double inverse(final double n1) {
        return 1 / n1;
    }
}
