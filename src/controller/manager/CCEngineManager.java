package controller.manager;

import java.util.Optional;

import model.manager.CCEngineModel;
import model.manager.EngineModelInterface;
import model.manager.EngineModelInterface.Calculator;
import utils.CalcException;

/**
 * Engine manager of the system.
 * It provides methods for selecting the calculator to use and to calculate the expression currently stored in the memory manager.
 */
public class CCEngineManager implements EngineManager {

    private final EngineModelInterface model = new CCEngineModel();
    private final MemoryManager memManager;

    /**
     * Construct an engine manager.
     * It will get the expression to calculate from the given memory manager.
     * @param memManager Memory manager used to retrieve the expression to calculate and to store the result.
     */
    public CCEngineManager(final MemoryManager memManager) {
        this.memManager = memManager;
    }

    @Override
    public void mount(final Calculator calcName) {
        this.model.setMounted(calcName);
        this.memManager.clear();
    }

    @Override
    public Optional<Calculator> getMounted() {
        return this.model.getMounted();
    }

    @Override
    public void calculate() {
        final var engine = new CCEngine(this.model.getMounted().get().getController());
        try {
            final String formatted = engine.calculateAndFormat(this.memManager.getCurrentState());
            if (Double.parseDouble(formatted) >= 0 && !formatted.contains("E")) {
                this.memManager.splitAndSetCurrentState(formatted);
            } else {
                this.memManager.setCurrentState(formatted);
            }
        } catch (CalcException e) {
            this.memManager.setErrorState(e.getMessage());
        }
    }

}
