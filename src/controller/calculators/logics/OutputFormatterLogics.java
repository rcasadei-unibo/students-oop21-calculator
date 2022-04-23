package controller.calculators.logics;
/**
 * This class handles the engine's memory displaying.
 */
public interface OutputFormatterLogics {
    /**
     * This will update the display.
     */
    void updateDisplay();
    /**
     * This method will interpret the engine's memory.
     * @return the value to be displayed
     */
    String format();
    /**
     * Updates the CCDisplay's upperText.
     */
    void updateDisplayUpperText();
    /**
     * Updated the history.
     * @param before the memory's value before calling "calculate" method.
     */
    void addResult(String before);
    /**
     * This method checks whether there is an error that is not to be written in the history.
     * @param before the memory's value before calling "calculate" method
     * @return true if there is no error.
     */
    default boolean checkForError(final String before) {
        return !(before.contains("Parenthesis mismatch") 
                || this.format().contains("Parenthesis mismatch")
                || before.contains("Syntax error") 
                || this.format().contains("Syntax error"));
    }
}
