package utils.calculate;

import java.util.LinkedList;
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

    /**
     * @param param
     * @return if the unaryyminus is encountered 
     */
    default String preprocessParameter(final String param) {
        final var tok = new Tokenizer(param);
        final List<String> l = new LinkedList<>();
        tok.getListSymbol().forEach(s -> {
            if ("-".equals(s) && (l.isEmpty() || "(".equals(l.get(l.size() - 1)))) {
                l.add("0.0");
            }
            l.add(s);
        });
        return l.stream().reduce("", (o1, o2) -> o1 + o2);
    }

}
