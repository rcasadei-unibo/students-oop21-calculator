package model.calculators;

import java.util.Map;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorModel extends StandardCalculatorModel {
    /**
     * 
     */
    public ScientificCalculatorModel() {
        super();
        this.unaryOpMap.putAll(Map.of("ln", new CCUnaryOperator((n) -> this.standardLog(n), 0, null),
                                      "mod", new CCUnaryOperator((n) -> this.mod(n), 0, null),
                                      "factorial", new CCUnaryOperator((n) -> this.factorial(n), 0, null),
                                      "exp", new CCUnaryOperator((n) -> this.exp, 0, null)
                             ));
        this.binaryOpMap.putAll(Map.of("log", new CCBinaryOperator((n, b) -> this.flexibleLog(n, b), 0, null),
                                       "nthRoot", new CCBinaryOperator((n, i) -> this.nthRoot, 0, null),
                                       "potenza", new CCBinaryOperator((n, e) -> this.potenza, 0, null)                                  
                             ));
    }
    
    
    
}
