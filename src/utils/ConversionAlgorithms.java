package utils;

import java.util.LinkedList;
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
 * @param number
 *        the decimal number to be converted
 * 
 * 
 * @return a List containing the binary conversion of the number
 */
    public static List<Integer> conversionToBinary(int number) {
        final List<Integer> binary = new LinkedList<>();
        while (number > 0) {
            binary.add(0, number % 2);
            number = (int) (number / 2);
        }
        return binary;
    }
    /**
     * 
     * @param number
     *          the decimal number to be converted
     * @return  a List containing the numeric conversion of the number without the the letters
     */
    public static List<Integer> conversionToHexadecimal(int number){
        final List<Integer> hexadecimal = new LinkedList<>();
        while (number > 0) {
            hexadecimal.add(0, number % 16);
            number = (int) (number / 16);
        }
        return hexadecimal;
    }
    private static String hexadecimalLetters(final int number) {
        switch (number) {
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
            default:
                return String.valueOf(number);
        } 
    }
    /**
     * @param number
     *        the decimal number to be converted
     * @return
     *         a List containing the octal conversion of the number
     */
    public static List<Integer> conversionToOctal(int number) {
        final List<Integer> binary = new LinkedList<>();
        while (number > 0) {
            binary.add(0, number % 8);
            number = (int) (number / 8);
        }
        return binary;
    }
    /**
     * 
     * @param base the starting base of conversion
     * @param number to be converted
     * @return an Integer containing the decimal conversion of the number
     */
    public static int conversionToDecimal(int base,List<Integer> number) {
        int ret = 0;
        int i = 0;
        for(int index = number.size()-1; index >= 0; index--) {
            //[3,2,1,0] base 8 -> 3*8^3 + 2*8^2 + 1*8^1 + 0*1
            ret += number.get(i)*Math.pow(base, index);
            i++;
        }
        return ret;
    }
    
    //TODO implement methods to convert to negative numbers
}
