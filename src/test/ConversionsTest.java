package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import util.ConversionAlgorithms;
//System.out.println("16 converts to : " + ConversionAlgorithms.conversionToHexadecimal(16));
/**
 * This class tests conversions.
 */
public class ConversionsTest {
    /**
     * Test for decimal to binary conversions.
     */
    @org.junit.Test
    public void decimalToBinary() {
        assertEquals(List.of(1, 0, 1, 0), ConversionAlgorithms.conversionToBinary(10));
        assertEquals(List.of(1, 0, 0, 0, 1), ConversionAlgorithms.conversionToBinary(17));
     }
    /**
     * Test for decimal to hexadecimal conversions.
     */
    @org.junit.Test
    public void decimalToHexadecimal() {
        assertEquals(List.of("A"), ConversionAlgorithms.conversionToHexadecimal(10));
        assertEquals(List.of("F"), ConversionAlgorithms.conversionToHexadecimal(15));
        assertEquals(List.of("F", "F"), ConversionAlgorithms.conversionToHexadecimal(255)); 
    }
    /**
     * Test for decimal to octal conversions.
     */
    @org.junit.Test
    public void decimalToOctal() {
        assertEquals(List.of(1, 2), ConversionAlgorithms.conversionToOctal(10));
        assertEquals(List.of(1, 7), ConversionAlgorithms.conversionToOctal(15));
        assertEquals(List.of(2, 0), ConversionAlgorithms.conversionToOctal(16)); 
    }
}