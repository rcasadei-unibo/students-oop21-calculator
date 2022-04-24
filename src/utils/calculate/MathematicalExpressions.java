package utils.calculate;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.ast.Operation;

/**
 *Expression class is used for calculating he result of an mathematical expression.
 *
 */
public interface MathematicalExpressions {

    /**
     * sets the expression.
     * @param expr
     */
    void setExpr(String expr);

    /**
     * Sets he engine for parsing the string.
     * @param engine
     */
    void setEngine(CCEngine engine);
    /**
     * Calculates the result of expression.
     * @return result
     * @throws CalcException
     */
    Operation getResult() throws CalcException;

    /**
     * @return once calculated an expression we can get back the derivate of it
     * @throws CalcException
     */
    Operation getDerivative() throws CalcException;

}
