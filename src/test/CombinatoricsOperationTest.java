package test;

import static org.junit.Assert.assertEquals;

import model.calculators.CalculatorModel;
import model.calculators.CombinatoricsCalculatorModelFactory;

/**
 * 
 * Test class for the operations of the Combinatorics Calculator based on its model.
 *
 */
public class CombinatoricsOperationTest {

    private final CalculatorModel calculator = CombinatoricsCalculatorModelFactory.create();

    /**
     * Method that test the "Fibonacci" operation.
     */
    @org.junit.Test
    public void fibonacciTest() {
        final var op = this.calculator.getUnaryOpMap().get("fibonacci");
        assertEquals(5.0, op.apply(5.0), 0);
        assertEquals(10946.0, op.apply(21.0), 0);
        assertEquals(1.0, op.apply(2.0), 0);
        assertEquals(0.0, op.apply(0.0), 0);
        assertEquals(75_025.0, op.apply(25.0), 0);
        assertEquals(354_224_848_179_261_915_075.0, op.apply(100.0), 0);
    }

    /**
     * Method that test the "Dispositions" operation.
     */
    @org.junit.Test
    public void fallingFactorialTest() {
        final var op = this.calculator.getBinaryOpMap().get("factorial");
        assertEquals(336.0, op.apply(8, 3), 0);
        assertEquals(0, op.apply(2, 4), 0);
        assertEquals(2432902008176640000.0, op.apply(20, 20), 0);
        assertEquals(1, op.apply(0, 0), 0);
    }

    /**
     * Method that test the "Subsets" operation.
     */
    @org.junit.Test
    public void binomailCoeffTest() {
        final var op = this.calculator.getBinaryOpMap().get("binomialCoefficient");
        assertEquals(56.0, op.apply(8, 3), 0);
        assertEquals(200.0, op.apply(200, 199), 0);
        assertEquals(1, op.apply(6000, 6000), 0);
        assertEquals(0, op.apply(5, 7), 0);
    }

    /**
     * Method that test the "Derangements" operation.
     */
    @org.junit.Test
    public void scombussolamentoTest() {
        final var op = this.calculator.getUnaryOpMap().get("derangement");
        assertEquals(44.0, op.apply(5), 0);
        assertEquals(0.0, op.apply(1), 0);
        assertEquals(4.810_665_157_34E11, op.apply(15), 0);
    }

    /**
     * Method that test the "Partitions" operation.
     */
    @org.junit.Test
    public void bellTest() {
        final var op = this.calculator.getUnaryOpMap().get("bellNumber");
        assertEquals(52.0, op.apply(5), 0);
        assertEquals(877, op.apply(7), 0);
        assertEquals(1.382_958_545E9, op.apply(15), 0);
    }

    /**
     * Method that test the "Partitions(binary)" operation.
     */
    @org.junit.Test
    public void stirlingTest() {
        final var op = this.calculator.getBinaryOpMap().get("stirlingNumber");
        assertEquals(1.0, op.apply(5, 5), 0);
        assertEquals(301.0, op.apply(7, 3), 0);
        assertEquals(1, op.apply(300, 1), 0);
        assertEquals(7_770, op.apply(9, 4), 0);
    }
}
