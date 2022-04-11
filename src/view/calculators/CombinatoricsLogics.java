package view.calculators;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public interface CombinatoricsLogics {
    /**
     * 
     * @param btnText
     * @return the String to be displayed after clicking on a number
     */
    String numberAction(String btnText);
    /**
     * 
     * @param btnName
     * @param opName
     * @return the String to be displayed after clicking on an operation
     */
    String opAction(String btnName, String opName);
    /**
     * 
     * @return the String to be displayed after clicking on the backspace button
     */
    String backspaceAction();
    /**
     * 
     * @return the current manager buffer made into a String
     */
    String getStream();
    /**
     * 
     * @return the String to be displayed(in the upper text)after clicking the '=' button
     */
    String calculateAction();
}
