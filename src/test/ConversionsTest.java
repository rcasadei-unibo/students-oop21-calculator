package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversion;

import utils.ConversionAlgorithms;

/**
 * This class tests conversions.
 */
public class ConversionsTest {
    /**
     * Test for decimal to binary conversions.
     */
    @org.junit.Test
    public void decimalToBinary() {
        assertTrue("01010".equals(ConversionAlgorithms.conversionToStringBinary(10)));
        assertTrue("11010".equals(ConversionAlgorithms.conversionToStringBinary(-10)));    
        assertTrue("01011".equals(ConversionAlgorithms.conversionToStringBinary(11)));
        assertTrue("11011".equals(ConversionAlgorithms.conversionToStringBinary(-11)));
        //System.out.println(ConversionAlgorithms.conversionToStringBinary(Integer.MAX_VALUE));
        //System.out.println(ConversionAlgorithms.conversionToStringBinary(Integer.MIN_VALUE));
    }
    /**
     * Test for decimal to hexadecimal conversions.
     */
    @org.junit.Test
    public void decimalToHexadecimal() {
        assertTrue("0A".equals(ConversionAlgorithms.conversionToStringHexadecimal(10)));
        assertTrue("1A".equals(ConversionAlgorithms.conversionToStringHexadecimal(-10)));
        assertTrue("0FF".equals(ConversionAlgorithms.conversionToStringHexadecimal(255)));
        assertTrue("1FF".equals(ConversionAlgorithms.conversionToStringHexadecimal(-255)));
        assertTrue("0100".equals(ConversionAlgorithms.conversionToStringHexadecimal(256)));
        assertTrue("1100".equals(ConversionAlgorithms.conversionToStringHexadecimal(-256)));
    }
    /**
     * Test for decimal to octal conversions.
     */
    @org.junit.Test
    public void decimalToOctal() {
        assertTrue("07".equals(ConversionAlgorithms.conversionToStringOctal(7)));
        assertTrue("17".equals(ConversionAlgorithms.conversionToStringOctal(-7)));
        assertTrue("010".equals(ConversionAlgorithms.conversionToStringOctal(8)));
        assertTrue("110".equals(ConversionAlgorithms.conversionToStringOctal(-8)));
        assertTrue("070".equals(ConversionAlgorithms.conversionToStringOctal(56)));
        assertTrue("170".equals(ConversionAlgorithms.conversionToStringOctal(-56)));
    }
    /**
     * Test for base to decimal conversions.
     */
    @org.junit.Test
    public void baseToDecimal() {
       assertTrue(10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBinary(10)));
       //assertFalse(10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBinary(-10)));
       assertTrue(-10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBinary(-10)));
       assertTrue(-10==ConversionAlgorithms.conversionToDecimal(16, ConversionAlgorithms.conversionToStringHexadecimal(-10)));
       //System.out.println("-10 1A is "+ConversionAlgorithms.conversionToStringHexadecimal(-10));
        //-256 = 1100
       //System.out.println(ConversionAlgorithms.conversionToDecimal(16, ConversionAlgorithms.conversionToStringHexadecimal(-256)));
       //System.out.println("-256 is "+ ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBinary(-256)));
       assertTrue(80==ConversionAlgorithms.conversionToDecimal(8, ConversionAlgorithms.conversionToStringOctal(80)));
    }

    
}