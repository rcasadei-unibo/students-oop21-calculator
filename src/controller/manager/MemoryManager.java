package controller.manager;

import java.util.List;

/**
 */
public interface MemoryManager {
    /**
     * Appends a given string to the input buffer.
     * @param s Input string to be read
     */
    void read(String s);

    /**
     * Appends all the strings in the given list to the input buffer.
     * @param list List of strings to append
     */
    void readAll(List<String> list);

    /**
     * Returns the current state of the input list.
     * @return List containing the strings given as inputs.
     */
    List<String> getCurrentState();

    /**
     * Sets the current state of the input buffer. Each element of the string will be read individually.
     * @param s String used to set the new state of the input buffer.
     */
    void setCurrentState(String s);

    /**
     * Removes all elements from input list.
     */
    void clear();

    /**
     * Removes last element in the input list.
     */
    void deleteLast();

    /**
     * TODO:Javadoc.
     * @param s
     */
    void splitAndSetCurrentState(String s);
}
