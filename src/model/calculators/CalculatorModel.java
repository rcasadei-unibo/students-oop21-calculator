package model.calculators;

import java.util.HashMap;
import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CalculatorModel {
    /**
     * 
     */
    protected Map<String, CCBinaryOperator> binaryOpMap = new HashMap<>();
    /**
     * 
     */
    protected Map<String, CCUnaryOperator> unaryOpMap = new HashMap<>();
    /**
     * 
     * @return a map containing all the binary operators
     */
    public Map<String, CCBinaryOperator> getBinaryOpMap() {
        return this.binaryOpMap;
    }
    /**
     * 
     * @return a map containing all the unary operators
     */
    public Map<String, CCUnaryOperator> getUnaryOpMap() {
        return this.unaryOpMap;
    }
}
