package utils;

import java.util.List;
/**
 * 
 * 
 *
 */
public interface FunctionCalculator {
    /**
     * 
     * @param eq the String equation to be calculated
     */
    void calculate(String eq);
    /**
     * 
     * @return a list containing the results (in order)
     * @throws CalcException
     */
    List<Double> getResults();
}
