package view.logics;
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
}
