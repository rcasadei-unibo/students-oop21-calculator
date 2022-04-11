package controller.manager;

import model.manager.EngineModelInterface.Calculator;

/**
 * The system manager. 
 * It contains references to the memory and engine managers.
 */
public class CCManager implements ManagerInterface {

    private final MemoryManager memManager; 
    private final EngineManager engineManager;

    /**
     * Construct a new system manager, initializes memory and engine managers and sets this manager as manager for all calculators in the system. 
     * Note that the same CCManager instance must be referenced by all calculators in the system. 
     */
    public CCManager() {
        this.memManager = new CCMemoryManager();
        this.engineManager = new CCEngineManager(this.memManager);

        for (final Calculator calc : Calculator.values()) {
            calc.getController().setManager(this);
        }
    }

    @Override
    public MemoryManager memory() {
        return this.memManager;
    }

    @Override
    public EngineManager engine() {
        return this.engineManager;
    }

}
