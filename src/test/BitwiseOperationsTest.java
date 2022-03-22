package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.calculators.ProgrammerCalculatorModel;
import utils.ConversionAlgorithms;
/**
 * 
 * This class tests bitwise operations.
 *
 */
public class BitwiseOperationsTest {
    final private static double TOLERANCE = 0.01;
    final private ProgrammerCalculatorModel calculator = new ProgrammerCalculatorModel();
    private String addLeadingZerosToByte(String stringBits) {
        //1100 = -4 0100 = 4        1.0000.0100 = -4 0.0000.0100 = 4
        final var sign = stringBits.charAt(0);
        stringBits = stringBits.substring(1);
        while(stringBits.length()%8!=0) {
            stringBits = "0".concat(stringBits);
        }
        return String.valueOf(sign).concat(stringBits);
    }
    /**
     * private test for leadingZeros.
     */
    @org.junit.Test
    public void testByte() {
        assertTrue("000000100".equals(this.addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBinary(4))));
        assertTrue("100000100".equals(this.addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBinary(-4))));
        
    }
    /**
     * Test for "not" operation.
     */
    @org.junit.Test
    public void testNot() {
        final var op = this.calculator.getUnaryOpMap().get("not");
        //TODO add test
        assertEquals("111111011",ConversionAlgorithms.conversionToStringBinary((int)op.apply(-4.0)));
        assertEquals("011111011",ConversionAlgorithms.conversionToStringBinary((int)op.apply(4.0)));
        assertEquals("111111111",ConversionAlgorithms.conversionToStringBinary((int)op.apply(0.0)));

    }
    /**
     * Test for "and" operation.
     */
    @org.junit.Test
    public void testAnd() {
        final var op = this.calculator.getBinaryOpMap().get("and");
        //(100000 32 op.apply 100000 32) = 100000 32
        assertEquals(32.0, op.apply(32.0, 32.0), TOLERANCE);
        //(100001 33 op.apply 100000 32) = 100000 32
        assertEquals(32.0, op.apply(33.0, 32.0), TOLERANCE);
        //(100001 33 op.apply 100010 34) = 100000 32
        assertEquals(32.0, op.apply(33.0, 34.0), TOLERANCE);
    }
    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testOr() {
        final var op = this.calculator.getBinaryOpMap().get("or");
        //(100000 32 or 100000 32) = 100000 32
        assertEquals(32.0, op.apply(32.0, 32.0), TOLERANCE);
        //(100001 33 or 100000 32) = 100001 33
        assertEquals(33.0, op.apply(33.0, 32.0), TOLERANCE);
        //(100001 33 or 100010 34) = 100011 35
        assertEquals(35.0, op.apply(33.0, 34.0), TOLERANCE);
        //
        assertEquals(255, op.apply(128,127), TOLERANCE);
    }
    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testXor() {
        final var op = this.calculator.getBinaryOpMap().get("xor");
      //(100000 32 xor 100000 32) = 000000 0
        assertEquals(0.0, op.apply(32.0, 32.0), TOLERANCE);
        //(100001 33 xor 100000 32) = 000001 1
        assertEquals(1.0, op.apply(33.0, 32.0), TOLERANCE);
        //(11111 31 xor 11100000 0) = 11111111 255
        //System.out.println(not(31,2));
        final var not = this.calculator.getUnaryOpMap().get("not");
        assertEquals(255.0, op.apply(31.0, not.apply(31)), TOLERANCE); 
    }
    @org.junit.Test
    public void testShiftL() {
        final var op = this.calculator.getBinaryOpMap().get("shiftL");
        assertEquals(25*2,op.apply(25, 1),TOLERANCE);
        assertEquals(25*4,op.apply(25, 2),TOLERANCE);
        assertEquals(25*32,op.apply(25, 5),TOLERANCE);
    }
    @org.junit.Test
    public void testShiftR() {
        final var op = this.calculator.getBinaryOpMap().get("shiftR");
        //11001 = 25      1100 = 8
        assertEquals((int)25/2,op.apply(25, 1),TOLERANCE);
        assertEquals((int)25/4,op.apply(25, 2),TOLERANCE);
        //11001         11
        assertEquals((int)25/8,op.apply(25, 3),TOLERANCE);
        //1
        assertEquals((int)25/16,op.apply(25, 4),TOLERANCE);
        //0
        assertEquals((int)25/32,op.apply(25, 5),TOLERANCE);
        //0
        assertEquals((int)25/64,op.apply(25, 6),TOLERANCE);
    }
    //TODO nand roL tests
    @org.junit.Test
    public void testroR() {
        final var op = this.calculator.getBinaryOpMap().get("roR");
        // ror(-11,3) = - 7
        // -1011,3 = - (011)(1) = -7 
        assertEquals(-7,op.apply(-11, 3),TOLERANCE);
        assertEquals(-11,op.apply(-11, 0),TOLERANCE);
        // -1011,1 = - (1)(101) = -13 
        assertEquals(-13,op.apply(-11, 1),TOLERANCE);
        
    }
}
