package utils.tokens;

/**
 * @author pesic
 *
 */
public class OpenParToken implements Token {

	@Override
	public TokenType getTypeToken() {
		return TokenType.OPENPAR;
	}

	@Override
	public String getSymbol() {
		return "(";
	}

}
