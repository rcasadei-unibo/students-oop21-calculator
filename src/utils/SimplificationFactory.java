package utils;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import utils.calculate.AbstractSyntaxNode;
import utils.calculate.Operator;
import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;
import utils.tokens.TokensFactory;

/**
 * @author pesic
 *
 */
public class SimplificationFactory {

    /**
     * @author pesic
     *
     */
    public class Handler {
        private final Token t;
        private final BiPredicate<AbstractSyntaxNode, AbstractSyntaxNode> filter;
        private final Supplier<AbstractSyntaxNode> supplier;
        private final AbstractSyntaxNode left;
        private final AbstractSyntaxNode right;
        private final Handler next;

        /**
         * @param t
         * @param left
         * @param right
         * @param filter
         * @param supplier
         * @param next
         */
        public Handler(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right, 
                final BiPredicate<AbstractSyntaxNode, AbstractSyntaxNode> filter, final Supplier<AbstractSyntaxNode> supplier, final Handler next){
            this.t = t;
            this.left = left;
            this.right = right;
            this.filter = filter;
            this.supplier = supplier;
            this.next = next;
        }
        /**
         * @return c
         */
        public AbstractSyntaxNode handle() {
            if (this.filter.test(left, right)) {
                return supplier.get();
            } else {
                return this.nextHandler();
            }
        }

        private AbstractSyntaxNode nextHandler() {
            if (next == null) {
                return new Handler(t, left, right, (l, r) -> true, () -> new AbstractSyntaxNode(t, left, right), null).handle();
            }
            return next.handle();
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
    
    /**
     * @param t
     * @param left
     * @param right
     * @return c
     */
    public Handler sumSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0), () -> right,
               new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0), () -> left,
               new Handler(t, left, right, (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER) && r.getToken().getTypeToken().equals(TokenType.NUMBER),
               () -> new AbstractSyntaxNode(TokensFactory.numberToken(
                       Double.parseDouble(left.getToken().getSymbol()) + Double.parseDouble(right.getToken().getSymbol()))), null)));
    }

    /**
     * @param t
     * @param left
     * @param right
     * @return c
     */
    public Handler subSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0), 
                () -> new AbstractSyntaxNode(TokensFactory.operatorToken(Operator.getOperatorBySymbolAndArgs("-", 1)), right),
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0), () -> left, 
                new Handler(t, left, right, (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER) && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                () -> new AbstractSyntaxNode(TokensFactory.numberToken(
                Double.parseDouble(left.getToken().getSymbol()) - Double.parseDouble(right.getToken().getSymbol()))), null)));
    }
    
    /**
     * @param t
     * @param left
     * @param right
     * @return c
     */
    public Handler mulSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0), 
                () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)),
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0), 
                () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)),
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 1.0), () -> right,
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0), () -> left,
                new Handler(t, left, right, (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER) && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                () -> new AbstractSyntaxNode(TokensFactory.numberToken(
                Double.parseDouble(left.getToken().getSymbol()) * Double.parseDouble(right.getToken().getSymbol()))), null)))));
    }
    
    /**
     * @param t
     * @param left
     * @param right
     * @return c
     */
    public Handler divSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0), () -> left,
               new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0), 
               () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)), 
               new Handler(t, left, right, (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER) && r.getToken().getTypeToken().equals(TokenType.NUMBER),
               () -> new AbstractSyntaxNode(TokensFactory.numberToken(
               Double.parseDouble(left.getToken().getSymbol()) / Double.parseDouble(right.getToken().getSymbol()))), null)));
    }

}
