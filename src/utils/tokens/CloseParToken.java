package utils.tokens;

/**
 * @author pesic
 *
 */
public class CloseParToken implements Token {

	@Override
	public TokenType getTypeToken() {
		return TokenType.CLOSEPAR;
	}

	@Override
	public String getSymbol() {
		return ")";
	}

}
