package utils.calculate;

import java.util.List;

/**
 * @author pesic
 *
 * 
 */
public interface Algorithm {

    /**
     * @param parameters
     */
    String setParameters(List<String> parameters);
    
    void unsetParameters();

    /**
     * @param expr
     * @return s
     */
    String calculate(Expression expr);

}
