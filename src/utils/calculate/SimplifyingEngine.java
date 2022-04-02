package utils.calculate;

import java.util.function.Predicate;

import utils.tokens.NumberToken;
import utils.tokens.OperatorToken;
import utils.tokens.Token;
import utils.tokens.TokenType;

/**
 * @author pesic
 *
 */
public class SimplifyingEngine {
	
	public AbstractSyntaxNode binaryOperator(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		OperatorToken operator = (OperatorToken)t;
		switch(operator.getSymbol()) {
		case "+": return simplifySumOperation(t, left, right);
		case "*": return simplifyProductOperation(t, left, right);
		case "-": return simplifySubtractionOperation(t, left, right);
		case "/": return simplifyDivisionOperation(t, left, right);
		default: return new AbstractSyntaxNode(t, left, right);
		}
	}
	
	private boolean verifyNumberValue(AbstractSyntaxNode source, Predicate<NumberToken> filter) {
		if(source.getToken().getTypeToken().equals(TokenType.NUMBER)) {
			NumberToken num = (NumberToken)source.getToken();
			return filter.test(num);
		}
		return false;
	}
	
	private AbstractSyntaxNode simplifySumOperation(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		boolean flag = false;
		if(verifyNumberValue(left, (num)->num.getValueToken() == 0.0) && !flag) {
			flag = true;
			return right;
		}else if(verifyNumberValue(right, (num)->num.getValueToken() == 0.0) && !flag) {
			return left;
		}
		
		return new AbstractSyntaxNode(t, left, right);
	}
	
	private AbstractSyntaxNode simplifyProductOperation(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		boolean flag = false;
		if(verifyNumberValue(left, (num)->num.getValueToken() == 0.0) && !flag) {
			flag = true;
			return new AbstractSyntaxNode(new NumberToken(0.0));
		}else if(verifyNumberValue(left, (num)->num.getValueToken() == 1.0) && !flag) {
			flag = true;
			return right;
		}else if(verifyNumberValue(right, (num)->num.getValueToken() == 0.0) && !flag) {
			flag = true;
			return new AbstractSyntaxNode(new NumberToken(0.0));
		}else if(verifyNumberValue(right, (num)->num.getValueToken() == 1.0) && !flag) {
			return left;
		}
		
		return new AbstractSyntaxNode(t, left, right);
	}
	
	private AbstractSyntaxNode simplifySubtractionOperation(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		boolean flag = false;
		if(verifyNumberValue(left, (num)->num.getValueToken() == 0.0) && !flag) {
			flag = true;
			return new AbstractSyntaxNode(new OperatorToken(Operator.getOperatorBySymbolAndArgs("-", 1)), right);
		
		}else if(verifyNumberValue(right, (num)->num.getValueToken() == 0.0) && !flag) {
			return left;
		}
		return new AbstractSyntaxNode(t, left, right);
	}
	
	private AbstractSyntaxNode simplifyDivisionOperation(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		if(verifyNumberValue(right, (num)->num.getValueToken() == 1.0)) {
			return left;
		}
		return new AbstractSyntaxNode(t, left, right);
	}
	
}
