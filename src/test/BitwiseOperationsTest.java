package test;
import static org.junit.Assert.assertEquals;

import utils.ConversionAlgorithms;
/**
 * 
 * This class tests bitwise operations.
 *
 */
public class BitwiseOperationsTest {
    private static double TOLERANCE = 0.01;
    private double not(final double n1, final double base) {
        final var bits = ConversionAlgorithms.conversionToStringBinary((int) n1).toCharArray();
        String toConvert = String.valueOf(bits[0]);
        for (int i = 1; i < bits.length; i++) {
            if (String.valueOf(bits[i]).equals("1")) {
                toConvert = toConvert.concat("0");
            }
            else {
                toConvert = toConvert.concat("1");
            }
        }
        return ConversionAlgorithms.conversionToDecimal((int) base, toConvert);
    }
    private double and(final double n1, final double n2) {
        return (int) n1 & (int) n2;
    }
    private double or(final double n1, final double n2) {
        return (int) n1 | (int) n2;
    }
    private double xor(final double n1, final double n2) {
        return (int) n1 ^ (int) n2;
    }
    private double shiftR(final double n1, final double n2) {
        return (int) n1 >> (int) n2;
    }
    private double shiftL(final double n1, final double n2) {
        return (int) n1 << (int) n2;
    }
    /**
     * Test for "not" operation.
     */
    @org.junit.Test
    public void testNot() {
        //100000 32     011111 31
        //TODO add test
        //assertEquals(-33.0, not(32.0), TOLERANCE);
        assertEquals(5, not(10,2),TOLERANCE);
        assertEquals(31,not(32,2),TOLERANCE);
        //System.out.println(Integer.MAX_VALUE);
    }
    /**
     * Test for "and" operation.
     */
    @org.junit.Test
    public void testAnd() {
        //(100000 32 and 100000 32) = 100000 32
        assertEquals(32.0, and(32.0, 32.0), TOLERANCE);
        //(100001 33 and 100000 32) = 100000 32
        assertEquals(32.0, and(33.0, 32.0), TOLERANCE);
        //(100001 33 and 100010 34) = 100000 32
        assertEquals(32.0, and(33.0, 34.0), TOLERANCE);
    }
    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testOr() {
        //(100000 32 or 100000 32) = 100000 32
        assertEquals(32.0, or(32.0, 32.0), TOLERANCE);
        //(100001 33 or 100000 32) = 100001 33
        assertEquals(33.0, or(33.0, 32.0), TOLERANCE);
        //(100001 33 or 100010 34) = 100011 35
        assertEquals(35.0, or(33.0, 34.0), TOLERANCE);
    }
    /**
     * Test for "or" operation.
     */
    @org.junit.Test
    public void testXor() {
      //(100000 32 xor 100000 32) = 000000 0
        assertEquals(0.0, xor(32.0, 32.0), TOLERANCE);
        //(100001 33 xor 100000 32) = 000001 1
        assertEquals(1.0, xor(33.0, 32.0), TOLERANCE);
        //(11111 31 xor 00000 0) = 11111 31
        System.out.println(not(31,2));
        assertEquals(31.0, xor(31.0, not(31,2)), TOLERANCE); 
    }
}
