package view.main;

import java.util.List;

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

    /**
     * Fetch the history of calculations from the memory manager.
     * @return List of string storing the history.
     */
    List<String> getHistory();
}
