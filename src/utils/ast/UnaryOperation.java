package utils.ast;

import static java.util.Objects.requireNonNull;

/**
 * @author pesic
 *
 */
public abstract class UnaryOperation implements Operation {
	final protected Operation op;
	
	public UnaryOperation(Operation op) {
		this.op = requireNonNull(op);
	}
	
	public Operation getOp(){
		return op;
	}
}
