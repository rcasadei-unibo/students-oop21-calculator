package utils.tokens;

import utils.calculate.Operator;

/**
 * @author pesic
 *
 */
public class OperatorToken implements Token {
	private Operator ops;
	
	public OperatorToken(Operator ops){
		if(ops == null) {
			throw new IllegalArgumentException("Can't have null argument");
		}
		this.ops = ops;
	}
	
	@Override
	public TokenType getTypeToken() {
		return TokenType.OPERATOR;
	}
	
	public Operator getOperation() {
		return ops;
	}
	@Override
	public String getSymbol() {
		return ops.getSymbol();
	}

}
