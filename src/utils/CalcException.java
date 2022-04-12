package utils;
/**
 * 
 * Generic exception that comes with a String.
 *
 */
public class CalcException extends Exception {
    private static final long serialVersionUID = -5288360931446877427L;
    /**
     * 
     * @param msg a String containing information about the exception
     */
    public CalcException(final String msg) {
        super(msg);
    }
}
