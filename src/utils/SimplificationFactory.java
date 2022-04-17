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
 * It is used by the simplification engine for reducing mathematical expression.
 *
 */
public class SimplificationFactory {

    /**
     * A handler class used for the chain of responsibility for the
     * simplification engine. Every binary Operation has its own C.O.R. in the
     * case no handler can do something on the input the standard handler is
     * returned.
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
         *            : Token
         * @param left
         *            : the left child of the parent in the ast
         * @param right
         *            : the right child of the parent in the ast
         * @param filter
         *            : true this is the right handler, otherwise next handler
         * @param supplier
         *            : what should return if filter returns true
         * @param next
         *            : the next handler
         */
        public Handler(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right,
                final BiPredicate<AbstractSyntaxNode, AbstractSyntaxNode> filter, final Supplier<AbstractSyntaxNode> supplier,
                final Handler next) {
            this.t = t;
            this.left = left;
            this.right = right;
            this.filter = filter;
            this.supplier = supplier;
            this.next = next;
        }

        /**
         * @return the result, if can be handled
         */
        public AbstractSyntaxNode handle() {
            if (this.filter.test(left, right)) {
                return supplier.get();
            } else {
                return this.nextHandler();
            }
        }

        /**
         * @return the next handler.
         */
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
     * @return result
     */
    public Handler sumSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0),
                () -> right,
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0),
                        () -> left,
                        new Handler(t, left, right,
                                (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER)
                                        && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                                () -> new AbstractSyntaxNode(
                                        TokensFactory.numberToken(Double.parseDouble(left.getToken().getSymbol())
                                                + Double.parseDouble(right.getToken().getSymbol()))),
                                null)));
    }

    /**
     * @param t
     * @param left
     * @param right
     * @return result
     */
    public Handler subSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0),
                () -> new AbstractSyntaxNode(TokensFactory.operatorToken(Operator.getOperatorBySymbolAndArgs("-", 1)), right),
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0),
                        () -> left,
                        new Handler(t, left, right,
                                (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER)
                                        && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                                () -> new AbstractSyntaxNode(
                                        TokensFactory.numberToken(Double.parseDouble(left.getToken().getSymbol())
                                                - Double.parseDouble(right.getToken().getSymbol()))),
                                null)));
    }

    /**
     * @param t
     * @param left
     * @param right
     * @return result
     */
    public Handler mulSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0),
                () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)),
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 0.0),
                        () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)),
                        new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 1.0),
                                () -> right,
                                new Handler(t, left, right,
                                        (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0), () -> left,
                                        new Handler(t, left, right,
                                                (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER)
                                                        && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                                                () -> new AbstractSyntaxNode(
                                                        TokensFactory.numberToken(Double.parseDouble(left.getToken().getSymbol())
                                                                * Double.parseDouble(right.getToken().getSymbol()))),
                                                null)))));
    }

    /**
     * @param t
     * @param left
     * @param right
     * @return result
     */
    public Handler divSimplification(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
        return new Handler(t, left, right, (l, r) -> this.verifyNumberValue(right, (num) -> num.getObjectToken() == 1.0),
                () -> left,
                new Handler(t, left, right, (l, r) -> this.verifyNumberValue(left, (num) -> num.getObjectToken() == 0.0),
                        () -> new AbstractSyntaxNode(TokensFactory.numberToken(0.0)),
                        new Handler(t, left, right,
                                (l, r) -> l.getToken().getTypeToken().equals(TokenType.NUMBER)
                                        && r.getToken().getTypeToken().equals(TokenType.NUMBER),
                                () -> new AbstractSyntaxNode(
                                        TokensFactory.numberToken(Double.parseDouble(left.getToken().getSymbol())
                                                / Double.parseDouble(right.getToken().getSymbol()))),
                                new Handler(t, left, right,
                                        (l, r) -> l.getToken().getTypeToken().equals(TokenType.VARIABLE)
                                                && r.getToken().getTypeToken().equals(TokenType.VARIABLE),
                                        () -> new AbstractSyntaxNode(TokensFactory.numberToken(1.0)), null))));
    }

}
