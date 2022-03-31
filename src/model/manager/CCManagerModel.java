package model.manager;

import java.util.List;

import java.util.ArrayList;

/**
 * Model of the system manager. 
 * Contains the currently mounted calculator and the input buffer.
 */
public class CCManagerModel implements ManagerModelInterface {

    private Calculator mounted;
    private final List<String> buffer = new ArrayList<>();

    @Override
    public void addInput(final String s) {
        this.buffer.add(s);
    }

    @Override
    public List<String> getCurrentState() {
        return List.copyOf(this.buffer);
    }

    @Override
    public void setCurrentState(final String s) {
        this.buffer.clear();
        this.buffer.add(s);
    }

    @Override
    public void clearBuffer() {
        this.buffer.clear();
    }

    @Override
    public Calculator getMounted() {
        return this.mounted;
    }

    @Override
    public void setMounted(final Calculator calculator) {
        this.mounted = calculator;
    }
}
