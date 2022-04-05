package utils.tokens;

/**
 * @author pesic
 *
 */
public class VariableToken implements Token {
	
	private String variable;
	
	public VariableToken(String variable){
		this.variable = variable;
	}
	
	@Override
	public TokenType getTypeToken() {
		return TokenType.VARIABLE;
	}
	@Override
	public String getSymbol() {
		return this.variable;
	}
	
}
