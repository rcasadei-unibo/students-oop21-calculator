package utils.calculate;

import java.util.List;
import java.util.Stack;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;

/**
 * Given a List in reversed polished notation it builds an AST.
 *
 */
public class ParserAST {

    private Stack<AbstractSyntaxNode> stack;
    private CCEngine engine;
    private boolean isVariableAllowed = true;
    private final SimplifyingEngine simplify = new SimplifyingEngine();

    private AbstractSyntaxNode createNumberOrVariableNode(final Token t) {
        return new AbstractSyntaxNode(t);
    }

    private AbstractSyntaxNode createUnaryOperatorOrFunctionNode(final Token t, final AbstractSyntaxNode left) {
        return new AbstractSyntaxNode(t, left);
    }

    private AbstractSyntaxNode createBinaryOperatorNode(final Token t, final AbstractSyntaxNode left,
            final AbstractSyntaxNode right) {
        return simplify.binaryOperator(t, left, right);
    }

    private AbstractSyntaxNode parseBinaryOperator(final Token token) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("For binary operator you need a least 2 nodes");
        }
        final AbstractSyntaxNode right = stack.pop();
        final AbstractSyntaxNode left = stack.pop();
        return createBinaryOperatorNode(token, left, right);
    }

    private AbstractSyntaxNode parseUnaryOperatorOrFunction(final Token token) {
        if (stack.size() < 1) {
            throw new IllegalArgumentException("For binary operator you need a least 2 nodes");
        }
        final AbstractSyntaxNode right = stack.pop();

        return createUnaryOperatorOrFunctionNode(token, right);
    }

    /**
     * Sets the engine for obtaining the RPN expression.
     * 
     * @param engine
     */
    public void setEngine(final CCEngine engine) {
        this.engine = engine;
    }

    /**
     * @param cond
     */
    public void setAreVariablesAllowed(final boolean cond) {
        this.isVariableAllowed = cond;
    }

    /**
     * @param expression
     * @return The root of the AST
     * @throws CalcException
     */
    public AbstractSyntaxNode parseToAST(final String expression) throws CalcException {
        List<Token> output;
        final Tokenizer tok;
        if (isVariableAllowed) {
            tok = new Tokenizer(expression);
        } else {
            tok = new Tokenizer(expression, false);
        }
        this.stack = new Stack<>();
        try {
            final List<String> l = tok.getListSymbol();
            final List<String> l1 = this.engine.parseToRPN(l);
            output = tok.convertToTokens(l1);
        } catch (CalcException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        output.forEach(token -> {
            if (token.getTypeToken().equals(TokenType.NUMBER) || token.getTypeToken().equals(TokenType.VARIABLE)
                    || token.getTypeToken().equals(TokenType.CONSTANT)) {

                stack.push(createNumberOrVariableNode(token));
            } else if (token.getTypeToken().equals(TokenType.OPERATOR)) {
                @SuppressWarnings("unchecked")
                final SpecialToken<Operator> opT = (SpecialToken<Operator>) token;
                if (((Operator) opT.getObjectToken()).getNumOperands() == 2) {
                    final var newToken = parseBinaryOperator(token);
                    stack.push(newToken);
                } else if (((Operator) opT.getObjectToken()).getNumOperands() == 1) {
                    final var newToken = parseUnaryOperatorOrFunction(token);
                    stack.push(newToken);
                }
            } else if (token.getTypeToken() == TokenType.FUNCTION) {
                final var newToken = parseUnaryOperatorOrFunction(token);
                stack.push(newToken);
            }

        });
        if (stack.size() != 1) {
            throw new CalcException("Unrecognized Operator");
        }

        return stack.pop();
    }

}
