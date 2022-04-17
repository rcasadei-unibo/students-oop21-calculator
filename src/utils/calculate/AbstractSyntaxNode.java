package utils.calculate;
import java.util.Optional;

import utils.tokens.Token;

/**
 * The fundamental data structure for building an AST(Abstract Syntax Tree), that is used for evaluating expressions.
 *
 */
public class AbstractSyntaxNode {
	
	private final Token token;
	private final Optional<AbstractSyntaxNode> left;
	private final Optional<AbstractSyntaxNode> right;
	
	/**
	 * @param t
	 */
	public AbstractSyntaxNode(final Token t) {
		this.token = t;
		this.left = Optional.empty();
		this.right = Optional.empty();
	}
	
	/**
	 * @param t
	 * @param right
	 */
	public AbstractSyntaxNode(final Token t, final AbstractSyntaxNode right) {
		this.token = t;
		this.right = Optional.ofNullable(right);
		this.left = Optional.empty();
	}
	
	/**
	 * @param t
	 * @param left
	 * @param right
	 */
	public AbstractSyntaxNode(final Token t, final AbstractSyntaxNode left, final AbstractSyntaxNode right) {
		this.token = t;
		this.left = Optional.ofNullable(left);
		this.right = Optional.ofNullable(right);
	}
	
	
	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * @return the left child
	 */
	public Optional<AbstractSyntaxNode> getLeft() {
		return left;
	}
	
	/**
	 * @return the right child
	 */
	public Optional<AbstractSyntaxNode> getRight() {
		return right;
	}
	
}
