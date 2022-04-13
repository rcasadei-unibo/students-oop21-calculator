package utils.calculate;

import java.util.List;

import utils.CalcException;

/**
 * @author pesic
 *
 * 
 */
public interface Algorithm {

    /**
     * @param parameters
     * @throws CalcException 
     */
    void setParameters(List<String> parameters) throws CalcException;
    /**
     * 
     */
    void unsetParameters();
    
    /**
     * @return s
     */
    List<String> getParameters();

    /**
     * @param expr
     * @return s
     * @throws CalcException 
     */
    String calculate(Expression expr) throws CalcException;

}
