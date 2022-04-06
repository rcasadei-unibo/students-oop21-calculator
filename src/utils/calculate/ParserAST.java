package utils.calculate;

import java.util.List;
import java.util.Stack;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.tokens.SpecialToken;
import utils.tokens.Token;
import utils.tokens.TokenType;


/**
 * @author pesic
 *
 */
public class ParserAST {

    private Stack<AbstractSyntaxNode> stack = new Stack<>();
    private List<Token> output;
    private Tokenizer tok;
    private CCEngine engine;
    private SimplifyingEngine simplify = new SimplifyingEngine();

    private AbstractSyntaxNode createNumberOrVariableNode(Token t) {
        return new AbstractSyntaxNode(t);
    }

    private AbstractSyntaxNode createUnaryOperatorOrFunctionNode(Token t, AbstractSyntaxNode left) {
        return new AbstractSyntaxNode(t, left);
    }

    private AbstractSyntaxNode createBinaryOperatorNode(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
        return simplify.binaryOperator(t, left, right);
    }

    private AbstractSyntaxNode parseBinaryOperator(Token token) {
        if (stack.size() < 2) {
            throw new IllegalStateException("For binary operator you need a least 2 nodes");
        }
        final AbstractSyntaxNode right = stack.pop();
        final AbstractSyntaxNode left = stack.pop();

        return createBinaryOperatorNode(token, left, right);
    }

    private AbstractSyntaxNode parseUnaryOperatorOrFunction(Token token) {
        if (stack.size() < 1) {
            throw new IllegalStateException("For binary operator you need a least 2 nodes");
        }
        final AbstractSyntaxNode right = stack.pop();

        return createUnaryOperatorOrFunctionNode(token, right);
    }
    
    public void setEngine(CCEngine engine) {
        this.engine = engine;
    }
    
    public AbstractSyntaxNode parseToAST(String expression) {
        tok = new Tokenizer(expression);
        try {
            this.output = tok.convertToTokens(this.engine.parseToRPN(tok.getListSymbol()));
        } catch (CalcException e) {
            System.out.println(e);
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
            throw new IllegalStateException("Something went wrong during the parsing");
        }

        return stack.pop();
    }

    /*public static void main(String[] args) {
        var parser = new ParserAST();
        parser.parseToAST("3x+5");
    }*/
}
