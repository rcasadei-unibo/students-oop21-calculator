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
        final var log = this.calc.getBinaryOpMap().get("log");
        assertEquals(1.0, log.apply(10.0, 10.0), 0);
        assertEquals(0.0, log.apply(1.0, 2.0), 0);
        assertEquals(3.0, log.apply(8.0, 2.0), 0);
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
        final var root = this.calc.getBinaryOpMap().get("nthRoot");
        assertEquals(9.0, root.apply(81, 2), 0);
        assertEquals(1.0, root.apply(1, 3), 0);
    }
    /**
     * Method that tests the "^" operator.
     */
    @org.junit.Test
    public void potenzaTest() {
        final var pot = this.calc.getBinaryOpMap().get("^");
        assertEquals(25.0, pot.apply(5.0, 2.0), 0);
        assertEquals(10000.0, pot.apply(10.0, 4), 0);
        assertEquals(-1.0, pot.apply(-1, 3), 0);
        assertEquals(1.0, pot.apply(-1.0, 20), 0);
    }
    /**
     * Method that tests the sin operator "sin".
     */
    @org.junit.Test
    public void sinTest() {
        final var sin = this.calc.getUnaryOpMap().get("sin");
        assertEquals(0, sin.apply(0), 0);
    }
    /**
     * Method that tests the cos operator "cos".
     */
    @org.junit.Test
    public void cosTest() {

    }
    /**
     * Method that tests the tan operator "tan".
     */
    @org.junit.Test
    public void tanTest() {

    }

}
