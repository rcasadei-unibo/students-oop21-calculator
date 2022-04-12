package controller.manager;


/**
 * Interface for the system manager.
 */
public interface ManagerInterface {
    /**
     * Returns the memory manager of the system. It contains functionalities for reading input, getting current state of the system and retrieve the result of a calculation.
     * @return Memory manager for this system.
     */
    MemoryManager memory();

    /**
     * Returns the engine manager of the system. It contains functionalities for calculating and selecting the calculator to use.
     * @return Engine manager for this system.
     */
    EngineManager engine();
}
