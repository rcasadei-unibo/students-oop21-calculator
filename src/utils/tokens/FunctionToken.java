package utils.tokens;

import utils.calculate.Function;

/**
 * @author pesic
 *
 */
public class FunctionToken implements Token {
	private Function func;
	
	public FunctionToken(Function func){
		if(func == null) {
			throw new IllegalArgumentException();
		}
		this.func = func;
	}
	@Override
	public TokenType getTypeToken() {
		return TokenType.FUNCTION;
	}
	
	public Function getFunction() {
		return func;
	}
	@Override
	public String getSymbol() {
		return func.getName();
	}
	
	
	
}
