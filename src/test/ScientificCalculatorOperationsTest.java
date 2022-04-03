package test;

import static org.junit.Assert.assertEquals;

import controller.calculators.CombinatoricsCalculatorController;
import controller.calculators.ScientificCalculatorController;
import model.calculators.CalculatorModel;
import model.calculators.ScientificCalculatorModel;
import utils.AbstractCalculator;

/**
 * 
 * 
 *
 */

public class ScientificCalculatorOperationsTest {
    private final CalculatorModel calc = new ScientificCalculatorModel();
    private final AbstractCalculator controller = new ScientificCalculatorController();
    /**
     * 
     */
    @org.junit.Test
    public void logarithmTest() {
        final var ln = this.calc.getUnaryOpMap().get("ln");
        final var log = this.calc.getBinaryOpMap().get("log");
        assertEquals(1.0, log.apply(10.0, 10.0), 0);
        assertEquals(1.0, controller.applyBinaryOperation("log", 10.0, 10.0), 0);
        assertEquals(0.0, log.apply(1.0, 2.0), 0);
        assertEquals(0.0, controller.applyBinaryOperation("log", 1.0, 2.0), 0);
        assertEquals(3.0, log.apply(8.0, 2.0), 0);
        assertEquals(3.0, controller.applyBinaryOperation("log", 8.0, 2.0), 0);
        assertEquals(1.0, ln.apply(Math.exp(1.0)), 0);
        assertEquals(1.0, controller.applyUnaryOperation("ln", Math.exp(1.0)), 0);
    }
    /**
     * 
     */
    @org.junit.Test
    public void modulusTest() {
        final var mod = this.calc.getUnaryOpMap().get("abs");
        assertEquals(10.0, mod.apply(-10.0), 0);
        assertEquals(10.0, controller.applyUnaryOperation("abs", -10.0), 0);
        assertEquals(10.0, mod.apply(10.0), 0);
        assertEquals(10.0, controller.applyUnaryOperation("abs", 10.0), 0);
        assertEquals(-10.0, -mod.apply(10.0), 0);
        assertEquals(-10.0, -controller.applyUnaryOperation("abs", 10.0), 0);
        assertEquals(0.0, mod.apply(0.0), 0);
        assertEquals(0.0, controller.applyUnaryOperation("abs", 0.0), 0);
    }
    /**
     * 
     */
    @org.junit.Test
    public void factorialTest() {
        final var fact = this.calc.getUnaryOpMap().get("factorial");
        assertEquals(120.0, fact.apply(5), 0);
        assertEquals(120.0, controller.applyUnaryOperation("factorial", 5.0), 0);
        assertEquals(120.0, fact.apply(4.56), 0);
        assertEquals(120.0, controller.applyUnaryOperation("factorial", 4.56), 0);
        assertEquals(24.0, fact.apply(4.499), 0);
        assertEquals(24.0, controller.applyUnaryOperation("factorial", 4.499), 0);
        assertEquals(1.0, fact.apply(0.0), 0);
        assertEquals(1.0, controller.applyUnaryOperation("factorial", 0.0), 0);
        assertEquals(1.0, fact.apply(0.56), 0);
        assertEquals(1.0, controller.applyUnaryOperation("factorial", 0.56), 0);
    }
    /**
     * 
     */
    @org.junit.Test
    public void rootTest() {
        final var root = this.calc.getBinaryOpMap().get("nthRoot");
        assertEquals(9.0, root.apply(81.0, 2.0), 0);
        assertEquals(9.0, controller.applyBinaryOperation("nthRoot", 81.0, 2.0), 0);
        assertEquals(1.0, root.apply(1.0, 3.0), 0);
        assertEquals(1.0, controller.applyBinaryOperation("nthRoot", 1.0, 3.0), 0);
        
    }
    /**
     * 
     */
    public void potenzaTest() {
        final var pot = this.calc.getBinaryOpMap().get("potenza");
        assertEquals(25.0, pot.apply(5, 2), 0);
        assertEquals(25.0, controller.applyBinaryOperation("potenza", 5.0, 2.0), 0);
        assertEquals(10000.0, pot.apply(10.0, 4), 0);
        assertEquals(10000.0, controller.applyBinaryOperation("potenza", 10.0, 4.0), 0);
        assertEquals(-1.0, pot.apply(-1, 3), 0);
        assertEquals(-1.0, controller.applyBinaryOperation("potenza", -1.0, 3.0), 0);
        assertEquals(1.0, pot.apply(-1.0, 20), 0);
        assertEquals(1.0, controller.applyBinaryOperation("potenza", -1.0, 20.0), 0);
    }
    

}
