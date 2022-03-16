package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;

/**
 * MISSING JAVADOC.
 *
 */
public class ProgrammerCalculatorModel extends CalculatorModel {
    /**
     * MISSING JAVADOC. 
     */
       public ProgrammerCalculatorModel() {
           this.unaryOpMap.putAll(Map.of("not", new CCUnaryOperator((n) -> this.not(n), 0, null)
                                              ));
           //TODO add precedence and Type
           this.binaryOpMap.putAll(Map.of("and", new CCBinaryOperator((n1, n2) -> this.and(n1, n2), 0, null),
                                          "or", new CCBinaryOperator((n1, n2) -> this.or(n1, n2), 0, null)));
       }
       private double not(final double n) {
           return ~(int) n;
       }
       private double and(final double n1, final double n2) {
           return (int) n1 & (int) n2;
       }
       private double or(final double n1, final double n2) {
           return (int) n1 | (int) n2;
       }
       
       //TODO shiftL, shiftR, ror, rol, nand, nor, xor, unsigned not(?)
}
