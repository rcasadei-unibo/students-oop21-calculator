package utils.calculate;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import utils.tokens.OperatorToken;
import utils.tokens.Token;
import utils.tokens.TokenType;

/**
 * @author pesic
 *
 */
public class ShuntingYardAlgorithm {
	
	private Stack<Token> operators;
	private List<Token> output;
	private Tokenizer tok;
	
	private void pushOperator(OperatorToken op) {
		if(operators.isEmpty()) {
			operators.push(op);
			return;
		}
		while(operators.size() > 0  
				&& (operators.peek().getTypeToken().equals(TokenType.OPERATOR) 
				&& ((OperatorToken)operators.peek()).getOperation().getPrecedence() >= op.getOperation().getPrecedence()) ) {
			output.add(operators.pop());
		}
		operators.push(op);
	}
	
	private void closeParanthesisPop() {
		if(operators.isEmpty()) {
			throw new IllegalStateException("No open Paranthesis found");
		}
		boolean openParFound = false;
		while(operators.size() > 0 
			&& !openParFound) {
			if(operators.peek().getTypeToken().equals(TokenType.OPENPAR)) {
				openParFound = true;
				operators.pop();
			}else{
				output.add(operators.pop());
			}
		}
		if(openParFound == false) {
			throw new IllegalStateException("No open Paranthesis found");
		}
		if(!operators.isEmpty()  && operators.peek().getTypeToken().equals(TokenType.FUNCTION)) {
			output.add(operators.pop());
		}
	}
	
	private void popUnaryOperation(OperatorToken op) {
		while(operators.size() > 0 
				&& operators.peek().getTypeToken().equals(TokenType.OPERATOR) 
				&& ((OperatorToken)operators.peek()).getOperation().getNumOperands() == 1
				&& !((OperatorToken)operators.peek()).getOperation().isLeftAssociative() 
				&& ((OperatorToken)operators.peek()).getOperation().getPrecedence() >= op.getOperation().getPrecedence()) {
			output.add(operators.pop());
		}
	}
	
	public List<Token> getReversedPolishedNotation(String expression){
		tok = new Tokenizer(expression);
		operators = new Stack<>();
		output = new LinkedList<>();
		
		while(tok.hasNextToken()) {
			Token t = tok.getNextToken();
			System.out.println(t.getTypeToken());
			if(t.getTypeToken().equals(TokenType.NUMBER) || t.getTypeToken().equals(TokenType.VARIABLE) || t.getTypeToken().equals(TokenType.CONSTANT)) {
				output.add(t);
			}else if(t.getTypeToken().equals(TokenType.OPERATOR)) {
				OperatorToken opT = (OperatorToken)t;
				if(!opT.getOperation().isLeftAssociative() && opT.getOperation().getNumOperands() == 1 ) {
					popUnaryOperation(opT);
					operators.push(opT);
				}else{
					pushOperator(opT);
				}
			}else if(t.getTypeToken().equals(TokenType.OPENPAR) || t.getTypeToken().equals(TokenType.FUNCTION) ) {
				operators.push(t);
			}else if(t.getTypeToken().equals(TokenType.CLOSEPAR)) {
				closeParanthesisPop();
			}
		}
		if(operators.size() > 0) {
			while(!operators.isEmpty()) {
				output.add(operators.pop());
			}
		}
		
		return output;
	}
	
	public static void main(String[] args) {
		var alg = new ShuntingYardAlgorithm();
		var li = alg.getReversedPolishedNotation("xpi");
		li.stream().map(t->t.getSymbol()).forEach(tt->System.out.println("token: "+tt));
	}
	
}
