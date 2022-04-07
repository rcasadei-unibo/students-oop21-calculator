package utils.calculate;

import java.util.List;

/**
 * @author pesic
 *
 * @param <O>
 */
public interface Algorithm<O> {

    /**
     * @param parameters
     */
    void setParameters(List<String> parameters);

    /**
     * @param expr
     * @return c
     */
    O calculate(Expression expr);
}
