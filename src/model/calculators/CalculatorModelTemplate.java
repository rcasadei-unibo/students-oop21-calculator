package model.calculators;

import java.util.HashMap;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;

/**
 * 
 * Implementation of the calculators Model. It contains two HashMaps: one with the binary operations of this calculator and one with the unary ones.
 *
 */
public final class CalculatorModelTemplate implements CalculatorModel {

    private final Map<String, CCBinaryOperator> binaryOpMap = new HashMap<>();
    private final Map<String, CCUnaryOperator> unaryOpMap = new HashMap<>();

    /**
     * 
     * @param binaryOpMap map containing all the binary operations of this calculator
     * @param unaryOpMap map containing all the unary operations of this calculator
     */
    public CalculatorModelTemplate(final Map<String, CCBinaryOperator> binaryOpMap, final Map<String, CCUnaryOperator> unaryOpMap) {
        this.binaryOpMap.putAll(binaryOpMap);
        this.unaryOpMap.putAll(unaryOpMap);
    }

    @Override
    public Map<String, CCBinaryOperator> getBinaryOpMap() {
        return this.binaryOpMap;
    }

    @Override
    public Map<String, CCUnaryOperator> getUnaryOpMap() {
        return this.unaryOpMap;
    }
}
