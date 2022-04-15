package controller.manager;

import java.util.List;
import java.util.stream.IntStream;

import model.manager.CCMemoryModel;
import model.manager.MemoryModelInterface;

/**
 * Memory manager of the system.
 * It provides methods for manipulating the input buffer and retrieving the result of a calculation.
 */
public class CCMemoryManager implements MemoryManager {

    private final MemoryModelInterface model;
    private boolean errorState;

    /**
     * Construct a new memory manager with an empty input buffer.
     */
    public CCMemoryManager() {
        this.model = new CCMemoryModel();
    }

    @Override
    public void read(final String s) {
        if (this.errorState) {
            this.clear();
            this.errorState = false;
        }
        if ("-".equals(s)) {
            final var buffer = model.getCurrentState();
            if (buffer.isEmpty() || "(".equals(buffer.get(buffer.size() - 1))) {
                model.addInput("0");
            }
        }
        model.addInput(s);
    }

    @Override
    public void readAll(final List<String> list) {
        list.forEach(s -> this.read(s));
    }

    @Override
    public List<String> getCurrentState() {
        return model.getCurrentState();
    }

    @Override
    public void setCurrentState(final String s) {
        this.clear();
        this.model.setCurrentState(s);
    }

    @Override
    public void splitAndSetCurrentState(final String s) {
        this.clear();
        List.of(s.split("")).stream().forEach(x -> this.model.addInput(x));
    }

    @Override
    public void clear() {
        model.clearBuffer();
    }

    @Override
    public void deleteLast() {
        final var state = this.model.getCurrentState();
        if (!state.isEmpty()) {
            this.clear();
            IntStream.range(0, state.size() - 1).forEach(i -> this.read(state.get(i)));
        }
    }

    @Override
    public void addResult(final String result) {
        this.model.addToHistory(result);
    }

    @Override
    public List<String> getHistory() {
        return this.model.getHistory();
    }

    @Override
    public void setErrorState(final String message) {
        this.errorState = true;
        this.setCurrentState(message);
    }

}
