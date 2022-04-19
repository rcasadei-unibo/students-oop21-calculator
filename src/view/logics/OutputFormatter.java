package view.logics;
/**
 * This class handles the engine's memory displaying.
 */
public interface OutputFormatter {
    /**
     * This will update the display.
     */
    void updateDisplay();
    /**
     * This method will interpret the engine's memory.
     * @return the value to be displayed
     */
    String format();
}
