package controller.manager;

import java.util.List;
import java.util.stream.IntStream;

import utils.CalcException;
import utils.NumberFormatter;
import model.manager.CCManagerModel;
import model.manager.ManagerModelInterface.Calculator;

/**
 * The system manager. 
 * It's in charge of mounting the selected calculator and of calculating a given input list of strings.
 */
public class CCManager implements ManagerInterface {

    private final CCManagerModel model = new CCManagerModel();

    /**
     * Construct a new system manager. 
     * Note: the same CCManager instance must be referenced by all calculators in the system. 
     */
    public CCManager() {
        for (final Calculator calc : Calculator.values()) {
            calc.getController().setManager(this);
        }
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
        List.of(s.split("")).stream().forEach(x -> this.model.addInput(x));
    }

    @Override
    public void clear() {
        model.clearBuffer();
    }

    @Override
    public void mount(final Calculator calcName) {
        this.model.setMounted(calcName);
        this.clear();
    }

    @Override
    public Calculator getMounted() {
        return this.model.getMounted();
    }

    @Override
    public void deleteLast() {
        final var newState = this.model.getCurrentState();
        if (!newState.isEmpty()) {
            this.clear();
            IntStream.range(0, newState.size() - 1).forEach(i -> this.read(newState.get(i)));
        }
    }

    @Override
    public void calculate() {
        final var engine = new CCEngine(this.model.getMounted().getController());
        try {
            final double result = engine.calculate(this.getCurrentState());
            final int maxIntDigits = 7;
            final int maxDecDigits = 10;
            final int decimalThreshold = 5;

            final String formatted = NumberFormatter.format(result, maxIntDigits, maxDecDigits, decimalThreshold);
            if (result >= 0 && !formatted.contains("E")) {
                this.setCurrentState(formatted);
            } else {
                this.model.setCurrentState(formatted);
            }
        } catch (CalcException e) {
            this.model.setCurrentState(e.getMessage());
        }
    }
}
