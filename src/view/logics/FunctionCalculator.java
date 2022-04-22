package view.logics;

import java.util.List;
/**
 * 
 * FunctionCalculator serves the purpose to take a F(x) function and 
 * calculate the results of the substitution of all the "x" with different values between a certain range.
 * Then the results can be obtained by calling the method getResults.
 *
 */
public interface FunctionCalculator {
    /**
     * 
     * @param eq is the equation which will be calculated and then the results will be put in a List of Doubles.
     * Calling this method will clear the list containig the previous results.
     * 
     */
    void calculate(String eq);
    /**
     * 
     * @return a the list containing the results (in order).
     * @throws CalcException
     */
    List<Double> getResults();
}
