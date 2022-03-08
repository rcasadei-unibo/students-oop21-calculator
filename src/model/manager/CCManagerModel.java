package model.manager;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 *
 */
public class CCManagerModel {

    private final List<String> buffer = new ArrayList<>();

    public void addInput(final String s) {
        this.buffer.add(s);
    }

    public List<String> getCurrentState() {
        return List.copyOf(this.buffer);
    }

    public void setCurrentState(final String string) {
        this.buffer.clear();
        this.buffer.add(string);
    }

    public void clearBuffer() {
        this.buffer.clear();
    }
}
