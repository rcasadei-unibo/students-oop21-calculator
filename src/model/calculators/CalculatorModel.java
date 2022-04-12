package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;

/**
 * 
 * Interface for the model of all the calculators. It contains all the operations(Unary/Binary)that this Calculator can do.
 *
 */
public interface CalculatorModel {
    /**
     * 
     * @return a map containing all the binary operations of this calculator
     */
    Map<String, CCBinaryOperator> getBinaryOpMap();

    /**
     * 
     * @return a map containing all the unary operations of this calculator
     */
    Map<String, CCUnaryOperator> getUnaryOpMap();
}
