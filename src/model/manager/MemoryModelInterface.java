package model.manager;

import java.util.List;

/**
 * Interface for the model of the memory manager.
 */
public interface MemoryModelInterface {
    /**
     * Appends a string to the input buffer used for the calculation.
     * @param s String to be added.
     */
    void addInput(String s);

    /**
     * Returns the current state of the input buffer.
     * @return Unmodifiable list containing the strings in the input buffer.
     */
    List<String> getCurrentState();

    /**
     * Sets the input buffer to have s as the only element.
     * @param s String to set the input buffer to.
     */
    void setCurrentState(String s);

    /**
     * Removes all elements from input buffer.
     */
    void clearBuffer();

    /**
     * Adds a string to the history.
     * @param s String to add to the history.
     */
    void addToHistory(String s);

    List<String> getHistory(); 
}
