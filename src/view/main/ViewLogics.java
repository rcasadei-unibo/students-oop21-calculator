package view.main;

import model.manager.EngineModelInterface.Calculator;

/**
 * Logics for the main View component of the application.
 */
public interface ViewLogics {

    /**
     * Select the calculator to use and display.
     * @param calc calculator to show.
     */
    void mount(Calculator calc);
}
