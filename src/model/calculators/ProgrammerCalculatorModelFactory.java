package model.calculators;
import java.util.HashMap;
import java.util.Map;
import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
import utils.ConversionAlgorithms;
import utils.Type;
/**
* This is a static factory for Bitwise operators.
*/
public final class ProgrammerCalculatorModelFactory {

    private ProgrammerCalculatorModelFactory() {
    }
    /**
     * @return a map containing the operator name and a function that applies the before-mentioned operator.
     */
    public static CalculatorModel create() {
        final Map<String, CCBinaryOperator> binaryOpMap = new HashMap<>(Map.of(
                "and", new CCBinaryOperator((n1, n2) -> and(n1, n2), 1, Type.LEFT),
                "or", new CCBinaryOperator((n1, n2) -> or(n1, n2), 1, null),
                "xor", new CCBinaryOperator((n1, n2) -> xor(n1, n2), 1, Type.LEFT), 
                "shiftR", new CCBinaryOperator((n1, n2) -> shiftR(n1, n2), 1, Type.LEFT), 
                "shiftL", new CCBinaryOperator((n1, n2) -> shiftL(n1, n2), 1, Type.LEFT),
                "nand",  new CCBinaryOperator((n1, n2) -> nand(n1, n2), 1, Type.LEFT),
                "nor",  new CCBinaryOperator((n1, n2) -> nor(n1, n2), 1, Type.LEFT),
                "roR",  new CCBinaryOperator((n1, n2) -> roR(n1, n2), 1, Type.LEFT),
                "roL",  new CCBinaryOperator((n1, n2) -> roL(n1, n2), 1, Type.LEFT)
                ));
        binaryOpMap.putAll(getBasicOperators());

        final Map<String, CCUnaryOperator> unaryOpMap = new HashMap<>(
                Map.of("not", new CCUnaryOperator((n1) -> not(n1), 1, null)
                  ));
        return new CalculatorModelTemplate(binaryOpMap, unaryOpMap);
    }
    private static Map<String, CCBinaryOperator> getBasicOperators() {
        final Map<String, CCBinaryOperator> x = StandardCalculatorModelFactory.create().getBinaryOpMap();
        x.remove("%");
        return x;
    }
    private static double and(final double n1, final double n2) {
        return (int) n1 & (int) n2;
    }
    private static double or(final double n1, final double n2) {
        return (int) n1 | (int) n2;
    }
    private static double xor(final double n1, final double n2) {
        return (int) n1 ^ (int) n2;
    }
    private static double shiftR(final double n1, final double n2) {
        return (int) n1 >> (int) n2;
    }
    private static double shiftL(final double n1, final double n2) {
        return (int) n1 << (int) n2;
    }
    private static double not(final double n1) {
        var stringBits = ConversionAlgorithms.conversionToStringBase(2, (int) n1);
        stringBits = addLeadingZerosToByte(stringBits);
        final var bits = stringBits.toCharArray();
        String toConvert = String.valueOf(bits[0]);
        for (int i = 1; i < bits.length; i++) {
            if ("1".equals(String.valueOf(bits[i]))) {
                toConvert = toConvert.concat("0");
            } else {
                toConvert = toConvert.concat("1");
            }
        }
        return ConversionAlgorithms.conversionToDecimal(2, toConvert);
    }
    private static String addLeadingZerosToByte(final String stringBits) {
        //1100 = -4 0100 = 4        1.0000.0100 = -4 0.0000.0100 = 4
        final var sign = stringBits.charAt(0);
        String stringBitsCopy = stringBits.substring(1);
        while (stringBitsCopy.length() % 8 != 0) {
            stringBitsCopy = "0".concat(stringBitsCopy);
        }
        return String.valueOf(sign).concat(stringBitsCopy);
    }
    private static double nand(final double n1, final double n2) {
        return not(and(n1, n2));
    }
    private static double nor(final double n1, final double n2) {
        return not(or(n1, n2));
    }
    private static int rollingCheck(final int length, final double n) {
        //in case it is asked to roll 9 on a byte it rolls 9-8
        if (length - 1 < (int) n) {
            return (int) (n % 8);
        }
        return (int) (n);
    }
    private static double roR(final double n1, final double n2) {
        String bits = addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBase(2, (int) n1));
        final int rorOf = rollingCheck(bits.length(), n2);
        final String sign = String.valueOf(bits.charAt(0));
        bits = bits.substring(1);
        //110011 = abs(10011) = roR(x,3) = "011" 10
        bits = bits.substring(bits.length() - rorOf).concat(bits.substring(0, bits.length() - rorOf));
        return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
    }
    private static double roL(final double n1, final double n2) {
        String bits = addLeadingZerosToByte(ConversionAlgorithms.conversionToStringBase(2, (int) n1));
        final int rolOf = rollingCheck(bits.length(), n2);
        final String sign = String.valueOf(bits.charAt(0));
        bits = bits.substring(1);
        //110011 = abs(10011) = roL(x,3) = "100" 11 = 11"100"
        //10011 = 10'011'
        bits = bits.substring(rolOf).concat(bits.substring(0, rolOf));
        return ConversionAlgorithms.conversionToDecimal(2, sign.concat(bits));
    }
}
