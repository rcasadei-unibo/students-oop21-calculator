package test;

import static org.junit.Assert.assertEquals;
import model.calculators.CalculatorModel;
import model.calculators.ScientificCalculatorModelFactory;
/**
 * 
 * 
 *
 */
public class ScientificCalculatorOperationsTest {
    private final CalculatorModel calc = ScientificCalculatorModelFactory.create();
    /**
     * Method that tests the natural logarithm operator "ln" and the
     * binary logarithm operatior "log".
     * 
     */
    @org.junit.Test
    public void logarithmTest() {
        final var ln = this.calc.getUnaryOpMap().get("ln");
        final var log = this.calc.getUnaryOpMap().get("log");
        assertEquals(1.0, log.apply(10.0), 0);
        assertEquals(0.0, log.apply(1.0), 0);
        assertEquals(2.0, log.apply(100), 0);
        assertEquals(1.0, ln.apply(Math.exp(1.0)), 0);
    }
    /**
     * 
     * Method that tests the absolute value operator "abs".
     * 
     */
    @org.junit.Test
    public void modulusTest() {
        final var mod = this.calc.getUnaryOpMap().get("abs");
        assertEquals(10.0, mod.apply(-10.0), 0);
        assertEquals(10.0, mod.apply(10.0), 0);
        assertEquals(-10.0, -mod.apply(10.0), 0);
        assertEquals(0.0, mod.apply(0.0), 0);
    }
    /**
     * 
     * Method that tests the factorial operator "!".
     * 
     */
    @org.junit.Test
    public void factorialTest() {
        final var fact = this.calc.getUnaryOpMap().get("factorial");
        assertEquals(120.0, fact.apply(5), 0);
        assertEquals(120.0, fact.apply(4.56), 0);
        assertEquals(24.0, fact.apply(4.499), 0);
        assertEquals(1.0, fact.apply(0.0), 0);
        assertEquals(1.0, fact.apply(0.56), 0);
    }
    /**
     * Method that tests the "nthRoot" operator.
     */
    @org.junit.Test
    public void rootTest() {
        final var root = this.calc.getBinaryOpMap().get("root");
        assertEquals(9.0, root.apply(2, 81), 0);
        assertEquals(1.0, root.apply(3, 1), 0);
    }
    /**
     * Method that tests the "^" operator.
     */
    @org.junit.Test
    public void potenzaTest() {
        final var pot = this.calc.getBinaryOpMap().get("^");
        assertEquals(25.0, pot.apply(5.0, 2.0), 0);
        assertEquals(10_000.0, pot.apply(10.0, 4), 0);
        assertEquals(-1.0, pot.apply(-1, 3), 0);
        assertEquals(1.0, pot.apply(-1.0, 20), 0);
    }
    /**
     * Method that tests the operator "sin".
     */
    @org.junit.Test
    public void sinTest() {
        final var sin = this.calc.getUnaryOpMap().get("sin");
        assertEquals(0, sin.apply(0), 0);
        assertEquals(0.841_4, sin.apply(1), 0.0001);
        assertEquals(-0.841_4, sin.apply(-1), 0.0001);
    }
    /**
     * Method that tests the operator "cos".
     */
    @org.junit.Test
    public void cosTest() {
        final var cos = this.calc.getUnaryOpMap().get("cos");
        assertEquals(1, cos.apply(0), 0);
        assertEquals(0.540_3, cos.apply(1), 0.0001);
        assertEquals(0.540_3, cos.apply(-1), 0.0001);
    }
    /**
     * Method that tests the operator "tan".
     */
    @org.junit.Test
    public void tanTest() {
        final var tan = this.calc.getUnaryOpMap().get("tan");
        assertEquals(0, tan.apply(0), 0);
        assertEquals(1.557_4, tan.apply(1), 0.0001);
        assertEquals(-1.557_4, tan.apply(-1), 0.0001);
    }
    /**
     * Method that tests the operator "csc".
     */
    @org.junit.Test
    public void cscTest() {
        final var csc = this.calc.getUnaryOpMap().get("csc");
        assertEquals(1.188_3, csc.apply(1), 0.0001);
        assertEquals(-1.188_3, csc.apply(-1), 0.0001);
        assertEquals(100, csc.apply(0.01), 1);
    }
    /**
     * Method that tests the operator "sec".
     */
    @org.junit.Test
    public void secTest() {
        final var sec = this.calc.getUnaryOpMap().get("sec");
        assertEquals(1.850_8, sec.apply(1), 0.0001);
        assertEquals(1.850_8, sec.apply(-1), 0.0001);
        assertEquals(1.00, sec.apply(0.01), 0.001);
    }
    /**
     * Method that tests the operator "cot".
     */
    @org.junit.Test
    public void cotTest() {
        final var cot = this.calc.getUnaryOpMap().get("cot");
        assertEquals(0.642_0, cot.apply(1), 0.0001);
        assertEquals(-0.6420, cot.apply(-1), 0.0001);
        assertEquals(99.996, cot.apply(0.01), 0.001);
    }
}
