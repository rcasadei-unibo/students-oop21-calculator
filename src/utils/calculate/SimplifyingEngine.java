package utils.calculate;

import utils.SimplificationFactory;
import utils.tokens.SpecialToken;
import utils.tokens.Token;


/**
 * simplifies Expression during the parsing of the AS (e.g. 5 + 0 => 5)
 *
 */
public class SimplifyingEngine implements Simplifier {

    /**
     * every binary operator has a simplification process.
     * @param t
     * @param left
     * @param right
     * @return the simplified version of the binary operator
     */
    @SuppressWarnings("unchecked")
    public AbstractSyntaxNode binaryOperator(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        final SpecialToken<Operator> operator = (SpecialToken<Operator>) t;
        switch (operator.getSymbol()) {
        case "+":
            return simplifySumOperation(t, left, right);
        case "*":
        case "\u00D7":
            return simplifyProductOperation(t, left, right);
        case "-":
            return simplifySubtractionOperation(t, left, right);
        case "÷":
        case "/":
            return simplifyDivisionOperation(t, left, right);
        default:
            return new AbstractSyntaxNode(t, left, right);
        }
    }

    private AbstractSyntaxNode simplifySumOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new SimplificationFactory().sumSimplification(t, left, right).handle();
    }

    private AbstractSyntaxNode simplifyProductOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new SimplificationFactory().mulSimplification(t, left, right).handle();
    }

    private AbstractSyntaxNode simplifySubtractionOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new SimplificationFactory().subSimplification(t, left, right).handle();
    }

    private AbstractSyntaxNode simplifyDivisionOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
      return new SimplificationFactory().divSimplification(t, left, right).handle();
    }

}
