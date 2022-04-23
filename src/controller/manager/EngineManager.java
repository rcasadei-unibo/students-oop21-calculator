package controller.manager;

import java.util.Optional;

import model.manager.EngineModelInterface.Calculator;

/**
 * Interface for the engine manager.
 */
public interface EngineManager {

    /**
     * Mount a selected calculator. This calculator will be used for executing future calculations.
     * @param calc Calculator to mount
     */
    void mount(Calculator calc);

    /**
     * Returns the currently mounted calculator.
     * @return Optional of the calculator currently mounted
     */
    Optional<Calculator> getMounted();

    /**
     * Calculates the result of the expression currently in memory. The result will be stored in the input buffer.
     */
    void calculate();
}
