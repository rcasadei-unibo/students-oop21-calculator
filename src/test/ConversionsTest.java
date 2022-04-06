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
        assertTrue("+1010".equals(ConversionAlgorithms.conversionToStringBase(2,(10))));
        assertTrue("-1010".equals(ConversionAlgorithms.conversionToStringBase(2,(-10))));    
        assertTrue("+1011".equals(ConversionAlgorithms.conversionToStringBase(2,(11))));
        assertTrue("-1011".equals(ConversionAlgorithms.conversionToStringBase(2,(-11))));
        //System.out.println(ConversionAlgorithms.conversionToStringBase(2,(Integer.MAX_VALUE));
        //System.out.println(ConversionAlgorithms.conversionToStringBase(2,(Integer.MIN_VALUE));
    }
    /**
     * Test for decimal to hexadecimal conversions.
     */
    @org.junit.Test
    public void decimalToHexadecimal() {
        assertTrue("+A".equals(ConversionAlgorithms.conversionToStringBase(16,(10))));
        assertTrue("-A".equals(ConversionAlgorithms.conversionToStringBase(16,(-10))));
        assertTrue("+FF".equals(ConversionAlgorithms.conversionToStringBase(16,(255))));
        assertTrue("-FF".equals(ConversionAlgorithms.conversionToStringBase(16,(-255))));
        assertTrue("+100".equals(ConversionAlgorithms.conversionToStringBase(16,(256))));
        assertTrue("-100".equals(ConversionAlgorithms.conversionToStringBase(16,(-256))));
    }
    /**
     * Test for decimal to octal conversions.
     */
    @org.junit.Test
    public void decimalToOctal() {
        assertTrue("+7".equals(ConversionAlgorithms.conversionToStringBase(8,(7))));
        assertTrue("-7".equals(ConversionAlgorithms.conversionToStringBase(8,(-7))));
        assertTrue("+10".equals(ConversionAlgorithms.conversionToStringBase(8,(8))));
        assertTrue("-10".equals(ConversionAlgorithms.conversionToStringBase(8,(-8))));
        assertTrue("+70".equals(ConversionAlgorithms.conversionToStringBase(8,(56))));
        assertTrue("-70".equals(ConversionAlgorithms.conversionToStringBase(8,(-56))));
    }
    /**
     * Test for base to decimal conversions.
     */
    @org.junit.Test
    public void baseToDecimal() {
       assertTrue(10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBase(2,(10))));
       //assertFalse(10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBase(2,(-10)));
       assertTrue(-10==ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBase(2,(-10))));
       assertTrue(-10==ConversionAlgorithms.conversionToDecimal(16, ConversionAlgorithms.conversionToStringBase(16,(-10))));
       //System.out.println("-10 1A is "+ConversionAlgorithms.conversionToStringBase(16,(-10));
        //-256 = 1100
       //System.out.println(ConversionAlgorithms.conversionToDecimal(16, ConversionAlgorithms.conversionToStringBase(16,(-256)));
       //System.out.println("-256 is "+ ConversionAlgorithms.conversionToDecimal(2, ConversionAlgorithms.conversionToStringBase(2,(-256)));
       assertTrue(80==ConversionAlgorithms.conversionToDecimal(8, ConversionAlgorithms.conversionToStringBase(8,(80))));
    }
    /**
     * Test for base to decimal conversions.
     */
    @org.junit.Test
    public void baseToUnsignedDecimal() {
       
       assertTrue(15==ConversionAlgorithms.unsignedConversionToDecimal(2, "1111"));
       assertTrue(63==ConversionAlgorithms.unsignedConversionToDecimal(8, "77"));
       assertTrue(255==ConversionAlgorithms.unsignedConversionToDecimal(8, "FF"));
       
    }
    
    

    
}