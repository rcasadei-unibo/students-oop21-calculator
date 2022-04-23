package model.manager;

import java.util.Optional;

/**
 * Model for the engine manager.
 * It contains a reference to the currently mounted calculator.
 */
public class CCEngineModel implements EngineModelInterface {
    private Optional<Calculator> mounted;

    @Override
    public Optional<Calculator> getMounted() {
        return this.mounted;
    }

    @Override
    public void setMounted(final Calculator calculator) {
        this.mounted = Optional.of(calculator);
    }
}
