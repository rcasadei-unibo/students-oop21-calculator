package utils.calculate;

import java.util.Optional;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.ast.Operation;

/**
 * Expression class is used for calculating he result of an mathematical expression.
 *
 */
public class Expression {
	
	private String expr;
	private final EvaluatorAST evaluator = new EvaluatorAST();
	private final ParserAST parser = new ParserAST();
	private Optional<Operation> result = Optional.empty();
	
	/**
	 * sets the expression.
	 * @param expr
	 */
	public void setExpr(final String expr) {
		this.expr = expr;
		result = Optional.empty();
	}
	
	/**
	 * Sets he engine for parsing the string.
	 * @param engine
	 */
	public void setEngine(final CCEngine engine) {
	    this.parser.setEngine(engine);
	}
	
	/**
	 * Calculates the result of expression.
	 * @return result
	 * @throws CalcException
	 */
	public Operation getResult() throws CalcException {
		this.result = Optional.of(evaluator.evaluate(parser.parseToAST(this.expr)));
		return result.get();
	}
	
	/**
	 * @return once calculated an expression we can get back the derivate of it
	 * @throws CalcException
	 */
	public Operation getDerivative() throws CalcException {
		if (result.isEmpty()) {
			getResult();
		}
		this.result = Optional.of(result.get().getDerivative());
		return result.get();
	}
	
	/**
	 * @return the "Stringify" version of the result
	 */
	public String toString() {
		return this.result.toString();
	}
	
	
}
