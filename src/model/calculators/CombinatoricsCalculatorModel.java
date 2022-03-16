package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorModel extends CalculatorModel {
    /**
     * 
     */
    public CombinatoricsCalculatorModel() {
        this.binaryOpMap = Map.of(
                "factorial", new CCBinaryOperator((n, m) -> this.fallingFactorial(n, m), 0, null),
                "binomial coefficient", new CCBinaryOperator((a, b) -> this.binomialCoefficient(a, b), 0, null));
        this.unaryOpMap = Map.of("", new CCUnaryOperator(null, 0, null));
    }
    /**
     * 
     * @param n
     * @param m
     * @return the falling factorial (n)m
     */
    private double fallingFactorial(final double n, final double m) {
        double result = 1;
        for (int i = 0; i < m; i++) {
            result *= n - i;
        }
        return result;
    }
    /**
     * 
     * @param a
     * @param b
     * @return the binomial coefficient a choose b
     */
    private double binomialCoefficient(final double a, final double b) {
        final double b1 = b > (a / 2) ? a - b : b;
        return b1 < 0 ? 0 : this.fallingFactorial(a, b1) / this.fallingFactorial(b1, b1);
    }
}
