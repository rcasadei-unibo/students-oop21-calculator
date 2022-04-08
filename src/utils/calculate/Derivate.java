package utils.calculate;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author pesic
 *
 */
public class Derivate implements Algorithm{

    @Override
    public String setParameters(final List<String> parameters) {
        return parameters.stream().reduce("", (res, s) -> res + s);
    }

    @Override
    public String calculate(final Expression expr) {
        final Expression expression = expr;
        final var result = expression.getDerivative().toString();
        expression.setExpr(result);
        return expression.getResult().toString();
    }

    @Override
    public void unsetParameters() {
    }

}
