package controller.calculators;

import controller.manager.CCEngine;
import controller.manager.CCManager;
import model.manager.ManagerModelInterface.Calculator;
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

    enum TypeAlgorithm {
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
    

    CalculatorAdvancedController(final CalculatorController controller){
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
     * @param c
     */
    public void read(final String c) {
        this.controller.getManager().read(c);
    }
    
    /**
     * 
     */
    public void reset() {
        this.op.unsetParameters();
        this.controller.getManager().clear();
    }
    
    /**
     * @return s
     */
    public String calculate() {
        final String mem = this.op.setParameters(this.controller.getManager().getCurrentState());
        this.controller.getManager().setCurrentState(mem);
        final var e = this.controller.getManager().getCurrentState().stream().reduce("", (res, s) -> res + s);
        this.expr.setExpr(e);
        final String res = this.op.calculate(expr);
        if (!this.type.equals(TypeAlgorithm.DERIVATE)) {
            //add the formater
        }
        reset();
        return res;
    }
}
