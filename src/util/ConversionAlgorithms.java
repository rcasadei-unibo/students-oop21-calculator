package util;

import java.util.LinkedList;
import java.util.List;

/**
 * This class contains methods for conversions, such as:
 * -Decimal to Binary
 * -Binary to Octal, Hexadecimal.
 * -Utility for negative numbers.
 * 
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
    /**
     * 
     * @param number
     *        the number to be converted to string
     * @return
     *        the string version of the number except for hexadecimal values
     *        10->A
     *        11->B...
     */
    public static String hexadecimalLetters(int number) {
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
     * @param number
     * @return the string binary conversion of number
     */
    public static String conversionToStringBinary(final int number) {
        return Integer.toBinaryString(number);
    }
    //TODO implement binary to hexadecimal methods and viceversa
    
    //TODO implement binary to octal methods and viceversa
    
    //TODO implement methods to convert to negative numbers
}
