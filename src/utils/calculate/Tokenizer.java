package utils.calculate;

import java.util.Set;

import utils.tokens.*;


/**
 * @author pesic
 *
 */
public class Tokenizer {
	
	private String expr;
	private int index = 0;
	private Token lastToken = null;
	private int lenExpr;
	private String variable;
	private Set<String> constants;
	private ExternData data = new ExternData();
	
	
	Tokenizer(String expr){
		this.expr = expr;
		this.lenExpr = expr.length();
		this.variable = data.getVariable();
		this.constants = data.getConstants();
	}
	
	public boolean hasNextToken() {
		return this.index <= this.lenExpr-1;
	}
	
	public Token getNextToken() {
		System.out.println("Index: "+index);
		if(!hasNextToken()) {
			return null;
		}
		
		char c = this.expr.charAt(index);
		if(Character.isDigit(c)) {
			if(lastToken != null) {
				System.out.println("a number");
				if(lastToken.getTypeToken().equals(TokenType.NUMBER)) {
					throw new IllegalArgumentException("2 numbers can't stay near");
				}
				if(lastToken.getTypeToken() != TokenType.OPENPAR &&
					lastToken.getTypeToken() != TokenType.FUNCTION &&
					lastToken.getTypeToken() != TokenType.OPERATOR)
				{
					lastToken = new OperatorToken(new Operator("*", 2, true));
					
					return lastToken;
				}
			}
			System.out.println("The first character of the expression is a number");
			return getNumberToken();
		}else if(c == '(') {
			if(lastToken != null) {
				if(lastToken.getTypeToken() != TokenType.OPENPAR &&
						lastToken.getTypeToken() != TokenType.OPERATOR &&
						lastToken.getTypeToken() != TokenType.FUNCTION) {
					lastToken = new OperatorToken(new Operator("*", 2, true));
					
					return lastToken;
				}
			}
			index++;
			return new OpenParToken();
		}else if(c == ')') {
			index++;
			return new CloseParToken();
		}else if(Operator.isAllowedOperator(String.valueOf(c))) {
			return getOperationToken();
		}else if(Character.isLetter(c)) {
			System.out.println("a function or a variable");
			if(lastToken != null) {
				System.out.println("a function or a variable with something before it");
				if(lastToken.getTypeToken() != TokenType.OPERATOR &&
					lastToken.getTypeToken() != TokenType.FUNCTION &&
					lastToken.getTypeToken() != TokenType.OPENPAR) {
					lastToken = new OperatorToken(Operator.getOperatorBySymbolAndArgs("*", 2));
					
					
					return lastToken;
				}
			}
			System.out.println("the first element of the expression is a function or a variable");
			return getFunctionOrVariableToken();
		}
		throw new IllegalArgumentException("the character: "+String.valueOf(c)+" wasn't recognized ");
	}
	
	private Token getNumberToken() {
		double num;
		int ind = this.index;
		while(Character.isDigit(this.expr.charAt(index)) && index < this.lenExpr-1 ) index++; 
		if(this.expr.charAt(index) == '.') index++;
		while(Character.isDigit(this.expr.charAt(index)) && index < this.lenExpr-1 ) index++;
		
		if(this.index - ind == 0) {
			num = Double.parseDouble(this.expr.substring(ind));
		}else {
			num = Double.parseDouble(this.expr.substring(ind, index));
		}
		
		var number = new NumberToken(num);
		lastToken = number;
		if(this.index == this.lenExpr -1 && ind == this.index) {
			index++;
		}
		return number;
	}
	
	private Token getFunctionOrVariableToken() {
		int newIndex = index;
		int previousIndex = -1;
		char c = this.expr.charAt(newIndex);
		Token newToken = null;
		while(newIndex <= this.lenExpr-1 && Character.isLetter(c)) {
			if(newIndex - index == 0) {
				if(String.valueOf(c).equals(this.variable)) {
					newToken = new VariableToken(this.variable);
					previousIndex = newIndex+1;
				}else if(constants.contains(String.valueOf(c))) {
					newToken = new ConstantToken(String.valueOf(c));
					previousIndex = newIndex+1;
				}
			}else {
				if(this.expr.substring(index, newIndex+1).equals(this.variable)) {
					newToken = new VariableToken(this.variable);
					previousIndex = newIndex+1;
				}else if(Function.isFunction(this.expr.substring(index, newIndex+1))) {
					newToken =new FunctionToken(Function.dictFunctions.get(this.expr.substring(index, newIndex+1)));
					previousIndex = newIndex+1;
				}else if(constants.contains(this.expr.substring(index, newIndex+1))) {
					newToken = new ConstantToken(this.expr.substring(index, newIndex+1));
					previousIndex = newIndex+1;
				}
			}
			newIndex++;
			if(newIndex <= this.lenExpr-1) {
				c = this.expr.charAt(newIndex);
			}
		}
		if(previousIndex != -1) {
			this.index = previousIndex;
			
		}else {
			this.index = newIndex;
		}
		if(newToken == null) {
			throw new IllegalArgumentException("The variable name or the function doesn't exist");
		}
		lastToken = newToken;
		return newToken;
		
	}
	
	private Token getOperationToken() {
		char c = this.expr.charAt(this.index++);
		int arguments = 2;
		
		if(lastToken == null) {
			arguments = 1;
		}else {
			if(lastToken.getTypeToken() == TokenType.OPENPAR) {
				arguments = 1;
			}else if(lastToken.getTypeToken() == TokenType.OPERATOR) {
				final Operator op = ((OperatorToken)lastToken).getOperation();
				if(op.getNumOperands() == 2 || (op.getNumOperands() == 1 && !op.isLeftAssociative())) {
					arguments = 1;
				}
			}
		}
		
		var newOp = Operator.getOperatorBySymbolAndArgs(String.valueOf(c), arguments);
		lastToken = new OperatorToken(newOp);
		return lastToken;
	}
	
	public static void main(String[] args) {
		var tok = new Tokenizer("xpi");
		while(tok.hasNextToken()) {
			Token t = tok.getNextToken();
			System.out.println(t.getTypeToken());
		}
	}
	
}
