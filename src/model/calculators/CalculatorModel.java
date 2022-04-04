package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public interface CalculatorModel {
    /**
     * 
     * @return a map containing all the binary operators
     */
    Map<String, CCBinaryOperator> getBinaryOpMap();
    /**
     * 
     * @return a map containing all the unary operators
     */
    Map<String, CCUnaryOperator> getUnaryOpMap();
}
