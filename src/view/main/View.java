package view.main;

import model.manager.EngineModelInterface.Calculator;

/**
 * Main View component of the application. 
 * It provides a method to show the GUI relative to a given calculator. 
 */
public interface View {

    /**
     * 
     * @param calc
     */
    void show(Calculator calc);
}
