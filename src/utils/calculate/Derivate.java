package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;

import utils.CalcException;

/**
 * @author pesic
 *
 */
public class Derivate implements Algorithm{

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

}
