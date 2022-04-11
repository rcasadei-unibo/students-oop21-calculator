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
     * @param c
     */
    public void read(final String c) {
        this.controller.getManager().read(c);
    }
    
    /**
     * 
     */
    public void deleteLast() {
        this.controller.getManager().deleteLast();
    }
    
    /**
     * @return c
     */
    public String getCurrentState() {
        return this.controller.getManager().getCurrentState().stream().reduce("", (s1, s2) -> s1 + s2);
    }
    
    /**
     * 
     */
    public void reset() {
        this.op.unsetParameters();
        this.controller.getManager().clear();
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
     * @throws CalcException 
     */
    public String calculate() throws CalcException {
        //final String mem = this.op.setParameters(this.controller.getManager().getCurrentState());
        //this.controller.getManager().setCurrentState(mem);
        final var e = this.controller.getManager().getCurrentState().stream().reduce("", (res, s) -> res + s);
        this.expr.setExpr(e);
        final String res = this.op.calculate(expr);
        /*if (!this.type.equals(TypeAlgorithm.DERIVATE)) {
            //add the formatter
        }*/
        reset();
        return res;
    }
    
   /* public static void main(String[] args) {
        var man = new CCManager(new CCMainGUI());
        Calculator.ADVANCED.getController().setManager(man);
       var calc = new CalculatorAdvancedController(Calculator.ADVANCED.getController());
       calc.setOperation(TypeAlgorithm.DERIVATE);
       var l = List.of("0", "1");
       var l1 = List.of("x");
       var l2 = List.of("log","(","x",")");
       
       l2.forEach(s -> {
           calc.read(s);
       });
       System.out.println(calc.calculate());
    }*/
}
