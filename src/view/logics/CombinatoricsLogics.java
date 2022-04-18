package view.logics;

/**
 * 
 * Interface of the logic used by CombinatoricsCalculatorPanel.
 *
 */
public interface CombinatoricsLogics {

    /**
     * 
     * @param btnText the value of the button that has been clicked on
     * @return the String to be displayed after clicking on a number
     */
    String numberAction(String btnText);

    /**
     * 
     * @param btnName the name of the button of the operation
     * @param opName the name that the same operation uses in Model
     * @return the String to be displayed after clicking on an operation
     */
    String operationAction(String btnName, String opName);

    /**
     * 
     * @return the String to be displayed after clicking on the backspace button
     */
    String backspaceAction();

    /**
     * 
     * @return the current manager buffer made into a String
     */
    String getBufferToString();

    /**
     * 
     * @return the String to be displayed(in the upper text)after clicking on the '=' button
     */
    String calculateAction();
}
