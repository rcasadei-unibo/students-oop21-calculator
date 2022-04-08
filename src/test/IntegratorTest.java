package test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import controller.manager.CCEngine;
import model.manager.ManagerModelInterface.Calculator;
import utils.calculate.Expression;
import utils.calculate.Integrator;

/**
 * @author pesic
 *
 */
public class IntegratorTest {
    private CCEngine engine = new CCEngine(Calculator.ADVANCED.getController());
    private Expression expr = new Expression();

    private void init() {
        expr.setEngine(engine);
    }
    
    @org.junit.Test
    public void testIntegrator() {
        init();
        final var integrator = new Integrator();
        integrator.setParameters(new LinkedList<String>(List.of("0.0", "1.0")));
        expr.setExpr("1.0");
        assertEquals(integrator.calculate(expr), 1.0, 0.01);
        expr.setExpr("x");
        assertEquals(integrator.calculate(expr), 0.5, 0.01);
        
    }
}
