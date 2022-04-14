package utils;

import java.util.List;
import java.util.Map;

import model.calculators.StandardCalculatorModelFactory;

/**
 * 
 *
 */
public final class OutputFormatter {
    private final Map<String, CCBinaryOperator> binary = StandardCalculatorModelFactory.create().getBinaryOpMap();
    /**
     * 
     */
    private OutputFormatter() {
    }
    /**
     * 
     * @param buffer
     * @return str.
     */
    public static String format(final List<String> buffer) {
        return buffer.stream().reduce("", (a, b) -> a + b, null);
    }
}
