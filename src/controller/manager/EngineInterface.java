package controller.manager;

import java.util.List;

import utils.CalcException;

/**
 * 
 */
public interface EngineInterface {

    /**
     * Calculates a given input expression (a list of strings in infix notation) and returns the result.
     * 
     * @param input List of strings representing the expression in infix notation.
     * @return result Double value resulted from the calculation.
     * @throws CalcException 
     */
    double calculate(List<String> input) throws CalcException;

    /**
     * Parses an expression in infix notation, stored as a list of strings, to an equivalent expression in reverse polish notation.
     * 
     * @param infix List of strings representing the expression in infix notation to parse.
     * @return List of strings representing the reverse polish notation of the input
     * @throws CalcException
     */
    List<String> parseToRPN(List<String> infix) throws CalcException;

    /**
     * TODO: javadoc.
     * 
     * @param input
     * @return string
     * @throws CalcException
     */
    String calculateAndFormat(List<String> input) throws CalcException;

}
