package controller.manager;


/**
 * 
 */
public interface ManagerInterface {
//
//    /**
//     * Mount a selected calculator.
//     * @param calc Calculator to mount
//     */
//    void mount(Calculator calc);
//
//    /**
//     * Returns the currently mounted calculator.
//     * @return Calculator currently mounted
//     */
//    Calculator getMounted();
//
//    /**
//     * Calculates the result of the expression currently in memory.
//     */
//    void calculate();

    /**
     * 
     * @return memory
     */
    MemoryManager memory();

    /**
     * 
     * @return engine
     */
    EngineManager engine();
}
