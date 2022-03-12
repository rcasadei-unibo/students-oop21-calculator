package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import util.ConversionAlgorithms;

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
        assertEquals(List.of(10), ConversionAlgorithms.conversionToHexadecimal(10));
        assertEquals(List.of(15), ConversionAlgorithms.conversionToHexadecimal(15));
        assertEquals(List.of(15, 15), ConversionAlgorithms.conversionToHexadecimal(255)); 
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
    /**
     * Test for decimal to octal conversions.
     */
    @org.junit.Test
    public void BaseToDecimal() {
       var Binary10 = ConversionAlgorithms.conversionToBinary(10);
       var Octal10 = ConversionAlgorithms.conversionToOctal(10);
       var Hexa10 = ConversionAlgorithms.conversionToHexadecimal(10);
       assertEquals(10,ConversionAlgorithms.conversionToDecimal(2, Binary10));
       assertEquals(10,ConversionAlgorithms.conversionToDecimal(8, Octal10));
       assertEquals(10,ConversionAlgorithms.conversionToDecimal(16, Hexa10));
       var Binary256 = ConversionAlgorithms.conversionToBinary(256);
       var Octal256 = ConversionAlgorithms.conversionToOctal(256);
       var Hexa256 = ConversionAlgorithms.conversionToHexadecimal(256);
       assertEquals(256,ConversionAlgorithms.conversionToDecimal(2, Binary256));
       assertEquals(256,ConversionAlgorithms.conversionToDecimal(8, Octal256));
       assertEquals(256,ConversionAlgorithms.conversionToDecimal(16, Hexa256));
    }
    
    
}