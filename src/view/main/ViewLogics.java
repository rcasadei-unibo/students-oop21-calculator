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
     * TODO: Javadoc.
     * @return
     */
    List<String> getHistory();
}
