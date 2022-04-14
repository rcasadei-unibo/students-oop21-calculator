package controller.calculators;

import java.util.List;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.calculate.Algorithm;
import utils.calculate.Expression;
import utils.calculate.Derivate;
import utils.calculate.Limit;

import utils.calculate.Integrator;

/**
 * @author pesic
 *
 */
public class CalculatorAdvancedController {
    private Algorithm op;
    private Expression expr = new Expression();
    private TypeAlgorithm type;
    private  CalculatorController controller;
    private String previousOp = "";
    private List<String> previousParams = List.of();
    private TypeAlgorithm previousType = TypeAlgorithm.DERIVATE;

    /**
     * @author pesic
     *
     */
    public enum TypeAlgorithm {
        DERIVATE(new Derivate()),
        INTEGRATE(new Integrator()),
        LIMIT(new Limit());
        private Algorithm alg;
        TypeAlgorithm(final Algorithm alg) {
            this.alg = alg;
        }
        public  Algorithm getAlg() {
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
     * @param typeOp
     */
    public void setOperation(final TypeAlgorithm typeOp) {
        this.type = typeOp;
        this.op = typeOp.getAlg();
        this.reset();
    }
    
    /**
     * @return s
     */
    public TypeAlgorithm getPreviousTypeOp() {
        return this.previousType;
    }
    
    /**
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
     * 
     */
    public void deleteLast() {
        this.controller.getManager().memory().deleteLast();
    }

    /**
     * @return c
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
     * @param params
     * @throws CalcException 
     */
    public void setParameters(final List<String> params) throws CalcException {
        this.op.setParameters(params);
    }
    
    /**
     * @return s
     */
    public String getPreviousOp() {
        return this.previousOp;
    }
    
    /**
     * @param oldExpr
     */
    public void addToHistory(String oldExpr) {
        this.controller.getManager().memory().addResult(oldExpr);
    }

    /**
     * @return s
     * @throws CalcException 
     */
    public String calculate() throws CalcException {
        final var e = this.controller.getManager().memory().getCurrentState().stream().reduce("", (res, s) -> res + s);
        this.expr.setExpr(e);
        final String res = this.op.calculate(expr);
        this.previousOp = e;
        this.previousParams = this.op.getParameters();
        this.previousType = this.type;
        /*if (!this.type.equals(TypeAlgorithm.DERIVATE)) {
            //add the formatter
        }*/
        reset();
        return res;
    }
    
}
