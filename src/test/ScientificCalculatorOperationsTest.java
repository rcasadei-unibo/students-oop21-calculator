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
     * 
     */
    @org.junit.Test
    public void rootTest() {
        final var root = this.calc.getBinaryOpMap().get("nthRoot");
        assertEquals(9.0, root.apply(81, 2), 0);
        assertEquals(1.0, root.apply(1, 3), 0);
    }
    /**
     * 
     */
    public void potenzaTest() {
        final var pot = this.calc.getBinaryOpMap().get("potenza");
        assertEquals(25.0, pot.apply(5, 2), 0);
        assertEquals(10000.0, pot.apply(10.0, 4), 0);
        assertEquals(-1.0, pot.apply(-1, 3), 0);
        assertEquals(1.0, pot.apply(-1.0, 20), 0);
    }

}
