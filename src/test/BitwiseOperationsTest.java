package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import utils.ConversionAlgorithms;
/**
 * 
 * This class tests bitwise operations.
 *
 */
public class BitwiseOperationsTest {
    private static double TOLERANCE = 0.01;
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
    private double not(final double n1) {
        var stringBits = ConversionAlgorithms.conversionToStringBinary((int) n1);
        stringBits=addLeadingZerosToByte(stringBits);
        
        final var bits = stringBits.toCharArray();
        String toConvert = String.valueOf(bits[0]);
        for (int i = 1; i < bits.length; i++) {
            if (String.valueOf(bits[i]).equals("1")) {
                toConvert = toConvert.concat("0");
            } else {
                toConvert = toConvert.concat("1");
            }
        }
        return ConversionAlgorithms.conversionToDecimal(2, toConvert);
    }
    private double not(final double n1, final double base) {
        var stringBits = ConversionAlgorithms.conversionToStringBinary((int) n1);
        stringBits=addLeadingZerosToByte(stringBits);
        
        final var bits = stringBits.toCharArray();
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
    //TODO ror, rol, nand, nor

 private String addLeadingZerosToByte(String stringBits) {
     //1100 = -4 0100 = 4        1.0000.0100 = -4 0.0000.0100 = 4
     final var sign = stringBits.charAt(0);
     stringBits = stringBits.substring(1);
     while(stringBits.length()%8!=0) {
         stringBits = "0".concat(stringBits);
     }
     return String.valueOf(sign).concat(stringBits);
 }
 private double roR(final double n1, final double n2) {
     String bits = ConversionAlgorithms.conversionToStringBinary((int) n1);
     final String sign = String.valueOf(bits.charAt(0));
     bits = bits.substring(1);
     System.out.println("abs : " + bits + " roll of :" + n2);
     System.out.println("roll: " + bits.substring(bits.length() - (int) n2));
     System.out.println("conc: " + bits.substring(0, bits.length() - (int) n2));
     System.out.println("res : " + bits.substring(bits.length() - (int) n2).concat(bits.substring(0, bits.length() - (int) n2)));
     //110011 = abs(10011) = roR(x,3) = "011" 10
     
     bits = bits.substring(bits.length() - (int) n2).concat(bits.substring(0, bits.length() - (int) n2));
     return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
 }
    /**
     * private test for leadingZeros
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
        
        //TODO add test
        assertEquals("111111011",ConversionAlgorithms.conversionToStringBinary((int)not(-4.0)));
        assertEquals("011111011",ConversionAlgorithms.conversionToStringBinary((int)not(4.0)));
        assertEquals("111111111",ConversionAlgorithms.conversionToStringBinary((int)not(0.0)));

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
        //
        assertEquals(255, or(128,127), TOLERANCE);
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
        //(11111 31 xor 11100000 0) = 11111111 255
        //System.out.println(not(31,2));
        assertEquals(255.0, xor(31.0, not(31,2)), TOLERANCE); 
    }
    @org.junit.Test
    public void testShiftL() {
        assertEquals(25*2,this.shiftL(25, 1),TOLERANCE);
        assertEquals(25*4,this.shiftL(25, 2),TOLERANCE);
        assertEquals(25*32,this.shiftL(25, 5),TOLERANCE);
    }
    @org.junit.Test
    public void testShiftR() {
        //11001 = 25      1100 = 8
        assertEquals((int)25/2,this.shiftR(25, 1),TOLERANCE);
        assertEquals((int)25/4,this.shiftR(25, 2),TOLERANCE);
        //11001         11
        assertEquals((int)25/8,this.shiftR(25, 3),TOLERANCE);
        //1
        assertEquals((int)25/16,this.shiftR(25, 4),TOLERANCE);
        //0
        assertEquals((int)25/32,this.shiftR(25, 5),TOLERANCE);
        //0
        assertEquals((int)25/64,this.shiftR(25, 6),TOLERANCE);
    }
    //TODO nand nor roL tests
    @org.junit.Test
    public void testroR() {
        // ror(-11,3) = - 7
        // -1011,3 = - (011)(1) = -7 
        assertEquals(-7,this.roR(-11, 3),TOLERANCE);
        assertEquals(-11,this.roR(-11, 0),TOLERANCE);
        // -1011,1 = - (1)(101) = -13 
        assertEquals(-13,this.roR(-11, 1),TOLERANCE);
        
    }
}
