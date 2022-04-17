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
        expr.setExpr("sin(x)");
        assertEquals(der.calculate(expr), "cos(x)");
        expr.setExpr("cos(x)");
        assertEquals(der.calculate(expr), "-sin(x)");
        expr.setExpr("tan(x)");
        assertEquals(der.calculate(expr), "(1.0)÷((cos(x))^(2.0))");
        expr.setExpr("csc(x)");
        assertEquals(der.calculate(expr), "-(cot(x))\u00D7(csc(x))");
        expr.setExpr("sec(x)");
        assertEquals(der.calculate(expr), "(tan(x))\u00D7(sec(x))");
        expr.setExpr("cot(x)");
        assertEquals(der.calculate(expr), "-(1.0)÷((sin(x))^(2.0))");
        expr.setExpr("log(x)");
        assertEquals(der.calculate(expr), "(1.0)÷(x)");
        expr.setExpr("(x)^2");
        assertEquals(der.calculate(expr), "((x)^(2.0))\u00D7((2.0)÷(x))");
        expr.setExpr("abs(x)");
        assertEquals(der.calculate(expr), "(x)÷(abs(x))");
        
    }
}
