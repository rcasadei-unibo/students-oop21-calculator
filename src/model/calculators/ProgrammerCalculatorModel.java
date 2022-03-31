package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.ConversionAlgorithms;
import utils.Type;
//TODO MISSING JAVADOC.
/**
 * MISSING JAVADOC.
 *
 */
public class ProgrammerCalculatorModel extends CalculatorModel {
    //TODO MISSING JAVADOC.
    /**
     * MISSING JAVADOC. 
     */
       public ProgrammerCalculatorModel() {
           super(Map.of("and", new CCBinaryOperator((n1, n2) -> and(n1, n2), 1, Type.LEFT),
                                          "or", new CCBinaryOperator((n1, n2) -> or(n1, n2), 1, null), //Or has no left to right order
                                          "xor", new CCBinaryOperator((n1, n2) -> xor(n1, n2), 1, Type.LEFT), 
                                          "shiftR", new CCBinaryOperator((n1, n2) -> shiftR(n1, n2), 1, Type.LEFT), 
                                          "shiftL", new CCBinaryOperator((n1, n2) -> shiftL(n1, n2), 1, Type.LEFT),
                                          "nand",  new CCBinaryOperator((n1, n2) -> nand(n1, n2), 1, Type.LEFT),
                                          "nor",  new CCBinaryOperator((n1, n2) -> nor(n1, n2), 1, Type.LEFT),
                                          "roR",  new CCBinaryOperator((n1, n2) -> roR(n1, n2), 1, Type.LEFT),
                                          "roL",  new CCBinaryOperator((n1, n2) -> roL(n1, n2), 1, Type.LEFT)
                                  ),
                 Map.of("not", new CCUnaryOperator((n1) -> not(n1), 1, null))); 
       }
       private static double and(final double n1, final double n2) {
           return (int) n1 & (int) n2;
       }
       private static double or(final double n1, final double n2) {
           return (int) n1 | (int) n2;
       }
       private static double xor(final double n1, final double n2) {
           return (int) n1 ^ (int) n2;
       }
       private static double shiftR(final double n1, final double n2) {
           return (int) n1 >> (int) n2;
       }
       private static double shiftL(final double n1, final double n2) {
           return (int) n1 << (int) n2;
       }
       private static double not(final double n1) {
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
       private static String addLeadingZerosToByte(String stringBits) {
           //1100 = -4 0100 = 4        1.0000.0100 = -4 0.0000.0100 = 4
           final var sign = stringBits.charAt(0);
           stringBits = stringBits.substring(1);
           while(stringBits.length()%8!=0) {
               stringBits = "0".concat(stringBits);
           }
           return String.valueOf(sign).concat(stringBits);
       }
       private static double nand(final double n1, final double n2) {
           return not(and(n1, n2));
       }
       private static double nor(final double n1, final double n2) {
           return not(or(n1,n2));
       }
       private static double roR(final double n1, double n2) {
           String bits = addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBinary((int) n1));
           if(bits.length()-1<n2) {
               n2 = n2%8;   //in case it is asked to roll 9 on a byte it rolls 9-8    
           }
           final String sign = String.valueOf(bits.charAt(0));
           bits = bits.substring(1);
           //110011 = abs(10011) = roR(x,3) = "011" 10
           
           bits = bits.substring(bits.length() - (int) n2).concat(bits.substring(0, bits.length() - (int) n2));
           return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
       }
       
       private static double roL(final double n1, double n2) {
           String bits = addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBinary((int) n1));
           if(bits.length()-1<n2) {
               n2 = n2%8;   //in case it is asked to roll 9 on a byte it rolls 9-8    
           }
           
           final String sign = String.valueOf(bits.charAt(0));
           bits = bits.substring(1);
           //110011 = abs(10011) = roL(x,3) = "100" 11 = 11"100"
           //10011 = 10'011'
           bits = bits.substring((int) n2).concat(bits.substring(0,(int) n2));
                   
           //bits.substring(0, (int) n2).concat(bits.substring((int) n2));
           return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
       }
}
