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
        super(Map.of(
                "factorial", new CCBinaryOperator((n, m) -> fallingFactorial(n, m), 0, null),
                "binomial coefficient", new CCBinaryOperator((a, b) -> binomialCoefficient(a, b), 0, null),
                "sequencesNumber", new CCBinaryOperator((n, m) -> sequencesNumber(n, m), 0, null),
                "binaryFibonacci", new CCBinaryOperator((n, k) -> binaryFibonacci(n, k), 0, null),
                "Stirling number", new CCBinaryOperator((n, k) -> stirlingNumber(n, k), 0, null)),
                Map.of(
                "fibonacci", new CCUnaryOperator((n) -> fibonacci(n), 0, null),
                "scombussolamento", new CCUnaryOperator((n) -> scombussolamento(n), 0, null),
                "Bell number", new CCUnaryOperator((n) -> bellNumber(n), 0, null)));
    }
    /**
     * 
     * @param n the biggest element of the factorial
     * @param m 
     * @return the falling factorial (n)m
     */
    private static double fallingFactorial(final double n, final double m) {
        if (m > n) {
            return 0;
        }
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
    private static double binomialCoefficient(final double a, final double b) {
        final double b1 = b > (a / 2) ? a - b : b;
        return b1 < 0 ? 0 : fallingFactorial(a, b1) / fallingFactorial(b1, b1);
    }
    /**
     * 
     * @param n the cardinality of the set A = {1,2,...,n}
     * @param m the length of the sequence
     * @return the number of sequences of A of length m
     */
    private static double sequencesNumber(final double n, final double m) {
        return Math.pow(n, m);
    }
    /**
     * 
     * @param n the length of the sequence
     * @param k the number of '1' in the sequence
     * @return the number of sequences of length n with no adjacent '1'(with k '1' in the sequence)
     */
    private static double binaryFibonacci(final double n, final double k) {
        return binomialCoefficient(n - k + 1, k);
    }
    /**
     * 
     * @param n the length of the sequence
     * @return the Fibonacci number of n which equals the number of sequences of length n with no adjacent '1'
     */
    private static double fibonacci(final double n) {
        if (n < 1) {
            return 0;
        }
        if (n < 3) {
            return 1;
        }
        double result = 0.0;
        for (double k = 0; k < n - 2; k++) {
            result += binaryFibonacci(n - 2, k);
        }
        return result;
    }
    /**
     * 
     * @param n n the cardinality of the set A = {1,2,...,n}
     * @return the number of sequences where no element is in its own position
     */
    private static double scombussolamento(final double n) {
        double result = 0;
        for (int k = 0; k <= n; k++) {
            result += Math.pow(-1, k) * fallingFactorial(n, n - k);
        }
        return result;
    }
    /**
     * 
     * @param n the cardinality of the set A
     * @return the number of partitions of the set A which equals Bell(n)
     */
    private static double bellNumber(final double n) {
        if (n == 0) {
            return 1;
        }
        double result = 0;
        for (int i = 0; i < n; i++) {
            result += binomialCoefficient(n - 1, i) * bellNumber(i);
        }
        return result;
    }
    /**
     * 
     * @param n the cardinality of the set A
     * @param k the number of blocks of the partitions
     * @return the number of partitions of the set A in k blocks which equals Stirling(n, k)
     */
    private static double stirlingNumber(final double n, final double k) {
        double result = 0;
        for (double i = 0; i <= k; i++) {
            result += Math.pow(-1, k - i) * Math.pow(i, n) * binomialCoefficient(k, i);
        }
        return result / fallingFactorial(k, k);
    }
}
