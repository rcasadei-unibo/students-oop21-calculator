package model.manager;

/**
 * Model for the engine manager.
 * It contains a reference to the currently mounted calculator.
 */
public class CCEngineModel implements EngineModelInterface {
    private Calculator mounted;

    @Override
    public Calculator getMounted() {
        return this.mounted;
    }

    @Override
    public void setMounted(final Calculator calculator) {
        this.mounted = calculator;
    }
}
