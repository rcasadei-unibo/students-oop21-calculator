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
           this.unaryOpMap.putAll(Map.of("not", new CCUnaryOperator((n1) -> this.not(n1), 1, null)));
           
           this.binaryOpMap.putAll(Map.of("and", new CCBinaryOperator((n1, n2) -> this.and(n1, n2), 1, Type.LEFT),
                                          "or", new CCBinaryOperator((n1, n2) -> this.or(n1, n2), 1, null), //Or has no left to right order
                                          "xor", new CCBinaryOperator((n1, n2) -> this.xor(n1, n2), 1, Type.LEFT), 
                                          "shiftR", new CCBinaryOperator((n1, n2) -> this.shiftR(n1, n2), 1, Type.LEFT), 
                                          "shiftL", new CCBinaryOperator((n1, n2) -> this.shiftL(n1, n2), 1, Type.LEFT)
                                  )); 
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
       private String addLeadingZerosToByte(String stringBits) {
           //1100 = -4 0100 = 4        1.0000.0100 = -4 0.0000.0100 = 4
           final var sign = stringBits.charAt(0);
           stringBits = stringBits.substring(1);
           while(stringBits.length()%8!=0) {
               stringBits = "0".concat(stringBits);
           }
           return String.valueOf(sign).concat(stringBits);
       }
       private double nand(final double n1, final double n2) {
           return not(and(n1, n2));
       }
       private double nor(final double n1, final double n2) {
           return not(or(n1,n2));
       }
       //TODO ror, rol,
       private double roR(final double n1, final double n2) {
           String bits = ConversionAlgorithms.conversionToStringBinary((int) n1);
           final String sign = String.valueOf(bits.charAt(0));
           bits = bits.substring(1);
           //110011 = abs(10011) = roR(x,3) = "011" 10
           bits = bits.substring(bits.length() -1 -(int)n2).concat(bits.substring(0, bits.length() -1 -(int) n2));
           return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
       }
       private double roL(final double n1, final double n2) {
           return 0.0;
       }
}
