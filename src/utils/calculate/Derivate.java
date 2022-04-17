package utils.calculate;

import java.util.List;

import utils.CalcException;

/**
 * The derivate class.
 *
 */
public class Derivate implements Algorithm {

    @Override
    public void setParameters(final List<String> parameters) {
    }

    @Override
    public String calculate(final Expression expr) throws CalcException {
        final Expression expression = expr;
        final var result = expression.getDerivative().toString();
        expression.setExpr(result);
        return expression.getResult().toString();
    }

    @Override
    public void unsetParameters() {
    }

    @Override
    public List<String> getParameters() {
        return List.of();
    }

}
