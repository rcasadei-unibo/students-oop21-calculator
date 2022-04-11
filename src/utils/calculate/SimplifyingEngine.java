package utils.calculate;

import java.util.function.Predicate;

import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;
import utils.tokens.TokensFactory;


/**
 * @author pesic
 *
 */
public class SimplifyingEngine {
    
    
    public AbstractSyntaxNode binaryOperator( final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        final SpecialToken<Operator> operator = (SpecialToken<Operator>) t;
        switch (operator.getSymbol()) {
        case "+":
            return simplifySumOperation(t, left, right);
        case "*":
            return simplifyProductOperation(t, left, right);
        case "-":
            return simplifySubtractionOperation(t, left, right);
        case "/":
            return simplifyDivisionOperation(t, left, right);
        default:
            return new AbstractSyntaxNode(t, left, right);
        }
    }

    private boolean verifyNumberValue(final AbstractSyntaxNode source, final Predicate<SpecialToken<Double>> filter) {
        if (source.getToken().getTypeToken().equals(TokenType.NUMBER)) {
            @SuppressWarnings("unchecked")
            final SpecialToken<Double> num = (SpecialToken<Double>) source.getToken();
            return filter.test(num);
        }
        return false;
    }

    private AbstractSyntaxNode simplifySumOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        boolean flag = false;
        if (verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0) && !flag) {
            flag = true;
            return right;
        } else if (verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0) && !flag) {
            return left;
        }

        return new AbstractSyntaxNode(t, left, right);
    }

    private AbstractSyntaxNode simplifyProductOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        boolean flag = false;
        if (verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0) && !flag) {
            flag = true;
            return new AbstractSyntaxNode(TokensFactory.numberToken(0.0));
        } else if (verifyNumberValue(left, (num) -> num.getObjectToken() == 1.0) && !flag) {
            flag = true;
            return right;
        } else if (verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0) && !flag) {
            flag = true;
            return new AbstractSyntaxNode(TokensFactory.numberToken(0.0));
        } else if (verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0) && !flag) {
            return left;
        }

        return new AbstractSyntaxNode(t, left, right);
    }

    private AbstractSyntaxNode simplifySubtractionOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        boolean flag = false;
        if (verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0) && !flag) {
            flag = true;
            return new AbstractSyntaxNode(TokensFactory.operatorToken(Operator.getOperatorBySymbolAndArgs("-", 1)), right);

        } else if (verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0) && !flag) {
            return left;
        }
        return new AbstractSyntaxNode(t, left, right);
    }

    private AbstractSyntaxNode simplifyDivisionOperation(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        if (verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0)) {
            return left;
        }
        return new AbstractSyntaxNode(t, left, right);
    }

}
