package controller.manager;

import model.manager.EngineModelInterface.Calculator;

/**
 *
 */
public interface EngineManager {

    /**
     * Mount a selected calculator.
     * @param calc Calculator to mount
     */
    void mount(Calculator calc);

    /**
     * Returns the currently mounted calculator.
     * @return Calculator currently mounted
     */
    Calculator getMounted();

    /**
     * Calculates the result of the expression currently in memory.
     */
    void calculate();
}
