package utils.calculate;

import java.util.Optional;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.ast.Operation;

/**
 * Expression class is used for calculating he result of an mathematical expression.
 *
 */
public class Expression implements MathematicalExpression {
	
	private String expr;
	private final TreeEvaluator<Operation> evaluator;
	private final MathematicalParser parser;
	private Optional<Operation> result = Optional.empty();
	
	/**
	 * 
	 */
	public Expression() {
	    this.evaluator = new EvaluatorAST();
	    this.parser = new ParserAST();
	}
	
	/**
	 * @param expr
	 * @param engine
	 */
	public Expression(final String expr, final CCEngine engine) {
	    this.evaluator = new EvaluatorAST();
        this.parser = new ParserAST();
	    this.expr = expr;
	    this.parser.setEngine(engine);
	}
	
	/**
	 * @param expr
	 * @param engine
	 * @param areVariablesAllowed
	 */
	public Expression(final String expr, final CCEngine engine, final boolean areVariablesAllowed) {
        this.evaluator = new EvaluatorAST();
        this.parser = new ParserAST();
        this.expr = expr;
        this.parser.setEngine(engine);
        this.parser.setAreVariablesAllowed(areVariablesAllowed);
    }
	
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
	    if (result.isPresent()) {
	        return result.get();
	    }
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
