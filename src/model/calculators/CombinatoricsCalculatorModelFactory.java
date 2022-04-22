package model.calculators;

import java.util.Map;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;

/**
 * 
 * Static Factory that creates the maps with the Combinatorics Operations and puts them in a Calculator Model.
 *
 */
public final class CombinatoricsCalculatorModelFactory {

    private static final double BELL_NUMBER_MAX = 30;

    private CombinatoricsCalculatorModelFactory() {
    }

    private static CCBinaryOperator createBinaryFunction(final BinaryOperator<Double> op) {
        return new CCBinaryOperator(op, 0, null);
    }

    private static CCUnaryOperator createUnaryFunction(final UnaryOperator<Double> op) {
        return new CCUnaryOperator(op, 0, null);
    }

    /**
     * 
     * @return a class(CalculatorModel)that has two maps that contain all the operators of this calculator
     */
    public static CalculatorModel create() {
        final Map<String, CCBinaryOperator> binaryOpMap = Map.of(
                "factorial", createBinaryFunction((n, m) -> fallingFactorial(n, m)),
                "binomialCoefficient", createBinaryFunction((a, b) -> binomialCoefficient(a, b)),
                "sequencesNumber", createBinaryFunction((n, m) -> sequencesNumber(n, m)),
                "binaryFibonacci", createBinaryFunction((n, k) -> binaryFibonacci(n, k)),
                "stirlingNumber", createBinaryFunction((n, k) -> stirlingNumber(n, k)));
        final Map<String, CCUnaryOperator> unaryOpMap = Map.of(
                "fibonacci", createUnaryFunction((n) -> fibonacci(n)),
                "derangement", createUnaryFunction((n) -> derangement(n)),
                "bellNumber", createUnaryFunction((n) -> bellNumber(n)));
        return new CalculatorModelTemplate(binaryOpMap, unaryOpMap);
    }

    /**
     * 
     * @param n the biggest element of the factorial
     * @param m the number of "iterations"
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
     * @param a the upper value
     * @param b the lower value
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
        double result = 0.0;
        if (n <= 3) {
            result++;
        }
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
    private static double derangement(final double n) {
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
        if (n > BELL_NUMBER_MAX) {
            return Double.POSITIVE_INFINITY;
        }
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
        if (k > n) {
            return 0;
        }
        double result = 0;
        for (double i = 0; i <= k; i++) {
            result += Math.pow(-1, k - i) * Math.pow(i, n) * binomialCoefficient(k, i);
        }
        return result / fallingFactorial(k, k);
    }
}
