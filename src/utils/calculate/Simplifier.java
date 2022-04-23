package utils.calculate;

import utils.tokens.Token;

/**
 * simplifies Expression during the parsing of the AS (e.g. 5 + 0 => 5).
 *
 */
public interface Simplifier {
    /**
     * every binary operator has a simplification process.
     * @param t type of token operation e.g. sum, mul, div, sub
     * @param left The left child
     * @param right The right child
     * @return the simplified version of the binary operator
     */
    AbstractSyntaxNode binaryOperator(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right);

}
