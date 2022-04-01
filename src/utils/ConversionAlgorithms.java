package utils;


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
 * @param number
 *        the decimal number to be converted
 * 
 * 
 * @return a List containing the binary conversion of the number
 */
    /*public static List<Integer> conversionToBinary(int number) {
        final List<Integer> binary = new LinkedList<>();
        while (number > 0) {
            binary.add(0, number % 2);
            number = (int) (number / 2);
        }
        return binary;
    }*/
    /**
     * 
     * @param number
     * @return the string correspoding to the binary conversion of the number.
     *         Whereas: 
     *         "10" would become "01010"
     *         "-10" would become "11010"
     *         "0" is converted to "10" meaning it's "-0"
     */
    public static String conversionToStringBinary(final int number) {
        final String value = Integer.toBinaryString(Math.abs(number));
        return number > 0 ? "0".concat(value) : "1".concat(value);
    }
    /**
     * 
     * @param number
     *          the decimal number to be converted
     * @return  a List containing the numeric conversion of the number without the the letters
     */
   /* public static List<Integer> conversionToHexadecimal(int number){
        final List<Integer> hexadecimal = new LinkedList<>();
        while (number > 0) {
            hexadecimal.add(0, number % 16);
            number = (int) (number / 16);
        }
        return hexadecimal;
    }*/
    /**
     * 
     * @param number
     * @return the string correspoding to the hexadecimal conversion of the number.
     *         Whereas: 
     *         "10" would become "0A"
     *         "-10" would become "1A"
     */
    public static String conversionToStringHexadecimal(final int number) {
        final String value = Integer.toHexString(Math.abs(number));
        return number > 0 ? "0".concat(value).toUpperCase() : "1".concat(value).toUpperCase();
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
                return bit.equals("0") ? 0.0 : Integer.parseInt(bit);
        } 
    }
    /**
     * @param number
     *        the decimal number to be converted
     * @return
     *         a List containing the octal conversion of the number
     */
   /* public static List<Integer> conversionToOctal(int number) {
        final List<Integer> binary = new LinkedList<>();
        while (number > 0) {
            binary.add(0, number % 8);
            number = (int) (number / 8);
        }
        return binary;
    }*/
    /**
     * 
     * @param number
     * @return the string correspoding to the octal conversion of the number.
     *         Whereas: 
     *         "10" would become "012"
     *         "-10" would become "112"
     */
    public static String conversionToStringOctal(final int number) {
        final String value = Integer.toOctalString(Math.abs(number));
        return number > 0 ? "0".concat(value) : "1".concat(value);
    }
    /**
     * 
     * @param base the starting base of conversion
     * @param number to be converted
     * @return an Integer containing the decimal conversion of the number
     */
    public static int conversionToDecimal(final int base, final String number) {
        int ret = 0;
        /*int i = 0;
        for(int index = number.size()-1; index >= 0; index--) {
            //[3,2,1,0] base 8 -> 3*8^3 + 2*8^2 + 1*8^1 + 0*1
            ret += number.get(i)*Math.pow(base, index);
            i++;
        }*/
        final var bits = number.toCharArray();
        for (int i = 1; i < bits.length; i++) {
            if(base==16) {
                // "0FF" 255 = (+)FF
                ret += hexadecimalLetters(String.valueOf(bits[i])) * Math.pow(base, bits.length -1 - i);
            }
            else {
             // "11010" -10 = (-)1010
                ret += Integer.parseInt(String.valueOf(bits[i])) * Math.pow(base, bits.length -1 - i);
            }
            
        }
        return String.valueOf(bits[0]).equals("0") ? ret : (-1) * ret;
    }   
}
