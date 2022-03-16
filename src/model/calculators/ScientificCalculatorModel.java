package model.calculators;

import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

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
        this.unaryOpMap.putAll(Map.of("ln", new CCUnaryOperator((n) -> this.ln(n), 0, null),
                                      "abs", new CCUnaryOperator((n) -> this.abs(n), 0, null),
                                      "factorial", new CCUnaryOperator((n) -> (double)this.factorial(n), 0, null)                                      
                             ));
        this.binaryOpMap.putAll(Map.of("log", new CCBinaryOperator((n, b) -> this.log(n, b), 0, null),
                                       "nthRoot", new CCBinaryOperator((n, i) -> this.nthRoot(n, i), 0, null),
                                       "potenza", new CCBinaryOperator((n, e) -> this.potenza(n, e), 0, null)                                  
                             ));
    }
    
    private double ln(final double n) {
        return Math.log(n);
    }
    
    private double abs(final double n) {
        return Math.abs(n);
    }
    
    private long factorial(final double n) {
        return LongStream.rangeClosed(1, Math.round(n)).reduce(1, (long n1, long n2) -> n1*n2);             
    }
    
    private double log(final double n, final double b) {
        return this.ln(n)/this.ln(b);
    }
    
    private double nthRoot(final double n, final double i) {
        return Math.pow(n, 1/i);
    }
    
    private double potenza(final double n, final double e) {
        return Math.pow(n, e);
    }
    
}
