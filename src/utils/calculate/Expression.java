package utils.calculate;

import java.util.Optional;

import utils.ast.Operation;

/**
 * @author pesic
 *
 */
public class Expression {
	
	private String expr;
	private EvaluatorAST evaluator = new EvaluatorAST();
	private ParserAST parser = new ParserAST();
	private Optional<Operation> result = Optional.empty();
	
	public void setExpr(String e) {
		this.expr = e;
	}
	
	public Operation getResult() {
		this.result = Optional.of(evaluator.evaluate(parser.parseToAST(this.expr)));
		return result.get();
	}
	
	public Operation getDerivative() {
		if(result.isEmpty()) {
			getResult();
		}
		this.result = Optional.of(result.get().getDerivative());
		return result.get();
	}
	
	public String toString() {
		return this.result.toString();
	}
	
	
}
