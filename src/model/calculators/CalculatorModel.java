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
    private final Map<String, CCBinaryOperator> binaryOpMap = new HashMap<>();
    private final Map<String, CCUnaryOperator> unaryOpMap = new HashMap<>();
    /**
     * 
     * @param binaryOpMap
     * @param unaryOpMap
     */
    public CalculatorModel(final Map<String, CCBinaryOperator> binaryOpMap, final Map<String, CCUnaryOperator> unaryOpMap) {
        this.binaryOpMap.putAll(binaryOpMap);
        this.unaryOpMap.putAll(unaryOpMap);
    }
    /**
     * 
     */
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
