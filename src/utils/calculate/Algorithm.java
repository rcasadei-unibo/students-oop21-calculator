package utils.calculate;

import java.util.List;

import controller.manager.CCEngine;
import utils.CalcException;

/**
 * Interface for calculating Limits, Integrals and derivatives.
 *
 * 
 */
public interface Algorithm {

    /**
     * Sets the parameters if needed.
     * @param parameters
     * @throws CalcException 
     */
    void setParameters(List<String> parameters) throws CalcException;
    /**
     * Unsets the parameters if any.
     */
    void unsetParameters();

    /**Return the current Parameters.
     * @return s
     */
    List<String> getParameters();

    /**
     * @param engine
     */
    void setEngine(CCEngine engine);

    /**
     * Calculate the final REsult of the expression.
     * @param expr
     * @return s
     * @throws CalcException 
     */
    String calculate(Expression expr) throws CalcException;

}
