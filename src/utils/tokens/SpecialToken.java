package utils.tokens;

/**
 * Some tokens as Number, Function, and others need to return a value or something specific as output, so this inteface extends
 * the Standard Token. (e.g NumberToken the value of the token )
 *
 * @param <T>
 */
public interface SpecialToken<T> extends Token {
	/**
	 * @return a specific object that belogns o a specific token.
	 */
	T getObjectToken();
	
}
