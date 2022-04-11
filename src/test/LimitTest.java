package test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import controller.manager.CCEngine;
import model.manager.ManagerModelInterface.Calculator;
import utils.CalcException;
import utils.calculate.Expression;
import utils.calculate.Limit;


/**
 * @author pesic
 *
 */
public class LimitTest {
    private CCEngine engine = new CCEngine(Calculator.ADVANCED.getController());
    private Expression expr = new Expression();

    private void init() {
        expr.setEngine(engine);
    }

    @org.junit.Test
    public void testDerivate() throws CalcException {
        init();
        final var limit = new Limit();
        limit.setParameters(new LinkedList<String>(List.of("0.0001")));
        expr.setExpr("x");
        assertEquals(limit.calculate(expr), 0.0, 0.01);
        expr.setExpr("sin(x)/x");
        assertEquals(limit.calculate(expr), 1.0, 0.01);
        
    }
}
