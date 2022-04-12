package controller.manager;

import java.util.List;
import java.util.stream.IntStream;

import model.manager.CCMemoryModel;
import model.manager.MemoryModelInterface;

/**
 * 
 */
public class CCMemoryManager implements MemoryManager {

    private final MemoryModelInterface model;
    /**
     * 
     */
    public CCMemoryManager() {
        this.model = new CCMemoryModel();
    }

    @Override
    public void read(final String s) {
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

}
