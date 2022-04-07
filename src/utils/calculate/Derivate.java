package utils.calculate;

import java.util.List;

/**
 * @author pesic
 *
 */
public class Derivate implements Algorithm<String> {

    @Override
    public void setParameters(final List<String> parameters) {
    }

    @Override
    public String calculate(final Expression expr) {
        final Expression expression = expr;
        final var result = expression.getDerivative().toString();
        expression.setExpr(result);
        return expression.getResult().toString();
    }
}
