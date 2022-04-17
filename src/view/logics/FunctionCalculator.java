package view.logics;

import java.util.List;

import utils.CalcException;
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
