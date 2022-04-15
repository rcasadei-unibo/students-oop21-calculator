package utils.tokens;

/**
 * Token interface for converting strings to Tokens.
 *
 */
public interface Token {
	
	/**
	 * @return the tokenType
	 */
	TokenType getTypeToken();
	
	/**
	 * @return the symbol for the specifed token
	 */
	String getSymbol();
	
}
