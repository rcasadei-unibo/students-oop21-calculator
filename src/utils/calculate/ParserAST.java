package utils.calculate;

import java.util.List;
import java.util.Stack;

import utils.tokens.OperatorToken;
import utils.tokens.Token;
import utils.tokens.TokenType;

/**
 * @author pesic
 *
 */
public class ParserAST {
	
	private Stack<AbstractSyntaxNode> stack = new Stack<>();
	private List<Token> output;
	private ShuntingYardAlgorithm alg = new ShuntingYardAlgorithm();
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
		if(stack.size() < 2) {
			throw new IllegalStateException("For binary operator you need a least 2 nodes");
		}
		AbstractSyntaxNode right = stack.pop();
		AbstractSyntaxNode left = stack.pop();
		
		return createBinaryOperatorNode(token, left, right);
	}
	
	private AbstractSyntaxNode parseUnaryOperatorOrFunction(Token token) {
		if(stack.size() < 1) {
			throw new IllegalStateException("For binary operator you need a least 2 nodes");
		}
		AbstractSyntaxNode right = stack.pop();
		
		return createUnaryOperatorOrFunctionNode(token, right);
	}
	
	public AbstractSyntaxNode parseToAST(String expression) {
		this.output= this.alg.getReversedPolishedNotation(expression);
		output.forEach(token->{
			if(token.getTypeToken().equals(TokenType.NUMBER) || token.getTypeToken().equals(TokenType.VARIABLE) || token.getTypeToken().equals(TokenType.CONSTANT)) {
				stack.push(createNumberOrVariableNode(token));
			}else if(token.getTypeToken().equals(TokenType.OPERATOR)) {
				OperatorToken opT = (OperatorToken)token;
				if(opT.getOperation().getNumOperands() == 2) {
					var newToken = parseBinaryOperator(token);
					stack.push(newToken);
				}else if(opT.getOperation().getNumOperands() == 1) {
					var newToken = parseUnaryOperatorOrFunction(token);
					stack.push(newToken);
				}
			}else if(token.getTypeToken() == TokenType.FUNCTION) {
				var newToken = parseUnaryOperatorOrFunction(token);
				stack.push(newToken);
			}
			
		});
		
		if(stack.size() != 1) {
			throw new IllegalStateException("Something went wrong during the parsing");
		}
		
		return stack.pop();
	}
	
	public static void main(String[] args) {
		var parser = new ParserAST();
		parser.parseToAST("3x+5");
	}
}
