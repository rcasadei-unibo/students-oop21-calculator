package utils.calculate;

import controller.manager.CCEngine;
import utils.CalcException;

/**
 * parses mathematical expressions.
 *
 */
public interface MathematicalParser {

    /**
     * Sets the engine for obtaining the RPN expression.
     * 
     * @param engine
     */
    void setEngine(CCEngine engine);

    /**
     * @param cond
     */
    void setAreVariablesAllowed(boolean cond);

    /**
     * @param expression
     * @return The root of the AST
     * @throws CalcException
     */
    AbstractSyntaxNode parseToAST(String expression) throws CalcException;

}
