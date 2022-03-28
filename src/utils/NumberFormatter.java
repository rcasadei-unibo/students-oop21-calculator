package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 
 */
public final class NumberFormatter {

    private NumberFormatter() { };

    /**
     * Removes trailing zeros from the decimal part of the number.
     * 
     * @param value String value of the number
     * @return Trimmed string
     */
    public static String trimZeros(final String value) {
        String s = value;
        String exp = "";
        if (value.contains("E")) {
            final int expPos = value.indexOf('E');
            s = value.substring(0, expPos);
            exp = trimLeadingZeros(value.substring(expPos));
        }

        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "").concat(exp) : s.concat(exp);
    }

    private static String trimLeadingZeros(final String s) {
        return s.replaceFirst("0+", "");
    }

    /**
     * 
     * Formats a given number according to a maximum number of digits for the integer part and for the decimal part.
     * If the number of integer digits of the number exceeds the maximum, the number will be formatted in exponential notation.
     * If the number of decimal digits of the number exceeds the maximum, the number will be rounded and, when necessary, formatted in exponential notation. 
     * 
     * @param number Double value to format
     * @param maxIntegerDigits Maximum number of digits for the integer part of the number
     * @param maxDecimalDigits Maximum number of digits for the decimal part of the number
     * @return Formatted string representation of the number
     */
    public static String format(final double number, final int maxIntegerDigits, final int maxDecimalDigits, final int decimalThreshold) {
        final String value = trimZeros(String.format("%.200f", number).replace(',', '.'));

        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        final DecimalFormat df = new DecimalFormat("0", symbols);
        df.setMaximumFractionDigits(maxDecimalDigits);

        final List<String> patt = new ArrayList<>();
        patt.add("0.");
        IntStream.range(0, maxDecimalDigits).forEach(i ->  patt.add("0"));
        final String pattern = patt.stream().reduce("", (a, b) -> a + b);

        if (integerDigits(value) > maxIntegerDigits) {
            //number too big, format in scientific notation 
            df.applyPattern(pattern + "E0000"); 
        } else if (Math.abs(number) < Math.pow(10, -decimalThreshold) && number != 0.0) {
            //number smaller than the given threshold, format in scientific notation
            df.applyPattern(pattern + "E0000");
        } else if (decimalDigits(value) > maxDecimalDigits) {
            //number with too many decimal digits, round to the maximum amount
            df.applyPattern(pattern); 
        } 
        return trimZeros(df.format(Double.valueOf(value)));
    }

    private static int decimalDigits(final String value) {
        if (!value.contains(".")) {
            return 0; 
        }
        final String[] s = value.split("\\.");
        return s[1].length();
    }

    private static int integerDigits(final String value) {
        if (!value.contains(".")) {
            return value.length(); 
        }
        final String[] s = value.split("\\.");
        return s[0].length();
    }

}
