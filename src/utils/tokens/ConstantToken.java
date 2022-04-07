package utils.tokens;

/**
 * @author pesic
 *
 */
public class ConstantToken implements Token {
	
	private String constant;
	
	public ConstantToken(String constant){
		this.constant = constant;
	}
	
	@Override
	public TokenType getTypeToken() {
		return TokenType.CONSTANT;
	}
	@Override
	public String getSymbol() {
		return this.constant;
	}
	

}
