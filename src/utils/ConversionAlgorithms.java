package utils;

import java.util.List;

/**
 * This class contains methods for conversions, such as:
 * -Decimal to Binary
 * -Binary to Octal, Hexadecimal.
 * -Utility for negative numbers.
 */
public final class ConversionAlgorithms {
    private ConversionAlgorithms() {
    }
    /**
     * 
     * @param number
     * @return the string correspoding to the binary conversion of the number.
     *         Whereas: 
     *         "10" would become "+1010"
     *         "-10" would become "-1010"
     *         "0" is converted to "+0" 
     */
    private static String conversionToStringBinary(final long number) {
        final String value = Long.toBinaryString(Math.abs(number));
        return number >= 0 ? "+".concat(value) : "-".concat(value);
    }
    
    
    /**
     * 
     * @param number
     * @return the string correspoding to the hexadecimal conversion of the number.
     *         Whereas: 
     *         "10" would become "0A"
     *         "-10" would become "-A"
     */
    private static String conversionToStringHexadecimal(final long number) {
        final String value = Long.toHexString(Math.abs(number));
        return number >= 0 ? "+".concat(value).toUpperCase() : "-".concat(value).toUpperCase();
    }
    private static double hexadecimalLetters(final String bit) {
        switch (bit) {
            case "A":
                return 10.0;
            case "B":
                return 11.0;
            case "C":
                return 12.0;
            case "D":
                return 13.0;
            case "E":
                return 14.0;
            case "F":
                return 15.0;
            default:
                return bit.equals("0") ? 0.0 : Long.parseLong(bit);
        } 
    }
    /**
     * 
     * @param number
     * @return the string correspoding to the octal conversion of the number.
     *         Whereas: 
     *         "10" would become "012"
     *         "-10" would become "112"
     */
    private static String conversionToStringOctal(final long number) {
        final String value = Long.toOctalString(Math.abs(number));
        return number >= 0 ? "+".concat(value) : "-".concat(value);
    }
    /**
     * Generic conversion algorithms that accepts base2, base8 and base10.
     * @param l the number to be converted
     * @param base in which the number will be converted
     * @return the string form of the converted number.
     * 
     */
    public static String conversionToStringBase(final int base, final long l) {
        switch (base) {
        case 2:
            return conversionToStringBinary(l);
        case 8:
            return conversionToStringOctal(l);
        case 16:
            return conversionToStringHexadecimal(l);
        default:
            return null;
        }
    }
    /**
     * 
     * @param base the starting base of conversion
     * @param number to be converted
     * @return an Integer containing the decimal conversion of the number
     */
    public static long conversionToDecimal(final int base, final String number) {
        long ret = 0;
        final var bits = number.toCharArray();
        for (int i = 1; i < bits.length; i++) {
            if (base == 16) {
                // "0FF" 255 = (+)FF
                ret += hexadecimalLetters(String.valueOf(bits[i])) * Math.pow(base, bits.length - 1 - i);
            } else {
             // "11010" -10 = (-)1010
                ret += Integer.parseInt(String.valueOf(bits[i])) * Math.pow(base, bits.length - 1 - i);
            }
        }
        return !String.valueOf(bits[0]).equals("-") ? ret : (-1) * ret;
    }
    /**
     * 
     * @param base
     * @param number
     * @return a.
     */
    public static int unsignedConversionToDecimal(final int base, final String number) {
        int ret = 0;
        final var bits = number.toCharArray();
        for (int i = 0; i < bits.length; i++) {
            if (base == 16) {
                ret += hexadecimalLetters(String.valueOf(bits[i])) * Math.pow(base, bits.length - 1 - i);
            } else {
                ret += Integer.parseInt(String.valueOf(bits[i])) * Math.pow(base, bits.length - 1 - i);
            }
        }
        return ret;
    }
    /**
     * @param base
     * @param number
     * @return a.
     */
    public static List<String> unsignedConversionToListDecimal(final int base, final String number){
        
        return null;
    }
}
