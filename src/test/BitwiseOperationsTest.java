package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.calculators.CalculatorModel;
import model.calculators.ProgrammerCalculatorModelFactory;
import utils.ConversionAlgorithms;

/**
 * 
 * This class tests bitwise operations.
 *
 */
public class BitwiseOperationsTest {
    final private static double TOLERANCE = 0.01;
    final private CalculatorModel calculator = ProgrammerCalculatorModelFactory.create();

    private String addLeadingZerosToByte(String stringBits) {
        // 1100 = -4 0100 = 4 1.0000.0100 = -4 0.0000.0100 = 4
        final var sign = stringBits.charAt(0);
        stringBits = stringBits.substring(1);
        while (stringBits.length() % 8 != 0) {
            stringBits = "0".concat(stringBits);
        }
        return String.valueOf(sign).concat(stringBits);
    }

    /**
     * private test for leadingZeros.
     */
    @org.junit.Test
    public void testByte() {
        assertTrue("+00000100".equals(this.addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBase(2, 4))));
        assertTrue("-00000100".equals(this.addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBase(2, -4))));
    }

    /**
     * Test for "not" operation.
     */
    @org.junit.Test
    public void testNot() {
        final var op = this.calculator.getUnaryOpMap().get("not");
        assertEquals("-11111011", ConversionAlgorithms.conversionToStringBase(2, (long) op.apply(-4.0)));
        assertEquals("+11111011", ConversionAlgorithms.conversionToStringBase(2, (long) op.apply(4.0)));
        assertEquals("+11111111", ConversionAlgorithms.conversionToStringBase(2, (long) op.apply(0.0)));

    }

    /**
     * Test for "and" operation.
     */
    @org.junit.Test
    public void testAnd() {
        final var op = this.calculator.getBinaryOpMap().get("and");
        // (100000 32 op.apply 100000 32) = 100000 32
        assertEquals(32.0, op.apply(32.0, 32.0), TOLERANCE);
        // (100001 33 op.apply 100000 32) = 100000 32
        assertEquals(32.0, op.apply(33.0, 32.0), TOLERANCE);
        // (100001 33 op.apply 100010 34) = 100000 32
        assertEquals(32.0, op.apply(33.0, 34.0), TOLERANCE);
    }

    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testOr() {
        final var op = this.calculator.getBinaryOpMap().get("or");
        // (100000 32 or 100000 32) = 100000 32
        assertEquals(32.0, op.apply(32.0, 32.0), TOLERANCE);
        // (100001 33 or 100000 32) = 100001 33
        assertEquals(33.0, op.apply(33.0, 32.0), TOLERANCE);
        // (100001 33 or 100010 34) = 100011 35
        assertEquals(35.0, op.apply(33.0, 34.0), TOLERANCE);
        //
        assertEquals(255, op.apply(128, 127), TOLERANCE);
    }

    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testXor() {
        final var op = this.calculator.getBinaryOpMap().get("xor");
        // (100000 32 xor 100000 32) = 000000 0
        assertEquals(0.0, op.apply(32.0, 32.0), TOLERANCE);
        // (100001 33 xor 100000 32) = 000001 1
        assertEquals(1.0, op.apply(33.0, 32.0), TOLERANCE);
        // (11111 31 xor 11100000 0) = 11111111 255
        final var not = this.calculator.getUnaryOpMap().get("not");
        assertEquals(255.0, op.apply(31.0, not.apply(31)), TOLERANCE);
    }

    @org.junit.Test
    public void testShiftL() {
        final var op = this.calculator.getBinaryOpMap().get("shiftL");
        assertEquals(25 * 2, op.apply(25, 1), TOLERANCE);
        assertEquals(25 * 4, op.apply(25, 2), TOLERANCE);
        assertEquals(25 * 32, op.apply(25, 5), TOLERANCE);
    }

    @org.junit.Test
    public void testShiftR() {
        final var op = this.calculator.getBinaryOpMap().get("shiftR");
        // 11001 = 25 1100 = 8
        assertEquals((long) 25 / 2, op.apply(25, 1), TOLERANCE);
        assertEquals((long) 25 / 4, op.apply(25, 2), TOLERANCE);
        // 11001 11
        assertEquals((long) 25 / 8, op.apply(25, 3), TOLERANCE);
        // 1
        assertEquals((long) 25 / 16, op.apply(25, 4), TOLERANCE);
        // 0
        assertEquals((long) 25 / 32, op.apply(25, 5), TOLERANCE);
        // 0
        assertEquals((long) 25 / 64, op.apply(25, 6), TOLERANCE);
    }

    @org.junit.Test
    public void testroR() {
        final var op = this.calculator.getBinaryOpMap().get("roR");

        assertEquals(-64 - 32 - 1, op.apply(-11, 3), TOLERANCE);
        assertEquals(-11, op.apply(-11, 0), TOLERANCE);
        // -1011,1 = - (1)(101) = -13
        assertEquals(-128 - 4 - 1, op.apply(-11, 1), TOLERANCE);
        assertEquals(-128 - 4 - 1, op.apply(-11, 9), TOLERANCE);
    }

    @org.junit.Test
    public void testroL() {
        final var op = this.calculator.getBinaryOpMap().get("roL");
        assertEquals(88, op.apply(11, 3), TOLERANCE);
    }

    @org.junit.Test
    public void testNor() {
        final var op = this.calculator.getBinaryOpMap().get("nor");
        // 10111 or 01111 = 11111 = (111)00000
        assertEquals(128 + 64 + 32, op.apply(23, 15), TOLERANCE);
    }

    @org.junit.Test
    public void testNand() {
        final var op = this.calculator.getBinaryOpMap().get("nand");
        // 10111 and 01111 = 00111 (111)11000
        assertEquals(255 - 7, op.apply(23, 15), TOLERANCE);
    }
}
