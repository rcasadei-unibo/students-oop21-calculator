package utils.calculate;
import java.util.Optional;

import utils.tokens.Token;

/**
 * @author pesic
 *
 */
public class AbstractSyntaxNode {
	
	private Token token;
	private Optional<AbstractSyntaxNode> left;
	private Optional<AbstractSyntaxNode> right;
	
	public AbstractSyntaxNode(Token t) {
		this.token = t;
		this.left = Optional.empty();
		this.right = Optional.empty();
	}
	
	public AbstractSyntaxNode(Token t, AbstractSyntaxNode right) {
		this.token = t;
		this.right = Optional.ofNullable(right);
		this.left = Optional.empty();
	}
	
	public AbstractSyntaxNode(Token t, AbstractSyntaxNode left, AbstractSyntaxNode right) {
		this.token = t;
		this.left = Optional.ofNullable(left);
		this.right = Optional.ofNullable(right);
	}
	
	
	public Token getToken(){
		return token;
	}
	
	public Optional<AbstractSyntaxNode> getLeft(){
		return left;
	}
	
	public Optional<AbstractSyntaxNode> getRight(){
		return right;
	}
	
}
