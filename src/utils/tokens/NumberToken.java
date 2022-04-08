package utils.tokens;

/**
 * @author pesic
 *
 */
public class NumberToken implements Token {
	
	private double value;
	
	public NumberToken(double value){
		this.value = value;
	}
	
	@Override
	public TokenType getTypeToken() {
		return TokenType.NUMBER;
	}
	
	public double getValueToken() {
		return value;
	}
	@Override
	public String getSymbol() {
		return String.valueOf(value);
	}

}
