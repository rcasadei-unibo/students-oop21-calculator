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
           this.binaryOpMap.putAll(Map.of("and", new CCBinaryOperator((n1, n2) -> this.and(n1, n2), 1, Type.LEFT),
                                          "or", new CCBinaryOperator((n1, n2) -> this.or(n1, n2), 1, null), //Or has no left to right order
                                          "xor", new CCBinaryOperator((n1, n2) -> this.xor(n1, n2), 1, Type.LEFT), 
                                          "shiftR", new CCBinaryOperator((n1, n2) -> this.shiftR(n1, n2), 1, Type.LEFT), 
                                          "shiftL", new CCBinaryOperator((n1, n2) -> this.shiftL(n1, n2), 1, Type.LEFT),
                                          "not", new CCBinaryOperator((n1, n2) -> this.not(n1, n2), 1, Type.LEFT)
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
       //TODO add tests
       private double shiftR(final double n1, final double n2) {
           return (int) n1 >> (int) n2;
       }
       private double shiftL(final double n1, final double n2) {
           return (int) n1 << (int) n2;
       }
       //TODO approx to the nearest byte
       //as of this version 11111 becomes 00000
       //to approx to 00011111 becomes 11100000
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
       //TODO ror, rol, nand, nor
}
