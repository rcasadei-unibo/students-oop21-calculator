package controller.manager;

import java.util.List;

import utils.CalcException;

/**
 * Interface for an engine.
 */
public interface EngineInterface {

    /**
     * Calculates a given input expression (a list of strings in infix notation) and returns the result.
     * 
     * @param input List of strings representing the expression in infix notation.
     * @return Double value resulted from the calculation.
     * @throws CalcException Exception thrown when a syntax error in the expression is found.
     */
    double calculate(List<String> input) throws CalcException;

    /**
     * Parses an expression in infix notation, stored as a list of strings, to an equivalent expression in reverse polish notation.
     * 
     * @param infix List of strings representing the expression in infix notation to parse.
     * @return List of strings representing the reverse polish notation of the input
     * @throws CalcException If there is a parenthesis mismatch.
     */
    List<String> parseToRPN(List<String> infix) throws CalcException;

    /**
     * Calculates a given input expression (a list of strings in infix notation) and returns the formatted result.
     * 
     * @param input List of strings representing the expression in infix notation.
     * @return Formatted string representation of the result of the calculation. 
     * @throws CalcException Exception thrown when a syntax error in the expression is found.
     */
    String calculateAndFormat(List<String> input) throws CalcException;

}
