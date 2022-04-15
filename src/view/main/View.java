package view.main;

import model.manager.EngineModelInterface.Calculator;

/**
 * Main View component of the application. 
 * It provides a method to show the GUI relative to a given calculator. 
 */
public interface View {

    /**
     * Displays the given Calculator's GUI.
     * @param calc Calculator to show.
     */
    void show(Calculator calc);
}
