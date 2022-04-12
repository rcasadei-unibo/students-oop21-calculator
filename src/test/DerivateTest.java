package test;

import static org.junit.Assert.assertEquals;

import controller.manager.CCEngine;
import model.manager.EngineModelInterface.Calculator;
import utils.CalcException;
import utils.calculate.Derivate;
import utils.calculate.Expression;

/**
 * @author pesic
 *
 */
public class DerivateTest {

    private CCEngine engine = new CCEngine(Calculator.ADVANCED.getController());
    private Expression expr = new Expression();

    private void init() {
        expr.setEngine(engine);
    }

    @org.junit.Test
    public void testDerivate() throws CalcException {
        init();
        final var der = new Derivate();
        expr.setExpr("x");
        assertEquals(der.calculate(expr), "1.0");
        expr.setExpr("1.0");
        assertEquals(der.calculate(expr), "0.0");
        expr.setExpr("3x");
        assertEquals(der.calculate(expr), "3.0");
        expr.setExpr("3x+5");
        assertEquals(der.calculate(expr), "3.0");
        expr.setExpr("3x/5");
        assertEquals(der.calculate(expr), "(3.0)");
    }
}
