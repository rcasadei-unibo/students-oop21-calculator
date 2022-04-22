package model.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the memory manager.
 * It contains a reference to the input buffer.
 */
public class CCMemoryModel implements MemoryModelInterface {

    private final List<String> buffer = new ArrayList<>();
    private final List<String> history = new ArrayList<>();

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
    public void addToHistory(final String result) {
        this.history.add(result);
    }

    @Override
    public List<String> getHistory() {
        return List.copyOf(this.history);
    }

}
