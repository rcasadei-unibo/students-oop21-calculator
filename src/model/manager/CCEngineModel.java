package model.manager;

/**
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
