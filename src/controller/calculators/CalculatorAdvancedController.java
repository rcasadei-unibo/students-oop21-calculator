package controller.calculators;

import java.util.List;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.calculate.Algorithm;
import utils.calculate.Expression;
import utils.calculate.Derivate;
import utils.calculate.Limit;
import utils.NumberFormatter;

import utils.calculate.Integrator;

/**
 * The controller for the advanced calculator.
 *
 */
public class CalculatorAdvancedController {
    private Algorithm op;
    private final Expression expr = new Expression();
    private TypeAlgorithm type;
    private CalculatorController controller;
    private String previousOp = "";
    private List<String> previousParams = List.of();
    private TypeAlgorithm previousType = TypeAlgorithm.DERIVATE;

    /**
     * The type of Operation.
     *
     */
    public enum TypeAlgorithm {
        /**
         * Takes the Algorithm interface as input.
         */
        DERIVATE(new Derivate()),
        /**
         * 
         */
        INTEGRATE(new Integrator()),
        /**
         * 
         */
        LIMIT(new Limit());

        private Algorithm alg;

        TypeAlgorithm(final Algorithm alg) {
            this.alg = alg;
        }

        /**
         * @return the given operation
         */
        public Algorithm getAlg() {
            return alg;
        }
    }

    /**
     * @param controller
     */
    public CalculatorAdvancedController(final CalculatorController controller) {
        this.controller = controller;
        this.expr.setEngine(new CCEngine(controller));
    }

    /**
     * Sets the operation.
     * 
     * @param typeOp
     */
    public void setOperation(final TypeAlgorithm typeOp) {
        this.type = typeOp;
        this.op = typeOp.getAlg();
        this.reset();
    }

    /**
     * Return the previous Operation type.
     * 
     * @return s
     */
    public TypeAlgorithm getPreviousTypeOp() {
        return this.previousType;
    }

    /**
     * Return the previous parameters.
     * 
     * @return c
     */
    public List<String> getPreviousParameters() {
        return this.previousParams;
    }

    /**
     * @param c
     */
    public void read(final String c) {
        this.controller.getManager().memory().read(c);
    }

    /**
     * Delete the last smbol inserted.
     */
    public void deleteLast() {
        this.controller.getManager().memory().deleteLast();
    }

    /**
     * @return The current stae in input buffer
     */
    public String getCurrentState() {
        return this.controller.getManager().memory().getCurrentState().stream().reduce("", (s1, s2) -> s1 + s2);
    }

    /**
     * 
     */
    public void reset() {
        this.op.unsetParameters();
        this.controller.getManager().memory().clear();
    }

    /**
     * Sets the paramets of the Operation.
     * 
     * @param params
     * @throws CalcException
     */
    public void setParameters(final List<String> params) throws CalcException {
        this.op.setParameters(params);
    }

    /**
     * Gives you back the previous Operation.
     * 
     * @return s
     */
    public String getPreviousOp() {
        return this.previousOp;
    }

    /**
     * adds to the history the expression = result.
     * 
     * @param oldExpr
     */
    public void addToHistory(final String oldExpr) {
        this.controller.getManager().memory().addResult(oldExpr);
    }

    /**
     * @return calculates the result given the operation
     * @throws CalcException
     */
    public String calculate() throws CalcException {
        final var e = this.controller.getManager().memory().getCurrentState().stream().reduce("", (res, s) -> res + s);
        this.expr.setExpr(e);
        String res = this.op.calculate(expr);
        this.previousOp = e;
        this.previousParams = this.op.getParameters();
        this.previousType = this.type;
        if (!this.type.equals(TypeAlgorithm.DERIVATE)) {
            res = NumberFormatter.format(Double.parseDouble(res), 8, 8, 5);
        }
        reset();
        return res;
    }

}
